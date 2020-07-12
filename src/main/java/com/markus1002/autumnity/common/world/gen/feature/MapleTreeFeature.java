package com.markus1002.autumnity.common.world.gen.feature;

import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class MapleTreeFeature extends Feature<BaseTreeFeatureConfig>
{
	public MapleTreeFeature(Codec<BaseTreeFeatureConfig> config)
	{
		super(config);
	}

	@Override
	public boolean func_230362_a_(ISeedReader worldIn, StructureManager manager, ChunkGenerator generator, Random rand, BlockPos position, BaseTreeFeatureConfig config)
	{
		int i = rand.nextInt(2) + 5;

		boolean flag = true;
		
		if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getHeight())
		{
			for(int j = position.getY(); j <= position.getY() + 1 + i; ++j)
			{
				int k = 1;
				if (j == position.getY())
				{
					k = 0;
				}

				if (j >= position.getY() + 1 + i - 2)
				{
					k = 2;
				}

				BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

				for(int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
				{
					for(int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
					{
						if (j >= 0 && j < worldIn.getHeight())
						{
							if (!func_236410_c_(worldIn, blockpos$mutableblockpos.setPos(l, j, i1)))
							{
								flag = false;
							}
						}
						else
						{
							flag = false;
						}
					}
				}
			}

			if (!flag)
			{
				return false;
			}
			else if ((isSoil(worldIn, position.down(), config.getSapling())) && position.getY() < worldIn.getMaxHeight() - i - 1)
			{
				this.setDirtAt(worldIn, position.down(), position);

				for(int i2 = 0; i2 < 2; ++i2)
				{
					BlockPos blockpos = position.up(i - 1 - i2);

					for(BlockPos blockpos1 : BlockPos.getAllInBoxMutable(blockpos.add(-2, -1, -2), blockpos.add(2, 3, 2)))
					{
						double d0 = blockpos1.distanceSq(blockpos.getX(), blockpos.getY(), blockpos.getZ(), false);
						if (d0 <= (double)(2.35F * 2.35F) || (d0 <= (double)(2.5F * 2.5F) && rand.nextInt(2) > 0))
						{
							if (isAirOrLeaves(worldIn, blockpos1))
							{
								this.setLogState(changedBlocks, worldIn, blockpos1, config.leavesProvider.getBlockState(rand, position), p_208519_5_);
							}
						}
					}
				}

				for(int i2 = 0; i2 < i; ++i2)
				{
					if (isAirOrLeaves(worldIn, position.up(i2)))
					{
						this.setLogState(changedBlocks, worldIn, position.up(i2), config.trunkProvider.getBlockState(rand, position), p_208519_5_);
					}
				}

				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	protected final void setLogState(Set<BlockPos> changedBlocks, IWorldWriter worldIn, BlockPos pos, BlockState p_208520_4_, MutableBoundingBox p_208520_5_)
	{

		this.func_208521_b(worldIn, pos, p_208520_4_);
		p_208520_5_.expandTo(new MutableBoundingBox(pos, pos));
		if (BlockTags.LOGS.contains(p_208520_4_.getBlock()))
		{

			changedBlocks.add(pos.toImmutable());
		}
	}

	private void func_208521_b(IWorldWriter p_208521_1_, BlockPos p_208521_2_, BlockState p_208521_3_)
	{
		p_208521_1_.setBlockState(p_208521_2_, p_208521_3_, 18);
	}
}