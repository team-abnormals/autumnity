package com.markus1002.autumnity.common.block;

import com.markus1002.autumnity.core.other.AutumnityFoods;
import com.markus1002.autumnity.core.registry.AutumnityItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
		player.getFoodStats().addStats(AutumnityFoods.COOKED_TURKEY.getHealing(), AutumnityFoods.COOKED_TURKEY.getSaturation());
	}
	
	@Override
	protected Item getLeg()
	{
		return AutumnityItems.COOKED_TURKEY_LEG.get();
	}
}