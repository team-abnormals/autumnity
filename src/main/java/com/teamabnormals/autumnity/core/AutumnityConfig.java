package com.teamabnormals.autumnity.core;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import org.apache.commons.lang3.tuple.Pair;

public class AutumnityConfig {
	public static class Common {
		@ConfigKey("foul_berries_require_pips")
		public final BooleanValue foulBerriesRequirePips;

		public final BooleanValue sappyMapleLogs;
		public final DoubleValue sappyMapleLogChance;
		public final BooleanValue fortuneAffectsMapleLogs;

		Common(ModConfigSpec.Builder builder) {
			builder.push("blocks");
			builder.push("foul_berries");
			foulBerriesRequirePips = builder.comment("If Foul Berry Bushes require pips to place, to prevent accidental placement (requires Berry Good)").define("Foul Berries require pips", ModList.get().isLoaded("berry_good"));
			builder.pop();
			builder.push("maple_log");
			sappyMapleLogs = builder.comment("If stripping Maple Logs with an axe can create Sappy Maple Logs").define("Sappy Maple Logs", true);
			sappyMapleLogChance = builder.comment("The base chance for a Maple Log to have sap when stripped").defineInRange("Sappy Maple Log chance", 0.25D, 0.0D, 1.0D);
			fortuneAffectsMapleLogs = builder.comment("If Fortune affects the odds for a Maple Log to have sap when stripped").define("Fortune affects Maple Logs", true);
			builder.pop();
			builder.pop();
		}
	}

	static final ModConfigSpec COMMON_SPEC;
	public static final AutumnityConfig.Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(AutumnityConfig.Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
