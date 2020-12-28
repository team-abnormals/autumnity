package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.autumnity.core.Autumnity;
import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityPaintings {
	public static final DeferredRegister<PaintingType> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, Autumnity.MOD_ID);

	public static final RegistryObject<PaintingType> SNAIL = PAINTINGS.register("snail", () -> new PaintingType(32, 32));
	public static final RegistryObject<PaintingType> PUMPKIN = PAINTINGS.register("pumpkin", () -> new PaintingType(32, 16));
}