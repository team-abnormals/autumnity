package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.abnormals_core.common.blocks.AbnormalsBeehiveBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.AbnormalsFlowerBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.AbnormalsLadderBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.AbnormalsStairsBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.BookshelfBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.LeafCarpetBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.VerticalSlabBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.chest.AbnormalsChestBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.chest.AbnormalsTrappedChestBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.sign.AbnormalsStandingSignBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.sign.AbnormalsWallSignBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.AbnormalsSaplingBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.AbnormalsWoodButtonBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.PlanksBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.StrippedLogBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.StrippedWoodBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.WoodDoorBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.WoodFenceBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.WoodFenceGateBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.WoodPostBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.WoodPressurePlateBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.WoodSlabBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.WoodStairsBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.WoodTrapDoorBlock;
import com.minecraftabnormals.abnormals_core.core.util.registry.BlockSubRegistryHelper;
import com.minecraftabnormals.autumnity.common.block.AutumnityJackOLanternBlock;
import com.minecraftabnormals.autumnity.common.block.CarvedLargePumpkinSliceBlock;
import com.minecraftabnormals.autumnity.common.block.ColoredMapleLeavesBlock;
import com.minecraftabnormals.autumnity.common.block.CookedTurkeyBlock;
import com.minecraftabnormals.autumnity.common.block.FoulBerryBushBlock;
import com.minecraftabnormals.autumnity.common.block.FoulBerryBushPipsBlock;
import com.minecraftabnormals.autumnity.common.block.LargePumpkinSliceBlock;
import com.minecraftabnormals.autumnity.common.block.LargeRedstoneJackOlanternSliceBlock;
import com.minecraftabnormals.autumnity.common.block.MapleLeavesBlock;
import com.minecraftabnormals.autumnity.common.block.MapleLogBlock;
import com.minecraftabnormals.autumnity.common.block.MaplePostBlock;
import com.minecraftabnormals.autumnity.common.block.MapleWoodBlock;
import com.minecraftabnormals.autumnity.common.block.PancakeBlock;
import com.minecraftabnormals.autumnity.common.block.PlaceableSlimeBlock;
import com.minecraftabnormals.autumnity.common.block.RedstoneJackOLanternBlock;
import com.minecraftabnormals.autumnity.common.block.SappyLogBlock;
import com.minecraftabnormals.autumnity.common.block.SappyPostBlock;
import com.minecraftabnormals.autumnity.common.block.SappyWoodBlock;
import com.minecraftabnormals.autumnity.common.block.SnailShellBlock;
import com.minecraftabnormals.autumnity.common.block.SnailSlimeBlock;
import com.minecraftabnormals.autumnity.common.block.TallFoulBerryBushBlock;
import com.minecraftabnormals.autumnity.common.block.TurkeyBlock;
import com.minecraftabnormals.autumnity.common.block.trees.MapleTree;
import com.minecraftabnormals.autumnity.common.block.trees.OrangeMapleTree;
import com.minecraftabnormals.autumnity.common.block.trees.RedMapleTree;
import com.minecraftabnormals.autumnity.common.block.trees.YellowMapleTree;
import com.minecraftabnormals.autumnity.core.Autumnity;
import com.minecraftabnormals.autumnity.core.other.AutumnityProperties;
import com.mojang.datafixers.util.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityBlocks {
	public static final BlockSubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getBlockSubHelper();

	public static final RegistryObject<Block> SNAIL_SLIME = HELPER.createBlock("snail_slime", () -> new PlaceableSlimeBlock(Block.Properties.create(Material.MISCELLANEOUS, MaterialColor.WHITE_TERRACOTTA).notSolid().doesNotBlockMovement().sound(SoundType.HONEY)), ItemGroup.MISC);
	public static final RegistryObject<Block> SNAIL_SLIME_BLOCK = HELPER.createBlock("snail_slime_block", () -> new SnailSlimeBlock(Block.Properties.create(Material.CLAY, MaterialColor.WHITE_TERRACOTTA).notSolid().sound(SoundType.HONEY)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> PANCAKE = HELPER.createBlock("pancake", () -> new PancakeBlock(Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.FOOD);
	public static final RegistryObject<Block> FOUL_BERRY_BUSH_PIPS = HELPER.createBlockNoItem("foul_berry_bush_pips", () -> new FoulBerryBushPipsBlock(AutumnityProperties.FOUL_BERRIES));
	public static final RegistryObject<Block> FOUL_BERRY_BUSH = HELPER.createBlockNoItem("foul_berry_bush", () -> new FoulBerryBushBlock(AutumnityProperties.FOUL_BERRIES));
	public static final RegistryObject<Block> TALL_FOUL_BERRY_BUSH = HELPER.createBlockNoItem("tall_foul_berry_bush", () -> new TallFoulBerryBushBlock(AutumnityProperties.FOUL_BERRIES));
	public static final RegistryObject<Block> POTTED_FOUL_BERRIES = HELPER.createBlockNoItem("potted_foul_berries", () -> new FlowerPotBlock(FOUL_BERRY_BUSH.get(), Block.Properties.from(Blocks.POTTED_ALLIUM)));
	public static final RegistryObject<Block> AUTUMN_CROCUS = HELPER.createBlock("autumn_crocus", () -> new AbnormalsFlowerBlock(AutumnityEffects.FOUL_TASTE, 320, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> POTTED_AUTUMN_CROCUS = HELPER.createBlockNoItem("potted_autumn_crocus", () -> new FlowerPotBlock(AUTUMN_CROCUS.get(), Block.Properties.from(Blocks.POTTED_ALLIUM)));
	public static final RegistryObject<Block> TURKEY = HELPER.createBlock("turkey", () -> new TurkeyBlock(Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.FOOD);
	public static final RegistryObject<Block> COOKED_TURKEY = HELPER.createBlock("cooked_turkey", () -> new CookedTurkeyBlock(Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.FOOD);

	// Pumpkins
	public static final RegistryObject<Block> LARGE_PUMPKIN_SLICE = HELPER.createBlock("large_pumpkin_slice", () -> new LargePumpkinSliceBlock(Block.Properties.from(Blocks.PUMPKIN)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CARVED_LARGE_PUMPKIN_SLICE = HELPER.createBlock("carved_large_pumpkin_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.from(Blocks.PUMPKIN)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SOUL_JACK_O_LANTERN = HELPER.createBlock("soul_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.from(Blocks.PUMPKIN).setLightLevel(AutumnityProperties.getMaxLightValue())), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> REDSTONE_JACK_O_LANTERN = HELPER.createBlock("redstone_jack_o_lantern", () -> new RedstoneJackOLanternBlock(Block.Properties.from(Blocks.PUMPKIN).setLightLevel(AutumnityProperties.getLightValueLit(7))), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> ENDER_JACK_O_LANTERN = HELPER.createCompatBlock("endergetic", "ender_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.from(Blocks.PUMPKIN).setLightLevel(AutumnityProperties.getMaxLightValue())), ItemGroup.BUILDING_BLOCKS);

	public static final RegistryObject<Block> LARGE_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_jack_o_lantern_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.from(Blocks.PUMPKIN).setLightLevel(AutumnityProperties.getMaxLightValue())), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> LARGE_SOUL_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_soul_jack_o_lantern_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.from(Blocks.PUMPKIN).setLightLevel(AutumnityProperties.getMaxLightValue())), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> LARGE_REDSTONE_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_redstone_jack_o_lantern_slice", () -> new LargeRedstoneJackOlanternSliceBlock(Block.Properties.from(Blocks.PUMPKIN).setLightLevel(AutumnityProperties.getLightValueLit(7))), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> LARGE_ENDER_JACK_O_LANTERN_SLICE = HELPER.createCompatBlock("endergetic", "large_ender_jack_o_lantern_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.from(Blocks.PUMPKIN).setLightLevel(AutumnityProperties.getMaxLightValue())), ItemGroup.BUILDING_BLOCKS);

	// Snail Shell Blocks
	public static final RegistryObject<Block> SNAIL_SHELL_BLOCK = HELPER.createBlock("snail_shell_block", () -> new SnailShellBlock(AutumnityProperties.SNAIL_SHELL), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_BRICKS = HELPER.createBlock("snail_shell_bricks", () -> new Block(AutumnityProperties.SNAIL_SHELL), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_BRICK_STAIRS = HELPER.createBlock("snail_shell_brick_stairs", () -> new AbnormalsStairsBlock(SNAIL_SHELL_BRICKS.get().getDefaultState(), AutumnityProperties.SNAIL_SHELL), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_BRICK_SLAB = HELPER.createBlock("snail_shell_brick_slab", () -> new SlabBlock(AutumnityProperties.SNAIL_SHELL), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_BRICK_WALL = HELPER.createBlock("snail_shell_brick_wall", () -> new WallBlock(AutumnityProperties.SNAIL_SHELL), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> SNAIL_SHELL_BRICK_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "snail_shell_brick_vertical_slab", () -> new VerticalSlabBlock(AutumnityProperties.SNAIL_SHELL), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CHISELED_SNAIL_SHELL_BRICKS = HELPER.createBlock("chiseled_snail_shell_bricks", () -> new Block(AutumnityProperties.SNAIL_SHELL), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_TILES = HELPER.createBlock("snail_shell_tiles", () -> new Block(AutumnityProperties.SNAIL_SHELL), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_TILE_STAIRS = HELPER.createBlock("snail_shell_tile_stairs", () -> new AbnormalsStairsBlock(SNAIL_SHELL_BRICKS.get().getDefaultState(), AutumnityProperties.SNAIL_SHELL), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_TILE_SLAB = HELPER.createBlock("snail_shell_tile_slab", () -> new SlabBlock(AutumnityProperties.SNAIL_SHELL), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SNAIL_SHELL_TILE_WALL = HELPER.createBlock("snail_shell_tile_wall", () -> new WallBlock(AutumnityProperties.SNAIL_SHELL), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> SNAIL_SHELL_TILE_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "snail_shell_tile_vertical_slab", () -> new VerticalSlabBlock(AutumnityProperties.SNAIL_SHELL), ItemGroup.BUILDING_BLOCKS);

	// Maple Stuff
	public static final RegistryObject<Block> STRIPPED_MAPLE_LOG = HELPER.createBlock("stripped_maple_log", () -> new StrippedLogBlock(AutumnityProperties.MAPLE_LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_MAPLE_WOOD = HELPER.createBlock("stripped_maple_wood", () -> new StrippedWoodBlock(AutumnityProperties.MAPLE_LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SAPPY_MAPLE_LOG = HELPER.createBlock("sappy_maple_log", () -> new SappyLogBlock(STRIPPED_MAPLE_LOG, AutumnityProperties.MAPLE_LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SAPPY_MAPLE_WOOD = HELPER.createBlock("sappy_maple_wood", () -> new SappyWoodBlock(STRIPPED_MAPLE_WOOD, AutumnityProperties.MAPLE_LOG), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_LOG = HELPER.createBlock("maple_log", () -> new MapleLogBlock(STRIPPED_MAPLE_LOG, SAPPY_MAPLE_LOG, Block.Properties.create(Material.WOOD, (materialcolor) -> {
		return materialcolor.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.ORANGE_TERRACOTTA : MaterialColor.WHITE_TERRACOTTA;
	}).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_WOOD = HELPER.createBlock("maple_wood", () -> new MapleWoodBlock(STRIPPED_MAPLE_WOOD, SAPPY_MAPLE_WOOD, AutumnityProperties.MAPLE_LOG), ItemGroup.BUILDING_BLOCKS);

	// public static final RegistryObject<Block> MAPLE_BRANCH = HELPER.createBlock("maple_branch", () -> new MapleBranch(AutumnityProperties.MAPLE_BRANCH), ItemGroup.DECORATIONS);
	// public static final RegistryObject<Block> LONG_MAPLE_BRANCH = HELPER.createBlock("long_maple_branch", () -> new LongMapleBranch(AutumnityProperties.MAPLE_BRANCH), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> MAPLE_PLANKS = HELPER.createBlock("maple_planks", () -> new PlanksBlock(AutumnityProperties.MAPLE), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_STAIRS = HELPER.createBlock("maple_stairs", () -> new WoodStairsBlock(MAPLE_PLANKS.get().getDefaultState(), AutumnityProperties.MAPLE), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_SLAB = HELPER.createBlock("maple_slab", () -> new WoodSlabBlock(AutumnityProperties.MAPLE), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_PRESSURE_PLATE = HELPER.createBlock("maple_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> MAPLE_BUTTON = HELPER.createBlock("maple_button", () -> new AbnormalsWoodButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> MAPLE_FENCE = HELPER.createFuelBlock("maple_fence", () -> new WoodFenceBlock(AutumnityProperties.MAPLE), 300, ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MAPLE_FENCE_GATE = HELPER.createFuelBlock("maple_fence_gate", () -> new WoodFenceGateBlock(AutumnityProperties.MAPLE), 300, ItemGroup.REDSTONE);
	public static final RegistryObject<Block> MAPLE_DOOR = HELPER.createBlock("maple_door", () -> new WoodDoorBlock(AutumnityProperties.MAPLE_DOOR), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> MAPLE_TRAPDOOR = HELPER.createBlock("maple_trapdoor", () -> new WoodTrapDoorBlock(AutumnityProperties.MAPLE_DOOR), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> VERTICAL_MAPLE_PLANKS = HELPER.createCompatBlock("quark", "vertical_maple_planks", () -> new Block(AutumnityProperties.MAPLE), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "maple_vertical_slab", () -> new VerticalSlabBlock(AutumnityProperties.MAPLE), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_BOOKSHELF = HELPER.createCompatBlock("quark", "maple_bookshelf", () -> new BookshelfBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_LADDER = HELPER.createCompatBlock("quark", "maple_ladder", () -> new AbnormalsLadderBlock(Block.Properties.create(Material.MISCELLANEOUS).notSolid().hardnessAndResistance(0.4F).harvestTool(ToolType.AXE).sound(SoundType.LADDER)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MAPLE_BEEHIVE = HELPER.createCompatBlock("buzzier_bees", "maple_beehive", () -> new AbnormalsBeehiveBlock(Block.Properties.from(Blocks.BEEHIVE)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> STRIPPED_MAPLE_POST = HELPER.createCompatFuelBlock("quark", "stripped_maple_post", () -> new WoodPostBlock(AutumnityProperties.MAPLE), 300, ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SAPPY_MAPLE_POST = HELPER.createCompatFuelBlock("quark", "sappy_maple_post", () -> new SappyPostBlock(STRIPPED_MAPLE_POST, AutumnityProperties.MAPLE), 300, ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_POST = HELPER.createCompatFuelBlock("quark", "maple_post", () -> new MaplePostBlock(STRIPPED_MAPLE_POST, SAPPY_MAPLE_POST, AutumnityProperties.MAPLE), 300, ItemGroup.BUILDING_BLOCKS);
	public static final Pair<RegistryObject<AbnormalsStandingSignBlock>, RegistryObject<AbnormalsWallSignBlock>> SIGNS = HELPER.createSignBlock("maple", MaterialColor.ORANGE_TERRACOTTA);
	public static final Pair<RegistryObject<AbnormalsChestBlock>, RegistryObject<AbnormalsTrappedChestBlock>> MAPLE_CHESTS = HELPER.createCompatChestBlocks("quark", "maple", MaterialColor.ORANGE_TERRACOTTA);

	public static final RegistryObject<Block> RED_MAPLE_LEAVES = HELPER.createBlock("red_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.createLeaves(MaterialColor.RED), 12665871), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> ORANGE_MAPLE_LEAVES = HELPER.createBlock("orange_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.createLeaves(MaterialColor.ORANGE_TERRACOTTA), 16745768), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> YELLOW_MAPLE_LEAVES = HELPER.createBlock("yellow_maple_leaves", () -> new ColoredMapleLeavesBlock(AutumnityProperties.createLeaves(MaterialColor.YELLOW_TERRACOTTA), 16766735), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MAPLE_LEAVES = HELPER.createBlock("maple_leaves", () -> new MapleLeavesBlock(AutumnityProperties.createLeaves(MaterialColor.FOLIAGE)), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> RED_MAPLE_SAPLING = HELPER.createBlock("red_maple_sapling", () -> new AbnormalsSaplingBlock(new RedMapleTree(), AutumnityProperties.createSapling(MaterialColor.RED)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> ORANGE_MAPLE_SAPLING = HELPER.createBlock("orange_maple_sapling", () -> new AbnormalsSaplingBlock(new OrangeMapleTree(), AutumnityProperties.createSapling(MaterialColor.ORANGE_TERRACOTTA)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> YELLOW_MAPLE_SAPLING = HELPER.createBlock("yellow_maple_sapling", () -> new AbnormalsSaplingBlock(new YellowMapleTree(), AutumnityProperties.createSapling(MaterialColor.YELLOW_TERRACOTTA)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MAPLE_SAPLING = HELPER.createBlock("maple_sapling", () -> new AbnormalsSaplingBlock(new MapleTree(), AutumnityProperties.createSapling(MaterialColor.FOLIAGE)), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> POTTED_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_maple_sapling", () -> new FlowerPotBlock(MAPLE_SAPLING.get(), Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F)));
	public static final RegistryObject<Block> POTTED_YELLOW_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_yellow_maple_sapling", () -> new FlowerPotBlock(YELLOW_MAPLE_SAPLING.get(), Block.Properties.create(Material.MISCELLANEOUS, MaterialColor.YELLOW_TERRACOTTA).hardnessAndResistance(0.0F)));
	public static final RegistryObject<Block> POTTED_ORANGE_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_orange_maple_sapling", () -> new FlowerPotBlock(ORANGE_MAPLE_SAPLING.get(), Block.Properties.create(Material.MISCELLANEOUS, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(0.0F)));
	public static final RegistryObject<Block> POTTED_RED_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_red_maple_sapling", () -> new FlowerPotBlock(RED_MAPLE_SAPLING.get(), Block.Properties.create(Material.MISCELLANEOUS, MaterialColor.RED).hardnessAndResistance(0.0F)));

	public static final RegistryObject<Block> MAPLE_LEAF_CARPET = HELPER.createBlock("maple_leaf_carpet", () -> new LeafCarpetBlock(AutumnityProperties.createLeafCarpet(MaterialColor.FOLIAGE)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> YELLOW_MAPLE_LEAF_CARPET = HELPER.createBlock("yellow_maple_leaf_carpet", () -> new LeafCarpetBlock(AutumnityProperties.createLeafCarpet(MaterialColor.YELLOW_TERRACOTTA)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> ORANGE_MAPLE_LEAF_CARPET = HELPER.createBlock("orange_maple_leaf_carpet", () -> new LeafCarpetBlock(AutumnityProperties.createLeafCarpet(MaterialColor.ORANGE_TERRACOTTA)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> RED_MAPLE_LEAF_CARPET = HELPER.createBlock("red_maple_leaf_carpet", () -> new LeafCarpetBlock(AutumnityProperties.createLeafCarpet(MaterialColor.RED)), ItemGroup.DECORATIONS);

	// Compat
	public static final RegistryObject<Block> FOUL_BERRY_SACK = HELPER.createCompatBlock("quark", "foul_berry_sack", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> TURKEY_EGG_CRATE = HELPER.createBlock("turkey_egg_crate", () -> new Block(Block.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.WOOD)), ModList.get().isLoaded("quark") && ModList.get().isLoaded("environmental") ? ItemGroup.DECORATIONS : null);
}