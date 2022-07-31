package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class AutumnityNoiseParameters {
	public static final DeferredRegister<NoiseParameters> NOISE_PARAMETERS = DeferredRegister.create(Registry.NOISE_REGISTRY, Autumnity.MOD_ID);

	public static final RegistryObject<NoiseParameters> SPOTTED_MAPLES = NOISE_PARAMETERS.register("spotted_maples", () -> new NoiseParameters(-8, 1.0D));
}
