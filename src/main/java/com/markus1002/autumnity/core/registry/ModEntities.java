package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.client.renderer.entity.ModBoatRenderer;
import com.markus1002.autumnity.client.renderer.entity.SnailRenderer;
import com.markus1002.autumnity.common.entity.item.boat.ModBoatEntity;
import com.markus1002.autumnity.common.entity.passive.SnailEntity;
import com.markus1002.autumnity.core.Config;
import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities
{
	public static final EntityType<ModBoatEntity> BOAT = EntityType.Builder.<ModBoatEntity>create(ModBoatEntity::new, EntityClassification.MISC)
			.size(1.375F, 0.5625F)
			.setTrackingRange(80)
			.setShouldReceiveVelocityUpdates(true)
			.setUpdateInterval(3)
			.build(Reference.location("boat").toString());

	public static final EntityType<SnailEntity> SNAIL = EntityType.Builder.<SnailEntity>create(SnailEntity::new, EntityClassification.CREATURE)
			.size(0.8F, 0.9F)
			.setTrackingRange(64)
			.setShouldReceiveVelocityUpdates(true)
			.setUpdateInterval(3)
			.build(Reference.location("snail").toString());

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> event)
	{
		registerEntity(BOAT, "boat");
		registerEntity(SNAIL, "snail");
	}
	
	public static void setupEntitySpawns(Biome biome)
	{
		if (Config.COMMON.snailSpawnBiomes.get().contains(biome.getRegistryName().toString()))
		{
			addEntitySpawn(biome, EntityClassification.CREATURE, new Biome.SpawnListEntry(ModEntities.SNAIL, 8, 1, 2));
		}
	}

	private static void addEntitySpawn(Biome biome, EntityClassification entityclassification, Biome.SpawnListEntry spawnlistentry)
	{
		biome.getSpawns(entityclassification).add(spawnlistentry);
	}

	@OnlyIn(Dist.CLIENT)
	public static void setupEntitiesClient()
	{
        RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends ModBoatEntity>)BOAT, ModBoatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends SnailEntity>)SNAIL, SnailRenderer::new);
	}

	private static void registerEntity(EntityType<?> entity, String name)
	{
		entity.setRegistryName(Reference.location(name));
		ForgeRegistries.ENTITIES.register(entity);
	}
}