package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StructureTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AutumnityStructureTagsProvider extends StructureTagsProvider {

	public AutumnityStructureTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Autumnity.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(StructureTags.CATS_SPAWN_IN).addOptional(new ResourceLocation(Autumnity.MOD_ID, "maple_hut"));
		this.tag(StructureTags.CATS_SPAWN_AS_BLACK).addOptional(new ResourceLocation(Autumnity.MOD_ID, "maple_hut"));
	}
}