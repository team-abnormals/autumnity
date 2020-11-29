package com.minecraftabnormals.autumnity.core.other;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class ModCompatibility
{
	public static final Item ENDER_TORCH = ForgeRegistries.ITEMS.getValue(new ResourceLocation("endergetic", "ender_torch"));
	public static final Block YUCCA_GATEAU = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("atmospheric", "yucca_gateau"));
}