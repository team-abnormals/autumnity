package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityConstants;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBlockTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.blueprint.core.data.server.tags.BlueprintItemTagsProvider;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.autumnity.core.other.tags.AutumnityItemTags.*;
import static com.teamabnormals.autumnity.core.registry.AutumnityItems.*;


public class AutumnityItemTagsProvider extends BlueprintItemTagsProvider {

	public AutumnityItemTagsProvider(PackOutput output, CompletableFuture<Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> lookup, ExistingFileHelper helper) {
		super(Autumnity.MOD_ID, output, provider, lookup, helper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addTags(Provider provider) {
		this.copyWoodsetTags();

		this.copy(BlockTags.SLABS, ItemTags.SLABS);
		this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
		this.copy(BlockTags.WALLS, ItemTags.WALLS);
		this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
		this.copy(Tags.Blocks.STRIPPED_LOGS, Tags.Items.STRIPPED_LOGS);
		this.copy(Tags.Blocks.STRIPPED_WOODS, Tags.Items.STRIPPED_WOODS);

		this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
		this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
		this.tag(ItemTags.PIGLIN_REPELLENTS).add(AutumnityBlocks.SOUL_JACK_O_LANTERN.asItem(), AutumnityBlocks.LARGE_SOUL_JACK_O_LANTERN_SLICE.asItem());
		this.tag(ItemTags.BOATS).add(MAPLE_BOAT.getFirst().get());
		this.tag(ItemTags.CHEST_BOATS).add(MAPLE_BOAT.getSecond().get());
		this.tag(BlueprintItemTags.FURNACE_BOATS).add(MAPLE_FURNACE_BOAT.get());
		this.tag(BlueprintItemTags.LARGE_BOATS).add(LARGE_MAPLE_BOAT.get());
		this.tag(ItemTags.FOX_FOOD).add(FOUL_BERRIES.get());
		this.tag(ItemTags.CHICKEN_FOOD).add(FOUL_BERRY_PIPS.get());
		this.tag(ItemTags.TRIMMABLE_ARMOR).add(SNAIL_SHELL_CHESTPLATE.get());
		this.tag(ItemTags.CHEST_ARMOR).add(SNAIL_SHELL_CHESTPLATE.get());

		this.copy(AutumnityBlockTags.MAPLE_LOGS, MAPLE_LOGS);
		this.tag(SNAIL_FOOD).add(Items.MUSHROOM_STEW, Items.SUSPICIOUS_STEW);
		this.tag(SNAIL_SNACKS).add(Items.RED_MUSHROOM, Items.BROWN_MUSHROOM).addTag(SNAIL_SPEED_SNACKS).addTag(SNAIL_GLOW_SNACKS);
		this.tag(SNAIL_SPEED_SNACKS).add(Items.CRIMSON_FUNGUS, Items.WARPED_FUNGUS);
		this.tag(SNAIL_GLOW_SNACKS);
		this.tag(TURKEY_FOOD).add(FOUL_BERRIES.get());
		this.tag(SNAIL_TEMPT_ITEMS).add(Items.WARPED_FUNGUS_ON_A_STICK).addTag(SNAIL_FOOD).addTag(SNAIL_SNACKS);
		this.tag(KNIVES);
		this.tag(TORCHES_ENDER).addOptional(AutumnityConstants.ENDER_TORCH);
		this.tag(TORCHES_CUPRIC).addOptional(AutumnityConstants.CUPRIC_TORCH);

		this.tag(ItemTags.MEAT).add(AutumnityBlocks.TURKEY.asItem(), TURKEY_PIECE.get(), AutumnityBlocks.COOKED_TURKEY.asItem(), COOKED_TURKEY_PIECE.get());

		this.tag(Tags.Items.FOODS).addTag(FOODS_PASTRY).add(SYRUP_BOTTLE.get());
		this.tag(Tags.Items.FOODS_COOKED_MEAT).addTag(FOODS_COOKED_TURKEY);
		this.tag(Tags.Items.FOODS_RAW_MEAT).addTag(FOODS_RAW_TURKEY);
		this.tag(Tags.Items.FOODS_EDIBLE_WHEN_PLACED).add(AutumnityBlocks.TURKEY.asItem(), AutumnityBlocks.COOKED_TURKEY.asItem(), AutumnityBlocks.PANCAKE.asItem());
		this.tag(Tags.Items.FOODS_FOOD_POISONING).add(AutumnityBlocks.TURKEY.asItem(), TURKEY_PIECE.get());
		this.tag(Tags.Items.FOODS_SOUP).add(FOUL_SOUP.get());
		this.tag(Tags.Items.ANIMAL_FOODS).addTags(SNAIL_FOOD, TURKEY_FOOD);
		this.tag(FOODS_PASTRY).add(PUMPKIN_BREAD.get());

		this.tag(Tags.Items.DRINKS).addTags(DRINKS_SYRUP);
		this.tag(DRINKS_SYRUP).add(SYRUP_BOTTLE.get());

		this.tag(FOODS_COOKED_TURKEY).add(AutumnityBlocks.COOKED_TURKEY.asItem(), COOKED_TURKEY_PIECE.get());
		this.tag(FOODS_RAW_TURKEY).add(AutumnityBlocks.TURKEY.asItem(), TURKEY_PIECE.get());

		this.tag(SEEDS_FOUL_BERRY).add(FOUL_BERRY_PIPS.get());
		this.tag(Tags.Items.SEEDS).addTag(SEEDS_FOUL_BERRY);
		this.tag(Tags.Items.EGGS).add(TURKEY_EGG.get());

		this.copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
		this.copy(Tags.Blocks.PUMPKINS_NORMAL, Tags.Items.PUMPKINS_NORMAL);
		this.copy(Tags.Blocks.PUMPKINS_CARVED, Tags.Items.PUMPKINS_CARVED);
		this.copy(Tags.Blocks.PUMPKINS_JACK_O_LANTERNS, Tags.Items.PUMPKINS_JACK_O_LANTERNS);
	}
}