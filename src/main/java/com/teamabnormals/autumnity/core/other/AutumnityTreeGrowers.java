package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.core.registry.AutumnityFeatures.AutumnityConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class AutumnityTreeGrowers {
	public static final TreeGrower MAPLE = new TreeGrower("autumnity:maple", Optional.empty(), Optional.of(AutumnityConfiguredFeatures.MAPLE_TREE), Optional.of(AutumnityConfiguredFeatures.MAPLE_TREE_BEES_005));
	public static final TreeGrower MAPLE_YELLOW = new TreeGrower("autumnity:maple_yellow", Optional.empty(), Optional.of(AutumnityConfiguredFeatures.YELLOW_MAPLE_TREE), Optional.of(AutumnityConfiguredFeatures.YELLOW_MAPLE_TREE_BEES_005));
	public static final TreeGrower MAPLE_ORANGE = new TreeGrower("autumnity:maple_orange", Optional.empty(), Optional.of(AutumnityConfiguredFeatures.ORANGE_MAPLE_TREE), Optional.of(AutumnityConfiguredFeatures.ORANGE_MAPLE_TREE_BEES_005));
	public static final TreeGrower MAPLE_RED = new TreeGrower("autumnity:maple_red", Optional.empty(), Optional.of(AutumnityConfiguredFeatures.RED_MAPLE_TREE), Optional.of(AutumnityConfiguredFeatures.RED_MAPLE_TREE_BEES_005));
}
