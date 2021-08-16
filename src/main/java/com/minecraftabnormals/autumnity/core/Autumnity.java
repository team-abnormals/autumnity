package com.minecraftabnormals.autumnity.core;

import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.minecraftabnormals.abnormals_core.core.util.registry.RegistryHelper;
import com.minecraftabnormals.autumnity.core.other.AutumnityClient;
import com.minecraftabnormals.autumnity.core.other.AutumnityCompat;
import com.minecraftabnormals.autumnity.core.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Autumnity.MOD_ID)
public class Autumnity {
	public static final String MOD_ID = "autumnity";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public Autumnity() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext context = ModLoadingContext.get();
		MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);
		AutumnityPaintings.PAINTINGS.register(bus);
		AutumnityEffects.EFFECTS.register(bus);
		AutumnityPotions.POTIONS.register(bus);
		AutumnityFeatures.FEATURES.register(bus);
		AutumnityStructures.STRUCTURES.register(bus);
		AutumnityParticles.PARTICLES.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);

		context.registerConfig(ModConfig.Type.COMMON, AutumnityConfig.COMMON_SPEC);
		DataUtil.registerConfigCondition(MOD_ID, AutumnityConfig.COMMON);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			AutumnityCompat.registerCompat();
			AutumnityPotions.registerBrewingRecipes();
			AutumnityBiomes.addBiomeTypes();
			AutumnityBiomes.addBiomesToGeneration();
			AutumnityFeatures.Configured.registerConfiguredFeatures();
			AutumnityStructures.Configured.registerConfiguredStructureFeatures();
			AutumnityStructures.registerNoiseSettings();
			AutumnityEntities.registerSpawns();
		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		AutumnityEntities.registerRenderers();
		event.enqueueWork(() -> {
			AutumnityClient.registerRenderLayers();
			AutumnityClient.registerBlockColors();
		});
	}
}