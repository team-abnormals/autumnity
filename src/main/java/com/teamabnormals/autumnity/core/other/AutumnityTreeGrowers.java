package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class AutumnityTreeGrowers {
	public static final TreeGrower MAPLE = new TreeGrower("autumnity:maple", Optional.empty(), Optional.of(AutumnityConfiguredFeatures.MAPLE_TREE), Optional.empty());
	public static final TreeGrower MAPLE_RED = new TreeGrower("autumnity:maple_red", Optional.empty(), Optional.of(AutumnityConfiguredFeatures.MAPLE_TREE_RED), Optional.empty());
	public static final TreeGrower MAPLE_ORANGE = new TreeGrower("autumnity:maple_orange", Optional.empty(), Optional.of(AutumnityConfiguredFeatures.MAPLE_TREE_ORANGE), Optional.empty());
	public static final TreeGrower MAPLE_YELLOW = new TreeGrower("autumnity:maple_yellow", Optional.empty(), Optional.of(AutumnityConfiguredFeatures.MAPLE_TREE_YELLOW), Optional.empty());
}
