package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.block.properties.CarvedSide;
import com.teamabnormals.autumnity.core.other.tags.AutumnityItemTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class GiantPumpkinChunkBlock extends AbstractLargePumpkinSliceBlock {
	public GiantPumpkinChunkBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (stack.getItem() == Items.SHEARS || stack.is(AutumnityItemTags.KNIVES)) {
			Direction hitface = hit.getDirection().getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : hit.getDirection();
			Direction facing = state.getValue(FACING);

			if (canCarve(hitface, facing)) {
				if (!level.isClientSide) {
					CarvedSide carvedside = CarvedSide.getCarvedSide(hitface.getAxis());
					BlockState blockstate = AutumnityBlocks.GIANT_CARVED_PUMPKIN_CHUNK.get().withPropertiesOf(state).setValue(GiantCarvedPumpkinChunkBlock.CARVED_SIDE, carvedside);

					level.playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
					level.setBlock(pos, blockstate, 11);
					ItemEntity itementity = new ItemEntity(level, (double) pos.getX() + 0.5D + (double) hitface.getStepX() * 0.65D, (double) pos.getY() + 0.1D, (double) pos.getZ() + 0.5D + (double) hitface.getStepZ() * 0.65D, new ItemStack(Items.PUMPKIN_SEEDS, 4));
					itementity.setDeltaMovement(0.05D * (double) hitface.getStepX() + level.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double) hitface.getStepZ() + level.random.nextDouble() * 0.02D);
					level.addFreshEntity(itementity);
					stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
				}

				return ItemInteractionResult.sidedSuccess(level.isClientSide);
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}
}