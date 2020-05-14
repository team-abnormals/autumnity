package com.markus1002.autumnity.core;

import com.markus1002.autumnity.core.registry.ModBiomes;
import com.markus1002.autumnity.core.registry.ModEntities;
import com.markus1002.autumnity.core.registry.ModFeatures;
import com.markus1002.autumnity.core.registry.ModPaintings;
import com.markus1002.autumnity.core.registry.ModParticles;
import com.markus1002.autumnity.core.registry.ModPotions;
import com.markus1002.autumnity.core.util.EventHandler;
import com.markus1002.autumnity.core.util.Reference;
import com.markus1002.autumnity.core.util.VanillaCompatibility;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
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
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(Reference.MOD_ID);
	
	public Autumnity()
	{
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::particleSetup);
        
        REGISTRY_HELPER.getDeferredBlockRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredItemRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredEntityRegister().register(modEventBus);
        ModBiomes.BIOMES.register(modEventBus);
        ModPaintings.PAINTINGS.register(modEventBus);
        
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