package com.minecraftabnormals.autumnity.common.entity.projectile;

import com.minecraftabnormals.autumnity.common.entity.passive.TurkeyEntity;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEntities;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class TurkeyEggEntity extends ProjectileItemEntity {
	public TurkeyEggEntity(EntityType<? extends TurkeyEggEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public TurkeyEggEntity(World worldIn, LivingEntity throwerIn) {
		super(AutumnityEntities.TURKEY_EGG.get(), throwerIn, worldIn);
	}

	public TurkeyEggEntity(World worldIn, double x, double y, double z) {
		super(AutumnityEntities.TURKEY_EGG.get(), x, y, z, worldIn);
	}

	public TurkeyEggEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
		this(AutumnityEntities.TURKEY_EGG.get(), world);
	}

	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
			}
		}
	}

	@Override
	protected void onHitEntity(EntityRayTraceResult result) {
		super.onHitEntity(result);
		result.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
	}

	@Override
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		if (!this.level.isClientSide) {
			if (this.random.nextInt(8) == 0) {
				int i = 1;
				if (this.random.nextInt(32) == 0) {
					i = 4;
				}

				for (int j = 0; j < i; ++j) {
					TurkeyEntity turkeyentity = AutumnityEntities.TURKEY.get().create(this.level);
					turkeyentity.setAge(-24000);
					turkeyentity.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
					this.level.addFreshEntity(turkeyentity);
				}
			}
			this.level.broadcastEntityEvent(this, (byte) 3);
			this.remove();
		}
	}

	@Override
	protected Item getDefaultItem() {
		return AutumnityItems.TURKEY_EGG.get();
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(AutumnityItems.TURKEY_EGG.get());
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}