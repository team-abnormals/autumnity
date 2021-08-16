package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.core.other.AutumnityEvents;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PancakeBlock extends Block {
	public static final IntegerProperty PANCAKES = IntegerProperty.create("pancakes", 1, 32);
	private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)};

	public PancakeBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(PANCAKES, Integer.valueOf(2)));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int i = Math.floorDiv(state.getValue(PANCAKES) - 1, 2);
		return SHAPES[i];
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
		if (blockstate.getBlock() == this) {
			return blockstate.setValue(PANCAKES, Integer.valueOf(Math.min(32, blockstate.getValue(PANCAKES) + 2)));
		} else {
			BlockState blockstate1 = context.getLevel().getBlockState(context.getClickedPos().below());
			if (blockstate1.getBlock() == this && blockstate1.getValue(PANCAKES) == 31) {
				context.getLevel().setBlock(context.getClickedPos().below(), blockstate1.setValue(PANCAKES, Integer.valueOf(32)), 11);
				return this.defaultBlockState().setValue(PANCAKES, Integer.valueOf(1));
			} else {
				return super.getStateForPlacement(context);
			}
		}
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);

		if (player.isShiftKeyDown()) {
			if (worldIn.getBlockState(pos.above()).getBlock() != this) {
				if (state.getValue(PANCAKES) > 1) {
					if (!worldIn.isClientSide) {
						int i = state.getValue(PANCAKES);

						popResource(worldIn, pos, new ItemStack(this.asItem()));

						if (i > 2) {
							worldIn.setBlock(pos, state.setValue(PANCAKES, Integer.valueOf(i - 2)), 3);
						} else {
							worldIn.removeBlock(pos, false);
						}
					}
					return ActionResultType.SUCCESS;
				} else if (worldIn.getBlockState(pos.below()).getBlock() == this && worldIn.getBlockState(pos.below()).getValue(PANCAKES) == 32) {
					if (!worldIn.isClientSide) {
						popResource(worldIn, pos, new ItemStack(this.asItem()));

						worldIn.setBlock(pos.below(), state.setValue(PANCAKES, Integer.valueOf(31)), 3);
						worldIn.removeBlock(pos, false);
					}
					return ActionResultType.SUCCESS;
				}
			}
		} else if (player.getItemInHand(handIn).getItem() != AutumnityBlocks.PANCAKE.get().asItem()) {
			if (worldIn.isClientSide) {
				if (this.eatCake(worldIn, pos, state, player, itemstack) == ActionResultType.SUCCESS) {
					return ActionResultType.SUCCESS;
				}

				if (itemstack.isEmpty()) {
					return ActionResultType.CONSUME;
				}
			}

			return this.eatCake(worldIn, pos, state, player, itemstack);
		}

		return ActionResultType.PASS;
	}

	private ActionResultType eatCake(World worldIn, BlockPos pos, BlockState state, PlayerEntity player, ItemStack itemstack) {
		int i = state.getValue(PANCAKES);
		if (!player.canEat(false)) {
			return ActionResultType.PASS;
		} else if (i < 31 && itemstack.getItem() == this.asItem()) {
			return ActionResultType.PASS;
		} else if (worldIn.getBlockState(pos.above()).getBlock() == this) {
			return ActionResultType.PASS;
		} else {
			ItemStack stack = this.getPickBlock(state, null, worldIn, pos, player);
			player.playSound(player.getEatingSound(stack), 1.0F, 1.0F + (worldIn.getRandom().nextFloat() - worldIn.getRandom().nextFloat()) * 0.4F);
			player.getFoodData().eat(4, 0.3F);
			if (i > 1) {
				worldIn.setBlock(pos, state.setValue(PANCAKES, Integer.valueOf(i - 1)), 3);
			} else {
				worldIn.removeBlock(pos, false);
			}

			if (player.hasEffect(AutumnityEffects.FOUL_TASTE.get())) {
				player.getFoodData().eat(2, 0.0F);
				AutumnityEvents.updateFoulTaste(player);
			}

			return ActionResultType.SUCCESS;
		}
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
		return useContext.getItemInHand().getItem() == this.asItem() && state.getValue(PANCAKES) < 31 || super.canBeReplaced(state, useContext);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PANCAKES);
	}

	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return !state.getCollisionShape(worldIn, pos).getFaceShape(Direction.UP).isEmpty() || (state.getBlock() == this && state.getValue(PANCAKES) >= 31);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
		return (int) Math.ceil(blockState.getValue(PANCAKES) / 2);
	}
}