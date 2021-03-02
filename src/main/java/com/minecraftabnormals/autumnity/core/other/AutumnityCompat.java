package com.minecraftabnormals.autumnity.core.other;

import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.minecraftabnormals.autumnity.common.entity.projectile.TurkeyEggEntity;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class AutumnityCompat {
	public static final Item ENDER_TORCH = ForgeRegistries.ITEMS.getValue(new ResourceLocation("endergetic", "ender_torch"));
	public static final Item CURSED_TORCH = ForgeRegistries.ITEMS.getValue(new ResourceLocation("caverns_and_chasms", "cursed_torch"));
	public static final Block YUCCA_GATEAU = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("atmospheric", "yucca_gateau"));

	public static void registerCompostables() {
		DataUtil.registerCompostable(AutumnityItems.FOUL_BERRIES.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityItems.FOUL_BERRY_PIPS.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityItems.PUMPKIN_BREAD.get(), 0.85F);
		DataUtil.registerCompostable(AutumnityBlocks.AUTUMN_CROCUS.get(), 0.65F);

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

		DataUtil.registerCompostable(AutumnityBlocks.MAPLE_LEAF_CARPET.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), 0.3F);
		DataUtil.registerCompostable(AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), 0.3F);

		DataUtil.registerCompostable(AutumnityBlocks.FOUL_BERRY_SACK.get(), 1.0F);
	}

	public static void registerFlammables() {
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

		DataUtil.registerFlammable(AutumnityBlocks.MAPLE_LEAF_CARPET.get(), 30, 60);
		DataUtil.registerFlammable(AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), 30, 60);
		DataUtil.registerFlammable(AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), 30, 60);
		DataUtil.registerFlammable(AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), 30, 60);
		
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

	public static void registerDispenserBehaviors() {
		DispenserBlock.registerDispenseBehavior(AutumnityItems.TURKEY_EGG.get(), new ProjectileDispenseBehavior() {
			protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				return Util.make(new TurkeyEggEntity(worldIn, position.getX(), position.getY(), position.getZ()), (egg) -> {
					egg.setItem(stackIn);
				});
			}
		});
	}
}