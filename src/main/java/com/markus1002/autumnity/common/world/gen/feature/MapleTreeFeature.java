package com.markus1002.autumnity.common.world.gen.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class MapleTreeFeature extends AbstractTreeFeature<NoFeatureConfig>
{
	private final Supplier<BlockState> trunk;
	private final Supplier<BlockState> leaf;

	public MapleTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn, boolean doBlockNotifyIn, Supplier<Block> trunkIn, Supplier<Block> leafIn, Supplier<Block> sapling)
	{
		super(configFactoryIn, doBlockNotifyIn);
		this.trunk = () -> trunkIn.get().getDefaultState();
		this.leaf = () -> leafIn.get().getDefaultState();
	    this.setSapling((net.minecraftforge.common.IPlantable)sapling.get());
	}

	public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox p_208519_5_)
	{
		int i = rand.nextInt(2) + 5;

		boolean flag = true;
		if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxHeight())
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

				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

				for(int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
				{
					for(int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
					{
						if (j >= 0 && j < worldIn.getMaxHeight()) {
							if (!func_214587_a(worldIn, blockpos$mutableblockpos.setPos(l, j, i1)))
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
			else if ((isSoil(worldIn, position.down(), getSapling())) && position.getY() < worldIn.getMaxHeight() - i - 1)
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
							if (isAirOrLeaves(worldIn, blockpos1) || func_214576_j(worldIn, blockpos1))
							{
								this.setLogState(changedBlocks, worldIn, blockpos1, this.leaf.get(), p_208519_5_);
							}
						}
					}
				}

				for(int i2 = 0; i2 < i; ++i2)
				{
					if (isAirOrLeaves(worldIn, position.up(i2)))
					{
						this.setLogState(changedBlocks, worldIn, position.up(i2), this.trunk.get(), p_208519_5_);
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
}