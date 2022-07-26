package com.teamabnormals.autumnity.common.block.properties;

import net.minecraft.core.Direction.Axis;
import net.minecraft.util.StringRepresentable;

public enum CarvedSide implements StringRepresentable {
	X("x"),
	Z("z");

	private final String name;

	CarvedSide(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}

	public static CarvedSide getCarvedSide(Axis axis) {
		return axis == Axis.X ? CarvedSide.X : CarvedSide.Z;
	}
}