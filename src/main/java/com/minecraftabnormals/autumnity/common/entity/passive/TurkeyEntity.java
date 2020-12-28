package com.minecraftabnormals.autumnity.common.entity.passive;

import com.minecraftabnormals.autumnity.core.other.AutumnityTags;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEntities;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import com.minecraftabnormals.autumnity.core.registry.AutumnitySoundEvents;
import com.minecraftabnormals.environmental.api.IEggLayingEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;
import java.util.UUID;

public class TurkeyEntity extends AnimalEntity implements IEggLayingEntity, IAngerable {
	private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.createKey(TurkeyEntity.class, DataSerializers.VARINT);

	private float wingRotation;
	private float destPos;
	private float oFlapSpeed;
	private float oFlap;
	private float wingRotDelta = 1.0F;

	private float peckTicks;
	private float prevPeckTicks;

	public int timeUntilNextEgg = this.rand.nextInt(9600) + 9600;
	public boolean turkeyJockey;

	private static final RangedInteger ANGER_RANGE = TickRangeConverter.convertRange(20, 39);
	private UUID lastHurtBy;

	public TurkeyEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new TurkeyEntity.PanicGoal(this));
		this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.3F));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.4D, false));
		this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new TemptGoal(this, 1.0D, false, Ingredient.fromTag(AutumnityTags.TURKEY_BREEDING_ITEMS)));
		this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new TurkeyEntity.HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
		this.targetSelector.addGoal(3, new TurkeyEntity.JockeyTargetGoal<>(this, PlayerEntity.class));
		this.targetSelector.addGoal(4, new ResetAngerGoal<>(this, true));
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isChild() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 12.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(ANGER_TIME, 0);
	}

	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 4) {
			this.peckTicks = 8;
		} else {
			super.handleStatusUpdate(id);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		this.peckTicks = 8;
		this.world.setEntityState(this, (byte) 4);

		return super.attackEntityAsMob(entityIn);
	}

	@Override
	public void livingTick() {
		super.livingTick();

		if (this.world.isRemote) {
			// Wing rotation
			this.oFlap = this.wingRotation;
			this.oFlapSpeed = this.destPos;
			this.destPos = (float) ((double) this.destPos + (double) (this.onGround ? -1 : 4) * 0.3D);
			this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
			if (!this.onGround && this.wingRotDelta < 1.0F) {
				this.wingRotDelta = 1.0F;
			}
			this.wingRotDelta = (float) ((double) this.wingRotDelta * 0.9D);
			this.wingRotation += this.wingRotDelta * 2.0F;
		}

		// Peck progress
		this.prevPeckTicks = this.peckTicks;

		if (this.peckTicks > 0) {
			this.peckTicks--;
		}

		// Motion
		Vector3d vector3d = this.getMotion();
		if (!this.onGround && vector3d.y < 0.0D) {
			this.setMotion(vector3d.mul(1.0D, 0.6D, 1.0D));
		}

		if (!this.world.isRemote) {
			if (this.isAlive() && !this.isChild() && !this.isTurkeyJockey() && --this.timeUntilNextEgg <= 0) {
				this.playSound(AutumnitySoundEvents.ENTITY_TURKEY_EGG.get(), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				this.entityDropItem(AutumnityItems.TURKEY_EGG.get());
				this.timeUntilNextEgg = this.getNextEggTime(this.rand);
			}

			this.func_241359_a_((ServerWorld) this.world, true);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public float getWingRotation(float partialTicks) {
		float f = MathHelper.lerp(partialTicks, this.oFlap, this.wingRotation);
		float f1 = MathHelper.lerp(partialTicks, this.oFlapSpeed, this.destPos);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}

	@OnlyIn(Dist.CLIENT)
	public float getPeckProgress(float partialTicks) {
		float f = MathHelper.lerp(partialTicks, this.prevPeckTicks, this.peckTicks) / 8.0F;

		if (f < 0.5F) {
			return 2.0F * f;
		} else {
			return -2.0F * f + 2.0F;
		}
	}

	@Override
	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return this.func_233678_J__() ? AutumnitySoundEvents.ENTITY_TURKEY_AGGRO.get() : AutumnitySoundEvents.ENTITY_TURKEY_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return AutumnitySoundEvents.ENTITY_TURKEY_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return AutumnitySoundEvents.ENTITY_TURKEY_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return Ingredient.fromTag(AutumnityTags.TURKEY_BREEDING_ITEMS).test(stack);
	}

	@Override
	protected int getExperiencePoints(PlayerEntity player) {
		return this.isTurkeyJockey() ? 10 : super.getExperiencePoints(player);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.turkeyJockey = compound.getBoolean("IsTurkeyJockey");
		if (compound.contains("EggLayTime")) {
			this.timeUntilNextEgg = compound.getInt("EggLayTime");
		}
		this.readAngerNBT((ServerWorld) this.world, compound);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("IsTurkeyJockey", this.turkeyJockey);
		compound.putInt("EggLayTime", this.timeUntilNextEgg);
		this.writeAngerNBT(compound);
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return this.isTurkeyJockey();
	}

	@Override
	public void updatePassenger(Entity passenger) {
		super.updatePassenger(passenger);
		float f = MathHelper.sin(this.renderYawOffset * ((float) Math.PI / 180F));
		float f1 = MathHelper.cos(this.renderYawOffset * ((float) Math.PI / 180F));
		passenger.setPosition(this.getPosX() + (double) (0.1F * f), this.getPosYHeight(0.5D) + passenger.getYOffset() + 0.0D, this.getPosZ() - (double) (0.1F * f1));
		if (passenger instanceof LivingEntity) {
			((LivingEntity) passenger).renderYawOffset = this.renderYawOffset;
		}
	}

	public boolean isTurkeyJockey() {
		return this.turkeyJockey;
	}

	public void setTurkeyJockey(boolean jockey) {
		this.turkeyJockey = jockey;
	}

	@Override
	public int getAngerTime() {
		return this.dataManager.get(ANGER_TIME);
	}

	@Override
	public void setAngerTime(int time) {
		this.dataManager.set(ANGER_TIME, time);
	}

	@Override
	public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity ageable) {
		return AutumnityEntities.TURKEY.get().create(world);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(AutumnityItems.TURKEY_SPAWN_EGG.get());
	}

	@Override
	public UUID getAngerTarget() {
		return this.lastHurtBy;
	}

	@Override
	public void setAngerTarget(UUID target) {
		this.lastHurtBy = target;
	}

	@Override
	public void func_230258_H__() {
		this.setAngerTime(ANGER_RANGE.getRandomWithinRange(this.rand));
	}

	@Override
	public int getEggTimer() {
		return this.timeUntilNextEgg;
	}

	@Override
	public void setEggTimer(int time) {
		this.timeUntilNextEgg = time;
	}

	@Override
	public boolean isBirdJockey() {
		return this.turkeyJockey;
	}

	@Override
	public Item getEggItem() {
		return AutumnityItems.TURKEY_EGG.get();
	}

	@Override
	public int getNextEggTime(Random rand) {
		return rand.nextInt(9600) + 9600;
	}

	static class PanicGoal extends net.minecraft.entity.ai.goal.PanicGoal {
		public PanicGoal(TurkeyEntity turkey) {
			super(turkey, 1.4D);
		}

		public boolean shouldExecute() {
			return this.creature.isChild() && super.shouldExecute();
		}
	}

	static class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
		public HurtByTargetGoal(TurkeyEntity turkey) {
			super(turkey);
		}

		protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn) {
			if (mobIn instanceof TurkeyEntity && !mobIn.isChild()) {
				super.setAttackTarget(mobIn, targetIn);
			}
		}

		public void startExecuting() {
			super.startExecuting();

			if (this.goalOwner.isChild()) {
				this.alertOthers();
				this.resetTask();
			}
		}
	}

	static class JockeyTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
		public JockeyTargetGoal(TurkeyEntity turkey, Class<T> classTarget) {
			super(turkey, classTarget, true);
		}

		public boolean shouldExecute() {
			TurkeyEntity turkey = (TurkeyEntity) this.goalOwner;

			return turkey.isTurkeyJockey() && super.shouldExecute();
		}
	}
}
