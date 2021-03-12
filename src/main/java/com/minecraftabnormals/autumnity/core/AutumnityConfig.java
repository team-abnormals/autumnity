package com.minecraftabnormals.autumnity.core;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;

public class AutumnityConfig {
	public static class Common {
		public final ForgeConfigSpec.ConfigValue<List<String>> snailSpawnBiomes;
		public final ForgeConfigSpec.ConfigValue<List<String>> turkeySpawnBiomes;
		public final ForgeConfigSpec.ConfigValue<List<String>> mapleTreeBiomes;
		public final ForgeConfigSpec.ConfigValue<Integer> mapleForestWeight;
		public final ForgeConfigSpec.ConfigValue<Integer> mapleForestHillsWeight;
		public final ForgeConfigSpec.ConfigValue<Integer> pumpkinFieldsWeight;
		public final ForgeConfigSpec.ConfigValue<Boolean> yellowSpottedForest;
		public final ForgeConfigSpec.ConfigValue<Boolean> orangeSpottedDarkForest;
		public final ForgeConfigSpec.ConfigValue<Boolean> redSpottedTaiga;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Common configurations for Autumnity")
			.push("common");
			
			builder.push("entities");
			snailSpawnBiomes = builder
					.comment("A list of biomes where snails can spawn. The list doesn't include biomes from this mod.")
					.define("Snail Spawn Biomes", Lists.newArrayList());
			turkeySpawnBiomes = builder
					.comment("A list of biomes where turkeys can spawn. The list doesn't include biomes from this mod.",
							"Chickens will not spawn in these biomes.")
					.define("Turkey Spawn Biomes", Lists.newArrayList());
			builder.pop();
			
			builder.comment("Values for biome frequencies; lower = more rare. (Requires restart)",
					"If a biome has a default weight of 0, it generates as a sub-biome and not on its own.")
			.push("biome_weights");
			
			mapleForestWeight = builder.define("Maple Forest weight", 6);
			mapleForestHillsWeight = builder.define("Maple Forest Hills weight", 0);
			pumpkinFieldsWeight = builder.define("Pumpkin Fields weight", 1);
			builder.pop();
			
			builder.push("misc");
			
			mapleTreeBiomes = builder
					.comment("A list of biomes where green maple trees can generate naturally.",
							"The list does not include biomes from this mod.")
					.define("Maple Tree Biomes", Lists.newArrayList("minecraft:forest", "minecraft:wooded_hills", "minecraft:flower_forest"));
			yellowSpottedForest = builder.define("Yellow Spotted Forest", true);
			orangeSpottedDarkForest = builder.define("Orange Spotted Dark Forest", true);
			redSpottedTaiga = builder.define("Red Spotted Taiga", true);
			builder.pop();
		}
	}

	static final ForgeConfigSpec COMMON_SPEC;
	public static final AutumnityConfig.Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(AutumnityConfig.Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}