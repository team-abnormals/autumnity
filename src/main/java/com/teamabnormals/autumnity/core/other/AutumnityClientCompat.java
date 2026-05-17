package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.woodworks.core.Woodworks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Woodworks.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AutumnityClientCompat {

	public static void register() {
		AutumnityBlocks.setupTabEditors();
		AutumnityItems.setupTabEditors();
		registerRenderLayers();
		registerBlockColors();
	}

	private static void registerRenderLayers() {
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.SNAIL_GOO.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.SNAIL_GOO_BLOCK.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.FOUL_BERRY_BUSH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.AUTUMN_CROCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.POTTED_AUTUMN_CROCUS.get(), RenderType.cutout());
		
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.DAHLIA.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.POTTED_DAHLIA.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.BLACK_DAHLIA.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.POTTED_BLACK_DAHLIA.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.MAPLE_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.MAPLE_TRAPDOOR.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.MAPLE_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.YELLOW_MAPLE_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.ORANGE_MAPLE_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.RED_MAPLE_SAPLING.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.POTTED_MAPLE_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.POTTED_YELLOW_MAPLE_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.POTTED_ORANGE_MAPLE_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.POTTED_RED_MAPLE_SAPLING.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(AutumnityBlocks.POTTED_FOUL_BERRIES.get(), RenderType.cutout());
	}

	private static void registerBlockColors() {
		BlockColors blockcolors = Minecraft.getInstance().getBlockColors();
		ItemColors itemcolors = Minecraft.getInstance().getItemColors();

		blockcolors.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : 0xA1C440, AutumnityBlocks.MAPLE_LEAVES.get(), AutumnityBlocks.MAPLE_LEAF_PILE.get());

		itemcolors.register((stack, tintIndex) -> {
					BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
					return blockcolors.getColor(blockstate, null, null, tintIndex);
				},
				AutumnityBlocks.MAPLE_LEAVES.get(), AutumnityBlocks.MAPLE_LEAF_PILE.get());
	}
}