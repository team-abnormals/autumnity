package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.blueprint.core.util.BlockUtil;
import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import java.util.function.Supplier;

public class MapleLogBlock extends RotatedPillarBlock {
	private static final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.WARPED_STEM);
	private final Supplier<Block> block;
	private final Supplier<Block> sappyBlock;

	public MapleLogBlock(Supplier<Block> strippedBlockIn, Supplier<Block> sappyBlockIn, Properties properties) {
		super(properties);
		this.block = strippedBlockIn;
		this.sappyBlock = sappyBlockIn;
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
		ItemStack stack = context.getItemInHand();
		Level level = context.getLevel();
		if (toolAction == ToolActions.AXE_STRIP) {
			int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack);
			float f = -1.0F / (i * (1.0F / 3.0F) + (4.0F / 3.0F)) + 1.0F;

			return block != null ? BlockUtil.transferAllBlockStates(state, level.getRandom().nextFloat() <= f ? this.sappyBlock.get().defaultBlockState() : this.block.get().defaultBlockState()) : null;
		}
		return super.getToolModifiedState(state, context, toolAction, simulate);
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}