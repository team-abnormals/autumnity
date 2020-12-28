package com.minecraftabnormals.autumnity.core.other;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import java.util.function.ToIntFunction;

public class AutumnityProperties {
	public static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
		return (p_235421_1_) -> p_235421_1_.get(BlockStateProperties.LIT) ? lightValue : 0;
	}

	public static ToIntFunction<BlockState> getMaxLightValue() {
		return (state) -> 15;
	}

	public static Block.Properties createLeaves(MaterialColor color) {
		return Block.Properties.create(Material.LEAVES, color).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid().harvestTool(ToolType.HOE).setAllowsSpawn(AutumnityProperties::allowsSpawnOnLeaves).setSuffocates(AutumnityProperties::isntSolid).setBlocksVision(AutumnityProperties::isntSolid);
	}

	public static Block.Properties createSapling(MaterialColor color) {
		return Block.Properties.create(Material.PLANTS, color).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT);
	}

	public static Block.Properties createLeafCarpet(MaterialColor color) {
		return Block.Properties.create(Material.CARPET, color).notSolid().hardnessAndResistance(0.0F).tickRandomly().sound(SoundType.PLANT).harvestTool(ToolType.HOE);
	}

	private static Boolean allowsSpawnOnLeaves(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
		return entity == EntityType.OCELOT || entity == EntityType.PARROT;
	}

	private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
		return false;
	}

	public static final Block.Properties FOUL_BERRIES = Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH);
	public static final Block.Properties MAPLE_LOG = Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F).sound(SoundType.WOOD);
	public static final Block.Properties MAPLE_BRANCH = Block.Properties.create(Material.PLANTS, MaterialColor.WHITE_TERRACOTTA).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.WOOD);
	public static final Block.Properties MAPLE = Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final Block.Properties MAPLE_DOOR = Block.Properties.create(Material.WOOD, MaterialColor.ORANGE_TERRACOTTA).notSolid().hardnessAndResistance(3.0F).sound(SoundType.WOOD);
	public static final Block.Properties SNAIL_SHELL = Block.Properties.create(Material.ROCK, MaterialColor.BROWN_TERRACOTTA).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3.0F, 9.0F);
}