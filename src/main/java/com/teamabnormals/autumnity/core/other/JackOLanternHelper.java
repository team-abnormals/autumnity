package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.ModList;

public class JackOLanternHelper {
	public static Block getJackOLantern(Item torch) {
		if (torch == Items.TORCH)
			return Blocks.JACK_O_LANTERN;
		else if (torch == Items.SOUL_TORCH)
			return AutumnityBlocks.SOUL_JACK_O_LANTERN.get();
		else if (torch == Items.REDSTONE_TORCH)
			return AutumnityBlocks.REDSTONE_JACK_O_LANTERN.get();
		else if ((ModList.get().isLoaded("endergetic") && torch == AutumnityCompat.ENDER_TORCH))
			return AutumnityBlocks.ENDER_JACK_O_LANTERN.get();
		else if ((ModList.get().isLoaded("caverns_and_chasms") && torch == AutumnityCompat.CUPRIC_TORCH))
			return AutumnityBlocks.CURSED_JACK_O_LANTERN.get();
		else
			return null;
	}

	public static Block getLargeJackOLantern(Item torch) {
		if (torch == Items.TORCH)
			return AutumnityBlocks.LARGE_JACK_O_LANTERN_SLICE.get();
		else if (torch == Items.SOUL_TORCH)
			return AutumnityBlocks.LARGE_SOUL_JACK_O_LANTERN_SLICE.get();
		else if (torch == Items.REDSTONE_TORCH)
			return AutumnityBlocks.LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get();
		else if ((ModList.get().isLoaded("endergetic") && torch == AutumnityCompat.ENDER_TORCH))
			return AutumnityBlocks.LARGE_ENDER_JACK_O_LANTERN_SLICE.get();
		else if ((ModList.get().isLoaded("caverns_and_chasms") && torch == AutumnityCompat.CUPRIC_TORCH))
			return AutumnityBlocks.LARGE_CURSED_JACK_O_LANTERN_SLICE.get();
		else
			return null;
	}
}
