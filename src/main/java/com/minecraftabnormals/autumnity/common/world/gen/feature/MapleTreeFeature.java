package com.minecraftabnormals.autumnity.common.world.gen.feature;

import com.minecraftabnormals.abnormals_core.core.util.TreeUtil;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class MapleTreeFeature extends Feature<BaseTreeFeatureConfig> {
	public MapleTreeFeature(Codec<BaseTreeFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random random, BlockPos position, BaseTreeFeatureConfig config) {
		int i = random.nextInt(2) + 5;

		boolean flag = true;
		if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxBuildHeight()) {
			for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
				int k = 1;
				if (j == position.getY()) {
					k = 0;
				}

				if (j >= position.getY() + 1 + i - 2) {
					k = 2;
				}

				BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < worldIn.getMaxBuildHeight()) {
							if (!isAirOrLeaves(worldIn, blockpos$mutableblockpos.set(l, j, i1))) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}

			if (!flag) {
				return false;
			} else if (isValidGround(worldIn, position.below()) && position.getY() < worldIn.getMaxBuildHeight() - i - 1) {
				setDirtAt(worldIn, position.below());

				for (int i2 = 0; i2 < 2; ++i2) {
					BlockPos blockpos = position.above(i - 1 - i2);

					for (BlockPos blockpos1 : BlockPos.betweenClosed(blockpos.offset(-2, -1, -2), blockpos.offset(2, 3, 2))) {
						double d0 = blockpos1.distSqr(blockpos.getX(), blockpos.getY(), blockpos.getZ(), false);
						if (d0 <= (double) (2.35F * 2.35F) || (d0 <= (double) (2.5F * 2.5F) && random.nextInt(2) > 0)) {
							if (isAirOrLeaves(worldIn, blockpos1)) {
								this.placeLeafAt(worldIn, blockpos1, random, config);
							}
						}
					}
				}

				for (int i2 = 0; i2 < i; ++i2) {
					if (isAirOrLeaves(worldIn, position.above(i2))) {
						this.placeLogAt(worldIn, position.above(i2), random, config);
					}
				}

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private void placeLogAt(IWorldWriter worldIn, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
		BlockState logState = config.trunkProvider.getState(rand, pos);
		TreeUtil.setForcedState(worldIn, pos, logState);
	}

	private void placeLeafAt(IWorldGenerationReader world, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
		if (isAirOrLeaves(world, pos)) {
			if (TreeUtil.isAirOrLeaves(world, pos)) {
				if (config.leavesProvider.getState(rand, pos).hasProperty(LeavesBlock.DISTANCE)) {
					TreeUtil.setForcedState(world, pos, config.leavesProvider.getState(rand, pos).setValue(LeavesBlock.DISTANCE, 1));
				} else {
					TreeUtil.setForcedState(world, pos, config.leavesProvider.getState(rand, pos));
				}
			}
		}
	}

	protected final void setLogState(IWorldWriter worldIn, BlockPos pos, BlockState state) {
		worldIn.setBlock(pos, state, 19);
	}

	public static boolean isAir(IWorldGenerationBaseReader worldIn, BlockPos pos) {
		if (!(worldIn instanceof net.minecraft.world.IBlockReader)) {
			return worldIn.isStateAtPosition(pos, BlockState::isAir);
		} else {
			return worldIn.isStateAtPosition(pos, state -> state.isAir((net.minecraft.world.IBlockReader) worldIn, pos));
		}
	}

	public static boolean isAirOrLeaves(IWorldGenerationBaseReader worldIn, BlockPos pos) {
		if (worldIn instanceof net.minecraft.world.IWorldReader) {
			return worldIn.isStateAtPosition(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.IWorldReader) worldIn, pos));
		}
		return worldIn.isStateAtPosition(pos, (state) -> {
			return state.isAir() || state.is(BlockTags.LEAVES);
		});
	}

	public static void setDirtAt(IWorld worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
			worldIn.setBlock(pos, Blocks.DIRT.defaultBlockState(), 18);
		}
	}

	public static boolean isValidGround(IWorld world, BlockPos pos) {
		return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) AutumnityBlocks.MAPLE_SAPLING.get());
	}
}