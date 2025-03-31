package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class AutumnityLootTables {
	public static final ResourceKey<LootTable> MAPLE_HUT = create("chests/maple_hut");

	private static ResourceKey<LootTable> create(String name) {
		return ResourceKey.create(Registries.LOOT_TABLE, Autumnity.location(name));
	}
}