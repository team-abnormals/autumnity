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
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

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

	public static final class AutumnityPlacementUtils {
		public static final BetterNoiseBasedCountPlacement SPOTTED_MAPLES_COUNT = new BetterNoiseBasedCountPlacement(AutumnityNoiseParameters.SPOTTED_MAPLES.getHolder().orElseThrow(), 12, -0.4F);
	}

	public static final class AutumnityConfiguredFeatures {
		public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Autumnity.MOD_ID);

		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> MAPLE_TREE = register("maple_tree", () -> new ConfiguredFeature<>(AutumnityFeatures.MAPLE_TREE.get(), Configs.MAPLE_TREE_CONFIG));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> MAPLE_TREE_YELLOW = register("maple_tree_yellow", () -> new ConfiguredFeature<>(AutumnityFeatures.MAPLE_TREE.get(), Configs.YELLOW_MAPLE_TREE_CONFIG));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> FALLEN_LEAVES_MAPLE_TREE_YELLOW = register("fallen_leaves_maple_tree_yellow", () -> new ConfiguredFeature<>(AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), Configs.YELLOW_MAPLE_TREE_CONFIG));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> MAPLE_TREE_ORANGE = register("maple_tree_orange", () -> new ConfiguredFeature<>(AutumnityFeatures.MAPLE_TREE.get(), Configs.ORANGE_MAPLE_TREE_CONFIG));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> FALLEN_LEAVES_MAPLE_TREE_ORANGE = register("fallen_leaves_maple_tree_orange", () -> new ConfiguredFeature<>(AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), Configs.ORANGE_MAPLE_TREE_CONFIG));
		public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> MAPLE_TREE_RED = register("maple_tree_red", () -> new ConfiguredFeature<>(AutumnityFeatures.MAPLE_TREE.get(), Configs.RED_MAPLE_TREE_CONFIG));

		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> FALLEN_LEAVES = register("fallen_leaves", () -> new ConfiguredFeature<>(AutumnityFeatures.FALLEN_LEAVES.get(), FeatureConfiguration.NONE));
		public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, ?>> MAPLE_FOREST_VEGETATION = register("maple_forest_vegetation", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_BROWN_MUSHROOM), 0.025F), new WeightedPlacedFeature(PlacementUtils.inlinePlaced(TreeFeatures.HUGE_RED_MUSHROOM), 0.05F), new WeightedPlacedFeature(AutumnityPlacedFeatures.RED_MAPLE_TREE_CHECKED.getHolder().get(), 0.3F), new WeightedPlacedFeature(AutumnityPlacedFeatures.FALLEN_LEAVES_ORANGE_MAPLE_TREE_CHECKED.getHolder().get(), 0.4F), new WeightedPlacedFeature(AutumnityPlacedFeatures.FALLEN_LEAVES_YELLOW_MAPLE_TREE_CHECKED.getHolder().get(), 0.2F)), AutumnityPlacedFeatures.MAPLE_TREE_CHECKED.getHolder().get())));

		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_TALL_GRASS = register("patch_tall_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.TALL_GRASS)))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_FOUL_BERRY_BUSH = register("patch_foul_berry_bush", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get().defaultBlockState().setValue(TallFoulBerryBushBlock.AGE, 3))), List.of(Blocks.GRASS_BLOCK))));
		public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> PATCH_PUMPKINS_PUMPKIN_FIELDS = register("patch_pumpkins_pumpkin_fields", () -> new ConfiguredFeature<>(AutumnityFeatures.PUMPKIN_FIELDS_PUMPKIN.get(), FeatureConfiguration.NONE));

		public static final RegistryObject<ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> FLOWER_MAPLE_FOREST = register("forest_flowers", () -> new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH)))), PlacementUtils.inlinePlaced(Feature.NO_BONEMEAL_FLOWER, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(AutumnityBlocks.AUTUMN_CROCUS.get()))))))));
		public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_PUMPKIN_FIELDS = register("flower_plain", () -> new ConfiguredFeature<>(Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(AutumnityBlocks.AUTUMN_CROCUS.get().defaultBlockState(), 1).add(Blocks.OXEYE_DAISY.defaultBlockState(), 1).add(Blocks.CORNFLOWER.defaultBlockState(), 1)))))));

		private static <FC extends FeatureConfiguration, F extends Feature<FC>> RegistryObject<ConfiguredFeature<FC, ?>> register(String name, Supplier<ConfiguredFeature<FC, F>> feature) {
			return CONFIGURED_FEATURES.register(name, feature);
		}
	}

	public static final class AutumnityPlacedFeatures {
		public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Autumnity.MOD_ID);

		public static final RegistryObject<PlacedFeature> MAPLE_TREE_CHECKED = register("maple_tree_checked", AutumnityConfiguredFeatures.MAPLE_TREE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> RED_MAPLE_TREE_CHECKED = register("red_maple_tree_checked", AutumnityConfiguredFeatures.MAPLE_TREE_RED, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

		public static final RegistryObject<PlacedFeature> FALLEN_LEAVES_YELLOW_MAPLE_TREE_CHECKED = register("fallen_leaves_yellow_maple_tree_checked", AutumnityConfiguredFeatures.FALLEN_LEAVES_MAPLE_TREE_YELLOW, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
		public static final RegistryObject<PlacedFeature> FALLEN_LEAVES_ORANGE_MAPLE_TREE_CHECKED = register("fallen_leaves_orange_maple_tree_checked", AutumnityConfiguredFeatures.FALLEN_LEAVES_MAPLE_TREE_ORANGE, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

		public static final RegistryObject<PlacedFeature> TREES_MAPLE = register("trees_maple", AutumnityConfiguredFeatures.MAPLE_TREE, VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));

		public static final RegistryObject<PlacedFeature> TREES_MAPLE_YELLOW = register("trees_maple_yellow", AutumnityConfiguredFeatures.MAPLE_TREE_YELLOW, treePlacementBase(AutumnityPlacementUtils.SPOTTED_MAPLES_COUNT).build());
		public static final RegistryObject<PlacedFeature> TREES_MAPLE_ORANGE = register("trees_maple_orange", AutumnityConfiguredFeatures.MAPLE_TREE_ORANGE, treePlacementBase(AutumnityPlacementUtils.SPOTTED_MAPLES_COUNT).build());
		public static final RegistryObject<PlacedFeature> TREES_MAPLE_RED = register("trees_maple_red", AutumnityConfiguredFeatures.MAPLE_TREE_RED, treePlacementBase(AutumnityPlacementUtils.SPOTTED_MAPLES_COUNT).build());

		public static final RegistryObject<PlacedFeature> FALLEN_LEAVES = register("fallen_leaves", AutumnityConfiguredFeatures.FALLEN_LEAVES, RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_FOUL_BERRY_BUSH = register("patch_foul_berry_bush", AutumnityConfiguredFeatures.PATCH_FOUL_BERRY_BUSH, RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_MAPLE_FOREST = register("flower_maple_forest", AutumnityConfiguredFeatures.FLOWER_MAPLE_FOREST, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		public static final RegistryObject<PlacedFeature> MAPLE_FOREST_VEGETATION = register("maple_forest_vegetation", AutumnityConfiguredFeatures.MAPLE_FOREST_VEGETATION, VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
		public static final RegistryObject<PlacedFeature> PUMPKIN_FIELDS_VEGETATION = register("pumpkin_fields_vegetation", AutumnityConfiguredFeatures.MAPLE_FOREST_VEGETATION, VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.2F, 1)));
		public static final RegistryObject<PlacedFeature> PATCH_TALL_GRASS_PUMPKIN_FIELDS = register("patch_tall_grass_pumpkin_fields", AutumnityConfiguredFeatures.PATCH_TALL_GRASS, NoiseThresholdCountPlacement.of(-0.8D, 5, 10), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> PATCH_PUMPKIN_PUMPKIN_FIELDS = register("patch_pumpkin_pumpkin_fields", AutumnityConfiguredFeatures.PATCH_PUMPKINS_PUMPKIN_FIELDS, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		public static final RegistryObject<PlacedFeature> FLOWER_PUMPKIN_FIELDS = register("flower_pumpkin_fields", AutumnityConfiguredFeatures.FLOWER_PUMPKIN_FIELDS, NoiseThresholdCountPlacement.of(-0.8D, 15, 4), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		private static Builder<PlacementModifier> treePlacementBase(PlacementModifier modifier) {
			return ImmutableList.<PlacementModifier>builder().add(modifier).add(InSquarePlacement.spread()).add(VegetationPlacements.TREE_THRESHOLD).add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR).add(BiomeFilter.biome());
		}

		private static RegistryObject<PlacedFeature> register(String name, RegistryObject<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... placementModifiers) {
			return register(name, feature, List.of(placementModifiers));
		}

		@SuppressWarnings("unchecked")
		private static RegistryObject<PlacedFeature> register(String name, RegistryObject<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placementModifiers) {
			return PLACED_FEATURES.register(name, () -> new PlacedFeature((Holder<ConfiguredFeature<?, ?>>) feature.getHolder().get(), ImmutableList.copyOf(placementModifiers)));
		}
	}
}