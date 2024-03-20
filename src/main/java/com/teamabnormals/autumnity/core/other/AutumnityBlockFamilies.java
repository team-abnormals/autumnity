package com.teamabnormals.autumnity.core.other;

import net.minecraft.data.BlockFamily;

import static com.teamabnormals.autumnity.core.registry.AutumnityBlocks.*;

public class AutumnityBlockFamilies {
	public static final BlockFamily MAPLE_PLANKS_FAMILY = new BlockFamily.Builder(MAPLE_PLANKS.get()).button(MAPLE_BUTTON.get()).fence(MAPLE_FENCE.get()).fenceGate(MAPLE_FENCE_GATE.get()).pressurePlate(MAPLE_PRESSURE_PLATE.get()).sign(MAPLE_SIGNS.getFirst().get(), MAPLE_SIGNS.getSecond().get()).slab(MAPLE_SLAB.get()).stairs(MAPLE_STAIRS.get()).door(MAPLE_DOOR.get()).trapdoor(MAPLE_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
	public static final BlockFamily SNAIL_SHELL_BRICKS_FAMILY = new BlockFamily.Builder(SNAIL_SHELL_BRICKS.get()).wall(SNAIL_SHELL_BRICK_WALL.get()).stairs(SNAIL_SHELL_BRICK_STAIRS.get()).slab(SNAIL_SHELL_BRICK_SLAB.get()).chiseled(CHISELED_SNAIL_SHELL_BRICKS.get()).getFamily();
	public static final BlockFamily SNAIL_SHELL_TILES_FAMILY = new BlockFamily.Builder(SNAIL_SHELL_TILES.get()).wall(SNAIL_SHELL_TILE_WALL.get()).stairs(SNAIL_SHELL_TILE_STAIRS.get()).slab(SNAIL_SHELL_TILE_SLAB.get()).getFamily();
}
