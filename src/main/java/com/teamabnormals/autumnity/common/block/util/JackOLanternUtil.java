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

import java.util.Map;

public class JackOLanternUtil {
	private static final Map<ResourceLocation, Block> JACK_O_LANTERNS = Util.make(Maps.newHashMap(), (jackolanterns) -> {
		jackolanterns.put(Items.TORCH.getRegistryName(), Blocks.JACK_O_LANTERN);
		jackolanterns.put(Items.SOUL_TORCH.getRegistryName(), AutumnityBlocks.SOUL_JACK_O_LANTERN.get());
		jackolanterns.put(Items.REDSTONE_TORCH.getRegistryName(), AutumnityBlocks.REDSTONE_JACK_O_LANTERN.get());
		jackolanterns.put(AutumnityConstants.ENDER_TORCH, AutumnityBlocks.ENDER_JACK_O_LANTERN.get());
		jackolanterns.put(AutumnityConstants.CUPRIC_TORCH, AutumnityBlocks.CUPRIC_JACK_O_LANTERN.get());
	});

	private static final Map<ResourceLocation, Block> LARGE_JACK_O_LANTERNS = Util.make(Maps.newHashMap(), (jackolanterns) -> {
		jackolanterns.put(Items.TORCH.getRegistryName(), AutumnityBlocks.LARGE_JACK_O_LANTERN_SLICE.get());
		jackolanterns.put(Items.SOUL_TORCH.getRegistryName(), AutumnityBlocks.LARGE_SOUL_JACK_O_LANTERN_SLICE.get());
		jackolanterns.put(Items.REDSTONE_TORCH.getRegistryName(), AutumnityBlocks.LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get());
		jackolanterns.put(AutumnityConstants.ENDER_TORCH, AutumnityBlocks.LARGE_ENDER_JACK_O_LANTERN_SLICE.get());
		jackolanterns.put(AutumnityConstants.CUPRIC_TORCH, AutumnityBlocks.LARGE_CUPRIC_JACK_O_LANTERN_SLICE.get());
	});

	public static Block getJackOLantern(ItemStack stack) {
		return JACK_O_LANTERNS.getOrDefault(stack.getItem().getRegistryName(), Blocks.AIR);
	}

	public static Block getLargeJackOLantern(ItemStack stack) {
		return LARGE_JACK_O_LANTERNS.getOrDefault(stack.getItem().getRegistryName(), Blocks.AIR);
	}
}