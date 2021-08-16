package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.common.entity.item.FallingHeadBlockEntity;
import com.minecraftabnormals.autumnity.core.other.AutumnityEvents;
import com.minecraftabnormals.autumnity.core.other.AutumnityTags;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEffects;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import com.minecraftabnormals.autumnity.core.registry.AutumnitySoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.ModList;

import java.util.Random;

public class TurkeyBlock extends FallingBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty CHUNKS = IntegerProperty.create("chunks", 0, 4);
	public static final VoxelShape[] NORTH_SHAPE = new VoxelShape[]{Block.box(1.0D, 0.0D, 2.0D, 15.0D, 8.0D, 16.0D),
			Block.box(1.0D, 0.0D, 2.0D, 13.0D, 8.0D, 16.0D),
			Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D),
			Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 10.0D),
			Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 6.0D)};
	public static final VoxelShape[] SOUTH_SHAPE = new VoxelShape[]{Block.box(1.0D, 0.0D, 0.0D, 15.0D, 8.0D, 14.0D),
			Block.box(3.0D, 0.0D, 0.0D, 15.0D, 8.0D, 14.0D),
			Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D),
			Block.box(3.0D, 0.0D, 6.0D, 13.0D, 8.0D, 14.0D),
			Block.box(3.0D, 0.0D, 10.0D, 13.0D, 8.0D, 14.0D)};
	public static final VoxelShape[] WEST_SHAPE = new VoxelShape[]{Block.box(2.0D, 0.0D, 1.0D, 16.0D, 8.0D, 15.0D),
			Block.box(2.0D, 0.0D, 3.0D, 16.0D, 8.0D, 15.0D),
			Block.box(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
			Block.box(2.0D, 0.0D, 3.0D, 10.0D, 8.0D, 13.0D),
			Block.box(2.0D, 0.0D, 3.0D, 6.0D, 8.0D, 13.0D)};
	public static final VoxelShape[] EAST_SHAPE = new VoxelShape[]{Block.box(0.0D, 0.0D, 1.0D, 14.0D, 8.0D, 15.0D),
			Block.box(0.0D, 0.0D, 1.0D, 14.0D, 8.0D, 13.0D),
			Block.box(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
			Block.box(6.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
			Block.box(10.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D)};

	public TurkeyBlock(Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CHUNKS, Integer.valueOf(0)));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		int i = state.getValue(CHUNKS);

		if (state.getValue(FACING) == Direction.NORTH)
			return NORTH_SHAPE[i];
		else if (state.getValue(FACING) == Direction.SOUTH)
			return SOUTH_SHAPE[i];
		else if (state.getValue(FACING) == Direction.WEST)
			return WEST_SHAPE[i];
		else
			return EAST_SHAPE[i];
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (worldIn.isEmptyBlock(pos.below()) || isFree(worldIn.getBlockState(pos.below())) && pos.getY() >= 0) {
			FallingBlockEntity fallingblockentity;

			if (state.getValue(CHUNKS) == 0) {
				fallingblockentity = new FallingHeadBlockEntity(worldIn, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
			} else {
				fallingblockentity = new FallingBlockEntity(worldIn, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
				fallingblockentity.dropItem = false;
			}

			this.falling(fallingblockentity);
			worldIn.addFreshEntity(fallingblockentity);
		}
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		if (worldIn.isClientSide) {
			if (this.eatTurkey(worldIn, pos, state, player, itemstack, handIn) == ActionResultType.SUCCESS) {
				return ActionResultType.SUCCESS;
			}

			if (itemstack.isEmpty()) {
				return ActionResultType.CONSUME;
			}
		}

		return this.eatTurkey(worldIn, pos, state, player, itemstack, handIn);
	}

	private ActionResultType eatTurkey(World worldIn, BlockPos pos, BlockState state, PlayerEntity player, ItemStack itemstack, Hand hand) {
		int i = state.getValue(CHUNKS);
		boolean flag = ModList.get().isLoaded("farmersdelight") ? itemstack.getItem().is(AutumnityTags.KNIVES) : itemstack.getItem().getToolTypes(itemstack).contains(ToolType.AXE);
		if (player.canEat(false) || flag) {
			if (flag) {
				popResource(worldIn, pos, new ItemStack(this.getLeg()));
				worldIn.playSound(player, pos, AutumnitySoundEvents.BLOCK_TURKEY_CUT.get(), SoundCategory.BLOCKS, 1.0F, (worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.2F + 1.0F);
				itemstack.hurtAndBreak(1, player, (playerIn) -> {
					playerIn.broadcastBreakEvent(hand);
				});
			} else {
				ItemStack stack = this.getPickBlock(state, null, worldIn, pos, player);
				player.playSound(player.getEatingSound(stack), 1.0F, 1.0F + (worldIn.getRandom().nextFloat() - worldIn.getRandom().nextFloat()) * 0.4F);
				this.restoreHunger(worldIn, player);
			}
			if (i < 4) {
				worldIn.setBlock(pos, state.setValue(CHUNKS, i + 1), 3);
			} else {
				worldIn.removeBlock(pos, false);
			}

			return ActionResultType.SUCCESS;
		} else {
			return ActionResultType.PASS;
		}
	}

	protected void restoreHunger(IWorld worldIn, PlayerEntity player) {
		player.getFoodData().eat(AutumnityItems.Foods.TURKEY.getNutrition(), AutumnityItems.Foods.TURKEY.getSaturationModifier());

		if (!worldIn.isClientSide() && worldIn.getRandom().nextFloat() < 0.1F) {
			player.addEffect(new EffectInstance(Effects.HUNGER, 600, 0));
		}

		int i = AutumnityItems.Foods.TURKEY.getNutrition();
		int j = i == 1 ? i : (int) (i * 0.5F);

		if (player.hasEffect(AutumnityEffects.FOUL_TASTE.get())) {
			player.getFoodData().eat(j, 0.0F);
			AutumnityEvents.updateFoulTaste(player);
		}
	}

	protected Item getLeg() {
		return AutumnityItems.TURKEY_PIECE.get();
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, CHUNKS);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
		return 10 - blockState.getValue(CHUNKS) * 2;
	}
}