package com.teamabnormals.autumnity.core.data.server;

import com.google.common.collect.Maps;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.autumnity.core.other.AutumnityBlockFamilies;
import com.teamabnormals.autumnity.core.other.tags.AutumnityItemTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityConditions;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.autumnity.integration.boatload.AutumnityBoatTypes;
import com.teamabnormals.blueprint.core.api.conditions.BlueprintAndCondition;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import com.teamabnormals.boatload.core.data.server.BoatloadRecipeProvider;
import com.teamabnormals.woodworks.core.data.server.WoodworksRecipeProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.autumnity.core.registry.AutumnityBlocks.*;

public class AutumnityRecipeProvider extends BlueprintRecipeProvider {
	public static final ModLoadedCondition BERRY_GOOD_LOADED = new ModLoadedCondition("berry_good");
	public static final ConfigValueCondition FOUL_BERRIES_REQUIRE_PIPES = config(AutumnityConfig.COMMON.foulBerriesRequirePips, "foul_berries_require_pips");
	public static final BlueprintAndCondition BERRY_GOOD_AND_PIPS = new BlueprintAndCondition(BERRY_GOOD_LOADED, FOUL_BERRIES_REQUIRE_PIPES);

	public static final ModLoadedCondition ENDERGETIC_LOADED = new ModLoadedCondition("endergetic");
	public static final ModLoadedCondition INCUBATION_LOADED = new ModLoadedCondition("incubation");
	public static final ModLoadedCondition CAVERNS_AND_CHASMS_LOADED = new ModLoadedCondition("caverns_and_chasms");
	public static final NotCondition ABNORMALS_DELIGHT_NOT_LOADED = new NotCondition(new ModLoadedCondition("abnormals_delight"));

