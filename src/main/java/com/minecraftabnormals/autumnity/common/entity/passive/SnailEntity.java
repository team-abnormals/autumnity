package com.minecraftabnormals.autumnity.common.entity.passive;

import com.minecraftabnormals.autumnity.core.other.AutumnityCriteriaTriggers;
import com.minecraftabnormals.autumnity.core.other.AutumnityTags;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEntities;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import com.minecraftabnormals.autumnity.core.registry.AutumnitySoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SoupItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class SnailEntity extends AnimalEntity {
	private static final UUID HIDING_ARMOR_BONUS_ID = UUID.fromString("73BF0604-4235-4D4C-8A74-6A633E526E24");
	private static final AttributeModifier HIDING_ARMOR_BONUS_MODIFIER = new AttributeModifier(HIDING_ARMOR_BONUS_ID, "Hiding armor bonus", 20.0D, AttributeModifier.Operation.ADDITION);
	private static final DataParameter<Integer> EATING_TIME = EntityDataManager.defineId(SnailEntity.class, DataSerializers.INT);
	private static final DataParameter<Boolean> HIDING = EntityDataManager.defineId(SnailEntity.class, DataSerializers.BOOLEAN);
	private int hidingTime = 0;
	private int slimeAmount = 0;

	private float hideTicks;
	private float prevHideTicks;

	private int shakeTicks;
	private int prevShakeTicks;

	// This exists so snails would work with Quark feeding troughs
	private boolean canBreed = true;

	private static final Predicate<LivingEntity> ENEMY_MATCHER = (livingentity) -> {
		if (livingentity == null) {
			return false;
		} else {
			livingentity.getItemBySlot(EquipmentSlotType.CHEST);
			if (livingentity.getItemBySlot(EquipmentSlotType.CHEST).getItem() == AutumnityItems.SNAIL_SHELL_CHESTPLATE.get()) {
				return false;
			} else if (livingentity instanceof PlayerEntity) {
				return !livingentity.isShiftKeyDown() && !livingentity.isSpectator() && !((PlayerEntity) livingentity).isCreative();
			} else {
				return !(livingentity instanceof SnailEntity) && !(livingentity instanceof MooshroomEntity);
			}
		}
	};

	public SnailEntity(EntityType<? extends SnailEntity> type, World worldIn) {
		super(type, worldIn);
		this.lookControl = new SnailEntity.LookHelperController();
		this.moveControl = new SnailEntity.MoveHelperController();
		this.setPathfindingMalus(PathNodeType.DANGER_CACTUS, 0.0F);
		this.setPathfindingMalus(PathNodeType.DAMAGE_CACTUS, 0.0F);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SnailEntity.HideGoal());
		this.goalSelector.addGoal(1, new SnailEntity.StopHidingGoal());
		this.goalSelector.addGoal(2, new BreedGoal(this, 0.5D));
		this.goalSelector.addGoal(3, new SnailEntity.FollowFoodGoal());
		this.goalSelector.addGoal(4, new SnailEntity.EatMushroomsGoal());
		this.goalSelector.addGoal(5, new SnailEntity.EatMooshroomMushroomsGoal());
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.5D));
		this.goalSelector.addGoal(7, new SnailEntity.WatchGoal());
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 18.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return sizeIn.height * 0.5F;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(AutumnityItems.SNAIL_SPAWN_EGG.get());
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return AutumnitySoundEvents.ENTITY_SNAIL_DEATH.get();
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return AutumnitySoundEvents.ENTITY_SNAIL_HURT.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(AutumnitySoundEvents.ENTITY_SNAIL_STEP.get(), 0.4F, 1.0F);
	}

	@Nullable
	@Override
	public SoundEvent getEatingSound(ItemStack itemStackIn) {
		return null;
	}

	@Override
	public void tick() {
		super.tick();

		if (this.level.isClientSide) {
			this.prevHideTicks = this.hideTicks;
			if (this.getHiding()) {
				this.hideTicks = MathHelper.clamp(this.hideTicks + 1, 0, 3);
			} else {
				this.hideTicks = MathHelper.clamp(this.hideTicks - 0.5F, 0, 3);
			}

			this.prevShakeTicks = this.shakeTicks;
			if (this.shakeTicks > 0) {
				this.shakeTicks = MathHelper.clamp(this.shakeTicks - 1, 0, 20);
			} else {
				this.shakeTicks = MathHelper.clamp(this.shakeTicks + 1, -20, 0);
			}
		}
	}

	@Override
	public void aiStep() {
		if (!this.canMove() || this.isImmobile()) {
			this.jumping = false;
			this.xxa = 0.0F;
			this.zza = 0.0F;
		}

		super.aiStep();

		if (this.isEating()) {
			this.eat();
			if (!this.level.isClientSide) {
				this.setEatingTime(this.getEatingTime() - 1);
			}
		}

		if (!this.level.isClientSide && this.isAlive()) {
			ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.MAINHAND);
			if (!itemstack.isEmpty()) {
				if (this.isFoodItem(itemstack)) {
					if (!this.isEating()) {
						this.setSlimeAmount(this.random.nextInt(3) + 5);

						if (Ingredient.of(AutumnityTags.SNAIL_GLOWING_FOODS).test(itemstack)) {
							this.addEffect(new EffectInstance(Effects.GLOWING, 200, 0));
						}
						if (Ingredient.of(AutumnityTags.SNAIL_SPEEDING_FOODS).test(itemstack)) {
							this.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 320, 2));
						}

						Item item = itemstack.getItem();
						ItemStack itemstack1 = itemstack.finishUsingItem(this.level, this);
						if (!itemstack1.isEmpty()) {
							if (itemstack1.getItem() != item) {
								this.setItemSlot(EquipmentSlotType.MAINHAND, itemstack1);
								this.spitOutItem();
							} else {
								this.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
							}
						}
					}
				} else {
					this.spitOutItem();
				}
			}

			if (this.getSlimeAmount() > 0 && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
				int i = MathHelper.floor(this.getX());
				int j = MathHelper.floor(this.getY());
				int k = MathHelper.floor(this.getZ());
				BlockState blockstate = AutumnityBlocks.SNAIL_SLIME.get().defaultBlockState();

				for (int l = 0; l < 4; ++l) {
					i = MathHelper.floor(this.getX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
					j = MathHelper.floor(this.getY());
					k = MathHelper.floor(this.getZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
					BlockPos blockpos = new BlockPos(i, j, k);
					if (this.getSlimeAmount() > 0 && this.level.isEmptyBlock(blockpos) && blockstate.canSurvive(this.level, blockpos)) {
						this.level.setBlockAndUpdate(blockpos, blockstate);
						this.setSlimeAmount(this.getSlimeAmount() - 1);
					}
				}
			}

			if (this.hidingTime > 0) {
				this.hidingTime--;
			}
		}
	}

	public void eat() {
		if ((this.getEatingTime() + 1) % 12 == 0 && !this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty()) {
			this.playSound(AutumnitySoundEvents.ENTITY_SNAIL_EAT.get(), 0.25F + 0.5F * (float) this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

			for (int i = 0; i < 6; ++i) {
				Vector3d vector3d = new Vector3d(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double) this.random.nextFloat() - 0.5D) * 0.1D);
				vector3d = vector3d.xRot(-this.xRot * ((float) Math.PI / 180F));
				vector3d = vector3d.yRot(-this.yRot * ((float) Math.PI / 180F));
				double d0 = (double) (-this.random.nextFloat()) * 0.2D;
				Vector3d vector3d1 = new Vector3d(((double) this.random.nextFloat() - 0.5D) * 0.2D, d0, 0.8D + ((double) this.random.nextFloat() - 0.5D) * 0.2D);
				vector3d1 = vector3d1.yRot(-this.yBodyRot * ((float) Math.PI / 180F));
				vector3d1 = vector3d1.add(this.getX(), this.getY() + (double) this.getEyeHeight(), this.getZ());
				this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItemBySlot(EquipmentSlotType.MAINHAND)), vector3d1.x, vector3d1.y, vector3d1.z, vector3d.x, vector3d.y + 0.05D, vector3d.z);
			}
		}
	}

	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();
		if (!this.isBaby() && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			this.spawnAtLocation(AutumnityItems.SNAIL_SHELL_PIECE.get(), 1);
		}
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		if (!this.getHiding() && !this.isEating()) {
			ItemStack itemstack = player.getItemInHand(hand);
			if (!itemstack.isEmpty() && this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty()) {
				if (this.isFoodItem(itemstack)) {
					if (!this.isBaby() && this.getSlimeAmount() <= 0) {
						if (!this.level.isClientSide) {
							ItemStack itemstack1 = itemstack.copy();
							itemstack1.setCount(1);
							this.setItemSlot(EquipmentSlotType.MAINHAND, itemstack1);
							this.setEatingTime(192);
							AutumnityCriteriaTriggers.FEED_SNAIL.trigger((ServerPlayerEntity) player, itemstack1);
							this.usePlayerItem(player, itemstack);
						}
						return ActionResultType.sidedSuccess(level.isClientSide());
					}
				} else if (this.isSnailBreedingItem(itemstack)) {
					boolean flag = false;

					if (!this.level.isClientSide && this.getAge() == 0 && this.canFallInLove()) {
						this.setInLove(player);
						flag = true;
					}
					else if (this.isBaby()) {
						this.ageUp((int) ((float) (-this.getAge() / 20) * 0.1F), true);
						flag = true;
					}

					if (flag) {
						if (!this.level.isClientSide && !player.abilities.instabuild) {
							ItemStack container = itemstack.getContainerItem();
							if (container.isEmpty() && itemstack.getItem() instanceof SoupItem)
								container = new ItemStack(Items.BOWL);

							itemstack.shrink(1);

							if (!container.isEmpty()) {
								if (itemstack.isEmpty()) {
									player.setItemInHand(hand, container);
								}
								else {
									if (!player.inventory.add(container)) {
										player.drop(container, false);
									}
								}
							}
						}

						return ActionResultType.sidedSuccess(level.isClientSide());
					}
				}
			}
		}

		this.canBreed = false;
		ActionResultType result = super.mobInteract(player, hand);
		this.canBreed = true;
		return result;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) {
			return false;
		} else {
			Entity entity = source.getDirectEntity();
			if (this.getHiding() && entity instanceof AbstractArrowEntity) {
				return false;
			} else if (source == DamageSource.CACTUS) {
				return false;
			}

			boolean flag = super.hurt(source, amount);

			if (!this.level.isClientSide) {
				if (!this.isNoAi()) {
					this.hidingTime = 160 + random.nextInt(200);
					this.setHiding(true);
				}
				this.spitOutItem();
			} else {
				this.shakeTicks = this.random.nextInt(2) == 0 ? -10 : 10;
			}

			return flag;
		}
	}

	private void spitOutItem() {
		ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.MAINHAND);
		if (!itemstack.isEmpty()) {
			if (!this.level.isClientSide) {
				ItemEntity itementity = new ItemEntity(this.level, this.getX() + this.getLookAngle().x, this.getY() + this.getEyeHeight(), this.getZ() + this.getLookAngle().z, itemstack);
				itementity.setPickUpDelay(40);
				itementity.setThrower(this.getUUID());
				this.level.addFreshEntity(itementity);
				this.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
				this.setEatingTime(0);
			}
		}
	}

	public int getEatingTime() {
		return this.entityData.get(EATING_TIME);
	}

	public void setEatingTime(int eatingTimeIn) {
		this.entityData.set(EATING_TIME, eatingTimeIn);
	}

	public boolean isEating() {
		return this.getEatingTime() > 0;
	}

	public boolean getHiding() {
		return this.entityData.get(HIDING);
	}

	public void setHiding(boolean hiding) {
		if (hiding) {
			this.entityData.set(HIDING, true);
		} else {
			this.entityData.set(HIDING, false);
		}

		if (!this.level.isClientSide) {
			this.getAttribute(Attributes.ARMOR).removeModifier(HIDING_ARMOR_BONUS_MODIFIER);
			if (hiding) {
				this.getAttribute(Attributes.ARMOR).addTransientModifier(HIDING_ARMOR_BONUS_MODIFIER);
			}
		}
	}

	public boolean canMove() {
		return !this.getHiding() && !this.isEating();
	}

	private int getSlimeAmount() {
		return this.slimeAmount;
	}

	private void setSlimeAmount(int slimeAmountIn) {
		this.slimeAmount = slimeAmountIn;
	}

	@OnlyIn(Dist.CLIENT)
	public float getHidingAnimationScale(float partialTicks) {
		return MathHelper.lerp(partialTicks, this.prevHideTicks, this.hideTicks) / 3.0F;
	}

	@OnlyIn(Dist.CLIENT)
	public float getHideTicks() {
		return this.hideTicks;
	}

	@OnlyIn(Dist.CLIENT)
	public float getShakingAnimationScale(float partialTicks) {
		return MathHelper.lerp(partialTicks, this.prevShakeTicks, this.shakeTicks) / 10.0F;
	}

	@OnlyIn(Dist.CLIENT)
	public float getShakeTicks() {
		return this.shakeTicks;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(EATING_TIME, 0);
		this.entityData.define(HIDING, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("SlimeAmount", this.getSlimeAmount());
		compound.putBoolean("Hiding", this.getHiding());
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.setSlimeAmount(compound.getInt("SlimeAmount"));
		this.setHiding(compound.getBoolean("Hiding"));
	}

	private boolean isFoodItem(ItemStack stack) {
		return Ingredient.of(AutumnityTags.SNAIL_FOODS).test(stack);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return this.canBreed && this.isSnailBreedingItem(stack);
	}

	private boolean isSnailBreedingItem(ItemStack stack) {
		return Ingredient.of(AutumnityTags.SNAIL_BREEDING_ITEMS).test(stack);
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
		return AutumnityEntities.SNAIL.get().create(world);
	}

	@Override
	protected float nextStep() {
		return this.moveDist + 0.6F;
	}

	public class HideGoal extends Goal {
		public HideGoal() {
			super();
		}

		public boolean canUse() {
			if (!SnailEntity.this.getHiding() && !SnailEntity.this.isEating()) {
				for (LivingEntity livingentity : SnailEntity.this.level.getEntitiesOfClass(LivingEntity.class, SnailEntity.this.getBoundingBox().inflate(0.3D), ENEMY_MATCHER)) {
					if (livingentity.isAlive() && livingentity.getBbHeight() > SnailEntity.this.getBbHeight()) {
						return true;
					}
				}
			}

			return false;
		}

		public void start() {
			SnailEntity.this.hidingTime = 100 + random.nextInt(100);
			SnailEntity.this.setHiding(true);
		}

		public boolean canContinueToUse() {
			return false;
		}
	}

	public class StopHidingGoal extends Goal {
		public StopHidingGoal() {
			super();
		}

		public boolean canUse() {
			return SnailEntity.this.getLastHurtByMob() == null && SnailEntity.this.hidingTime <= 0;
		}

		public void start() {
			SnailEntity.this.setHiding(false);
		}

		public boolean canContinueToUse() {
			return false;
		}
	}

	class FollowFoodGoal extends TemptGoal {
		public FollowFoodGoal() {
			super(SnailEntity.this, 0.5F, false, null);
		}

		public boolean canUse() {
			return SnailEntity.this.canMove() && super.canUse();
		}

		protected boolean shouldFollowItem(ItemStack stack) {
			return Ingredient.of(AutumnityTags.SNAIL_TEMPTATION_ITEMS).test(stack);
		}
	}

	class WatchGoal extends LookAtGoal {
		public WatchGoal() {
			super(SnailEntity.this, PlayerEntity.class, 6.0F);
		}

		public boolean canUse() {
			return super.canUse() && !SnailEntity.this.getHiding();
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && !SnailEntity.this.getHiding();
		}
	}

	class EatMushroomsGoal extends Goal {
		private double mushroomX;
		private double mushroomY;
		private double mushroomZ;

		public EatMushroomsGoal() {
			super();
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			if (SnailEntity.this.getRandom().nextInt(20) != 0) {
				return false;
			} else {
				return !SnailEntity.this.isBaby() && SnailEntity.this.canMove() && SnailEntity.this.getSlimeAmount() <= 0 && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(SnailEntity.this.level, SnailEntity.this) && this.canMoveToMushroom();
			}
		}

		protected boolean canMoveToMushroom() {
			Vector3d vec3d = this.findMushroom();
			if (vec3d == null) {
				return false;
			} else {
				this.mushroomX = vec3d.x;
				this.mushroomY = vec3d.y;
				this.mushroomZ = vec3d.z;
				return true;
			}
		}

		public boolean canContinueToUse() {
			return !SnailEntity.this.getNavigation().isDone();
		}

		public void start() {
			SnailEntity.this.getNavigation().moveTo(this.mushroomX, this.mushroomY, this.mushroomZ, 0.5D);
		}

		@Nullable
		protected Vector3d findMushroom() {
			Random random = SnailEntity.this.getRandom();
			BlockPos blockpos = new BlockPos(SnailEntity.this.getX(), SnailEntity.this.getBoundingBox().minY, SnailEntity.this.getZ());

			for (int i = 0; i < 10; ++i) {
				BlockPos blockpos1 = blockpos.offset(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
				if (this.isBlockMushroom(blockpos1)) {
					return new Vector3d((double) blockpos1.getX(), (double) blockpos1.getY(), (double) blockpos1.getZ());
				}
			}

			return null;
		}

		public void tick() {
			if (!SnailEntity.this.isBaby() && SnailEntity.this.canMove() && SnailEntity.this.getSlimeAmount() <= 0) {
				BlockPos blockpos = SnailEntity.this.blockPosition();

				if (this.isBlockMushroom(blockpos)) {
					if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(SnailEntity.this.level, SnailEntity.this)) {
						SnailEntity.this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(SnailEntity.this.level.getBlockState(blockpos).getBlock().asItem(), 1));
						SnailEntity.this.setEatingTime(192);
						SnailEntity.this.level.destroyBlock(blockpos, false);
					}
				}
			}
		}

		private boolean isBlockMushroom(BlockPos pos) {
			return AutumnityTags.SNAIL_BLOCK_FOODS.contains(SnailEntity.this.level.getBlockState(pos).getBlock());
		}
	}

	public class EatMooshroomMushroomsGoal extends Goal {
		private MooshroomEntity targetMooshroom;
		private int delayCounter;

		public EatMooshroomMushroomsGoal() {
			super();
		}

		public boolean canUse() {
			if (!SnailEntity.this.isBaby() && SnailEntity.this.canMove() && SnailEntity.this.getSlimeAmount() <= 0) {
				List<MooshroomEntity> list = SnailEntity.this.level.getEntitiesOfClass(MooshroomEntity.class, SnailEntity.this.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
				MooshroomEntity mooshroom = null;
				double d0 = Double.MAX_VALUE;

				for (MooshroomEntity mooshroom1 : list) {
					if (mooshroom1.getAge() >= 0) {
						double d1 = SnailEntity.this.distanceToSqr(mooshroom1);
						if (!(d1 > d0)) {
							d0 = d1;
							mooshroom = mooshroom1;
						}
					}
				}

				if (mooshroom == null) {
					return false;
				} else {
					this.targetMooshroom = mooshroom;
					return true;
				}
			} else {
				return false;
			}
		}

		public boolean canContinueToUse() {
			if (!this.targetMooshroom.isAlive()) {
				return false;
			} else if (!SnailEntity.this.canMove()) {
				return false;
			} else if (SnailEntity.this.getSlimeAmount() > 0) {
				return false;
			} else {
				double d0 = this.targetMooshroom.distanceToSqr(SnailEntity.this);
				return !(d0 > 256.0D);
			}
		}

		public void start() {
			this.delayCounter = 0;
		}

		public void stop() {
			this.targetMooshroom = null;
		}

		public void tick() {
			if (--this.delayCounter <= 0) {
				this.delayCounter = 10;
				SnailEntity.this.getNavigation().moveTo(this.targetMooshroom, 0.5D);
			}

			if (this.targetMooshroom != null && this.targetMooshroom.isAlive()) {
				double d0 = this.targetMooshroom.distanceToSqr(SnailEntity.this);
				if (d0 < 2.0D) {
					if (this.targetMooshroom.getMushroomType() == MooshroomEntity.Type.BROWN) {
						SnailEntity.this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BROWN_MUSHROOM, 1));
					} else {
						SnailEntity.this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.RED_MUSHROOM, 1));
					}

					SnailEntity.this.setEatingTime(192);
					this.targetMooshroom.hurt(DamageSource.mobAttack(SnailEntity.this), 0.0F);
				}
			}
		}
	}

	public class LookHelperController extends LookController {
		public LookHelperController() {
			super(SnailEntity.this);
		}

		public void tick() {
			if (!SnailEntity.this.getHiding()) {
				super.tick();
			}
		}
	}

	class MoveHelperController extends MovementController {
		public MoveHelperController() {
			super(SnailEntity.this);
		}

		public void tick() {
			if (SnailEntity.this.canMove()) {
				super.tick();
			}
		}
	}
}