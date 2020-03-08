package com.markus1002.autumnity.core.util;

import com.google.common.collect.Maps;
import com.markus1002.autumnity.common.dispenser.DispenseModBoatBehavior;
import com.markus1002.autumnity.common.entity.item.boat.ModBoatEntity;
import com.markus1002.autumnity.core.registry.ModBlocks;
import com.markus1002.autumnity.core.registry.ModItems;
import com.markus1002.autumnity.core.util.compatibility.Quark;

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
		registerCompostable(ModBlocks.MAPLE_LEAVES.asItem(), 0.3F);
		registerCompostable(ModBlocks.YELLOW_MAPLE_LEAVES.asItem(), 0.3F);
		registerCompostable(ModBlocks.ORANGE_MAPLE_LEAVES.asItem(), 0.3F);
		registerCompostable(ModBlocks.RED_MAPLE_LEAVES.asItem(), 0.3F);
		
		registerCompostable(ModBlocks.MAPLE_SAPLING, 0.3F);
		registerCompostable(ModBlocks.YELLOW_MAPLE_SAPLING, 0.3F);
		registerCompostable(ModBlocks.ORANGE_MAPLE_SAPLING, 0.3F);
		registerCompostable(ModBlocks.RED_MAPLE_SAPLING, 0.3F);
		
		registerCompostable(ModBlocks.YELLOW_MAPLE_LEAF_CARPET, 0.35F);
		registerCompostable(ModBlocks.ORANGE_MAPLE_LEAF_CARPET, 0.35F);
		registerCompostable(ModBlocks.RED_MAPLE_LEAF_CARPET, 0.35F);
		
		registerFlammable(ModBlocks.MAPLE_LOG, 5, 5);
		registerFlammable(ModBlocks.MAPLE_WOOD, 5, 5);
		registerFlammable(ModBlocks.STRIPPED_MAPLE_LOG, 5, 5);
		registerFlammable(ModBlocks.STRIPPED_MAPLE_WOOD, 5, 5);
		registerFlammable(ModBlocks.SAPPY_MAPLE_LOG, 5, 5);
		registerFlammable(ModBlocks.SAPPY_MAPLE_WOOD, 5, 5);
		
		registerFlammable(ModBlocks.MAPLE_LEAVES, 30, 60);
		registerFlammable(ModBlocks.YELLOW_MAPLE_LEAVES, 30, 60);
		registerFlammable(ModBlocks.ORANGE_MAPLE_LEAVES, 30, 60);
		registerFlammable(ModBlocks.RED_MAPLE_LEAVES, 30, 60);
		
        registerFlammable(ModBlocks.YELLOW_MAPLE_LEAF_CARPET, 30, 60);
        registerFlammable(ModBlocks.ORANGE_MAPLE_LEAF_CARPET, 30, 60);
        registerFlammable(ModBlocks.RED_MAPLE_LEAF_CARPET, 30, 60);
        
		registerFlammable(ModBlocks.MAPLE_PLANKS, 5, 20);
		registerFlammable(ModBlocks.MAPLE_STAIRS, 5, 20);
		registerFlammable(ModBlocks.MAPLE_SLAB, 5, 20);
		
		registerFlammable(ModBlocks.MAPLE_FENCE, 5, 20);
		registerFlammable(ModBlocks.MAPLE_FENCE_GATE, 5, 20);
		
		registerStrippable(ModBlocks.MAPLE_LOG, ModBlocks.STRIPPED_MAPLE_LOG);
		registerStrippable(ModBlocks.MAPLE_WOOD, ModBlocks.STRIPPED_MAPLE_WOOD);
		
		DispenserBlock.registerDispenseBehavior(ModItems.MAPLE_BOAT, new DispenseModBoatBehavior(ModBoatEntity.BoatType.MAPLE));
		
		if (Quark.isInstalled())
		{
	        registerFlammable(ModBlocks.MAPLE_LEAF_CARPET, 30, 60);
	        
			registerFlammable(ModBlocks.MAPLE_VERTICAL_SLAB, 5, 20);
			registerFlammable(ModBlocks.VERTICAL_MAPLE_PLANKS, 5, 20);
			registerFlammable(ModBlocks.MAPLE_BOOKSHELF, 30, 20);
			
	        registerCompostable(ModBlocks.MAPLE_LEAF_CARPET, 0.35F);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void setupVanillaCompatibilityClient()
	{
		BlockColors blockcolors = Minecraft.getInstance().getBlockColors();
		ItemColors itemcolors = Minecraft.getInstance().getItemColors();

		blockcolors.register((state, reader, pos, tintIndex) -> {
			return reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.getDefault();
		}, ModBlocks.MAPLE_LEAVES, ModBlocks.MAPLE_LEAF_CARPET);

		blockcolors.register((state, reader, pos, tintIndex) -> {
			return 12665871;
		}, ModBlocks.RED_MAPLE_LEAVES, ModBlocks.RED_MAPLE_LEAF_CARPET);

		blockcolors.register((state, reader, pos, tintIndex) -> {
			return 16745768;
		}, ModBlocks.ORANGE_MAPLE_LEAVES, ModBlocks.ORANGE_MAPLE_LEAF_CARPET);

		blockcolors.register((state, reader, pos, tintIndex) -> {
			return 16760576;
		}, ModBlocks.YELLOW_MAPLE_LEAVES, ModBlocks.YELLOW_MAPLE_LEAF_CARPET);

		itemcolors.register((stack, tintIndex) -> {
			BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
			return blockcolors.getColor(blockstate, (IEnviromentBlockReader)null, (BlockPos)null, tintIndex);
		}, ModBlocks.MAPLE_LEAVES, ModBlocks.RED_MAPLE_LEAVES, ModBlocks.ORANGE_MAPLE_LEAVES, ModBlocks.YELLOW_MAPLE_LEAVES,
				ModBlocks.MAPLE_LEAF_CARPET, ModBlocks.RED_MAPLE_LEAF_CARPET, ModBlocks.ORANGE_MAPLE_LEAF_CARPET,
				ModBlocks.YELLOW_MAPLE_LEAF_CARPET);
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