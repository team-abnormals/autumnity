package com.minecraftabnormals.autumnity.common.entity.passive;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;

import com.minecraftabnormals.autumnity.core.other.AutumnityTags;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEntities;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import com.minecraftabnormals.environmental.api.IEggLayingEntity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.IMob;
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
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class TurkeyEntity extends AnimalEntity implements IEggLayingEntity
{
	private static final DataParameter<Boolean> IS_INTIMIDATING = EntityDataManager.createKey(TurkeyEntity.class, DataSerializers.BOOLEAN);

	private float wingRotation;
	private float destPos;
	private float oFlapSpeed;
	private float oFlap;
	private float wingRotDelta = 1.0F;

	private float peckTicks;
	private float prevPeckTicks;

	private float intimidationTicks;
	private float prevIntimidationTicks;
	public int timeUntilNextEgg = this.rand.nextInt(9600) + 9600;
	public boolean turkeyJockey;

	private static final Predicate<LivingEntity> ENEMY_MATCHER = (livingentity) -> {
		return livingentity != null && livingentity instanceof IMob && !livingentity.isPotionActive(Effects.WEAKNESS);
	};

	public TurkeyEntity(EntityType<? extends AnimalEntity> type, World worldIn)
	{
		super(type, worldIn);
		this.setPathPriority(PathNodeType.WATER, 0.0F);
	}

	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new TurkeyEntity.PanicGoal());
		this.goalSelector.addGoal(2, new TurkeyEntity.ThreatenGoal());
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.3F));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.4D, false));
		this.goalSelector.addGoal(5, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, false, Ingredient.fromTag(AutumnityTags.TURKEY_BREEDING_ITEMS)));
		this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new TurkeyEntity.HurtByTargetGoal());
	}

	@Override
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(IS_INTIMIDATING, false);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn)
	{
		return this.isChild() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes()
	{
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 12.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 4)
		{
			this.peckTicks = 8;
		}
		else
		{
			super.handleStatusUpdate(id);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn)
	{
		this.peckTicks = 8;
		this.world.setEntityState(this, (byte)4);

		return super.attackEntityAsMob(entityIn);
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		if (this.world.isRemote)
		{
			// Intimidation animation
			this.prevIntimidationTicks = this.intimidationTicks;
			if (this.isIntimidating())
			{
				this.intimidationTicks = MathHelper.clamp(this.intimidationTicks + 1.0F, 0.0F, 8.0F);
			}
			else
			{
				this.intimidationTicks = MathHelper.clamp(this.intimidationTicks - 1.0F, 0.0F, 8.0F);
			}

			// Wing rotation
			this.oFlap = this.wingRotation;
			this.oFlapSpeed = this.destPos;
			this.destPos = (float)((double)this.destPos + (double)(this.onGround && !this.isIntimidating() ? -1 : 4) * 0.3D);
			this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
			if ((!this.onGround || this.isIntimidating()) && this.wingRotDelta < 1.0F)
			{
				this.wingRotDelta = 1.0F;
			}
			this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);
			this.wingRotation += this.wingRotDelta * 2.0F;

			// Peck progress
			this.prevPeckTicks = this.peckTicks;

			if (this.peckTicks > 0)
			{
				this.peckTicks--;
			}
		}

		// Motion
		Vector3d vector3d = this.getMotion();
		if (!this.onGround && vector3d.y < 0.0D)
		{
			this.setMotion(vector3d.mul(1.0D, 0.6D, 1.0D));
		}

		if (!this.world.isRemote)
		{
			if (this.isAlive() && !this.isChild() && !this.isTurkeyJockey() && --this.timeUntilNextEgg <= 0)
			{
				this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				this.entityDropItem(AutumnityItems.TURKEY_EGG.get());
				this.timeUntilNextEgg = this.getNextEggTime(this.rand);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public float getWingRotation(float partialTicks)
	{
		float f = MathHelper.lerp(partialTicks, this.oFlap, this.wingRotation);
		float f1 = MathHelper.lerp(partialTicks, this.oFlapSpeed, this.destPos);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}

	@OnlyIn(Dist.CLIENT)
	public float getPeckProgress(float partialTicks)
	{
		float f = MathHelper.lerp(partialTicks, this.prevPeckTicks, this.peckTicks) / 8.0F;

		if (f < 0.5F)
		{
			return 2.0F * f;
		}
		else
		{
			return -2.0F * f + 2.0F;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public float getIntimidationAnimationScale(float partialTicks)
	{
		return MathHelper.lerp(partialTicks, this.prevIntimidationTicks, this.intimidationTicks) / 8.0F;
	}

	public void setIntimidating(boolean intimidating)
	{
		this.dataManager.set(IS_INTIMIDATING, intimidating);
	}

	public boolean isIntimidating()
	{
		return this.dataManager.get(IS_INTIMIDATING);
	}

	@Override
	public boolean onLivingFall(float distance, float damageMultiplier)
	{
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_CHICKEN_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_CHICKEN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_CHICKEN_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn)
	{
		this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack)
	{
		return Ingredient.fromTag(AutumnityTags.TURKEY_BREEDING_ITEMS).test(stack);
	}

	@Override
	protected int getExperiencePoints(PlayerEntity player)
	{
		return this.isTurkeyJockey() ? 10 : super.getExperiencePoints(player);
	}

	@Override
	public void readAdditional(CompoundNBT compound)
	{
		super.readAdditional(compound);
		this.turkeyJockey = compound.getBoolean("IsTurkeyJockey");
		if (compound.contains("EggLayTime"))
		{
			this.timeUntilNextEgg = compound.getInt("EggLayTime");
		}

	}

	@Override
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		compound.putBoolean("IsTurkeyJockey", this.turkeyJockey);
		compound.putInt("EggLayTime", this.timeUntilNextEgg);
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer)
	{
		return this.isTurkeyJockey();
	}

	@Override
	public void updatePassenger(Entity passenger)
	{
		super.updatePassenger(passenger);
		float f = MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180F));
		float f1 = MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180F));
		passenger.setPosition(this.getPosX() + (double)(0.1F * f), this.getPosYHeight(0.5D) + passenger.getYOffset() + 0.0D, this.getPosZ() - (double)(0.1F * f1));
		if (passenger instanceof LivingEntity)
		{
			((LivingEntity)passenger).renderYawOffset = this.renderYawOffset;
		}
	}

	public boolean isTurkeyJockey()
	{
		return this.turkeyJockey;
	}

	public void setTurkeyJockey(boolean jockey)
	{
		this.turkeyJockey = jockey;
	}

	@Override
	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public TurkeyEntity createChild(AgeableEntity ageable) {
		return AutumnityEntities.TURKEY.get().create(this.world);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target)
	{
		return new ItemStack(AutumnityItems.TURKEY_SPAWN_EGG.get());
	}

	@Override
	public int getEggTimer()
	{
		return this.timeUntilNextEgg;
	}

	@Override
	public void setEggTimer(int time)
	{
		this.timeUntilNextEgg = time;
	}

	@Override
	public boolean isBirdJockey()
	{
		return this.turkeyJockey;
	}
	
	@Override
	public Item getEggItem()
	{
		return AutumnityItems.TURKEY_EGG.get();
	}
	
	@Override
	public int getNextEggTime(Random rand)
	{
		return rand.nextInt(9600) + 9600;
	}
	
	class PanicGoal extends net.minecraft.entity.ai.goal.PanicGoal
	{
		public PanicGoal()
		{
			super(TurkeyEntity.this, 1.4D);
		}

		public boolean shouldExecute()
		{
			return !TurkeyEntity.this.isChild() ? false : super.shouldExecute();
		}
	}

	class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal
	{
		public HurtByTargetGoal()
		{
			super(TurkeyEntity.this);
		}

		protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn)
		{
			if (mobIn instanceof TurkeyEntity && !mobIn.isChild())
			{
				super.setAttackTarget(mobIn, targetIn);
			}
		}

		public void startExecuting()
		{
			super.startExecuting();

			if (TurkeyEntity.this.isChild())
			{
				this.alertOthers();
				this.resetTask();
			}
		}
	}

	class ThreatenGoal extends Goal
	{
		private LivingEntity target;
		private int threateningTicks;

		public ThreatenGoal()
		{
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean shouldExecute()
		{
			if (!TurkeyEntity.this.isChild() && (TurkeyEntity.this.isOnGround() || TurkeyEntity.this.isInWater()) && !TurkeyEntity.this.isIntimidating())
			{
				LivingEntity livingentity = TurkeyEntity.this.getAttackTarget();

				if (TurkeyEntity.this.getRNG().nextInt(20) == 0 && livingentity != null && livingentity.isAlive() && !livingentity.isPotionActive(Effects.WEAKNESS) && TurkeyEntity.this.canEntityBeSeen(livingentity))
				{
					this.target = livingentity;
					return true;
				}

				LivingEntity newtarget = null;
				double d0 = -1.0D;

				for(LivingEntity livingentity1 : TurkeyEntity.this.world.getEntitiesWithinAABB(LivingEntity.class, TurkeyEntity.this.getBoundingBox().grow(6.0D), ENEMY_MATCHER))
				{
					double d1 = livingentity1.getDistanceSq(TurkeyEntity.this);
					if (d0 == -1.0D || d1 < d0)
					{
						d0 = d1;
						newtarget = livingentity1;
					}
				}

				if (TurkeyEntity.this.getRNG().nextInt(20) == 0 && newtarget != null && TurkeyEntity.this.canEntityBeSeen(newtarget))
				{
					this.target = newtarget;
					return true;
				}
			}

			return false;
		}

		public void tick()
		{
			if (this.target != null)
			{
				TurkeyEntity.this.getLookController().setLookPositionWithEntity(this.target, (float)TurkeyEntity.this.getHorizontalFaceSpeed(), (float)TurkeyEntity.this.getVerticalFaceSpeed());
			}

			--this.threateningTicks;
			if (this.threateningTicks == 0)
			{
				this.resetTask();
			}
		}

		public boolean shouldContinueExecuting()
		{
			return (TurkeyEntity.this.isOnGround() || TurkeyEntity.this.isInWater()) && this.target != null && this.target.isAlive() && TurkeyEntity.this.canEntityBeSeen(this.target);
		}

		public void startExecuting()
		{
			super.startExecuting();
			TurkeyEntity.this.navigator.clearPath();
			TurkeyEntity.this.setIntimidating(true);
			this.threateningTicks = 50;
		}

		public void resetTask()
		{
			if (this.target != null)
			{
				this.target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 120));
				this.target = null;
			}
			TurkeyEntity.this.setIntimidating(false);
			super.resetTask();
		}
	}
}
