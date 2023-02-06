package com.teamabnormals.autumnity.core.data.server;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityBlockFamilies;
import com.teamabnormals.autumnity.core.other.tags.AutumnityItemTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.blueprint.core.Blueprint;
import com.teamabnormals.blueprint.core.api.conditions.QuarkFlagRecipeCondition;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.*;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class AutumnityRecipeProvider extends RecipeProvider {
	public static final ModLoadedCondition WOODWORKS_LOADED = new ModLoadedCondition("woodworks");
	public static final ModLoadedCondition BERRY_GOOD_LOADED = new ModLoadedCondition("berry_good");
	public static final ModLoadedCondition BOATLOAD_LOADED = new ModLoadedCondition("boatload");
	public static final ModLoadedCondition ENDERGETIC_LOADED = new ModLoadedCondition("endergetic");
	public static final ModLoadedCondition INCUBATION_LOADED = new ModLoadedCondition("incubation");
	public static final ModLoadedCondition CAVERNS_AND_CHASMS_LOADED = new ModLoadedCondition("caverns_and_chasms");
	public static final NotCondition ABNORMALS_DELIGHT_NOT_LOADED = new NotCondition(new ModLoadedCondition("abnormals_delight"));

	public static final OrCondition WOODEN_LADDERS = new OrCondition(WOODWORKS_LOADED, quarkFlag("variant_ladders"));
	public static final OrCondition WOODEN_BOOKSHELVES = new OrCondition(WOODWORKS_LOADED, quarkFlag("variant_bookshelves"));
	public static final OrCondition WOODEN_CHESTS = new OrCondition(WOODWORKS_LOADED, quarkFlag("variant_chests"));

	public static final QuarkFlagRecipeCondition LEAF_CARPETS = quarkFlag("leaf_carpet");
	public static final QuarkFlagRecipeCondition VERTICAL_PLANKS = quarkFlag("vertical_planks");
	public static final QuarkFlagRecipeCondition VERTICAL_SLABS = quarkFlag("vertical_slabs");
	public static final QuarkFlagRecipeCondition HEDGES = quarkFlag("hedges");
	public static final QuarkFlagRecipeCondition WOODEN_POSTS = quarkFlag("wooden_posts");
	public static final QuarkFlagRecipeCondition BERRY_SACK = quarkFlag("berry_sack");
	public static final AndCondition WOOD_TO_CHEST_RECIPES = new AndCondition(quarkFlag("variant_chests"), quarkFlag("wood_to_chest_recipes"));

	public AutumnityRecipeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		oneToOneConversionRecipe(consumer, Items.MAGENTA_DYE, AutumnityBlocks.AUTUMN_CROCUS.get(), "magenta_dye");
		conditionalRecipe(consumer, BERRY_GOOD_LOADED, oneToOneConversionRecipeBuilder(AutumnityItems.FOUL_BERRY_PIPS.get(), AutumnityItems.FOUL_BERRIES.get(), 1));
		conditionalNineBlockStorageRecipes(consumer, BERRY_SACK, AutumnityItems.FOUL_BERRIES.get(), AutumnityBlocks.FOUL_BERRY_SACK.get());
		conditionalRecipe(consumer, ABNORMALS_DELIGHT_NOT_LOADED, ShapelessRecipeBuilder.shapeless(AutumnityItems.FOUL_SOUP.get()).requires(AutumnityItems.FOUL_BERRIES.get(), 2).requires(Items.SPIDER_EYE).requires(Items.BOWL, 1).unlockedBy("has_foul_berries", has(AutumnityItems.FOUL_BERRIES.get())));

		foodCookingRecipes(consumer, AutumnityItems.TURKEY_PIECE.get(), AutumnityItems.COOKED_TURKEY_PIECE.get());
		foodCookingRecipes(consumer, AutumnityBlocks.TURKEY.get(), AutumnityBlocks.COOKED_TURKEY.get());
		conditionalRecipe(consumer, ABNORMALS_DELIGHT_NOT_LOADED, oneToOneConversionRecipeBuilder(AutumnityItems.TURKEY_PIECE.get(), AutumnityBlocks.TURKEY.get(), 5), getModConversionRecipeName(AutumnityItems.TURKEY_PIECE.get(), AutumnityBlocks.TURKEY.get()));
		conditionalRecipe(consumer, ABNORMALS_DELIGHT_NOT_LOADED, oneToOneConversionRecipeBuilder(AutumnityItems.COOKED_TURKEY_PIECE.get(), AutumnityBlocks.COOKED_TURKEY.get(), 5), getModConversionRecipeName(AutumnityItems.COOKED_TURKEY_PIECE.get(), AutumnityBlocks.COOKED_TURKEY.get()));
		conditionalNineBlockStorageRecipes(consumer, INCUBATION_LOADED, AutumnityItems.TURKEY_EGG.get(), AutumnityBlocks.TURKEY_EGG_CRATE.get());

		foodCookingRecipes(consumer, AutumnityItems.SAP_BOTTLE.get(), AutumnityItems.SYRUP_BOTTLE.get());
		oneToOneConversionRecipe(consumer, Items.SUGAR, AutumnityItems.SAP_BOTTLE.get(), "sugar");
		ShapelessRecipeBuilder.shapeless(AutumnityBlocks.SAPPY_MAPLE_LOG.get()).requires(AutumnityItems.SAP_BOTTLE.get()).requires(AutumnityBlocks.MAPLE_LOG.get()).unlockedBy("has_sap_bottle", has(AutumnityItems.SAP_BOTTLE.get())).save(consumer, getModConversionRecipeName(AutumnityBlocks.SAPPY_MAPLE_LOG.get(), AutumnityItems.SAP_BOTTLE.get()));
		ShapelessRecipeBuilder.shapeless(AutumnityBlocks.SAPPY_MAPLE_WOOD.get()).requires(AutumnityItems.SAP_BOTTLE.get()).requires(AutumnityBlocks.MAPLE_WOOD.get()).unlockedBy("has_sap_bottle", has(AutumnityItems.SAP_BOTTLE.get())).save(consumer, getModConversionRecipeName(AutumnityBlocks.SAPPY_MAPLE_WOOD.get(), AutumnityItems.SAP_BOTTLE.get()));
		ShapelessRecipeBuilder.shapeless(AutumnityBlocks.PANCAKE.get()).requires(AutumnityItems.SYRUP_BOTTLE.get()).requires(BlueprintItemTags.MILK).requires(BlueprintItemTags.EGGS).requires(Items.WHEAT, 2).unlockedBy("has_syrup_bottle", has(AutumnityItems.SYRUP_BOTTLE.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(AutumnityItems.MAPLE_LEAF_BANNER_PATTERN.get()).requires(Items.PAPER).requires(AutumnityItems.SYRUP_BOTTLE.get()).unlockedBy("has_syrup_bottle", has(AutumnityItems.SYRUP_BOTTLE.get())).save(consumer);
		conditionalRecipe(consumer, ABNORMALS_DELIGHT_NOT_LOADED, ShapelessRecipeBuilder.shapeless(AutumnityItems.PUMPKIN_BREAD.get(), 2).requires(AutumnityItems.SYRUP_BOTTLE.get()).requires(BlueprintItemTags.PUMPKINS).requires(Items.WHEAT, 2).unlockedBy("has_syrup_bottle", has(AutumnityItems.SYRUP_BOTTLE.get())));

		ShapedRecipeBuilder.shaped(AutumnityBlocks.REDSTONE_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', Blocks.REDSTONE_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityBlocks.SOUL_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', Blocks.SOUL_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityBlocks.LARGE_PUMPKIN_SLICE.get(), 4).define('#', Blocks.PUMPKIN).pattern("##").pattern("##").unlockedBy("has_pumpkin", has(Blocks.PUMPKIN)).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityBlocks.LARGE_JACK_O_LANTERN_SLICE.get()).define('A', AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get())).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityBlocks.LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get()).define('A', AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.REDSTONE_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get())).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityBlocks.LARGE_SOUL_JACK_O_LANTERN_SLICE.get()).define('A', AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.SOUL_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get())).save(consumer);

		conditionalRecipe(consumer, ENDERGETIC_LOADED, ShapedRecipeBuilder.shaped(AutumnityBlocks.ENDER_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', AutumnityItemTags.TORCHES_ENDER).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)));
		conditionalRecipe(consumer, ENDERGETIC_LOADED, ShapedRecipeBuilder.shaped(AutumnityBlocks.LARGE_ENDER_JACK_O_LANTERN_SLICE.get()).define('A', AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', AutumnityItemTags.TORCHES_ENDER).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get())));
		conditionalRecipe(consumer, CAVERNS_AND_CHASMS_LOADED, ShapedRecipeBuilder.shaped(AutumnityBlocks.CUPRIC_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', AutumnityItemTags.TORCHES_CUPRIC).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)));
		conditionalRecipe(consumer, CAVERNS_AND_CHASMS_LOADED, ShapedRecipeBuilder.shaped(AutumnityBlocks.LARGE_CUPRIC_JACK_O_LANTERN_SLICE.get()).define('A', AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', AutumnityItemTags.TORCHES_CUPRIC).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get())));

		nineBlockStorageRecipes(consumer, AutumnityBlocks.SNAIL_GOO.get(), AutumnityBlocks.SNAIL_GOO_BLOCK.get());
		nineBlockStorageRecipes(consumer, AutumnityItems.SNAIL_SHELL_PIECE.get(), AutumnityBlocks.SNAIL_SHELL_BLOCK.get());
		ShapedRecipeBuilder.shaped(AutumnityBlocks.SNAIL_SHELL_BRICKS.get(), 8).define('#', AutumnityItems.SNAIL_SHELL_PIECE.get()).define('S', Blocks.STONE_BRICKS).pattern("###").pattern("#S#").pattern("###").unlockedBy("has_snail_shell_piece", has(AutumnityItems.SNAIL_SHELL_PIECE.get())).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityBlocks.SNAIL_SHELL_TILES.get(), 8).define('#', AutumnityItems.SNAIL_SHELL_PIECE.get()).define('S', Blocks.SMOOTH_STONE).pattern("###").pattern("#S#").pattern("###").unlockedBy("has_snail_shell_piece", has(AutumnityItems.SNAIL_SHELL_PIECE.get())).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityItems.SNAIL_SHELL_CHESTPLATE.get()).define('X', AutumnityItems.SNAIL_SHELL_PIECE.get()).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_snail_shell_piece", has(AutumnityItems.SNAIL_SHELL_PIECE.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(AutumnityItems.SWIRL_BANNER_PATTERN.get()).requires(Items.PAPER).requires(AutumnityItems.SNAIL_SHELL_PIECE.get()).unlockedBy("has_snail_shell_piece", has(AutumnityItems.SNAIL_SHELL_PIECE.get())).save(consumer);

		generateRecipes(consumer, AutumnityBlockFamilies.SNAIL_SHELL_BRICKS_FAMILY);
		conditionalRecipe(consumer, VERTICAL_SLABS, verticalSlabBuilder(AutumnityBlocks.SNAIL_SHELL_BRICK_VERTICAL_SLAB.get(), Ingredient.of(AutumnityBlocks.SNAIL_SHELL_BRICKS.get())).unlockedBy(getHasName(AutumnityBlocks.SNAIL_SHELL_BRICKS.get()), has(AutumnityBlocks.SNAIL_SHELL_BRICKS.get())));
		conditionalRecipe(consumer, VERTICAL_SLABS, ShapelessRecipeBuilder.shapeless(AutumnityBlocks.SNAIL_SHELL_BRICK_VERTICAL_SLAB.get()).requires(AutumnityBlocks.SNAIL_SHELL_BRICK_SLAB.get()).unlockedBy(getHasName(AutumnityBlocks.SNAIL_SHELL_BRICK_VERTICAL_SLAB.get()), has(AutumnityBlocks.SNAIL_SHELL_BRICK_VERTICAL_SLAB.get())), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(AutumnityBlocks.SNAIL_SHELL_BRICK_VERTICAL_SLAB.get()) + "_revert"));
		stonecutterResultFromBase(consumer, AutumnityBlocks.SNAIL_SHELL_BRICK_SLAB.get(), AutumnityBlocks.SNAIL_SHELL_BRICKS.get(), 2);
		stonecutterResultFromBase(consumer, AutumnityBlocks.SNAIL_SHELL_BRICK_STAIRS.get(), AutumnityBlocks.SNAIL_SHELL_BRICKS.get());
		stonecutterResultFromBase(consumer, AutumnityBlocks.SNAIL_SHELL_BRICK_WALL.get(), AutumnityBlocks.SNAIL_SHELL_BRICKS.get());
		conditionalStonecuttingRecipe(consumer, VERTICAL_SLABS, AutumnityBlocks.SNAIL_SHELL_BRICK_VERTICAL_SLAB.get(), AutumnityBlocks.SNAIL_SHELL_BRICKS.get(), 2);
		stonecutterResultFromBase(consumer, AutumnityBlocks.CHISELED_SNAIL_SHELL_BRICKS.get(), AutumnityBlocks.SNAIL_SHELL_BRICKS.get());

		generateRecipes(consumer, AutumnityBlockFamilies.SNAIL_SHELL_TILES_FAMILY);
		conditionalRecipe(consumer, VERTICAL_SLABS, verticalSlabBuilder(AutumnityBlocks.SNAIL_SHELL_TILE_VERTICAL_SLAB.get(), Ingredient.of(AutumnityBlocks.SNAIL_SHELL_TILES.get())).unlockedBy(getHasName(AutumnityBlocks.SNAIL_SHELL_TILES.get()), has(AutumnityBlocks.SNAIL_SHELL_TILES.get())));
		conditionalRecipe(consumer, VERTICAL_SLABS, ShapelessRecipeBuilder.shapeless(AutumnityBlocks.SNAIL_SHELL_TILE_VERTICAL_SLAB.get()).requires(AutumnityBlocks.SNAIL_SHELL_TILE_SLAB.get()).unlockedBy(getHasName(AutumnityBlocks.SNAIL_SHELL_TILE_VERTICAL_SLAB.get()), has(AutumnityBlocks.SNAIL_SHELL_TILE_VERTICAL_SLAB.get())), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(AutumnityBlocks.SNAIL_SHELL_TILE_VERTICAL_SLAB.get()) + "_revert"));
		stonecutterResultFromBase(consumer, AutumnityBlocks.SNAIL_SHELL_TILE_SLAB.get(), AutumnityBlocks.SNAIL_SHELL_TILES.get(), 2);
		stonecutterResultFromBase(consumer, AutumnityBlocks.SNAIL_SHELL_TILE_STAIRS.get(), AutumnityBlocks.SNAIL_SHELL_TILES.get());
		stonecutterResultFromBase(consumer, AutumnityBlocks.SNAIL_SHELL_TILE_WALL.get(), AutumnityBlocks.SNAIL_SHELL_TILES.get());
		conditionalStonecuttingRecipe(consumer, VERTICAL_SLABS, AutumnityBlocks.SNAIL_SHELL_TILE_VERTICAL_SLAB.get(), AutumnityBlocks.SNAIL_SHELL_TILES.get(), 2);
		stonecutterResultFromBase(consumer, AutumnityBlocks.SNAIL_SHELL_TILES.get(), AutumnityBlocks.SNAIL_SHELL_BRICKS.get());
		stonecutterResultFromBase(consumer, AutumnityBlocks.SNAIL_SHELL_TILE_SLAB.get(), AutumnityBlocks.SNAIL_SHELL_BRICKS.get(), 2);
		stonecutterResultFromBase(consumer, AutumnityBlocks.SNAIL_SHELL_TILE_STAIRS.get(), AutumnityBlocks.SNAIL_SHELL_BRICKS.get());
		stonecutterResultFromBase(consumer, AutumnityBlocks.SNAIL_SHELL_TILE_WALL.get(), AutumnityBlocks.SNAIL_SHELL_BRICKS.get());
		conditionalStonecuttingRecipe(consumer, VERTICAL_SLABS, AutumnityBlocks.SNAIL_SHELL_TILE_VERTICAL_SLAB.get(), AutumnityBlocks.SNAIL_SHELL_BRICKS.get(), 2);

		generateRecipes(consumer, AutumnityBlockFamilies.MAPLE_PLANKS_FAMILY);
		planksFromLogs(consumer, AutumnityBlocks.MAPLE_PLANKS.get(), AutumnityItemTags.MAPLE_LOGS);
		woodFromLogs(consumer, AutumnityBlocks.MAPLE_WOOD.get(), AutumnityBlocks.MAPLE_LOG.get());
		woodFromLogs(consumer, AutumnityBlocks.STRIPPED_MAPLE_WOOD.get(), AutumnityBlocks.STRIPPED_MAPLE_LOG.get());
		woodFromLogs(consumer, AutumnityBlocks.SAPPY_MAPLE_WOOD.get(), AutumnityBlocks.SAPPY_MAPLE_LOG.get());
		leafPileRecipes(consumer, AutumnityBlocks.MAPLE_LEAVES.get(), AutumnityBlocks.MAPLE_LEAF_PILE.get());
		leafPileRecipes(consumer, AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE.get());
		leafPileRecipes(consumer, AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE.get());
		leafPileRecipes(consumer, AutumnityBlocks.RED_MAPLE_LEAVES.get(), AutumnityBlocks.RED_MAPLE_LEAF_PILE.get());
		conditionalRecipe(consumer, VERTICAL_SLABS, verticalSlabBuilder(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get(), Ingredient.of(AutumnityBlocks.MAPLE_PLANKS.get())).unlockedBy(getHasName(AutumnityBlocks.MAPLE_PLANKS.get()), has(AutumnityBlocks.MAPLE_PLANKS.get())));
		conditionalRecipe(consumer, VERTICAL_SLABS, ShapelessRecipeBuilder.shapeless(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get()).requires(AutumnityBlocks.MAPLE_SLAB.get()).unlockedBy(getHasName(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get()), has(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get())), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get()) + "_revert"));
		leafCarpetRecipe(consumer, AutumnityBlocks.MAPLE_LEAVES.get(), AutumnityBlocks.MAPLE_LEAF_CARPET.get(), AutumnityBlocks.MAPLE_HEDGE.get(), AutumnityItemTags.MAPLE_LOGS);
		leafCarpetRecipe(consumer, AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.YELLOW_MAPLE_HEDGE.get(), AutumnityItemTags.MAPLE_LOGS);
		leafCarpetRecipe(consumer, AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.ORANGE_MAPLE_HEDGE.get(), AutumnityItemTags.MAPLE_LOGS);
		leafCarpetRecipe(consumer, AutumnityBlocks.RED_MAPLE_LEAVES.get(), AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.RED_MAPLE_HEDGE.get(), AutumnityItemTags.MAPLE_LOGS);
		woodenBoat(consumer, AutumnityItems.MAPLE_BOAT.getFirst().get(), AutumnityBlocks.MAPLE_PLANKS.get());
		chestBoat(consumer, AutumnityItems.MAPLE_BOAT.getSecond().get(), AutumnityItems.MAPLE_BOAT.getFirst().get());
		conditionalRecipe(consumer, BOATLOAD_LOADED, ShapedRecipeBuilder.shaped(AutumnityItems.MAPLE_FURNACE_BOAT.get()).define('F', Items.FURNACE).define('B', AutumnityItems.MAPLE_BOAT.getFirst().get()).pattern("F").pattern("B").group("furnace_boat").unlockedBy(getHasName(AutumnityItems.MAPLE_BOAT.getFirst().get()), has(AutumnityItems.MAPLE_BOAT.getFirst().get())));
		conditionalRecipe(consumer, BOATLOAD_LOADED, ShapedRecipeBuilder.shaped(AutumnityItems.LARGE_MAPLE_BOAT.get()).define('#', AutumnityBlocks.MAPLE_PLANKS.get()).define('B', AutumnityItems.MAPLE_BOAT.getFirst().get()).pattern("#B#").pattern("###").group("large_boat").unlockedBy(getHasName(AutumnityItems.MAPLE_BOAT.getFirst().get()), has(AutumnityItems.MAPLE_BOAT.getFirst().get())));
		//conditionalRecipe(consumer, WOODWORKS_LOADED, ShapedRecipeBuilder.shaped(AutumnityBlocks.MAPLE_BOARDS.get(), 3).define('#', AutumnityBlocks.MAPLE_PLANKS.get()).pattern("#").pattern("#").pattern("#").group("wooden_boards").unlockedBy(getHasName(AutumnityBlocks.MAPLE_PLANKS.get()), has(AutumnityBlocks.MAPLE_PLANKS.get())));
		//conditionalRecipe(consumer, WOODEN_BOOKSHELVES, ShapedRecipeBuilder.shaped(AutumnityBlocks.MAPLE_BOOKSHELF.get()).define('#', AutumnityBlocks.MAPLE_PLANKS.get()).define('X', Items.BOOK).pattern("###").pattern("XXX").pattern("###").group("wooden_bookshelf").unlockedBy("has_book", has(Items.BOOK)));
		//conditionalRecipe(consumer, WOODEN_LADDERS, ShapedRecipeBuilder.shaped(AutumnityBlocks.MAPLE_LADDER.get(), 4).define('#', AutumnityBlocks.MAPLE_PLANKS.get()).define('S', Items.STICK).pattern("S S").pattern("S#S").pattern("S S").group("wooden_ladder").unlockedBy("has_stick", has(Items.STICK)));
		//conditionalRecipe(consumer, WOODWORKS_LOADED, ShapedRecipeBuilder.shaped(AutumnityBlocks.MAPLE_BEEHIVE.get()).define('#', AutumnityBlocks.MAPLE_PLANKS.get()).define('H', Items.HONEYCOMB).pattern("###").pattern("HHH").pattern("###").group("wooden_beehive").unlockedBy("has_honeycomb", has(Items.HONEYCOMB)));
		//conditionalRecipe(consumer, WOODEN_CHESTS, ShapedRecipeBuilder.shaped(AutumnityBlocks.MAPLE_CHEST.getFirst().get()).define('#', AutumnityBlocks.MAPLE_PLANKS.get()).pattern("###").pattern("# #").pattern("###").group("wooden_chest").unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])));
		//conditionalRecipe(consumer, WOODEN_CHESTS, ShapelessRecipeBuilder.shapeless(AutumnityBlocks.MAPLE_CHEST.getSecond().get()).requires(AutumnityBlocks.MAPLE_CHEST.getFirst().get()).requires(Blocks.TRIPWIRE_HOOK).group("wooden_trapped_chest").unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK)));
		conditionalRecipe(consumer, VERTICAL_PLANKS, ShapedRecipeBuilder.shaped(AutumnityBlocks.VERTICAL_MAPLE_PLANKS.get(), 3).define('#', AutumnityBlocks.MAPLE_PLANKS.get()).pattern("#").pattern("#").pattern("#").group("vertical_planks").unlockedBy(getHasName(AutumnityBlocks.MAPLE_PLANKS.get()), has(AutumnityBlocks.MAPLE_PLANKS.get())));
		conditionalRecipe(consumer, VERTICAL_PLANKS, ShapelessRecipeBuilder.shapeless(AutumnityBlocks.VERTICAL_MAPLE_PLANKS.get()).requires(AutumnityBlocks.MAPLE_PLANKS.get()).unlockedBy(getHasName(AutumnityBlocks.VERTICAL_MAPLE_PLANKS.get()), has(AutumnityBlocks.VERTICAL_MAPLE_PLANKS.get())), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(AutumnityBlocks.VERTICAL_MAPLE_PLANKS.get()) + "_revert"));
		conditionalRecipe(consumer, WOODEN_POSTS, ShapedRecipeBuilder.shaped(AutumnityBlocks.MAPLE_POST.get(), 8).define('#', AutumnityBlocks.MAPLE_WOOD.get()).pattern("#").pattern("#").pattern("#").group("wooden_post").unlockedBy(getHasName(AutumnityBlocks.MAPLE_WOOD.get()), has(AutumnityBlocks.MAPLE_WOOD.get())));
		conditionalRecipe(consumer, WOODEN_POSTS, ShapedRecipeBuilder.shaped(AutumnityBlocks.STRIPPED_MAPLE_POST.get(), 8).define('#', AutumnityBlocks.STRIPPED_MAPLE_WOOD.get()).pattern("#").pattern("#").pattern("#").group("wooden_post").unlockedBy(getHasName(AutumnityBlocks.STRIPPED_MAPLE_WOOD.get()), has(AutumnityBlocks.STRIPPED_MAPLE_WOOD.get())));
		conditionalRecipe(consumer, WOOD_TO_CHEST_RECIPES, ShapedRecipeBuilder.shaped(AutumnityBlocks.MAPLE_CHEST.getFirst().get(), 4).define('#', AutumnityItemTags.MAPLE_LOGS).pattern("###").pattern("# #").pattern("###").group("wooden_chest").unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(AutumnityBlocks.MAPLE_CHEST.getFirst().get()) + "_wood"));

		ShapedRecipeBuilder.shaped(Blocks.CAKE).define('A', BlueprintItemTags.BUCKETS_MILK).define('B', Items.SUGAR).define('C', Items.WHEAT).define('E', BlueprintItemTags.EGGS).pattern("AAA").pattern("BEB").pattern("CCC").unlockedBy("has_egg", has(Items.EGG)).save(consumer);
		ShapelessRecipeBuilder.shapeless(Items.PUMPKIN_PIE).requires(BlueprintItemTags.PUMPKINS).requires(Items.SUGAR).requires(BlueprintItemTags.EGGS).unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).unlockedBy("has_pumpkin", has(BlueprintItemTags.PUMPKINS)).save(consumer);
		ShapelessRecipeBuilder.shapeless(Items.PUMPKIN_SEEDS, 4).requires(BlueprintItemTags.PUMPKINS).unlockedBy("has_pumpkin", has(BlueprintItemTags.PUMPKINS)).save(consumer);
	}

	public static QuarkFlagRecipeCondition quarkFlag(String flag) {
		return new QuarkFlagRecipeCondition(new ResourceLocation(Blueprint.MOD_ID, "quark_flag"), flag);
	}

	public static RecipeBuilder verticalSlabBuilder(ItemLike item, Ingredient ingredient) {
		return ShapedRecipeBuilder.shaped(item, 6).define('#', ingredient).pattern("#").pattern("#").pattern("#");
	}

	public static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ItemLike item, ItemLike storage) {
		nineBlockStorageRecipes(consumer, item, storage, Autumnity.MOD_ID + ":" + getSimpleRecipeName(storage), null, Autumnity.MOD_ID + ":" + getSimpleRecipeName(item), null);
	}

	public static void oneToOneConversionRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, @Nullable String group) {
		oneToOneConversionRecipe(consumer, output, input, group, 1);
	}

	public static void oneToOneConversionRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, @Nullable String group, int count) {
		ShapelessRecipeBuilder.shapeless(output, count).requires(input).group(group).unlockedBy(getHasName(input), has(input)).save(consumer, getModConversionRecipeName(output, input));
	}

	public static ShapelessRecipeBuilder oneToOneConversionRecipeBuilder(ItemLike output, ItemLike input, int count) {
		return ShapelessRecipeBuilder.shapeless(output, count).requires(input).unlockedBy(getHasName(input), has(input));
	}

	public static void foodCookingRecipes(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), output, 0.35F, 200).unlockedBy(getHasName(input), has(input)).save(consumer);
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(input), output, 0.35F, 100).unlockedBy(getHasName(input), has(input)).save(consumer, RecipeBuilder.getDefaultRecipeId(output) + "_from_smoking");
		SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(input), output, 0.35F, 600).unlockedBy(getHasName(input), has(input)).save(consumer, RecipeBuilder.getDefaultRecipeId(output) + "_from_campfire_cooking");
	}

	public static void leafPileRecipes(Consumer<FinishedRecipe> consumer, ItemLike leaves, ItemLike leafPile) {
		ShapelessRecipeBuilder.shapeless(leafPile, 4).requires(leaves).group("leaf_pile").unlockedBy(getHasName(leaves), has(leaves)).save(consumer);
		ShapedRecipeBuilder.shaped(leaves).define('#', leafPile).pattern("##").pattern("##").group("leaves").unlockedBy(getHasName(leafPile), has(leafPile)).save(consumer, getModConversionRecipeName(leaves, leafPile));
	}

	public static void leafCarpetRecipe(Consumer<FinishedRecipe> consumer, ItemLike leaves, ItemLike leafCarpet, ItemLike hedge, TagKey<Item> logTag) {
		conditionalRecipe(consumer, LEAF_CARPETS, new ShapedRecipeBuilder(leafCarpet, 3).define('#', leaves).pattern("##").group("leaf_carpet").unlockedBy(getHasName(leaves), has(leaves)));
		conditionalRecipe(consumer, HEDGES, new ShapedRecipeBuilder(hedge, 2).define('#', leaves).define('L', logTag).pattern("#").pattern("L").group("hedge").unlockedBy(getHasName(leaves), has(leaves)));
	}

	public static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input) {
		stonecutterResultFromBase(consumer, output, input, 1);
	}

	public static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, int count) {
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), output, count).unlockedBy(getHasName(input), has(input)).save(consumer, getModConversionRecipeName(output, input) + "_stonecutting");
	}

	protected static ResourceLocation getModConversionRecipeName(ItemLike output, ItemLike input) {
		return new ResourceLocation(Autumnity.MOD_ID, getConversionRecipeName(output, input));
	}

	public static void conditionalNineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike item, ItemLike storage) {
		conditionalNineBlockStorageRecipes(consumer, condition, item, storage, Autumnity.MOD_ID + ":" + getSimpleRecipeName(storage), null, Autumnity.MOD_ID + ":" + getSimpleRecipeName(item), null);
	}

	protected static void conditionalNineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike item, ItemLike storage, String storageLocation, @Nullable String itemGroup, String itemLocation, @Nullable String storageGroup) {
		conditionalRecipe(consumer, condition, ShapelessRecipeBuilder.shapeless(item, 9).requires(storage).group(storageGroup).unlockedBy(getHasName(storage), has(storage)), new ResourceLocation(itemLocation));
		conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(storage).define('#', item).pattern("###").pattern("###").pattern("###").group(itemGroup).unlockedBy(getHasName(item), has(item)), new ResourceLocation(storageLocation));
	}

	public static void conditionalRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeBuilder recipe) {
		conditionalRecipe(consumer, condition, recipe, RecipeBuilder.getDefaultRecipeId(recipe.getResult()));
	}

	public static void conditionalStonecuttingRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike output, ItemLike input, int count) {
		conditionalRecipe(consumer, condition, SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), output, count).unlockedBy(getHasName(input), has(input)), new ResourceLocation(Autumnity.MOD_ID, getConversionRecipeName(output, input) + "_stonecutting"));
	}

	public static void conditionalRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeBuilder recipe, ResourceLocation id) {
		ConditionalRecipe.builder().addCondition(condition).addRecipe(consumer1 -> recipe.save(consumer1, id)).generateAdvancement(new ResourceLocation(id.getNamespace(), "recipes/" + recipe.getResult().getItemCategory().getRecipeFolderName() + "/" + id.getPath())).build(consumer, id);
	}

	private static void chestBoat(Consumer<FinishedRecipe> consumer, ItemLike chestBoat, ItemLike boat) {
		ShapelessRecipeBuilder.shapeless(chestBoat).requires(Tags.Items.CHESTS_WOODEN).requires(boat).group("chest_boat").unlockedBy("has_boat", has(ItemTags.BOATS)).save(consumer);
	}
}