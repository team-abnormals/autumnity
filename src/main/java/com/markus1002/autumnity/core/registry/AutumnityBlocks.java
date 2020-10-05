package com.markus1002.autumnity.core.registry;

import java.util.function.ToIntFunction;

import com.markus1002.autumnity.common.block.AutumnCrocusBlock;
import com.markus1002.autumnity.common.block.AutumnityJackOLanternBlock;
import com.markus1002.autumnity.common.block.CarvedLargePumpkinSliceBlock;
import com.markus1002.autumnity.common.block.ColoredMapleLeavesBlock;
import com.markus1002.autumnity.common.block.FoulBerryBushBlock;
import com.markus1002.autumnity.common.block.FoulBerryBushPipsBlock;
import com.markus1002.autumnity.common.block.LargePumpkinSliceBlock;
import com.markus1002.autumnity.common.block.LargeRedstoneJackOlanternSliceBlock;
import com.markus1002.autumnity.common.block.MapleLeavesBlock;
import com.markus1002.autumnity.common.block.MapleLogBlock;
import com.markus1002.autumnity.common.block.MapleWoodBlock;
import com.markus1002.autumnity.common.block.PancakeBlock;
import com.markus1002.autumnity.common.block.RedstoneJackOLanternBlock;
import com.markus1002.autumnity.common.block.SappyLogBlock;
import com.markus1002.autumnity.common.block.SappyWoodBlock;
import com.markus1002.autumnity.common.block.SnailSlimeBlock;
import com.markus1002.autumnity.common.block.SnailSlimeFullBlock;
import com.markus1002.autumnity.common.block.TallFoulBerryBushBlock;
import com.markus1002.autumnity.common.block.trees.MapleTree;
import com.markus1002.autumnity.common.block.trees.OrangeMapleTree;
import com.markus1002.autumnity.common.block.trees.RedMapleTree;
import com.markus1002.autumnity.common.block.trees.YellowMapleTree;
import com.markus1002.autumnity.core.Autumnity;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.abnormals_core.common.blocks.AbnormalsBeehiveBlock;
import com.teamabnormals.abnormals_core.common.blocks.AbnormalsLadderBlock;
import com.teamabnormals.abnormals_core.common.blocks.BookshelfBlock;
import com.teamabnormals.abnormals_core.common.blocks.LeafCarpetBlock;
import com.teamabnormals.abnormals_core.common.blocks.VerticalSlabBlock;
import com.teamabnormals.abnormals_core.common.blocks.chest.AbnormalsChestBlock;
import com.teamabnormals.abnormals_core.common.blocks.chest.AbnormalsTrappedChestBlock;
import com.teamabnormals.abnormals_core.common.blocks.sign.AbnormalsStandingSignBlock;
import com.teamabnormals.abnormals_core.common.blocks.sign.AbnormalsWallSignBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsSaplingBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsWoodButtonBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.PlanksBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.StrippedLogBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.StrippedWoodBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodDoorBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodFenceBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodFenceGateBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodPressurePlateBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodSlabBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodStairsBlock;
import com.teamabnormals.abnormals_core.common.blocks.wood.WoodTrapDoorBlock;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityBlocks
{
	public static final RegistryHelper HELPER = Autumnity.REGISTRY_HELPER;

	public static final RegistryObject<Block> SNAIL_SLIME = HELPER.createBlock("snail_slime", () -> new SnailSlimeBlock(Block.Properties.create(Material.SNOW, MaterialColor.WHITE_TERRACOTTA).notSolid().doesNotBlockMovement().sound(SoundType.HONEY)), ItemGroup.MISC);
	public static final RegistryObject<Block> SNAIL_SLIME_BLOCK = HELPER.createBlock("snail_slime_block", () -> new SnailSlimeFullBlock(Block.Properties.create(Material.CLAY, MaterialColor.WHITE_TERRACOTTA).notSolid().sound(SoundType.HONEY)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> PANCAKE = HELPER.createBlock("pancake", () -> new PancakeBlock(Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.FOOD);
	public static final RegistryObject<Block> FOUL_BERRY_BUSH_PIPS = HELPER.createBlockNoItem("foul_berry_bush_pips", () -> new FoulBerryBushPipsBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH)));
	public static final RegistryObject<Block> FOUL_BERRY_BUSH = HELPER.createBlockNoItem("foul_berry_bush", () -> new FoulBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH)));
	public static final RegistryObject<Block> TALL_FOUL_BERRY_BUSH = HELPER.createBlockNoItem("tall_foul_berry_bush", () -> new TallFoulBerryBushBlock(Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH)));
	public static final RegistryObject<Block> AUTUMN_CROCUS = HELPER.createBlock("autumn_crocus", () -> new AutumnCrocusBlock(() -> AutumnityEffects.FOUL_TASTE.get(), 320, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> POTTED_AUTUMN_CROCUS = HELPER.createBlockNoItem("potted_autumn_crocus", () -> new FlowerPotBlock(AUTUMN_CROCUS.get(), Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F)));
	
	// Pumpkins
	public static final RegistryObject<Block> LARGE_PUMPKIN_SLICE = HELPER.createBlock("large_pumpkin_slice", () -> new LargePumpkinSliceBlock(Block.Properties.create(Material.GOURD, MaterialColor.ADOBE).hardnessAndResistance(1.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> CARVED_LARGE_PUMPKIN_SLICE = HELPER.createBlock("carved_large_pumpkin_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.create(Material.GOURD, MaterialColor.ADOBE).hardnessAndResistance(1.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> LARGE_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_jack_o_lantern_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.create(Material.GOURD, MaterialColor.ADOBE).hardnessAndResistance(1.0F).sound(SoundType.WOOD).setLightLevel((p_235462_0_) -> {
		return 15;
	})), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SOUL_JACK_O_LANTERN = HELPER.createBlock("soul_jack_o_lantern", () -> new AutumnityJackOLanternBlock(Block.Properties.create(Material.GOURD, MaterialColor.ADOBE).hardnessAndResistance(1.0F).sound(SoundType.WOOD).setLightLevel((p_235462_0_) -> {
		return 15;
	})), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> LARGE_SOUL_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_soul_jack_o_lantern_slice", () -> new CarvedLargePumpkinSliceBlock(Block.Properties.create(Material.GOURD, MaterialColor.ADOBE).hardnessAndResistance(1.0F).sound(SoundType.WOOD).setLightLevel((p_235462_0_) -> {
		return 15;
	})), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> REDSTONE_JACK_O_LANTERN = HELPER.createBlock("redstone_jack_o_lantern", () -> new RedstoneJackOLanternBlock(Block.Properties.create(Material.GOURD, MaterialColor.ADOBE).setLightLevel(getLightValueLit(7)).hardnessAndResistance(1.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> LARGE_REDSTONE_JACK_O_LANTERN_SLICE = HELPER.createBlock("large_redstone_jack_o_lantern_slice", () -> new LargeRedstoneJackOlanternSliceBlock(Block.Properties.create(Material.GOURD, MaterialColor.ADOBE).setLightLevel(getLightValueLit(7)).hardnessAndResistance(1.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);

	// Snail Shell Blocks
	public static final RegistryObject<Block> SNAIL_SHELL_BLOCK = HELPER.createBlock("snail_shell_block", () -> new Block(Block.Properties.create(Material.ROCK, MaterialColor.BROWN_TERRACOTTA).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3.0F, 9.0F)), ItemGroup.BUILDING_BLOCKS);
	
	// Maple Stuff
	public static final RegistryObject<Block> STRIPPED_MAPLE_LOG = HELPER.createBlock("stripped_maple_log", () -> new StrippedLogBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> STRIPPED_MAPLE_WOOD = HELPER.createBlock("stripped_maple_wood", () -> new StrippedWoodBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SAPPY_MAPLE_LOG = HELPER.createBlock("sappy_maple_log", () -> new SappyLogBlock(STRIPPED_MAPLE_LOG, Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> SAPPY_MAPLE_WOOD = HELPER.createBlock("sappy_maple_wood", () -> new SappyWoodBlock(STRIPPED_MAPLE_WOOD, Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_LOG = HELPER.createBlock("maple_log", () -> new MapleLogBlock(STRIPPED_MAPLE_LOG, SAPPY_MAPLE_LOG, Block.Properties.create(Material.WOOD, (materialcolor) -> {
		return materialcolor.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.ORANGE_TERRACOTTA : MaterialColor.WHITE_TERRACOTTA;
	}).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_WOOD = HELPER.createBlock("maple_wood", () -> new MapleWoodBlock(STRIPPED_MAPLE_WOOD, SAPPY_MAPLE_WOOD, Block.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);

	public static final RegistryObject<Block> MAPLE_PLANKS = HELPER.createBlock("maple_planks", () -> new PlanksBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_STAIRS = HELPER.createBlock("maple_stairs", () -> new WoodStairsBlock(MAPLE_PLANKS.get().getDefaultState(), Block.Properties.from(MAPLE_PLANKS.get())), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_SLAB = HELPER.createBlock("maple_slab", () -> new WoodSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_PRESSURE_PLATE = HELPER.createBlock("maple_pressure_plate", () -> new WoodPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> MAPLE_BUTTON = HELPER.createBlock("maple_button", () -> new AbnormalsWoodButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> MAPLE_FENCE = HELPER.createBlock("maple_fence", () -> new WoodFenceBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MAPLE_FENCE_GATE = HELPER.createBlock("maple_fence_gate", () -> new WoodFenceGateBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> MAPLE_DOOR = HELPER.createBlock("maple_door", () -> new WoodDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).notSolid().hardnessAndResistance(3.0F).sound(SoundType.WOOD)), ItemGroup.REDSTONE);
	public static final RegistryObject<Block> MAPLE_TRAPDOOR = HELPER.createBlock("maple_trapdoor", () -> new WoodTrapDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).notSolid().hardnessAndResistance(3.0F).sound(SoundType.WOOD)), ItemGroup.REDSTONE);
	public static final Pair<RegistryObject<AbnormalsStandingSignBlock>, RegistryObject<AbnormalsWallSignBlock>> SIGNS = HELPER.createSignBlock("maple", MaterialColor.ORANGE_TERRACOTTA);

	public static final RegistryObject<Block> RED_MAPLE_LEAVES = HELPER.createBlock("red_maple_leaves", () -> new ColoredMapleLeavesBlock(createLeaves(), 12665871), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> ORANGE_MAPLE_LEAVES = HELPER.createBlock("orange_maple_leaves", () -> new ColoredMapleLeavesBlock(createLeaves(), 16745768), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> YELLOW_MAPLE_LEAVES = HELPER.createBlock("yellow_maple_leaves", () -> new ColoredMapleLeavesBlock(createLeaves(), 16766735), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MAPLE_LEAVES = HELPER.createBlock("maple_leaves", () -> new MapleLeavesBlock(createLeaves()), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> RED_MAPLE_SAPLING = HELPER.createBlock("red_maple_sapling", () -> new AbnormalsSaplingBlock(new RedMapleTree(), Block.Properties.create(Material.PLANTS, MaterialColor.RED).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> ORANGE_MAPLE_SAPLING = HELPER.createBlock("orange_maple_sapling", () -> new AbnormalsSaplingBlock(new OrangeMapleTree(), Block.Properties.create(Material.PLANTS, MaterialColor.ORANGE_TERRACOTTA).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> YELLOW_MAPLE_SAPLING = HELPER.createBlock("yellow_maple_sapling", () -> new AbnormalsSaplingBlock(new YellowMapleTree(), Block.Properties.create(Material.PLANTS, MaterialColor.YELLOW_TERRACOTTA).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> MAPLE_SAPLING = HELPER.createBlock("maple_sapling", () -> new AbnormalsSaplingBlock(new MapleTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT)), ItemGroup.DECORATIONS);

	public static final RegistryObject<Block> POTTED_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_maple_sapling", () -> new FlowerPotBlock(MAPLE_SAPLING.get(), Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F)));
	public static final RegistryObject<Block> POTTED_YELLOW_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_yellow_maple_sapling", () -> new FlowerPotBlock(YELLOW_MAPLE_SAPLING.get(), Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F)));
	public static final RegistryObject<Block> POTTED_ORANGE_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_orange_maple_sapling", () -> new FlowerPotBlock(ORANGE_MAPLE_SAPLING.get(), Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F)));
	public static final RegistryObject<Block> POTTED_RED_MAPLE_SAPLING = HELPER.createBlockNoItem("potted_red_maple_sapling", () -> new FlowerPotBlock(RED_MAPLE_SAPLING.get(), Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F)));

	public static final RegistryObject<Block> MAPLE_LEAF_CARPET = HELPER.createBlock("maple_leaf_carpet", () -> new LeafCarpetBlock(Block.Properties.create(Material.CARPET).notSolid().hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> YELLOW_MAPLE_LEAF_CARPET = HELPER.createBlock("yellow_maple_leaf_carpet", () -> new LeafCarpetBlock(Block.Properties.create(Material.CARPET, MaterialColor.YELLOW_TERRACOTTA).notSolid().hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> ORANGE_MAPLE_LEAF_CARPET = HELPER.createBlock("orange_maple_leaf_carpet", () -> new LeafCarpetBlock(Block.Properties.create(Material.CARPET, MaterialColor.ORANGE_TERRACOTTA).notSolid().hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> RED_MAPLE_LEAF_CARPET = HELPER.createBlock("red_maple_leaf_carpet", () -> new LeafCarpetBlock(Block.Properties.create(Material.CARPET, MaterialColor.RED).notSolid().hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT)), ItemGroup.DECORATIONS);

	// Compat
	public static final RegistryObject<Block> MAPLE_VERTICAL_SLAB = HELPER.createCompatBlock("quark", "maple_vertical_slab", () -> new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> VERTICAL_MAPLE_PLANKS = HELPER.createCompatBlock("quark", "vertical_maple_planks", () -> new Block(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_BOOKSHELF = HELPER.createCompatBlock("quark", "maple_bookshelf", () -> new BookshelfBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.WOOD)), ItemGroup.BUILDING_BLOCKS);
	public static final RegistryObject<Block> MAPLE_LADDER = HELPER.createCompatBlock("quark", "maple_ladder", () -> new AbnormalsLadderBlock(Block.Properties.create(Material.MISCELLANEOUS).notSolid().hardnessAndResistance(0.4F).harvestTool(ToolType.AXE).sound(SoundType.LADDER)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> FOUL_BERRY_SACK = HELPER.createCompatBlock("quark", "foul_berry_sack", () -> new Block(Block.Properties.create(Material.WOOL, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)), ItemGroup.DECORATIONS);
	public static final RegistryObject<Block> POTTED_FOUL_BERRIES = HELPER.createBlockNoItem("potted_foul_berries", () -> new FlowerPotBlock(FOUL_BERRY_BUSH.get(), Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F)));
	public static final RegistryObject<Block> MAPLE_BEEHIVE = HELPER.createCompatBlock("buzzier_bees", "maple_beehive", () -> new AbnormalsBeehiveBlock(Block.Properties.from(Blocks.BEEHIVE)), ItemGroup.DECORATIONS);
	public static final Pair<RegistryObject<AbnormalsChestBlock>, RegistryObject<AbnormalsTrappedChestBlock>> MAPLE_CHESTS = HELPER.createCompatChestBlocks("maple", MaterialColor.ORANGE_TERRACOTTA);

	private static ToIntFunction<BlockState> getLightValueLit(int lightValue)
	{
		return (p_235421_1_) -> {
			return p_235421_1_.get(BlockStateProperties.LIT) ? lightValue : 0;
		};
	}
	
	public static Block.Properties createLeaves()
	{
		return Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid().harvestTool(ToolType.HOE).setAllowsSpawn(AutumnityBlocks::allowsSpawnOnLeaves).setSuffocates(AutumnityBlocks::isntSolid).setBlocksVision(AutumnityBlocks::isntSolid);
	}

	private static Boolean allowsSpawnOnLeaves(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity)
	{
		return entity == EntityType.OCELOT || entity == EntityType.PARROT;
	}

	private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos)
	{
		return false;
	}
}