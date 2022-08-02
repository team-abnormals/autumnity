package com.teamabnormals.autumnity.core.other.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class AutumnityBlockTags {
	public static final TagKey<Block> SNAIL_SNACKS = blockTag("snail_snacks");
	public static final TagKey<Block> SLIPPERY_SNAIL_SLIME_BLOCKS = blockTag("slippery_snail_slime_blocks");
	public static final TagKey<Block> MAPLE_LOGS = blockTag("maple_logs");

	private static TagKey<Block> blockTag(String name) {
		return TagUtil.blockTag(Autumnity.MOD_ID, name);
	}
}