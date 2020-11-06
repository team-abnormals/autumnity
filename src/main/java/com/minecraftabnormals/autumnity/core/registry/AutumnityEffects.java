package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.autumnity.common.potion.ExtensionEffect;
import com.minecraftabnormals.autumnity.common.potion.FoulTasteEffect;
import com.minecraftabnormals.autumnity.core.Reference;

import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityEffects
{
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Reference.MOD_ID);
	
	public static final RegistryObject<Effect> EXTENSION = EFFECTS.register("extension", ExtensionEffect::new);
	public static final RegistryObject<Effect> FOUL_TASTE = EFFECTS.register("foul_taste", FoulTasteEffect::new);
}