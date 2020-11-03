package com.markus1002.autumnity.common.block;

import java.util.function.Supplier;

import com.teamabnormals.abnormals_core.common.blocks.AbnormalsFlowerBlock;

import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;

public class AutumnCrocusBlock extends AbnormalsFlowerBlock 
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