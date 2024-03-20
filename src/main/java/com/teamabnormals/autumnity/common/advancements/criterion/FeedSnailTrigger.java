package com.teamabnormals.autumnity.common.advancements.criterion;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.teamabnormals.autumnity.common.advancements.criterion.FeedSnailTrigger.TriggerInstance;
import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class FeedSnailTrigger extends SimpleCriterionTrigger<TriggerInstance> {
	private static final ResourceLocation ID = new ResourceLocation(Autumnity.MOD_ID, "feed_snail");

	public ResourceLocation getId() {
		return ID;
	}

	public TriggerInstance createInstance(JsonObject json, ContextAwarePredicate entityPredicate, DeserializationContext conditionsParser) {
		return new TriggerInstance(entityPredicate, ItemPredicate.fromJson(json.get("item")));
	}

	public void trigger(ServerPlayer player, ItemStack item) {
		this.trigger(player, (p_226325_1_) -> {
			return p_226325_1_.test(item);
		});
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {
		private final ItemPredicate item;

		public TriggerInstance(ContextAwarePredicate p_i231522_1_, ItemPredicate p_i231522_2_) {
			super(FeedSnailTrigger.ID, p_i231522_1_);
			this.item = p_i231522_2_;
		}

		public static TriggerInstance any() {
			return new TriggerInstance(ContextAwarePredicate.ANY, ItemPredicate.ANY);
		}

		public static TriggerInstance forItem(ItemLike p_203913_0_) {
			return new TriggerInstance(ContextAwarePredicate.ANY, new ItemPredicate(null, ImmutableSet.of(p_203913_0_.asItem()), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY));
		}

		public boolean test(ItemStack item) {
			return this.item.matches(item);
		}

		public JsonObject serializeToJson(SerializationContext p_230240_1_) {
			JsonObject jsonobject = super.serializeToJson(p_230240_1_);
			jsonobject.add("item", this.item.serializeToJson());
			return jsonobject;
		}
	}
}