package com.markus1002.autumnity.core.other;

import com.google.common.collect.Maps;
import com.markus1002.autumnity.core.registry.AutumnityBlocks;
import com.markus1002.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.abnormals_core.core.utils.DataUtils;

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
		DataUtils.registerCompostable(AutumnityItems.FOUL_BERRIES.get(), 0.3F);
		DataUtils.registerCompostable(AutumnityItems.FOUL_BERRY_PIPS.get(), 0.3F);
	    DataUtils.registerCompostable(AutumnityBlocks.AUTUMN_CROCUS.get().asItem(), 0.65F);
	    
	    DataUtils.registerCompostable(AutumnityBlocks.LARGE_PUMPKIN_SLICE.get().asItem(), 0.65F);
		
		DataUtils.registerCompostable(AutumnityBlocks.MAPLE_LEAVES.get().asItem(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.YELLOW_MAPLE_LEAVES.get().asItem(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.ORANGE_MAPLE_LEAVES.get().asItem(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.RED_MAPLE_LEAVES.get().asItem(), 0.3F);

		DataUtils.registerCompostable(AutumnityBlocks.MAPLE_SAPLING.get().asItem(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.YELLOW_MAPLE_SAPLING.get().asItem(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.ORANGE_MAPLE_SAPLING.get().asItem(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.RED_MAPLE_SAPLING.get().asItem(), 0.3F);

		DataUtils.registerCompostable( AutumnityBlocks.MAPLE_LEAF_CARPET.get().asItem(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get().asItem(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get().asItem(), 0.3F);
		DataUtils.registerCompostable(AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get().asItem(), 0.3F);
		
		DataUtils.registerCompostable(AutumnityBlocks.FOUL_BERRY_SACK.get().asItem(), 1.0F);

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
		
		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get(), 5, 20);
		DataUtils.registerFlammable(AutumnityBlocks.VERTICAL_MAPLE_PLANKS.get(), 5, 20);
		DataUtils.registerFlammable(AutumnityBlocks.MAPLE_BOOKSHELF.get(), 30, 20);
		DataUtils.registerFlammable(AutumnityBlocks.FOUL_BERRY_SACK.get(), 30, 60);
	}

	public static void setupVanillaCompatibilityClient()
	{
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.SNAIL_SLIME.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.SNAIL_SLIME_BLOCK.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.FOUL_BERRY_BUSH_PIPS.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.FOUL_BERRY_BUSH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.AUTUMN_CROCUS.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_AUTUMN_CROCUS.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_DOOR.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_TRAPDOOR.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.RED_MAPLE_LEAVES.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.YELLOW_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.ORANGE_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.RED_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_YELLOW_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_ORANGE_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_RED_MAPLE_SAPLING.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_LADDER.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_FOUL_BERRIES.get(), RenderType.getCutoutMipped());
		
		
		BlockColors blockcolors = Minecraft.getInstance().getBlockColors();
		ItemColors itemcolors = Minecraft.getInstance().getItemColors();

		blockcolors.register((state, world, pos, tintIndex) -> {
			return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefault();
		}, AutumnityBlocks.MAPLE_LEAVES.get(), AutumnityBlocks.MAPLE_LEAF_CARPET.get());

		blockcolors.register((state, world, pos, tintIndex) -> {
			return 12665871;
		}, AutumnityBlocks.RED_MAPLE_LEAVES.get(), AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get());

		blockcolors.register((state, world, pos, tintIndex) -> {
			return 16745768;
		}, AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get());

		blockcolors.register((state, world, pos, tintIndex) -> {
			return 16760576;
		}, AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get());

		itemcolors.register((stack, tintIndex) -> {
			BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
			return blockcolors.getColor(blockstate, null, null, tintIndex);
		}, AutumnityBlocks.MAPLE_LEAVES.get(), AutumnityBlocks.RED_MAPLE_LEAVES.get(), AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(),
				AutumnityBlocks.MAPLE_LEAF_CARPET.get(), AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(),
				AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get());
	}

	public static void registerStrippable(Block log, Block stripped)
	{
		AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
		AxeItem.BLOCK_STRIPPING_MAP.put(log, stripped);
	}
}