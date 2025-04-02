package com.teamabnormals.autumnity.common.entity.ai.goal;

import com.teamabnormals.autumnity.common.entity.animal.Snail;
import com.teamabnormals.autumnity.common.entity.animal.Snail.Action;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SnailEatGoal extends Goal {
	private final Snail snail;
	private int eatTime;

	public SnailEatGoal(Snail snail) {
		super();
		this.snail = snail;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
	}

	@Override
	public boolean canUse() {
		return this.snail.hasSnack();
	}

	@Override
	public void start() {
		this.eatTime = this.adjustedTickDelay(192);
		this.snail.getNavigation().stop();
		this.snail.getMoveControl().setWantedPosition(this.snail.getX(), this.snail.getY(), this.snail.getZ(), 0.0D);
		this.snail.setAction(Action.EATING);
	}

	@Override
	public void stop() {
		this.snail.setAction(Action.DEFAULT);
	}

	@Override
	public void tick() {
		--this.eatTime;

		if (this.eatTime <= 0) {
			this.snail.eatSnack();
		}
	}

	@Override
	public boolean canContinueToUse() {
		return this.snail.hasSnack();
	}
}