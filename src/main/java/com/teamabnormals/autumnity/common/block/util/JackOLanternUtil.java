package com.teamabnormals.autumnity.common.block.util;

import com.google.common.collect.Maps;
import com.teamabnormals.autumnity.core.other.AutumnityConstants;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class JackOLanternUtil {
	private static final Map<ResourceLocation, Block> JACK_O_LANTERNS = Util.make(Maps.newHashMap(), (jackolanterns) -> {
		jackolanterns.put(ForgeRegistries.ITEMS.getKey(Items.TORCH), Blocks.JACK_O_LANTERN);
		jackolanterns.put(ForgeRegistries.ITEMS.getKey(Items.SOUL_TORCH), AutumnityBlocks.SOUL_JACK_O_LANTERN.get());
		jackolanterns.put(ForgeRegistries.ITEMS.getKey(Items.REDSTONE_TORCH), AutumnityBlocks.REDSTONE_JACK_O_LANTERN.get());
		jackolanterns.put(AutumnityConstants.ENDER_TORCH, AutumnityBlocks.ENDER_JACK_O_LANTERN.get());
		jackolanterns.put(AutumnityConstants.CUPRIC_TORCH, AutumnityBlocks.CUPRIC_JACK_O_LANTERN.get());
	});

	private static final Map<ResourceLocation, Block> LARGE_JACK_O_LANTERNS = Util.make(Maps.newHashMap(), (jackolanterns) -> {
		jackolanterns.put(ForgeRegistries.ITEMS.getKey(Items.TORCH), AutumnityBlocks.LARGE_JACK_O_LANTERN_SLICE.get());
		jackolanterns.put(ForgeRegistries.ITEMS.getKey(Items.SOUL_TORCH), AutumnityBlocks.LARGE_SOUL_JACK_O_LANTERN_SLICE.get());
		jackolanterns.put(ForgeRegistries.ITEMS.getKey(Items.REDSTONE_TORCH), AutumnityBlocks.LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get());
		jackolanterns.put(AutumnityConstants.ENDER_TORCH, AutumnityBlocks.LARGE_ENDER_JACK_O_LANTERN_SLICE.get());
		jackolanterns.put(AutumnityConstants.CUPRIC_TORCH, AutumnityBlocks.LARGE_CUPRIC_JACK_O_LANTERN_SLICE.get());
	});

	public static Block getJackOLantern(ItemStack stack) {
		return JACK_O_LANTERNS.getOrDefault(ForgeRegistries.ITEMS.getKey(stack.getItem()), Blocks.AIR);
	}

	public static Block getLargeJackOLantern(ItemStack stack) {
		return LARGE_JACK_O_LANTERNS.getOrDefault(ForgeRegistries.ITEMS.getKey(stack.getItem()), Blocks.AIR);
	}
}