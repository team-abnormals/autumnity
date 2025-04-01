package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.client.model.SnailModel;
import com.teamabnormals.autumnity.client.model.TurkeyModel;
import com.teamabnormals.autumnity.client.renderer.entity.SnailRenderer;
import com.teamabnormals.autumnity.client.renderer.entity.TurkeyEggRenderer;
import com.teamabnormals.autumnity.client.renderer.entity.TurkeyRenderer;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(modid = Autumnity.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AutumnityModelLayers {
	public static final ModelLayerLocation SNAIL = register("snail");
	public static final ModelLayerLocation TURKEY = register("turkey");

	public static ModelLayerLocation register(String name) {
		return register(name, "main");
	}

	public static ModelLayerLocation register(String name, String layer) {
		return new ModelLayerLocation(Autumnity.location(name), layer);
	}

	@SubscribeEvent
	public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
		event.registerLayerDefinition(SNAIL, SnailModel::createBodyLayer);
		event.registerLayerDefinition(TURKEY, TurkeyModel::createBodyLayer);
	}

	@SubscribeEvent
	public static void registerRenderers(RegisterRenderers event) {
		event.registerEntityRenderer(AutumnityEntityTypes.SNAIL.get(), SnailRenderer::new);
		event.registerEntityRenderer(AutumnityEntityTypes.TURKEY.get(), TurkeyRenderer::new);
		event.registerEntityRenderer(AutumnityEntityTypes.TURKEY_EGG.get(), TurkeyEggRenderer::new);
	}
}
