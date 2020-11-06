package com.minecraftabnormals.autumnity.core.registry;

import com.teamabnormals.abnormals_core.core.library.api.BannerManager;

import net.minecraft.tileentity.BannerPattern;

public class AutumnityBanners
{
	public static final BannerPattern MAPLE_LEAF = BannerManager.createPattern("mca", "maple_leaf", "mpl");
	
	public static void registerBanners()
	{
		BannerManager.addPattern(MAPLE_LEAF, AutumnityItems.MAPLE_LEAF_BANNNER_PATTERN.get());
	}
}