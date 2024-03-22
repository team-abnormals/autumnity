package com.teamabnormals.autumnity.core.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.teamabnormals.autumnity.common.block.TallFoulBerryBushBlock;
import com.teamabnormals.autumnity.common.levelgen.feature.FallenLeavesFeature;
import com.teamabnormals.autumnity.common.levelgen.feature.FallenLeavesMapleTreeFeature;
import com.teamabnormals.autumnity.common.levelgen.feature.MapleTreeFeature;
import com.teamabnormals.autumnity.common.levelgen.feature.PumpkinFieldsPumpkinFeature;
import com.teamabnormals.autumnity.common.levelgen.placement.BetterNoiseBasedCountPlacement;
import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Autumnity.MOD_ID);

	public static final RegistryObject<Feature<TreeConfiguration>> MAPLE_TREE = FEATURES.register("maple_tree", () -> new MapleTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> FALLEN_LEAVES_MAPLE_TREE = FEATURES.register("fallen_leaves_maple_tree", () -> new FallenLeavesMapleTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () -> new FallenLeavesFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> PUMPKIN_FIELDS_PUMPKIN = FEATURES.register("pumpkin_fields_pumpkin", () -> new PumpkinFieldsPumpkinFeature(NoneFeatureConfiguration.CODEC));

	public static final class States {
		private static final BlockState MAPLE_LOG = AutumnityBlocks.MAPLE_LOG.get().defaultBlockState();
		private static final BlockState MAPLE_LEAVES = AutumnityBlocks.MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState YELLOW_MAPLE_LEAVES = AutumnityBlocks.YELLOW_MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState ORANGE_MAPLE_LEAVES = AutumnityBlocks.ORANGE_MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState RED_MAPLE_LEAVES = AutumnityBlocks.RED_MAPLE_LEAVES.get().defaultBlockState();
	}

	public static final class Configs {
		public static final TreeConfiguration MAPLE_TREE_CONFIG = createMaple(States.MAPLE_LEAVES).build();
		public static final TreeConfiguration YELLOW_MAPLE_TREE_CONFIG = createMaple(States.YELLOW_MAPLE_LEAVES).build();
		public static final TreeConfiguration ORANGE_MAPLE_TREE_CONFIG = createMaple(States.ORANGE_MAPLE_LEAVES).build();
		public static final TreeConfiguration RED_MAPLE_TREE_CONFIG = createMaple(States.RED_MAPLE_LEAVES).build();

		private static TreeConfiguration.TreeConfigurationBuilder createMaple(BlockState leavesState) {
			return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(States.MAPLE_LOG), new StraightTrunkPlacer(5, 1, 0), BlockStateProvider.simple(leavesState), new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0), new TwoLayersFeatureSize(0, 0, 0)).ignoreVines();
		}
	}

	public static final class AutumnityNoiseParameters {
		public static final ResourceKey<NoiseParameters> SPOTTED_MAPLES = createKey("spotted_maples");

		public static void bootstrap(BootstapContext<NoiseParameters> context) {
			context.register(SPOTTED_MAPLES, new NoiseParameters(-8, 1.0D));

		}

		public static ResourceKey<NoiseParameters> createKey(String name) {
			return ResourceKey.create(Registries.NOISE, new ResourceLocation(Autumnity.MOD_ID, name));
		}
	}

	public static final class AutumnityConfiguredFeatures {
		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE = createKey("maple_tree");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE_YELLOW = createKey("maple_tree_yellow");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE_ORANGE = createKey("maple_tree_orange");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE_RED = createKey("maple_tree_red");

		public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_LEAVES_MAPLE_TREE_YELLOW = createKey("fallen_leaves_maple_tree_yellow");
		public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_LEAVES_MAPLE_TREE_ORANGE = createKey("fallen_leaves_maple_tree_orange");

		public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_LEAVES = createKey("fallen_leaves");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_FOREST_VEGETATION = createKey("maple_forest_vegetation");

		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_GRASS = createKey("patch_tall_grass");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FOUL_BERRY_BUSH = createKey("patch_foul_berry_bush");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PUMPKINS_PUMPKIN_FIELDS = createKey("patch_pumpkins_pumpkin_fields");

		public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_MAPLE_FOREST = createKey("flower_maple_forest");
		public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PUMPKIN_FIELDS = createKey("flower_pumpkin_fields");

		public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
			HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
			HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

			register(context, MAPLE_TREE, AutumnityFeatures.MAPLE_TREE.get(), Configs.MAPLE_TREE_CONFIG);
			register(context, MAPLE_TREE_YELLOW, AutumnityFeatures.MAPLE_TREE.get(), Configs.YELLOW_MAPLE_TREE_CONFIG);
			register(context, MAPLE_TREE_ORANGE, AutumnityFeatures.MAPLE_TREE.get(), Configs.ORANGE_MAPLE_TREE_CONFIG);
			register(context, MAPLE_TREE_RED, AutumnityFeatures.MAPLE_TREE.get(), Configs.RED_MAPLE_TREE_CONFIG);

			register(context, FALLEN_LEAVES_MAPLE_TREE_YELLOW, AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), Configs.YELLOW_MAPLE_TREE_CONFIG);
			register(context, FALLEN_LEAVES_MAPLE_TREE_ORANGE, AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), Configs.ORANGE_MAPLE_TREE_CONFIG);

			register(context, FALLEN_LEAVES, AutumnityFeatures.FALLEN_LEAVES.get(), FeatureConfiguration.NONE);
			register(context, MAPLE_FOREST_VEGETATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F), new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.RED_MAPLE_TREE_CHECKED), 0.3F), new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.FALLEN_LEAVES_ORANGE_MAPLE_TREE_CHECKED), 0.4F), new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.FALLEN_LEAVES_YELLOW_MAPLE_TREE_CHECKED), 0.2F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.MAPLE_TREE_CHECKED)));

			register(context, PATCH_TALL_GRASS, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.TALL_GRASS))));
			register(context, PATCH_FOUL_BERRY_BUSH, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get().defaultBlockState().setValue(TallFoulBerryBushBlock.AGE, 3))), List.of(Blocks.GRASS_BLOCK)));
			register(context, PATCH_PUMPKINS_PUMPKIN_FIELDS, AutumnityFeatures.PUMPKIN_FIELDS_PUMPKIN.get(), FeatureConfiguration.NONE);

			register(context, FLOWER_MAPLE_FOREST, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH)))), PlacementUtils.inlinePlaced(Feature.NO_BONEMEAL_FLOWER, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(AutumnityBlocks.AUTUMN_CROCUS.get())))))));
			register(context, FLOWER_PUMPKIN_FIELDS, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(AutumnityBlocks.AUTUMN_CROCUS.get().defaultBlockState(), 1).add(Blocks.OXEYE_DAISY.defaultBlockState(), 1).add(Blocks.CORNFLOWER.defaultBlockState(), 1))))));
		}

		public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
			return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Autumnity.MOD_ID, name));
		}

		public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
			context.register(key, new ConfiguredFeature<>(feature, config));
		}
	}

	public static final class AutumnityPlacedFeatures {
		public static final ResourceKey<PlacedFeature> MAPLE_TREE_CHECKED = createKey("maple_tree_checked");
		public static final ResourceKey<PlacedFeature> RED_MAPLE_TREE_CHECKED = createKey("red_maple_tree_checked");
		public static final ResourceKey<PlacedFeature> FALLEN_LEAVES_YELLOW_MAPLE_TREE_CHECKED = createKey("fallen_leaves_yellow_maple_tree_checked");
		public static final ResourceKey<PlacedFeature> FALLEN_LEAVES_ORANGE_MAPLE_TREE_CHECKED = createKey("fallen_leaves_orange_maple_tree_checked");
		public static final ResourceKey<PlacedFeature> TREES_MAPLE = createKey("trees_maple");
		public static final ResourceKey<PlacedFeature> TREES_MAPLE_YELLOW = createKey("trees_maple_yellow");
		public static final ResourceKey<PlacedFeature> TREES_MAPLE_ORANGE = createKey("trees_maple_orange");
		public static final ResourceKey<PlacedFeature> TREES_MAPLE_RED = createKey("trees_maple_red");
		public static final ResourceKey<PlacedFeature> FALLEN_LEAVES = createKey("fallen_leaves");
		public static final ResourceKey<PlacedFeature> PATCH_FOUL_BERRY_BUSH = createKey("patch_foul_berry_bush");
		public static final ResourceKey<PlacedFeature> FLOWER_MAPLE_FOREST = createKey("flower_maple_forest");
		public static final ResourceKey<PlacedFeature> MAPLE_FOREST_VEGETATION = createKey("maple_forest_vegetation");
		public static final ResourceKey<PlacedFeature> PUMPKIN_FIELDS_VEGETATION = createKey("pumpkin_fields_vegetation");
		public static final ResourceKey<PlacedFeature> PATCH_TALL_GRASS_PUMPKIN_FIELDS = createKey("patch_tall_grass_pumpkin_fields");
		public static final ResourceKey<PlacedFeature> PATCH_PUMPKIN_PUMPKIN_FIELDS = createKey("patch_pumpkin_pumpkin_fields");
		public static final ResourceKey<PlacedFeature> FLOWER_PUMPKIN_FIELDS = createKey("flower_pumpkin_fields");

		public static void bootstrap(BootstapContext<PlacedFeature> context) {
			HolderGetter<NoiseParameters> noise = context.lookup(Registries.NOISE);

			register(context, MAPLE_TREE_CHECKED, AutumnityConfiguredFeatures.MAPLE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, RED_MAPLE_TREE_CHECKED, AutumnityConfiguredFeatures.MAPLE_TREE_RED, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

			register(context, FALLEN_LEAVES_YELLOW_MAPLE_TREE_CHECKED, AutumnityConfiguredFeatures.FALLEN_LEAVES_MAPLE_TREE_YELLOW, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
			register(context, FALLEN_LEAVES_ORANGE_MAPLE_TREE_CHECKED, AutumnityConfiguredFeatures.FALLEN_LEAVES_MAPLE_TREE_ORANGE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

			register(context, TREES_MAPLE, AutumnityConfiguredFeatures.MAPLE_TREE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));

			BetterNoiseBasedCountPlacement spottedMaplesCount = new BetterNoiseBasedCountPlacement(noise.get(AutumnityNoiseParameters.SPOTTED_MAPLES).get(), 12, -0.4F);
			register(context, TREES_MAPLE_YELLOW, AutumnityConfiguredFeatures.MAPLE_TREE_YELLOW, treePlacementBase(spottedMaplesCount).build());
			register(context, TREES_MAPLE_ORANGE, AutumnityConfiguredFeatures.MAPLE_TREE_ORANGE, treePlacementBase(spottedMaplesCount).build());
			register(context, TREES_MAPLE_RED, AutumnityConfiguredFeatures.MAPLE_TREE_RED, treePlacementBase(spottedMaplesCount).build());

			register(context, FALLEN_LEAVES, AutumnityConfiguredFeatures.FALLEN_LEAVES, RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, PATCH_FOUL_BERRY_BUSH, AutumnityConfiguredFeatures.PATCH_FOUL_BERRY_BUSH, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, FLOWER_MAPLE_FOREST, AutumnityConfiguredFeatures.FLOWER_MAPLE_FOREST, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

			register(context, MAPLE_FOREST_VEGETATION, AutumnityConfiguredFeatures.MAPLE_FOREST_VEGETATION, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
			register(context, PUMPKIN_FIELDS_VEGETATION, AutumnityConfiguredFeatures.MAPLE_FOREST_VEGETATION, VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.2F, 1)));
			register(context, PATCH_TALL_GRASS_PUMPKIN_FIELDS, AutumnityConfiguredFeatures.PATCH_TALL_GRASS, NoiseThresholdCountPlacement.of(-0.8D, 5, 10), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, PATCH_PUMPKIN_PUMPKIN_FIELDS, AutumnityConfiguredFeatures.PATCH_PUMPKINS_PUMPKIN_FIELDS, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
			register(context, FLOWER_PUMPKIN_FIELDS, AutumnityConfiguredFeatures.FLOWER_PUMPKIN_FIELDS, NoiseThresholdCountPlacement.of(-0.8D, 15, 4), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		}

		private static Builder<PlacementModifier> treePlacementBase(PlacementModifier modifier) {
			return ImmutableList.<PlacementModifier>builder().add(modifier).add(InSquarePlacement.spread()).add(SurfaceWaterDepthFilter.forMaxDepth(0)).add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR).add(BiomeFilter.biome());
		}

		public static ResourceKey<PlacedFeature> createKey(String name) {
			return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Autumnity.MOD_ID, name));
		}

		public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
			context.register(key, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(feature), modifiers));
		}

		public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
			register(context, key, feature, List.of(modifiers));
		}
	}
}