package com.markus1002.autumnity.core.util;

import com.google.common.collect.Maps;
import com.markus1002.autumnity.common.dispenser.DispenseModBoatBehavior;
import com.markus1002.autumnity.common.entity.item.boat.ModBoatEntity;
import com.markus1002.autumnity.core.registry.ModBlocks;
import com.markus1002.autumnity.core.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VanillaCompatibility
{
	public static void setupVanillaCompatibility()
	{
		registerCompostable(ModBlocks.MAPLE_LEAVES.get().asItem(), 0.3F);
		registerCompostable(ModBlocks.YELLOW_MAPLE_LEAVES.get().asItem(), 0.3F);
		registerCompostable(ModBlocks.ORANGE_MAPLE_LEAVES.get().asItem(), 0.3F);
		registerCompostable(ModBlocks.RED_MAPLE_LEAVES.get().asItem(), 0.3F);

		registerCompostable(ModBlocks.MAPLE_SAPLING.get(), 0.3F);
		registerCompostable(ModBlocks.YELLOW_MAPLE_SAPLING.get(), 0.3F);
		registerCompostable(ModBlocks.ORANGE_MAPLE_SAPLING.get(), 0.3F);
		registerCompostable(ModBlocks.RED_MAPLE_SAPLING.get(), 0.3F);

		registerCompostable(ModBlocks.MAPLE_LEAF_CARPET.get(), 0.35F);
		registerCompostable(ModBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), 0.35F);
		registerCompostable(ModBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), 0.35F);
		registerCompostable(ModBlocks.RED_MAPLE_LEAF_CARPET.get(), 0.35F);

		registerFlammable(ModBlocks.MAPLE_LOG.get(), 5, 5);
		registerFlammable(ModBlocks.MAPLE_WOOD.get(), 5, 5);
		registerFlammable(ModBlocks.STRIPPED_MAPLE_LOG.get(), 5, 5);
		registerFlammable(ModBlocks.STRIPPED_MAPLE_WOOD.get(), 5, 5);
		registerFlammable(ModBlocks.SAPPY_MAPLE_LOG.get(), 5, 5);
		registerFlammable(ModBlocks.SAPPY_MAPLE_WOOD.get(), 5, 5);

		registerFlammable(ModBlocks.MAPLE_PLANKS.get(), 5, 20);
		registerFlammable(ModBlocks.MAPLE_STAIRS.get(), 5, 20);
		registerFlammable(ModBlocks.MAPLE_SLAB.get(), 5, 20);

		registerFlammable(ModBlocks.MAPLE_FENCE.get(), 5, 20);
		registerFlammable(ModBlocks.MAPLE_FENCE_GATE.get(), 5, 20);

		registerFlammable(ModBlocks.MAPLE_LEAVES.get(), 30, 60);
		registerFlammable(ModBlocks.YELLOW_MAPLE_LEAVES.get(), 30, 60);
		registerFlammable(ModBlocks.ORANGE_MAPLE_LEAVES.get(), 30, 60);
		registerFlammable(ModBlocks.RED_MAPLE_LEAVES.get(), 30, 60);

		registerFlammable(ModBlocks.MAPLE_LEAF_CARPET.get(), 30, 60);
		registerFlammable(ModBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), 30, 60);
		registerFlammable(ModBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), 30, 60);
		registerFlammable(ModBlocks.RED_MAPLE_LEAF_CARPET.get(), 30, 60);

		registerStrippable(ModBlocks.MAPLE_LOG.get(), ModBlocks.STRIPPED_MAPLE_LOG.get());
		registerStrippable(ModBlocks.MAPLE_WOOD.get(), ModBlocks.STRIPPED_MAPLE_WOOD.get());
		
		registerFlammable(ModBlocks.MAPLE_VERTICAL_SLAB.get(), 5, 20);
		registerFlammable(ModBlocks.VERTICAL_MAPLE_PLANKS.get(), 5, 20);
		registerFlammable(ModBlocks.MAPLE_BOOKSHELF.get(), 30, 20);

		DispenserBlock.registerDispenseBehavior(ModItems.MAPLE_BOAT, new DispenseModBoatBehavior(ModBoatEntity.BoatType.MAPLE));
	}

	@OnlyIn(Dist.CLIENT)
	public static void setupVanillaCompatibilityClient()
	{
		BlockColors blockcolors = Minecraft.getInstance().getBlockColors();
		ItemColors itemcolors = Minecraft.getInstance().getItemColors();

		blockcolors.register((state, reader, pos, tintIndex) -> {
			return reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.getDefault();
		}, ModBlocks.MAPLE_LEAVES.get(), ModBlocks.MAPLE_LEAF_CARPET.get());

		blockcolors.register((state, reader, pos, tintIndex) -> {
			return 12665871;
		}, ModBlocks.RED_MAPLE_LEAVES.get(), ModBlocks.RED_MAPLE_LEAF_CARPET.get());

		blockcolors.register((state, reader, pos, tintIndex) -> {
			return 16745768;
		}, ModBlocks.ORANGE_MAPLE_LEAVES.get(), ModBlocks.ORANGE_MAPLE_LEAF_CARPET.get());

		blockcolors.register((state, reader, pos, tintIndex) -> {
			return 16760576;
		}, ModBlocks.YELLOW_MAPLE_LEAVES.get(), ModBlocks.YELLOW_MAPLE_LEAF_CARPET.get());

		itemcolors.register((stack, tintIndex) -> {
			BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
			return blockcolors.getColor(blockstate, (IEnviromentBlockReader)null, (BlockPos)null, tintIndex);
		}, ModBlocks.MAPLE_LEAVES.get(), ModBlocks.RED_MAPLE_LEAVES.get(), ModBlocks.ORANGE_MAPLE_LEAVES.get(), ModBlocks.YELLOW_MAPLE_LEAVES.get(),
				ModBlocks.MAPLE_LEAF_CARPET.get(), ModBlocks.RED_MAPLE_LEAF_CARPET.get(), ModBlocks.ORANGE_MAPLE_LEAF_CARPET.get(),
				ModBlocks.YELLOW_MAPLE_LEAF_CARPET.get());
	}

	public static void registerCompostable(IItemProvider itemIn, float chance)
	{
		ComposterBlock.CHANCES.put(itemIn, chance);
	}

	public static void registerFlammable(Block blockIn, int encouragement, int flammability)
	{
		((FireBlock)Blocks.FIRE).setFireInfo(blockIn, encouragement, flammability);
	}

	public static void registerStrippable(Block log, Block stripped)
	{
		AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
		AxeItem.BLOCK_STRIPPING_MAP.put(log, stripped);
	}
}