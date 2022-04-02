package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.common.levelgen.feature.FallenLeavesFeature;
import com.teamabnormals.autumnity.common.levelgen.feature.FallenLeavesMapleTreeFeature;
import com.teamabnormals.autumnity.common.levelgen.feature.MapleTreeFeature;
import com.teamabnormals.autumnity.common.levelgen.feature.PumpkinFieldsPumpkinFeature;
import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Autumnity.MOD_ID);

	public static final RegistryObject<Feature<TreeConfiguration>> MAPLE_TREE = FEATURES.register("maple_tree", () -> new MapleTreeFeature(TreeConfiguration.CODEC));
	// public static final RegistryObject<Feature<BaseTreeFeatureConfig>> DEAD_MAPLE_TREE = FEATURES.register("dead_maple_tree", () -> new DeadMapleTreeFeature(BaseTreeFeatureConfig.CODEC));
	public static final RegistryObject<Feature<TreeConfiguration>> FALLEN_LEAVES_MAPLE_TREE = FEATURES.register("fallen_leaves_maple_tree", () -> new FallenLeavesMapleTreeFeature(TreeConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> FALLEN_LEAVES = FEATURES.register("fallen_leaves", () -> new FallenLeavesFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<NoneFeatureConfiguration>> PUMPKIN_FIELDS_PUMPKIN = FEATURES.register("pumpkin_fields_pumpkin", () -> new PumpkinFieldsPumpkinFeature(NoneFeatureConfiguration.CODEC));

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
		public static final TreeConfiguration MAPLE_TREE_CONFIG = createMaple(States.MAPLE_LEAVES).build();
		public static final TreeConfiguration YELLOW_MAPLE_TREE_CONFIG = createMaple(States.YELLOW_MAPLE_LEAVES).build();
		public static final TreeConfiguration ORANGE_MAPLE_TREE_CONFIG = createMaple(States.ORANGE_MAPLE_LEAVES).build();
		public static final TreeConfiguration RED_MAPLE_TREE_CONFIG = createMaple(States.RED_MAPLE_LEAVES).build();

//		public static final RandomPatchConfiguration TALL_FOUL_BERRY_BUSH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(States.TALL_FOUL_BERRY_BUSH), new DoublePlantPlacer())).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).noProjection().build();
//		public static final RandomPatchConfiguration AUTUMN_CROCUS_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(States.AUTUMN_CROCUS), new SimpleBlockPlacer())).tries(64).build();
//		public static final RandomPatchConfiguration ROSE_BUSH_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(States.ROSE_BUSH), new DoublePlantPlacer())).tries(64).noProjection().build();
//		public static final RandomPatchConfiguration OXEYE_DAISY_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(States.OXEYE_DAISY), new SimpleBlockPlacer())).tries(64).build();
//		public static final RandomPatchConfiguration CORNFLOWER_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(BlockStateProvider.simple(States.CORNFLOWER), new SimpleBlockPlacer())).tries(64).build();
//

		private static TreeConfiguration.TreeConfigurationBuilder createMaple(BlockState leavesState) {
			return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(States.MAPLE_LOG), new StraightTrunkPlacer(0, 0, 0), BlockStateProvider.simple(leavesState), new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0), new TwoLayersFeatureSize(0, 0, 0)).ignoreVines();
		}

	}

	public static final class AutumnityConfiguredFeatures {
		public static final Holder<ConfiguredFeature<?, ?>> MAPLE_TREE_GREEN = register("maple_tree_green", AutumnityFeatures.MAPLE_TREE.get(), Configs.MAPLE_TREE_CONFIG);
		public static final Holder<ConfiguredFeature<?, ?>> MAPLE_TREE_YELLOW = register("maple_tree_yellow", AutumnityFeatures.MAPLE_TREE.get(), Configs.YELLOW_MAPLE_TREE_CONFIG);
		public static final Holder<ConfiguredFeature<?, ?>> FALLEN_LEAVES_MAPLE_TREE_YELLOW = register("fallen_leaves_maple_tree_yellow", AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), Configs.YELLOW_MAPLE_TREE_CONFIG);
		public static final Holder<ConfiguredFeature<?, ?>> MAPLE_TREE_ORANGE = register("maple_tree_orange", AutumnityFeatures.MAPLE_TREE.get(), Configs.ORANGE_MAPLE_TREE_CONFIG);
		public static final Holder<ConfiguredFeature<?, ?>> FALLEN_LEAVES_MAPLE_TREE_ORANGE = register("fallen_leaves_maple_tree_orange", AutumnityFeatures.FALLEN_LEAVES_MAPLE_TREE.get(), Configs.ORANGE_MAPLE_TREE_CONFIG);
		public static final Holder<ConfiguredFeature<?, ?>> MAPLE_TREE_RED = register("maple_tree_red", AutumnityFeatures.MAPLE_TREE.get(), Configs.RED_MAPLE_TREE_CONFIG);

		//		public static final ConfiguredFeature<?, ?> MAPLE_TREE = MAPLE_TREE_GREEN.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.01F, 1)));
