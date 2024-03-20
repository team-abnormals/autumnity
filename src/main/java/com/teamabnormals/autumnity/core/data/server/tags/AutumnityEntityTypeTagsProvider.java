package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityEntityTypeTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AutumnityEntityTypeTagsProvider extends EntityTypeTagsProvider {

	public AutumnityEntityTypeTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Autumnity.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(AutumnityEntityTypeTags.CAN_WEAR_TURKEY).add(EntityType.PLAYER, EntityType.ZOMBIE, EntityType.HUSK, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIFIED_PIGLIN).addTag(EntityTypeTags.SKELETONS);
	}
}
