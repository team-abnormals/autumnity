package com.markus1002.autumnity.common.item;

import com.markus1002.autumnity.core.registry.ModEffects;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModFoods
{
	public static final Food SYRUP_BOTTLE = (new Food.Builder()).hunger(4).saturation(0.3F).build();
	public static final Food FOUL_BERRIES = (new Food.Builder()).hunger(2).saturation(0.1F).fastToEat().effect(new EffectInstance(ModEffects.FOUL_TASTE, 1200, 0), 1.0F).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8F).build();
	public static final Food FOUL_BERRY_PIE = (new Food.Builder()).hunger(6).saturation(0.3F).effect(new EffectInstance(ModEffects.FOUL_TASTE, 1800, 1), 1.0F).build();
}