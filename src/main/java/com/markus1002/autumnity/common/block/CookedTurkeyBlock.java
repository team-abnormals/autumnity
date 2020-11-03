package com.markus1002.autumnity.common.block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.IWorld;

public class CookedTurkeyBlock extends TurkeyBlock
{
	public CookedTurkeyBlock(Properties builder)
	{
		super(builder);
	}
	
	@Override
	protected void restoreHunger(IWorld worldIn, PlayerEntity player)
	{
		player.getFoodStats().addStats(2, 0.6F);
	}
}