package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityPaintings {
	public static final DeferredRegister<Motive> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, Autumnity.MOD_ID);

	public static final RegistryObject<Motive> SNAIL = PAINTINGS.register("snail", () -> new Motive(32, 32));
	public static final RegistryObject<Motive> PUMPKIN = PAINTINGS.register("pumpkin", () -> new Motive(32, 16));
}