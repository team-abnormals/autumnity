package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.core.other.AutumnityEvents;
import com.minecraftabnormals.autumnity.core.other.AutumnityFoods;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEffects;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
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

		int i = AutumnityFoods.COOKED_TURKEY.getHealing();
		int j = i == 1 ? i : (int) (i * 0.5F);

		if (player.isPotionActive(AutumnityEffects.FOUL_TASTE.get()))
		{
			player.getFoodStats().addStats(j, 0.0F);
			AutumnityEvents.updateFoulTaste(player);
		}
	}

	@Override
	protected Item getLeg()
	{
		return AutumnityItems.COOKED_TURKEY_LEG.get();
	}
}