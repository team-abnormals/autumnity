package com.teamabnormals.autumnity.core.other;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = Autumnity.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class AutumnityDataMaps {
	public static final DataMapType<Item, JackOLantern> JACK_O_LANTERNS = DataMapType.builder(Autumnity.location("jack_o_lanterns"), Registries.ITEM, JackOLantern.CODEC).synced(JackOLantern.CODEC, false).build();

	@SubscribeEvent
	public static void registerDataMaps(RegisterDataMapTypesEvent event) {
		event.register(JACK_O_LANTERNS);
	}

	public record JackOLantern(Holder<Block> jackOLantern, Holder<Block> largeJackOLantern) {
		public static final Codec<JackOLantern> CODEC = RecordCodecBuilder.create(in -> in.group(
				RegistryFixedCodec.create(Registries.BLOCK).fieldOf("jack_o_lantern").forGetter(JackOLantern::jackOLantern),
				RegistryFixedCodec.create(Registries.BLOCK).fieldOf("large_jack_o_lantern_slice").forGetter(JackOLantern::largeJackOLantern)
		).apply(in, JackOLantern::new));
	}
}