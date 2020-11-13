package com.minecraftabnormals.autumnity.core.other;

import com.minecraftabnormals.autumnity.core.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;

public class AutumnityTags
{
	public static final INamedTag<Block> SNAIL_BLOCK_FOODS = BlockTags.makeWrapperTag(Reference.MOD_ID + ":snail_foods");
	public static final INamedTag<Block> SLIPPERY_SNAIL_SLIME_BLOCKS = BlockTags.makeWrapperTag(Reference.MOD_ID + ":slippery_snail_slime_blocks");
	public static final INamedTag<Block> MAPLE_LOGS = BlockTags.makeWrapperTag(Reference.MOD_ID + ":maple_logs");
	
	public static final INamedTag<Item> SNAIL_FOODS = ItemTags.makeWrapperTag(Reference.MOD_ID + ":snail_foods");
	public static final INamedTag<Item> SNAIL_BREEDING_ITEMS = ItemTags.makeWrapperTag(Reference.MOD_ID + ":snail_breeding_items");
	public static final INamedTag<Item> SNAIL_TEMPTATION_ITEMS = ItemTags.makeWrapperTag(Reference.MOD_ID + ":snail_temptation_items");
	public static final INamedTag<Item> SNAIL_GLOWING_FOODS = ItemTags.makeWrapperTag(Reference.MOD_ID + ":snail_glowing_foods");
	public static final INamedTag<Item> SNAIL_SPEEDING_FOODS = ItemTags.makeWrapperTag(Reference.MOD_ID + ":snail_speeding_foods");
	
	public static final INamedTag<Item> TURKEY_BREEDING_ITEMS = ItemTags.makeWrapperTag(Reference.MOD_ID + ":turkey_breeding_items");
}