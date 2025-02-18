package com.teamabnormals.autumnity.core.data.server;

import com.google.common.collect.Maps;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.autumnity.core.other.AutumnityBlockFamilies;
import com.teamabnormals.autumnity.core.other.tags.AutumnityItemTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.autumnity.integration.boatload.AutumnityBoatTypes;
import com.teamabnormals.blueprint.core.api.conditions.BlueprintAndCondition;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import com.teamabnormals.boatload.core.data.server.BoatloadRecipeProvider;
import com.teamabnormals.woodworks.core.WoodworksConfig;
import com.teamabnormals.woodworks.core.data.server.WoodworksRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import java.util.function.Consumer;

import static com.teamabnormals.autumnity.core.registry.AutumnityBlocks.*;

public class AutumnityRecipeProvider extends BlueprintRecipeProvider {
	public static final ModLoadedCondition BERRY_GOOD_LOADED = new ModLoadedCondition("berry_good");
	public static final ConfigValueCondition FOUL_BERRIES_REQUIRE_PIPES = new ConfigValueCondition(new ResourceLocation(Autumnity.MOD_ID, "config"), AutumnityConfig.COMMON.foulBerriesRequirePips, "foul_berries_require_pips", Maps.newHashMap(), false);
	public static final BlueprintAndCondition BERRY_GOOD_AND_PIPS = new BlueprintAndCondition(BERRY_GOOD_LOADED, FOUL_BERRIES_REQUIRE_PIPES);

	public static final ModLoadedCondition ENDERGETIC_LOADED = new ModLoadedCondition("endergetic");
	public static final ModLoadedCondition INCUBATION_LOADED = new ModLoadedCondition("incubation");
	public static final ModLoadedCondition CAVERNS_AND_CHASMS_LOADED = new ModLoadedCondition("caverns_and_chasms");
	public static final NotCondition ABNORMALS_DELIGHT_NOT_LOADED = new NotCondition(new ModLoadedCondition("abnormals_delight"));

	public AutumnityRecipeProvider(PackOutput output) {
		super(Autumnity.MOD_ID, output);
	}

	@Override
	public void buildRecipes(Consumer<FinishedRecipe> consumer) {
		conversionRecipe(consumer, Items.MAGENTA_DYE, AUTUMN_CROCUS.get(), "magenta_dye");
		conditionalRecipe(consumer, BERRY_GOOD_AND_PIPS, RecipeCategory.MISC, conversionRecipeBuilder(AutumnityItems.FOUL_BERRY_PIPS.get(), AutumnityItems.FOUL_BERRIES.get(), 1));
		conditionalStorageRecipes(consumer, BERRY_GOOD_LOADED, RecipeCategory.FOOD, AutumnityItems.FOUL_BERRIES.get(), RecipeCategory.DECORATIONS, FOUL_BERRY_BASKET.get());
		conditionalRecipe(consumer, ABNORMALS_DELIGHT_NOT_LOADED, RecipeCategory.FOOD, ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, AutumnityItems.FOUL_SOUP.get()).requires(AutumnityItems.FOUL_BERRIES.get(), 2).requires(Items.SPIDER_EYE).requires(Items.BOWL, 1).unlockedBy("has_foul_berries", has(AutumnityItems.FOUL_BERRIES.get())));

		foodCookingRecipes(consumer, AutumnityItems.TURKEY_PIECE.get(), AutumnityItems.COOKED_TURKEY_PIECE.get());
		foodCookingRecipes(consumer, TURKEY.get(), COOKED_TURKEY.get());
		conditionalRecipe(consumer, ABNORMALS_DELIGHT_NOT_LOADED, RecipeCategory.FOOD, conversionRecipeBuilder(RecipeCategory.FOOD, AutumnityItems.TURKEY_PIECE.get(), TURKEY.get(), 5), getModConversionRecipeName(AutumnityItems.TURKEY_PIECE.get(), TURKEY.get()));
		conditionalRecipe(consumer, ABNORMALS_DELIGHT_NOT_LOADED, RecipeCategory.FOOD, conversionRecipeBuilder(RecipeCategory.FOOD, AutumnityItems.COOKED_TURKEY_PIECE.get(), COOKED_TURKEY.get(), 5), getModConversionRecipeName(AutumnityItems.COOKED_TURKEY_PIECE.get(), COOKED_TURKEY.get()));
		conditionalStorageRecipes(consumer, INCUBATION_LOADED, RecipeCategory.MISC, AutumnityItems.TURKEY_EGG.get(), RecipeCategory.DECORATIONS, TURKEY_EGG_CRATE.get());

