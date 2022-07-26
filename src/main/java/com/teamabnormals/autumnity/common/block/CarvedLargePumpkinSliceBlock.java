package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.block.properties.CarvedSide;
import com.teamabnormals.autumnity.core.other.JackOLanternHelper;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.BlockHitResult;

public class CarvedLargePumpkinSliceBlock extends AbstractLargePumpkinSliceBlock {
	public static final EnumProperty<CarvedSide> CARVED_SIDE = AutumnityBlockStateProperties.CARVED_SIDE;

	public CarvedLargePumpkinSliceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CARVED_SIDE, CarvedSide.X));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF, CARVED_SIDE);
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
					blockstate.setValue(CarvedLargePumpkinSliceBlock.FACING, state.getValue(FACING)).setValue(CarvedLargePumpkinSliceBlock.HALF, state.getValue(HALF)).setValue(CarvedLargePumpkinSliceBlock.CARVED_SIDE, state.getValue(CARVED_SIDE));
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

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockpos = context.getClickedPos();
		BlockState bottomblock = context.getLevel().getBlockState(blockpos.below());
		BlockState topblock = context.getLevel().getBlockState(blockpos.above());
		CarvedSide carvedside = context.getHorizontalDirection().getAxis() == Axis.X ? CarvedSide.X : CarvedSide.Z;

		if (bottomblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && bottomblock.getValue(HALF) == Half.BOTTOM) {
			return this.defaultBlockState().setValue(FACING, bottomblock.getValue(FACING)).setValue(HALF, Half.TOP).setValue(CARVED_SIDE, carvedside);
		} else if (topblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && topblock.getValue(HALF) == Half.TOP) {
			return this.defaultBlockState().setValue(FACING, topblock.getValue(FACING)).setValue(HALF, Half.BOTTOM).setValue(CARVED_SIDE, carvedside);
		}

		return this.defaultBlockState().setValue(FACING, getFacing(context)).setValue(HALF, Mth.sin(context.getPlayer().getViewXRot(1.0F) * ((float) Math.PI / 180F)) > 0 ? Half.BOTTOM : Half.TOP).setValue(CARVED_SIDE, carvedside);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		if (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) {
			return super.rotate(state, rot).setValue(CARVED_SIDE, state.getValue(CARVED_SIDE) == CarvedSide.X ? CarvedSide.Z : CarvedSide.X);
		}

		return super.rotate(state, rot);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
}