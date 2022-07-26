package com.teamabnormals.autumnity.core;

import com.google.common.collect.Lists;
import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class AutumnityConfig {
	public static class Common {
		public final ForgeConfigSpec.ConfigValue<List<String>> snailSpawnBiomes;
		public final ForgeConfigSpec.ConfigValue<List<String>> turkeySpawnBiomes;
		public final ForgeConfigSpec.ConfigValue<List<String>> mapleTreeBiomes;

		@ConfigKey("yellow_spotted_forest")
		public final ForgeConfigSpec.ConfigValue<Boolean> yellowSpottedForest;

		@ConfigKey("orange_spotted_dark_forest")
		public final ForgeConfigSpec.ConfigValue<Boolean> orangeSpottedDarkForest;

		@ConfigKey("red_spotted_taiga")
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
