package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBiomeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AutumnityBiomeTagsProvider extends BiomeTagsProvider {

	public AutumnityBiomeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Autumnity.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(BiomeTags.IS_FOREST).add(AutumnityBiomes.MAPLE_FOREST.get(), AutumnityBiomes.SPOTTED_FOREST.get(), AutumnityBiomes.SPOTTED_DARK_FOREST.get());
		this.tag(BiomeTags.IS_TAIGA).add(AutumnityBiomes.SPOTTED_TAIGA.get());
		this.tag(BiomeTags.HAS_VILLAGE_TAIGA).add(AutumnityBiomes.SPOTTED_TAIGA.get());
		this.tag(BiomeTags.HAS_WOODLAND_MANSION).add(AutumnityBiomes.SPOTTED_DARK_FOREST.get());
		this.tag(BiomeTags.HAS_RUINED_PORTAL_STANDARD).add(AutumnityBiomes.PUMPKIN_FIELDS.get());
		this.tag(BiomeTags.HAS_MINESHAFT).add(AutumnityBiomes.PUMPKIN_FIELDS.get());
		this.tag(BiomeTags.HAS_STRONGHOLD).add(AutumnityBiomes.MAPLE_FOREST.get(), AutumnityBiomes.PUMPKIN_FIELDS.get(), AutumnityBiomes.SPOTTED_FOREST.get(), AutumnityBiomes.SPOTTED_DARK_FOREST.get(), AutumnityBiomes.SPOTTED_TAIGA.get());
		this.tag(AutumnityBiomeTags.HAS_MAPLE_HUT).add(AutumnityBiomes.MAPLE_FOREST.get(), AutumnityBiomes.PUMPKIN_FIELDS.get());
	}
}