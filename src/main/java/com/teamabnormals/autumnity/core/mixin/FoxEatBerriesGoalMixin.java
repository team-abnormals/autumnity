package com.teamabnormals.autumnity.core.mixin;

import com.teamabnormals.autumnity.common.block.FoulBerryBush;
import com.teamabnormals.autumnity.common.block.TallFoulBerryBushBlock;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Fox.FoxEatBerriesGoal.class)
public abstract class FoxEatBerriesGoalMixin extends MoveToBlockGoal {

	public FoxEatBerriesGoalMixin(PathfinderMob mob, double speedModifier, int searchRange) {
		super(mob, speedModifier, searchRange);
	}

	@Inject(at = @At("HEAD"), method = "onReachedTarget")
	private void onReachedTarget(CallbackInfo ci) {
		Fox fox = (Fox) this.mob;
		Level level = fox.level();
		if (EventHooks.canEntityGrief(level, fox)) {
			BlockState state = level.getBlockState(this.blockPos);
			if (FoulBerryBush.hasBerries(state)) {
				int age = state.getValue(TallFoulBerryBushBlock.AGE);
				int berryCount = 2;

				ItemStack stack = fox.getItemBySlot(EquipmentSlot.MAINHAND);
				if (stack.isEmpty()) {
					fox.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AutumnityItems.FOUL_BERRIES.get()));
					berryCount--;
				}
				if (berryCount > 0) {
					Block.popResource(level, this.blockPos, new ItemStack(AutumnityItems.FOUL_BERRIES.get(), berryCount));
				}

				fox.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);

				BlockState newState = state.setValue(TallFoulBerryBushBlock.AGE, age - 1);
				level.setBlock(this.blockPos, newState, 2);
				TallFoulBerryBushBlock.setHalfState(level, this.blockPos, state, age - 1);

				level.gameEvent(GameEvent.BLOCK_CHANGE, this.blockPos, GameEvent.Context.of(fox));
			}
		}
	}

	@Inject(at = @At("RETURN"), method = "isValidTarget", cancellable = true)
	private void isValidTarget(LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (FoulBerryBush.hasBerries(level.getBlockState(pos))) {
			cir.setReturnValue(true);
		}
	}
}