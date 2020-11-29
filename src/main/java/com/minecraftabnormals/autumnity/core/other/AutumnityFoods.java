package com.minecraftabnormals.autumnity.core.other;

import com.minecraftabnormals.autumnity.core.registry.AutumnityEffects;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class AutumnityFoods
{
	public static final Food SYRUP_BOTTLE = (new Food.Builder()).hunger(4).saturation(0.3F).build();
	public static final Food FOUL_BERRIES = (new Food.Builder()).hunger(1).saturation(0.1F).effect(() -> new EffectInstance(AutumnityEffects.FOUL_TASTE.get(), 320, 0), 1.0F).build();
	public static final Food PUMPKIN_BREAD = (new Food.Builder()).hunger(8).saturation(0.4F).build();
	public static final Food TURKEY = (new Food.Builder()).hunger(1).saturation(0.3F).meat().effect(() -> new EffectInstance(Effects.HUNGER, 600, 0), 0.1F).build();
	public static final Food COOKED_TURKEY = (new Food.Builder()).hunger(2).saturation(0.6F).meat().build();
}