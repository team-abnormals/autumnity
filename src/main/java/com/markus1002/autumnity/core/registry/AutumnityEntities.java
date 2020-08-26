package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.client.renderer.entity.SnailRenderer;
import com.markus1002.autumnity.common.entity.passive.SnailEntity;
import com.markus1002.autumnity.core.Autumnity;
import com.markus1002.autumnity.core.Config;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityEntities
{
	public static final RegistryHelper HELPER = Autumnity.REGISTRY_HELPER;

	public static final RegistryObject<EntityType<SnailEntity>> SNAIL = HELPER.createLivingEntity("snail", SnailEntity::new, EntityClassification.CREATURE, 0.8F, 0.9F);

	public static void setupEntitySpawns(Biome biome)
	{
		if (biome == AutumnityBiomes.MAPLE_FOREST.get() || biome == AutumnityBiomes.MAPLE_FOREST_HILLS.get() || biome == AutumnityBiomes.PUMPKIN_FIELDS.get())
		{
			addEntitySpawn(biome, EntityClassification.CREATURE, new Biome.SpawnListEntry(AutumnityEntities.SNAIL.get(), 16, 2, 2));
		}
		
		if (Config.COMMON.snailSpawnBiomes.get().contains(biome.getRegistryName().toString()))
		{
			addEntitySpawn(biome, EntityClassification.CREATURE, new Biome.SpawnListEntry(AutumnityEntities.SNAIL.get(), 10, 2, 2));
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
	
    public static void registerAttributes()
    {
    	GlobalEntityTypeAttributes.put(SNAIL.get(), SnailEntity.registerAttributes().create());
    }
}