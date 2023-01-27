package com.teamabnormals.autumnity.core.other.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class AutumnityBannerPatternTags {
	public static final TagKey<BannerPattern> PATTERN_ITEM_MAPLE_LEAF = bannerPatternTag("pattern_item/maple_leaf");
	public static final TagKey<BannerPattern> PATTERN_ITEM_SWIRL = bannerPatternTag("pattern_item/swirl");

	private static TagKey<BannerPattern> bannerPatternTag(String name) {
		return TagKey.create(Registry.BANNER_PATTERN_REGISTRY, new ResourceLocation(Autumnity.MOD_ID, name));
	}
}