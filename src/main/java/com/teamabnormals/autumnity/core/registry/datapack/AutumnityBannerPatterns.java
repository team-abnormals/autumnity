package com.teamabnormals.autumnity.core.registry.datapack;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public final class AutumnityBannerPatterns {
	public static final ResourceKey<BannerPattern> MAPLE_LEAF = create("maple_leaf");
	public static final ResourceKey<BannerPattern> SWIRL = create("swirl");

	public static void bootstrap(BootstrapContext<BannerPattern> context) {
		register(context, MAPLE_LEAF);
		register(context, SWIRL);
	}

	private static ResourceKey<BannerPattern> create(String name) {
		return ResourceKey.create(Registries.BANNER_PATTERN, Autumnity.location(name));
	}

	public static void register(BootstrapContext<BannerPattern> context, ResourceKey<BannerPattern> resourceKey) {
		context.register(resourceKey, new BannerPattern(resourceKey.location(), "block.minecraft.banner." + resourceKey.location().toShortLanguageKey()));
	}
}