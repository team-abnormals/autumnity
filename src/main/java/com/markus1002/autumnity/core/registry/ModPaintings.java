package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.core.Reference;

import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModPaintings
{
	public static final DeferredRegister<PaintingType> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, Reference.MOD_ID);

	public static final RegistryObject<PaintingType> SNAIL = PAINTINGS.register("snail", () -> new PaintingType(32, 32));
	public static final RegistryObject<PaintingType> PUMPKIN = PAINTINGS.register("pumpkin", () -> new PaintingType(32, 16));

}