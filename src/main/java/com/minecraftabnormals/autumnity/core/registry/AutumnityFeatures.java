package com.minecraftabnormals.autumnity.core.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.minecraftabnormals.autumnity.common.world.gen.feature.FallenLeavesFeature;
import com.minecraftabnormals.autumnity.common.world.gen.feature.FallenLeavesMapleTreeFeature;
import com.minecraftabnormals.autumnity.common.world.gen.feature.MapleTreeFeature;
import com.minecraftabnormals.autumnity.common.world.gen.feature.PumpkinFieldsPumpkinFeature;
import com.minecraftabnormals.autumnity.core.Autumnity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Autumnity.MOD_ID);

	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> MAPLE_TREE = FEATURES.register("maple_tree", () -> new MapleTreeFeature(BaseTreeFeatureConfig.CODEC));
	// public static final RegistryObject<Feature<BaseTreeFeatureConfig>> DEAD_MAPLE_TREE = FEATURES.register("dead_maple_tree", () -> new DeadMapleTreeFeature(BaseTreeFeatureConfig.CODEC));
	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> FALLEN_LEAVES_MAPLE_TREE = FEATURES.register("fallen_leaves_maple_tree", () -> new FallenLeavesMapleTreeFeature(BaseTreeFeatureConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () -> new FallenLeavesFeature(NoFeatureConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> PUMPKIN_FIELDS_PUMPKIN = FEATURES.register("pumpkin_fields_pumpkin", () -> new PumpkinFieldsPumpkinFeature(NoFeatureConfig.CODEC));

	public static final class States {
		private static final BlockState MAPLE_LOG = AutumnityBlocks.MAPLE_LOG.get().defaultBlockState();
		private static final BlockState MAPLE_LEAVES = AutumnityBlocks.MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState YELLOW_MAPLE_LEAVES = AutumnityBlocks.YELLOW_MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState ORANGE_MAPLE_LEAVES = AutumnityBlocks.ORANGE_MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState RED_MAPLE_LEAVES = AutumnityBlocks.RED_MAPLE_LEAVES.get().defaultBlockState();
		private static final BlockState TALL_FOUL_BERRY_BUSH = AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get().defaultBlockState().setValue(SweetBerryBushBlock.AGE, 3);
		private static final BlockState AUTUMN_CROCUS = AutumnityBlocks.AUTUMN_CROCUS.get().defaultBlockState();
		private static final BlockState ROSE_BUSH = Blocks.ROSE_BUSH.defaultBlockState();
		private static final BlockState OXEYE_DAISY = Blocks.OXEYE_DAISY.defaultBlockState();
		private static final BlockState CORNFLOWER = Blocks.CORNFLOWER.defaultBlockState();
	}

	public static final class Configs {
		public static final BaseTreeFeatureConfig MAPLE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.MAPLE_LOG), new SimpleBlockStateProvider(States.MAPLE_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).ignoreVines().build();
		public static final BaseTreeFeatureConfig YELLOW_MAPLE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.MAPLE_LOG), new SimpleBlockStateProvider(States.YELLOW_MAPLE_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).ignoreVines().build();
		public static final BaseTreeFeatureConfig ORANGE_MAPLE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.MAPLE_LOG), new SimpleBlockStateProvider(States.ORANGE_MAPLE_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).ignoreVines().build();
		public static final BaseTreeFeatureConfig RED_MAPLE_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.MAPLE_LOG), new SimpleBlockStateProvider(States.RED_MAPLE_LEAVES), new BlobFoliagePlacer(FeatureSpread.fixed(0), FeatureSpread.fixed(0), 0), new StraightTrunkPlacer(0, 0, 0), new TwoLayerFeature(0, 0, 0))).ignoreVines().build();
		public static final BlockClusterFeatureConfig TALL_FOUL_BERRY_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.TALL_FOUL_BERRY_BUSH), new DoublePlantBlockPlacer())).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).noProjection().build();
		public static final BlockClusterFeatureConfig AUTUMN_CROCUS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.AUTUMN_CROCUS), new SimpleBlockPlacer())).tries(64).build();
		public static final BlockClusterFeatureConfig ROSE_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.ROSE_BUSH), new DoublePlantBlockPlacer())).tries(64).noProjection().build();
		public static final BlockClusterFeatureConfig OXEYE_DAISY_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.OXEYE_DAISY), new SimpleBlockPlacer())).tries(64).build();
		public static final BlockClusterFeatureConfig CORNFLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.CORNFLOWER), new SimpleBlockPlacer())).tries(64).build();
	}

	public static final class Configured {
		public static final ConfiguredFeature<?, ?> MAPLE_TREE_GREEN = AutumnityFeatures.MAPLE_TREE.get().configured(Configs.MAPLE_TREE_CONFIG);
		public static final ConfiguredFeature<?, ?> MAPLE_TREE_YELLOW = AutumnityFeatures.MAPLE_TREE.get().configured(Configs.YELLOW_MAPLE_TREE_CONFIG);
		public static final ConfiguredFeature<?, ?> FALLEN_LEAVES_MAPLE_TREE_YELLOW = AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get().configured(Configs.YELLOW_MAPLE_TREE_CONFIG);
		public static final ConfiguredFeature<?, ?> MAPLE_TREE_ORANGE = AutumnityFeatures.MAPLE_TREE.get().configured(Configs.ORANGE_MAPLE_TREE_CONFIG);
		public static final ConfiguredFeature<?, ?> FALLEN_LEAVES_MAPLE_TREE_ORANGE = AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get().configured(Configs.ORANGE_MAPLE_TREE_CONFIG);
		public static final ConfiguredFeature<?, ?> MAPLE_TREE_RED = AutumnityFeatures.MAPLE_TREE.get().configured(Configs.RED_MAPLE_TREE_CONFIG);
		public static final ConfiguredFeature<?, ?> MAPLE_TREE = MAPLE_TREE_GREEN.decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.01F, 1)));
		public static final ConfiguredFeature<?, ?> MAPLE_TREE_YELLOW_SPOTTED_FOREST = MAPLE_TREE_YELLOW.decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.01F, 1)));

		public static final ConfiguredFeature<?, ?> FALLEN_LEAVES = AutumnityFeatures.FALLEN_LEAVES.get().configured(IFeatureConfig.NONE).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE.chance(32));
		public static final ConfiguredFeature<?, ?> MAPLE_VEGETATION = Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(Features.HUGE_BROWN_MUSHROOM.weighted(0.025F), Features.HUGE_RED_MUSHROOM.weighted(0.05F), MAPLE_TREE_RED.weighted(0.3F), FALLEN_LEAVES_MAPLE_TREE_ORANGE.weighted(0.4F), FALLEN_LEAVES_MAPLE_TREE_YELLOW.weighted(0.2F)), MAPLE_TREE_GREEN)).decorated(Features.Placements.HEIGHTMAP_SQUARE);
		public static final ConfiguredFeature<?, ?> MAPLE_FOREST_VEGETATION = MAPLE_VEGETATION.decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
		public static final ConfiguredFeature<?, ?> PUMPKIN_FIELDS_VEGETATION = MAPLE_VEGETATION.decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(1, 0.2F, 1)));
		public static final ConfiguredFeature<?, ?> YELLOW_SPOTTED_FOREST_VEGETATION = Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(MAPLE_TREE_YELLOW.weighted(0.05F), Features.BIRCH_BEES_0002.weighted(0.2F), Features.FANCY_OAK_BEES_0002.weighted(0.1F)), Features.OAK_BEES_0002)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
		public static final ConfiguredFeature<?, ?> ORANGE_SPOTTED_DARK_FOREST_VEGETATION = Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(Features.HUGE_BROWN_MUSHROOM.weighted(0.025F), Features.HUGE_RED_MUSHROOM.weighted(0.05F), Features.DARK_OAK.weighted(0.6666667F), MAPLE_TREE_ORANGE.weighted(0.3F), Features.FANCY_OAK.weighted(0.1F)), Features.OAK)).decorated(Placement.DARK_OAK_TREE.configured(IPlacementConfig.NONE));
		public static final ConfiguredFeature<?, ?> RED_SPOTTED_TAIGA_VEGETATION = Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(MAPLE_TREE_RED.weighted(0.1F), Features.PINE.weighted(0.33333334F)), Features.SPRUCE)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.1F, 1)));

		public static final ConfiguredFeature<?, ?> PATCH_ROSE_BUSH = Feature.RANDOM_PATCH.configured(Configs.ROSE_BUSH_CONFIG);
		public static final ConfiguredFeature<?, ?> PATCH_FOUL_BERRY_BUSH = Feature.RANDOM_PATCH.configured(Configs.TALL_FOUL_BERRY_BUSH_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).chance(1);
		public static final ConfiguredFeature<?, ?> PATCH_GRASS_MAPLE_FOREST = Feature.RANDOM_PATCH.configured(Features.Configs.TALL_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).count(1);
		public static final ConfiguredFeature<?, ?> PATCH_GRASS_PUMPKIN_FIELDS = Feature.RANDOM_PATCH.configured(Features.Configs.TALL_GRASS_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP).squared().decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 5, 10)));
		public static final ConfiguredFeature<?, ?> PATCH_PUMPKINS_PUMPKIN_FIELDS = AutumnityFeatures.PUMPKIN_FIELDS_PUMPKIN.get().configured(IFeatureConfig.NONE).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).count(5);

		public static final ConfiguredFeature<?, ?> FLOWER_AUTUMN_CROCUS = Feature.FLOWER.configured(Configs.AUTUMN_CROCUS_CONFIG);
		public static final ConfiguredFeature<?, ?> FLOWER_OXEYE_DAISY = Feature.FLOWER.configured(Configs.CORNFLOWER_CONFIG);
		public static final ConfiguredFeature<?, ?> FLOWER_CORNFLOWER = Feature.FLOWER.configured(Configs.OXEYE_DAISY_CONFIG);
		public static final ConfiguredFeature<?, ?> FLOWER_MAPLE_FOREST = Feature.SIMPLE_RANDOM_SELECTOR.configured(new SingleRandomFeature(ImmutableList.of(() -> PATCH_ROSE_BUSH, () -> FLOWER_AUTUMN_CROCUS))).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(4);
		public static final ConfiguredFeature<?, ?> FLOWER_PUMPKIN_FIELDS = Feature.SIMPLE_RANDOM_SELECTOR.configured(new SingleRandomFeature(ImmutableList.of(() -> FLOWER_OXEYE_DAISY, () -> FLOWER_CORNFLOWER, () -> FLOWER_AUTUMN_CROCUS))).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP).decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 15, 4)));

		private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
			Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Autumnity.MOD_ID, name), configuredFeature);
		}

		public static void registerConfiguredFeatures() {
			register("maple_tree_green", MAPLE_TREE_GREEN);
			register("maple_tree_yellow", MAPLE_TREE_YELLOW);
			register("fallen_leaves_maple_tree_yellow", FALLEN_LEAVES_MAPLE_TREE_YELLOW);
			register("maple_tree_orange", MAPLE_TREE_ORANGE);
			register("fallen_leaves_maple_tree_orange", FALLEN_LEAVES_MAPLE_TREE_ORANGE);
			register("maple_tree_red", MAPLE_TREE_RED);
			register("maple_tree", MAPLE_TREE);
			register("maple_tree_yellow_spotted_forest", MAPLE_TREE_YELLOW_SPOTTED_FOREST);

			register("fallen_leaves", FALLEN_LEAVES);
			register("maple_vegetation", MAPLE_VEGETATION);
			register("maple_forest_vegetation", MAPLE_FOREST_VEGETATION);
			register("pumpkin_fields_vegetation", PUMPKIN_FIELDS_VEGETATION);
			register("yellow_spotted_forest_vegetation", YELLOW_SPOTTED_FOREST_VEGETATION);
			register("orange_spotted_dark_forest_vegetation", ORANGE_SPOTTED_DARK_FOREST_VEGETATION);
			register("red_spotted_taiga_vegetation", RED_SPOTTED_TAIGA_VEGETATION);

			register("patch_rose_bush", PATCH_ROSE_BUSH);
			register("patch_grass_foul_berry_bush", PATCH_FOUL_BERRY_BUSH);
			register("patch_grass_maple_forest", PATCH_GRASS_MAPLE_FOREST);
			register("patch_grass_pumpkin_fields", PATCH_GRASS_PUMPKIN_FIELDS);
			register("patch_pumpkins_pumpkin_fields", PATCH_PUMPKINS_PUMPKIN_FIELDS);

			register("flower_autumn_crocus", FLOWER_AUTUMN_CROCUS);
			register("flower_oxeye_daisy", FLOWER_OXEYE_DAISY);
			register("flower_cornflower", FLOWER_CORNFLOWER);
			register("flower_maple_forest", FLOWER_MAPLE_FOREST);
			register("flower_pumpkin_fields", FLOWER_PUMPKIN_FIELDS);
		}
	}
}