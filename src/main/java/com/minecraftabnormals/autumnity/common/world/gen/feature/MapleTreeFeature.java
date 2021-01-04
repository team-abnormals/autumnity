package com.minecraftabnormals.autumnity.common.world.gen.feature;

import java.util.Random;

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

public class MapleTreeFeature extends Feature<BaseTreeFeatureConfig> {
	public MapleTreeFeature(Codec<BaseTreeFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random random, BlockPos position, BaseTreeFeatureConfig config) {
		int i = random.nextInt(2) + 5;

		boolean flag = true;
		if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getHeight()) {
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
						if (j >= 0 && j < worldIn.getHeight()) {
							if (!isAirOrLeaves(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
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
			} else if (isValidGround(worldIn, position.down()) && position.getY() < worldIn.getHeight() - i - 1) {
				setDirtAt(worldIn, position.down());

				for (int i2 = 0; i2 < 2; ++i2) {
					BlockPos blockpos = position.up(i - 1 - i2);

					for (BlockPos blockpos1 : BlockPos.getAllInBoxMutable(blockpos.add(-2, -1, -2), blockpos.add(2, 3, 2))) {
						double d0 = blockpos1.distanceSq(blockpos.getX(), blockpos.getY(), blockpos.getZ(), false);
						if (d0 <= (double) (2.35F * 2.35F) || (d0 <= (double) (2.5F * 2.5F) && random.nextInt(2) > 0)) {
							if (isAirOrLeaves(worldIn, blockpos1)) {
								this.placeLeafAt(worldIn, blockpos1, random, config);
							}
						}
					}
				}

				for (int i2 = 0; i2 < i; ++i2) {
					if (isAirOrLeaves(worldIn, position.up(i2))) {
						this.placeLogAt(worldIn, position.up(i2), random, config);
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
		this.setLogState(worldIn, pos, config.trunkProvider.getBlockState(rand, pos));
	}

	private void placeLeafAt(IWorldGenerationReader world, BlockPos pos, Random rand, BaseTreeFeatureConfig config) {
		if (isAirOrLeaves(world, pos)) {
			this.setLogState(world, pos, config.leavesProvider.getBlockState(rand, pos).with(LeavesBlock.DISTANCE, 1));
		}
	}

	protected final void setLogState(IWorldWriter worldIn, BlockPos pos, BlockState state) {
		worldIn.setBlockState(pos, state, 19);
	}

	public static boolean isAir(IWorldGenerationBaseReader worldIn, BlockPos pos) {
		if (!(worldIn instanceof net.minecraft.world.IBlockReader)) {
			return worldIn.hasBlockState(pos, BlockState::isAir);
		} else {
			return worldIn.hasBlockState(pos, state -> state.isAir((net.minecraft.world.IBlockReader) worldIn, pos));
		}
	}

	public static boolean isAirOrLeaves(IWorldGenerationBaseReader worldIn, BlockPos pos) {
		if (worldIn instanceof net.minecraft.world.IWorldReader) {
			return worldIn.hasBlockState(pos, state -> state.canBeReplacedByLeaves((net.minecraft.world.IWorldReader) worldIn, pos));
		}
		return worldIn.hasBlockState(pos, (state) -> {
			return state.isAir() || state.isIn(BlockTags.LEAVES);
		});
	}

	public static void setDirtAt(IWorld worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND) {
			worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState(), 18);
		}
	}

	public static boolean isValidGround(IWorld world, BlockPos pos) {
		return world.getBlockState(pos).canSustainPlant(world, pos, Direction.UP, (IPlantable) AutumnityBlocks.MAPLE_SAPLING.get());
	}
}