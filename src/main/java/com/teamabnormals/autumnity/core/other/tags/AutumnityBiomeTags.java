package com.teamabnormals.autumnity.core.other.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class AutumnityBiomeTags {
	public static final TagKey<Biome> HAS_MAPLE_HUT = biomeTag("has_structure/maple_hut");

	private static TagKey<Biome> biomeTag(String name) {
		return TagUtil.biomeTag(Autumnity.MOD_ID, name);
	}
}
