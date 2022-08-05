package com.teamabnormals.autumnity.core.data.server;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityBlockFamilies;
import com.teamabnormals.autumnity.core.other.tags.AutumnityItemTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.blueprint.core.Blueprint;
import com.teamabnormals.blueprint.core.api.conditions.QuarkFlagRecipeCondition;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class AutumnityRecipeProvider extends RecipeProvider {
	public static final QuarkFlagRecipeCondition VERTICAL_SLABS = new QuarkFlagRecipeCondition(new ResourceLocation(Blueprint.MOD_ID, "quark_flag"), "vertical_slabs");

	public AutumnityRecipeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		oneToOneConversionRecipe(consumer, Items.MAGENTA_DYE, AutumnityBlocks.AUTUMN_CROCUS.get(), "magenta_dye");

		foodCookingRecipes(consumer, AutumnityItems.TURKEY_PIECE.get(), AutumnityItems.COOKED_TURKEY_PIECE.get());
		foodCookingRecipes(consumer, AutumnityBlocks.TURKEY.get(), AutumnityBlocks.COOKED_TURKEY.get());

		foodCookingRecipes(consumer, AutumnityItems.SAP_BOTTLE.get(), AutumnityItems.SYRUP_BOTTLE.get());
		oneToOneConversionRecipe(consumer, Items.SUGAR, AutumnityItems.SAP_BOTTLE.get(), "sugar");
		ShapelessRecipeBuilder.shapeless(AutumnityBlocks.SAPPY_MAPLE_LOG.get()).requires(AutumnityItems.SAP_BOTTLE.get()).requires(AutumnityBlocks.MAPLE_LOG.get()).unlockedBy("has_sap_bottle", has(AutumnityItems.SAP_BOTTLE.get())).save(consumer, getModConversionRecipeName(AutumnityBlocks.SAPPY_MAPLE_LOG.get(), AutumnityItems.SAP_BOTTLE.get()));
		ShapelessRecipeBuilder.shapeless(AutumnityBlocks.SAPPY_MAPLE_WOOD.get()).requires(AutumnityItems.SAP_BOTTLE.get()).requires(AutumnityBlocks.MAPLE_WOOD.get()).unlockedBy("has_sap_bottle", has(AutumnityItems.SAP_BOTTLE.get())).save(consumer, getModConversionRecipeName(AutumnityBlocks.SAPPY_MAPLE_WOOD.get(), AutumnityItems.SAP_BOTTLE.get()));
		ShapelessRecipeBuilder.shapeless(AutumnityBlocks.PANCAKE.get()).requires(AutumnityItems.SYRUP_BOTTLE.get()).requires(BlueprintItemTags.MILK).requires(BlueprintItemTags.EGGS).requires(Items.WHEAT, 2).unlockedBy("has_syrup_bottle", has(AutumnityItems.SYRUP_BOTTLE.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(AutumnityItems.MAPLE_LEAF_BANNER_PATTERN.get()).requires(Items.PAPER).requires(AutumnityItems.SYRUP_BOTTLE.get()).unlockedBy("has_syrup_bottle", has(AutumnityItems.SYRUP_BOTTLE.get())).save(consumer);

		ShapedRecipeBuilder.shaped(AutumnityBlocks.REDSTONE_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', Blocks.REDSTONE_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityBlocks.SOUL_JACK_O_LANTERN.get()).define('A', Blocks.CARVED_PUMPKIN).define('B', Blocks.SOUL_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN)).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityBlocks.LARGE_PUMPKIN_SLICE.get(), 4).define('#', Blocks.PUMPKIN).pattern("##").pattern("##").unlockedBy("has_pumpkin", has(Blocks.PUMPKIN)).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityBlocks.LARGE_JACK_O_LANTERN_SLICE.get()).define('A', AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get())).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityBlocks.LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get()).define('A', AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.REDSTONE_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get())).save(consumer);
		ShapedRecipeBuilder.shaped(AutumnityBlocks.LARGE_SOUL_JACK_O_LANTERN_SLICE.get()).define('A', AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get()).define('B', Blocks.SOUL_TORCH).pattern("A").pattern("B").unlockedBy("has_carved_large_pumpkin_slice", has(AutumnityBlocks.CARVED_LARGE_PUMPKIN_SLICE.get())).save(consumer);

		nineBlockStorageRecipes(consumer, AutumnityBlocks.SNAIL_SLIME.get(), AutumnityBlocks.SNAIL_SLIME_BLOCK.get());
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
		conditionalRecipe(consumer, VERTICAL_SLABS, verticalSlabBuilder(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get(), Ingredient.of(AutumnityBlocks.MAPLE_PLANKS.get())).unlockedBy(getHasName(AutumnityBlocks.MAPLE_PLANKS.get()), has(AutumnityBlocks.MAPLE_PLANKS.get())));
		conditionalRecipe(consumer, VERTICAL_SLABS, ShapelessRecipeBuilder.shapeless(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get()).requires(AutumnityBlocks.MAPLE_SLAB.get()).unlockedBy(getHasName(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get()), has(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get())), new ResourceLocation(RecipeBuilder.getDefaultRecipeId(AutumnityBlocks.MAPLE_VERTICAL_SLAB.get()) + "_revert"));
		woodenBoat(consumer, AutumnityItems.MAPLE_BOAT.get(), AutumnityBlocks.MAPLE_PLANKS.get());
	}

	protected static RecipeBuilder verticalSlabBuilder(ItemLike item, Ingredient ingredient) {
		return ShapedRecipeBuilder.shaped(item, 6).define('#', ingredient).pattern("#").pattern("#").pattern("#");
	}

	protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, ItemLike item, ItemLike storage) {
		nineBlockStorageRecipes(consumer, item, storage, Autumnity.MOD_ID + ":" + getSimpleRecipeName(storage), null, Autumnity.MOD_ID + ":" + getSimpleRecipeName(item), null);
	}

	protected static void oneToOneConversionRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, @Nullable String group) {
		oneToOneConversionRecipe(consumer, output, input, group, 1);
	}

	protected static void oneToOneConversionRecipe(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, @Nullable String group, int count) {
		ShapelessRecipeBuilder.shapeless(output, count).requires(input).group(group).unlockedBy(getHasName(input), has(input)).save(consumer, getModConversionRecipeName(output, input));
	}

	public static void foodCookingRecipes(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), output, 0.35F, 200).unlockedBy(getHasName(input), has(input)).save(consumer);
		SimpleCookingRecipeBuilder.smoking(Ingredient.of(input), output, 0.35F, 100).unlockedBy(getHasName(input), has(input)).save(consumer, RecipeBuilder.getDefaultRecipeId(output) + "_from_smoking");
		SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(input), output, 0.35F, 600).unlockedBy(getHasName(input), has(input)).save(consumer, RecipeBuilder.getDefaultRecipeId(output) + "_from_campfire_cooking");
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


	public static void conditionalRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeBuilder recipe) {
		conditionalRecipe(consumer, condition, recipe, RecipeBuilder.getDefaultRecipeId(recipe.getResult()));
	}

	public static void conditionalStonecuttingRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike output, ItemLike input, int count) {
		conditionalRecipe(consumer, condition, SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), output, count).unlockedBy(getHasName(input), has(input)), new ResourceLocation(Autumnity.MOD_ID, getConversionRecipeName(output, input) + "_stonecutting"));
	}

	public static void conditionalRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeBuilder recipe, ResourceLocation id) {
		ConditionalRecipe.builder().addCondition(condition).addRecipe(consumer1 -> recipe.save(consumer1, id)).generateAdvancement(new ResourceLocation(id.getNamespace(), "recipes/" + recipe.getResult().getItemCategory().getRecipeFolderName() + "/" + id.getPath())).build(consumer, id);
	}
}