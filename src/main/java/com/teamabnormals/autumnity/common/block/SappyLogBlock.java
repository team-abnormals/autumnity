package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.blueprint.core.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SappyLogBlock extends RotatedPillarBlock {
	private final Supplier<Block> saplessBlock;

	public SappyLogBlock(RegistryObject<Block> saplessBlockIn, Properties properties) {
		super(properties);
		this.saplessBlock = saplessBlockIn;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();
		if (stack.is(Items.GLASS_BOTTLE)) {
			stack.shrink(1);
			level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
			if (stack.isEmpty()) {
				player.setItemInHand(hand, new ItemStack(AutumnityItems.SAP_BOTTLE.get()));
			} else if (!player.getInventory().add(new ItemStack(AutumnityItems.SAP_BOTTLE.get()))) {
				player.drop(new ItemStack(AutumnityItems.SAP_BOTTLE.get()), false);
			}

			level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
			if (!level.isClientSide()) {
				player.awardStat(Stats.ITEM_USED.get(item));
			}

			level.setBlockAndUpdate(pos, BlockUtil.transferAllBlockStates(state, this.saplessBlock.get().defaultBlockState()));
			return InteractionResult.sidedSuccess(level.isClientSide);
		}

		return super.use(state, level, pos, player, hand, result);
	}
}