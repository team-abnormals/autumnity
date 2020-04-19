package com.markus1002.autumnity.common.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModFoods
{
	public static final Food SYRUP_BOTTLE = (new Food.Builder()).hunger(4).saturation(0.3F).build();
	public static final Food FOUL_BERRIES = (new Food.Builder()).hunger(2).saturation(0.1F).effect(new EffectInstance(Effects.HUNGER, 300, 0), 0.8F).build();
	public static final Food FOUL_BERRY_PIE = (new Food.Builder()).hunger(8).saturation(0.3F).effect(new EffectInstance(Effects.HUNGER, 300, 0), 0.3F).build();
}