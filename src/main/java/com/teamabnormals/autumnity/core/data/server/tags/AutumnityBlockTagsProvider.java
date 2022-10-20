package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBlockTags;
import com.teamabnormals.blueprint.core.other.tags.BlueprintBlockTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.teamabnormals.autumnity.core.registry.AutumnityBlocks.*;

public class AutumnityBlockTagsProvider extends BlockTagsProvider {

	public AutumnityBlockTagsProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, Autumnity.MOD_ID, fileHelper);
	}

	@Override
	public void addTags() {
		this.tag(BlockTags.SLABS).add(SNAIL_SHELL_BRICK_SLAB.get(), SNAIL_SHELL_TILE_SLAB.get());
		this.tag(BlockTags.STAIRS).add(SNAIL_SHELL_BRICK_STAIRS.get(), SNAIL_SHELL_TILE_STAIRS.get());
		this.tag(BlockTags.WALLS).add(SNAIL_SHELL_BRICK_WALL.get(), SNAIL_SHELL_TILE_WALL.get());

		this.tag(BlockTags.PLANKS).add(MAPLE_PLANKS.get(), VERTICAL_MAPLE_PLANKS.get());
		this.tag(BlockTags.LOGS_THAT_BURN).addTag(AutumnityBlockTags.MAPLE_LOGS);
		this.tag(BlockTags.WOODEN_SLABS).add(MAPLE_SLAB.get());
		this.tag(BlockTags.WOODEN_STAIRS).add(MAPLE_STAIRS.get());
		this.tag(BlockTags.WOODEN_FENCES).add(MAPLE_FENCE.get());
		this.tag(BlockTags.FENCE_GATES).add(MAPLE_FENCE_GATE.get());
		this.tag(BlockTags.WOODEN_DOORS).add(MAPLE_DOOR.get());
		this.tag(BlockTags.WOODEN_TRAPDOORS).add(MAPLE_TRAPDOOR.get());
		this.tag(BlockTags.WOODEN_BUTTONS).add(MAPLE_BUTTON.get());
		this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(MAPLE_PRESSURE_PLATE.get());
		this.tag(BlockTags.STANDING_SIGNS).add(MAPLE_SIGN.getFirst().get());
		this.tag(BlockTags.WALL_SIGNS).add(MAPLE_SIGN.getSecond().get());
		this.tag(BlockTags.CLIMBABLE).add(MAPLE_LADDER.get());
		this.tag(BlockTags.BEEHIVES).add(MAPLE_BEEHIVE.get());

		this.tag(BlockTags.SMALL_FLOWERS).add(AUTUMN_CROCUS.get());
		this.tag(BlockTags.LEAVES).add(MAPLE_LEAVES.get(), YELLOW_MAPLE_LEAVES.get(), ORANGE_MAPLE_LEAVES.get(), RED_MAPLE_LEAVES.get());
		this.tag(BlockTags.SAPLINGS).add(MAPLE_SAPLING.get(), YELLOW_MAPLE_SAPLING.get(), ORANGE_MAPLE_SAPLING.get(), RED_MAPLE_SAPLING.get());
		this.tag(BlockTags.FLOWER_POTS).add(POTTED_AUTUMN_CROCUS.get(), POTTED_FOUL_BERRIES.get(), POTTED_MAPLE_SAPLING.get(), POTTED_YELLOW_MAPLE_SAPLING.get(), POTTED_ORANGE_MAPLE_SAPLING.get(), POTTED_RED_MAPLE_SAPLING.get());

		this.tag(BlockTags.BEE_GROWABLES).add(FOUL_BERRY_BUSH_PIPS.get(), FOUL_BERRY_BUSH.get(), TALL_FOUL_BERRY_BUSH.get());
		this.tag(BlockTags.ENDERMAN_HOLDABLE).add(LARGE_PUMPKIN_SLICE.get(), CARVED_LARGE_PUMPKIN_SLICE.get());
		this.tag(BlockTags.PIGLIN_REPELLENTS).add(SOUL_JACK_O_LANTERN.get(), LARGE_SOUL_JACK_O_LANTERN_SLICE.get());
		this.tag(BlockTags.GUARDED_BY_PIGLINS).add(MAPLE_CHEST.getFirst().get(), MAPLE_CHEST.getSecond().get());

		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(SNAIL_SHELL_BLOCK.get(), SNAIL_SHELL_BRICKS.get(), SNAIL_SHELL_BRICK_STAIRS.get(), SNAIL_SHELL_BRICK_SLAB.get(), SNAIL_SHELL_BRICK_WALL.get(), SNAIL_SHELL_BRICK_VERTICAL_SLAB.get(), CHISELED_SNAIL_SHELL_BRICKS.get(), SNAIL_SHELL_TILES.get(), SNAIL_SHELL_TILE_STAIRS.get(), SNAIL_SHELL_TILE_SLAB.get(), SNAIL_SHELL_TILE_WALL.get(), SNAIL_SHELL_TILE_VERTICAL_SLAB.get());
		this.tag(BlockTags.MINEABLE_WITH_AXE).add(FOUL_BERRY_BUSH_PIPS.get(), FOUL_BERRY_BUSH.get(), TALL_FOUL_BERRY_BUSH.get(), MAPLE_BOARDS.get(), MAPLE_BOOKSHELF.get(), MAPLE_LADDER.get(), MAPLE_BEEHIVE.get(), MAPLE_CHEST.getFirst().get(), MAPLE_CHEST.getSecond().get(), MAPLE_HEDGE.get(), YELLOW_MAPLE_HEDGE.get(), ORANGE_MAPLE_HEDGE.get(), RED_MAPLE_HEDGE.get(), MAPLE_POST.get(), STRIPPED_MAPLE_POST.get(), SOUL_JACK_O_LANTERN.get(), REDSTONE_JACK_O_LANTERN.get(), ENDER_JACK_O_LANTERN.get(), CUPRIC_JACK_O_LANTERN.get(), LARGE_PUMPKIN_SLICE.get(), CARVED_LARGE_PUMPKIN_SLICE.get(), LARGE_JACK_O_LANTERN_SLICE.get(), LARGE_SOUL_JACK_O_LANTERN_SLICE.get(), LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get(), LARGE_ENDER_JACK_O_LANTERN_SLICE.get(), LARGE_CUPRIC_JACK_O_LANTERN_SLICE.get(), TURKEY_EGG_CRATE.get());
		this.tag(BlockTags.MINEABLE_WITH_HOE).add(FOUL_BERRY_SACK.get(), MAPLE_LEAVES.get(), YELLOW_MAPLE_LEAVES.get(), ORANGE_MAPLE_LEAVES.get(), RED_MAPLE_LEAVES.get(), MAPLE_LEAF_PILE.get(), YELLOW_MAPLE_LEAF_PILE.get(), ORANGE_MAPLE_LEAF_PILE.get(), RED_MAPLE_LEAF_PILE.get(), MAPLE_LEAF_CARPET.get(), YELLOW_MAPLE_LEAF_CARPET.get(), ORANGE_MAPLE_LEAF_CARPET.get(), RED_MAPLE_LEAF_CARPET.get());
		this.tag(AutumnityBlockTags.MINEABLE_WITH_KNIFE).add(TURKEY.get(), COOKED_TURKEY.get());

		this.tag(AutumnityBlockTags.MAPLE_LOGS).add(MAPLE_LOG.get(), STRIPPED_MAPLE_LOG.get(), SAPPY_MAPLE_LOG.get(), MAPLE_WOOD.get(), STRIPPED_MAPLE_WOOD.get(), SAPPY_MAPLE_WOOD.get());
		this.tag(AutumnityBlockTags.SLIPPERY_SNAIL_GOO_BLOCKS).add(Blocks.WET_SPONGE);
		this.tag(AutumnityBlockTags.SNAIL_SNACKS).add(Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.CRIMSON_FUNGUS, Blocks.WARPED_FUNGUS).addOptional(new ResourceLocation("quark", "glowshroom"));

		this.tag(Tags.Blocks.CHESTS_WOODEN).add(MAPLE_CHEST.getFirst().get(), MAPLE_CHEST.getSecond().get());
		this.tag(Tags.Blocks.CHESTS_TRAPPED).add(MAPLE_CHEST.getSecond().get());
		this.tag(Tags.Blocks.FENCES_WOODEN).add(MAPLE_FENCE.get());
		this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(MAPLE_FENCE_GATE.get());

		this.tag(BlueprintBlockTags.LEAF_PILES).add(MAPLE_LEAF_PILE.get(), YELLOW_MAPLE_LEAF_PILE.get(), ORANGE_MAPLE_LEAF_PILE.get(), RED_MAPLE_LEAF_PILE.get());
		this.tag(BlueprintBlockTags.LADDERS).add(MAPLE_LADDER.get());
		this.tag(BlueprintBlockTags.VERTICAL_SLABS).add(SNAIL_SHELL_BRICK_VERTICAL_SLAB.get(), SNAIL_SHELL_TILE_VERTICAL_SLAB.get());
		this.tag(BlueprintBlockTags.WOODEN_VERTICAL_SLABS).add(MAPLE_VERTICAL_SLAB.get());
		this.tag(BlueprintBlockTags.HEDGES).add(MAPLE_HEDGE.get(), YELLOW_MAPLE_HEDGE.get(), ORANGE_MAPLE_HEDGE.get(), RED_MAPLE_HEDGE.get());
	}
}