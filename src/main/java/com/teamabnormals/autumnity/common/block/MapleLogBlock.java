package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.blueprint.common.block.LogBlock;
import com.teamabnormals.blueprint.core.util.BlockUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

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
	public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility toolAction, boolean simulate) {
		ItemStack stack = context.getItemInHand();
		Level level = context.getLevel();
		if (toolAction == ItemAbilities.AXE_STRIP && block != null && sappyBlock != null) {
			boolean sappy = AutumnityConfig.COMMON.sappyMapleLogs.get();
			if (sappy) {
				float f = (float) AutumnityConfig.COMMON.sappyMapleLogChance.getAsDouble();
				if (AutumnityConfig.COMMON.fortuneAffectsMapleLogs.get()) {
					int i = EnchantmentHelper.getTagEnchantmentLevel(level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.FORTUNE), stack);
					f = 1.0F - (1.0F - f) * (4.0F / (i + 4.0F));
				}

				sappy = !level.isClientSide() && level.getRandom().nextFloat() <= f;
			}
			return BlockUtil.transferAllBlockStates(state, sappy ? this.sappyBlock.get().defaultBlockState() : this.block.get().defaultBlockState());
		}
		return super.getToolModifiedState(state, context, toolAction, simulate);
	}
}