package com.teamabnormals.autumnity.core.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.teamabnormals.autumnity.common.block.TallFoulBerryBushBlock;
import com.teamabnormals.autumnity.common.levelgen.feature.FallenMapleLeavesFeature;
import com.teamabnormals.autumnity.common.levelgen.feature.FallenLeavesMapleTreeFeature;
import com.teamabnormals.autumnity.common.levelgen.feature.MapleTreeFeature;
import com.teamabnormals.autumnity.common.levelgen.feature.PumpkinFieldsPumpkinFeature;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.common.levelgen.placement.BetterNoiseBasedCountPlacement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration.TreeConfigurationBuilder;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class AutumnityFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Autumnity.MOD_ID);

	public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> MAPLE_TREE = FEATURES.register("maple_tree", () -> new MapleTreeFeature(TreeConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> FALLEN_LEAVES_MAPLE_TREE = FEATURES.register("fallen_leaves_maple_tree", () -> new FallenLeavesMapleTreeFeature(TreeConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> FALLEN_MAPLE_LEAVES = FEATURES.register("fallen_maple_leaves", () -> new FallenMapleLeavesFeature(TreeConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> PUMPKIN_FIELDS_PUMPKIN = FEATURES.register("pumpkin_fields_pumpkin", () -> new PumpkinFieldsPumpkinFeature(NoneFeatureConfiguration.CODEC));

	public static final class States {
		private static final BlockState MAPLE_LOG = AutumnityBlocks.MAPLE_LOG.get().defaultBlockState();
		private static final BlockState MAPLE_LEAVES = AutumnityBlocks.MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState YELLOW_MAPLE_LEAVES = AutumnityBlocks.YELLOW_MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState ORANGE_MAPLE_LEAVES = AutumnityBlocks.ORANGE_MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState RED_MAPLE_LEAVES = AutumnityBlocks.RED_MAPLE_LEAVES.get().defaultBlockState();
	}

	public static final class AutumnityNoiseParameters {
		public static final ResourceKey<NoiseParameters> SPOTTED_MAPLES = createKey("spotted_maples");

		public static void bootstrap(BootstrapContext<NoiseParameters> context) {
			context.register(SPOTTED_MAPLES, new NoiseParameters(-8, 1.0D));

		}

		public static ResourceKey<NoiseParameters> createKey(String name) {
			return ResourceKey.create(Registries.NOISE, Autumnity.location(name));
		}
	}

	public static final class AutumnityConfiguredFeatures {
		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE = createKey("maple_tree");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE_BEES_0002 = createKey("maple_tree_bees_0002");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE_BEES_002 = createKey("maple_tree_bees_002");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE_BEES_005 = createKey("maple_tree_bees_005");

		public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE_TREE = createKey("yellow_maple_tree");
		public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE_TREE_BEES_005 = createKey("yellow_maple_tree_bees_005");
		public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_0002 = createKey("yellow_maple_tree_fallen_leaves_bees_0002");
		public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_002 = createKey("yellow_maple_tree_fallen_leaves_bees_002");

		public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_TREE = createKey("orange_maple_tree");
		public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_TREE_BEES_005 = createKey("orange_maple_tree_bees_005");
		public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_0002 = createKey("orange_maple_tree_fallen_leaves_bees_0002");
		public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_002 = createKey("orange_maple_tree_fallen_leaves_bees_002");

		public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_TREE = createKey("red_maple_tree");
		public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_TREE_BEES_005 = createKey("red_maple_tree_bees_005");
		public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_TREE_FALLEN_LEAVES_BEES_0002 = createKey("red_maple_tree_fallen_leaves_bees_0002");
		public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_TREE_FALLEN_LEAVES_BEES_002 = createKey("red_maple_tree_fallen_leaves_bees_002");

		public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_MAPLE_LEAVES_YELLOW = createKey("fallen_maple_leaves_yellow");
		public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_MAPLE_LEAVES_ORANGE = createKey("fallen_maple_leaves_orange");

		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_FOREST_VEGETATION = createKey("maple_forest_vegetation");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PUMPKIN_FIELDS_VEGETATION = createKey("pumpkin_fields_vegetation");

		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_GRASS = createKey("patch_tall_grass");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FOUL_BERRY_BUSH = createKey("patch_foul_berry_bush");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PUMPKINS_PUMPKIN_FIELDS = createKey("patch_pumpkins_pumpkin_fields");

		public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_MAPLE_FOREST = createKey("flower_maple_forest");
		public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PUMPKIN_FIELDS = createKey("flower_pumpkin_fields");

		public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
			HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
			HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

			ImmutableList<TreeDecorator> bees0002 = ImmutableList.of(new BeehiveDecorator(0.002F));
			ImmutableList<TreeDecorator> bees002 = ImmutableList.of(new BeehiveDecorator(0.02F));
			ImmutableList<TreeDecorator> bees005 = ImmutableList.of(new BeehiveDecorator(0.05F));

			register(context, MAPLE_TREE, AutumnityFeatures.MAPLE_TREE.get(), maple().build());
			register(context, MAPLE_TREE_BEES_0002, AutumnityFeatures.MAPLE_TREE.get(), maple().decorators(bees0002).build());
			register(context, MAPLE_TREE_BEES_002, AutumnityFeatures.MAPLE_TREE.get(), maple().decorators(bees002).build());
			register(context, MAPLE_TREE_BEES_005, AutumnityFeatures.MAPLE_TREE.get(), maple().decorators(bees005).build());

			register(context, YELLOW_MAPLE_TREE, AutumnityFeatures.MAPLE_TREE.get(), yellowMaple().build());
			register(context, YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), yellowMaple().decorators(bees0002).build());
			register(context, YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_002, AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), yellowMaple().decorators(bees002).build());
			register(context, YELLOW_MAPLE_TREE_BEES_005, AutumnityFeatures.MAPLE_TREE.get(), yellowMaple().decorators(bees005).build());

			register(context, ORANGE_MAPLE_TREE, AutumnityFeatures.MAPLE_TREE.get(), orangeMaple().build());
			register(context, ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), orangeMaple().decorators(bees0002).build());
			register(context, ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_002, AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), orangeMaple().decorators(bees002).build());
			register(context, ORANGE_MAPLE_TREE_BEES_005, AutumnityFeatures.MAPLE_TREE.get(), orangeMaple().decorators(bees005).build());

			register(context, RED_MAPLE_TREE, AutumnityFeatures.MAPLE_TREE.get(), redMaple().build());
			register(context, RED_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), redMaple().decorators(bees0002).build());
			register(context, RED_MAPLE_TREE_FALLEN_LEAVES_BEES_002, AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), redMaple().decorators(bees002).build());
			register(context, RED_MAPLE_TREE_BEES_005, AutumnityFeatures.MAPLE_TREE.get(), redMaple().decorators(bees005).build());

			register(context, FALLEN_MAPLE_LEAVES_YELLOW, AutumnityFeatures.FALLEN_MAPLE_LEAVES.get(), yellowMaple().build());
			register(context, FALLEN_MAPLE_LEAVES_ORANGE, AutumnityFeatures.FALLEN_MAPLE_LEAVES.get(), orangeMaple().build());

			register(context, MAPLE_FOREST_VEGETATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F), new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.RED_MAPLE_TREE_FALLEN_LEAVES_BEES_0002), 0.3F), new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_0002), 0.4F), new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_0002), 0.2F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.MAPLE_TREE_BEES_0002)));
			register(context, PUMPKIN_FIELDS_VEGETATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F), new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.RED_MAPLE_TREE_FALLEN_LEAVES_BEES_002), 0.3F), new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_002), 0.4F), new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_002), 0.2F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.MAPLE_TREE_BEES_002)));

			register(context, PATCH_TALL_GRASS, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.TALL_GRASS))));
			register(context, PATCH_FOUL_BERRY_BUSH, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get().defaultBlockState().setValue(TallFoulBerryBushBlock.AGE, 3))), List.of(Blocks.GRASS_BLOCK)));
			register(context, PATCH_PUMPKINS_PUMPKIN_FIELDS, AutumnityFeatures.PUMPKIN_FIELDS_PUMPKIN.get(), FeatureConfiguration.NONE);

			register(context, FLOWER_MAPLE_FOREST, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH)))), PlacementUtils.inlinePlaced(Feature.NO_BONEMEAL_FLOWER, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(AutumnityBlocks.AUTUMN_CROCUS.get())))))));
			register(context, FLOWER_PUMPKIN_FIELDS, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(AutumnityBlocks.AUTUMN_CROCUS.get().defaultBlockState(), 1).add(Blocks.OXEYE_DAISY.defaultBlockState(), 1).add(Blocks.CORNFLOWER.defaultBlockState(), 1))))));
		}
		
		private static TreeConfigurationBuilder maple() {
			return maple(States.MAPLE_LEAVES);
		}

		private static TreeConfigurationBuilder yellowMaple() {
			return maple(States.YELLOW_MAPLE_LEAVES);
		}

		private static TreeConfigurationBuilder orangeMaple() {
			return maple(States.ORANGE_MAPLE_LEAVES);
		}

		private static TreeConfigurationBuilder redMaple() {
			return maple(States.RED_MAPLE_LEAVES);
		}

		private static TreeConfigurationBuilder maple(BlockState leavesState) {
			return new TreeConfigurationBuilder(BlockStateProvider.simple(States.MAPLE_LOG), new StraightTrunkPlacer(5, 1, 0), BlockStateProvider.simple(leavesState), new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0), new TwoLayersFeatureSize(0, 0, 0)).ignoreVines();
		}

		public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
			return ResourceKey.create(Registries.CONFIGURED_FEATURE, Autumnity.location(name));
		}

		public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
			context.register(key, new ConfiguredFeature<>(feature, config));
		}
	}

	public static final class AutumnityPlacedFeatures {
		public static final ResourceKey<PlacedFeature> MAPLE_TREE_BEES_0002 = createKey("maple_tree_bees_0002");
		public static final ResourceKey<PlacedFeature> YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_0002 = createKey("yellow_maple_tree_fallen_leaves_bees_0002");
		public static final ResourceKey<PlacedFeature> ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_0002 = createKey("orange_maple_tree_fallen_leaves_bees_0002");
		public static final ResourceKey<PlacedFeature> RED_MAPLE_TREE_FALLEN_LEAVES_BEES_0002 = createKey("red_maple_tree_fallen_leaves_bees_0002");
		
		public static final ResourceKey<PlacedFeature> MAPLE_TREE_BEES_002 = createKey("maple_tree_bees_002");
		public static final ResourceKey<PlacedFeature> YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_002 = createKey("yellow_maple_tree_fallen_leaves_bees_002");
		public static final ResourceKey<PlacedFeature> RED_MAPLE_TREE_FALLEN_LEAVES_BEES_002 = createKey("red_maple_tree_fallen_leaves_bees_002");
		public static final ResourceKey<PlacedFeature> ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_002 = createKey("orange_maple_tree_fallen_leaves_bees_002");

		public static final ResourceKey<PlacedFeature> TREES_MAPLE = createKey("trees_maple");
		public static final ResourceKey<PlacedFeature> TREES_MAPLE_YELLOW = createKey("trees_maple_yellow");
		public static final ResourceKey<PlacedFeature> TREES_MAPLE_ORANGE = createKey("trees_maple_orange");
		public static final ResourceKey<PlacedFeature> TREES_MAPLE_RED = createKey("trees_maple_red");

		public static final ResourceKey<PlacedFeature> FALLEN_MAPLE_LEAVES_YELLOW = createKey("fallen_maple_leaves_yellow");
		public static final ResourceKey<PlacedFeature> FALLEN_MAPLE_LEAVES_ORANGE = createKey("fallen_maple_leaves_orange");

		public static final ResourceKey<PlacedFeature> PATCH_FOUL_BERRY_BUSH = createKey("patch_foul_berry_bush");
		public static final ResourceKey<PlacedFeature> FLOWER_MAPLE_FOREST = createKey("flower_maple_forest");
		public static final ResourceKey<PlacedFeature> MAPLE_FOREST_VEGETATION = createKey("maple_forest_vegetation");

		public static final ResourceKey<PlacedFeature> PUMPKIN_FIELDS_VEGETATION = createKey("pumpkin_fields_vegetation");
		public static final ResourceKey<PlacedFeature> PATCH_TALL_GRASS_PUMPKIN_FIELDS = createKey("patch_tall_grass_pumpkin_fields");
		public static final ResourceKey<PlacedFeature> PATCH_PUMPKIN_PUMPKIN_FIELDS = createKey("patch_pumpkin_pumpkin_fields");
		public static final ResourceKey<PlacedFeature> FLOWER_PUMPKIN_FIELDS = createKey("flower_pumpkin_fields");

		public static void bootstrap(BootstrapContext<PlacedFeature> context) {
			HolderGetter<NoiseParameters> noise = context.lookup(Registries.NOISE);

			register(context, MAPLE_TREE_BEES_0002, AutumnityConfiguredFeatures.MAPLE_TREE_BEES_0002, PlacementUtils.filteredByBlockSurvival(AutumnityBlocks.MAPLE_SAPLING.get()));
			register(context, YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, AutumnityConfiguredFeatures.YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, PlacementUtils.filteredByBlockSurvival(AutumnityBlocks.YELLOW_MAPLE_SAPLING.get()));
			register(context, ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, AutumnityConfiguredFeatures.ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, PlacementUtils.filteredByBlockSurvival(AutumnityBlocks.ORANGE_MAPLE_SAPLING.get()));
			register(context, RED_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, AutumnityConfiguredFeatures.RED_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, PlacementUtils.filteredByBlockSurvival(AutumnityBlocks.RED_MAPLE_SAPLING.get()));

			register(context, MAPLE_TREE_BEES_002, AutumnityConfiguredFeatures.MAPLE_TREE_BEES_002, PlacementUtils.filteredByBlockSurvival(AutumnityBlocks.MAPLE_SAPLING.get()));
			register(context, YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_002, AutumnityConfiguredFeatures.YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_002, PlacementUtils.filteredByBlockSurvival(AutumnityBlocks.YELLOW_MAPLE_SAPLING.get()));
			register(context, RED_MAPLE_TREE_FALLEN_LEAVES_BEES_002, AutumnityConfiguredFeatures.RED_MAPLE_TREE_FALLEN_LEAVES_BEES_002, PlacementUtils.filteredByBlockSurvival(AutumnityBlocks.ORANGE_MAPLE_SAPLING.get()));
			register(context, ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_002, AutumnityConfiguredFeatures.ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_002, PlacementUtils.filteredByBlockSurvival(AutumnityBlocks.RED_MAPLE_SAPLING.get()));

			register(context, TREES_MAPLE, AutumnityConfiguredFeatures.MAPLE_TREE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));

			BetterNoiseBasedCountPlacement spottedMaplesCount = new BetterNoiseBasedCountPlacement(noise.get(AutumnityNoiseParameters.SPOTTED_MAPLES).get(), 12, -0.4F);
			register(context, TREES_MAPLE_YELLOW, AutumnityConfiguredFeatures.YELLOW_MAPLE_TREE, treePlacementBase(spottedMaplesCount).build());
			register(context, TREES_MAPLE_ORANGE, AutumnityConfiguredFeatures.ORANGE_MAPLE_TREE, treePlacementBase(spottedMaplesCount).build());
			register(context, TREES_MAPLE_RED, AutumnityConfiguredFeatures.RED_MAPLE_TREE, treePlacementBase(spottedMaplesCount).build());

			register(context, FALLEN_MAPLE_LEAVES_YELLOW, AutumnityConfiguredFeatures.FALLEN_MAPLE_LEAVES_YELLOW, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, FALLEN_MAPLE_LEAVES_ORANGE, AutumnityConfiguredFeatures.FALLEN_MAPLE_LEAVES_ORANGE, CountPlacement.of(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, PATCH_FOUL_BERRY_BUSH, AutumnityConfiguredFeatures.PATCH_FOUL_BERRY_BUSH, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, FLOWER_MAPLE_FOREST, AutumnityConfiguredFeatures.FLOWER_MAPLE_FOREST, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

			register(context, MAPLE_FOREST_VEGETATION, AutumnityConfiguredFeatures.MAPLE_FOREST_VEGETATION, VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
			register(context, PUMPKIN_FIELDS_VEGETATION, AutumnityConfiguredFeatures.PUMPKIN_FIELDS_VEGETATION, VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.2F, 1)));
			register(context, PATCH_TALL_GRASS_PUMPKIN_FIELDS, AutumnityConfiguredFeatures.PATCH_TALL_GRASS, NoiseThresholdCountPlacement.of(-0.8D, 5, 10), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, PATCH_PUMPKIN_PUMPKIN_FIELDS, AutumnityConfiguredFeatures.PATCH_PUMPKINS_PUMPKIN_FIELDS, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
			register(context, FLOWER_PUMPKIN_FIELDS, AutumnityConfiguredFeatures.FLOWER_PUMPKIN_FIELDS, NoiseThresholdCountPlacement.of(-0.8D, 15, 4), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		}

		private static Builder<PlacementModifier> treePlacementBase(PlacementModifier modifier) {
			return ImmutableList.<PlacementModifier>builder().add(modifier).add(InSquarePlacement.spread()).add(SurfaceWaterDepthFilter.forMaxDepth(0)).add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR).add(BiomeFilter.biome());
		}

		public static ResourceKey<PlacedFeature> createKey(String name) {
			return ResourceKey.create(Registries.PLACED_FEATURE, Autumnity.location(name));
		}

		public static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
			context.register(key, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(feature), modifiers));
		}

		public static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
			register(context, key, feature, List.of(modifiers));
		}
	}
}