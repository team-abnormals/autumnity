package com.teamabnormals.autumnity.common.block;

import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.PushReaction;

public abstract class AbstractLargePumpkinSliceBlock extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

	public AbstractLargePumpkinSliceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, Half.BOTTOM));
	}

	protected static Direction getFacing(BlockPlaceContext context) {
		float f = Mth.wrapDegrees(context.getRotation()) / 45;

		if (f > -2 && f <= 0) {
			return Direction.NORTH;
		} else if (f > 0 && f <= 2) {
			return Direction.EAST;
		} else if (f > 2) {
			return Direction.SOUTH;
		} else {
			return Direction.WEST;
		}
	}

	protected static boolean canCarve(Direction hitFace, Direction facing) {
		return hitFace == facing || hitFace == facing.getCounterClockWise();
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		Direction.Axis axis = state.getValue(FACING).getAxis();

		if (mirrorIn != Mirror.NONE) {
			if ((mirrorIn == Mirror.FRONT_BACK && axis == Direction.Axis.X) || (mirrorIn == Mirror.LEFT_RIGHT && axis == Direction.Axis.Z)) {
				return state.rotate(Rotation.COUNTERCLOCKWISE_90);
			} else {
				return state.rotate(Rotation.CLOCKWISE_90);
			}
		} else {
			return state;
		}
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
		return PushReaction.NORMAL;
	}
}