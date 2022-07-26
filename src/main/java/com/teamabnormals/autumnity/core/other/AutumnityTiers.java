package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.autumnity.core.registry.AutumnitySoundEvents;
import com.teamabnormals.blueprint.core.api.BlueprintArmorMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public class AutumnityTiers {
	public static final BlueprintArmorMaterial SNAIL_SHELL = new BlueprintArmorMaterial(new ResourceLocation(Autumnity.MOD_ID, "snail_shell"), 23, new int[]{0, 0, 5, 0}, 9, AutumnitySoundEvents.ITEM_ARMOR_EQUIP_SNAIL, 0.0F, 0.0F, () -> Ingredient.of(AutumnityItems.SNAIL_SHELL_PIECE.get()));
}
