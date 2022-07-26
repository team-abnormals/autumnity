package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class AutumnityModelLayers {
	public static final ModelLayerLocation SNAIL = register("snail");
	public static final ModelLayerLocation TURKEY = register("turkey");

	public static ModelLayerLocation register(String name) {
		return register(name, "main");
	}

	public static ModelLayerLocation register(String name, String layer) {
		return new ModelLayerLocation(new ResourceLocation(Autumnity.MOD_ID, name), layer);
	}
}
