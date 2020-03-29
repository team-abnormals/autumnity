package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.world.biome.MapleForestBiome;
import com.markus1002.autumnity.common.world.biome.MapleForestHillsBiome;
import com.markus1002.autumnity.common.world.biome.PumpkinFieldsBiome;
import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomes
{
	public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, Reference.MOD_ID);

	public static RegistryObject<Biome> MAPLE_FOREST = BIOMES.register("maple_forest", () -> new MapleForestBiome());
	public static RegistryObject<Biome> MAPLE_FOREST_HILLS = BIOMES.register("maple_forest_hills", () -> new MapleForestHillsBiome());
	public static RegistryObject<Biome> PUMPKIN_FIELDS = BIOMES.register("pumpkin_fields", () -> new PumpkinFieldsBiome());

    public static void setupBiomes()
    {
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(MAPLE_FOREST.get(), 6));
        BiomeDictionary.addTypes(MAPLE_FOREST.get(), Type.FOREST, Type.OVERWORLD);
        
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(MAPLE_FOREST_HILLS.get(), 4));
        BiomeDictionary.addTypes(MAPLE_FOREST_HILLS.get(), Type.FOREST, Type.OVERWORLD, Type.HILLS, Type.RARE);
        
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(PUMPKIN_FIELDS.get(), 1));
        BiomeDictionary.addTypes(PUMPKIN_FIELDS.get(), Type.FOREST, Type.OVERWORLD, Type.SPARSE, Type.RARE);
    }
}