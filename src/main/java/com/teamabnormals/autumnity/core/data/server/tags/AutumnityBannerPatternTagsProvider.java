package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBannerPatternTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBannerPatterns;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AutumnityBannerPatternTagsProvider extends BannerPatternTagsProvider {

	public AutumnityBannerPatternTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Autumnity.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(AutumnityBannerPatternTags.PATTERN_ITEM_MAPLE_LEAF).add(AutumnityBannerPatterns.MAPLE_LEAF.get());
		this.tag(AutumnityBannerPatternTags.PATTERN_ITEM_SWIRL).add(AutumnityBannerPatterns.SWIRL.get());
	}
}