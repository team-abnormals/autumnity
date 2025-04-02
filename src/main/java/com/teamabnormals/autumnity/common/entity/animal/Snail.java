package com.teamabnormals.autumnity.common.entity.animal;

import com.teamabnormals.autumnity.common.entity.ai.goal.SnailEatGoal;
import com.teamabnormals.autumnity.common.entity.ai.goal.SnailEatMooshroomMushroomsGoal;
import com.teamabnormals.autumnity.common.entity.ai.goal.SnailEatMushroomsGoal;
import com.teamabnormals.autumnity.common.entity.ai.goal.SnailHideGoal;
import com.teamabnormals.autumnity.core.Autumnity;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class Snail extends Animal {
	private static final AttributeModifier HIDING_ARMOR_BONUS_MODIFIER = new AttributeModifier(Autumnity.location("hiding_armor_bonus"), 20.0D, Operation.ADD_VALUE);
	private static final EntityDataAccessor<Integer> GOO_AMOUNT = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> ACTION = SynchedEntityData.defineId(Snail.class, EntityDataSerializers.BYTE);
	private int hidingTime = 0;

	private float hideAmount;
	private float hideAmountO;

	private int shakeAmount;
	private int shakeAmountO;

	// This exists so snails would work with Quark feeding troughs
	private boolean canBreed = true;

	public Snail(EntityType<? extends Snail> type, Level worldIn) {
		super(type, worldIn);
		this.setPathfindingMalus(PathType.DANGER_OTHER, 0.0F);
		this.setPathfindingMalus(PathType.DAMAGE_OTHER, 0.0F);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SnailHideGoal(this));
		this.goalSelector.addGoal(1, new SnailEatGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 0.5D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 0.5D, Ingredient.of(AutumnityItemTags.SNAIL_TEMPT_ITEMS), false));
		this.goalSelector.addGoal(4, new SnailEatMushroomsGoal(this));
		this.goalSelector.addGoal(5, new SnailEatMooshroomMushroomsGoal(this));
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
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(GOO_AMOUNT, 0);
		builder.define(ACTION, (byte) 0);
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

			if (this.getGooAmount() > 0 && EventHooks.canEntityGrief(this.level(), this)) {
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
			ItemStack stack = player.getItemInHand(hand);
			if (!stack.isEmpty() && !this.hasSnack()) {
				if (this.isSnack(stack)) {
					if (!this.isBaby() && this.getGooAmount() <= 0) {
						if (!this.level().isClientSide) {
							ItemStack itemstack1 = stack.copy();
							itemstack1.setCount(1);
							this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
							this.usePlayerItem(player, hand, stack);
						}
						return InteractionResult.sidedSuccess(this.level().isClientSide());
					}
				} else if (this.isSnailBreedingItem(stack)) {
					boolean flag = false;

					if (!this.level().isClientSide && this.getAge() == 0 && this.canFallInLove()) {
						this.setInLove(player);
						flag = true;
					} else if (this.isBaby()) {
						this.ageUp((int) ((float) (-this.getAge() / 20) * 0.1F), true);
						flag = true;
					}

					if (flag) {
						FoodProperties properties = stack.getFoodProperties(this);
						if (properties != null) {
							stack.consume(1, player);
							Optional<ItemStack> optional = properties.usingConvertsTo();
							if (optional.isPresent() && !this.hasInfiniteMaterials()) {
								if (stack.isEmpty()) {
									player.setItemInHand(hand, optional.get().copy());
								} else if (!this.level().isClientSide()) {
									ItemStack container = optional.get().copy();
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

	public void spitOutItem() {
		ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
		if (!itemstack.isEmpty() && !this.level().isClientSide) {
			ItemEntity itementity = new ItemEntity(this.level(), this.getX() + this.getLookAngle().x, this.getY() + this.getEyeHeight(), this.getZ() + this.getLookAngle().z, itemstack);
			itementity.setPickUpDelay(40);
			itementity.setThrower(this);
			this.level().addFreshEntity(itementity);
			this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		}
	}

	public void eatSnack() {
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

	public boolean hasSnack() {
		return this.isSnack(this.getMainHandItem());
	}

	public int getGooAmount() {
		return this.entityData.get(GOO_AMOUNT);
	}

	public void setGooAmount(int amount) {
		this.entityData.set(GOO_AMOUNT, amount);
	}

	public int getHidingTime() {
		return this.hidingTime;
	}

	public void setHidingTime(int hidingTimeIn) {
		this.hidingTime = hidingTimeIn;
	}

	public Action getAction() {
		return Action.byId(this.entityData.get(ACTION));
	}

	public void setAction(Action action) {
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
}