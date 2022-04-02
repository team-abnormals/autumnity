package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.blueprint.core.api.BlueprintArmorMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.crafting.Ingredient;

public class AutumnityTiers {
	public static final BlueprintArmorMaterial SNAIL_SHELL = new BlueprintArmorMaterial(new ResourceLocation(Autumnity.MOD_ID, "snail_shell"), 23, new int[]{0, 0, 5, 0}, 9, () -> SoundEvents.ARMOR_EQUIP_TURTLE, 0.0F, 0.0F, () -> Ingredient.of(AutumnityItems.SNAIL_SHELL_PIECE.get()));
}
