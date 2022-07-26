package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.core.other.AutumnityEvents;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

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
		this.registerDefaultState(this.stateDefinition.any().setValue(PANCAKES, 2));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		int i = Math.floorDiv(state.getValue(PANCAKES) - 1, 2);
		return SHAPES[i];
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
		if (blockstate.getBlock() == this) {
			return blockstate.setValue(PANCAKES, Math.min(32, blockstate.getValue(PANCAKES) + 2));
		} else {
			BlockState blockstate1 = context.getLevel().getBlockState(context.getClickedPos().below());
			if (blockstate1.getBlock() == this && blockstate1.getValue(PANCAKES) == 31) {
				context.getLevel().setBlock(context.getClickedPos().below(), blockstate1.setValue(PANCAKES, 32), 11);
				return this.defaultBlockState().setValue(PANCAKES, 1);
			} else {
				return super.getStateForPlacement(context);
			}
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);

		if (player.isShiftKeyDown()) {
			if (worldIn.getBlockState(pos.above()).getBlock() != this) {
				if (state.getValue(PANCAKES) > 1) {
					if (!worldIn.isClientSide) {
						int i = state.getValue(PANCAKES);

						popResource(worldIn, pos, new ItemStack(this.asItem()));

						if (i > 2) {
							worldIn.setBlock(pos, state.setValue(PANCAKES, i - 2), 3);
						} else {
							worldIn.removeBlock(pos, false);
						}
					}
					return InteractionResult.SUCCESS;
				} else if (worldIn.getBlockState(pos.below()).getBlock() == this && worldIn.getBlockState(pos.below()).getValue(PANCAKES) == 32) {
					if (!worldIn.isClientSide) {
						popResource(worldIn, pos, new ItemStack(this.asItem()));

						worldIn.setBlock(pos.below(), state.setValue(PANCAKES, 31), 3);
						worldIn.removeBlock(pos, false);
					}
					return InteractionResult.SUCCESS;
				}
			}
		} else if (player.getItemInHand(handIn).getItem() != AutumnityBlocks.PANCAKE.get().asItem()) {
			if (worldIn.isClientSide) {
				if (this.eatCake(worldIn, pos, state, player, itemstack) == InteractionResult.SUCCESS) {
					return InteractionResult.SUCCESS;
				}

				if (itemstack.isEmpty()) {
					return InteractionResult.CONSUME;
				}
			}

			return this.eatCake(worldIn, pos, state, player, itemstack);
		}

		return InteractionResult.PASS;
	}

	private InteractionResult eatCake(Level worldIn, BlockPos pos, BlockState state, Player player, ItemStack itemstack) {
		int i = state.getValue(PANCAKES);
		if (!player.canEat(false)) {
			return InteractionResult.PASS;
		} else if (i < 31 && itemstack.getItem() == this.asItem()) {
			return InteractionResult.PASS;
		} else if (worldIn.getBlockState(pos.above()).getBlock() == this) {
			return InteractionResult.PASS;
		} else {
			ItemStack stack = this.getCloneItemStack(state, null, worldIn, pos, player);
			player.playSound(player.getEatingSound(stack), 1.0F, 1.0F + (worldIn.getRandom().nextFloat() - worldIn.getRandom().nextFloat()) * 0.4F);
			player.getFoodData().eat(4, 0.3F);
			if (i > 1) {
				worldIn.setBlock(pos, state.setValue(PANCAKES, i - 1), 3);
			} else {
				worldIn.removeBlock(pos, false);
			}

			if (player.hasEffect(AutumnityMobEffects.FOUL_TASTE.get())) {
				player.getFoodData().eat(2, 0.0F);
				AutumnityEvents.updateFoulTaste(player);
			}

			return InteractionResult.SUCCESS;
		}
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
		return useContext.getItemInHand().getItem() == this.asItem() && state.getValue(PANCAKES) < 31 || super.canBeReplaced(state, useContext);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(PANCAKES);
	}

	protected boolean isValidGround(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return !state.getCollisionShape(worldIn, pos).getFaceShape(Direction.UP).isEmpty() || (state.getBlock() == this && state.getValue(PANCAKES) >= 31);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
		return (int) Math.ceil(blockState.getValue(PANCAKES) / 2);
	}
}