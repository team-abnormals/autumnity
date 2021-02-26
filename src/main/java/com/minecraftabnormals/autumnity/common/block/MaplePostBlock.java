package com.minecraftabnormals.autumnity.common.block;

import java.util.function.Supplier;

import com.minecraftabnormals.abnormals_core.common.blocks.wood.WoodPostBlock;
import com.minecraftabnormals.abnormals_core.core.util.BlockUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class MaplePostBlock extends WoodPostBlock {
	private final Supplier<Block> sappyBlock;

	public MaplePostBlock(Supplier<Block> strippedBlockIn, Supplier<Block> sappyBlockIn, Properties properties) {
		super(strippedBlockIn, properties);
		this.sappyBlock = sappyBlockIn;
	}

	@Override
	public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
		if (toolType == ToolType.AXE) {
			int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
			float f = -1.0F / (i * (1.0F / 3.0F) + (4.0F / 3.0F)) + 1.0F;
			player.sendStatusMessage(new StringTextComponent(Float.toString(f)), true);

			if (world.getRandom().nextFloat() <= f)
				return BlockUtil.transferAllBlockStates(state, this.sappyBlock.get().getDefaultState());
		}
		return super.getToolModifiedState(state, world, pos, player, stack, toolType);
	}
}