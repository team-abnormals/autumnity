package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.blueprint.common.block.LogBlock;
import com.teamabnormals.blueprint.core.util.BlockUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import java.util.function.Supplier;

public class MapleLogBlock extends LogBlock {
	private final Supplier<Block> block;
	private final Supplier<Block> sappyBlock;

	public MapleLogBlock(Supplier<Block> strippedBlockIn, Supplier<Block> sappyBlockIn, Properties properties) {
		super(strippedBlockIn, properties);
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
			return block != null ? BlockUtil.transferAllBlockStates(state, !level.isClientSide() && level.getRandom().nextFloat() <= f ? this.sappyBlock.get().defaultBlockState() : this.block.get().defaultBlockState()) : null;
		}
		return super.getToolModifiedState(state, context, toolAction, simulate);
	}
}