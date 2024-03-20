package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBiomeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBiomes;
import com.teamabnormals.blueprint.core.other.tags.BlueprintBiomeTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AutumnityBiomeTagsProvider extends BiomeTagsProvider {

	public AutumnityBiomeTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Autumnity.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(AutumnityBiomeTags.HAS_MAPLE_HUT).add(AutumnityBiomes.MAPLE_FOREST, AutumnityBiomes.PUMPKIN_FIELDS);
		this.tag(AutumnityBiomeTags.HAS_SNAIL).add(AutumnityBiomes.MAPLE_FOREST, AutumnityBiomes.PUMPKIN_FIELDS);
		this.tag(AutumnityBiomeTags.HAS_TURKEY).add(AutumnityBiomes.MAPLE_FOREST, AutumnityBiomes.PUMPKIN_FIELDS);
		this.tag(AutumnityBiomeTags.HAS_MAPLE_TREE).add(Biomes.FOREST, Biomes.WINDSWEPT_FOREST, Biomes.FLOWER_FOREST);
		this.tag(AutumnityBiomeTags.HAS_YELLOW_MAPLE_TREE).add(Biomes.FOREST);
		this.tag(AutumnityBiomeTags.HAS_ORANGE_MAPLE_TREE).add(Biomes.DARK_FOREST);
		this.tag(AutumnityBiomeTags.HAS_RED_MAPLE_TREE).add(Biomes.TAIGA, Biomes.WINDSWEPT_FOREST);

		this.tag(BlueprintBiomeTags.IS_GRASSLAND).add(AutumnityBiomes.PUMPKIN_FIELDS);
		this.tag(BiomeTags.IS_FOREST).add(AutumnityBiomes.MAPLE_FOREST);
		this.tag(BiomeTags.IS_OVERWORLD).add(AutumnityBiomes.MAPLE_FOREST, AutumnityBiomes.PUMPKIN_FIELDS);
		this.tag(BiomeTags.HAS_RUINED_PORTAL_STANDARD).add(AutumnityBiomes.PUMPKIN_FIELDS);
		this.tag(BiomeTags.HAS_MINESHAFT).add(AutumnityBiomes.PUMPKIN_FIELDS);
		this.tag(BiomeTags.STRONGHOLD_BIASED_TO).add(AutumnityBiomes.MAPLE_FOREST, AutumnityBiomes.PUMPKIN_FIELDS);
	}
}