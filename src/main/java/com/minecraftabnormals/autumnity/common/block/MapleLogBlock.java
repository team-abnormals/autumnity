package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.abnormals_core.core.util.BlockUtil;
import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.function.Supplier;

public class MapleLogBlock extends RotatedPillarBlock {
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.WARPED_STEM);
	private final Supplier<Block> block;
	private final Supplier<Block> sappyBlock;

	public MapleLogBlock(Supplier<Block> strippedBlock, Supplier<Block> sappyBlock, Properties properties) {
		super(properties);
		this.block = strippedBlock;
		this.sappyBlock = sappyBlock;
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
		if (toolType == ToolType.AXE)
			return block != null ? BlockUtil.transferAllBlockStates(state, world.getRandom().nextInt(4) == 0 ? this.sappyBlock.get().getDefaultState() : this.block.get().getDefaultState()) : null;
		return super.getToolModifiedState(state, world, pos, player, stack, toolType);
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}