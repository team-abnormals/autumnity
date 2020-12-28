package com.minecraftabnormals.autumnity.core;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Config {
	public static class Common {
		public final ForgeConfigSpec.ConfigValue<List<String>> snailSpawnBiomes;
		public final ForgeConfigSpec.ConfigValue<List<String>> mapleTreeBiomes;
		public final ForgeConfigSpec.ConfigValue<Integer> mapleForestWeight;
		public final ForgeConfigSpec.ConfigValue<Integer> pumpkinFieldsWeight;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("snail");
			snailSpawnBiomes = builder
					.comment("A list of biomes where snails can spawn. The list doesn't include maple forests.")
					.define("Snail Spawn Biomes", Lists.newArrayList());
			builder.pop();
			builder.push("biomes");
			mapleTreeBiomes = builder
					.comment("A list of biomes where maple trees can naturally generate. The list doesn't include maple forests.")
					.define("Maple Tree Biomes", Lists.newArrayList("minecraft:forest", "minecraft:wooded_hills", "minecraft:flower_forest"));
			mapleForestWeight = builder
					.comment("The greater the number the more common the biome is.")
					.define("Maple Forest Weight", 6);
			pumpkinFieldsWeight = builder
					.comment("The greater the number the more common the biome is.")
					.define("Pumpkin Fields Weight", 1);
			builder.pop();
		}
	}

	static final ForgeConfigSpec COMMON_SPEC;
	public static final Config.Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config.Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}