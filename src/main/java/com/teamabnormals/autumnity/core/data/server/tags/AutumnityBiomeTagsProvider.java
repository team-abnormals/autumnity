package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBiomeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBiomes;
import com.teamabnormals.blueprint.core.other.tags.BlueprintBiomeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AutumnityBiomeTagsProvider extends BiomeTagsProvider {

	public AutumnityBiomeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Autumnity.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(AutumnityBiomeTags.HAS_MAPLE_HUT).add(AutumnityBiomes.MAPLE_FOREST.get(), AutumnityBiomes.PUMPKIN_FIELDS.get());
		this.tag(AutumnityBiomeTags.HAS_SNAIL).add(AutumnityBiomes.MAPLE_FOREST.get(), AutumnityBiomes.PUMPKIN_FIELDS.get());
		this.tag(AutumnityBiomeTags.HAS_TURKEY).add(AutumnityBiomes.MAPLE_FOREST.get(), AutumnityBiomes.PUMPKIN_FIELDS.get());
		this.tag(AutumnityBiomeTags.HAS_MAPLE_TREE).add(Biomes.FOREST, Biomes.WINDSWEPT_FOREST, Biomes.FLOWER_FOREST);
		this.tag(AutumnityBiomeTags.HAS_YELLOW_MAPLE_TREE).add(Biomes.FOREST);
		this.tag(AutumnityBiomeTags.HAS_ORANGE_MAPLE_TREE).add(Biomes.DARK_FOREST);
		this.tag(AutumnityBiomeTags.HAS_RED_MAPLE_TREE).add(Biomes.TAIGA, Biomes.WINDSWEPT_FOREST);

		this.tag(BlueprintBiomeTags.IS_GRASSLAND).add(AutumnityBiomes.PUMPKIN_FIELDS.get());
		this.tag(BiomeTags.IS_FOREST).add(AutumnityBiomes.MAPLE_FOREST.get());
		this.tag(BiomeTags.IS_OVERWORLD).add(AutumnityBiomes.MAPLE_FOREST.get(), AutumnityBiomes.PUMPKIN_FIELDS.get());
		this.tag(BiomeTags.HAS_RUINED_PORTAL_STANDARD).add(AutumnityBiomes.PUMPKIN_FIELDS.get());
		this.tag(BiomeTags.HAS_MINESHAFT).add(AutumnityBiomes.PUMPKIN_FIELDS.get());
		this.tag(BiomeTags.STRONGHOLD_BIASED_TO).add(AutumnityBiomes.MAPLE_FOREST.get(), AutumnityBiomes.PUMPKIN_FIELDS.get());
	}
}