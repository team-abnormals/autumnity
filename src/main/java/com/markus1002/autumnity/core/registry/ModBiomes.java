package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.world.biome.MapleForestBiome;
import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomes
{
	public static final Biome MAPLE_FOREST = new MapleForestBiome();

	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event)
	{
		addBiome(MAPLE_FOREST, "maple_forest", 8, true, BiomeType.COOL, Type.FOREST, Type.OVERWORLD);
	}

	private static void addBiome(Biome biome, String name, int weight, boolean canGenerate, BiomeType biomeType, Type... types)
	{
		biome.setRegistryName(Reference.location(name));
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		if (canGenerate)
		{
			BiomeManager.addBiome(biomeType, new BiomeEntry(biome, weight));
		}
		BiomeManager.addSpawnBiome(biome);
	}
}