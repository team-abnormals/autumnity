package com.teamabnormals.autumnity.core.data.client;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.teamabnormals.autumnity.core.registry.AutumnityItems.*;

public class AutumnityItemModelProvider extends BlueprintItemModelProvider {

	public AutumnityItemModelProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Autumnity.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
		this.generatedItem(MAPLE_BOAT.getFirst(), MAPLE_BOAT.getSecond(), MAPLE_FURNACE_BOAT, LARGE_MAPLE_BOAT);
		this.generatedItem(SAP_BOTTLE, SYRUP_BOTTLE, FOUL_BERRIES, FOUL_BERRY_PIPS, FOUL_SOUP, PUMPKIN_BREAD);
		this.generatedItem(SNAIL_SHELL_PIECE, TURKEY_EGG);
		this.handheldItem(TURKEY_PIECE, COOKED_TURKEY_PIECE);
		this.generatedItem(MAPLE_LEAF_BANNER_PATTERN, SWIRL_BANNER_PATTERN);
		this.spawnEggItem(SNAIL_SPAWN_EGG, TURKEY_SPAWN_EGG);
		this.trimmableArmorItem(SNAIL_SHELL_CHESTPLATE);
	}
}