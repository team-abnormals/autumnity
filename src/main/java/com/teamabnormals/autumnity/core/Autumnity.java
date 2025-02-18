package com.teamabnormals.autumnity.core;

import com.teamabnormals.autumnity.client.model.SnailModel;
import com.teamabnormals.autumnity.client.model.TurkeyModel;
import com.teamabnormals.autumnity.client.renderer.entity.SnailRenderer;
import com.teamabnormals.autumnity.client.renderer.entity.TurkeyEggRenderer;
import com.teamabnormals.autumnity.client.renderer.entity.TurkeyRenderer;
import com.teamabnormals.autumnity.core.data.client.AutumnityBlockStateProvider;
import com.teamabnormals.autumnity.core.data.client.AutumnityItemModelProvider;
import com.teamabnormals.autumnity.core.data.server.AutumnityAdvancementProvider;
import com.teamabnormals.autumnity.core.data.server.AutumnityDatapackBuiltinEntriesProvider;
import com.teamabnormals.autumnity.core.data.server.AutumnityLootTableProvider;
import com.teamabnormals.autumnity.core.data.server.AutumnityRecipeProvider;
import com.teamabnormals.autumnity.core.data.server.modifiers.AutumnityAdvancementModifierProvider;
import com.teamabnormals.autumnity.core.data.server.modifiers.AutumnityLootModifierProvider;
import com.teamabnormals.autumnity.core.data.server.tags.*;
import com.teamabnormals.autumnity.core.other.AutumnityClientCompat;
import com.teamabnormals.autumnity.core.other.AutumnityCompat;
import com.teamabnormals.autumnity.core.other.AutumnityModelLayers;
import com.teamabnormals.autumnity.core.registry.*;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.gallery.core.data.client.GalleryAssetsRemolderProvider;
import com.teamabnormals.gallery.core.data.client.GalleryItemModelProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

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
		AutumnityPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES.register(bus);
		AutumnityFeatures.FEATURES.register(bus);
		AutumnityParticleTypes.PARTICLE_TYPES.register(bus);
		AutumnityLootConditions.LOOT_CONDITION_TYPES.register(bus);
		AutumnityBannerPatterns.BANNER_PATTERNS.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			AutumnityBlocks.setupTabEditors();
			AutumnityItems.setupTabEditors();
			bus.addListener(this::registerLayerDefinitions);
			bus.addListener(this::registerRenderers);
		});

		context.registerConfig(ModConfig.Type.COMMON, AutumnityConfig.COMMON_SPEC);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			AutumnityCompat.registerCompat();
			AutumnityPotions.registerBrewingRecipes();
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
		PackOutput output = generator.getPackOutput();
		CompletableFuture<Provider> provider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		boolean server = event.includeServer();

		AutumnityDatapackBuiltinEntriesProvider datapackEntries = new AutumnityDatapackBuiltinEntriesProvider(output, provider);
		generator.addProvider(server, datapackEntries);
		provider = datapackEntries.getRegistryProvider();

		AutumnityBlockTagsProvider blockTags = new AutumnityBlockTagsProvider(output, provider, helper);
		generator.addProvider(server, blockTags);
		generator.addProvider(server, new AutumnityItemTagsProvider(output, provider, blockTags.contentsGetter(), helper));
		generator.addProvider(server, new AutumnityBiomeTagsProvider(output, provider, helper));
		generator.addProvider(server, new AutumnityBannerPatternTagsProvider(output, provider, helper));
		generator.addProvider(server, new AutumnityPaintingVariantTagsProvider(output, provider, helper));
		generator.addProvider(server, new AutumnityStructureTagsProvider(output, provider, helper));
		generator.addProvider(server, new AutumnityEntityTypeTagsProvider(output, provider, helper));
		generator.addProvider(server, new AutumnityRecipeProvider(output));
		generator.addProvider(server, AutumnityAdvancementProvider.create(output, provider, helper));
		generator.addProvider(server, new AutumnityLootTableProvider(output));
		generator.addProvider(server, new AutumnityAdvancementModifierProvider(output, provider));
		generator.addProvider(server, new AutumnityLootModifierProvider(output, provider));

		boolean client = event.includeClient();
		generator.addProvider(client, new AutumnityItemModelProvider(output, helper));
		generator.addProvider(client, new AutumnityBlockStateProvider(output, helper));

		generator.addProvider(client, new GalleryItemModelProvider(MOD_ID, output, helper));
		generator.addProvider(client, new GalleryAssetsRemolderProvider(MOD_ID, output, provider));
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
	}
}