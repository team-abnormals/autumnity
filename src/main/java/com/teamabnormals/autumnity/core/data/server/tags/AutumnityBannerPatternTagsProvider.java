package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBannerPatternTags;
import com.teamabnormals.autumnity.core.registry.datapack.AutumnityBannerPatterns;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AutumnityBannerPatternTagsProvider extends BannerPatternTagsProvider {

	public AutumnityBannerPatternTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Autumnity.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(AutumnityBannerPatternTags.PATTERN_ITEM_MAPLE_LEAF).add(AutumnityBannerPatterns.MAPLE_LEAF);
		this.tag(AutumnityBannerPatternTags.PATTERN_ITEM_SWIRL).add(AutumnityBannerPatterns.SWIRL);
	}
}