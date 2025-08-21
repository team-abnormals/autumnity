package com.teamabnormals.autumnity.core.data.server;

import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
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
				.add(AutumnityBlocks.PANCAKE.getId(), new Compostable(0.85F), false)
				.add(AutumnityBlocks.GIANT_PUMPKIN_CHUNK.getId(), new Compostable(0.65F), false)
				.add(AutumnityBlocks.GIANT_CARVED_PUMPKIN_CHUNK.getId(), new Compostable(0.65F), false)
				.add(AutumnityBlocks.MAPLE_LEAVES.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.YELLOW_MAPLE_LEAVES.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.ORANGE_MAPLE_LEAVES.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.RED_MAPLE_LEAVES.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.MAPLE_SAPLING.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.YELLOW_MAPLE_SAPLING.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.ORANGE_MAPLE_SAPLING.getId(), new Compostable(0.3F), false)
				.add(AutumnityBlocks.RED_MAPLE_SAPLING.getId(), new Compostable(0.3F), false);
	}
}