package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.block.properties.CarvedSide;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;

public class LargeJackOLanternSliceBlock extends AbstractLargePumpkinSliceBlock {
	public static final EnumProperty<CarvedSide> CARVED_SIDE = AutumnityBlockStateProperties.CARVED_SIDE;

	public LargeJackOLanternSliceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CARVED_SIDE, CarvedSide.X));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF, CARVED_SIDE);
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