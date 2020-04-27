package com.markus1002.autumnity.common.entity.monster;

import java.util.Collection;

import com.markus1002.autumnity.core.registry.ModEffects;
import com.markus1002.autumnity.core.registry.ModEntities;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpatterEntity extends CreeperEntity
{
	public SpatterEntity(EntityType<? extends CreeperEntity> type, World worldIn)
	{
		super(type, worldIn);
	}

	public SpatterEntity(FMLPlayMessages.SpawnEntity packet, World worldIn)
	{
		super(ModEntities.SPATTER, worldIn);
	}

	protected void explode()
	{
		if (!this.world.isRemote)
		{
			Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
			float f = this.func_225509_J__() ? 1.6F : 0.8F;
			this.dead = true;
			this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), (float)this.explosionRadius * f, explosion$mode);
			this.remove();
			this.spawnLingeringCloud();
		}
	}

	protected void spawnLingeringCloud()
	{
		AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
		areaeffectcloudentity.setRadius(2.5F);
		areaeffectcloudentity.setRadiusOnUse(-0.5F);
		areaeffectcloudentity.setWaitTime(10);
		areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
		areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());

		boolean flag = false;
		Collection<EffectInstance> collection = this.getActivePotionEffects();
		if (!collection.isEmpty())
		{
			for(EffectInstance effectinstance : collection)
			{
				if (effectinstance.getPotion() != ModEffects.ANTI_HEALING)
				{
					areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
				}
				else if (effectinstance.getDuration() > 1200)
				{
					flag = true;
					areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
				}
			}
		}

		if (!flag)
		{
			areaeffectcloudentity.addEffect(new EffectInstance(new EffectInstance(ModEffects.ANTI_HEALING, 1200)));
		}

		this.world.addEntity(areaeffectcloudentity);
	}

	public boolean isPotionApplicable(EffectInstance potioneffectIn)
	{
		return potioneffectIn.getPotion() == ModEffects.ANTI_HEALING ? false : super.isPotionApplicable(potioneffectIn);
	}

	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}