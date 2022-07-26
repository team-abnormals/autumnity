package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.block.properties.CarvedSide;
import com.teamabnormals.autumnity.core.other.tags.AutumnityItemTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fml.ModList;

public class LargePumpkinSliceBlock extends AbstractLargePumpkinSliceBlock {
	public LargePumpkinSliceBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		if (itemstack.getItem() == Items.SHEARS || (ModList.get().isLoaded("farmersdelight") && itemstack.is(AutumnityItemTags.KNIVES))) {
			Direction hitface = hit.getDirection().getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : hit.getDirection();
			Direction facing = state.getValue(FACING);

			if (canCarve(hitface, facing)) {
				if (!worldIn.isClientSide) {
					CarvedSide carvedside = CarvedSide.getCarvedSide(hitface.getAxis());
					BlockState blockstate = AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get().defaultBlockState().setValue(CarvedLargePumpkinSliceBlock.FACING, facing).setValue(CarvedLargePumpkinSliceBlock.HALF, state.getValue(HALF)).setValue(CarvedLargePumpkinSliceBlock.CARVED_SIDE, carvedside);

					worldIn.playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
					worldIn.setBlock(pos, blockstate, 11);
					ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + 0.5D + (double) hitface.getStepX() * 0.65D, (double) pos.getY() + 0.1D, (double) pos.getZ() + 0.5D + (double) hitface.getStepZ() * 0.65D, new ItemStack(Items.PUMPKIN_SEEDS, 4));
					itementity.setDeltaMovement(0.05D * (double) hitface.getStepX() + worldIn.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double) hitface.getStepZ() + worldIn.random.nextDouble() * 0.02D);
					worldIn.addFreshEntity(itementity);
					itemstack.hurtAndBreak(1, player, (p_220282_1_) -> {
						p_220282_1_.broadcastBreakEvent(handIn);
					});
				}

				return InteractionResult.sidedSuccess(worldIn.isClientSide);
			}
		}

		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockpos = context.getClickedPos();
		BlockState bottomblock = context.getLevel().getBlockState(blockpos.below());
		BlockState topblock = context.getLevel().getBlockState(blockpos.above());

		if (bottomblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && bottomblock.getValue(HALF) == Half.BOTTOM) {
			return this.defaultBlockState().setValue(FACING, bottomblock.getValue(FACING)).setValue(HALF, Half.TOP);
		} else if (topblock.getBlock() instanceof AbstractLargePumpkinSliceBlock && topblock.getValue(HALF) == Half.TOP) {
			return this.defaultBlockState().setValue(FACING, topblock.getValue(FACING)).setValue(HALF, Half.BOTTOM);
		}

		return this.defaultBlockState().setValue(FACING, getFacing(context)).setValue(HALF, Mth.sin(context.getPlayer().getViewXRot(1.0F) * ((float) Math.PI / 180F)) > 0 ? Half.BOTTOM : Half.TOP);
	}
}