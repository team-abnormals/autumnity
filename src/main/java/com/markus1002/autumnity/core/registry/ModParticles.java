package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.particle.FallingLeafParticle;
import com.markus1002.autumnity.core.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles
{
	public static final BasicParticleType FALLING_LEAF = new BasicParticleType(false);

	@OnlyIn(Dist.CLIENT)
	public static void registerFactories()
	{
		Minecraft.getInstance().particles.registerFactory(FALLING_LEAF, FallingLeafParticle.Factory::new);
	}
	
    @SubscribeEvent
    public static void registerParticles(RegistryEvent.Register<ParticleType<?>> event)
    {
    	registerParticle(FALLING_LEAF, "falling_leaf");
	}
	
	private static void registerParticle(ParticleType<?> particle, String name)
	{
		particle.setRegistryName(Reference.location(name));
		ForgeRegistries.PARTICLE_TYPES.register(particle);
	}
}