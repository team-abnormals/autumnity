package com.teamabnormals.autumnity.core.mixin;

import com.teamabnormals.autumnity.common.entity.animal.Turkey;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.autumnity.core.registry.AutumnitySoundEvents;
import com.teamabnormals.incubation.core.api.EggLayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(Turkey.class)
public abstract class TurkeyMixin extends Animal implements EggLayer {

	@Shadow
	public int timeUntilNextEgg;

	@Shadow
	public boolean turkeyJockey;

	protected TurkeyMixin(EntityType<? extends Animal> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	public int getEggTimer() {
		return this.timeUntilNextEgg;
	}

	@Override
	public void setEggTimer(int time) {
		this.timeUntilNextEgg = time;
	}

	@Override
	public boolean isBirdJockey() {
		return this.turkeyJockey;
	}

	@Override
	public Item getEggItem() {
		return AutumnityItems.TURKEY_EGG.get();
	}

	@Override
	public int getNextEggTime(Random rand) {
		return ((Turkey) (Object) this).getRandomNextEggTime(rand);
	}

	@Override
	public SoundEvent getEggLayingSound() {
		return AutumnitySoundEvents.ENTITY_TURKEY_EGG.get();
	}
}