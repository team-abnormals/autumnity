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

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF);
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		if (itemstack.getItem() == Items.SHEARS || (ModList.get().isLoaded("farmersdelight") && itemstack.getItem().is(AutumnityTags.KNIVES))) {
			Direction direction = hit.getDirection();
			Direction direction1 = direction.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : hit.getDirection();
			Direction direction2 = state.getValue(FACING);

			if (canCarve(direction1, direction2)) {
				if (!worldIn.isClientSide) {
					CarvedSide carvedside = CarvedSide.getCarvedSide(direction1.getAxis());
					BlockState blockstate = AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get().defaultBlockState().setValue(CarvedLargePumpkinSliceBlock.FACING, direction2).setValue(CarvedLargePumpkinSliceBlock.HALF, state.getValue(HALF)).setValue(CarvedLargePumpkinSliceBlock.CARVED_SIDE, carvedside);

					worldIn.playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					worldIn.setBlock(pos, blockstate, 11);
					ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + 0.5D + (double) direction1.getStepX() * 0.65D, (double) pos.getY() + 0.1D, (double) pos.getZ() + 0.5D + (double) direction1.getStepZ() * 0.65D, new ItemStack(Items.PUMPKIN_SEEDS, 4));
					itementity.setDeltaMovement(0.05D * (double) direction1.getStepX() + worldIn.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double) direction1.getStepZ() + worldIn.random.nextDouble() * 0.02D);
					worldIn.addFreshEntity(itementity);
					itemstack.hurtAndBreak(1, player, (p_220282_1_) -> {
						p_220282_1_.broadcastBreakEvent(handIn);
					});
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

		if (bottomblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && bottomblock.getValue(HALF) == Half.BOTTOM) {
			return this.defaultBlockState().setValue(FACING, bottomblock.getValue(FACING)).setValue(HALF, Half.TOP);
		} else if (topblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && topblock.getValue(HALF) == Half.TOP) {
			return this.defaultBlockState().setValue(FACING, topblock.getValue(FACING)).setValue(HALF, Half.BOTTOM);
		}

		return this.defaultBlockState().setValue(FACING, getFacing(context)).setValue(HALF, MathHelper.sin(context.getPlayer().getViewXRot(1.0F) * ((float) Math.PI / 180F)) > 0 ? Half.BOTTOM : Half.TOP);
	}
}