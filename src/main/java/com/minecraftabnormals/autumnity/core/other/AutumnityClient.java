package com.minecraftabnormals.autumnity.core.other;

import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;

public class AutumnityClient
{
	public static void setRenderLayers()
	{
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.SNAIL_SLIME.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.SNAIL_SLIME_BLOCK.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.FOUL_BERRY_BUSH_PIPS.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.FOUL_BERRY_BUSH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.AUTUMN_CROCUS.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_AUTUMN_CROCUS.get(), RenderType.getCutoutMipped());

		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_BRANCH.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.LONG_MAPLE_BRANCH.get(), RenderType.getCutoutMipped());

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
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.SLIME.get(), RenderType.getTranslucent());
	}

	public static void registerBlockColors()
	{
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
				AutumnityBlocks.MAPLE_LEAF_CARPET.get(), AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get());
	}
}