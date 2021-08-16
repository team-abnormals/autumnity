package com.minecraftabnormals.autumnity.common.block.properties;

import net.minecraft.util.Direction.Axis;
import net.minecraft.util.IStringSerializable;

public enum CarvedSide implements IStringSerializable {
	X("x"),
	Z("z");

	private final String name;

	CarvedSide(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public String getSerializedName() {
		return this.name;
	}

	public static CarvedSide getCarvedSide(Axis axis) {
		return axis == Axis.X ? CarvedSide.X : CarvedSide.Z;
	}
}