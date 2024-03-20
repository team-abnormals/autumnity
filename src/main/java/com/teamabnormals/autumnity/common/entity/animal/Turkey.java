package com.teamabnormals.autumnity.common.entity.animal;

import com.teamabnormals.autumnity.core.other.tags.AutumnityItemTags;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.autumnity.core.registry.AutumnitySoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

public class Turkey extends Animal implements NeutralMob {
	private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(Turkey.class, EntityDataSerializers.INT);

	private float wingRotation;
	private float destPos;
	private float oFlapSpeed;
	private float oFlap;
	private float wingRotDelta = 1.0F;

	private float peckTicks;
	private float prevPeckTicks;

	public int timeUntilNextEgg = this.random.nextInt(9600) + 9600;
	public boolean turkeyJockey;

	private static final UniformInt ANGER_RANGE = TimeUtil.rangeOfSeconds(20, 39);
	private UUID lastHurtBy;

	public Turkey(EntityType<? extends Animal> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new Turkey.PanicGoal(this));
		this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.3F));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.4D, false));
		this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new TemptGoal(this, 1.0D, Ingredient.of(AutumnityItemTags.TURKEY_FOOD), false));
		this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new Turkey.HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new Turkey.JockeyTargetGoal<>(this, Player.class));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, true));
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return this.isBaby() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ANGER_TIME, 0);
	}

	@Override
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
		this.level().broadcastEntityEvent(this, (byte) 4);

		return super.doHurtTarget(entityIn);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (this.level().isClientSide) {
			// Wing rotation
			this.oFlap = this.wingRotation;
			this.oFlapSpeed = this.destPos;
			this.destPos = (float) ((double) this.destPos + (double) (this.onGround() ? -1 : 4) * 0.3D);
			this.destPos = Mth.clamp(this.destPos, 0.0F, 1.0F);
			if (!this.onGround() && this.wingRotDelta < 1.0F) {
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
		Vec3 vector3d = this.getDeltaMovement();
		if (!this.onGround() && vector3d.y < 0.0D) {
			this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
		}

		if (!this.level().isClientSide) {
			if (this.isAlive() && !this.isBaby() && !this.isTurkeyJockey() && --this.timeUntilNextEgg <= 0) {
				this.playSound(AutumnitySoundEvents.ENTITY_TURKEY_EGG.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
				this.spawnAtLocation(AutumnityItems.TURKEY_EGG.get());
				this.timeUntilNextEgg = this.getRandomNextEggTime(this.random);
			}

			this.updatePersistentAnger((ServerLevel) this.level(), true);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public float getWingRotation(float partialTicks) {
		float f = Mth.lerp(partialTicks, this.oFlap, this.wingRotation);
		float f1 = Mth.lerp(partialTicks, this.oFlapSpeed, this.destPos);
		return (Mth.sin(f) + 1.0F) * f1;
	}

	@OnlyIn(Dist.CLIENT)
	public float getPeckProgress(float partialTicks) {
		float f = Mth.lerp(partialTicks, this.prevPeckTicks, this.peckTicks) / 8.0F;

		if (f < 0.5F) {
			return 2.0F * f;
		} else {
			return -2.0F * f + 2.0F;
		}
	}

	@Override
	public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
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
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return Ingredient.of(AutumnityItemTags.TURKEY_FOOD).test(stack);
	}

	@Override
	public int getExperienceReward() {
		return this.isTurkeyJockey() ? 10 : super.getExperienceReward();
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.turkeyJockey = compound.getBoolean("IsTurkeyJockey");
		if (compound.contains("EggLayTime")) {
			this.timeUntilNextEgg = compound.getInt("EggLayTime");
		}
		this.readPersistentAngerSaveData(this.level(), compound);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
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
	public void positionRider(Entity passenger, Entity.MoveFunction function) {
		super.positionRider(passenger, function);
		float f = Mth.sin(this.yBodyRot * ((float) Math.PI / 180F));
		float f1 = Mth.cos(this.yBodyRot * ((float) Math.PI / 180F));
		function.accept(passenger, this.getX() + (double) (0.1F * f), this.getY(0.5D) + passenger.getMyRidingOffset() + 0.0D, this.getZ() - (double) (0.1F * f1));
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
	public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob ageable) {
		return AutumnityEntityTypes.TURKEY.get().create(world);
	}

	@Override
	public ItemStack getPickedResult(HitResult target) {
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
		this.setRemainingPersistentAngerTime(ANGER_RANGE.sample(this.random));
	}

	public int getRandomNextEggTime(RandomSource rand) {
		return rand.nextInt(9600) + 9600;
	}

	static class PanicGoal extends net.minecraft.world.entity.ai.goal.PanicGoal {
		public PanicGoal(Turkey turkey) {
			super(turkey, 1.4D);
		}

		@Override
		public boolean canUse() {
			return this.mob.isBaby() && super.canUse();
		}
	}

	static class HurtByTargetGoal extends net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal {
		public HurtByTargetGoal(Turkey turkey) {
			super(turkey);
		}

		@Override
		protected void alertOther(Mob mobIn, LivingEntity targetIn) {
			if (mobIn instanceof Turkey && !mobIn.isBaby()) {
				super.alertOther(mobIn, targetIn);
			}
		}

		@Override
		public void start() {
			super.start();

			if (this.mob.isBaby()) {
				this.alertOthers();
				this.stop();
			}
		}
	}

	static class JockeyTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
		public JockeyTargetGoal(Turkey turkey, Class<T> classTarget) {
			super(turkey, classTarget, true);
		}

		@Override
		public boolean canUse() {
			Turkey turkey = (Turkey) this.mob;

			return turkey.isTurkeyJockey() && super.canUse();
		}
	}
}
