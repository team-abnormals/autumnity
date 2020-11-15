package com.minecraftabnormals.autumnity.core;

import com.minecraftabnormals.autumnity.core.other.AutumnityClient;
import com.minecraftabnormals.autumnity.core.other.AutumnityCompat;
import com.minecraftabnormals.autumnity.core.other.AutumnityEvents;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBanners;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBiomes;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEffects;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEntities;
import com.minecraftabnormals.autumnity.core.registry.AutumnityFeatures;
import com.minecraftabnormals.autumnity.core.registry.AutumnityPaintings;
import com.minecraftabnormals.autumnity.core.registry.AutumnityParticles;
import com.minecraftabnormals.autumnity.core.registry.AutumnityPotions;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
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
	public static final RegistryHelper REGISTRY_REPLACER = new RegistryHelper("minecraft");
	
	public Autumnity()
	{
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			modEventBus.addListener(EventPriority.LOWEST, this::registerItemColors);
			modEventBus.addListener(EventPriority.LOWEST, this::clientSetup);

		});
		
		modEventBus.addListener(EventPriority.LOWEST, this::commonSetup);
        
        REGISTRY_HELPER.getDeferredBlockRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredItemRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredEntityRegister().register(modEventBus);
        REGISTRY_HELPER.getDeferredSoundRegister().register(modEventBus);
		REGISTRY_REPLACER.getDeferredItemRegister().register(modEventBus);
        AutumnityBiomes.BIOMES.register(modEventBus);
        AutumnityPaintings.PAINTINGS.register(modEventBus);
        AutumnityEffects.EFFECTS.register(modEventBus);
        AutumnityPotions.POTIONS.register(modEventBus);
        AutumnityFeatures.FEATURES.register(modEventBus);
        AutumnityParticles.PARTICLES.register(modEventBus);
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
		
		MinecraftForge.EVENT_BUS.register(new AutumnityEvents());
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event)
	{
		DeferredWorkQueue.runLater(() -> 
		{
			AutumnityCompat.registerCompostables();
			AutumnityCompat.registerFlammables();
			AutumnityCompat.registerDispenserBehaviors();
			
			AutumnityBanners.registerBanners();
			AutumnityPotions.registerBrewingRecipes();
			AutumnityBiomes.registerBiomes();

			for(Biome biome : ForgeRegistries.BIOMES.getValues())
			{
				AutumnityFeatures.setupBiomeFeatures(biome);
				AutumnityEntities.setupEntitySpawns(biome);
			}
			AutumnityEntities.registerAttributes();
		});
	}

	private void clientSetup(final FMLClientSetupEvent event)
	{
		DeferredWorkQueue.runLater(() -> 
		{
			AutumnityEntities.setupEntitiesClient();
			
			AutumnityClient.setRenderLayers();
			AutumnityClient.registerBlockColors();
		});
	}
	

	@OnlyIn(Dist.CLIENT)
	private void registerItemColors(ColorHandlerEvent.Item event)
	{
		REGISTRY_HELPER.processSpawnEggColors(event);
	}
}