package com.markus1002.autumnity.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.extensions.IForgeBlock;

public class BookshelfBlock extends Block implements IForgeBlock
{
	public BookshelfBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos)
	{
		return 1;
	}
}