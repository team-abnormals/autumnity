package com.teamabnormals.autumnity.common.block.properties;

import net.minecraft.util.StringRepresentable;

public enum SnailShellOrientation implements StringRepresentable {
	UP("up"),
	DOWN("down"),
	HORIZONTAL("horizontal");

	private final String name;

	SnailShellOrientation(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}