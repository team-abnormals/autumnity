package com.minecraftabnormals.autumnity.common.advancements.criterion;

import com.google.gson.JsonObject;
import com.minecraftabnormals.autumnity.core.Autumnity;
import net.minecraft.advancements.criterion.*;
import net.minecraft.advancements.criterion.EntityPredicate.AndPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

public class FeedSnailTrigger extends AbstractCriterionTrigger<FeedSnailTrigger.Instance> {
	private static final ResourceLocation ID = new ResourceLocation(Autumnity.MOD_ID, "feed_snail");

	public ResourceLocation getId() {
		return ID;
	}

	public FeedSnailTrigger.Instance createInstance(JsonObject json, AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
		return new FeedSnailTrigger.Instance(entityPredicate, ItemPredicate.fromJson(json.get("item")));
	}

	public void trigger(ServerPlayerEntity player, ItemStack item) {
		this.trigger(player, (p_226325_1_) -> {
			return p_226325_1_.test(item);
		});
	}

	public static class Instance extends CriterionInstance {
		private final ItemPredicate item;

		public Instance(EntityPredicate.AndPredicate p_i231522_1_, ItemPredicate p_i231522_2_) {
			super(FeedSnailTrigger.ID, p_i231522_1_);
			this.item = p_i231522_2_;
		}

		public static FeedSnailTrigger.Instance any() {
			return new FeedSnailTrigger.Instance(EntityPredicate.AndPredicate.ANY, ItemPredicate.ANY);
		}

		public static FeedSnailTrigger.Instance forItem(IItemProvider p_203913_0_) {
			return new FeedSnailTrigger.Instance(EntityPredicate.AndPredicate.ANY, new ItemPredicate(null, p_203913_0_.asItem(), MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NBTPredicate.ANY));
		}

		public boolean test(ItemStack item) {
			return this.item.matches(item);
		}

		public JsonObject serializeToJson(ConditionArraySerializer p_230240_1_) {
			JsonObject jsonobject = super.serializeToJson(p_230240_1_);
			jsonobject.add("item", this.item.serializeToJson());
			return jsonobject;
		}
	}
}