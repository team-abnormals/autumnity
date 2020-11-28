package com.minecraftabnormals.autumnity.common.block;

import java.util.Random;

import com.minecraftabnormals.autumnity.common.entity.item.FallingHeadBlockEntity;
import com.minecraftabnormals.autumnity.core.other.AutumnityFoods;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import com.minecraftabnormals.autumnity.core.registry.AutumnitySoundEvents;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TurkeyBlock extends FallingBlock
{
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty CHUNKS = IntegerProperty.create("chunks", 0, 4);
	public static final VoxelShape[] NORTH_SHAPE = new VoxelShape[]{Block.makeCuboidShape(1.0D, 0.0D, 2.0D, 15.0D, 8.0D, 16.0D),
			Block.makeCuboidShape(1.0D, 0.0D, 2.0D, 13.0D, 8.0D, 16.0D),
			Block.makeCuboidShape(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D),
			Block.makeCuboidShape(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 10.0D),
			Block.makeCuboidShape(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 6.0D)};
	public static final VoxelShape[] SOUTH_SHAPE = new VoxelShape[]{Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 8.0D, 14.0D),
			Block.makeCuboidShape(3.0D, 0.0D, 0.0D, 15.0D, 8.0D, 14.0D),
			Block.makeCuboidShape(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D),
			Block.makeCuboidShape(3.0D, 0.0D, 6.0D, 13.0D, 8.0D, 14.0D),
			Block.makeCuboidShape(3.0D, 0.0D, 10.0D, 13.0D, 8.0D, 14.0D)};
	public static final VoxelShape[] WEST_SHAPE = new VoxelShape[]{Block.makeCuboidShape(2.0D, 0.0D, 1.0D, 16.0D, 8.0D, 15.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 3.0D, 16.0D, 8.0D, 15.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 3.0D, 10.0D, 8.0D, 13.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 3.0D, 6.0D, 8.0D, 13.0D)};
	public static final VoxelShape[] EAST_SHAPE = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 14.0D, 8.0D, 15.0D),
			Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 14.0D, 8.0D, 13.0D),
			Block.makeCuboidShape(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
			Block.makeCuboidShape(6.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
			Block.makeCuboidShape(10.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D)};

	public TurkeyBlock(Properties builder)
	{
		super(builder);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(CHUNKS, Integer.valueOf(0)));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		int i = state.get(CHUNKS);

		if (state.get(FACING) == Direction.NORTH)
			return NORTH_SHAPE[i];
		else if (state.get(FACING) == Direction.SOUTH)
			return SOUTH_SHAPE[i];
		else if (state.get(FACING) == Direction.WEST)
			return WEST_SHAPE[i];
		else
			return EAST_SHAPE[i];
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
	{
		if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0)
		{
			FallingBlockEntity fallingblockentity;

			if (state.get(CHUNKS) == 0)
			{
				fallingblockentity = new FallingHeadBlockEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, worldIn.getBlockState(pos));
			}
			else
			{
				fallingblockentity = new FallingBlockEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, worldIn.getBlockState(pos));
			}

			this.onStartFalling(fallingblockentity);
			worldIn.addEntity(fallingblockentity);
		}
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		ItemStack itemstack = player.getHeldItem(handIn);
		if (worldIn.isRemote)
		{
			if (this.eatTurkey(worldIn, pos, state, player, itemstack, handIn) == ActionResultType.SUCCESS)
			{
				return ActionResultType.SUCCESS;
			}

			if (itemstack.isEmpty())
			{
				return ActionResultType.CONSUME;
			}
		}

		return this.eatTurkey(worldIn, pos, state, player, itemstack, handIn);
	}

	private ActionResultType eatTurkey(World worldIn, BlockPos pos, BlockState state, PlayerEntity player, ItemStack itemstack, Hand hand)
	{
		int i = state.get(CHUNKS);
		boolean flag = itemstack.getItem() instanceof AxeItem;
		if (player.canEat(false) || flag)
		{
			if (flag)
			{
				spawnAsEntity(worldIn, pos, new ItemStack(this.getLeg()));
				worldIn.playSound(player, pos, AutumnitySoundEvents.BLOCK_TURKEY_CUT.get(), SoundCategory.BLOCKS, 1.0F, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F + 1.0F);
				itemstack.damageItem(1, player, (playerIn) -> {
					playerIn.sendBreakAnimation(hand);
				});
			}
			else
			{
				ItemStack stack = this.getPickBlock(state, null, worldIn, pos, player);
				player.playSound(player.getEatSound(stack), 1.0F, 1.0F + (worldIn.getRandom().nextFloat() - worldIn.getRandom().nextFloat()) * 0.4F);
				this.restoreHunger(worldIn, player);
			}
			if (i < 4)
			{
				worldIn.setBlockState(pos, state.with(CHUNKS, Integer.valueOf(i + 1)), 3);
			}
			else
			{
				worldIn.removeBlock(pos, false);
			}

			return ActionResultType.SUCCESS;
		}
		else
		{
			return ActionResultType.PASS;
		}
	}

	protected void restoreHunger(IWorld worldIn, PlayerEntity player)
	{
		player.getFoodStats().addStats(AutumnityFoods.TURKEY.getHealing(), AutumnityFoods.TURKEY.getSaturation());

		if (!worldIn.isRemote() && worldIn.getRandom().nextFloat() < 0.1F)
		{
			player.addPotionEffect(new EffectInstance(Effects.HUNGER, 600, 0));
		}
	}

	protected Item getLeg()
	{
		return AutumnityItems.TURKEY_LEG.get();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, CHUNKS);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing());
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type)
	{
		return false;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}
	
	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		return 10 - blockState.get(CHUNKS) * 2;
	}
}