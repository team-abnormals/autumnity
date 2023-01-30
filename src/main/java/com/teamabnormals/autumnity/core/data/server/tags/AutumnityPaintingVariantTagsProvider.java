package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityPaintings;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AutumnityPaintingVariantTagsProvider extends PaintingVariantTagsProvider {

	public AutumnityPaintingVariantTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Autumnity.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(PaintingVariantTags.PLACEABLE).add(AutumnityPaintings.SNAIL.get(), AutumnityPaintings.PUMPKIN.get());
	}
}