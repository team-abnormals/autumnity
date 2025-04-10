package com.teamabnormals.autumnity.common.entity.ai.goal;

import com.teamabnormals.autumnity.common.entity.animal.Snail;
import com.teamabnormals.autumnity.common.entity.animal.Snail.Action;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;
import java.util.function.Predicate;

public class SnailHideGoal extends Goal {
	private static final Predicate<LivingEntity> ENEMY_MATCHER = (livingentity) -> {
		if (livingentity == null) {
			return false;
		} else {
			if (livingentity.getItemBySlot(EquipmentSlot.CHEST).is(AutumnityItems.SNAIL_SHELL_CHESTPLATE.get())) {
				return false;
			} else if (livingentity instanceof Player player) {
				return !player.isSteppingCarefully() && !livingentity.isSpectator() && !player.isCreative();
			} else {
				return !(livingentity instanceof Snail) && !(livingentity instanceof MushroomCow);
			}
		}
	};

	private final Snail snail;

	public SnailHideGoal(Snail snail) {
		super();
		this.snail = snail;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
	}

	@Override
	public boolean canUse() {
		return this.snail.getHidingTime() > 0 || this.snail.getLastHurtByMob() != null || this.shouldHideFromMob();
	}

	@Override
	public void start() {
		this.hide();
		this.snail.getNavigation().stop();
		this.snail.getMoveControl().setWantedPosition(this.snail.getX(), this.snail.getY(), this.snail.getZ(), 0.0D);
		this.snail.setAction(Action.HIDING);
	}

	@Override
	public void stop() {
		this.snail.setAction(Action.DEFAULT);
	}

	@Override
	public void tick() {
		if ((this.snail.getLastHurtByMob() != null || this.shouldHideFromMob()) && this.snail.getHidingTime() < 120) {
			this.hide();
		} else {
			this.snail.setHidingTime(this.snail.getHidingTime() - 1);
		}
	}

	@Override
	public boolean canContinueToUse() {
		return this.snail.getHidingTime() > 0;
	}

	private void hide() {
		this.snail.setHidingTime(120 + this.snail.getRandom().nextInt(120));
	}

	private boolean shouldHideFromMob() {
		for (LivingEntity livingentity : this.snail.level().getEntitiesOfClass(LivingEntity.class, this.snail.getBoundingBox().inflate(0.5D), ENEMY_MATCHER)) {
			if (livingentity.isAlive() && livingentity.getBbHeight() > this.snail.getBbHeight()) {
				return true;
			}
		}

		return false;
	}
}