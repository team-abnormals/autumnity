package com.teamabnormals.autumnity.core.registry.datapack;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class AutumnityPaintingVariants {
	public static final ResourceKey<PaintingVariant> SNAIL = create("snail");
	public static final ResourceKey<PaintingVariant> PUMPKIN = create("pumpkin");

	public static void bootstrap(BootstrapContext<PaintingVariant> context) {
		register(context, SNAIL, 2, 2);
		register(context, PUMPKIN, 2, 1);
	}

	private static ResourceKey<PaintingVariant> create(String name) {
		return ResourceKey.create(Registries.PAINTING_VARIANT, Autumnity.location(name));
	}

	private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> key, int width, int height) {
		context.register(key, new PaintingVariant(width, height, key.location()));
	}
}