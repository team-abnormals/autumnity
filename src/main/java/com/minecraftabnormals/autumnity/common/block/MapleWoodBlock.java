package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.abnormals_core.core.util.BlockUtil;
import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class MapleWoodBlock extends RotatedPillarBlock {
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.WARPED_HYPHAE);
	private final Supplier<Block> block;
	private final Supplier<Block> sappyBlock;

	public MapleWoodBlock(Supplier<Block> strippedBlock, Supplier<Block> sappyBlock, Properties properties) {
		super(properties);
		this.block = strippedBlock;
		this.sappyBlock = sappyBlock;
	}

	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		ItemStack stack = player.getHeldItem(hand);
		if (stack.getItem() instanceof AxeItem) {
			world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!world.isRemote) {
				BlockState strippedState = world.getRandom().nextInt(4) == 0 ? this.sappyBlock.get().getDefaultState() : this.block.get().getDefaultState();
				world.setBlockState(pos, BlockUtil.transferAllBlockStates(state, strippedState));
				stack.damageItem(1, player, (playerIn) -> {
					playerIn.sendBreakAnimation(hand);
				});
			}
			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}