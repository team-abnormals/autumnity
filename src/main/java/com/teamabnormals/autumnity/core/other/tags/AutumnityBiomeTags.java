package com.teamabnormals.autumnity.core.other.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class AutumnityBiomeTags {
	public static final TagKey<Biome> HAS_MAPLE_HUT = biomeTag("has_structure/maple_hut");

	public static final TagKey<Biome> HAS_SNAIL = biomeTag("has_animal/snail");
	public static final TagKey<Biome> HAS_TURKEY = biomeTag("has_animal/turkey");

	public static final TagKey<Biome> HAS_MAPLE_TREE = biomeTag("has_feature/maple_tree");
	public static final TagKey<Biome> HAS_YELLOW_MAPLE_TREE = biomeTag("has_feature/spotted_maple_tree/yellow");
	public static final TagKey<Biome> HAS_ORANGE_MAPLE_TREE = biomeTag("has_feature/spotted_maple_tree/orange");
	public static final TagKey<Biome> HAS_RED_MAPLE_TREE = biomeTag("has_feature/spotted_maple_tree/red");

	private static TagKey<Biome> biomeTag(String name) {
		return TagUtil.biomeTag(Autumnity.MOD_ID, name);
	}
}