//		public static final ConfiguredFeature<?, ?> MAPLE_TREE_YELLOW_SPOTTED_FOREST = MAPLE_TREE_YELLOW.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.01F, 1)));
//
//		public static final ConfiguredFeature<?, ?> FALLEN_LEAVES = AutumnityFeatures.FALLEN_LEAVES.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.chance(32));
//		public static final ConfiguredFeature<?, ?> MAPLE_VEGETATION = Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(Features.HUGE_BROWN_MUSHROOM.weighted(0.025F), Features.HUGE_RED_MUSHROOM.weighted(0.05F), MAPLE_TREE_RED.weighted(0.3F), FALLEN_LEAVES_MAPLE_TREE_ORANGE.weighted(0.4F), FALLEN_LEAVES_MAPLE_TREE_YELLOW.weighted(0.2F)), MAPLE_TREE_GREEN)).decorated(Features.Decorators.HEIGHTMAP_SQUARE);
//		public static final ConfiguredFeature<?, ?> MAPLE_FOREST_VEGETATION = MAPLE_VEGETATION.decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1)));
//		public static final ConfiguredFeature<?, ?> PUMPKIN_FIELDS_VEGETATION = MAPLE_VEGETATION.decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(1, 0.2F, 1)));
//		public static final ConfiguredFeature<?, ?> YELLOW_SPOTTED_FOREST_VEGETATION = Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(MAPLE_TREE_YELLOW.weighted(0.05F), Features.BIRCH_BEES_0002.weighted(0.2F), Features.FANCY_OAK_BEES_0002.weighted(0.1F)), Features.OAK_BEES_0002)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1)));
//		public static final ConfiguredFeature<?, ?> ORANGE_SPOTTED_DARK_FOREST_VEGETATION = Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(Features.HUGE_BROWN_MUSHROOM.weighted(0.025F), Features.HUGE_RED_MUSHROOM.weighted(0.05F), Features.DARK_OAK.weighted(0.6666667F), MAPLE_TREE_ORANGE.weighted(0.3F), Features.FANCY_OAK.weighted(0.1F)), Features.OAK)).decorated(FeatureDecorator.DARK_OAK_TREE.configured(DecoratorConfiguration.NONE));
//		public static final ConfiguredFeature<?, ?> RED_SPOTTED_TAIGA_VEGETATION = Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(MAPLE_TREE_RED.weighted(0.1F), Features.PINE.weighted(0.33333334F)), Features.SPRUCE)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1)));
//
//		public static final ConfiguredFeature<?, ?> PATCH_ROSE_BUSH = Feature.RANDOM_PATCH.configured(Configs.ROSE_BUSH_CONFIG);
//		public static final ConfiguredFeature<?, ?> PATCH_FOUL_BERRY_BUSH = Feature.RANDOM_PATCH.configured(Configs.TALL_FOUL_BERRY_BUSH_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).chance(1);
//		public static final ConfiguredFeature<?, ?> PATCH_GRASS_MAPLE_FOREST = Feature.RANDOM_PATCH.configured(Features.Configs.TALL_GRASS_CONFIG).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).count(1);
//		public static final ConfiguredFeature<?, ?> PATCH_GRASS_PUMPKIN_FIELDS = Feature.RANDOM_PATCH.configured(Features.Configs.TALL_GRASS_CONFIG).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP).squared().decorated(FeatureDecorator.COUNT_NOISE.configured(new NoiseDependantDecoratorConfiguration(-0.8D, 5, 10)));
//		public static final ConfiguredFeature<?, ?> PATCH_PUMPKINS_PUMPKIN_FIELDS = AutumnityFeatures.PUMPKIN_FIELDS_PUMPKIN.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE).count(5);
//
//		public static final ConfiguredFeature<?, ?> FLOWER_AUTUMN_CROCUS = Feature.FLOWER.configured(Configs.AUTUMN_CROCUS_CONFIG);
//		public static final ConfiguredFeature<?, ?> FLOWER_OXEYE_DAISY = Feature.FLOWER.configured(Configs.CORNFLOWER_CONFIG);
//		public static final ConfiguredFeature<?, ?> FLOWER_CORNFLOWER = Feature.FLOWER.configured(Configs.OXEYE_DAISY_CONFIG);
//		public static final ConfiguredFeature<?, ?> FLOWER_MAPLE_FOREST = Feature.SIMPLE_RANDOM_SELECTOR.configured(new SimpleRandomFeatureConfiguration(ImmutableList.of(() -> PATCH_ROSE_BUSH, () -> FLOWER_AUTUMN_CROCUS))).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(4);
//		public static final ConfiguredFeature<?, ?> FLOWER_PUMPKIN_FIELDS = Feature.SIMPLE_RANDOM_SELECTOR.configured(new SimpleRandomFeatureConfiguration(ImmutableList.of(() -> FLOWER_OXEYE_DAISY, () -> FLOWER_CORNFLOWER, () -> FLOWER_AUTUMN_CROCUS))).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP).decorated(FeatureDecorator.COUNT_NOISE.configured(new NoiseDependantDecoratorConfiguration(-0.8D, 15, 4)));

		public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> register(String name, F feature, FC config) {
			return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Autumnity.MOD_ID, name), new ConfiguredFeature<>(feature, config));
		}