		foodCookingRecipes(consumer, AutumnityItems.SAP_BOTTLE.get(), AutumnityItems.SYRUP_BOTTLE.get());
		conversionRecipe(consumer, Items.SUGAR, AutumnityItems.SAP_BOTTLE.get(), "sugar");
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SAPPY_MAPLE_LOG.get()).requires(AutumnityItems.SAP_BOTTLE.get()).requires(STRIPPED_MAPLE_LOG.get()).unlockedBy("has_sap_bottle", has(AutumnityItems.SAP_BOTTLE.get())).save(consumer, getModConversionRecipeName(SAPPY_MAPLE_LOG.get(), AutumnityItems.SAP_BOTTLE.get()));
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SAPPY_MAPLE_WOOD.get()).requires(AutumnityItems.SAP_BOTTLE.get()).requires(STRIPPED_MAPLE_WOOD.get()).unlockedBy("has_sap_bottle", has(AutumnityItems.SAP_BOTTLE.get())).save(consumer, getModConversionRecipeName(SAPPY_MAPLE_WOOD.get(), AutumnityItems.SAP_BOTTLE.get()));
		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, PANCAKE.get()).requires(AutumnityItems.SYRUP_BOTTLE.get()).requires(BlueprintItemTags.MILK).requires(Tags.Items.EGGS).requires(Items.WHEAT, 2).unlockedBy("has_syrup_bottle", has(AutumnityItems.SYRUP_BOTTLE.get())).save(consumer);
		conditionalRecipe(consumer, ABNORMALS_DELIGHT_NOT_LOADED, RecipeCategory.FOOD, ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, AutumnityItems.PUMPKIN_BREAD.get(), 2).requires(AutumnityItems.SYRUP_BOTTLE.get()).requires(BlueprintItemTags.PUMPKINS).requires(Items.WHEAT, 2).unlockedBy("has_syrup_bottle", has(AutumnityItems.SYRUP_BOTTLE.get())));

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, REDSTONE_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', Blocks.REDSTONE_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, SOUL_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', Blocks.SOUL_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_PUMPKIN_SLICE.get(), 4).define('#', Blocks.PUMPKIN).pattern("##").pattern("##").unlockedBy("has_pumpkin", has(Blocks.PUMPKIN)).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_JACK_O_LANTERN_SLICE.get()).define('A', CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(CARVED_LARGE_PUMPKIN_SLICE.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get()).define('A', CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.REDSTONE_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(CARVED_LARGE_PUMPKIN_SLICE.get())).save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_SOUL_JACK_O_LANTERN_SLICE.get()).define('A', CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.SOUL_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(CARVED_LARGE_PUMPKIN_SLICE.get())).save(consumer);

		conditionalRecipe(consumer, ENDERGETIC_LOADED, RecipeCategory.BUILDING_BLOCKS, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ENDER_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', AutumnityItemTags.TORCHES_ENDER).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)));
		conditionalRecipe(consumer, ENDERGETIC_LOADED, RecipeCategory.BUILDING_BLOCKS, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_ENDER_JACK_O_LANTERN_SLICE.get()).define('A', CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', AutumnityItemTags.TORCHES_ENDER).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(CARVED_LARGE_PUMPKIN_SLICE.get())));
		conditionalRecipe(consumer, CAVERNS_AND_CHASMS_LOADED, RecipeCategory.BUILDING_BLOCKS, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CUPRIC_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', AutumnityItemTags.TORCHES_CUPRIC).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)));
		conditionalRecipe(consumer, CAVERNS_AND_CHASMS_LOADED, RecipeCategory.BUILDING_BLOCKS, ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LARGE_CUPRIC_JACK_O_LANTERN_SLICE.get()).define('A', CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', AutumnityItemTags.TORCHES_CUPRIC).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(CARVED_LARGE_PUMPKIN_SLICE.get())));

		storageRecipes(consumer, RecipeCategory.MISC, SNAIL_GOO.get(), RecipeCategory.DECORATIONS, SNAIL_GOO_BLOCK.get());
		storageRecipes(consumer, RecipeCategory.MISC, AutumnityItems.SNAIL_SHELL_PIECE.get(), RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_BLOCK.get());
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AutumnityItems.SNAIL_SHELL_CHESTPLATE.get()).define('X', AutumnityItems.SNAIL_SHELL_PIECE.get()).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_snail_shell_piece", has(AutumnityItems.SNAIL_SHELL_PIECE.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AutumnityItems.SWIRL_BANNER_PATTERN.get()).requires(Items.PAPER).requires(AutumnityItems.SNAIL_SHELL_PIECE.get()).unlockedBy("has_snail_shell_piece", has(AutumnityItems.SNAIL_SHELL_PIECE.get())).save(consumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, AutumnityBlocks.SNAIL_SHELL_BRICKS.get(), 8).define('#', Blocks.STONE_BRICKS).define('S', AutumnityItems.SNAIL_SHELL_PIECE.get()).pattern("###").pattern("#S#").pattern("###").unlockedBy("has_snail_shell_piece", has(AutumnityItems.SNAIL_SHELL_PIECE.get())).save(consumer);
		generateRecipes(consumer, AutumnityBlockFamilies.SNAIL_SHELL_BRICKS_FAMILY);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_BRICK_SLAB.get(), SNAIL_SHELL_BRICKS.get(), 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_BRICK_STAIRS.get(), SNAIL_SHELL_BRICKS.get());
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, SNAIL_SHELL_BRICK_WALL.get(), SNAIL_SHELL_BRICKS.get());
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, CHISELED_SNAIL_SHELL_BRICKS.get(), SNAIL_SHELL_BRICKS.get());

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILES.get(), 4).define('#', SNAIL_SHELL_BRICKS.get()).pattern("##").pattern("##").unlockedBy("has_snail_shell_bricks", has(SNAIL_SHELL_BRICKS.get())).save(consumer);
		generateRecipes(consumer, AutumnityBlockFamilies.SNAIL_SHELL_TILES_FAMILY);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILE_SLAB.get(), SNAIL_SHELL_TILES.get(), 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILE_STAIRS.get(), SNAIL_SHELL_TILES.get());
		stonecutterRecipe(consumer, RecipeCategory.DECORATIONS, SNAIL_SHELL_TILE_WALL.get(), SNAIL_SHELL_TILES.get());
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILES.get(), SNAIL_SHELL_BRICKS.get());
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILE_SLAB.get(), SNAIL_SHELL_BRICKS.get(), 2);
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILE_STAIRS.get(), SNAIL_SHELL_BRICKS.get());
		stonecutterRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, SNAIL_SHELL_TILE_WALL.get(), SNAIL_SHELL_BRICKS.get());

		generateRecipes(consumer, AutumnityBlockFamilies.MAPLE_PLANKS_FAMILY);
		planksFromLogs(consumer, MAPLE_PLANKS.get(), AutumnityItemTags.MAPLE_LOGS, 4);
		woodFromLogs(consumer, MAPLE_WOOD.get(), MAPLE_LOG.get());
		woodFromLogs(consumer, STRIPPED_MAPLE_WOOD.get(), STRIPPED_MAPLE_LOG.get());
		woodFromLogs(consumer, SAPPY_MAPLE_WOOD.get(), SAPPY_MAPLE_LOG.get());
		hangingSign(consumer, MAPLE_HANGING_SIGNS.getFirst().get(), STRIPPED_MAPLE_LOG.get());
		leafPileRecipes(consumer, MAPLE_LEAVES.get(), MAPLE_LEAF_PILE.get());
		leafPileRecipes(consumer, YELLOW_MAPLE_LEAVES.get(), YELLOW_MAPLE_LEAF_PILE.get());
		leafPileRecipes(consumer, ORANGE_MAPLE_LEAVES.get(), ORANGE_MAPLE_LEAF_PILE.get());
		leafPileRecipes(consumer, RED_MAPLE_LEAVES.get(), RED_MAPLE_LEAF_PILE.get());

		BoatloadRecipeProvider.boatRecipes(consumer, AutumnityBoatTypes.MAPLE);
		WoodworksRecipeProvider.baseRecipes(consumer, MAPLE_PLANKS.get(), MAPLE_SLAB.get(), MAPLE_BOARDS.get(), MAPLE_BOOKSHELF.get(), CHISELED_MAPLE_BOOKSHELF.get(), MAPLE_LADDER.get(), MAPLE_BEEHIVE.get(), MAPLE_CHEST.get(), TRAPPED_MAPLE_CHEST.get(), Autumnity.MOD_ID);
		WoodworksRecipeProvider.sawmillRecipes(consumer, AutumnityBlockFamilies.MAPLE_PLANKS_FAMILY, AutumnityItemTags.MAPLE_LOGS, MAPLE_BOARDS.get(), MAPLE_LADDER.get(), Autumnity.MOD_ID);
	}
}