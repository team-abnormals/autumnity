package com.markus1002.autumnity.core.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

public class TradeHelper
{
	static class EmeraldForItemsTrade implements VillagerTrades.ITrade
	{
		private final Item field_221183_a;
		private final int field_221184_b;
		private final int field_221185_c;
		private final int field_221186_d;
		private final float field_221187_e;

		public EmeraldForItemsTrade(IItemProvider p_i50539_1_, int p_i50539_2_, int p_i50539_3_, int p_i50539_4_)
		{
			this.field_221183_a = p_i50539_1_.asItem();
			this.field_221184_b = p_i50539_2_;
			this.field_221185_c = p_i50539_3_;
			this.field_221186_d = p_i50539_4_;
			this.field_221187_e = 0.05F;
		}

		public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_)
		{
			ItemStack itemstack = new ItemStack(this.field_221183_a, this.field_221184_b);
			return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.field_221185_c, this.field_221186_d, this.field_221187_e);
		}
	}

	static class ItemsForEmeraldsTrade implements VillagerTrades.ITrade
	{
		private final ItemStack field_221208_a;
		private final int field_221209_b;
		private final int field_221210_c;
		private final int field_221211_d;
		private final int field_221212_e;
		private final float field_221213_f;

		public ItemsForEmeraldsTrade(Block p_i50528_1_, int p_i50528_2_, int p_i50528_3_, int p_i50528_4_, int p_i50528_5_)
		{
			this(new ItemStack(p_i50528_1_), p_i50528_2_, p_i50528_3_, p_i50528_4_, p_i50528_5_);
		}

		public ItemsForEmeraldsTrade(Item p_i50529_1_, int p_i50529_2_, int p_i50529_3_, int p_i50529_4_)
		{
			this(new ItemStack(p_i50529_1_), p_i50529_2_, p_i50529_3_, 12, p_i50529_4_);
		}

		public ItemsForEmeraldsTrade(Item p_i50530_1_, int p_i50530_2_, int p_i50530_3_, int p_i50530_4_, int p_i50530_5_)
		{
			this(new ItemStack(p_i50530_1_), p_i50530_2_, p_i50530_3_, p_i50530_4_, p_i50530_5_);
		}

		public ItemsForEmeraldsTrade(ItemStack p_i50531_1_, int p_i50531_2_, int p_i50531_3_, int p_i50531_4_, int p_i50531_5_)
		{
			this(p_i50531_1_, p_i50531_2_, p_i50531_3_, p_i50531_4_, p_i50531_5_, 0.05F);
		}

		public ItemsForEmeraldsTrade(ItemStack p_i50532_1_, int p_i50532_2_, int p_i50532_3_, int p_i50532_4_, int p_i50532_5_, float p_i50532_6_)
		{
			this.field_221208_a = p_i50532_1_;
			this.field_221209_b = p_i50532_2_;
			this.field_221210_c = p_i50532_3_;
			this.field_221211_d = p_i50532_4_;
			this.field_221212_e = p_i50532_5_;
			this.field_221213_f = p_i50532_6_;
		}

		public MerchantOffer getOffer(Entity p_221182_1_, Random p_221182_2_)
		{
			return new MerchantOffer(new ItemStack(Items.EMERALD, this.field_221209_b), new ItemStack(this.field_221208_a.getItem(), this.field_221210_c), this.field_221211_d, this.field_221212_e, this.field_221213_f);
		}
	}
}
