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

import java.util.Random;
import java.util.UUID;

public class TurkeyEntity extends AnimalEntity implements IEggLayingEntity, IAngerable {
	private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.defineId(TurkeyEntity.class, DataSerializers.INT);

	private float wingRotation;
	private float destPos;
	private float oFlapSpeed;
	private float oFlap;
	private float wingRotDelta = 1.0F;

	private float peckTicks;
	private float prevPeckTicks;

	public int timeUntilNextEgg = this.random.nextInt(9600) + 9600;
	public boolean turkeyJockey;

	private static final RangedInteger ANGER_RANGE = TickRangeConverter.rangeOfSeconds(20, 39);
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
		this.goalSelector.addGoal(5, new TemptGoal(this, 1.0D, false, Ingredient.of(AutumnityTags.TURKEY_BREEDING_ITEMS)));
		this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new TurkeyEntity.HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new TurkeyEntity.JockeyTargetGoal<>(this, PlayerEntity.class));
		this.targetSelector.addGoal(4, new ResetAngerGoal<>(this, true));
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isBaby() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ANGER_TIME, 0);
	}

	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 4) {
			this.peckTicks = 8;
		} else {
			super.handleEntityEvent(id);
		}
	}

	@Override
	public boolean doHurtTarget(Entity entityIn) {
		this.peckTicks = 8;
		this.level.broadcastEntityEvent(this, (byte) 4);

		return super.doHurtTarget(entityIn);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.level.isClientSide) {
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
		Vector3d vector3d = this.getDeltaMovement();
		if (!this.onGround && vector3d.y < 0.0D) {
			this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
		}

		if (!this.level.isClientSide) {
			if (this.isAlive() && !this.isBaby() && !this.isTurkeyJockey() && --this.timeUntilNextEgg <= 0) {
				this.playSound(AutumnitySoundEvents.ENTITY_TURKEY_EGG.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
				this.spawnAtLocation(AutumnityItems.TURKEY_EGG.get());
				this.timeUntilNextEgg = this.getNextEggTime(this.random);
			}

			this.updatePersistentAnger((ServerWorld) this.level, true);
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
	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return this.isAngry() ? AutumnitySoundEvents.ENTITY_TURKEY_AGGRO.get() : AutumnitySoundEvents.ENTITY_TURKEY_AMBIENT.get();
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
	public SoundEvent getEggLayingSound() {
		return AutumnitySoundEvents.ENTITY_TURKEY_EGG.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return Ingredient.of(AutumnityTags.TURKEY_BREEDING_ITEMS).test(stack);
	}

	@Override
	protected int getExperienceReward(PlayerEntity player) {
		return this.isTurkeyJockey() ? 10 : super.getExperienceReward(player);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.turkeyJockey = compound.getBoolean("IsTurkeyJockey");
		if (compound.contains("EggLayTime")) {
			this.timeUntilNextEgg = compound.getInt("EggLayTime");
		}
		this.readPersistentAngerSaveData((ServerWorld) this.level, compound);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("IsTurkeyJockey", this.turkeyJockey);
		compound.putInt("EggLayTime", this.timeUntilNextEgg);
		this.addPersistentAngerSaveData(compound);
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return this.isTurkeyJockey();
	}

	@Override
	public void positionRider(Entity passenger) {
		super.positionRider(passenger);
		float f = MathHelper.sin(this.yBodyRot * ((float) Math.PI / 180F));
		float f1 = MathHelper.cos(this.yBodyRot * ((float) Math.PI / 180F));
		passenger.setPos(this.getX() + (double) (0.1F * f), this.getY(0.5D) + passenger.getMyRidingOffset() + 0.0D, this.getZ() - (double) (0.1F * f1));
		if (passenger instanceof LivingEntity) {
			((LivingEntity) passenger).yBodyRot = this.yBodyRot;
		}
	}

	public boolean isTurkeyJockey() {
		return this.turkeyJockey;
	}

	public void setTurkeyJockey(boolean jockey) {
		this.turkeyJockey = jockey;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(ANGER_TIME);
	}

	@Override
	public void setRemainingPersistentAngerTime(int time) {
		this.entityData.set(ANGER_TIME, time);
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
		return AutumnityEntities.TURKEY.get().create(world);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(AutumnityItems.TURKEY_SPAWN_EGG.get());
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.lastHurtBy;
	}

	@Override
	public void setPersistentAngerTarget(UUID target) {
		this.lastHurtBy = target;
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(ANGER_RANGE.randomValue(this.random));
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

		public boolean canUse() {
			return this.mob.isBaby() && super.canUse();
		}
	}

	static class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
		public HurtByTargetGoal(TurkeyEntity turkey) {
			super(turkey);
		}

		protected void alertOther(MobEntity mobIn, LivingEntity targetIn) {
			if (mobIn instanceof TurkeyEntity && !mobIn.isBaby()) {
				super.alertOther(mobIn, targetIn);
			}
		}

		public void start() {
			super.start();

			if (this.mob.isBaby()) {
				this.alertOthers();
				this.stop();
			}
		}
	}

	static class JockeyTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
		public JockeyTargetGoal(TurkeyEntity turkey, Class<T> classTarget) {
			super(turkey, classTarget, true);
		}

		public boolean canUse() {
			TurkeyEntity turkey = (TurkeyEntity) this.mob;

			return turkey.isTurkeyJockey() && super.canUse();
		}
	}
}
