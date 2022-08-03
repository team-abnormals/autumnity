package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.block.properties.CarvedSide;
import com.teamabnormals.autumnity.core.other.JackOLanternHelper;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		Block jackolantern = JackOLanternHelper.getLargeJackOLantern(itemstack.getItem());

		if (jackolantern != null) {
			Direction hitface = hit.getDirection();
			Direction facing = state.getValue(FACING);
			CarvedSide carvedside = state.getValue(CARVED_SIDE);

			if (canCarve(hitface, facing) && (hitface.getAxis() == Axis.X && carvedside == CarvedSide.X || hitface.getAxis() == Axis.Z && carvedside == CarvedSide.Z)) {
				if (!worldIn.isClientSide) {
					BlockState blockstate = jackolantern == AutumnityBlocks.LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get() ? jackolantern.defaultBlockState().setValue(RedstoneJackOLanternBlock.LIT, worldIn.hasNeighborSignal(pos)) : jackolantern.defaultBlockState();
					blockstate = blockstate.setValue(FACING, state.getValue(FACING)).setValue(HALF, state.getValue(HALF)).setValue(CARVED_SIDE, state.getValue(CARVED_SIDE));
					worldIn.setBlock(pos, blockstate, 11);

					worldIn.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
					if (!player.getAbilities().instabuild) {
						itemstack.shrink(1);
					}
				}

				return InteractionResult.sidedSuccess(worldIn.isClientSide);
			}
		}

		return super.use(state, worldIn, pos, player, handIn, hit);
	}
}