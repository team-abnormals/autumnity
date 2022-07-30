package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.common.entity.projectile.ThrownTurkeyEgg;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.registries.ForgeRegistries;

public class AutumnityCompat {
	public static final Item ENDER_TORCH = ForgeRegistries.ITEMS.getValue(new ResourceLocation("endergetic", "ender_torch"));
	public static final Item CUPRIC_TORCH = ForgeRegistries.ITEMS.getValue(new ResourceLocation("caverns_and_chasms", "cupric_torch"));
	public static final Block YUCCA_GATEAU = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("atmospheric", "yucca_gateau"));

	public static void registerCompat() {
		registerCompostables();
		registerFlammables();
		registerDispenserBehaviors();
	}

	private static void registerCompostables() {
		DataUtil.registerCompostable(AutumnityItems.FOUL_BERRIES.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityItems.FOUL_BERRY_PIPS.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityItems.PUMPKIN_BREAD.get(), 0.85F);
		DataUtil.registerCompostable(AutumnityBlocks.AUTUMN_CROCUS.get(), 0.65F);

		DataUtil.registerCompostable(AutumnityBlocks.PANCAKE.get(), 0.85F);

		DataUtil.registerCompostable(AutumnityBlocks.LARGE_PUMPKIN_SLICE.get(), 0.65F);
		DataUtil.registerCompostable(AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get(), 0.65F);

		DataUtil.registerCompostable(AutumnityBlocks.MAPLE_LEAVES.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.RED_MAPLE_LEAVES.get(), 0.3F);

		DataUtil.registerCompostable(AutumnityBlocks.MAPLE_SAPLING.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.YELLOW_MAPLE_SAPLING.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.ORANGE_MAPLE_SAPLING.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.RED_MAPLE_SAPLING.get(), 0.3F);

		DataUtil.registerCompostable(AutumnityBlocks.MAPLE_LEAF_PILE.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.RED_MAPLE_LEAF_PILE.get(), 0.3F);

		DataUtil.registerCompostable(AutumnityBlocks.FOUL_BERRY_SACK.get(), 1.0F);
	}

	private static void registerFlammables() {
		DataUtil.registerFlammable(AutumnityBlocks.FOUL_BERRY_BUSH_PIPS.get(), 60, 100);
		DataUtil.registerFlammable(AutumnityBlocks.FOUL_BERRY_BUSH.get(), 60, 100);
		DataUtil.registerFlammable(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get(), 60, 100);
		DataUtil.registerFlammable(AutumnityBlocks.AUTUMN_CROCUS.get(), 60, 100);

		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_LOG.get(), 5, 5);
		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(AutumnityBlocks.STRIPPED_MAPLE_LOG.get(), 5, 5);
		DataUtil.registerFlammable(AutumnityBlocks.STRIPPED_MAPLE_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(AutumnityBlocks.SAPPY_MAPLE_LOG.get(), 5, 5);
		DataUtil.registerFlammable(AutumnityBlocks.SAPPY_MAPLE_WOOD.get(), 5, 5);

		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_PLANKS.get(), 5, 20);
		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_STAIRS.get(), 5, 20);
		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_SLAB.get(), 5, 20);

		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_FENCE.get(), 5, 20);
		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_FENCE_GATE.get(), 5, 20);

		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(AutumnityBlocks.RED_MAPLE_LEAVES.get(), 30, 60);

		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(AutumnityBlocks.RED_MAPLE_LEAF_PILE.get(), 30, 60);

		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_HEDGE.get(), 5, 20);
		DataUtil.registerFlammable(AutumnityBlocks.YELLOW_MAPLE_HEDGE.get(), 5, 20);
		DataUtil.registerFlammable(AutumnityBlocks.ORANGE_MAPLE_HEDGE.get(), 5, 20);
		DataUtil.registerFlammable(AutumnityBlocks.RED_MAPLE_HEDGE.get(), 5, 20);

		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_BEEHIVE.get(), 5, 20);
		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get(), 5, 20);
		DataUtil.registerFlammable(AutumnityBlocks.VERTICAL_MAPLE_PLANKS.get(), 5, 20);
		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_BOOKSHELF.get(), 30, 20);
		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_POST.get(), 5, 20);
		DataUtil.registerFlammable(AutumnityBlocks.STRIPPED_MAPLE_POST.get(), 5, 20);
		DataUtil.registerFlammable(AutumnityBlocks.FOUL_BERRY_SACK.get(), 30, 60);
		DataUtil.registerFlammable(AutumnityBlocks.TURKEY_EGG_CRATE.get(), 5, 20);
	}

	private static void registerDispenserBehaviors() {
		DispenserBlock.registerBehavior(AutumnityItems.TURKEY_EGG.get(), new AbstractProjectileDispenseBehavior() {
			@Override
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				return Util.make(new ThrownTurkeyEgg(worldIn, position.x(), position.y(), position.z()), (egg) -> {
					egg.setItem(stackIn);
				});
			}
		});
	}
}