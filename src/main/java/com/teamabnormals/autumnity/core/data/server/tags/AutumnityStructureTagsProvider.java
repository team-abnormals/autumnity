package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StructureTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AutumnityStructureTagsProvider extends StructureTagsProvider {

	public AutumnityStructureTagsProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, Autumnity.MOD_ID, fileHelper);
	}

	@Override
	public void addTags() {
		this.tag(StructureTags.CATS_SPAWN_IN).addOptional(new ResourceLocation(Autumnity.MOD_ID, "maple_hut"));
		this.tag(StructureTags.CATS_SPAWN_AS_BLACK).addOptional(new ResourceLocation(Autumnity.MOD_ID, "maple_hut"));
	}
}