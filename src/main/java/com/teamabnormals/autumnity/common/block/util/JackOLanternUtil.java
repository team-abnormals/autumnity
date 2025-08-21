package com.teamabnormals.autumnity.common.block.util;

import com.google.common.collect.Maps;
import com.teamabnormals.autumnity.core.other.AutumnityConstants;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class JackOLanternUtil {
	private static final Map<ResourceLocation, Block> JACK_O_LANTERNS = Util.make(Maps.newHashMap(), (jackolanterns) -> {
		jackolanterns.put(BuiltInRegistries.ITEM.getKey(Items.TORCH), Blocks.JACK_O_LANTERN);
		jackolanterns.put(BuiltInRegistries.ITEM.getKey(Items.SOUL_TORCH), AutumnityBlocks.SOUL_JACK_O_LANTERN.get());
		jackolanterns.put(BuiltInRegistries.ITEM.getKey(Items.REDSTONE_TORCH), AutumnityBlocks.REDSTONE_JACK_O_LANTERN.get());
		jackolanterns.put(AutumnityConstants.ENDER_TORCH, AutumnityBlocks.ENDER_JACK_O_LANTERN.get());
		jackolanterns.put(AutumnityConstants.CUPRIC_TORCH, AutumnityBlocks.CUPRIC_JACK_O_LANTERN.get());
	});

	private static final Map<ResourceLocation, Block> LARGE_JACK_O_LANTERNS = Util.make(Maps.newHashMap(), (jackolanterns) -> {
		jackolanterns.put(BuiltInRegistries.ITEM.getKey(Items.TORCH), AutumnityBlocks.GIANT_JACK_O_LANTERN_CHUNK.get());
		jackolanterns.put(BuiltInRegistries.ITEM.getKey(Items.SOUL_TORCH), AutumnityBlocks.GIANT_SOUL_JACK_O_LANTERN_CHUNK.get());
		jackolanterns.put(BuiltInRegistries.ITEM.getKey(Items.REDSTONE_TORCH), AutumnityBlocks.GIANT_REDSTONE_JACK_O_LANTERN_CHUNK.get());
		jackolanterns.put(AutumnityConstants.ENDER_TORCH, AutumnityBlocks.GIANT_ENDER_JACK_O_LANTERN_CHUNK.get());
		jackolanterns.put(AutumnityConstants.CUPRIC_TORCH, AutumnityBlocks.GIANT_CUPRIC_JACK_O_LANTERN_CHUNK.get());
	});

	public static Block getJackOLantern(ItemStack stack) {
		return JACK_O_LANTERNS.getOrDefault(BuiltInRegistries.ITEM.getKey(stack.getItem()), Blocks.AIR);
	}

	public static Block getLargeJackOLantern(ItemStack stack) {
		return LARGE_JACK_O_LANTERNS.getOrDefault(BuiltInRegistries.ITEM.getKey(stack.getItem()), Blocks.AIR);
	}
}