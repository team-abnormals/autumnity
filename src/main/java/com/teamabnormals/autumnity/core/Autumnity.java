package com.teamabnormals.autumnity.core;

import com.teamabnormals.autumnity.core.data.client.AutumnityBlockStateProvider;
import com.teamabnormals.autumnity.core.data.client.AutumnityItemModelProvider;
import com.teamabnormals.autumnity.core.data.server.*;
import com.teamabnormals.autumnity.core.data.server.tags.*;
import com.teamabnormals.autumnity.core.other.AutumnityClientCompat;
import com.teamabnormals.autumnity.core.other.AutumnityCompat;
import com.teamabnormals.autumnity.core.registry.*;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.gallery.core.data.client.GalleryItemModelProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod(Autumnity.MOD_ID)
public class Autumnity {
	public static final String MOD_ID = "autumnity";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public Autumnity(IEventBus bus, ModContainer container) {
		AutumnityBlocks.BLOCKS.register(bus);
		AutumnityItems.ITEMS.register(bus);
		AutumnityEntityTypes.ENTITY_TYPES.register(bus);
		AutumnitySoundEvents.SOUND_EVENTS.register(bus);
		AutumnityMobEffects.MOB_EFFECTS.register(bus);
		AutumnityPotions.POTIONS.register(bus);
		AutumnityPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES.register(bus);
		AutumnityFeatures.FEATURES.register(bus);
		AutumnityParticleTypes.PARTICLE_TYPES.register(bus);
		AutumnityConditions.CONDITION_SERIALIZERS.register(bus);
		AutumnityCriteriaTriggers.TRIGGERS.register(bus);
		AutumnityArmorMaterials.ARMOR_MATERIALS.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);

		container.registerConfig(ModConfig.Type.COMMON, AutumnityConfig.COMMON_SPEC);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(AutumnityCompat::register);
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(AutumnityClientCompat::register);
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<Provider> provider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		boolean server = event.includeServer();

		AutumnityDatapackProvider datapackEntries = new AutumnityDatapackProvider(output, provider);
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
		generator.addProvider(server, new AutumnityRecipeProvider(output, provider));
		generator.addProvider(server, AutumnityAdvancementProvider.create(output, provider, helper));
		generator.addProvider(server, new AutumnityLootTableProvider(output, provider));
		generator.addProvider(server, new AutumnityAdvancementModifierProvider(output, provider));
		generator.addProvider(server, new AutumnityDataRemolderProvider(output, provider));
		generator.addProvider(server, new AutumnityDataMapProvider(output, provider));

		boolean client = event.includeClient();
		generator.addProvider(client, new AutumnityItemModelProvider(output, helper));
		generator.addProvider(client, new AutumnityBlockStateProvider(output, helper));

		generator.addProvider(client, new GalleryItemModelProvider(MOD_ID, output, helper, provider));
	}

	public static ResourceLocation location(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}