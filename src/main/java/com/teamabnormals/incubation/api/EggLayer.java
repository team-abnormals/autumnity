package com.teamabnormals.incubation.api;

import net.minecraft.world.item.Item;
import net.minecraft.sounds.SoundEvent;

import java.util.Random;

public interface EggLayer {
	int getEggTimer();

	void setEggTimer(int time);

	boolean isBirdJockey();

	Item getEggItem();

	int getNextEggTime(Random rand);

	SoundEvent getEggLayingSound();
}
