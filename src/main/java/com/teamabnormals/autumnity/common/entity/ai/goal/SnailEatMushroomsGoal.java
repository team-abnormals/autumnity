package com.teamabnormals.autumnity.common.entity.ai.goal;

import com.teamabnormals.autumnity.common.entity.animal.Snail;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SnailEatMushroomsGoal extends Goal {
	private final Snail snail;
	private double mushroomX;
	private double mushroomY;
	private double mushroomZ;

	public SnailEatMushroomsGoal(Snail snail) {
		super();
		this.snail = snail;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if (this.snail.getRandom().nextInt(20) != 0) {
			return false;
		} else {
			return !this.snail.isBaby() && !this.snail.hasSnack() && this.snail.getGooAmount() <= 0 && EventHooks.canEntityGrief(this.snail.level(), this.snail) && this.canMoveToMushroom();
		}
	}

	@Override
	public boolean canContinueToUse() {
		return !this.snail.getNavigation().isDone() && !this.snail.hasSnack() && this.snail.getGooAmount() <= 0;
	}

	@Override
	public void start() {
		this.snail.getNavigation().moveTo(this.mushroomX, this.mushroomY, this.mushroomZ, 0.5D);
	}

	@Override
	public void tick() {
		if (!this.snail.isBaby() && this.snail.getGooAmount() <= 0) {
			BlockPos blockpos = this.snail.blockPosition();

			if (this.isBlockMushroom(blockpos)) {
				if (EventHooks.canEntityGrief(this.snail.level(), this.snail)) {
					this.snail.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(this.snail.level().getBlockState(blockpos).getBlock().asItem(), 1));
					this.snail.level().destroyBlock(blockpos, false);
				}
			}
		}
	}

	@Nullable
	private Vec3 findMushroom() {
		RandomSource random = this.snail.getRandom();
		BlockPos blockpos = BlockPos.containing(this.snail.getX(), this.snail.getBoundingBox().minY, this.snail.getZ());

		for (int i = 0; i < 10; ++i) {
			BlockPos blockpos1 = blockpos.offset(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
			if (this.isBlockMushroom(blockpos1)) {
				return new Vec3(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
			}
		}

		return null;
	}

	private boolean canMoveToMushroom() {
		Vec3 vec3d = this.findMushroom();
		if (vec3d == null) {
			return false;
		} else {
			this.mushroomX = vec3d.x;
			this.mushroomY = vec3d.y;
			this.mushroomZ = vec3d.z;
			return true;
		}
	}

	private boolean isBlockMushroom(BlockPos pos) {
		return this.snail.level().getBlockState(pos).is(AutumnityBlockTags.SNAIL_SNACKS);
	}
}