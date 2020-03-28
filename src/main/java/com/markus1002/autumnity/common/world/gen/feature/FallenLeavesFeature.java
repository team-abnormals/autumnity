package com.markus1002.autumnity.common.world.gen.feature;

import java.util.Random;
import java.util.function.Function;

import com.markus1002.autumnity.core.registry.ModBlocks;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class FallenLeavesFeature extends Feature<NoFeatureConfig>
{
	public FallenLeavesFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn)
	{
		super(configFactoryIn);
	}

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
	{
		int i = 0;
		BlockState blockstate = rand.nextInt(4) > 0 ? ModBlocks.ORANGE_MAPLE_LEAF_CARPET.get().getDefaultState() : ModBlocks.YELLOW_MAPLE_LEAF_CARPET.get().getDefaultState();

		for(int x = -3; x <= 3; ++x)
		{
			for(int z = -3; z <= 3; ++z)
			{
				if (Math.abs(x) < 2 || Math.abs(z) < 2) 
				{
					for(int y = -3; y <= 3; ++y)
					{
						BlockPos blockpos = pos.add(x, y, z);
						if (rand.nextInt(3) > 0 && worldIn.isAirBlock(blockpos) && blockpos.getY() < worldIn.getWorld().getDimension().getHeight() && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK)
						{
							worldIn.setBlockState(blockpos, blockstate, 2);
							++i;
						}
					}
				}
			}
		}

		return i > 0;
	}
}