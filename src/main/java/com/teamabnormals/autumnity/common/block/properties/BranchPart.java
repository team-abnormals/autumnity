package com.teamabnormals.autumnity.common.block.properties;

import net.minecraft.util.StringRepresentable;

public enum BranchPart implements StringRepresentable {
	TIP,
	BASE;

	public String toString() {
		return this.getSerializedName();
	}

	public String getSerializedName() {
		return this == TIP ? "tip" : "base";
	}
}