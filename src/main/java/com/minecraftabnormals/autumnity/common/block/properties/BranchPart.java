package com.minecraftabnormals.autumnity.common.block.properties;

import net.minecraft.util.IStringSerializable;

public enum BranchPart implements IStringSerializable
{
   TIP,
   BASE;

   public String toString()
   {
      return this.getString();
   }

   public String getString()
   {
      return this == TIP ? "tip" : "base";
   }
}