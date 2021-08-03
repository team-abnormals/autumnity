package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.abnormals_core.common.potion.AbnormalsEffect;
import com.minecraftabnormals.autumnity.core.Autumnity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityEffects {
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Autumnity.MOD_ID);

	public static final RegistryObject<Effect> EXTENSION = EFFECTS.register("extension", () -> new AbnormalsEffect(EffectType.BENEFICIAL, 16767620));
	public static final RegistryObject<Effect> FOUL_TASTE = EFFECTS.register("foul_taste", () -> new AbnormalsEffect(EffectType.BENEFICIAL, 15363616));
}