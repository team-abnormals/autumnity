package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBiomeTags;
import com.teamabnormals.autumnity.core.registry.datapack.AutumnityBiomes;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AutumnityBiomeTagsProvider extends BiomeTagsProvider {

	public AutumnityBiomeTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Autumnity.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(AutumnityBiomes.MAPLE_FOREST,
				BiomeTags.IS_OVERWORLD, BiomeTags.IS_FOREST,
				BiomeTags.STRONGHOLD_BIASED_TO,
				AutumnityBiomeTags.IS_AUTUMNAL,
				Tags.Biomes.IS_TEMPERATE_OVERWORLD, Tags.Biomes.IS_DECIDUOUS_TREE
		);

		this.tag(AutumnityBiomes.PUMPKIN_FIELDS,
				BiomeTags.IS_OVERWORLD,
				BiomeTags.HAS_MINESHAFT, BiomeTags.HAS_RUINED_PORTAL_STANDARD, BiomeTags.STRONGHOLD_BIASED_TO,
				AutumnityBiomeTags.IS_AUTUMNAL,
				Tags.Biomes.IS_TEMPERATE_OVERWORLD, Tags.Biomes.IS_SPARSE_VEGETATION_OVERWORLD, Tags.Biomes.IS_DECIDUOUS_TREE
		);

		this.tag(AutumnityBiomeTags.HAS_MAPLE_HUT).addTag(AutumnityBiomeTags.IS_AUTUMNAL);

		this.tag(AutumnityBiomeTags.HAS_TURKEY).addTag(AutumnityBiomeTags.IS_AUTUMNAL);
		this.tag(AutumnityBiomeTags.HAS_SNAIL).addTag(AutumnityBiomeTags.IS_AUTUMNAL);

		this.tag(AutumnityBiomeTags.HAS_MAPLE_TREE).add(Biomes.FOREST, Biomes.WINDSWEPT_FOREST, Biomes.FLOWER_FOREST);
		this.tag(AutumnityBiomeTags.HAS_ORANGE_MAPLE_TREE).add(Biomes.DARK_FOREST);
		this.tag(AutumnityBiomeTags.HAS_RED_MAPLE_TREE).add(Biomes.TAIGA, Biomes.WINDSWEPT_FOREST);
	}

	@SafeVarargs
	private void tag(ResourceKey<Biome> biome, TagKey<Biome>... tags) {
		for (TagKey<Biome> key : tags) {
			tag(key).add(biome);
		}
	}
}