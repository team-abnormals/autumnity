package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.abnormals_core.core.api.banner.BannerManager;
import net.minecraft.tileentity.BannerPattern;

public class AutumnityBanners
{
	public static final BannerPattern MAPLE_LEAF = BannerManager.createPattern("mca", "maple_leaf", "mpl");
	public static final BannerPattern SWIRL = BannerManager.createPattern("mca", "swirl", "swl");
	
	public static void registerBanners()
	{
		BannerManager.addPattern(MAPLE_LEAF, AutumnityItems.MAPLE_LEAF_BANNER_PATTERN.get());
		BannerManager.addPattern(SWIRL, AutumnityItems.SWIRL_BANNER_PATTERN.get());
	}
}