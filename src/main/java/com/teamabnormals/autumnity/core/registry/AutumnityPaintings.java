package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityPaintings {
	public static final DeferredRegister<PaintingVariant> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Autumnity.MOD_ID);

	public static final RegistryObject<PaintingVariant> SNAIL = PAINTINGS.register("snail", () -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> PUMPKIN = PAINTINGS.register("pumpkin", () -> new PaintingVariant(32, 16));
}