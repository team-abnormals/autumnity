package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityEntityTypeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AutumnityEntityTypeTagsProvider extends EntityTypeTagsProvider {

	public AutumnityEntityTypeTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Autumnity.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		//TODO: Humanoid? Entity type tag
		this.tag(AutumnityEntityTypeTags.CAN_WEAR_TURKEY).add(EntityType.PLAYER, EntityType.ZOMBIE, EntityType.HUSK, EntityType.DROWNED, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIFIED_PIGLIN).addTag(EntityTypeTags.SKELETONS);
		this.tag(AutumnityEntityTypeTags.FOUL_BERRY_IMMUNE).add(EntityType.BEE, EntityType.FOX, AutumnityEntityTypes.TURKEY.get(), AutumnityEntityTypes.SNAIL.get());
	}
}
