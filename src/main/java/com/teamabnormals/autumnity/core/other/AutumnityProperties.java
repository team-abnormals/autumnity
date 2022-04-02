package com.teamabnormals.autumnity.core.other;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.ToIntFunction;

public class AutumnityProperties {
	public static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
		return (p_235421_1_) -> p_235421_1_.getValue(BlockStateProperties.LIT) ? lightValue : 0;
	}

	public static ToIntFunction<BlockState> getMaxLightValue() {
		return (state) -> 15;
	}

	public static ToIntFunction<BlockState> getLowerLightValue() {
		return (state) -> 10;
	}

	public static Block.Properties createLeaves(MaterialColor color) {
		return Block.Properties.of(Material.LEAVES, color).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AutumnityProperties::allowsSpawnOnLeaves).isSuffocating(AutumnityProperties::isntSolid).isViewBlocking(AutumnityProperties::isntSolid);
	}

	public static Block.Properties createSapling(MaterialColor color) {
		return Block.Properties.of(Material.PLANT, color).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS);
	}

	public static Block.Properties createLeafCarpet(MaterialColor color) {
		return Block.Properties.of(Material.CLOTH_DECORATION, color).noOcclusion().strength(0.0F).randomTicks().sound(SoundType.GRASS);
	}

	private static Boolean allowsSpawnOnLeaves(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
		return entity == EntityType.OCELOT || entity == EntityType.PARROT;
	}

	private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
		return false;
	}

	public static final Block.Properties FOUL_BERRIES = Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH);
	public static final Block.Properties MAPLE_LOG = Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_ORANGE).strength(2.0F).sound(SoundType.WOOD);
	public static final Block.Properties MAPLE_BRANCH = Block.Properties.of(Material.PLANT, MaterialColor.TERRACOTTA_WHITE).noCollission().randomTicks().instabreak().sound(SoundType.WOOD);
	public static final Block.Properties MAPLE = Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_ORANGE).strength(2.0F, 3.0F).sound(SoundType.WOOD);
	public static final Block.Properties MAPLE_DOOR = Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_ORANGE).noOcclusion().strength(3.0F).sound(SoundType.WOOD);
	public static final Block.Properties SNAIL_SHELL = Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().strength(3.0F, 9.0F);
}