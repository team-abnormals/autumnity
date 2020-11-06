package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.common.block.properties.CarvedSide;
import com.minecraftabnormals.autumnity.common.block.properties.SnailShellOrientation;

import net.minecraft.state.EnumProperty;

public class AutumnityBlockStateProperties
{
	public static final EnumProperty<CarvedSide> CARVED_SIDE = EnumProperty.create("carved_side", CarvedSide.class);
	public static final EnumProperty<SnailShellOrientation> ORIENTATION = EnumProperty.create("orientation", SnailShellOrientation.class);
}