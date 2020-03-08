package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.block.AutumnalLeavesBlock;
import com.markus1002.autumnity.common.block.BookshelfBlock;
import com.markus1002.autumnity.common.block.LeafCarpetBlock;
import com.markus1002.autumnity.common.block.ModDoorBlock;
import com.markus1002.autumnity.common.block.ModLadderBlock;
import com.markus1002.autumnity.common.block.ModPressurePlateBlock;
import com.markus1002.autumnity.common.block.ModSaplingBlock;
import com.markus1002.autumnity.common.block.ModTrapDoorBlock;
import com.markus1002.autumnity.common.block.ModWoodButtonBlock;
import com.markus1002.autumnity.common.block.PancakeBlock;
import com.markus1002.autumnity.common.block.SappyLogBlock;
import com.markus1002.autumnity.common.block.SnailSlimeBlock;
import com.markus1002.autumnity.common.block.SnailSlimeFullBlock;
import com.markus1002.autumnity.common.block.VerticalSlabBlock;
import com.markus1002.autumnity.common.block.trees.MapleTree;
import com.markus1002.autumnity.common.block.trees.OrangeMapleTree;
import com.markus1002.autumnity.common.block.trees.RedMapleTree;
import com.markus1002.autumnity.common.block.trees.YellowMapleTree;
import com.markus1002.autumnity.common.item.FuelItem;
import com.markus1002.autumnity.core.util.Reference;
import com.markus1002.autumnity.core.util.compatibility.Quark;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks
{
	public static final Block SNAIL_SLIME = new SnailSlimeBlock(Block.Properties.create(Material.CLAY, MaterialColor.WHITE_TERRACOTTA).doesNotBlockMovement().sound(SoundType.SLIME));
	public static final Block SNAIL_SLIME_BLOCK = new SnailSlimeFullBlock(Block.Properties.create(Material.CLAY, MaterialColor.WHITE_TERRACOTTA).sound(SoundType.SLIME));
    public static final Block PANCAKE = new PancakeBlock(Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH));

	// Maple Stuff
	public static final Block MAPLE_LOG = new LogBlock(MaterialColor.ORANGE_TERRACOTTA, Block.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
	public static final Block MAPLE_WOOD = new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
	public static final Block STRIPPED_MAPLE_LOG = new LogBlock(MaterialColor.ORANGE_TERRACOTTA, Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
	public static final Block STRIPPED_MAPLE_WOOD = new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
	public static final Block SAPPY_MAPLE_LOG = new SappyLogBlock(STRIPPED_MAPLE_LOG, MaterialColor.ORANGE_TERRACOTTA, Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
	public static final Block SAPPY_MAPLE_WOOD = new SappyLogBlock(STRIPPED_MAPLE_WOOD, MaterialColor.ORANGE_TERRACOTTA, Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
	
	public static final Block MAPLE_LEAVES = new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT));
	public static final Block YELLOW_MAPLE_LEAVES = new AutumnalLeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT), 16766735);
	public static final Block ORANGE_MAPLE_LEAVES = new AutumnalLeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT), 16745768);
	public static final Block RED_MAPLE_LEAVES = new AutumnalLeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT), 12665871);
	
	public static final Block MAPLE_SAPLING = new ModSaplingBlock(new MapleTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
	public static final Block YELLOW_MAPLE_SAPLING = new ModSaplingBlock(new YellowMapleTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
	public static final Block ORANGE_MAPLE_SAPLING = new ModSaplingBlock(new OrangeMapleTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT));
	public static final Block RED_MAPLE_SAPLING = new ModSaplingBlock(new RedMapleTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT));

    public static final Block POTTED_MAPLE_SAPLING = new FlowerPotBlock(MAPLE_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F));
    public static final Block POTTED_YELLOW_MAPLE_SAPLING = new FlowerPotBlock(YELLOW_MAPLE_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F));
    public static final Block POTTED_ORANGE_MAPLE_SAPLING = new FlowerPotBlock(ORANGE_MAPLE_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F));
    public static final Block POTTED_RED_MAPLE_SAPLING = new FlowerPotBlock(RED_MAPLE_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F));
    
	public static final Block YELLOW_MAPLE_LEAF_CARPET = new LeafCarpetBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT));
	public static final Block ORANGE_MAPLE_LEAF_CARPET = new LeafCarpetBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT));
	public static final Block RED_MAPLE_LEAF_CARPET = new LeafCarpetBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT));
	
	public static final Block MAPLE_PLANKS = new Block(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
	public static final Block MAPLE_STAIRS = new StairsBlock(() -> MAPLE_PLANKS.getDefaultState(), Block.Properties.from(MAPLE_PLANKS));
	public static final Block MAPLE_SLAB = new SlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
	public static final Block MAPLE_PRESSURE_PLATE = new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD));
	public static final Block MAPLE_BUTTON = new ModWoodButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD));
	public static final Block MAPLE_FENCE = new FenceBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
	public static final Block MAPLE_FENCE_GATE = new FenceGateBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
	public static final Block MAPLE_DOOR = new ModDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(3.0F).sound(SoundType.WOOD));
	public static final Block MAPLE_TRAPDOOR = new ModTrapDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(3.0F).sound(SoundType.WOOD));

	// Quark
	public static final Block MAPLE_VERTICAL_SLAB = new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
	public static final Block VERTICAL_MAPLE_PLANKS = new Block(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
	public static final Block MAPLE_BOOKSHELF = new BookshelfBlock(Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(1.5F).sound(SoundType.WOOD));
	public static final Block MAPLE_LADDER = new ModLadderBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.4F).sound(SoundType.LADDER));
	public static final Block MAPLE_LEAF_CARPET = new LeafCarpetBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT));

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		registerBlock(SNAIL_SLIME, ItemGroup.MISC, "snail_slime");
		registerBlock(SNAIL_SLIME_BLOCK, ItemGroup.DECORATIONS, "snail_slime_block");
    	registerBlock(PANCAKE, ItemGroup.FOOD, "pancake");

		// Maple Stuff
		registerBlock(MAPLE_LOG, ItemGroup.BUILDING_BLOCKS, "maple_log");
		registerBlock(MAPLE_WOOD, ItemGroup.BUILDING_BLOCKS, "maple_wood");
		registerBlock(STRIPPED_MAPLE_LOG, ItemGroup.BUILDING_BLOCKS, "stripped_maple_log");
		registerBlock(STRIPPED_MAPLE_WOOD, ItemGroup.BUILDING_BLOCKS, "stripped_maple_wood");
		registerBlock(SAPPY_MAPLE_LOG, ItemGroup.BUILDING_BLOCKS, "sappy_maple_log");
		registerBlock(SAPPY_MAPLE_WOOD, ItemGroup.BUILDING_BLOCKS, "sappy_maple_wood");
		
		registerBlock(MAPLE_LEAVES, ItemGroup.DECORATIONS, "maple_leaves");
		registerBlock(YELLOW_MAPLE_LEAVES, ItemGroup.DECORATIONS, "yellow_maple_leaves");
		registerBlock(ORANGE_MAPLE_LEAVES, ItemGroup.DECORATIONS, "orange_maple_leaves");
		registerBlock(RED_MAPLE_LEAVES, ItemGroup.DECORATIONS, "red_maple_leaves");
		
		registerBlock(MAPLE_SAPLING, ItemGroup.DECORATIONS, "maple_sapling");
		registerBlock(YELLOW_MAPLE_SAPLING, ItemGroup.DECORATIONS, "yellow_maple_sapling");
		registerBlock(ORANGE_MAPLE_SAPLING, ItemGroup.DECORATIONS, "orange_maple_sapling");
		registerBlock(RED_MAPLE_SAPLING, ItemGroup.DECORATIONS, "red_maple_sapling");
		
		registerBlock(POTTED_MAPLE_SAPLING, "potted_maple_sapling");
		registerBlock(POTTED_YELLOW_MAPLE_SAPLING, "potted_yellow_maple_sapling");
		registerBlock(POTTED_ORANGE_MAPLE_SAPLING, "potted_orange_maple_sapling");
		registerBlock(POTTED_RED_MAPLE_SAPLING, "potted_red_maple_sapling");

		registerBlock(YELLOW_MAPLE_LEAF_CARPET, ItemGroup.DECORATIONS, "yellow_maple_leaf_carpet");
		registerBlock(ORANGE_MAPLE_LEAF_CARPET, ItemGroup.DECORATIONS, "orange_maple_leaf_carpet");
		registerBlock(RED_MAPLE_LEAF_CARPET, ItemGroup.DECORATIONS, "red_maple_leaf_carpet");
		
		registerBlock(MAPLE_PLANKS, ItemGroup.BUILDING_BLOCKS, "maple_planks");
		registerBlock(MAPLE_STAIRS, ItemGroup.BUILDING_BLOCKS, "maple_stairs");
		registerBlock(MAPLE_SLAB, ItemGroup.BUILDING_BLOCKS, "maple_slab");
		registerBlock(MAPLE_PRESSURE_PLATE, ItemGroup.REDSTONE, "maple_pressure_plate");
		registerBlock(MAPLE_BUTTON, ItemGroup.REDSTONE, "maple_button");
		registerBlock(MAPLE_FENCE, ItemGroup.DECORATIONS, "maple_fence");
		registerBlock(MAPLE_FENCE_GATE, ItemGroup.REDSTONE, "maple_fence_gate");
		registerBlock(MAPLE_DOOR, ItemGroup.REDSTONE, "maple_door");
		registerBlock(MAPLE_TRAPDOOR, ItemGroup.REDSTONE, "maple_trapdoor");
		
		// Quark
		if (Quark.isInstalled())
		{
			registerBlock(MAPLE_VERTICAL_SLAB, ItemGroup.BUILDING_BLOCKS, "maple_vertical_slab", 150);
			registerBlock(VERTICAL_MAPLE_PLANKS, ItemGroup.BUILDING_BLOCKS, "vertical_maple_planks");
			registerBlock(MAPLE_BOOKSHELF, ItemGroup.BUILDING_BLOCKS, "maple_bookshelf", 300);
			registerBlock(MAPLE_LADDER, ItemGroup.DECORATIONS, "maple_ladder", 300);
			registerBlock(MAPLE_LEAF_CARPET, ItemGroup.DECORATIONS, "maple_leaf_carpet");
		}
	}

	private static void registerBlock(Block block, ItemGroup group, String name)
	{
		registerBlock(block, name);
		BlockItem blockItem = new BlockItem(block, new Item.Properties().group(group));
		blockItem.setRegistryName(block.getRegistryName());
		ForgeRegistries.ITEMS.register(blockItem);
	}

	private static void registerBlock(Block block, ItemGroup group, String name, int fuelTimeIn)
	{
		registerBlock(block, name);
		BlockItem blockItem = new FuelItem(block, new Item.Properties().group(group), fuelTimeIn);
		blockItem.setRegistryName(block.getRegistryName());
		ForgeRegistries.ITEMS.register(blockItem);
	}

	private static void registerBlock(Block block, String name)
	{
		block.setRegistryName(Reference.location(name));
		ForgeRegistries.BLOCKS.register(block);
	}
}