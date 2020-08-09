package com.markus1002.autumnity.core.other;

import com.markus1002.autumnity.core.registry.AutumnityEffects;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;

public class AutumnityFoods
{
	public static final Food SYRUP_BOTTLE = (new Food.Builder()).hunger(4).saturation(0.3F).build();
	public static final Food FOUL_BERRIES = (new Food.Builder()).hunger(1).saturation(0.1F).effect(new EffectInstance(AutumnityEffects.FOUL_TASTE, 320, 0), 1.0F).build();
}