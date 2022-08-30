package com.teamabnormals.autumnity.core;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class AutumnityConfig {
	public static class Common {
		public final ForgeConfigSpec.ConfigValue<Boolean> generateSpottedForests;
		public final ForgeConfigSpec.ConfigValue<List<String>> snailSpawnBiomes;
		public final ForgeConfigSpec.ConfigValue<List<String>> turkeySpawnBiomes;
		public final ForgeConfigSpec.ConfigValue<List<String>> mapleTreeBiomes;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("entities");
			snailSpawnBiomes = builder
					.comment("A list of biomes where Snails can spawn", "The list does not include biomes from this mod")
					.define("Snail Spawn Biomes", Lists.newArrayList());
			turkeySpawnBiomes = builder
					.comment("A list of biomes where Turkeys replace Chicken spawns", "The list does not include biomes from this mod")
					.define("Turkey Spawn Biomes", Lists.newArrayList());
			builder.pop();

			builder.push("generation");
			generateSpottedForests = builder
					.comment("If colored Maple Trees should generate sparsely in Forests, Dark Forests, and Taigas respectively")
					.define("Generate Spotted Forests", true);
			mapleTreeBiomes = builder
					.comment("A list of biomes where green Maple Trees can generate naturally", "The list does not include biomes from this mod")
					.define("Maple Tree Biomes", Lists.newArrayList("minecraft:forest", "minecraft:wooded_hills", "minecraft:flower_forest"));
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
