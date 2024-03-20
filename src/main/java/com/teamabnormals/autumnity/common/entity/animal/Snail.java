package com.teamabnormals.autumnity.common.entity.animal;

import com.teamabnormals.autumnity.core.other.AutumnityCriteriaTriggers;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBlockTags;
import com.teamabnormals.autumnity.core.other.tags.AutumnityItemTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.autumnity.core.registry.AutumnitySoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class Snail extends Animal {
	private static final UUID HIDING_ARMOR_BONUS_ID = UUID.fromString("73BF0604-4235-4D4C-8A74-6A633E526E24");
	private static final AttributeModifier HIDING_ARMOR_BONUS_MODIFIER = new AttributeModifier(HIDING_ARMOR_BONUS_ID, "Hiding armor bonus", 20.0D, AttributeModifier.Operation.ADDITION);
	private static final EntityDataAccessor<Integer> GOO_AMOUNT = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> ACTION = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.BYTE);
	private int hidingTime = 0;

	private float hideAmount;
	private float hideAmountO;

	private int shakeAmount;
	private int shakeAmountO;

	// This exists so snails would work with Quark feeding troughs
	private boolean canBreed = true;

	private static final Predicate<LivingEntity> ENEMY_MATCHER = (livingentity) -> {
		if (livingentity == null) {
			return false;
		} else {
			livingentity.getItemBySlot(EquipmentSlot.CHEST);
			if (livingentity.getItemBySlot(EquipmentSlot.CHEST).getItem() == AutumnityItems.SNAIL_SHELL_CHESTPLATE.get()) {
				return false;
			} else if (livingentity instanceof Player) {
				return !livingentity.isShiftKeyDown() && !livingentity.isSpectator() && !((Player) livingentity).isCreative();
			} else {
				return !(livingentity instanceof Snail) && !(livingentity instanceof MushroomCow);
			}
		}
	};

	public Snail(EntityType<? extends Snail> type, Level worldIn) {
		super(type, worldIn);
		this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0F);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new Snail.HideGoal());
		this.goalSelector.addGoal(1, new Snail.EatGoal());
		this.goalSelector.addGoal(2, new BreedGoal(this, 0.5D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 0.5D, Ingredient.of(AutumnityItemTags.SNAIL_TEMPT_ITEMS), false));
		this.goalSelector.addGoal(4, new Snail.EatMushroomsGoal());
		this.goalSelector.addGoal(5, new Snail.EatMooshroomMushroomsGoal());
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.5D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 18.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(GOO_AMOUNT, 0);
		this.entityData.define(ACTION, (byte) 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("GooAmount", this.getGooAmount());
		compound.putInt("HidingTime", this.getHidingTime());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setGooAmount(compound.getInt("GooAmount"));
		this.setHidingTime(compound.getInt("HidingTime"));
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return sizeIn.height * 0.5F;
	}

	@Override
	public ItemStack getPickedResult(HitResult target) {
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

		if (this.level().isClientSide) {
			this.hideAmountO = this.hideAmount;
			if (this.getAction() == Action.HIDING) {
				this.hideAmount = Mth.clamp(this.hideAmount + 1, 0, 3);
			} else {
				this.hideAmount = Mth.clamp(this.hideAmount - 0.5F, 0, 3);
			}

			this.shakeAmountO = this.shakeAmount;
			if (this.shakeAmount > 0) {
				this.shakeAmount = Mth.clamp(this.shakeAmount - 1, 0, 20);
			} else {
				this.shakeAmount = Mth.clamp(this.shakeAmount + 1, -20, 0);
			}
		}
	}

	@Override
	public void aiStep() {
		if (this.getAction() != Action.DEFAULT || this.isImmobile()) {
			this.jumping = false;
			this.xxa = 0.0F;
			this.zza = 0.0F;
		}

		super.aiStep();

		if (this.getAction() == Action.EATING) {
			this.eat();
		}

		if (!this.level().isClientSide && this.isAlive()) {
			if (!this.getMainHandItem().isEmpty() && !this.hasSnack()) {
				this.spitOutItem();
			}

			if (this.getGooAmount() > 0 && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this)) {
				BlockState blockstate = AutumnityBlocks.SNAIL_GOO.get().defaultBlockState();
				BlockPos blockpos = this.blockPosition();
				if (this.getGooAmount() > 0 && this.level().isEmptyBlock(blockpos) && blockstate.canSurvive(this.level(), blockpos)) {
					this.level().setBlockAndUpdate(blockpos, blockstate);
					this.setGooAmount(this.getGooAmount() - 1);
				}
			}
		}
	}

	public void eat() {
		if (this.tickCount % 12 == 0 && !this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
			this.playSound(AutumnitySoundEvents.ENTITY_SNAIL_EAT.get(), 0.25F + 0.5F * (float) this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

			if (this.level().isClientSide) {
				for (int i = 0; i < 6; ++i) {
					Vec3 vector3d = new Vec3(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double) this.random.nextFloat() - 0.5D) * 0.1D);
					vector3d = vector3d.xRot(-this.getXRot() * ((float) Math.PI / 180F));
					vector3d = vector3d.yRot(-this.getYRot() * ((float) Math.PI / 180F));
					double d0 = (double) (-this.random.nextFloat()) * 0.2D;
					Vec3 vector3d1 = new Vec3(((double) this.random.nextFloat() - 0.5D) * 0.2D, d0, 0.8D + ((double) this.random.nextFloat() - 0.5D) * 0.2D);
					vector3d1 = vector3d1.yRot(-this.yBodyRot * ((float) Math.PI / 180F));
					vector3d1 = vector3d1.add(this.getX(), this.getY() + (double) this.getEyeHeight(), this.getZ());
					this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItemBySlot(EquipmentSlot.MAINHAND)), vector3d1.x, vector3d1.y, vector3d1.z, vector3d.x, vector3d.y + 0.05D, vector3d.z);
				}
			}
		}
	}

	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();
		if (!this.isBaby() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
			this.spawnAtLocation(AutumnityItems.SNAIL_SHELL_PIECE.get(), 1);
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		if (this.getAction() == Action.DEFAULT) {
			ItemStack itemstack = player.getItemInHand(hand);
			if (!itemstack.isEmpty() && !this.hasSnack()) {
				if (this.isSnack(itemstack)) {
					if (!this.isBaby() && this.getGooAmount() <= 0) {
						if (!this.level().isClientSide) {
							ItemStack itemstack1 = itemstack.copy();
							itemstack1.setCount(1);
							this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
							AutumnityCriteriaTriggers.FEED_SNAIL.trigger((ServerPlayer) player, itemstack1);
							this.usePlayerItem(player, hand, itemstack);
						}
						return InteractionResult.sidedSuccess(this.level().isClientSide());
					}
				} else if (this.isSnailBreedingItem(itemstack)) {
					boolean flag = false;

					if (!this.level().isClientSide && this.getAge() == 0 && this.canFallInLove()) {
						this.setInLove(player);
						flag = true;
					} else if (this.isBaby()) {
						this.ageUp((int) ((float) (-this.getAge() / 20) * 0.1F), true);
						flag = true;
					}

					if (flag) {
						if (!this.level().isClientSide && !player.getAbilities().instabuild) {
							ItemStack container = itemstack.getCraftingRemainingItem();
							if (container.isEmpty() && itemstack.getItem() instanceof BowlFoodItem)
								container = new ItemStack(Items.BOWL);

							itemstack.shrink(1);

							if (!container.isEmpty()) {
								if (itemstack.isEmpty()) {
									player.setItemInHand(hand, container);
								} else {
									if (!player.getInventory().add(container)) {
										player.drop(container, false);
									}
								}
							}
						}

						return InteractionResult.sidedSuccess(this.level().isClientSide());
					}
				}
			}
		}

		this.canBreed = false;
		InteractionResult result = super.mobInteract(player, hand);
		this.canBreed = true;
		return result;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) {
			return false;
		} else {
			Entity entity = source.getDirectEntity();
			if (this.getAction() == Action.HIDING && entity instanceof AbstractArrow) {
				return false;
			} else if (source.is(DamageTypes.CACTUS)) {
				return false;
			}

			this.spitOutItem();

			if (this.level().isClientSide) {
				this.shakeAmount = this.random.nextInt(2) == 0 ? -10 : 10;
			}

			return super.hurt(source, amount);
		}
	}

	private void spitOutItem() {
		ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
		if (!itemstack.isEmpty() && !this.level().isClientSide) {
			ItemEntity itementity = new ItemEntity(this.level(), this.getX() + this.getLookAngle().x, this.getY() + this.getEyeHeight(), this.getZ() + this.getLookAngle().z, itemstack);
			itementity.setPickUpDelay(40);
			itementity.setThrower(this.getUUID());
			this.level().addFreshEntity(itementity);
			this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}
	}

	private void eatSnack() {
		ItemStack itemstack = this.getMainHandItem();

		if (Ingredient.of(AutumnityItemTags.SNAIL_GLOW_SNACKS).test(itemstack)) {
			this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
		}
		if (Ingredient.of(AutumnityItemTags.SNAIL_SPEED_SNACKS).test(itemstack)) {
			this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 320, 2));
		}

		Item item = itemstack.getItem();
		ItemStack itemstack1 = itemstack.finishUsingItem(this.level(), this);
		if (!itemstack1.isEmpty()) {
			if (itemstack1.getItem() != item) {
				this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
				this.spitOutItem();
			} else {
				this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			}
		}

		this.setGooAmount(4);
	}

	private boolean hasSnack() {
		return this.isSnack(this.getMainHandItem());
	}

	private int getGooAmount() {
		return this.entityData.get(GOO_AMOUNT);
	}

	private void setGooAmount(int amount) {
		this.entityData.set(GOO_AMOUNT, amount);
	}

	private int getHidingTime() {
		return this.hidingTime;
	}

	private void setHidingTime(int hidingTimeIn) {
		this.hidingTime = hidingTimeIn;
	}

	public Action getAction() {
		return Action.byId(this.entityData.get(ACTION));
	}

	private void setAction(Action action) {
		this.entityData.set(ACTION, (byte) action.getId());

		if (!this.level().isClientSide) {
			this.getAttribute(Attributes.ARMOR).removeModifier(HIDING_ARMOR_BONUS_MODIFIER);
			if (action == Action.HIDING) {
				this.getAttribute(Attributes.ARMOR).addTransientModifier(HIDING_ARMOR_BONUS_MODIFIER);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public float getHideAmount(float partialTicks) {
		return Mth.lerp(partialTicks, this.hideAmountO, this.hideAmount) / 3.0F;
	}

	@OnlyIn(Dist.CLIENT)
	public float getShakeAmount(float partialTicks) {
		return Mth.lerp(partialTicks, this.shakeAmountO, this.shakeAmount) / 20.0F;
	}

	private boolean isSnack(ItemStack stack) {
		return Ingredient.of(AutumnityItemTags.SNAIL_SNACKS).test(stack);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return this.canBreed && this.isSnailBreedingItem(stack);
	}

	private boolean isSnailBreedingItem(ItemStack stack) {
		return Ingredient.of(AutumnityItemTags.SNAIL_FOOD).test(stack);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob ageable) {
		return AutumnityEntityTypes.SNAIL.get().create(world);
	}

	@Override
	protected float nextStep() {
		return this.moveDist + 0.6F;
	}

	public enum Action {
		DEFAULT(0),
		EATING(1),
		HIDING(2);

		private static final Action[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(Action::getId)).toArray(Action[]::new);
		private final int id;

		Action(int idIn) {
			this.id = idIn;
		}

		public int getId() {
			return this.id;
		}

		public static Action byId(int indexIn) {
			if (indexIn < 0 || indexIn >= VALUES.length) {
				indexIn = 0;
			}

			return VALUES[indexIn];
		}
	}

	public class HideGoal extends Goal {
		public HideGoal() {
			super();
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
		}

		@Override
		public boolean canUse() {
			return Snail.this.getHidingTime() > 0 || Snail.this.getLastHurtByMob() != null || this.shouldHideFromMob();
		}

		@Override
		public void start() {
			this.hide();
			Snail.this.getNavigation().stop();
			Snail.this.getMoveControl().setWantedPosition(Snail.this.getX(), Snail.this.getY(), Snail.this.getZ(), 0.0D);
			Snail.this.setAction(Action.HIDING);
		}

		@Override
		public void stop() {
			Snail.this.setAction(Action.DEFAULT);
		}

		@Override
		public void tick() {
			if ((Snail.this.getLastHurtByMob() != null || this.shouldHideFromMob()) && Snail.this.getHidingTime() < 120) {
				this.hide();
			} else {
				Snail.this.setHidingTime(Snail.this.getHidingTime() - 1);
			}
		}

		@Override
		public boolean canContinueToUse() {
			return Snail.this.getHidingTime() > 0;
		}

		private void hide() {
			Snail.this.setHidingTime(120 + Snail.this.random.nextInt(120));
		}

		private boolean shouldHideFromMob() {
			for (LivingEntity livingentity : Snail.this.level().getEntitiesOfClass(LivingEntity.class, Snail.this.getBoundingBox().inflate(0.5D), ENEMY_MATCHER)) {
				if (livingentity.isAlive() && livingentity.getBbHeight() > Snail.this.getBbHeight()) {
					return true;
				}
			}

			return false;
		}
	}

	class EatGoal extends Goal {
		private int eatTime;

		public EatGoal() {
			super();
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
		}

		@Override
		public boolean canUse() {
			return Snail.this.hasSnack();
		}

		@Override
		public void start() {
			this.eatTime = this.adjustedTickDelay(192);
			Snail.this.getNavigation().stop();
			Snail.this.getMoveControl().setWantedPosition(Snail.this.getX(), Snail.this.getY(), Snail.this.getZ(), 0.0D);
			Snail.this.setAction(Action.EATING);
		}

		@Override
		public void stop() {
			Snail.this.setAction(Action.DEFAULT);
		}

		@Override
		public void tick() {
			--this.eatTime;

			if (this.eatTime <= 0) {
				Snail.this.eatSnack();
			}
		}

		@Override
		public boolean canContinueToUse() {
			return Snail.this.hasSnack();
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

		@Override
		public boolean canUse() {
			if (Snail.this.getRandom().nextInt(20) != 0) {
				return false;
			} else {
				return !Snail.this.isBaby() && !Snail.this.hasSnack() && Snail.this.getGooAmount() <= 0 && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(Snail.this.level(), Snail.this) && this.canMoveToMushroom();
			}
		}

		@Override
		public boolean canContinueToUse() {
			return !Snail.this.getNavigation().isDone() && !Snail.this.hasSnack() && Snail.this.getGooAmount() <= 0;
		}

		@Override
		public void start() {
			Snail.this.getNavigation().moveTo(this.mushroomX, this.mushroomY, this.mushroomZ, 0.5D);
		}

		@Override
		public void tick() {
			if (!Snail.this.isBaby() && Snail.this.getGooAmount() <= 0) {
				BlockPos blockpos = Snail.this.blockPosition();

				if (this.isBlockMushroom(blockpos)) {
					if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(Snail.this.level(), Snail.this)) {
						Snail.this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Snail.this.level().getBlockState(blockpos).getBlock().asItem(), 1));
						Snail.this.level().destroyBlock(blockpos, false);
					}
				}
			}
		}

		@Nullable
		private Vec3 findMushroom() {
			RandomSource random = Snail.this.getRandom();
			BlockPos blockpos = BlockPos.containing(Snail.this.getX(), Snail.this.getBoundingBox().minY, Snail.this.getZ());

			for (int i = 0; i < 10; ++i) {
				BlockPos blockpos1 = blockpos.offset(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
				if (this.isBlockMushroom(blockpos1)) {
					return new Vec3(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
				}
			}

			return null;
		}

		private boolean canMoveToMushroom() {
			Vec3 vec3d = this.findMushroom();
			if (vec3d == null) {
				return false;
			} else {
				this.mushroomX = vec3d.x;
				this.mushroomY = vec3d.y;
				this.mushroomZ = vec3d.z;
				return true;
			}
		}

		private boolean isBlockMushroom(BlockPos pos) {
			return Snail.this.level().getBlockState(pos).is(AutumnityBlockTags.SNAIL_SNACKS);
		}
	}

	public class EatMooshroomMushroomsGoal extends Goal {
		private MushroomCow targetMooshroom;
		private int timeToRecalcPath;

		public EatMooshroomMushroomsGoal() {
			super();
		}

		@Override
		public boolean canUse() {
			if (!Snail.this.isBaby() && !Snail.this.hasSnack() && Snail.this.getGooAmount() <= 0) {
				List<MushroomCow> list = Snail.this.level().getEntitiesOfClass(MushroomCow.class, Snail.this.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
				MushroomCow mooshroom = null;
				double d0 = Double.MAX_VALUE;

				for (MushroomCow mooshroom1 : list) {
					if (mooshroom1.getAge() >= 0) {
						double d1 = Snail.this.distanceToSqr(mooshroom1);
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

		@Override
		public boolean canContinueToUse() {
			if (!this.targetMooshroom.isAlive()) {
				return false;
			} else if (Snail.this.hasSnack()) {
				return false;
			} else if (Snail.this.getGooAmount() > 0) {
				return false;
			} else {
				double d0 = this.targetMooshroom.distanceToSqr(Snail.this);
				return !(d0 > 256.0D);
			}
		}

		@Override
		public void start() {
			this.timeToRecalcPath = 0;
		}

		@Override
		public void stop() {
			this.targetMooshroom = null;
		}

		@Override
		public void tick() {
			if (--this.timeToRecalcPath <= 0) {
				this.timeToRecalcPath = this.adjustedTickDelay(10);
				Snail.this.getNavigation().moveTo(this.targetMooshroom, 0.5D);
			}

			if (this.targetMooshroom != null && this.targetMooshroom.isAlive()) {
				double d0 = this.targetMooshroom.distanceToSqr(Snail.this);
				if (d0 < 2.0D) {
					if (this.targetMooshroom.getVariant() == MushroomCow.MushroomType.BROWN) {
						Snail.this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BROWN_MUSHROOM, 1));
					} else {
						Snail.this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.RED_MUSHROOM, 1));
					}

					this.targetMooshroom.hurt(Snail.this.damageSources().mobAttack(Snail.this), 0.0F);
				}
			}
		}
	}
}