package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.autumnity.common.world.biome.MapleForestBiome;
import com.minecraftabnormals.autumnity.common.world.biome.MapleForestHillsBiome;
import com.minecraftabnormals.autumnity.common.world.biome.PumpkinFieldsBiome;
import com.minecraftabnormals.autumnity.core.Config;
import com.minecraftabnormals.autumnity.core.Reference;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityBiomes
{
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Reference.MOD_ID);

	public static final RegistryObject<Biome> MAPLE_FOREST = BIOMES.register("maple_forest", MapleForestBiome::new);
	public static final RegistryObject<Biome> MAPLE_FOREST_HILLS = BIOMES.register("maple_forest_hills", MapleForestHillsBiome::new);
	public static final RegistryObject<Biome> PUMPKIN_FIELDS = BIOMES.register("pumpkin_fields", PumpkinFieldsBiome::new);

    public static void registerBiomes()
    {
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(MAPLE_FOREST.get(), Config.COMMON.mapleForestWeight.get()));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(PUMPKIN_FIELDS.get(), Config.COMMON.pumpkinFieldsWeight.get()));
        
        BiomeDictionary.addTypes(MAPLE_FOREST.get(), Type.FOREST, Type.OVERWORLD);
        BiomeDictionary.addTypes(MAPLE_FOREST_HILLS.get(), Type.FOREST, Type.OVERWORLD, Type.HILLS, Type.RARE);
        BiomeDictionary.addTypes(PUMPKIN_FIELDS.get(), Type.PLAINS, Type.OVERWORLD, Type.SPARSE, Type.RARE);
    }
}