package com.teamabnormals.autumnity.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class AutumnityConfig {
	public static class Common {
		public final ForgeConfigSpec.ConfigValue<Boolean> generateSpottedForests;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("generation");
			generateSpottedForests = builder
					.comment("If colored Maple Trees should generate sparsely in Forests, Dark Forests, and Taigas respectively")
					.define("Generate Spotted Forests", true);
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