	public AutumnityRecipeProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Autumnity.MOD_ID, output, provider);
	}

	@Override
	public void buildRecipes(RecipeOutput output) {
		conversionRecipe(output, Items.MAGENTA_DYE, AUTUMN_CROCUS.get(), "magenta_dye");
		conversionRecipeBuilder(AutumnityItems.FOUL_BERRY_PIPS.get(), AutumnityItems.FOUL_BERRIES.get(), 1).save(output.withConditions(BERRY_GOOD_AND_PIPS));
		conditionalStorageRecipes(output, BERRY_GOOD_LOADED, RecipeCategory.FOOD, AutumnityItems.FOUL_BERRIES.get(), RecipeCategory.DECORATIONS, FOUL_BERRY_BASKET.get());
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, AutumnityItems.FOUL_SOUP.get()).requires(AutumnityItems.FOUL_BERRIES.get(), 2).requires(Items.SPIDER_EYE).requires(Items.BOWL, 1).unlockedBy("has_foul_berries", has(AutumnityItems.FOUL_BERRIES.get())).save(output.withConditions(ABNORMALS_DELIGHT_NOT_LOADED));

		foodCookingRecipes(output, AutumnityItems.TURKEY_PIECE.get(), AutumnityItems.COOKED_TURKEY_PIECE.get());
		foodCookingRecipes(output, TURKEY.get(), COOKED_TURKEY.get());
		conversionRecipeBuilder(RecipeCategory.FOOD, AutumnityItems.TURKEY_PIECE.get(), TURKEY.get(), 5).save(output.withConditions(ABNORMALS_DELIGHT_NOT_LOADED), getModConversionRecipeName(AutumnityItems.TURKEY_PIECE.get(), TURKEY.get()));
		conversionRecipeBuilder(RecipeCategory.FOOD, AutumnityItems.COOKED_TURKEY_PIECE.get(), COOKED_TURKEY.get(), 5).save(output.withConditions(ABNORMALS_DELIGHT_NOT_LOADED), getModConversionRecipeName(AutumnityItems.COOKED_TURKEY_PIECE.get(), COOKED_TURKEY.get()));
		conditionalStorageRecipes(output, INCUBATION_LOADED, RecipeCategory.MISC, AutumnityItems.TURKEY_EGG.get(), RecipeCategory.DECORATIONS, TURKEY_EGG_CRATE.get());

		foodCookingRecipes(output, AutumnityItems.SAP_BOTTLE.get(), AutumnityItems.SYRUP_BOTTLE.get());
		conversionRecipe(output, Items.SUGAR, AutumnityItems.SAP_BOTTLE.get(), "sugar");
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SAPPY_MAPLE_LOG.get()).requires(AutumnityItems.SAP_BOTTLE.get()).requires(STRIPPED_MAPLE_LOG.get()).unlockedBy("has_sap_bottle", has(AutumnityItems.SAP_BOTTLE.get())).save(output, getModConversionRecipeName(SAPPY_MAPLE_LOG.get(), AutumnityItems.SAP_BOTTLE.get()));
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SAPPY_MAPLE_WOOD.get()).requires(AutumnityItems.SAP_BOTTLE.get()).requires(STRIPPED_MAPLE_WOOD.get()).unlockedBy("has_sap_bottle", has(AutumnityItems.SAP_BOTTLE.get())).save(output, getModConversionRecipeName(SAPPY_MAPLE_WOOD.get(), AutumnityItems.SAP_BOTTLE.get()));
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, PANCAKE.get()).requires(AutumnityItems.SYRUP_BOTTLE.get()).requires(Tags.Items.DRINKS_MILK).requires(Tags.Items.EGGS).requires(Items.WHEAT, 2).unlockedBy("has_syrup_bottle", has(AutumnityItems.SYRUP_BOTTLE.get())).save(output);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, AutumnityItems.PUMPKIN_BREAD.get(), 2).requires(AutumnityItems.SYRUP_BOTTLE.get()).requires(Tags.Items.PUMPKINS_NORMAL).requires(Items.WHEAT, 2).unlockedBy("has_syrup_bottle", has(AutumnityItems.SYRUP_BOTTLE.get())).save(output.withConditions(ABNORMALS_DELIGHT_NOT_LOADED));

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, REDSTONE_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', Blocks.REDSTONE_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, SOUL_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', Blocks.SOUL_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_PUMPKIN_SLICE.get(), 4).define('#', Blocks.PUMPKIN).pattern("##").pattern("##").unlockedBy("has_pumpkin", has(Blocks.PUMPKIN)).save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_JACK_O_LANTERN_SLICE.get()).define('A', CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(CARVED_LARGE_PUMPKIN_SLICE.get())).save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get()).define('A', CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.REDSTONE_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(CARVED_LARGE_PUMPKIN_SLICE.get())).save(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_SOUL_JACK_O_LANTERN_SLICE.get()).define('A', CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.SOUL_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(CARVED_LARGE_PUMPKIN_SLICE.get())).save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ENDER_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', AutumnityItemTags.TORCHES_ENDER).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).save(output.withConditions(ENDERGETIC_LOADED));
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_ENDER_JACK_O_LANTERN_SLICE.get()).define('A', CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', AutumnityItemTags.TORCHES_ENDER).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(CARVED_LARGE_PUMPKIN_SLICE.get())).save(output.withConditions(ENDERGETIC_LOADED));
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CUPRIC_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', AutumnityItemTags.TORCHES_CUPRIC).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).save(output.withConditions(CAVERNS_AND_CHASMS_LOADED));
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_CUPRIC_JACK_O_LANTERN_SLICE.get()).define('A', CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', AutumnityItemTags.TORCHES_CUPRIC).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(CARVED_LARGE_PUMPKIN_SLICE.get())).save(output.withConditions(CAVERNS_AND_CHASMS_LOADED));

		storageRecipes(output, RecipeCategory.MISC, SNAIL_GOO.get(), RecipeCategory.DECORATIONS, SNAIL_GOO_BLOCK.get());
		storageRecipes(output, RecipeCategory.MISC, AutumnityItems.SNAIL_SHELL_PIECE.get(), RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_BLOCK.get());
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AutumnityItems.SNAIL_SHELL_CHESTPLATE.get()).define('X', AutumnityItems.SNAIL_SHELL_PIECE.get()).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_snail_shell_piece", has(AutumnityItems.SNAIL_SHELL_PIECE.get())).save(output);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AutumnityItems.SWIRL_BANNER_PATTERN.get()).requires(Items.PAPER).requires(AutumnityItems.SNAIL_SHELL_PIECE.get()).unlockedBy("has_snail_shell_piece", has(AutumnityItems.SNAIL_SHELL_PIECE.get())).save(output);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, AutumnityBlocks.SNAIL_SHELL_BRICKS.get(), 8).define('#', Blocks.STONE_BRICKS).define('S', AutumnityItems.SNAIL_SHELL_PIECE.get()).pattern("###").pattern("#S#").pattern("###").unlockedBy("has_snail_shell_piece", has(AutumnityItems.SNAIL_SHELL_PIECE.get())).save(output);
		generateRecipes(output, AutumnityBlockFamilies.SNAIL_SHELL_BRICKS_FAMILY, FeatureFlags.DEFAULT_FLAGS);
		stonecutterRecipe(output, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_BRICK_SLAB.get(), SNAIL_SHELL_BRICKS.get(), 2);
		stonecutterRecipe(output, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_BRICK_STAIRS.get(), SNAIL_SHELL_BRICKS.get());
		stonecutterRecipe(output, RecipeCategory.DECORATIONS, SNAIL_SHELL_BRICK_WALL.get(), SNAIL_SHELL_BRICKS.get());
		stonecutterRecipe(output, RecipeCategory.BUILDING_BLOCKS, CHISELED_SNAIL_SHELL_BRICKS.get(), SNAIL_SHELL_BRICKS.get());

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILES.get(), 4).define('#', SNAIL_SHELL_BRICKS.get()).pattern("##").pattern("##").unlockedBy("has_snail_shell_bricks", has(SNAIL_SHELL_BRICKS.get())).save(output);
		generateRecipes(output, AutumnityBlockFamilies.SNAIL_SHELL_TILES_FAMILY, FeatureFlags.DEFAULT_FLAGS);
		stonecutterRecipe(output, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILE_SLAB.get(), SNAIL_SHELL_TILES.get(), 2);
		stonecutterRecipe(output, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILE_STAIRS.get(), SNAIL_SHELL_TILES.get());
		stonecutterRecipe(output, RecipeCategory.DECORATIONS, SNAIL_SHELL_TILE_WALL.get(), SNAIL_SHELL_TILES.get());
		stonecutterRecipe(output, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILES.get(), SNAIL_SHELL_BRICKS.get());
		stonecutterRecipe(output, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILE_SLAB.get(), SNAIL_SHELL_BRICKS.get(), 2);
		stonecutterRecipe(output, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILE_STAIRS.get(), SNAIL_SHELL_BRICKS.get());
		stonecutterRecipe(output, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILE_WALL.get(), SNAIL_SHELL_BRICKS.get());

		generateRecipes(output, AutumnityBlockFamilies.MAPLE_PLANKS_FAMILY, FeatureFlags.DEFAULT_FLAGS);
		planksFromLogs(output, MAPLE_PLANKS.get(), AutumnityItemTags.MAPLE_LOGS, 4);
		woodFromLogs(output, MAPLE_WOOD.get(), MAPLE_LOG.get());
		woodFromLogs(output, STRIPPED_MAPLE_WOOD.get(), STRIPPED_MAPLE_LOG.get());
		woodFromLogs(output, SAPPY_MAPLE_WOOD.get(), SAPPY_MAPLE_LOG.get());
		hangingSign(output, MAPLE_HANGING_SIGNS.getFirst().get(), STRIPPED_MAPLE_LOG.get());
		leafPileRecipes(output, MAPLE_LEAVES.get(), MAPLE_LEAF_PILE.get());
		leafPileRecipes(output, YELLOW_MAPLE_LEAVES.get(), YELLOW_MAPLE_LEAF_PILE.get());
		leafPileRecipes(output, ORANGE_MAPLE_LEAVES.get(), ORANGE_MAPLE_LEAF_PILE.get());
		leafPileRecipes(output, RED_MAPLE_LEAVES.get(), RED_MAPLE_LEAF_PILE.get());

		BoatloadRecipeProvider.boatRecipes(output, AutumnityBoatTypes.MAPLE);
		WoodworksRecipeProvider.baseRecipes(output, MAPLE_PLANKS.get(), MAPLE_SLAB.get(), MAPLE_BOARDS.get(), MAPLE_BOOKSHELF.get(), CHISELED_MAPLE_BOOKSHELF.get(), MAPLE_LADDER.get(), MAPLE_BEEHIVE.get(), MAPLE_CHEST.get(), TRAPPED_MAPLE_CHEST.get(), Autumnity.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(output, AutumnityBlockFamilies.MAPLE_PLANKS_FAMILY, AutumnityItemTags.MAPLE_LOGS, MAPLE_BOARDS.get(), MAPLE_LADDER.get(), Autumnity.MOD_ID);
	}

	public static ConfigValueCondition config(ModConfigSpec.ConfigValue<?> value, String key, boolean inverted) {
		return new ConfigValueCondition(AutumnityConditions.CONFIG.get(), value, key, Maps.newHashMap(), inverted);
	}

	public static ConfigValueCondition config(ModConfigSpec.ConfigValue<?> value, String key) {
		return config(value, key, false);
	}
}