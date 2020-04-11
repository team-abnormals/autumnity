package com.markus1002.autumnity.core;

import com.markus1002.autumnity.core.registry.ModBiomes;
import com.markus1002.autumnity.core.registry.ModBlocks;
import com.markus1002.autumnity.core.registry.ModEntities;
import com.markus1002.autumnity.core.registry.ModFeatures;
import com.markus1002.autumnity.core.registry.ModItems;
import com.markus1002.autumnity.core.registry.ModParticles;
import com.markus1002.autumnity.core.registry.ModPotions;
import com.markus1002.autumnity.core.util.EventHandler;
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
	 * - Berries that make undead mobs not attack you (foul berries?) (foulness effect)
	 * - Nautilus snail
	 * - Snake snail
	 * - Quark feeding thing
	 */
	
	public Autumnity()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::particleSetup);
        
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBiomes.BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
		
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event)
	{
		VanillaCompatibility.setupVanillaCompatibility();
		ModPotions.setupBrewingRecipes();
        ModBiomes.setupBiomes();
		
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