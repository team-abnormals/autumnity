package com.teamabnormals.autumnity.core.data.server;

import com.teamabnormals.autumnity.core.other.AutumnityConstants;
import com.teamabnormals.autumnity.core.other.AutumnityDataMaps;
import com.teamabnormals.autumnity.core.other.AutumnityDataMaps.JackOLantern;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class AutumnityDataMapProvider extends DataMapProvider {

	public AutumnityDataMapProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider);
	}

	@Override
	protected void gather(Provider provider) {
		this.builder(NeoForgeDataMaps.COMPOSTABLES)
				.add(AutumnityItems.FOUL_BERRIES, new Compostable(0.3F), false)
				.add(AutumnityItems.FOUL_BERRY_PIPS, new Compostable(0.3F), false)
				.add(AutumnityItems.PUMPKIN_BREAD, new Compostable(0.85F), false)
				.add(AutumnityBlocks.AUTUMN_CROCUS.getId(), new Compostable(0.65F), false)
				.add(AutumnityBlocks.DAHLIA.getId(), new Compostable(0.65F), false)
				.add(AutumnityBlocks.BLACK_DAHLIA.getId(), new Compostable(0.65F), false)
				.add(AutumnityBlocks.PANCAKE.getId(), new Compostable(0.85F), false)
				.add(AutumnityBlocks.LARGE_PUMPKIN_SLICE.getId(), new Compostable(0.65F), false)
				.add(AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.getId(), new Compostable(0.65F), false)
				.add(AutumnityBlocks.MAPLE_LEAVES.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.YELLOW_MAPLE_LEAVES.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.ORANGE_MAPLE_LEAVES.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.RED_MAPLE_LEAVES.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.MAPLE_SAPLING.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.YELLOW_MAPLE_SAPLING.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.ORANGE_MAPLE_SAPLING.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.RED_MAPLE_SAPLING.getId(), new Compostable(0.3F), false);

		this.builder(AutumnityDataMaps.JACK_O_LANTERNS)
				.add(Items.TORCH.builtInRegistryHolder(), new JackOLantern(Blocks.JACK_O_LANTERN.builtInRegistryHolder(), AutumnityBlocks.LARGE_JACK_O_LANTERN_SLICE), false)
				.add(Items.SOUL_TORCH.builtInRegistryHolder(), new JackOLantern(AutumnityBlocks.SOUL_JACK_O_LANTERN, AutumnityBlocks.LARGE_SOUL_JACK_O_LANTERN_SLICE), false)
				.add(Items.REDSTONE_TORCH.builtInRegistryHolder(), new JackOLantern(AutumnityBlocks.REDSTONE_JACK_O_LANTERN, AutumnityBlocks.LARGE_REDSTONE_JACK_O_LANTERN_SLICE), false)
				.add(AutumnityConstants.ENDER_TORCH, new JackOLantern(AutumnityBlocks.ENDER_JACK_O_LANTERN, AutumnityBlocks.LARGE_ENDER_JACK_O_LANTERN_SLICE), false, new ModLoadedCondition(AutumnityConstants.ENDERGETIC))
				.add(AutumnityConstants.CUPRIC_TORCH, new JackOLantern(AutumnityBlocks.CUPRIC_JACK_O_LANTERN, AutumnityBlocks.LARGE_CUPRIC_JACK_O_LANTERN_SLICE), false, new ModLoadedCondition(AutumnityConstants.CAVERNS_AND_CHASMS));
	}
}