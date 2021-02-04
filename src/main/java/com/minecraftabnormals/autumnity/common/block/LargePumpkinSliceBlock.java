package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.common.block.properties.CarvedSide;
import com.minecraftabnormals.autumnity.core.other.AutumnityTags;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.Half;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

public class LargePumpkinSliceBlock extends AbstractLargePumpkinSliceBlock {
	public LargePumpkinSliceBlock(Properties properties) {
		super(properties);
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF);
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getHeldItem(handIn);
		if (itemstack.getItem() == Items.SHEARS || (ModList.get().isLoaded("farmersdelight") && itemstack.getItem().isIn(AutumnityTags.KNIVES))) {
			Direction direction = hit.getFace();
			Direction direction1 = direction.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : hit.getFace();
			Direction direction2 = state.get(FACING);

			if (canCarve(direction1, direction2)) {
				if (!worldIn.isRemote) {
					CarvedSide carvedside = CarvedSide.getCarvedSide(direction1.getAxis());
					BlockState blockstate = AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get().getDefaultState().with(CarvedLargePumpkinSliceBlock.FACING, direction2).with(CarvedLargePumpkinSliceBlock.HALF, state.get(HALF)).with(CarvedLargePumpkinSliceBlock.CARVED_SIDE, carvedside);

					worldIn.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					worldIn.setBlockState(pos, blockstate, 11);
					ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + 0.5D + (double) direction1.getXOffset() * 0.65D, (double) pos.getY() + 0.1D, (double) pos.getZ() + 0.5D + (double) direction1.getZOffset() * 0.65D, new ItemStack(Items.PUMPKIN_SEEDS, 4));
					itementity.setMotion(0.05D * (double) direction1.getXOffset() + worldIn.rand.nextDouble() * 0.02D, 0.05D, 0.05D * (double) direction1.getZOffset() + worldIn.rand.nextDouble() * 0.02D);
					worldIn.addEntity(itementity);
					itemstack.damageItem(1, player, (p_220282_1_) -> {
						p_220282_1_.sendBreakAnimation(handIn);
					});
				}

				return ActionResultType.func_233537_a_(worldIn.isRemote);
			}
		}

		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getPos();
		BlockState bottomblock = context.getWorld().getBlockState(blockpos.down());
		BlockState topblock = context.getWorld().getBlockState(blockpos.up());

		if (bottomblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && bottomblock.get(HALF) == Half.BOTTOM) {
			return this.getDefaultState().with(FACING, bottomblock.get(FACING)).with(HALF, Half.TOP);
		} else if (topblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && topblock.get(HALF) == Half.TOP) {
			return this.getDefaultState().with(FACING, topblock.get(FACING)).with(HALF, Half.BOTTOM);
		}

		return this.getDefaultState().with(FACING, getFacing(context)).with(HALF, MathHelper.sin(context.getPlayer().getPitch(1.0F) * ((float) Math.PI / 180F)) > 0 ? Half.BOTTOM : Half.TOP);
	}
}