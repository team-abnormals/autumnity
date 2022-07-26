package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.common.block.*;
import com.teamabnormals.autumnity.common.block.trees.MapleTree;
import com.teamabnormals.autumnity.common.block.trees.OrangeMapleTree;
import com.teamabnormals.autumnity.common.block.trees.RedMapleTree;
import com.teamabnormals.autumnity.common.block.trees.YellowMapleTree;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityProperties;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.block.*;
import com.teamabnormals.blueprint.common.block.chest.BlueprintChestBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintTrappedChestBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintStandingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallSignBlock;
import com.teamabnormals.blueprint.common.block.wood.*;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityBlocks {
	public static final BlockSubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getBlockSubHelper();

	public static final RegistryObject<Block> SNAIL_SLIME = HELPER.createBlock("snail_slime", () -> new PlaceableSlimeBlock(Block.Properties.of(Material.DECORATION, MaterialColor.TERRACOTTA_WHITE).noOcclusion().noCollission().sound(SoundType.HONEY_BLOCK)), CreativeModeTab.TAB_MISC);
	public static final RegistryObject<Block> SNAIL_SLIME_BLOCK = HELPER.createBlock("snail_slime_block", () -> new SnailSlimeBlock(Block.Properties.of(Material.CLAY, MaterialColor.TERRACOTTA_WHITE).noOcclusion().sound(SoundType.HONEY_BLOCK)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> PANCAKE = HELPER.createBlock("pancake", () -> new PancakeBlock(Block.Properties.of(Material.CAKE).strength(0.5F).sound(SoundType.WOOL)), CreativeModeTab.TAB_FOOD);
	public static final RegistryObject<Block> FOUL_BERRY_BUSH_PIPS = HELPER.createBlockNoItem("foul_berry_bush_pips", () -> new FoulBerryBushPipsBlock(AutumnityProperties.FOUL_BERRIES));
	public static final RegistryObject<Block> FOUL_BERRY_BUSH = HELPER.createBlockNoItem("foul_berry_bush", () -> new FoulBerryBushBlock(AutumnityProperties.FOUL_BERRIES));
	public static final RegistryObject<Block> TALL_FOUL_BERRY_BUSH = HELPER.createBlockNoItem("tall_foul_berry_bush", () -> new TallFoulBerryBushBlock(AutumnityProperties.FOUL_BERRIES));
	public static final RegistryObject<Block> POTTED_FOUL_BERRIES = HELPER.createBlockNoItem("potted_foul_berries", () -> new FlowerPotBlock(FOUL_BERRY_BUSH.get(), Block.Properties.copy(Blocks.POTTED_ALLIUM)));
	public static final RegistryObject<Block> AUTUMN_CROCUS = HELPER.createBlock("autumn_crocus", () -> new BlueprintFlowerBlock(AutumnityMobEffects.FOUL_TASTE, 16, Block.Properties.of(Material.PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> POTTED_AUTUMN_CROCUS = HELPER.createBlockNoItem("potted_autumn_crocus", () -> new FlowerPotBlock(AUTUMN_CROCUS.get(), Block.Properties.copy(Blocks.POTTED_ALLIUM)));
	public static final RegistryObject<Block> TURKEY = HELPER.createBlock("turkey", () -> new TurkeyBlock(Block.Properties.of(Material.CAKE).strength(0.5F).sound(SoundType.WOOL)), CreativeModeTab.TAB_FOOD);
	public static final RegistryObject<Block> COOKED_TURKEY = HELPER.createBlock("cooked_turkey", () -> new CookedTurkeyBlock(Block.Properties.of(Material.CAKE).strength(0.5F).sound(SoundType.WOOL)), CreativeModeTab.TAB_FOOD);

	// Pumpkins
	public static final RegistryObject<Block> LARGE_PUMPKIN_SLICE = HELPER.createBlock("large_pumpkin_slice", () -> new LargePumpkinSliceBlock(Block.Properties.copy(Blocks.PUMPKIN)), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CARVED_LARGE_PUMPKIN_SLICE = HELPER.createBlock("carved_large_pumpkin_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.copy(Blocks.PUMPKIN)), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SOUL_JACK_O_LANTERN = HELPER.createBlock("soul_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> REDSTONE_JACK_O_LANTERN = HELPER.createBlock("redstone_jack_o_lantern", () -> new RedstoneJackOLanternBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLightValueLit(7))), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> ENDER_JACK_O_LANTERN = HELPER.createCompatBlock("endergetic", "ender_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getMaxLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CURSED_JACK_O_LANTERN = HELPER.createCompatBlock("caverns_and_chasms", "cursed_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS);

	public static final RegistryObject<Block> LARGE_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_jack_o_lantern_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getMaxLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LARGE_SOUL_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_soul_jack_o_lantern_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LARGE_REDSTONE_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_redstone_jack_o_lantern_slice", () -> new LargeRedstoneJackOlanternSliceBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLightValueLit(7))), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LARGE_ENDER_JACK_O_LANTERN_SLICE = HELPER.createCompatBlock("endergetic", "large_ender_jack_o_lantern_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getMaxLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> LARGE_CURSED_JACK_O_LANTERN_SLICE = HELPER.createCompatBlock("caverns_and_chasms", "large_cursed_jack_o_lantern_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.copy(Blocks.PUMPKIN).lightLevel(AutumnityProperties.getLowerLightValue())), CreativeModeTab.TAB_BUILDING_BLOCKS);

	// Snail Shell Blocks
	public static final RegistryObject<Block> SNAIL_SHELL_BLOCK = HELPER.createBlock("snail_shell_block", () -> new SnailShellBlock(AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_BRICKS = HELPER.createBlock("snail_shell_bricks", () -> new Block(AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_BRICK_STAIRS = HELPER.createBlock("snail_shell_brick_stairs", () -> new StairBlock(() -> SNAIL_SHELL_BRICKS.get().defaultBlockState(), AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_BRICK_SLAB = HELPER.createBlock("snail_shell_brick_slab", () -> new SlabBlock(AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_BRICK_WALL = HELPER.createBlock("snail_shell_brick_wall", () -> new WallBlock(AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> SNAIL_SHELL_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "snail_shell_brick_vertical_slab", () -> new VerticalSlabBlock(AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_SNAIL_SHELL_BRICKS = HELPER.createBlock("chiseled_snail_shell_bricks", () -> new Block(AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_TILES = HELPER.createBlock("snail_shell_tiles", () -> new Block(AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_TILE_STAIRS = HELPER.createBlock("snail_shell_tile_stairs", () -> new StairBlock(() -> SNAIL_SHELL_BRICKS.get().defaultBlockState(), AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_TILE_SLAB = HELPER.createBlock("snail_shell_tile_slab", () -> new SlabBlock(AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_TILE_WALL = HELPER.createBlock("snail_shell_tile_wall", () -> new WallBlock(AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> SNAIL_SHELL_TILE_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "snail_shell_tile_vertical_slab", () -> new VerticalSlabBlock(AutumnityProperties.SNAIL_SHELL), CreativeModeTab.TAB_BUILDING_BLOCKS);

	// Maple Stuff
	public static final RegistryObject<Block> STRIPPED_MAPLE_LOG = HELPER.createBlock("stripped_maple_log", () -> new StrippedLogBlock(AutumnityProperties.MAPLE_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_MAPLE_WOOD = HELPER.createBlock("stripped_maple_wood", () -> new StrippedWoodBlock(AutumnityProperties.MAPLE_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SAPPY_MAPLE_LOG = HELPER.createBlock("sappy_maple_log", () -> new SappyLogBlock(STRIPPED_MAPLE_LOG, AutumnityProperties.MAPLE_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> SAPPY_MAPLE_WOOD = HELPER.createBlock("sappy_maple_wood", () -> new SappyWoodBlock(STRIPPED_MAPLE_WOOD, AutumnityProperties.MAPLE_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_LOG = HELPER.createBlock("maple_log", () -> new MapleLogBlock(STRIPPED_MAPLE_LOG, SAPPY_MAPLE_LOG, Block.Properties.of(Material.WOOD, (materialcolor) -> {
		return materialcolor.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.TERRACOTTA_ORANGE : MaterialColor.TERRACOTTA_WHITE;
	}).strength(2.0F).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_WOOD = HELPER.createBlock("maple_wood", () -> new MapleWoodBlock(STRIPPED_MAPLE_WOOD, SAPPY_MAPLE_WOOD, AutumnityProperties.MAPLE_LOG), CreativeModeTab.TAB_BUILDING_BLOCKS);

	// public static final RegistryObject<Block> MAPLE_BRANCH = HELPER.createBlock("maple_branch", () -> new MapleBranch(AutumnityProperties.MAPLE_BRANCH), ItemGroup.DECORATIONS);
	// public static final RegistryObject<Block> LONG_MAPLE_BRANCH = HELPER.createBlock("long_maple_branch", () -> new LongMapleBranch(AutumnityProperties.MAPLE_BRANCH), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> MAPLE_PLANKS = HELPER.createBlock("maple_planks", () -> new PlanksBlock(AutumnityProperties.MAPLE), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_STAIRS = HELPER.createBlock("maple_stairs", () -> new WoodStairBlock(MAPLE_PLANKS.get().defaultBlockState(), AutumnityProperties.MAPLE), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_SLAB = HELPER.createBlock("maple_slab", () -> new WoodSlabBlock(AutumnityProperties.MAPLE), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_PRESSURE_PLATE = HELPER.createBlock("maple_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_ORANGE).noCollission().strength(0.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> MAPLE_BUTTON = HELPER.createBlock("maple_button", () -> new BlueprintWoodButtonBlock(Block.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> MAPLE_FENCE = HELPER.createFuelBlock("maple_fence", () -> new WoodFenceBlock(AutumnityProperties.MAPLE), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MAPLE_FENCE_GATE = HELPER.createFuelBlock("maple_fence_gate", () -> new WoodFenceGateBlock(AutumnityProperties.MAPLE), 300, CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> MAPLE_DOOR = HELPER.createBlock("maple_door", () -> new WoodDoorBlock(AutumnityProperties.MAPLE_DOOR), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> MAPLE_TRAPDOOR = HELPER.createBlock("maple_trapdoor", () -> new WoodTrapDoorBlock(AutumnityProperties.MAPLE_DOOR), CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Block> VERTICAL_MAPLE_PLANKS = HELPER.createCompatBlock("quark", "vertical_maple_planks", () -> new Block(AutumnityProperties.MAPLE), CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_VERTICAL_SLAB = HELPER.createCompatFuelBlock("quark", "maple_vertical_slab", () -> new VerticalSlabBlock(AutumnityProperties.MAPLE), 150, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_BOOKSHELF = HELPER.createCompatFuelBlock("quark", "maple_bookshelf", () -> new BookshelfBlock(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_ORANGE).strength(1.5F).sound(SoundType.WOOD)), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_LADDER = HELPER.createCompatFuelBlock("quark", "maple_ladder", () -> new BlueprintLadderBlock(Block.Properties.of(Material.DECORATION).noOcclusion().strength(0.4F).sound(SoundType.LADDER)), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MAPLE_BEEHIVE = HELPER.createCompatBlock("buzzier_bees", "maple_beehive", () -> new BlueprintBeehiveBlock(Block.Properties.copy(Blocks.BEEHIVE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> STRIPPED_MAPLE_POST = HELPER.createCompatFuelBlock("quark", "stripped_maple_post", () -> new WoodPostBlock(AutumnityProperties.MAPLE), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_POST = HELPER.createCompatFuelBlock("quark", "maple_post", () -> new WoodPostBlock(STRIPPED_MAPLE_POST, AutumnityProperties.MAPLE), 300, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> SIGNS = HELPER.createSignBlock("maple", MaterialColor.TERRACOTTA_ORANGE);
	public static final Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> MAPLE_CHESTS = HELPER.createCompatChestBlocks("quark", "maple", MaterialColor.TERRACOTTA_ORANGE);

	public static final RegistryObject<Block> RED_MAPLE_LEAVES = HELPER.createBlock("red_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.createLeaves(MaterialColor.COLOR_RED), 12665871), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> ORANGE_MAPLE_LEAVES = HELPER.createBlock("orange_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.createLeaves(MaterialColor.TERRACOTTA_ORANGE), 16745768), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> YELLOW_MAPLE_LEAVES = HELPER.createBlock("yellow_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.createLeaves(MaterialColor.TERRACOTTA_YELLOW), 16766735), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MAPLE_LEAVES = HELPER.createBlock("maple_leaves", () -> new MapleLeavesBlock(AutumnityProperties.createLeaves(MaterialColor.PLANT)), CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> RED_MAPLE_SAPLING = HELPER.createBlock("red_maple_sapling", () -> new BlueprintSaplingBlock(new RedMapleTree(), AutumnityProperties.createSapling(MaterialColor.COLOR_RED)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> ORANGE_MAPLE_SAPLING = HELPER.createBlock("orange_maple_sapling", () -> new BlueprintSaplingBlock(new OrangeMapleTree(), AutumnityProperties.createSapling(MaterialColor.TERRACOTTA_ORANGE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> YELLOW_MAPLE_SAPLING = HELPER.createBlock("yellow_maple_sapling", () -> new BlueprintSaplingBlock(new YellowMapleTree(), AutumnityProperties.createSapling(MaterialColor.TERRACOTTA_YELLOW)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MAPLE_SAPLING = HELPER.createBlock("maple_sapling", () -> new BlueprintSaplingBlock(new MapleTree(), AutumnityProperties.createSapling(MaterialColor.PLANT)), CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> POTTED_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_maple_sapling", () -> new FlowerPotBlock(MAPLE_SAPLING.get(), Block.Properties.of(Material.DECORATION).strength(0.0F)));
	public static final RegistryObject<Block> POTTED_YELLOW_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_yellow_maple_sapling", () -> new FlowerPotBlock(YELLOW_MAPLE_SAPLING.get(), Block.Properties.of(Material.DECORATION, MaterialColor.TERRACOTTA_YELLOW).strength(0.0F)));
	public static final RegistryObject<Block> POTTED_ORANGE_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_orange_maple_sapling", () -> new FlowerPotBlock(ORANGE_MAPLE_SAPLING.get(), Block.Properties.of(Material.DECORATION, MaterialColor.TERRACOTTA_ORANGE).strength(0.0F)));
	public static final RegistryObject<Block> POTTED_RED_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_red_maple_sapling", () -> new FlowerPotBlock(RED_MAPLE_SAPLING.get(), Block.Properties.of(Material.DECORATION, MaterialColor.COLOR_RED).strength(0.0F)));

	public static final RegistryObject<Block> MAPLE_LEAF_CARPET = HELPER.createBlock("maple_leaf_carpet", () -> new LeafCarpetBlock(AutumnityProperties.createLeafCarpet(MaterialColor.PLANT)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> YELLOW_MAPLE_LEAF_CARPET = HELPER.createBlock("yellow_maple_leaf_carpet", () -> new LeafCarpetBlock(AutumnityProperties.createLeafCarpet(MaterialColor.TERRACOTTA_YELLOW)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> ORANGE_MAPLE_LEAF_CARPET = HELPER.createBlock("orange_maple_leaf_carpet", () -> new LeafCarpetBlock(AutumnityProperties.createLeafCarpet(MaterialColor.TERRACOTTA_ORANGE)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> RED_MAPLE_LEAF_CARPET = HELPER.createBlock("red_maple_leaf_carpet", () -> new LeafCarpetBlock(AutumnityProperties.createLeafCarpet(MaterialColor.COLOR_RED)), CreativeModeTab.TAB_DECORATIONS);

	public static final RegistryObject<Block> RED_MAPLE_HEDGE = HELPER.createCompatFuelBlock("quark", "red_maple_hedge", () -> new HedgeBlock(AutumnityProperties.MAPLE), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> ORANGE_MAPLE_HEDGE = HELPER.createCompatFuelBlock("quark", "orange_maple_hedge", () -> new HedgeBlock(AutumnityProperties.MAPLE), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> YELLOW_MAPLE_HEDGE = HELPER.createCompatFuelBlock("quark", "yellow_maple_hedge", () -> new HedgeBlock(AutumnityProperties.MAPLE), 300, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> MAPLE_HEDGE = HELPER.createCompatFuelBlock("quark", "maple_hedge", () -> new HedgeBlock(AutumnityProperties.MAPLE), 300, CreativeModeTab.TAB_DECORATIONS);

	// Compat
	public static final RegistryObject<Block> FOUL_BERRY_SACK = HELPER.createCompatBlock("quark", "foul_berry_sack", () -> new Block(Block.Properties.of(Material.WOOL, MaterialColor.TERRACOTTA_ORANGE).strength(0.5F).sound(SoundType.WOOL)), CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Block> TURKEY_EGG_CRATE = HELPER.createBlock("turkey_egg_crate", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_WHITE).strength(1.5F).sound(SoundType.WOOD)), ModList.get().isLoaded("quark") && ModList.get().isLoaded("environmental") ? CreativeModeTab.TAB_DECORATIONS : null);
}