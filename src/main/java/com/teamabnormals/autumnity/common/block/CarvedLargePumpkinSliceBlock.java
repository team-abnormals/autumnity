package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.block.properties.CarvedSide;
import com.teamabnormals.autumnity.common.block.util.JackOLanternUtil;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CarvedLargePumpkinSliceBlock extends LargeJackOLanternSliceBlock {
	public CarvedLargePumpkinSliceBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		Block jackolantern = JackOLanternUtil.getLargeJackOLantern(stack);

		if (jackolantern instanceof LargeJackOLanternSliceBlock) {
			Direction hitface = hit.getDirection();
			Direction facing = state.getValue(FACING);
			CarvedSide carvedside = state.getValue(CARVED_SIDE);

			if (canCarve(hitface, facing) && (hitface.getAxis() == Axis.X && carvedside == CarvedSide.X || hitface.getAxis() == Axis.Z && carvedside == CarvedSide.Z)) {
				if (!level.isClientSide()) {
					BlockState blockstate = jackolantern.withPropertiesOf(state);
					if (jackolantern == AutumnityBlocks.LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get()) {
						boolean powered = blockstate.getValue(POWERED);
						blockstate = blockstate.setValue(RedstoneJackOLanternBlock.LIT, powered || isAnotherSlicePowered(level, blockstate, pos));
					}

					level.setBlock(pos, blockstate, 11);
					level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
				}

				if (!player.getAbilities().instabuild) stack.shrink(1);
				return ItemInteractionResult.sidedSuccess(level.isClientSide());
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}
}