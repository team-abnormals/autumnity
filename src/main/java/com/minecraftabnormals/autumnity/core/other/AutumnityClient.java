package com.minecraftabnormals.autumnity.core.other;

import com.minecraftabnormals.autumnity.core.Autumnity;
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

public class AutumnityClient {

	public static void registerRenderLayers() {
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.SNAIL_SLIME.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.SNAIL_SLIME_BLOCK.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.FOUL_BERRY_BUSH_PIPS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.FOUL_BERRY_BUSH.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.AUTUMN_CROCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_AUTUMN_CROCUS.get(), RenderType.getCutout());

		// RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_BRANCH.get(), RenderType.getCutout());
		// RenderTypeLookup.setRenderLayer(AutumnityBlocks.LONG_MAPLE_BRANCH.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_DOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_TRAPDOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_POST.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.STRIPPED_MAPLE_POST.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.RED_MAPLE_LEAVES.get(), RenderType.getCutoutMipped());

		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), RenderType.getCutoutMipped());

		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.YELLOW_MAPLE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.ORANGE_MAPLE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.RED_MAPLE_SAPLING.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_MAPLE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_YELLOW_MAPLE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_ORANGE_MAPLE_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_RED_MAPLE_SAPLING.get(), RenderType.getCutout());
		
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_HEDGE.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.YELLOW_MAPLE_HEDGE.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.ORANGE_MAPLE_HEDGE.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.RED_MAPLE_HEDGE.get(), RenderType.getCutoutMipped());

		RenderTypeLookup.setRenderLayer(AutumnityBlocks.MAPLE_LADDER.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(AutumnityBlocks.POTTED_FOUL_BERRIES.get(), RenderType.getCutout());
	}

	public static void registerBlockColors() {
		BlockColors blockcolors = Minecraft.getInstance().getBlockColors();
		ItemColors itemcolors = Minecraft.getInstance().getItemColors();

		blockcolors.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefault(), AutumnityBlocks.MAPLE_LEAVES.get(), AutumnityBlocks.MAPLE_LEAF_CARPET.get(), AutumnityBlocks.MAPLE_HEDGE.get());
		blockcolors.register((state, world, pos, tintIndex) -> 12665871, AutumnityBlocks.RED_MAPLE_LEAVES.get(), AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.RED_MAPLE_HEDGE.get());
		blockcolors.register((state, world, pos, tintIndex) -> 16745768, AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.ORANGE_MAPLE_HEDGE.get());
		blockcolors.register((state, world, pos, tintIndex) -> 16760576, AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.YELLOW_MAPLE_HEDGE.get());

		itemcolors.register((stack, tintIndex) -> {
					BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
					return blockcolors.getColor(blockstate, null, null, tintIndex);
				}, AutumnityBlocks.MAPLE_LEAVES.get(), AutumnityBlocks.RED_MAPLE_LEAVES.get(), AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(),
				AutumnityBlocks.MAPLE_LEAF_CARPET.get(), AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get(),
				AutumnityBlocks.MAPLE_HEDGE.get(), AutumnityBlocks.RED_MAPLE_HEDGE.get(), AutumnityBlocks.ORANGE_MAPLE_HEDGE.get(), AutumnityBlocks.YELLOW_MAPLE_HEDGE.get());
	}
}