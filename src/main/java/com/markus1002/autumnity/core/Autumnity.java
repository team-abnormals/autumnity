package com.markus1002.autumnity.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.markus1002.autumnity.core.registry.ModEntities;
import com.markus1002.autumnity.core.registry.ModFeatures;
import com.markus1002.autumnity.core.registry.ModParticles;
import com.markus1002.autumnity.core.registry.ModPotions;
import com.markus1002.autumnity.core.util.EventHandler;
import com.markus1002.autumnity.core.util.Reference;
import com.markus1002.autumnity.core.util.VanillaCompatibility;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod("autumnity")
public class Autumnity
{
	/*
	 * TODO:
	 * - Vanilla compatibility
	 * - Orange mushrooms
	 * - Pancakes
	 * - Flower pots
	 * - Ladder breaking speed with axes
	 * - Maple forest variants
	 * - Sturdiness effect?
	 * - Register stuff when other mod not installed?
	 * 
	 * Maple Stuff:
	 * 
	 * - Maple Sign
	 *
	 * - Maple Chest
	 * - Maple Crate?
	 * 
	 * - Crafting Recipes
	 */
	
	private static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

	public Autumnity()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::particleSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
		
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event)
	{
		VanillaCompatibility.setupVanillaCompatibility();
		ModPotions.setupBrewingRecipes();
		
		for(Biome biome : ForgeRegistries.BIOMES.getValues())
		{
			ModFeatures.setupBiomeFeatures(biome);
			ModEntities.setupEntitySpawns(biome);
		}
	}

	private void clientSetup(final FMLClientSetupEvent event)
	{
		ModEntities.setupEntitiesClient();
		VanillaCompatibility.setupVanillaCompatibilityClient();
	}
	
	private void particleSetup(ParticleFactoryRegisterEvent event)
	{
        ModParticles.registerFactories();
	}
}