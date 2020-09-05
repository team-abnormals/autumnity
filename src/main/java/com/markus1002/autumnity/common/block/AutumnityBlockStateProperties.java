package com.markus1002.autumnity.common.block;

import com.markus1002.autumnity.common.block.properties.CarvedSide;

import net.minecraft.state.EnumProperty;

public class AutumnityBlockStateProperties
{
	public static final EnumProperty<CarvedSide> CARVED_SIDE = EnumProperty.create("carved_side", CarvedSide.class);
}