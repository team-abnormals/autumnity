package com.teamabnormals.autumnity.core;

import com.teamabnormals.autumnity.client.renderer.entity.FallingHeadBlockRenderer;
import com.teamabnormals.autumnity.client.renderer.entity.SnailRenderer;
import com.teamabnormals.autumnity.client.renderer.entity.TurkeyEggRenderer;
import com.teamabnormals.autumnity.client.renderer.entity.TurkeyRenderer;
import com.teamabnormals.autumnity.client.renderer.entity.model.SnailModel;
import com.teamabnormals.autumnity.client.renderer.entity.model.TurkeyModel;
import com.teamabnormals.autumnity.core.data.client.AutumnityBlockStateProvider;
import com.teamabnormals.autumnity.core.data.server.modifiers.AutumnityModdedBiomeSliceProvider;
import com.teamabnormals.autumnity.core.data.server.tags.AutumnityBiomeTagsProvider;
import com.teamabnormals.autumnity.core.other.AutumnityClientCompat;
import com.teamabnormals.autumnity.core.other.AutumnityCompat;
import com.teamabnormals.autumnity.core.other.AutumnityModelLayers;
import com.teamabnormals.autumnity.core.registry.*;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityConfiguredFeatures;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityPlacedFeatures;
import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

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
		AutumnityMobEffects.MOB_EFFECTS.register(bus);
		AutumnityPotions.POTIONS.register(bus);
		AutumnityNoiseParameters.NOISE_PARAMETERS.register(bus);
		AutumnityPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES.register(bus);
		AutumnityFeatures.FEATURES.register(bus);
		AutumnityConfiguredFeatures.CONFIGURED_FEATURES.register(bus);
		AutumnityPlacedFeatures.PLACED_FEATURES.register(bus);
		AutumnityParticleTypes.PARTICLE_TYPES.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);

		bus.addGenericListener(Block.class, this::registerConfigConditions);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			bus.addListener(this::registerLayerDefinitions);
			bus.addListener(this::registerRenderers);
		});

		context.registerConfig(ModConfig.Type.COMMON, AutumnityConfig.COMMON_SPEC);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			AutumnityCompat.registerCompat();
			AutumnityPotions.registerBrewingRecipes();
			AutumnityBiomes.addBiomeTypes();
			AutumnityEntityTypes.registerSpawns();
		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			AutumnityClientCompat.registerRenderLayers();
			AutumnityClientCompat.registerBlockColors();
		});
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		boolean includeServer = event.includeServer();
		if (includeServer) {
			generator.addProvider(new AutumnityBiomeTagsProvider(generator, existingFileHelper));
			generator.addProvider(new AutumnityModdedBiomeSliceProvider(generator));
		}

		boolean includeClient = event.includeClient();
		if (includeClient) {
			generator.addProvider(new AutumnityBlockStateProvider(generator, existingFileHelper));
		}
	}

	private void registerConfigConditions(RegistryEvent.Register<Block> event) {
		DataUtil.registerConfigCondition(MOD_ID, AutumnityConfig.COMMON);
	}

	@OnlyIn(Dist.CLIENT)
	private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(AutumnityModelLayers.SNAIL, SnailModel::createBodyLayer);
		event.registerLayerDefinition(AutumnityModelLayers.TURKEY, TurkeyModel::createBodyLayer);
	}

	@OnlyIn(Dist.CLIENT)
	private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(AutumnityEntityTypes.SNAIL.get(), SnailRenderer::new);
		event.registerEntityRenderer(AutumnityEntityTypes.TURKEY.get(), TurkeyRenderer::new);
		event.registerEntityRenderer(AutumnityEntityTypes.TURKEY_EGG.get(), TurkeyEggRenderer::new);
		event.registerEntityRenderer(AutumnityEntityTypes.FALLING_HEAD_BLOCK.get(), FallingHeadBlockRenderer::new);
	}
}