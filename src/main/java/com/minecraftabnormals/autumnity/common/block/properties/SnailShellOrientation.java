package com.minecraftabnormals.autumnity.common.block.properties;

import net.minecraft.util.IStringSerializable;

public enum SnailShellOrientation implements IStringSerializable {
	UP("up"),
	DOWN("down"),
	HORIZONTAL("horizontal");

	private final String name;

	private SnailShellOrientation(String name) {
		this.name = name;
	}

	public String getSerializedName() {
		return this.name;
	}
}