package com.teamabnormals.autumnity.core.data.client;

import com.teamabnormals.autumnity.common.block.*;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityBlockFamilies;
import com.teamabnormals.blueprint.core.data.client.BlueprintBlockStateProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

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

		this.giantPumpkinChunkBlock(GIANT_PUMPKIN_CHUNK);
		this.giantPumpkinChunkBlock(GIANT_CARVED_PUMPKIN_CHUNK);
		this.giantPumpkinChunkBlock(GIANT_JACK_O_LANTERN_CHUNK);
		this.giantPumpkinChunkBlock(GIANT_SOUL_JACK_O_LANTERN_CHUNK);
		this.giantPumpkinChunkBlock(GIANT_REDSTONE_JACK_O_LANTERN_CHUNK);
		this.giantPumpkinChunkBlock(GIANT_ENDER_JACK_O_LANTERN_CHUNK);
		this.giantPumpkinChunkBlock(GIANT_CUPRIC_JACK_O_LANTERN_CHUNK);
	}

	public void giantPumpkinChunkBlock(DeferredBlock<Block> chunk) {
		Block block = chunk.get();
		String name = name(block);

		this.getVariantBuilder(block).forAllStates(state -> {
			String half = state.getValue(AbstractLargePumpkinSliceBlock.HALF).getSerializedName();

			Direction facing = state.getValue(AbstractLargePumpkinSliceBlock.FACING);
			String direction = switch (facing) {
				case SOUTH -> "southeast";
				case WEST -> "southwest";
				case NORTH -> "northwest";
				case EAST -> "northeast";
				default -> "";
			};

			String suffix = "_" + half + "_" + direction;
			String templateSuffix;

			ResourceLocation sideTexture = Autumnity.location("block/giant_pumpkin_side");
			ResourceLocation insideTexture;
			ResourceLocation faceTexture;

			if (block instanceof GiantJackOLanternChunkBlock) {
				String carvedSide = state.getValue(GiantCarvedPumpkinChunkBlock.CARVED_SIDE).getSerializedName();
				suffix += "_" + carvedSide;
				templateSuffix = suffix;

				insideTexture = Autumnity.location("block/giant_pumpkin_inside_seedless");
				String faceName = name.replace("_chunk", "");
				if (block instanceof GiantRedstoneJackOLanternChunkBlock && state.getValue(GiantRedstoneJackOLanternChunkBlock.LIT)) {
					faceName += "_on";
					suffix += "_on";
				}
				faceTexture = Autumnity.location("block/" + faceName);
			} else {
				templateSuffix = suffix + "_x";

				insideTexture = Autumnity.location("block/giant_pumpkin_inside");
				faceTexture = sideTexture;
			}

			return ConfiguredModel.builder()
					.modelFile(models().withExistingParent(name + suffix, Autumnity.location("block/template_giant_pumpkin_chunk" + templateSuffix))
							.texture("top", Autumnity.location("block/giant_pumpkin_top"))
							.texture("inside", insideTexture)
							.texture("side", sideTexture)
							.texture("face", faceTexture))
					.build();
		});

		String itemName = "block/" + name + "_bottom_northeast";
		if (block instanceof GiantJackOLanternChunkBlock)
			itemName += "_z";

		this.simpleBlockItem(block, models().getExistingFile(Autumnity.location(itemName)));
	}
}