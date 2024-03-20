package com.teamabnormals.autumnity.common.entity.projectile;

import com.teamabnormals.autumnity.common.entity.animal.Turkey;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class ThrownTurkeyEgg extends ThrowableItemProjectile {

	public ThrownTurkeyEgg(EntityType<? extends ThrownTurkeyEgg> type, Level worldIn) {
		super(type, worldIn);
	}

	public ThrownTurkeyEgg(Level worldIn, LivingEntity throwerIn) {
		super(AutumnityEntityTypes.TURKEY_EGG.get(), throwerIn, worldIn);
	}

	public ThrownTurkeyEgg(Level worldIn, double x, double y, double z) {
		super(AutumnityEntityTypes.TURKEY_EGG.get(), x, y, z, worldIn);
	}

	public ThrownTurkeyEgg(PlayMessages.SpawnEntity spawnEntity, Level world) {
		this(AutumnityEntityTypes.TURKEY_EGG.get(), world);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D, ((double) this.random.nextFloat() - 0.5D) * 0.08D);
			}
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level().isClientSide) {
			if (this.random.nextInt(8) == 0) {
				int i = 1;
				if (this.random.nextInt(32) == 0) {
					i = 4;
				}

				for (int j = 0; j < i; ++j) {
					Turkey turkeyentity = AutumnityEntityTypes.TURKEY.get().create(this.level());
					turkeyentity.setAge(-24000);
					turkeyentity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
					this.level().addFreshEntity(turkeyentity);
				}
			}
			this.level().broadcastEntityEvent(this, (byte) 3);
			this.discard();
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
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}