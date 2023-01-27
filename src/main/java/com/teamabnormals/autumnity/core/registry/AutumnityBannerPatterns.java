package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class AutumnityBannerPatterns {
	public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registry.BANNER_PATTERN_REGISTRY, Autumnity.MOD_ID);

	public static final RegistryObject<BannerPattern> MAPLE_LEAF = BANNER_PATTERNS.register("maple_leaf", () -> new BannerPattern("mpl"));
	public static final RegistryObject<BannerPattern> SWIRL = BANNER_PATTERNS.register("swirl", () -> new BannerPattern("swl"));
}