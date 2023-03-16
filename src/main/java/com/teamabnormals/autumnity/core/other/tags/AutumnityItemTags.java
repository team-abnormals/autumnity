package com.teamabnormals.autumnity.core.other.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AutumnityItemTags {
	public static final TagKey<Item> SNAIL_FOOD = itemTag("snail_food");
	public static final TagKey<Item> SNAIL_SNACKS = itemTag("snail_snacks");
	public static final TagKey<Item> SNAIL_TEMPT_ITEMS = itemTag("snail_tempt_items");
	public static final TagKey<Item> SNAIL_GLOW_SNACKS = itemTag("snail_glow_snacks");
	public static final TagKey<Item> SNAIL_SPEED_SNACKS = itemTag("snail_speed_snacks");
	public static final TagKey<Item> TURKEY_FOOD = itemTag("turkey_food");
	public static final TagKey<Item> MAPLE_LOGS = itemTag("maple_logs");

	public static final TagKey<Item> TORCHES_ENDER = TagUtil.itemTag("forge", "torches/ender");
	public static final TagKey<Item> TORCHES_CUPRIC = TagUtil.itemTag("forge", "torches/cupric");

	public static final TagKey<Item> COOKED_TURKEY = TagUtil.itemTag("forge", "cooked_turkey");
	public static final TagKey<Item> RAW_TURKEY = TagUtil.itemTag("forge", "raw_turkey");
	public static final TagKey<Item> SEEDS_FOUL_BERRY = TagUtil.itemTag("forge", "seeds/foul_berry");

	public static final TagKey<Item> KNIVES = TagUtil.itemTag("farmersdelight", "tools/knives");

	private static TagKey<Item> itemTag(String name) {
		return TagUtil.itemTag(Autumnity.MOD_ID, name);
	}
}