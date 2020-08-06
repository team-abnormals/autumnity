package com.markus1002.autumnity.common.item;

import com.markus1002.autumnity.core.registry.ModEffects;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;

public class ModFoods
{
	public static final Food SYRUP_BOTTLE = (new Food.Builder()).hunger(4).saturation(0.3F).build();
	public static final Food FOUL_BERRIES = (new Food.Builder()).hunger(1).saturation(0.1F).effect(new EffectInstance(ModEffects.FOUL_TASTE, 1200, 0), 1.0F).build();
	public static final Food FOUL_BERRY_PIE = (new Food.Builder()).hunger(4).saturation(0.3F).effect(new EffectInstance(ModEffects.FOUL_TASTE, 1800, 1), 1.0F).build();
}