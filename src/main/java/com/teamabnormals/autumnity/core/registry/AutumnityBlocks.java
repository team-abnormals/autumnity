package com.teamabnormals.autumnity.core.registry;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.autumnity.common.block.*;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityConstants;
import com.teamabnormals.autumnity.core.other.AutumnityTreeGrowers;
import com.teamabnormals.blueprint.common.block.BlueprintBeehiveBlock;
import com.teamabnormals.blueprint.common.block.BlueprintDirectionalBlock;
import com.teamabnormals.blueprint.common.block.LeafPileBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintChestBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintTrappedChestBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintCeilingHangingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintStandingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallHangingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallSignBlock;
import com.teamabnormals.blueprint.core.api.BlockSetTypeRegistryHelper;
import com.teamabnormals.blueprint.core.api.WoodTypeRegistryHelper;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.blueprint.core.util.PropertyUtil.WoodSetProperties;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import static net.minecraft.world.item.CreativeModeTabs.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

public class AutumnityBlocks {
	public static final BlockSubRegistryHelper BLOCKS = Autumnity.REGISTRY_HELPER.getBlockSubHelper();

	public static final DeferredBlock<Block> SNAIL_GOO = BLOCKS.createBlock("snail_goo", () -> new SnailGooBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion().noCollission().sound(SoundType.HONEY_BLOCK).pushReaction(PushReaction.DESTROY)));
	public static final DeferredBlock<Block> SNAIL_GOO_BLOCK = BLOCKS.createBlock("snail_goo_block", () -> new SnailGooFullBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion().sound(SoundType.HONEY_BLOCK)));
	public static final DeferredBlock<Block> PANCAKE = BLOCKS.createBlock("pancake", () -> new PancakeBlock(Block.Properties.of().strength(0.5F).sound(SoundType.WOOL)));
	public static final DeferredBlock<Block> AUTUMN_CROCUS = BLOCKS.createBlock("autumn_crocus", () -> new FlowerBlock(AutumnityMobEffects.FOUL_TASTE, 16, PropertyUtil.flower()));
	public static final DeferredBlock<Block> POTTED_AUTUMN_CROCUS = BLOCKS.createBlockNoItem("potted_autumn_crocus", () -> new FlowerPotBlock(AUTUMN_CROCUS.get(), PropertyUtil.flowerPot()));

	public static final DeferredBlock<Block> TURKEY = BLOCKS.createBlock("turkey", () -> new TurkeyBlock(Block.Properties.of().strength(0.5F).sound(SoundType.WOOL)));
	public static final DeferredBlock<Block> COOKED_TURKEY = BLOCKS.createBlock("cooked_turkey", () -> new CookedTurkeyBlock(Block.Properties.of().strength(0.5F).sound(SoundType.WOOL)));
	public static final DeferredBlock<Block> TURKEY_EGG_CRATE = BLOCKS.createBlock("turkey_egg_crate", () -> new Block(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));

	public static final DeferredBlock<Block> FOUL_BERRY_BUSH = BLOCKS.createBlockNoItem("foul_berry_bush", () -> new FoulBerryBushBlock(AutumnityProperties.FOUL_BERRIES));
	public static final DeferredBlock<Block> TALL_FOUL_BERRY_BUSH = BLOCKS.createBlockNoItem("tall_foul_berry_bush", () -> new TallFoulBerryBushBlock(AutumnityProperties.FOUL_BERRIES));
	public static final DeferredBlock<Block> POTTED_FOUL_BERRIES = BLOCKS.createBlockNoItem("potted_foul_berries", () -> new FlowerPotBlock(FOUL_BERRY_BUSH.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> FOUL_BERRY_BASKET = BLOCKS.createBlock("foul_berry_basket", () -> new BlueprintDirectionalBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));

	public static final DeferredBlock<Block> SOUL_JACK_O_LANTERN = BLOCKS.createBlock("soul_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.ofFullCopy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())));
	public static final DeferredBlock<Block> REDSTONE_JACK_O_LANTERN = BLOCKS.createBlock("redstone_jack_o_lantern", () -> new RedstoneJackOLanternBlock(Block.Properties.ofFullCopy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLightValueLit(7))));
	public static final DeferredBlock<Block> ENDER_JACK_O_LANTERN = BLOCKS.createBlock("ender_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.ofFullCopy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getMaxLightValue())));
	public static final DeferredBlock<Block> CUPRIC_JACK_O_LANTERN = BLOCKS.createBlock("cupric_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.ofFullCopy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())));

	public static final DeferredBlock<Block> LARGE_PUMPKIN_SLICE = BLOCKS.createBlock("large_pumpkin_slice", () -> new LargePumpkinSliceBlock(Block.Properties.ofFullCopy(Blocks.PUMPKIN)));
	public static final DeferredBlock<Block> CARVED_LARGE_PUMPKIN_SLICE = BLOCKS.createBlock("carved_large_pumpkin_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.ofFullCopy(Blocks.PUMPKIN)));
	public static final DeferredBlock<Block> LARGE_JACK_O_LANTERN_SLICE = BLOCKS.createBlock("large_jack_o_lantern_slice", () -> new LargeJackOLanternSliceBlock(Block.Properties.ofFullCopy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getMaxLightValue())));
	public static final DeferredBlock<Block> LARGE_SOUL_JACK_O_LANTERN_SLICE = BLOCKS.createBlock("large_soul_jack_o_lantern_slice", () -> new LargeJackOLanternSliceBlock(Block.Properties.ofFullCopy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())));
	public static final DeferredBlock<Block> LARGE_REDSTONE_JACK_O_LANTERN_SLICE = BLOCKS.createBlock("large_redstone_jack_o_lantern_slice", () -> new LargeRedstoneJackOlanternSliceBlock(Block.Properties.ofFullCopy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLightValueLit(7))));
	public static final DeferredBlock<Block> LARGE_ENDER_JACK_O_LANTERN_SLICE = BLOCKS.createBlock("large_ender_jack_o_lantern_slice", () -> new LargeJackOLanternSliceBlock(Block.Properties.ofFullCopy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getMaxLightValue())));
	public static final DeferredBlock<Block> LARGE_CUPRIC_JACK_O_LANTERN_SLICE = BLOCKS.createBlock("large_cupric_jack_o_lantern_slice", () -> new LargeJackOLanternSliceBlock(Block.Properties.ofFullCopy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())));

	public static final DeferredBlock<Block> SNAIL_SHELL_BLOCK = BLOCKS.createBlock("snail_shell_block", () -> new SnailShellBlock(AutumnityProperties.SNAIL_SHELL));
	public static final DeferredBlock<Block> SNAIL_SHELL_BRICKS = BLOCKS.createBlock("snail_shell_bricks", () -> new Block(AutumnityProperties.SNAIL_SHELL));
	public static final DeferredBlock<Block> SNAIL_SHELL_BRICK_STAIRS = BLOCKS.createBlock("snail_shell_brick_stairs", () -> new StairBlock(SNAIL_SHELL_BRICKS.get().defaultBlockState(), AutumnityProperties.SNAIL_SHELL));
	public static final DeferredBlock<Block> SNAIL_SHELL_BRICK_SLAB = BLOCKS.createBlock("snail_shell_brick_slab", () -> new SlabBlock(AutumnityProperties.SNAIL_SHELL));
	public static final DeferredBlock<Block> SNAIL_SHELL_BRICK_WALL = BLOCKS.createBlock("snail_shell_brick_wall", () -> new WallBlock(AutumnityProperties.SNAIL_SHELL));
	public static final DeferredBlock<Block> CHISELED_SNAIL_SHELL_BRICKS = BLOCKS.createBlock("chiseled_snail_shell_bricks", () -> new Block(AutumnityProperties.SNAIL_SHELL));
	public static final DeferredBlock<Block> SNAIL_SHELL_TILES = BLOCKS.createBlock("snail_shell_tiles", () -> new Block(AutumnityProperties.SNAIL_SHELL));
	public static final DeferredBlock<Block> SNAIL_SHELL_TILE_STAIRS = BLOCKS.createBlock("snail_shell_tile_stairs", () -> new StairBlock(SNAIL_SHELL_BRICKS.get().defaultBlockState(), AutumnityProperties.SNAIL_SHELL));
	public static final DeferredBlock<Block> SNAIL_SHELL_TILE_SLAB = BLOCKS.createBlock("snail_shell_tile_slab", () -> new SlabBlock(AutumnityProperties.SNAIL_SHELL));
	public static final DeferredBlock<Block> SNAIL_SHELL_TILE_WALL = BLOCKS.createBlock("snail_shell_tile_wall", () -> new WallBlock(AutumnityProperties.SNAIL_SHELL));

	public static final DeferredBlock<Block> STRIPPED_MAPLE_LOG = BLOCKS.createBlock("stripped_maple_log", () -> new RotatedPillarBlock(AutumnityProperties.MAPLE.log()));
	public static final DeferredBlock<Block> STRIPPED_MAPLE_WOOD = BLOCKS.createBlock("stripped_maple_wood", () -> new RotatedPillarBlock(AutumnityProperties.MAPLE.log()));
	public static final DeferredBlock<Block> SAPPY_MAPLE_LOG = BLOCKS.createBlock("sappy_maple_log", () -> new SappyLogBlock(STRIPPED_MAPLE_LOG, AutumnityProperties.MAPLE.log()));
	public static final DeferredBlock<Block> SAPPY_MAPLE_WOOD = BLOCKS.createBlock("sappy_maple_wood", () -> new SappyLogBlock(STRIPPED_MAPLE_WOOD, AutumnityProperties.MAPLE.log()));
	public static final DeferredBlock<Block> MAPLE_LOG = BLOCKS.createBlock("maple_log", () -> new MapleLogBlock(STRIPPED_MAPLE_LOG, SAPPY_MAPLE_LOG, AutumnityProperties.MAPLE.log()));
	public static final DeferredBlock<Block> MAPLE_WOOD = BLOCKS.createBlock("maple_wood", () -> new MapleLogBlock(STRIPPED_MAPLE_WOOD, SAPPY_MAPLE_WOOD, AutumnityProperties.MAPLE.log()));
	public static final DeferredBlock<Block> MAPLE_PLANKS = BLOCKS.createBlock("maple_planks", () -> new Block(AutumnityProperties.MAPLE.planks()));
	public static final DeferredBlock<Block> MAPLE_STAIRS = BLOCKS.createBlock("maple_stairs", () -> new StairBlock(MAPLE_PLANKS.get().defaultBlockState(), AutumnityProperties.MAPLE.planks()));
	public static final DeferredBlock<Block> MAPLE_SLAB = BLOCKS.createBlock("maple_slab", () -> new SlabBlock(AutumnityProperties.MAPLE.planks()));
	public static final DeferredBlock<Block> MAPLE_PRESSURE_PLATE = BLOCKS.createBlock("maple_pressure_plate", () -> new PressurePlateBlock(AutumnityProperties.MAPLE_BLOCK_SET, AutumnityProperties.MAPLE.pressurePlate()));
	public static final DeferredBlock<Block> MAPLE_BUTTON = BLOCKS.createBlock("maple_button", () -> new ButtonBlock(AutumnityProperties.MAPLE_BLOCK_SET, 30, AutumnityProperties.MAPLE.button()));
	public static final DeferredBlock<Block> MAPLE_FENCE = BLOCKS.createBlock("maple_fence", () -> new FenceBlock(AutumnityProperties.MAPLE.planks()));
	public static final DeferredBlock<Block> MAPLE_FENCE_GATE = BLOCKS.createBlock("maple_fence_gate", () -> new FenceGateBlock(AutumnityProperties.MAPLE_WOOD_TYPE, AutumnityProperties.MAPLE.planks()));
	public static final DeferredBlock<Block> MAPLE_DOOR = BLOCKS.createBlock("maple_door", () -> new DoorBlock(AutumnityProperties.MAPLE_BLOCK_SET, AutumnityProperties.MAPLE.door()));
	public static final DeferredBlock<Block> MAPLE_TRAPDOOR = BLOCKS.createBlock("maple_trapdoor", () -> new TrapDoorBlock(AutumnityProperties.MAPLE_BLOCK_SET, AutumnityProperties.MAPLE.trapdoor()));
	public static final Pair<DeferredBlock<BlueprintStandingSignBlock>, DeferredBlock<BlueprintWallSignBlock>> MAPLE_SIGNS = BLOCKS.createSignBlock("maple", AutumnityProperties.MAPLE_WOOD_TYPE, AutumnityProperties.MAPLE.sign());
	public static final Pair<DeferredBlock<BlueprintCeilingHangingSignBlock>, DeferredBlock<BlueprintWallHangingSignBlock>> MAPLE_HANGING_SIGNS = BLOCKS.createHangingSignBlock("maple", AutumnityProperties.MAPLE_WOOD_TYPE, AutumnityProperties.MAPLE.hangingSign());

	public static final DeferredBlock<Block> MAPLE_BOARDS = BLOCKS.createBlock("maple_boards", () -> new RotatedPillarBlock(AutumnityProperties.MAPLE.planks()));
	public static final DeferredBlock<Block> MAPLE_BOOKSHELF = BLOCKS.createBlock("maple_bookshelf", () -> new Block(AutumnityProperties.MAPLE.bookshelf()));
	public static final DeferredBlock<Block> CHISELED_MAPLE_BOOKSHELF = BLOCKS.createBlock("chiseled_maple_bookshelf", () -> new ChiseledMapleBookShelfBlock(AutumnityProperties.MAPLE.chiseledBookshelf()));
	public static final DeferredBlock<Block> MAPLE_LADDER = BLOCKS.createBlock("maple_ladder", () -> new LadderBlock(AutumnityProperties.MAPLE.ladder()));
	public static final DeferredBlock<Block> MAPLE_BEEHIVE = BLOCKS.createBlock("maple_beehive", () -> new BlueprintBeehiveBlock(AutumnityProperties.MAPLE.beehive()));
	public static final DeferredBlock<BlueprintChestBlock> MAPLE_CHEST = BLOCKS.createChestBlock("maple", AutumnityProperties.MAPLE.chest());
	public static final DeferredBlock<BlueprintTrappedChestBlock> TRAPPED_MAPLE_CHEST = BLOCKS.createTrappedChestBlock("maple", AutumnityProperties.MAPLE.chest());

	public static final DeferredBlock<Block> MAPLE_LEAVES = BLOCKS.createBlock("maple_leaves", () -> new MapleLeavesBlock(AutumnityProperties.MAPLE.leaves()));
	public static final DeferredBlock<Block> MAPLE_SAPLING = BLOCKS.createBlock("maple_sapling", () -> new SaplingBlock(AutumnityTreeGrowers.MAPLE, AutumnityProperties.MAPLE.sapling()));
	public static final DeferredBlock<Block> POTTED_MAPLE_SAPLING = BLOCKS.createBlockNoItem("potted_maple_sapling", () -> new FlowerPotBlock(MAPLE_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> MAPLE_LEAF_PILE = BLOCKS.createBlock("maple_leaf_pile", () -> new LeafPileBlock(AutumnityProperties.MAPLE.leafPile()));

	public static final DeferredBlock<Block> YELLOW_MAPLE_LEAVES = BLOCKS.createBlock("yellow_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.YELLOW_MAPLE.leaves(), 16766735));
	public static final DeferredBlock<Block> YELLOW_MAPLE_SAPLING = BLOCKS.createBlock("yellow_maple_sapling", () -> new SaplingBlock(AutumnityTreeGrowers.MAPLE_YELLOW, AutumnityProperties.YELLOW_MAPLE.sapling()));
	public static final DeferredBlock<Block> POTTED_YELLOW_MAPLE_SAPLING = BLOCKS.createBlockNoItem("potted_yellow_maple_sapling", () -> new FlowerPotBlock(YELLOW_MAPLE_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> YELLOW_MAPLE_LEAF_PILE = BLOCKS.createBlock("yellow_maple_leaf_pile", () -> new LeafPileBlock(AutumnityProperties.YELLOW_MAPLE.leafPile()));

	public static final DeferredBlock<Block> ORANGE_MAPLE_LEAVES = BLOCKS.createBlock("orange_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.ORANGE_MAPLE.leaves(), 16745768));
	public static final DeferredBlock<Block> ORANGE_MAPLE_SAPLING = BLOCKS.createBlock("orange_maple_sapling", () -> new SaplingBlock(AutumnityTreeGrowers.MAPLE_ORANGE, AutumnityProperties.ORANGE_MAPLE.sapling()));
	public static final DeferredBlock<Block> POTTED_ORANGE_MAPLE_SAPLING = BLOCKS.createBlockNoItem("potted_orange_maple_sapling", () -> new FlowerPotBlock(ORANGE_MAPLE_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> ORANGE_MAPLE_LEAF_PILE = BLOCKS.createBlock("orange_maple_leaf_pile", () -> new LeafPileBlock(AutumnityProperties.ORANGE_MAPLE.leafPile()));

	public static final DeferredBlock<Block> RED_MAPLE_LEAVES = BLOCKS.createBlock("red_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.RED_MAPLE.leaves(), 12665871));
	public static final DeferredBlock<Block> RED_MAPLE_SAPLING = BLOCKS.createBlock("red_maple_sapling", () -> new SaplingBlock(AutumnityTreeGrowers.MAPLE_RED, AutumnityProperties.RED_MAPLE.sapling()));
	public static final DeferredBlock<Block> POTTED_RED_MAPLE_SAPLING = BLOCKS.createBlockNoItem("potted_red_maple_sapling", () -> new FlowerPotBlock(RED_MAPLE_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final DeferredBlock<Block> RED_MAPLE_LEAF_PILE = BLOCKS.createBlock("red_maple_leaf_pile", () -> new LeafPileBlock(AutumnityProperties.RED_MAPLE.leafPile()));

	public static void setupTabEditors() {
		CreativeModeTabContentsPopulator.mod(Autumnity.MOD_ID)
				.tab(FOOD_AND_DRINKS)
				.addItemsBefore(of(Items.CAKE), PANCAKE)
				.tab(BUILDING_BLOCKS)
				.addItemsBefore(of(Blocks.BAMBOO_BLOCK), MAPLE_LOG, MAPLE_WOOD, STRIPPED_MAPLE_LOG, STRIPPED_MAPLE_WOOD, SAPPY_MAPLE_LOG, SAPPY_MAPLE_WOOD, MAPLE_PLANKS)
				.addItemsBefore(modLoaded(Blocks.BAMBOO_BLOCK, "woodworks"), MAPLE_BOARDS)
				.addItemsBefore(of(Blocks.BAMBOO_BLOCK), MAPLE_STAIRS, MAPLE_SLAB, MAPLE_FENCE, MAPLE_FENCE_GATE, MAPLE_DOOR, MAPLE_TRAPDOOR, MAPLE_PRESSURE_PLATE, MAPLE_BUTTON)
				.addItemsBefore(of(Blocks.SEA_LANTERN), SNAIL_SHELL_BLOCK,
						SNAIL_SHELL_BRICKS, SNAIL_SHELL_BRICK_STAIRS, SNAIL_SHELL_BRICK_SLAB, SNAIL_SHELL_BRICK_WALL, CHISELED_SNAIL_SHELL_BRICKS,
						SNAIL_SHELL_TILES, SNAIL_SHELL_TILE_STAIRS, SNAIL_SHELL_TILE_SLAB, SNAIL_SHELL_TILE_WALL
				)
				.tab(NATURAL_BLOCKS)
				.addItemsBefore(of(Blocks.MUSHROOM_STEM), MAPLE_LOG)
				.addItemsBefore(of(Blocks.AZALEA_LEAVES), MAPLE_LEAVES, MAPLE_LEAF_PILE, YELLOW_MAPLE_LEAVES, YELLOW_MAPLE_LEAF_PILE, ORANGE_MAPLE_LEAVES, ORANGE_MAPLE_LEAF_PILE, RED_MAPLE_LEAVES, RED_MAPLE_LEAF_PILE)
				.addItemsBefore(of(Blocks.AZALEA), MAPLE_SAPLING, YELLOW_MAPLE_SAPLING, ORANGE_MAPLE_SAPLING, RED_MAPLE_SAPLING)
				.addItemsAfter(of(Blocks.HONEY_BLOCK), SNAIL_GOO_BLOCK)
				.addItemsAfter(of(Blocks.LILY_OF_THE_VALLEY), AUTUMN_CROCUS)
				.addItemsAfter(of(Blocks.PUMPKIN), LARGE_PUMPKIN_SLICE)
				.addItemsAfter(of(Blocks.CARVED_PUMPKIN), CARVED_LARGE_PUMPKIN_SLICE)
				.addItemsAfter(of(Blocks.JACK_O_LANTERN), REDSTONE_JACK_O_LANTERN, LARGE_REDSTONE_JACK_O_LANTERN_SLICE)
				.addItemsAfter(modLoaded(Blocks.JACK_O_LANTERN, "caverns_and_chasms"), CUPRIC_JACK_O_LANTERN, LARGE_CUPRIC_JACK_O_LANTERN_SLICE)
				.addItemsAfter(modLoaded(Blocks.JACK_O_LANTERN, "endergetic"), ENDER_JACK_O_LANTERN, LARGE_ENDER_JACK_O_LANTERN_SLICE)
				.addItemsAfter(of(Blocks.JACK_O_LANTERN), LARGE_JACK_O_LANTERN_SLICE, SOUL_JACK_O_LANTERN, LARGE_SOUL_JACK_O_LANTERN_SLICE)
				.tab(FUNCTIONAL_BLOCKS)
				.addItemsBefore(of(Blocks.BAMBOO_SIGN), MAPLE_SIGNS.getFirst(), MAPLE_HANGING_SIGNS.getFirst())
				.tab(REDSTONE_BLOCKS)
				.addItemsAfter(of(Blocks.SLIME_BLOCK), SNAIL_GOO_BLOCK)
				.tab(INGREDIENTS)
				.addItemsAfter(of(Items.SLIME_BALL), SNAIL_GOO);

		CreativeModeTabContentsPopulator.mod("berry_good_1")
				.tab(NATURAL_BLOCKS)
				.addItemsAfter(ofID(AutumnityConstants.SWEET_BERRY_BASKET), FOUL_BERRY_BASKET);

		CreativeModeTabContentsPopulator.mod("incubation_1")
				.tab(NATURAL_BLOCKS)
				.addItemsAfter(ofID(AutumnityConstants.CHICKEN_EGG_CRATE), TURKEY_EGG_CRATE);

		CreativeModeTabContentsPopulator.mod("woodworks_1")
				.tab(FUNCTIONAL_BLOCKS)
				.addItemsBefore(ofID(AutumnityConstants.BAMBOO_LADDER), MAPLE_LADDER)
				.addItemsBefore(ofID(AutumnityConstants.BAMBOO_BEEHIVE), MAPLE_BEEHIVE)
				.addItemsBefore(ofID(AutumnityConstants.BAMBOO_BOOKSHELF), MAPLE_BOOKSHELF, CHISELED_MAPLE_BOOKSHELF)
				.addItemsBefore(ofID(AutumnityConstants.BAMBOO_CLOSET), MAPLE_CHEST)
				.tab(REDSTONE_BLOCKS)
				.addItemsBefore(ofID(AutumnityConstants.TRAPPED_BAMBOO_CLOSET), TRAPPED_MAPLE_CHEST);
	}

	public static Predicate<ItemStack> modLoaded(ItemLike item, String... modids) {
		return stack -> of(item).test(stack) && BlockSubRegistryHelper.areModsLoaded(modids);
	}

	public static Predicate<ItemStack> ofID(ResourceLocation location, ItemLike fallback, String... modids) {
		return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) ? of(BuiltInRegistries.ITEM.get(location)) : of(fallback)).test(stack);
	}

	public static Predicate<ItemStack> ofID(ResourceLocation location, String... modids) {
		return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) && of(BuiltInRegistries.ITEM.get(location)).test(stack));
	}

	public static final class AutumnityProperties {
		public static final BlockSetType MAPLE_BLOCK_SET = BlockSetTypeRegistryHelper.register(new BlockSetType(Autumnity.MOD_ID + ":maple"));
		public static final WoodType MAPLE_WOOD_TYPE = WoodTypeRegistryHelper.registerWoodType(new WoodType(Autumnity.MOD_ID + ":maple", MAPLE_BLOCK_SET));

		public static final WoodSetProperties MAPLE = WoodSetProperties.builder(MapColor.TERRACOTTA_ORANGE).build();
		public static final WoodSetProperties YELLOW_MAPLE = WoodSetProperties.builder(MapColor.TERRACOTTA_ORANGE).leavesColor(MapColor.TERRACOTTA_YELLOW).build();
		public static final WoodSetProperties ORANGE_MAPLE = WoodSetProperties.builder(MapColor.TERRACOTTA_ORANGE).leavesColor(MapColor.TERRACOTTA_ORANGE).build();
		public static final WoodSetProperties RED_MAPLE = WoodSetProperties.builder(MapColor.TERRACOTTA_ORANGE).leavesColor(MapColor.TERRACOTTA_RED).build();

		public static final Block.Properties FOUL_BERRIES = Block.Properties.of().randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY);
		public static final Block.Properties SNAIL_SHELL = Block.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().strength(3.0F, 9.0F);

		public static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
			return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
		}

		public static ToIntFunction<BlockState> getMaxLightValue() {
			return (state) -> 15;
		}

		public static ToIntFunction<BlockState> getLowerLightValue() {
			return (state) -> 10;
		}
	}
}