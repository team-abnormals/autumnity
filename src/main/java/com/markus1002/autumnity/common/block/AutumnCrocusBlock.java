package com.markus1002.autumnity.common.block;

import com.google.common.base.Supplier;

import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;

/* 
 * Custom Flower class to avoid NullPointerException
 * Will likely be added to AbnormalsCore in the future
 */
public class AutumnCrocusBlock extends FlowerBlock 
{
	private final Supplier<Effect> stewEffect;
	private final int stewEffectDuration;

	public AutumnCrocusBlock(Supplier<Effect> stewEffect, int stewEffectDuration, Properties properties) 
	{
		// Dummy effect to avoid NullPointerException
		super(Effects.BLINDNESS, stewEffectDuration, properties);
		this.stewEffect = stewEffect;
		this.stewEffectDuration = stewEffectDuration;
	}

	@Override
	public Effect getStewEffect() 
	{
		return this.stewEffect.get();
	}

	@Override
	public int getStewEffectDuration() 
	{
		return this.stewEffectDuration;
	}
}
