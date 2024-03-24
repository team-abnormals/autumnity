package com.teamabnormals.autumnity.core.registry;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.autumnity.common.block.*;
import com.teamabnormals.autumnity.common.block.grower.MapleTreeGrower;
import com.teamabnormals.autumnity.common.block.grower.OrangeMapleTreeGrower;
import com.teamabnormals.autumnity.common.block.grower.RedMapleTreeGrower;
import com.teamabnormals.autumnity.common.block.grower.YellowMapleTreeGrower;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityConstants;
import com.teamabnormals.blueprint.common.block.BlueprintBeehiveBlock;
import com.teamabnormals.blueprint.common.block.BlueprintDirectionalBlock;
import com.teamabnormals.blueprint.common.block.LeafPileBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintChestBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintTrappedChestBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintCeilingHangingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintStandingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallHangingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallSignBlock;
import com.teamabnormals.blueprint.core.api.WoodTypeRegistryHelper;
import com.teamabnormals.blueprint.core.util.PropertyUtil;
import com.teamabnormals.blueprint.core.util.PropertyUtil.WoodSetProperties;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import static net.minecraft.world.item.CreativeModeTabs.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityBlocks {
	public static final BlockSubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getBlockSubHelper();

	public static final RegistryObject<Block> SNAIL_GOO = HELPER.createBlock("snail_goo", () -> new SnailGooBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion().noCollission().sound(SoundType.HONEY_BLOCK)));
	public static final RegistryObject<Block> SNAIL_GOO_BLOCK = HELPER.createBlock("snail_goo_block", () -> new SnailGooFullBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion().sound(SoundType.HONEY_BLOCK)));
	public static final RegistryObject<Block> PANCAKE = HELPER.createBlock("pancake", () -> new PancakeBlock(Block.Properties.of().strength(0.5F).sound(SoundType.WOOL)));
	public static final RegistryObject<Block> AUTUMN_CROCUS = HELPER.createBlock("autumn_crocus", () -> new FlowerBlock(AutumnityMobEffects.FOUL_TASTE, 16, PropertyUtil.flower()));
	public static final RegistryObject<Block> POTTED_AUTUMN_CROCUS = HELPER.createBlockNoItem("potted_autumn_crocus", () -> new FlowerPotBlock(AUTUMN_CROCUS.get(), PropertyUtil.flowerPot()));

	public static final RegistryObject<Block> TURKEY = HELPER.createBlock("turkey", () -> new TurkeyBlock(Block.Properties.of().strength(0.5F).sound(SoundType.WOOL)));
	public static final RegistryObject<Block> COOKED_TURKEY = HELPER.createBlock("cooked_turkey", () -> new CookedTurkeyBlock(Block.Properties.of().strength(0.5F).sound(SoundType.WOOL)));
	public static final RegistryObject<Block> TURKEY_EGG_CRATE = HELPER.createBlock("turkey_egg_crate", () -> new Block(Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(1.5F).sound(SoundType.WOOD)));

	public static final RegistryObject<Block> FOUL_BERRY_BUSH = HELPER.createBlockNoItem("foul_berry_bush", () -> new FoulBerryBushBlock(AutumnityProperties.FOUL_BERRIES));
	public static final RegistryObject<Block> TALL_FOUL_BERRY_BUSH = HELPER.createBlockNoItem("tall_foul_berry_bush", () -> new TallFoulBerryBushBlock(AutumnityProperties.FOUL_BERRIES));
	public static final RegistryObject<Block> POTTED_FOUL_BERRIES = HELPER.createBlockNoItem("potted_foul_berries", () -> new FlowerPotBlock(FOUL_BERRY_BUSH.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> FOUL_BERRY_BASKET = HELPER.createBlock("foul_berry_basket", () -> new BlueprintDirectionalBlock(Block.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).strength(1.5F).sound(SoundType.WOOD)));

	public static final RegistryObject<Block> SOUL_JACK_O_LANTERN = HELPER.createBlock("soul_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())));
	public static final RegistryObject<Block> REDSTONE_JACK_O_LANTERN = HELPER.createBlock("redstone_jack_o_lantern", () -> new RedstoneJackOLanternBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLightValueLit(7))));
	public static final RegistryObject<Block> ENDER_JACK_O_LANTERN = HELPER.createBlock("ender_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getMaxLightValue())));
	public static final RegistryObject<Block> CUPRIC_JACK_O_LANTERN = HELPER.createBlock("cupric_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())));

	public static final RegistryObject<Block> LARGE_PUMPKIN_SLICE = HELPER.createBlock("large_pumpkin_slice", () -> new LargePumpkinSliceBlock(Block.Properties.copy(Blocks.PUMPKIN)));
	public static final RegistryObject<Block> CARVED_LARGE_PUMPKIN_SLICE = HELPER.createBlock("carved_large_pumpkin_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.copy(Blocks.PUMPKIN)));
	public static final RegistryObject<Block> LARGE_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_jack_o_lantern_slice", () -> new LargeJackOLanternSliceBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getMaxLightValue())));
	public static final RegistryObject<Block> LARGE_SOUL_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_soul_jack_o_lantern_slice", () -> new LargeJackOLanternSliceBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())));
	public static final RegistryObject<Block> LARGE_REDSTONE_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_redstone_jack_o_lantern_slice", () -> new LargeRedstoneJackOlanternSliceBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLightValueLit(7))));
	public static final RegistryObject<Block> LARGE_ENDER_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_ender_jack_o_lantern_slice", () -> new LargeJackOLanternSliceBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getMaxLightValue())));
	public static final RegistryObject<Block> LARGE_CUPRIC_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_cupric_jack_o_lantern_slice", () -> new LargeJackOLanternSliceBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())));

	public static final RegistryObject<Block> SNAIL_SHELL_BLOCK = HELPER.createBlock("snail_shell_block", () -> new SnailShellBlock(AutumnityProperties.SNAIL_SHELL));
	public static final RegistryObject<Block> SNAIL_SHELL_BRICKS = HELPER.createBlock("snail_shell_bricks", () -> new Block(AutumnityProperties.SNAIL_SHELL));
	public static final RegistryObject<Block> SNAIL_SHELL_BRICK_STAIRS = HELPER.createBlock("snail_shell_brick_stairs", () -> new StairBlock(() -> SNAIL_SHELL_BRICKS.get().defaultBlockState(), AutumnityProperties.SNAIL_SHELL));
	public static final RegistryObject<Block> SNAIL_SHELL_BRICK_SLAB = HELPER.createBlock("snail_shell_brick_slab", () -> new SlabBlock(AutumnityProperties.SNAIL_SHELL));
	public static final RegistryObject<Block> SNAIL_SHELL_BRICK_WALL = HELPER.createBlock("snail_shell_brick_wall", () -> new WallBlock(AutumnityProperties.SNAIL_SHELL));
	public static final RegistryObject<Block> CHISELED_SNAIL_SHELL_BRICKS = HELPER.createBlock("chiseled_snail_shell_bricks", () -> new Block(AutumnityProperties.SNAIL_SHELL));
	public static final RegistryObject<Block> SNAIL_SHELL_TILES = HELPER.createBlock("snail_shell_tiles", () -> new Block(AutumnityProperties.SNAIL_SHELL));
	public static final RegistryObject<Block> SNAIL_SHELL_TILE_STAIRS = HELPER.createBlock("snail_shell_tile_stairs", () -> new StairBlock(() -> SNAIL_SHELL_BRICKS.get().defaultBlockState(), AutumnityProperties.SNAIL_SHELL));
	public static final RegistryObject<Block> SNAIL_SHELL_TILE_SLAB = HELPER.createBlock("snail_shell_tile_slab", () -> new SlabBlock(AutumnityProperties.SNAIL_SHELL));
	public static final RegistryObject<Block> SNAIL_SHELL_TILE_WALL = HELPER.createBlock("snail_shell_tile_wall", () -> new WallBlock(AutumnityProperties.SNAIL_SHELL));

	public static final RegistryObject<Block> STRIPPED_MAPLE_LOG = HELPER.createBlock("stripped_maple_log", () -> new RotatedPillarBlock(AutumnityProperties.MAPLE.log()));
	public static final RegistryObject<Block> STRIPPED_MAPLE_WOOD = HELPER.createBlock("stripped_maple_wood", () -> new RotatedPillarBlock(AutumnityProperties.MAPLE.log()));
	public static final RegistryObject<Block> SAPPY_MAPLE_LOG = HELPER.createBlock("sappy_maple_log", () -> new SappyLogBlock(STRIPPED_MAPLE_LOG, AutumnityProperties.MAPLE.log()));
	public static final RegistryObject<Block> SAPPY_MAPLE_WOOD = HELPER.createBlock("sappy_maple_wood", () -> new SappyLogBlock(STRIPPED_MAPLE_WOOD, AutumnityProperties.MAPLE.log()));
	public static final RegistryObject<Block> MAPLE_LOG = HELPER.createBlock("maple_log", () -> new MapleLogBlock(STRIPPED_MAPLE_LOG, SAPPY_MAPLE_LOG, AutumnityProperties.MAPLE.log()));
	public static final RegistryObject<Block> MAPLE_WOOD = HELPER.createBlock("maple_wood", () -> new MapleLogBlock(STRIPPED_MAPLE_WOOD, SAPPY_MAPLE_WOOD, AutumnityProperties.MAPLE.log()));
	public static final RegistryObject<Block> MAPLE_PLANKS = HELPER.createBlock("maple_planks", () -> new Block(AutumnityProperties.MAPLE.planks()));
	public static final RegistryObject<Block> MAPLE_STAIRS = HELPER.createBlock("maple_stairs", () -> new StairBlock(() -> MAPLE_PLANKS.get().defaultBlockState(), AutumnityProperties.MAPLE.planks()));
	public static final RegistryObject<Block> MAPLE_SLAB = HELPER.createBlock("maple_slab", () -> new SlabBlock(AutumnityProperties.MAPLE.planks()));
	public static final RegistryObject<Block> MAPLE_PRESSURE_PLATE = HELPER.createBlock("maple_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AutumnityProperties.MAPLE.pressurePlate(), AutumnityProperties.MAPLE_BLOCK_SET));
	public static final RegistryObject<Block> MAPLE_BUTTON = HELPER.createBlock("maple_button", () -> new ButtonBlock(AutumnityProperties.MAPLE.button(), AutumnityProperties.MAPLE_BLOCK_SET, 30, true));
	public static final RegistryObject<Block> MAPLE_FENCE = HELPER.createFuelBlock("maple_fence", () -> new FenceBlock(AutumnityProperties.MAPLE.planks()), 300);
	public static final RegistryObject<Block> MAPLE_FENCE_GATE = HELPER.createFuelBlock("maple_fence_gate", () -> new FenceGateBlock(AutumnityProperties.MAPLE.planks(), AutumnityProperties.MAPLE_WOOD_TYPE), 300);
	public static final RegistryObject<Block> MAPLE_DOOR = HELPER.createBlock("maple_door", () -> new DoorBlock(AutumnityProperties.MAPLE.door(), AutumnityProperties.MAPLE_BLOCK_SET));
	public static final RegistryObject<Block> MAPLE_TRAPDOOR = HELPER.createBlock("maple_trapdoor", () -> new TrapDoorBlock(AutumnityProperties.MAPLE.trapdoor(), AutumnityProperties.MAPLE_BLOCK_SET));
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> MAPLE_SIGNS = HELPER.createSignBlock("maple", AutumnityProperties.MAPLE_WOOD_TYPE, AutumnityProperties.MAPLE.sign());
	public static final Pair<RegistryObject<BlueprintCeilingHangingSignBlock>, RegistryObject<BlueprintWallHangingSignBlock>> MAPLE_HANGING_SIGNS = HELPER.createHangingSignBlock("maple", AutumnityProperties.MAPLE_WOOD_TYPE, AutumnityProperties.MAPLE.hangingSign());

	public static final RegistryObject<Block> MAPLE_BOARDS = HELPER.createFuelBlock("maple_boards", () -> new RotatedPillarBlock(AutumnityProperties.MAPLE.planks()), 300);
	public static final RegistryObject<Block> MAPLE_BOOKSHELF = HELPER.createFuelBlock("maple_bookshelf", () -> new Block(AutumnityProperties.MAPLE.bookshelf()), 300);
	public static final RegistryObject<Block> CHISELED_MAPLE_BOOKSHELF = HELPER.createFuelBlock("chiseled_maple_bookshelf", () -> new ChiseledMapleBookShelfBlock(AutumnityProperties.MAPLE.chiseledBookshelf()), 300);
	public static final RegistryObject<Block> MAPLE_LADDER = HELPER.createFuelBlock("maple_ladder", () -> new LadderBlock(AutumnityProperties.MAPLE.ladder()), 300);
	public static final RegistryObject<Block> MAPLE_BEEHIVE = HELPER.createBlock("maple_beehive", () -> new BlueprintBeehiveBlock(AutumnityProperties.MAPLE.beehive()));
	public static final RegistryObject<BlueprintChestBlock> MAPLE_CHEST = HELPER.createChestBlock("maple", AutumnityProperties.MAPLE.chest());
	public static final RegistryObject<BlueprintTrappedChestBlock> TRAPPED_MAPLE_CHEST = HELPER.createTrappedChestBlockNamed("maple", AutumnityProperties.MAPLE.chest());

	public static final RegistryObject<Block> MAPLE_LEAVES = HELPER.createBlock("maple_leaves", () -> new MapleLeavesBlock(AutumnityProperties.MAPLE.leaves()));
	public static final RegistryObject<Block> MAPLE_SAPLING = HELPER.createBlock("maple_sapling", () -> new SaplingBlock(new MapleTreeGrower(), AutumnityProperties.MAPLE.sapling()));
	public static final RegistryObject<Block> POTTED_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_maple_sapling", () -> new FlowerPotBlock(MAPLE_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> MAPLE_LEAF_PILE = HELPER.createBlock("maple_leaf_pile", () -> new LeafPileBlock(AutumnityProperties.MAPLE.leafPile()));

	public static final RegistryObject<Block> YELLOW_MAPLE_LEAVES = HELPER.createBlock("yellow_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.YELLOW_MAPLE.leaves(), 16766735));
	public static final RegistryObject<Block> YELLOW_MAPLE_SAPLING = HELPER.createBlock("yellow_maple_sapling", () -> new SaplingBlock(new YellowMapleTreeGrower(), AutumnityProperties.YELLOW_MAPLE.sapling()));
	public static final RegistryObject<Block> POTTED_YELLOW_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_yellow_maple_sapling", () -> new FlowerPotBlock(YELLOW_MAPLE_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> YELLOW_MAPLE_LEAF_PILE = HELPER.createBlock("yellow_maple_leaf_pile", () -> new LeafPileBlock(AutumnityProperties.YELLOW_MAPLE.leafPile()));

	public static final RegistryObject<Block> ORANGE_MAPLE_LEAVES = HELPER.createBlock("orange_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.ORANGE_MAPLE.leaves(), 16745768));
	public static final RegistryObject<Block> ORANGE_MAPLE_SAPLING = HELPER.createBlock("orange_maple_sapling", () -> new SaplingBlock(new OrangeMapleTreeGrower(), AutumnityProperties.ORANGE_MAPLE.sapling()));
	public static final RegistryObject<Block> POTTED_ORANGE_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_orange_maple_sapling", () -> new FlowerPotBlock(ORANGE_MAPLE_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> ORANGE_MAPLE_LEAF_PILE = HELPER.createBlock("orange_maple_leaf_pile", () -> new LeafPileBlock(AutumnityProperties.ORANGE_MAPLE.leafPile()));

	public static final RegistryObject<Block> RED_MAPLE_LEAVES = HELPER.createBlock("red_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.RED_MAPLE.leaves(), 12665871));
	public static final RegistryObject<Block> RED_MAPLE_SAPLING = HELPER.createBlock("red_maple_sapling", () -> new SaplingBlock(new RedMapleTreeGrower(), AutumnityProperties.RED_MAPLE.sapling()));
	public static final RegistryObject<Block> POTTED_RED_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_red_maple_sapling", () -> new FlowerPotBlock(RED_MAPLE_SAPLING.get(), PropertyUtil.flowerPot()));
	public static final RegistryObject<Block> RED_MAPLE_LEAF_PILE = HELPER.createBlock("red_maple_leaf_pile", () -> new LeafPileBlock(AutumnityProperties.RED_MAPLE.leafPile()));

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
		return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) ? of(ForgeRegistries.ITEMS.getValue(location)) : of(fallback)).test(stack);
	}

	public static Predicate<ItemStack> ofID(ResourceLocation location, String... modids) {
		return stack -> (BlockSubRegistryHelper.areModsLoaded(modids) && of(ForgeRegistries.ITEMS.getValue(location)).test(stack));
	}

	public static final class AutumnityProperties {
		public static final BlockSetType MAPLE_BLOCK_SET = BlockSetType.register(new BlockSetType(Autumnity.MOD_ID + ":maple"));
		public static final WoodType MAPLE_WOOD_TYPE = WoodTypeRegistryHelper.registerWoodType(new WoodType(Autumnity.MOD_ID + ":maple", MAPLE_BLOCK_SET));

		public static final WoodSetProperties MAPLE = WoodSetProperties.builder(MapColor.TERRACOTTA_ORANGE).build();
		public static final WoodSetProperties YELLOW_MAPLE = WoodSetProperties.builder(MapColor.TERRACOTTA_ORANGE).leavesColor(MapColor.TERRACOTTA_YELLOW).build();
		public static final WoodSetProperties ORANGE_MAPLE = WoodSetProperties.builder(MapColor.TERRACOTTA_ORANGE).leavesColor(MapColor.TERRACOTTA_ORANGE).build();
		public static final WoodSetProperties RED_MAPLE = WoodSetProperties.builder(MapColor.TERRACOTTA_ORANGE).leavesColor(MapColor.TERRACOTTA_RED).build();

		public static final Block.Properties FOUL_BERRIES = Block.Properties.of().randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH);
		public static final Block.Properties MAPLE_BRANCH = Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().randomTicks().instabreak().sound(SoundType.WOOD);
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