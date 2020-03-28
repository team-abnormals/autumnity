package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.world.biome.MapleForestBiome;
import com.markus1002.autumnity.common.world.biome.MapleForestHillsBiome;
import com.markus1002.autumnity.common.world.biome.PumpkinFieldsBiome;
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
	public static Biome MAPLE_FOREST;
	public static Biome MAPLE_FOREST_HILLS;
	public static Biome PUMPKIN_FIELDS;

	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event)
	{
		MAPLE_FOREST = registerBiome(new MapleForestBiome(), "maple_forest", 6, true, BiomeType.COOL, Type.FOREST, Type.OVERWORLD);
		MAPLE_FOREST_HILLS = registerBiome(new MapleForestHillsBiome(), "maple_forest_hills", 4, true, BiomeType.COOL, Type.FOREST, Type.OVERWORLD, Type.HILLS, Type.RARE);
		PUMPKIN_FIELDS = registerBiome(new PumpkinFieldsBiome(), "pumpkin_fields", 6, true, BiomeType.COOL, Type.PLAINS, Type.OVERWORLD, Type.SPARSE, Type.RARE);
	}

	private static Biome registerBiome(Biome biome, String name, int weight, boolean canGenerate, BiomeType biomeType, Type... types)
	{
		biome.setRegistryName(Reference.location(name));
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		if (canGenerate)
		{
			BiomeManager.addBiome(biomeType, new BiomeEntry(biome, weight));
		}
		BiomeManager.addSpawnBiome(biome);
		return biome;
	}
}