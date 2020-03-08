package com.markus1002.autumnity.core.util.compatibility;

import net.minecraftforge.fml.ModList;

public class Quark
{
	public static boolean isInstalled()
	{
		return ModList.get() != null && ModList.get().getModContainerById("quark").isPresent();
	}
}