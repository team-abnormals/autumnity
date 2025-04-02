package com.teamabnormals.autumnity.common.entity.ai.goal;

import com.teamabnormals.autumnity.common.entity.animal.Snail;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class SnailEatMooshroomMushroomsGoal extends Goal {
	private final Snail snail;
	private MushroomCow targetMooshroom;
	private int timeToRecalcPath;

	public SnailEatMooshroomMushroomsGoal(Snail snail) {
		super();
		this.snail = snail;
	}

	@Override
	public boolean canUse() {
		if (!this.snail.isBaby() && !this.snail.hasSnack() && this.snail.getGooAmount() <= 0) {
			List<MushroomCow> list = this.snail.level().getEntitiesOfClass(MushroomCow.class, this.snail.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
			MushroomCow mooshroom = null;
			double d0 = Double.MAX_VALUE;

			for (MushroomCow mooshroom1 : list) {
				if (mooshroom1.getAge() >= 0) {
					double d1 = this.snail.distanceToSqr(mooshroom1);
					if (!(d1 > d0)) {
						d0 = d1;
						mooshroom = mooshroom1;
					}
				}
			}

			if (mooshroom == null) {
				return false;
			} else {
				this.targetMooshroom = mooshroom;
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean canContinueToUse() {
		if (!this.targetMooshroom.isAlive()) {
			return false;
		} else if (this.snail.hasSnack()) {
			return false;
		} else if (this.snail.getGooAmount() > 0) {
			return false;
		} else {
			double d0 = this.targetMooshroom.distanceToSqr(this.snail);
			return !(d0 > 256.0D);
		}
	}

	@Override
	public void start() {
		this.timeToRecalcPath = 0;
	}

	@Override
	public void stop() {
		this.targetMooshroom = null;
	}

	@Override
	public void tick() {
		if (--this.timeToRecalcPath <= 0) {
			this.timeToRecalcPath = this.adjustedTickDelay(10);
			this.snail.getNavigation().moveTo(this.targetMooshroom, 0.5D);
		}

		if (this.targetMooshroom != null && this.targetMooshroom.isAlive()) {
			double d0 = this.targetMooshroom.distanceToSqr(this.snail);
			if (d0 < 2.0D) {
				if (this.targetMooshroom.getVariant() == MushroomCow.MushroomType.BROWN) {
					this.snail.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BROWN_MUSHROOM, 1));
				} else {
					this.snail.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.RED_MUSHROOM, 1));
				}

				this.targetMooshroom.hurt(this.snail.damageSources().mobAttack(this.snail), 0.0F);
			}
		}
	}
}