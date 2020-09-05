package com.markus1002.autumnity.common.block.properties;

import net.minecraft.util.IStringSerializable;

public enum CarvedSide implements IStringSerializable
{
	X("x"),
	Z("z");

	private final String name;

	private CarvedSide(String name)
	{
		this.name = name;
	}

	public String toString()
	{
		return this.name;
	}

	public String getString()
	{
		return this.name;
	}
}