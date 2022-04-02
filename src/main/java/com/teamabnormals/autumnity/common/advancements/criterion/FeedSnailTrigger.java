//package com.minecraftabnormals.autumnity.common.advancements.criterion;
//
//import com.google.gson.JsonObject;
//import com.minecraftabnormals.autumnity.core.Autumnity;
//import net.minecraft.advancements.criterion.*;
//import net.minecraft.advancements.critereon.EntityPredicate.Composite;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.advancements.critereon.DeserializationContext;
//import net.minecraft.advancements.critereon.SerializationContext;
//import net.minecraft.world.level.ItemLike;
//import net.minecraft.resources.ResourceLocation;
//
//import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
//import net.minecraft.advancements.critereon.EnchantmentPredicate;
//import net.minecraft.advancements.critereon.EntityPredicate;
//import net.minecraft.advancements.critereon.ItemPredicate;
//import net.minecraft.advancements.critereon.MinMaxBounds;
//import net.minecraft.advancements.critereon.NbtPredicate;
//import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
//
//public class FeedSnailTrigger extends SimpleCriterionTrigger<FeedSnailTrigger.Instance> {
//	private static final ResourceLocation ID = new ResourceLocation(Autumnity.MOD_ID, "feed_snail");
//
//	public ResourceLocation getId() {
//		return ID;
//	}
//
//	public FeedSnailTrigger.Instance createInstance(JsonObject json, Composite entityPredicate, DeserializationContext conditionsParser) {
//		return new FeedSnailTrigger.Instance(entityPredicate, ItemPredicate.fromJson(json.get("item")));
//	}
//
//	public void trigger(ServerPlayer player, ItemStack item) {
//		this.trigger(player, (p_226325_1_) -> {
//			return p_226325_1_.test(item);
//		});
//	}
//
//	public static class Instance extends AbstractCriterionTriggerInstance {
//		private final ItemPredicate item;
//
//		public Instance(EntityPredicate.Composite p_i231522_1_, ItemPredicate p_i231522_2_) {
//			super(FeedSnailTrigger.ID, p_i231522_1_);
//			this.item = p_i231522_2_;
//		}
//
//		public static FeedSnailTrigger.Instance any() {
//			return new FeedSnailTrigger.Instance(EntityPredicate.Composite.ANY, ItemPredicate.ANY);
//		}
//
//		public static FeedSnailTrigger.Instance forItem(ItemLike p_203913_0_) {
//			return new FeedSnailTrigger.Instance(EntityPredicate.Composite.ANY, new ItemPredicate(null, p_203913_0_.asItem(), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY));
//		}
//
//		public boolean test(ItemStack item) {
//			return this.item.matches(item);
//		}
//
//		public JsonObject serializeToJson(SerializationContext p_230240_1_) {
//			JsonObject jsonobject = super.serializeToJson(p_230240_1_);
//			jsonobject.add("item", this.item.serializeToJson());
//			return jsonobject;
//		}
//	}
//}