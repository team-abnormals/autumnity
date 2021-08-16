package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.common.block.properties.CarvedSide;
import com.minecraftabnormals.autumnity.core.other.JackOLanternHelper;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.Half;
import net.minecraft.util.*;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CarvedLargePumpkinSliceBlock extends AbstractLargePumpkinSliceBlock {
	public static final EnumProperty<CarvedSide> CARVED_SIDE = AutumnityBlockStateProperties.CARVED_SIDE;

	public CarvedLargePumpkinSliceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CARVED_SIDE, CarvedSide.X));
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF, CARVED_SIDE);
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		Block jackolantern = JackOLanternHelper.getLargeJackOLantern(itemstack.getItem());

		if (jackolantern != null) {
			Direction direction = hit.getDirection();
			Direction direction1 = state.getValue(FACING);
			CarvedSide carvedside = state.getValue(CARVED_SIDE);

			if (canCarve(direction, direction1) && (direction.getAxis() == Axis.X && carvedside == CarvedSide.X || direction.getAxis() == Axis.Z && carvedside == CarvedSide.Z)) {
				if (!worldIn.isClientSide) {
					BlockState blockstate = jackolantern == AutumnityBlocks.LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get() ? jackolantern.defaultBlockState().setValue(RedstoneJackOLanternBlock.LIT, worldIn.hasNeighborSignal(pos)) : jackolantern.defaultBlockState();
					BlockState blockstate1 = blockstate.setValue(CarvedLargePumpkinSliceBlock.FACING, state.getValue(FACING)).setValue(CarvedLargePumpkinSliceBlock.HALF, state.getValue(HALF)).setValue(CarvedLargePumpkinSliceBlock.CARVED_SIDE, state.getValue(CARVED_SIDE));
					worldIn.setBlock(pos, blockstate1, 11);

					worldIn.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					if (!player.abilities.instabuild) {
						itemstack.shrink(1);
					}
				}

				return ActionResultType.sidedSuccess(worldIn.isClientSide);
			}
		}

		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getClickedPos();
		BlockState bottomblock = context.getLevel().getBlockState(blockpos.below());
		BlockState topblock = context.getLevel().getBlockState(blockpos.above());
		CarvedSide carvedside = context.getHorizontalDirection().getAxis() == Axis.X ? CarvedSide.X : CarvedSide.Z;

		if (bottomblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && bottomblock.getValue(HALF) == Half.BOTTOM) {
			return this.defaultBlockState().setValue(FACING, bottomblock.getValue(FACING)).setValue(HALF, Half.TOP).setValue(CARVED_SIDE, carvedside);
		} else if (topblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && topblock.getValue(HALF) == Half.TOP) {
			return this.defaultBlockState().setValue(FACING, topblock.getValue(FACING)).setValue(HALF, Half.BOTTOM).setValue(CARVED_SIDE, carvedside);
		}

		return this.defaultBlockState().setValue(FACING, getFacing(context)).setValue(HALF, MathHelper.sin(context.getPlayer().getViewXRot(1.0F) * ((float) Math.PI / 180F)) > 0 ? Half.BOTTOM : Half.TOP).setValue(CARVED_SIDE, carvedside);
	}

	public BlockState rotate(BlockState state, Rotation rot) {
		if (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90) {
			return super.rotate(state, rot).setValue(CARVED_SIDE, state.getValue(CARVED_SIDE) == CarvedSide.X ? CarvedSide.Z : CarvedSide.X);
		}

		return super.rotate(state, rot);
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
}