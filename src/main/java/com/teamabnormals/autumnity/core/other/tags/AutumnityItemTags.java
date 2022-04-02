package com.teamabnormals.autumnity.core.other.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AutumnityItemTags {
	public static final TagKey<Item> SNAIL_FOODS = itemTag("snail_foods");
	public static final TagKey<Item> SNAIL_BREEDING_ITEMS = itemTag("snail_breeding_items");
	public static final TagKey<Item> SNAIL_TEMPTATION_ITEMS = itemTag("snail_temptation_items");
	public static final TagKey<Item> SNAIL_GLOWING_FOODS = itemTag("snail_glowing_foods");
	public static final TagKey<Item> SNAIL_SPEEDING_FOODS = itemTag("snail_speeding_foods");
	public static final TagKey<Item> TURKEY_BREEDING_ITEMS = itemTag("turkey_breeding_items");

	public static final TagKey<Item> KNIVES = TagUtil.itemTag("farmersdelight", "tools/knives");

	private static TagKey<Item> itemTag(String name) {
		return TagUtil.itemTag(Autumnity.MOD_ID, name);
	}
}