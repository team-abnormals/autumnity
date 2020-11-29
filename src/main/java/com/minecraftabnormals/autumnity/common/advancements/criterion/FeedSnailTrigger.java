package com.minecraftabnormals.autumnity.common.advancements.criterion;

import com.google.gson.JsonObject;
import com.minecraftabnormals.autumnity.core.Reference;

import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.potion.Potion;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class FeedSnailTrigger extends AbstractCriterionTrigger<FeedSnailTrigger.Instance>
{
	private static final ResourceLocation ID = Reference.location("feed_snail");

	public ResourceLocation getId()
	{
		return ID;
	}

	public FeedSnailTrigger.Instance deserializeTrigger(JsonObject json, AndPredicate entityPredicate, ConditionArrayParser conditionsParser)
	{
		return new FeedSnailTrigger.Instance(entityPredicate, ItemPredicate.deserialize(json.get("item")));
	}

	public void trigger(ServerPlayerEntity player, ItemStack item)
	{
		this.triggerListeners(player, (p_226325_1_) -> {
			return p_226325_1_.test(item);
		});
	}

	public static class Instance extends CriterionInstance
	{
		private final ItemPredicate item;

		public Instance(EntityPredicate.AndPredicate p_i231522_1_, ItemPredicate p_i231522_2_)
		{
			super(FeedSnailTrigger.ID, p_i231522_1_);
			this.item = p_i231522_2_;
		}

		public static FeedSnailTrigger.Instance any()
		{
			return new FeedSnailTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, ItemPredicate.ANY);
		}

		public static FeedSnailTrigger.Instance forItem(IItemProvider p_203913_0_)
		{
			return new FeedSnailTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, new ItemPredicate((ITag<Item>)null, p_203913_0_.asItem(), MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, EnchantmentPredicate.enchantments, EnchantmentPredicate.enchantments, (Potion)null, NBTPredicate.ANY));
		}

		public boolean test(ItemStack item)
		{
			return this.item.test(item);
		}

		public JsonObject func_230240_a_(ConditionArraySerializer p_230240_1_)
		{
			JsonObject jsonobject = super.serialize(p_230240_1_);
			jsonobject.add("item", this.item.serialize());
			return jsonobject;
		}
	}
}