package com.minecraftabnormals.autumnity.common.world.gen.feature;

import java.util.Random;

import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class FallenLeavesMapleTreeFeature extends MapleTreeFeature
{
	public FallenLeavesMapleTreeFeature(Codec<BaseTreeFeatureConfig> config)
	{
		super(config);
	}

	public boolean func_230362_a_(ISeedReader worldIn, StructureManager manager, ChunkGenerator generator, Random random, BlockPos position, BaseTreeFeatureConfig config)
	{
		boolean flag = super.func_230362_a_(worldIn, manager, generator, random, position, config);

		if (flag && random.nextInt(6) != 0)
		{
			BlockState blockstate = config.leavesProvider.getBlockState(random, position);
			BlockState leafcarpet = null;

			if (blockstate == AutumnityBlocks.RED_MAPLE_LEAVES.get().getDefaultState())
			{
				leafcarpet = AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get().getDefaultState();
			}
			else if (blockstate == AutumnityBlocks.ORANGE_MAPLE_LEAVES.get().getDefaultState())
			{
				leafcarpet = AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get().getDefaultState();
			}
			else if (blockstate == AutumnityBlocks.YELLOW_MAPLE_LEAVES.get().getDefaultState())
			{
				leafcarpet = AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get().getDefaultState();
			}
			else
			{
				leafcarpet = AutumnityBlocks.MAPLE_LEAF_CARPET.get().getDefaultState();
			}

			for(int x = -3; x <= 3; ++x)
			{
				for(int z = -3; z <= 3; ++z)
				{
					if (Math.abs(x) < 2 || Math.abs(z) < 2) 
					{
						for(int y = -3; y <= 3; ++y)
						{
							BlockPos blockpos = position.add(x, y, z);
							if (random.nextInt(8) > 0 && worldIn.isAirBlock(blockpos) && blockpos.getY() < worldIn.getHeight() && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK)
							{
								worldIn.setBlockState(blockpos, leafcarpet, 2);
							}
						}
					}
				}
			}
		}

		return flag;
	}
}