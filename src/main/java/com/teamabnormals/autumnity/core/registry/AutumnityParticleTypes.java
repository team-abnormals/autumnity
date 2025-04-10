package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.client.particle.FallingMapleLeafParticle;
import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = Autumnity.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AutumnityParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, Autumnity.MOD_ID);

	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAPLE_LEAVES = PARTICLE_TYPES.register("maple_leaves", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YELLOW_MAPLE_LEAVES = PARTICLE_TYPES.register("yellow_maple_leaves", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ORANGE_MAPLE_LEAVES = PARTICLE_TYPES.register("orange_maple_leaves", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RED_MAPLE_LEAVES = PARTICLE_TYPES.register("red_maple_leaves", () -> new SimpleParticleType(false));

	@SubscribeEvent
	public static void registerParticleTypes(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(MAPLE_LEAVES.get(), FallingMapleLeafParticle.Factory::new);
		event.registerSpriteSet(YELLOW_MAPLE_LEAVES.get(), FallingMapleLeafParticle.Factory::new);
		event.registerSpriteSet(ORANGE_MAPLE_LEAVES.get(), FallingMapleLeafParticle.Factory::new);
		event.registerSpriteSet(RED_MAPLE_LEAVES.get(), FallingMapleLeafParticle.Factory::new);
	}
}