package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.client.particle.FallingMapleLeafParticle;
import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = Autumnity.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AutumnityParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, Autumnity.MOD_ID);

	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_MAPLE_LEAF = PARTICLE_TYPES.register("falling_maple_leaf", () -> new SimpleParticleType(false));

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerParticleTypes(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(FALLING_MAPLE_LEAF.get(), FallingMapleLeafParticle.Factory::new);
	}
}