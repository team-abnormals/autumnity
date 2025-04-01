package com.teamabnormals.autumnity.core.registry.datapack;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBiomeTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.level.biome.Biome;

public class AutumnityWolfVariants {
	public static final ResourceKey<WolfVariant> SPICY = create("spicy");

	public static void bootstrap(BootstrapContext<WolfVariant> context) {
		register(context, SPICY, "wolf_spicy", AutumnityBiomeTags.IS_AUTUMNAL);
	}

	private static ResourceKey<WolfVariant> create(String name) {
		return ResourceKey.create(Registries.WOLF_VARIANT, Autumnity.location(name));
	}

	private static void register(BootstrapContext<WolfVariant> context, ResourceKey<WolfVariant> key, String name, TagKey<Biome> spawnBiomes) {
		register(context, key, name, context.lookup(Registries.BIOME).getOrThrow(spawnBiomes));
	}

	private static void register(BootstrapContext<WolfVariant> context, ResourceKey<WolfVariant> key, String name, HolderSet<Biome> spawnBiomes) {
		ResourceLocation texture = Autumnity.location("entity/wolf/" + name);
		ResourceLocation tame = Autumnity.location("entity/wolf/" + name + "_tame");
		ResourceLocation angry = Autumnity.location("entity/wolf/" + name + "_angry");
		context.register(key, new WolfVariant(texture, tame, angry, spawnBiomes));
	}
}