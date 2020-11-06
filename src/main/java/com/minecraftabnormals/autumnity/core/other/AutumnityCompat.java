package com.minecraftabnormals.autumnity.core.other;

import com.minecraftabnormals.autumnity.common.entity.projectile.TurkeyEggEntity;
import com.minecraftabnormals.autumnity.core.Autumnity;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.abnormals_core.core.utils.DataUtils;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class AutumnityCompat
{
	public static void registerCompostables()
	{
		DataUtils.registerCompostable(AutumnityItems.FOUL_BERRIES.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityItems.FOUL_BERRY_PIPS.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityItems.PUMPKIN_BREAD.get(), 0.85F);
		DataUtils.registerCompostable(AutumnityBlocks.AUTUMN_CROCUS.get(), 0.65F);

		DataUtils.registerCompostable(AutumnityBlocks.LARGE_PUMPKIN_SLICE.get(), 0.65F);
		DataUtils.registerCompostable(AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get(), 0.65F);

		DataUtils.registerCompostable(AutumnityBlocks.MAPLE_LEAVES.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.RED_MAPLE_LEAVES.get(), 0.3F);

		DataUtils.registerCompostable(AutumnityBlocks.MAPLE_SAPLING.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.YELLOW_MAPLE_SAPLING.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.ORANGE_MAPLE_SAPLING.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.RED_MAPLE_SAPLING.get(), 0.3F);

		DataUtils.registerCompostable( AutumnityBlocks.MAPLE_LEAF_CARPET.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), 0.3F);

		DataUtils.registerCompostable(AutumnityBlocks.FOUL_BERRY_SACK.get(), 1.0F);
	}

	public static void registerFlammables()
	{
		DataUtils.registerFlammable(AutumnityBlocks.FOUL_BERRY_BUSH_PIPS.get(), 60, 100);
		DataUtils.registerFlammable(AutumnityBlocks.FOUL_BERRY_BUSH.get(), 60, 100);
		DataUtils.registerFlammable(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get(), 60, 100);
		DataUtils.registerFlammable(AutumnityBlocks.AUTUMN_CROCUS.get(), 60, 100);

		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_LOG.get(), 5, 5);
		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_WOOD.get(), 5, 5);
		DataUtils.registerFlammable(AutumnityBlocks.STRIPPED_MAPLE_LOG.get(), 5, 5);
		DataUtils.registerFlammable(AutumnityBlocks.STRIPPED_MAPLE_WOOD.get(), 5, 5);
		DataUtils.registerFlammable(AutumnityBlocks.SAPPY_MAPLE_LOG.get(), 5, 5);
		DataUtils.registerFlammable(AutumnityBlocks.SAPPY_MAPLE_WOOD.get(), 5, 5);

		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_PLANKS.get(), 5, 20);
		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_STAIRS.get(), 5, 20);
		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_SLAB.get(), 5, 20);

		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_FENCE.get(), 5, 20);
		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_FENCE_GATE.get(), 5, 20);

		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_LEAVES.get(), 30, 60);
		DataUtils.registerFlammable(AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), 30, 60);
		DataUtils.registerFlammable(AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), 30, 60);
		DataUtils.registerFlammable(AutumnityBlocks.RED_MAPLE_LEAVES.get(), 30, 60);

		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_LEAF_CARPET.get(), 30, 60);
		DataUtils.registerFlammable(AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), 30, 60);
		DataUtils.registerFlammable(AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), 30, 60);
		DataUtils.registerFlammable(AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), 30, 60);

		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_BEEHIVE.get(), 5, 20);
		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get(), 5, 20);
		DataUtils.registerFlammable(AutumnityBlocks.VERTICAL_MAPLE_PLANKS.get(), 5, 20);
		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_BOOKSHELF.get(), 30, 20);
		DataUtils.registerFlammable(AutumnityBlocks.FOUL_BERRY_SACK.get(), 30, 60);
	}

	public static void registerDispenserBehaviors()
	{
		Autumnity.REGISTRY_HELPER.processSpawnEggDispenseBehaviors();
		DispenserBlock.registerDispenseBehavior(AutumnityItems.TURKEY_EGG.get(), new ProjectileDispenseBehavior()
		{
			protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
			{
				return Util.make(new TurkeyEggEntity(worldIn, position.getX(), position.getY(), position.getZ()), (egg) -> {
					egg.setItem(stackIn);
				});
			}
		});
	}
}