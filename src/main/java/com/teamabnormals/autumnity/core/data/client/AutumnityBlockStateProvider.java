package com.teamabnormals.autumnity.core.data.client;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityBlockFamilies;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.teamabnormals.autumnity.core.registry.AutumnityBlocks.*;

public class AutumnityBlockStateProvider extends BlueprintBlockStateProvider {

	public AutumnityBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Autumnity.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.blockFamily(AutumnityBlockFamilies.MAPLE_PLANKS_FAMILY);
		this.blockFamily(AutumnityBlockFamilies.SNAIL_SHELL_BRICKS_FAMILY);
		this.blockFamily(AutumnityBlockFamilies.SNAIL_SHELL_TILES_FAMILY);

		this.block(CHISELED_SNAIL_SHELL_BRICKS);

		this.logBlocks(MAPLE_LOG, MAPLE_WOOD);
		this.logBlocks(STRIPPED_MAPLE_LOG, STRIPPED_MAPLE_WOOD);
		this.logBlocks(SAPPY_MAPLE_LOG, SAPPY_MAPLE_WOOD);
		this.hangingSignBlocks(STRIPPED_MAPLE_LOG, MAPLE_HANGING_SIGNS.getFirst(), MAPLE_HANGING_SIGNS.getSecond());

		this.leavesBlocks(MAPLE_LEAVES, MAPLE_LEAF_PILE);
		this.leavesBlocks(YELLOW_MAPLE_LEAVES, YELLOW_MAPLE_LEAF_PILE);
		this.leavesBlocks(ORANGE_MAPLE_LEAVES, ORANGE_MAPLE_LEAF_PILE);
		this.leavesBlocks(RED_MAPLE_LEAVES, RED_MAPLE_LEAF_PILE);

		this.crossBlockWithPot(MAPLE_SAPLING, POTTED_MAPLE_SAPLING);
		this.crossBlockWithPot(YELLOW_MAPLE_SAPLING, POTTED_YELLOW_MAPLE_SAPLING);
		this.crossBlockWithPot(ORANGE_MAPLE_SAPLING, POTTED_ORANGE_MAPLE_SAPLING);
		this.crossBlockWithPot(RED_MAPLE_SAPLING, POTTED_RED_MAPLE_SAPLING);
		this.crossBlockWithPot(AUTUMN_CROCUS, POTTED_AUTUMN_CROCUS);

		this.woodworksBlocks(MAPLE_PLANKS, MAPLE_BOARDS, MAPLE_LADDER, MAPLE_BOOKSHELF, MAPLE_BEEHIVE, MAPLE_CHEST, TRAPPED_MAPLE_CHEST);
		this.chiseledBookshelfBlock(CHISELED_MAPLE_BOOKSHELF, BOTTOM_BOOKSHELF_POSITIONS);
	}
}