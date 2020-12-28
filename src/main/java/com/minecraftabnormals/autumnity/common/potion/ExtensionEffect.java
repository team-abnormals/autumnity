package com.minecraftabnormals.autumnity.common.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.InstantEffect;

import javax.annotation.Nullable;

public class ExtensionEffect extends InstantEffect {
	public ExtensionEffect() {
		super(EffectType.BENEFICIAL, 16767620);
	}

	public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
		for (EffectInstance effect : entityLivingBaseIn.getActivePotionEffects()) {
			if (effect.getDuration() > 10) {
				entityLivingBaseIn.addPotionEffect(new EffectInstance(effect.getPotion(), effect.getDuration() + 180 + 180 * (amplifier + 1), effect.getAmplifier(), effect.isAmbient(), effect.doesShowParticles(), effect.isShowIcon()));
			}
		}
	}
}