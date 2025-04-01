package com.teamabnormals.autumnity.core.other.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class AutumnityEntityTypeTags {
	public static final TagKey<EntityType<?>> CAN_WEAR_TURKEY = entityTypeTag("can_wear_turkey");
	public static final TagKey<EntityType<?>> FOUL_BERRY_IMMUNE = entityTypeTag("foul_berry_immune");

	private static TagKey<EntityType<?>> entityTypeTag(String name) {
		return TagUtil.entityTypeTag(Autumnity.MOD_ID, name);
	}
}
