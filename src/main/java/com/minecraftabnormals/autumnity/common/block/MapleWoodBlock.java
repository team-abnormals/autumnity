package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.abnormals_core.core.util.BlockUtil;
import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.function.Supplier;

public class MapleWoodBlock extends RotatedPillarBlock {
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.WARPED_HYPHAE);
	private final Supplier<Block> block;
	private final Supplier<Block> sappyBlock;

	public MapleWoodBlock(Supplier<Block> strippedBlockIn, Supplier<Block> sappyBlockIn, Properties properties) {
		super(properties);
		this.block = strippedBlockIn;
		this.sappyBlock = sappyBlockIn;
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
		if (toolType == ToolType.AXE) {
			int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
			float f = -1.0F / (i * (1.0F / 3.0F) + (4.0F / 3.0F)) + 1.0F;

			return block != null ? BlockUtil.transferAllBlockStates(state, world.getRandom().nextFloat() <= f ? this.sappyBlock.get().getDefaultState() : this.block.get().getDefaultState()) : null;
		}
		return super.getToolModifiedState(state, world, pos, player, stack, toolType);
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}