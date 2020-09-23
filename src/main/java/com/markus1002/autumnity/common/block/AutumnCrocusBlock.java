package com.markus1002.autumnity.common.block;

import com.google.common.base.Supplier;
import com.teamabnormals.abnormals_core.common.blocks.AbnormalsFlowerBlock;

import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;

public class AutumnCrocusBlock extends AbnormalsFlowerBlock 
{
	private final Supplier<Effect> stewEffect;

	public AutumnCrocusBlock(Supplier<Effect> stewEffect, int stewEffectDuration, Properties properties) 
	{
		super(Effects.BLINDNESS, stewEffectDuration, properties);
		this.stewEffect = stewEffect;
	}

	public Effect getStewEffect() 
	{
		return this.stewEffect.get();
	}
}
