package com.teamabnormals.autumnity.core;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.tuple.Pair;

public class AutumnityConfig {
	public static class Common {
		@ConfigKey("foul_berries_require_pips")
		public final ForgeConfigSpec.ConfigValue<Boolean> foulBerriesRequirePips;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("tweaks");
			builder.push("foul_berries");
			foulBerriesRequirePips = builder.comment("If Foul Berry Bushes require pips to place, to prevent accidental placement (requires Berry Good)").define("Foul Berries require pips", ModList.get().isLoaded("berry_good"));
			builder.pop();
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
