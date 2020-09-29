package com.markus1002.autumnity.common.block;

import com.markus1002.autumnity.common.block.properties.CarvedSide;
import com.markus1002.autumnity.core.registry.AutumnityBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.Half;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CarvedLargePumpkinSliceBlock extends AbstractLargePumpkinSliceBlock
{
	public static final EnumProperty<CarvedSide> CARVED_SIDE = AutumnityBlockStateProperties.CARVED_SIDE;

	public CarvedLargePumpkinSliceBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(CARVED_SIDE, CarvedSide.X));
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(FACING, HALF, CARVED_SIDE);
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		ItemStack itemstack = player.getHeldItem(handIn);
		if (this == AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get() && (itemstack.getItem() == Items.TORCH || itemstack.getItem() == Items.SOUL_TORCH || itemstack.getItem() == Items.REDSTONE_TORCH))
		{
			Direction direction = hit.getFace();
			Direction direction1 = state.get(FACING);
			CarvedSide carvedside = state.get(CARVED_SIDE);

			if (canCarve(direction, direction1) && (direction.getAxis() == Axis.X && carvedside == CarvedSide.X || direction.getAxis() == Axis.Z && carvedside == CarvedSide.Z))
			{
				if (!worldIn.isRemote)
				{
					Item item = itemstack.getItem();
					BlockState blockstate = item == Items.TORCH ? AutumnityBlocks.LARGE_JACK_O_LANTERN_SLICE.get().getDefaultState() : item == Items.SOUL_TORCH ? AutumnityBlocks.LARGE_SOUL_JACK_O_LANTERN_SLICE.get().getDefaultState() : AutumnityBlocks.LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get().getDefaultState().with(RedstoneJackOLanternBlock.LIT, Boolean.valueOf(worldIn.isBlockPowered(pos)));
					BlockState blockstate1 = blockstate.with(CarvedLargePumpkinSliceBlock.FACING, state.get(FACING)).with(CarvedLargePumpkinSliceBlock.HALF, state.get(HALF)).with(CarvedLargePumpkinSliceBlock.CARVED_SIDE, state.get(CARVED_SIDE));
					worldIn.setBlockState(pos, blockstate1, 11);

					worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					if (!player.abilities.isCreativeMode)
					{
						itemstack.shrink(1);
					}
				}

				return ActionResultType.func_233537_a_(worldIn.isRemote);
			}
		}

		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}

	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockPos blockpos = context.getPos();
		BlockState bottomblock = context.getWorld().getBlockState(blockpos.down());
		BlockState topblock = context.getWorld().getBlockState(blockpos.up());
		CarvedSide carvedside = context.getPlacementHorizontalFacing().getAxis() == Axis.X ? CarvedSide.X : CarvedSide.Z;

		if (bottomblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && bottomblock.get(HALF) == Half.BOTTOM)
		{
			return this.getDefaultState().with(FACING, bottomblock.get(FACING)).with(HALF, Half.TOP).with(CARVED_SIDE, carvedside);
		}
		else if (topblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && topblock.get(HALF) == Half.TOP)
		{
			return this.getDefaultState().with(FACING, topblock.get(FACING)).with(HALF, Half.BOTTOM).with(CARVED_SIDE, carvedside);
		}

		return this.getDefaultState().with(FACING, getFacing(context)).with(HALF, MathHelper.sin(context.getPlayer().getPitch(1.0F) * ((float)Math.PI / 180F)) > 0 ? Half.BOTTOM : Half.TOP).with(CARVED_SIDE, carvedside);
	}

	public BlockState rotate(BlockState state, Rotation rot)
	{
		if (rot == Rotation.CLOCKWISE_90 || rot == Rotation.COUNTERCLOCKWISE_90)
		{
			return super.rotate(state, rot).with(CARVED_SIDE, state.get(CARVED_SIDE) == CarvedSide.X ? CarvedSide.Z : CarvedSide.X);
		}

		return super.rotate(state, rot);
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}
}