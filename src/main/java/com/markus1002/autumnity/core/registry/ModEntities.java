package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.client.renderer.entity.SnailRenderer;
import com.markus1002.autumnity.common.entity.passive.SnailEntity;
import com.markus1002.autumnity.core.Autumnity;
import com.markus1002.autumnity.core.Config;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities
{
	public static final RegistryHelper HELPER = Autumnity.REGISTRY_HELPER;

	public static final RegistryObject<EntityType<SnailEntity>> SNAIL = HELPER.createLivingEntity("snail", SnailEntity::new, EntityClassification.CREATURE, 0.8F, 0.9F);

	public static void setupEntitySpawns(Biome biome)
	{
		if (biome == ModBiomes.MAPLE_FOREST.get())
		{
			addEntitySpawn(biome, EntityClassification.CREATURE, new Biome.SpawnListEntry(ModEntities.SNAIL.get(), 8, 1, 2));
		}
		else if (biome == ModBiomes.PUMPKIN_FIELDS.get())
		{
			addEntitySpawn(biome, EntityClassification.CREATURE, new Biome.SpawnListEntry(ModEntities.SNAIL.get(), 12, 1, 2));
		}

		if (Config.COMMON.snailSpawnBiomes.get().contains(biome.getRegistryName().toString()))
		{
			addEntitySpawn(biome, EntityClassification.CREATURE, new Biome.SpawnListEntry(ModEntities.SNAIL.get(), 8, 1, 2));
		}
	}

	private static void addEntitySpawn(Biome biome, EntityClassification entityclassification, Biome.SpawnListEntry spawnlistentry)
	{
		biome.getSpawns(entityclassification).add(spawnlistentry);
	}

	@OnlyIn(Dist.CLIENT)
	public static void setupEntitiesClient()
	{
		RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends SnailEntity>)SNAIL.get(), SnailRenderer::new);
	}
}