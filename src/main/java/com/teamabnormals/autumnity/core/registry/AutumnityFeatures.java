package com.teamabnormals.autumnity.core.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.teamabnormals.autumnity.common.block.TallFoulBerryBushBlock;
import com.teamabnormals.autumnity.common.levelgen.feature.*;
import com.teamabnormals.autumnity.common.levelgen.feature.configurations.FallenLeavesConfiguration;
import com.teamabnormals.autumnity.common.levelgen.feature.configurations.NoiseSelectorFeatureConfiguration;
import com.teamabnormals.autumnity.common.levelgen.feature.treedecorators.FallenLeavesDecorator;
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
import net.minecraft.world.level.block.PipeBlock;
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
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class AutumnityFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Autumnity.MOD_ID);
	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, Autumnity.MOD_ID);

	public static final DeferredHolder<Feature<?>, Feature<TreeConfiguration>> MAPLE_TREE = FEATURES.register("maple_tree", () -> new MapleTreeFeature(TreeConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<FallenLeavesConfiguration>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () -> new FallenLeavesFeature(FallenLeavesConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> PUMPKIN_FIELDS_PUMPKIN = FEATURES.register("pumpkin_fields_pumpkin", () -> new PumpkinFieldsPumpkinFeature(NoneFeatureConfiguration.CODEC));
	public static final DeferredHolder<Feature<?>, Feature<NoiseSelectorFeatureConfiguration>> NOISE_SELECTOR = FEATURES.register("noise_selector", () -> new NoiseSelectorFeature(NoiseSelectorFeatureConfiguration.CODEC));

	public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<?>> FALLEN_LEAVES_DECORATOR = TREE_DECORATOR_TYPES.register("fallen_leaves", () -> new TreeDecoratorType<>(FallenLeavesDecorator.CODEC));

	public static final class States {
		private static final BlockState MAPLE_LOG = AutumnityBlocks.MAPLE_LOG.get().defaultBlockState();
		private static final BlockState MAPLE_LEAVES = AutumnityBlocks.MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState YELLOW_MAPLE_LEAVES = AutumnityBlocks.YELLOW_MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState ORANGE_MAPLE_LEAVES = AutumnityBlocks.ORANGE_MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState RED_MAPLE_LEAVES = AutumnityBlocks.RED_MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState YELLOW_MAPLE_LEAF_PILE = AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE.get().defaultBlockState();
		private static final BlockState ORANGE_MAPLE_LEAF_PILE = AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE.get().defaultBlockState();
		private static final BlockState RED_MAPLE_LEAF_PILE = AutumnityBlocks.RED_MAPLE_LEAF_PILE.get().defaultBlockState();
	}

	public static final class AutumnityNoiseParameters {
		public static final ResourceKey<NoiseParameters> SPOTTED_MAPLES = createKey("spotted_maples");
		public static final ResourceKey<NoiseParameters> MAPLE_GRADIENT = createKey("maple_gradient");
		public static final ResourceKey<NoiseParameters> AUTUMN_PROGRESS = createKey("autumn_progress");

		public static void bootstrap(BootstrapContext<NoiseParameters> context) {
			context.register(SPOTTED_MAPLES, new NoiseParameters(-9, 1.0D, 1.5D));
			context.register(MAPLE_GRADIENT, new NoiseParameters(-1, 1.0D, 1.0D));
			context.register(AUTUMN_PROGRESS, new NoiseParameters(-9, 1.0D, 1.5D));
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

		public static final ResourceKey<ConfiguredFeature<?, ?>> EARLY_MAPLE_FOREST_TREES = createKey("early_maple_forest_trees");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MIDDLE_MAPLE_FOREST_TREES = createKey("middle_maple_forest_trees");
		public static final ResourceKey<ConfiguredFeature<?, ?>> LATE_MAPLE_FOREST_TREES = createKey("late_maple_forest_trees");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_FOREST_TREES = createKey("maple_forest_trees");

		public static final ResourceKey<ConfiguredFeature<?, ?>> EARLY_PUMPKIN_FIELDS_TREES = createKey("early_pumpkin_fields_trees");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MIDDLE_PUMPKIN_FIELDS_TREES = createKey("middle_pumpkin_fields_trees");
		public static final ResourceKey<ConfiguredFeature<?, ?>> LATE_PUMPKIN_FIELDS_TREES = createKey("late_pumpkin_fields_trees");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PUMPKIN_FIELDS_TREES = createKey("pumpkin_fields_trees");

		public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_MAPLE_LEAVES_YELLOW = createKey("fallen_maple_leaves_yellow");
		public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_MAPLE_LEAVES_ORANGE = createKey("fallen_maple_leaves_orange");
		public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_MAPLE_LEAVES_RED = createKey("fallen_maple_leaves_red");

		public static final ResourceKey<ConfiguredFeature<?, ?>> EARLY_MAPLE_FOREST_FALLEN_LEAVES = createKey("early_maple_forest_fallen_leaves");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MIDDLE_MAPLE_FOREST_FALLEN_LEAVES = createKey("middle_maple_forest_fallen_leaves");
		public static final ResourceKey<ConfiguredFeature<?, ?>> LATE_MAPLE_FOREST_FALLEN_LEAVES = createKey("late_maple_forest_fallen_leaves");
		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_FOREST_FALLEN_LEAVES = createKey("maple_forest_fallen_leaves");

		public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_FOREST_VEGETATION = createKey("maple_forest_vegetation");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PUMPKIN_FIELDS_VEGETATION = createKey("pumpkin_fields_vegetation");
		public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_MAPLE = createKey("trees_maple");

		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MAPLE_FOREST_GRASS = createKey("patch_maple_forest_grass");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_GRASS = createKey("patch_tall_grass");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FOUL_BERRY_BUSH = createKey("patch_foul_berry_bush");
		public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PUMPKINS_PUMPKIN_FIELDS = createKey("patch_pumpkins_pumpkin_fields");

		public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_MAPLE_FOREST = createKey("flower_maple_forest");
		public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PUMPKIN_FIELDS = createKey("flower_pumpkin_fields");

		public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
			HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
			HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
			HolderGetter<NoiseParameters> noise = context.lookup(Registries.NOISE);

			TreeDecorator bees0002 = new BeehiveDecorator(0.002F);
			TreeDecorator bees002 = new BeehiveDecorator(0.02F);
			TreeDecorator bees005 = new BeehiveDecorator(0.05F);

			TreeDecorator yellowleaves = fallenLeavesDecorator(States.YELLOW_MAPLE_LEAF_PILE);
			TreeDecorator orangeleaves = fallenLeavesDecorator(States.ORANGE_MAPLE_LEAF_PILE);
			TreeDecorator redleaves = fallenLeavesDecorator(States.RED_MAPLE_LEAF_PILE);

			register(context, MAPLE_TREE, AutumnityFeatures.MAPLE_TREE.get(), maple().build());
			register(context, MAPLE_TREE_BEES_0002, AutumnityFeatures.MAPLE_TREE.get(), maple().decorators(ImmutableList.of(bees0002)).build());
			register(context, MAPLE_TREE_BEES_002, AutumnityFeatures.MAPLE_TREE.get(), maple().decorators(ImmutableList.of(bees002)).build());
			register(context, MAPLE_TREE_BEES_005, AutumnityFeatures.MAPLE_TREE.get(), maple().decorators(ImmutableList.of(bees005)).build());

			register(context, YELLOW_MAPLE_TREE, AutumnityFeatures.MAPLE_TREE.get(), yellowMaple().build());
			register(context, YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, AutumnityFeatures.MAPLE_TREE.get(), yellowMaple().decorators(ImmutableList.of(bees0002, yellowleaves)).build());
			register(context, YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_002, AutumnityFeatures.MAPLE_TREE.get(), yellowMaple().decorators(ImmutableList.of(bees002, yellowleaves)).build());
			register(context, YELLOW_MAPLE_TREE_BEES_005, AutumnityFeatures.MAPLE_TREE.get(), yellowMaple().decorators(ImmutableList.of(bees005)).build());

			register(context, ORANGE_MAPLE_TREE, AutumnityFeatures.MAPLE_TREE.get(), orangeMaple().build());
			register(context, ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, AutumnityFeatures.MAPLE_TREE.get(), orangeMaple().decorators(ImmutableList.of(bees0002, orangeleaves)).build());
			register(context, ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_002, AutumnityFeatures.MAPLE_TREE.get(), orangeMaple().decorators(ImmutableList.of(bees002, orangeleaves)).build());
			register(context, ORANGE_MAPLE_TREE_BEES_005, AutumnityFeatures.MAPLE_TREE.get(), orangeMaple().decorators(ImmutableList.of(bees005)).build());

			register(context, RED_MAPLE_TREE, AutumnityFeatures.MAPLE_TREE.get(), redMaple().build());
			register(context, RED_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, AutumnityFeatures.MAPLE_TREE.get(), redMaple().decorators(ImmutableList.of(bees0002, redleaves)).build());
			register(context, RED_MAPLE_TREE_FALLEN_LEAVES_BEES_002, AutumnityFeatures.MAPLE_TREE.get(), redMaple().decorators(ImmutableList.of(bees002, redleaves)).build());
			register(context, RED_MAPLE_TREE_BEES_005, AutumnityFeatures.MAPLE_TREE.get(), redMaple().decorators(ImmutableList.of(bees005)).build());

			register(context, EARLY_MAPLE_FOREST_TREES, AutumnityFeatures.NOISE_SELECTOR.get(), new NoiseSelectorFeatureConfiguration(noise.get(AutumnityNoiseParameters.MAPLE_GRADIENT).get(), List.of(new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_0002), -0.35F), new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_0002), 0.1F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.MAPLE_TREE_BEES_0002)));
			register(context, MIDDLE_MAPLE_FOREST_TREES, AutumnityFeatures.NOISE_SELECTOR.get(), new NoiseSelectorFeatureConfiguration(noise.get(AutumnityNoiseParameters.MAPLE_GRADIENT).get(), List.of(new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.RED_MAPLE_TREE_FALLEN_LEAVES_BEES_0002), -0.2F), new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_0002), 0.15F), new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_0002), 0.25F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.MAPLE_TREE_BEES_0002)));
			register(context, LATE_MAPLE_FOREST_TREES, AutumnityFeatures.NOISE_SELECTOR.get(), new NoiseSelectorFeatureConfiguration(noise.get(AutumnityNoiseParameters.MAPLE_GRADIENT).get(), List.of(new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.RED_MAPLE_TREE_FALLEN_LEAVES_BEES_0002), 0.2F), new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_0002), 0.8F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_0002)));
			register(context, MAPLE_FOREST_TREES, AutumnityFeatures.NOISE_SELECTOR.get(), new NoiseSelectorFeatureConfiguration(noise.get(AutumnityNoiseParameters.AUTUMN_PROGRESS).get(), 0.3F, List.of(new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.EARLY_MAPLE_FOREST_TREES), -0.4F), new NoiseThresholdPlacedFeature((placedFeatures.getOrThrow(AutumnityPlacedFeatures.MIDDLE_MAPLE_FOREST_TREES)), 0.4F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.LATE_MAPLE_FOREST_TREES)));

			register(context, EARLY_PUMPKIN_FIELDS_TREES, AutumnityFeatures.NOISE_SELECTOR.get(), new NoiseSelectorFeatureConfiguration(noise.get(AutumnityNoiseParameters.MAPLE_GRADIENT).get(), List.of(new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_002), -0.2F), new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_002), 0.2F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.MAPLE_TREE_BEES_002)));
			register(context, MIDDLE_PUMPKIN_FIELDS_TREES, AutumnityFeatures.NOISE_SELECTOR.get(), new NoiseSelectorFeatureConfiguration(noise.get(AutumnityNoiseParameters.MAPLE_GRADIENT).get(), List.of(new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.RED_MAPLE_TREE_FALLEN_LEAVES_BEES_002), -0.2F), new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_002), 0.15F), new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_002), 0.25F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.MAPLE_TREE_BEES_002)));
			register(context, LATE_PUMPKIN_FIELDS_TREES, AutumnityFeatures.NOISE_SELECTOR.get(), new NoiseSelectorFeatureConfiguration(noise.get(AutumnityNoiseParameters.MAPLE_GRADIENT).get(), List.of(new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.RED_MAPLE_TREE_FALLEN_LEAVES_BEES_002), 0.2F), new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_002), 0.8F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_002)));
			register(context, PUMPKIN_FIELDS_TREES, AutumnityFeatures.NOISE_SELECTOR.get(), new NoiseSelectorFeatureConfiguration(noise.get(AutumnityNoiseParameters.AUTUMN_PROGRESS).get(), 0.3F, List.of(new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.EARLY_PUMPKIN_FIELDS_TREES), -0.4F), new NoiseThresholdPlacedFeature((placedFeatures.getOrThrow(AutumnityPlacedFeatures.MIDDLE_PUMPKIN_FIELDS_TREES)), 0.4F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.LATE_PUMPKIN_FIELDS_TREES)));

			register(context, FALLEN_MAPLE_LEAVES_YELLOW, AutumnityFeatures.FALLEN_LEAVES.get(), fallenLeaves(States.YELLOW_MAPLE_LEAF_PILE, 3, 3));
			register(context, FALLEN_MAPLE_LEAVES_ORANGE, AutumnityFeatures.FALLEN_LEAVES.get(), fallenLeaves(States.ORANGE_MAPLE_LEAF_PILE, 3, 3));
			register(context, FALLEN_MAPLE_LEAVES_RED, AutumnityFeatures.FALLEN_LEAVES.get(), fallenLeaves(States.RED_MAPLE_LEAF_PILE, 3, 3));

			register(context, EARLY_MAPLE_FOREST_FALLEN_LEAVES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.FALLEN_MAPLE_LEAVES_ORANGE), 0.4F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.FALLEN_MAPLE_LEAVES_YELLOW)));
			register(context, MIDDLE_MAPLE_FOREST_FALLEN_LEAVES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.FALLEN_MAPLE_LEAVES_ORANGE), 0.8F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.FALLEN_MAPLE_LEAVES_YELLOW)));
			register(context, LATE_MAPLE_FOREST_FALLEN_LEAVES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.FALLEN_MAPLE_LEAVES_RED), 0.4F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.FALLEN_MAPLE_LEAVES_ORANGE)));
			register(context, MAPLE_FOREST_FALLEN_LEAVES, AutumnityFeatures.NOISE_SELECTOR.get(), new NoiseSelectorFeatureConfiguration(noise.get(AutumnityNoiseParameters.AUTUMN_PROGRESS).get(), 0.3F, List.of(new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.EARLY_MAPLE_FOREST_FALLEN_LEAVES), -0.4F), new NoiseThresholdPlacedFeature((placedFeatures.getOrThrow(AutumnityPlacedFeatures.MIDDLE_MAPLE_FOREST_FALLEN_LEAVES)), 0.4F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.LATE_MAPLE_FOREST_FALLEN_LEAVES)));

			register(context, MAPLE_FOREST_VEGETATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.MAPLE_FOREST_TREES)));
			register(context, PUMPKIN_FIELDS_VEGETATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.PUMPKIN_FIELDS_TREES)));
			register(context, TREES_MAPLE, AutumnityFeatures.NOISE_SELECTOR.get(), new NoiseSelectorFeatureConfiguration(noise.get(AutumnityNoiseParameters.SPOTTED_MAPLES).get(), List.of(new NoiseThresholdPlacedFeature(placedFeatures.getOrThrow(AutumnityPlacedFeatures.MAPLE_TREE_BEES_0002), 0.4F)), placedFeatures.getOrThrow(AutumnityPlacedFeatures.YELLOW_MAPLE_TREE_FALLEN_LEAVES_BEES_0002)));

			register(context, PATCH_MAPLE_FOREST_GRASS, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.SHORT_GRASS.defaultBlockState(), 3).add(Blocks.FERN.defaultBlockState(), 1))))));
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

		private static FallenLeavesConfiguration fallenLeaves(BlockState leafPileState, int radius, int ySpread) {
			return new FallenLeavesConfiguration(BlockStateProvider.simple(leafPileState.setValue(PipeBlock.DOWN, true)), radius, ySpread);
		}

		private static TreeDecorator fallenLeavesDecorator(BlockState leafPileState) {
			return new FallenLeavesDecorator(BlockStateProvider.simple(leafPileState.setValue(PipeBlock.DOWN, true)), 3, 3);
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

		public static final ResourceKey<PlacedFeature> EARLY_MAPLE_FOREST_TREES = createKey("early_maple_forest_trees");
		public static final ResourceKey<PlacedFeature> MIDDLE_MAPLE_FOREST_TREES = createKey("middle_maple_forest_trees");
		public static final ResourceKey<PlacedFeature> LATE_MAPLE_FOREST_TREES = createKey("late_maple_forest_trees");
		public static final ResourceKey<PlacedFeature> MAPLE_FOREST_TREES = createKey("maple_forest_trees");

		public static final ResourceKey<PlacedFeature> EARLY_PUMPKIN_FIELDS_TREES = createKey("early_pumpkin_fields_trees");
		public static final ResourceKey<PlacedFeature> MIDDLE_PUMPKIN_FIELDS_TREES = createKey("middle_pumpkin_fields_trees");
		public static final ResourceKey<PlacedFeature> LATE_PUMPKIN_FIELDS_TREES = createKey("late_pumpkin_fields_trees");
		public static final ResourceKey<PlacedFeature> PUMPKIN_FIELDS_TREES = createKey("pumpkin_fields_trees");

		public static final ResourceKey<PlacedFeature> FALLEN_MAPLE_LEAVES_YELLOW = createKey("fallen_maple_leaves_yellow");
		public static final ResourceKey<PlacedFeature> FALLEN_MAPLE_LEAVES_ORANGE = createKey("fallen_maple_leaves_orange");
		public static final ResourceKey<PlacedFeature> FALLEN_MAPLE_LEAVES_RED = createKey("fallen_maple_leaves_red");

		public static final ResourceKey<PlacedFeature> EARLY_MAPLE_FOREST_FALLEN_LEAVES = createKey("early_maple_forest_fallen_leaves");
		public static final ResourceKey<PlacedFeature> MIDDLE_MAPLE_FOREST_FALLEN_LEAVES = createKey("middle_maple_forest_fallen_leaves");
		public static final ResourceKey<PlacedFeature> LATE_MAPLE_FOREST_FALLEN_LEAVES = createKey("late_maple_forest_fallen_leaves");
		public static final ResourceKey<PlacedFeature> MAPLE_FOREST_FALLEN_LEAVES = createKey("maple_forest_fallen_leaves");

		public static final ResourceKey<PlacedFeature> TREES_MAPLE = createKey("trees_maple");
		public static final ResourceKey<PlacedFeature> SPOTTED_MAPLES_ORANGE = createKey("spotted_maples_orange");
		public static final ResourceKey<PlacedFeature> SPOTTED_MAPLES_RED = createKey("spotted_maples_red");

		public static final ResourceKey<PlacedFeature> PATCH_FOUL_BERRY_BUSH = createKey("patch_foul_berry_bush");
		public static final ResourceKey<PlacedFeature> FLOWER_MAPLE_FOREST = createKey("flower_maple_forest");
		public static final ResourceKey<PlacedFeature> MAPLE_FOREST_VEGETATION = createKey("maple_forest_vegetation");
		public static final ResourceKey<PlacedFeature> PATCH_GRASS_MAPLE_FOREST = createKey("patch_grass_maple_forest");

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

			register(context, EARLY_MAPLE_FOREST_TREES, AutumnityConfiguredFeatures.EARLY_MAPLE_FOREST_TREES);
			register(context, MIDDLE_MAPLE_FOREST_TREES, AutumnityConfiguredFeatures.MIDDLE_MAPLE_FOREST_TREES);
			register(context, LATE_MAPLE_FOREST_TREES, AutumnityConfiguredFeatures.LATE_MAPLE_FOREST_TREES);
			register(context, MAPLE_FOREST_TREES, AutumnityConfiguredFeatures.MAPLE_FOREST_TREES);

			register(context, EARLY_PUMPKIN_FIELDS_TREES, AutumnityConfiguredFeatures.EARLY_PUMPKIN_FIELDS_TREES);
			register(context, MIDDLE_PUMPKIN_FIELDS_TREES, AutumnityConfiguredFeatures.MIDDLE_PUMPKIN_FIELDS_TREES);
			register(context, LATE_PUMPKIN_FIELDS_TREES, AutumnityConfiguredFeatures.LATE_PUMPKIN_FIELDS_TREES);
			register(context, PUMPKIN_FIELDS_TREES, AutumnityConfiguredFeatures.PUMPKIN_FIELDS_TREES);

			register(context, FALLEN_MAPLE_LEAVES_YELLOW, AutumnityConfiguredFeatures.FALLEN_MAPLE_LEAVES_YELLOW);
			register(context, FALLEN_MAPLE_LEAVES_ORANGE, AutumnityConfiguredFeatures.FALLEN_MAPLE_LEAVES_ORANGE);
			register(context, FALLEN_MAPLE_LEAVES_RED, AutumnityConfiguredFeatures.FALLEN_MAPLE_LEAVES_RED);

			register(context, EARLY_MAPLE_FOREST_FALLEN_LEAVES, AutumnityConfiguredFeatures.EARLY_MAPLE_FOREST_FALLEN_LEAVES, RarityFilter.onAverageOnceEvery(3));
			register(context, MIDDLE_MAPLE_FOREST_FALLEN_LEAVES, AutumnityConfiguredFeatures.MIDDLE_MAPLE_FOREST_FALLEN_LEAVES);
			register(context, LATE_MAPLE_FOREST_FALLEN_LEAVES, AutumnityConfiguredFeatures.LATE_MAPLE_FOREST_FALLEN_LEAVES);

			register(context, TREES_MAPLE, AutumnityConfiguredFeatures.TREES_MAPLE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));

			BetterNoiseBasedCountPlacement spottedMaplesCount = new BetterNoiseBasedCountPlacement(noise.get(AutumnityNoiseParameters.SPOTTED_MAPLES).get(), 1, -0.4F);
			register(context, SPOTTED_MAPLES_ORANGE, AutumnityConfiguredFeatures.ORANGE_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, treePlacementBase(spottedMaplesCount).build());
			register(context, SPOTTED_MAPLES_RED, AutumnityConfiguredFeatures.RED_MAPLE_TREE_FALLEN_LEAVES_BEES_0002, treePlacementBase(spottedMaplesCount).build());

			register(context, MAPLE_FOREST_FALLEN_LEAVES, AutumnityConfiguredFeatures.MAPLE_FOREST_FALLEN_LEAVES, CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, PATCH_FOUL_BERRY_BUSH, AutumnityConfiguredFeatures.PATCH_FOUL_BERRY_BUSH, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
			register(context, FLOWER_MAPLE_FOREST, AutumnityConfiguredFeatures.FLOWER_MAPLE_FOREST, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

			register(context, MAPLE_FOREST_VEGETATION, AutumnityConfiguredFeatures.MAPLE_FOREST_VEGETATION, VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
			register(context, PATCH_GRASS_MAPLE_FOREST, AutumnityConfiguredFeatures.PATCH_MAPLE_FOREST_GRASS, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

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