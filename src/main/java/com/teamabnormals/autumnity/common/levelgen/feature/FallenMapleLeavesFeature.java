package com.teamabnormals.autumnity.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class FallenMapleLeavesFeature extends Feature<TreeConfiguration> {

	public FallenMapleLeavesFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
		TreeConfiguration config = context.config();
		RandomSource random = context.random();
		BlockPos origin = context.origin();

		return placeLeaves(getMatchingLeafPile(config.foliageProvider.getState(random, origin).getBlock()), origin, context.level(), random);
	}

	public static boolean placeLeaves(BlockState leafpile, BlockPos origin, WorldGenLevel level, RandomSource random) {
		int i = 0;
		int radius = random.nextInt(4) == 0 ? 2 : 3;
		int ditheroffset = random.nextBoolean() ? 1 : 0;

		MutableBlockPos mutable = new MutableBlockPos();
		for (int x = -radius; x <= radius; ++x) {
			for (int z = -radius; z <= radius; ++z) {
				if ((Math.abs(x) < radius || Math.abs(z) < radius - 1) && (Math.abs(x) < radius - 1 || Math.abs(z) < radius)) {
					for (int y = -3; y <= 3; ++y) {
						mutable.set(origin.getX() + x, origin.getY() + y, origin.getZ() + z);

						float leafdensity = 1.0F - (float) Math.sqrt(x * x + z * z) / (radius * 2.0F - 1.0F);
						leafdensity += random.nextFloat() * 0.2F;
						if ((x + z) % 2 == ditheroffset)
							leafdensity -= 0.35F;

						if (leafdensity >= 0.5F && level.isEmptyBlock(mutable) && mutable.getY() < level.getMaxBuildHeight() && level.getBlockState(mutable.below()).getBlock() == Blocks.GRASS_BLOCK) {
							level.setBlock(mutable, leafpile.setValue(PipeBlock.DOWN, true), 2);
							++i;
						}
					}
				}
			}
		}

		return i > 0;
	}

	public static BlockState getMatchingLeafPile(Block leaves) {
		BlockState leafpile;
		if (leaves == AutumnityBlocks.RED_MAPLE_LEAVES.get())
			leafpile = AutumnityBlocks.RED_MAPLE_LEAF_PILE.get().defaultBlockState();
		else if (leaves == AutumnityBlocks.ORANGE_MAPLE_LEAVES.get())
			leafpile = AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE.get().defaultBlockState();
		else if (leaves == AutumnityBlocks.YELLOW_MAPLE_LEAVES.get())
			leafpile = AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE.get().defaultBlockState();
		else
			leafpile = AutumnityBlocks.MAPLE_LEAF_PILE.get().defaultBlockState();
		return leafpile;
	}
}