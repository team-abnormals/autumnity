package com.markus1002.autumnity.core.util;

import com.google.common.collect.Maps;
import com.markus1002.autumnity.core.registry.ModBlocks;
import com.markus1002.autumnity.core.registry.ModItems;
import com.minecraftabnormals.abnormals_core.core.utils.DataUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;

public class VanillaCompatibility
{
	public static void setupVanillaCompatibility()
	{
		DataUtils.registerCompostable(0.3F, ModItems.FOUL_BERRIES.get());
		DataUtils.registerCompostable(0.3F, ModItems.FOUL_BERRY_PIPS.get());
	    DataUtils.registerCompostable(1.0F, ModItems.FOUL_BERRY_PIE.get());
	    DataUtils.registerCompostable(0.65F, ModBlocks.AUTUMN_CROCUS.get().asItem());
		
		DataUtils.registerCompostable(0.3F, ModBlocks.MAPLE_LEAVES.get().asItem());
		DataUtils.registerCompostable(0.3F, ModBlocks.YELLOW_MAPLE_LEAVES.get().asItem());
		DataUtils.registerCompostable(0.3F, ModBlocks.ORANGE_MAPLE_LEAVES.get().asItem());
		DataUtils.registerCompostable(0.3F, ModBlocks.RED_MAPLE_LEAVES.get().asItem());

		DataUtils.registerCompostable(0.3F, ModBlocks.MAPLE_SAPLING.get().asItem());
		DataUtils.registerCompostable(0.3F, ModBlocks.YELLOW_MAPLE_SAPLING.get().asItem());
		DataUtils.registerCompostable(0.3F, ModBlocks.ORANGE_MAPLE_SAPLING.get().asItem());
		DataUtils.registerCompostable(0.3F, ModBlocks.RED_MAPLE_SAPLING.get().asItem());

		DataUtils.registerCompostable(0.3F, ModBlocks.MAPLE_LEAF_CARPET.get().asItem());
		DataUtils.registerCompostable(0.3F, ModBlocks.YELLOW_MAPLE_LEAF_CARPET.get().asItem());
		DataUtils.registerCompostable(0.3F, ModBlocks.ORANGE_MAPLE_LEAF_CARPET.get().asItem());
		DataUtils.registerCompostable(0.3F, ModBlocks.RED_MAPLE_LEAF_CARPET.get().asItem());
		
		DataUtils.registerCompostable(1.0F, ModBlocks.FOUL_BERRY_SACK.get().asItem());

		DataUtils.registerFlammable(ModBlocks.FOUL_BERRY_BUSH_PIPS.get(), 60, 100);
		DataUtils.registerFlammable(ModBlocks.FOUL_BERRY_BUSH.get(), 60, 100);
		DataUtils.registerFlammable(ModBlocks.TALL_FOUL_BERRY_BUSH.get(), 60, 100);
		DataUtils.registerFlammable(ModBlocks.AUTUMN_CROCUS.get(), 60, 100);
		
		DataUtils.registerFlammable(ModBlocks.MAPLE_LOG.get(), 5, 5);
		DataUtils.registerFlammable(ModBlocks.MAPLE_WOOD.get(), 5, 5);
		DataUtils.registerFlammable(ModBlocks.STRIPPED_MAPLE_LOG.get(), 5, 5);
		DataUtils.registerFlammable(ModBlocks.STRIPPED_MAPLE_WOOD.get(), 5, 5);
		DataUtils.registerFlammable(ModBlocks.SAPPY_MAPLE_LOG.get(), 5, 5);
		DataUtils.registerFlammable(ModBlocks.SAPPY_MAPLE_WOOD.get(), 5, 5);

		DataUtils.registerFlammable(ModBlocks.MAPLE_PLANKS.get(), 5, 20);
		DataUtils.registerFlammable(ModBlocks.MAPLE_STAIRS.get(), 5, 20);
		DataUtils.registerFlammable(ModBlocks.MAPLE_SLAB.get(), 5, 20);

		DataUtils.registerFlammable(ModBlocks.MAPLE_FENCE.get(), 5, 20);
		DataUtils.registerFlammable(ModBlocks.MAPLE_FENCE_GATE.get(), 5, 20);

		DataUtils.registerFlammable(ModBlocks.MAPLE_LEAVES.get(), 30, 60);
		DataUtils.registerFlammable(ModBlocks.YELLOW_MAPLE_LEAVES.get(), 30, 60);
		DataUtils.registerFlammable(ModBlocks.ORANGE_MAPLE_LEAVES.get(), 30, 60);
		DataUtils.registerFlammable(ModBlocks.RED_MAPLE_LEAVES.get(), 30, 60);

		DataUtils.registerFlammable(ModBlocks.MAPLE_LEAF_CARPET.get(), 30, 60);
		DataUtils.registerFlammable(ModBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), 30, 60);
		DataUtils.registerFlammable(ModBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), 30, 60);
		DataUtils.registerFlammable(ModBlocks.RED_MAPLE_LEAF_CARPET.get(), 30, 60);
		
		DataUtils.registerFlammable(ModBlocks.MAPLE_VERTICAL_SLAB.get(), 5, 20);
		DataUtils.registerFlammable(ModBlocks.VERTICAL_MAPLE_PLANKS.get(), 5, 20);
		DataUtils.registerFlammable(ModBlocks.MAPLE_BOOKSHELF.get(), 30, 20);
	}

	public static void setupVanillaCompatibilityClient()
	{
		RenderTypeLookup.setRenderLayer(ModBlocks.SNAIL_SLIME.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.SNAIL_SLIME_BLOCK.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.FOUL_BERRY_BUSH_PIPS.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.FOUL_BERRY_BUSH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.TALL_FOUL_BERRY_BUSH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.AUTUMN_CROCUS.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_AUTUMN_CROCUS.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.MAPLE_DOOR.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.MAPLE_TRAPDOOR.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.MAPLE_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.YELLOW_MAPLE_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.ORANGE_MAPLE_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.RED_MAPLE_LEAVES.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.RED_MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.YELLOW_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.ORANGE_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.RED_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_YELLOW_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_ORANGE_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_RED_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(ModBlocks.MAPLE_LADDER.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_FOUL_BERRIES.get(), RenderType.getCutoutMipped());
		
		
		BlockColors blockcolors = Minecraft.getInstance().getBlockColors();
		ItemColors itemcolors = Minecraft.getInstance().getItemColors();

		blockcolors.register((state, world, pos, tintIndex) -> {
			return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefault();
		}, ModBlocks.MAPLE_LEAVES.get(), ModBlocks.MAPLE_LEAF_CARPET.get());

		blockcolors.register((state, world, pos, tintIndex) -> {
			return 12665871;
		}, ModBlocks.RED_MAPLE_LEAVES.get(), ModBlocks.RED_MAPLE_LEAF_CARPET.get());

		blockcolors.register((state, world, pos, tintIndex) -> {
			return 16745768;
		}, ModBlocks.ORANGE_MAPLE_LEAVES.get(), ModBlocks.ORANGE_MAPLE_LEAF_CARPET.get());

		blockcolors.register((state, world, pos, tintIndex) -> {
			return 16760576;
		}, ModBlocks.YELLOW_MAPLE_LEAVES.get(), ModBlocks.YELLOW_MAPLE_LEAF_CARPET.get());

		itemcolors.register((stack, tintIndex) -> {
			BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
			return blockcolors.getColor(blockstate, null, null, tintIndex);
		}, ModBlocks.MAPLE_LEAVES.get(), ModBlocks.RED_MAPLE_LEAVES.get(), ModBlocks.ORANGE_MAPLE_LEAVES.get(), ModBlocks.YELLOW_MAPLE_LEAVES.get(),
				ModBlocks.MAPLE_LEAF_CARPET.get(), ModBlocks.RED_MAPLE_LEAF_CARPET.get(), ModBlocks.ORANGE_MAPLE_LEAF_CARPET.get(),
				ModBlocks.YELLOW_MAPLE_LEAF_CARPET.get());
	}

	public static void registerStrippable(Block log, Block stripped)
	{
		AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
		AxeItem.BLOCK_STRIPPING_MAP.put(log, stripped);
	}
}