//		public static void registerConfiguredFeatures() {
//			register("maple_tree_green", MAPLE_TREE_GREEN);
//			register("maple_tree_yellow", MAPLE_TREE_YELLOW);
//			register("fallen_leaves_maple_tree_yellow", FALLEN_LEAVES_MAPLE_TREE_YELLOW);
//			register("maple_tree_orange", MAPLE_TREE_ORANGE);
//			register("fallen_leaves_maple_tree_orange", FALLEN_LEAVES_MAPLE_TREE_ORANGE);
//			register("maple_tree_red", MAPLE_TREE_RED);
//			register("maple_tree", MAPLE_TREE);
//			register("maple_tree_yellow_spotted_forest", MAPLE_TREE_YELLOW_SPOTTED_FOREST);
//
//			register("fallen_leaves", FALLEN_LEAVES);
//			register("maple_vegetation", MAPLE_VEGETATION);
//			register("maple_forest_vegetation", MAPLE_FOREST_VEGETATION);
//			register("pumpkin_fields_vegetation", PUMPKIN_FIELDS_VEGETATION);
//			register("yellow_spotted_forest_vegetation", YELLOW_SPOTTED_FOREST_VEGETATION);
//			register("orange_spotted_dark_forest_vegetation", ORANGE_SPOTTED_DARK_FOREST_VEGETATION);
//			register("red_spotted_taiga_vegetation", RED_SPOTTED_TAIGA_VEGETATION);
//
//			register("patch_rose_bush", PATCH_ROSE_BUSH);
//			register("patch_grass_foul_berry_bush", PATCH_FOUL_BERRY_BUSH);
//			register("patch_grass_maple_forest", PATCH_GRASS_MAPLE_FOREST);
//			register("patch_grass_pumpkin_fields", PATCH_GRASS_PUMPKIN_FIELDS);
//			register("patch_pumpkins_pumpkin_fields", PATCH_PUMPKINS_PUMPKIN_FIELDS);
//
//			register("flower_autumn_crocus", FLOWER_AUTUMN_CROCUS);
//			register("flower_oxeye_daisy", FLOWER_OXEYE_DAISY);
//			register("flower_cornflower", FLOWER_CORNFLOWER);
//			register("flower_maple_forest", FLOWER_MAPLE_FOREST);
//			register("flower_pumpkin_fields", FLOWER_PUMPKIN_FIELDS);
//		}
	}

	public static final class AutumnityPlacedFeatures {
		public static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... placementModifiers) {
			return register(name, configuredFeature, List.of(placementModifiers));
		}

		public static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> placementModifiers) {
			return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Autumnity.MOD_ID, name), new PlacedFeature(Holder.hackyErase(configuredFeature), placementModifiers));
		}
	}
}