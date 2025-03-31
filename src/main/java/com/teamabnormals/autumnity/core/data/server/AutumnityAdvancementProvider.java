package com.teamabnormals.autumnity.core.data.server;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityCriteriaTriggers;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
import net.minecraft.advancements.critereon.PlayerInteractTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.AdvancementProvider.AdvancementGenerator;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AutumnityAdvancementProvider implements AdvancementGenerator {

	public static AdvancementProvider create(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		return new AdvancementProvider(output, provider, helper, List.of(new AutumnityAdvancementProvider()));
	}

	@Override
	public void generate(Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper helper) {
		createAdvancement("sneaky_protection", "husbandry", ResourceLocation.withDefaultNamespace("husbandry/breed_an_animal"), AutumnityItems.SNAIL_SHELL_CHESTPLATE.get(), AdvancementType.TASK, true, true, false)
				.addCriterion("snail_shell_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AutumnityItems.SNAIL_SHELL_CHESTPLATE.get()))
				.save(consumer, Autumnity.MOD_ID + ":husbandry/sneaky_protection");

		createAdvancement("cure_foul_taste", "husbandry", ResourceLocation.withDefaultNamespace("husbandry/plant_seed"), AutumnityItems.FOUL_BERRIES.get(), AdvancementType.TASK, true, true, false)
				.addCriterion("cure_foul_taste", AutumnityCriteriaTriggers.curedFoulTaste())
				.save(consumer, Autumnity.MOD_ID + ":husbandry/cure_foul_taste");

		createAdvancement("fungivore_diet", "husbandry", ResourceLocation.withDefaultNamespace("husbandry/breed_an_animal"), Items.CRIMSON_FUNGUS, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("red_mushroom", feedSnailTrigger(Items.RED_MUSHROOM))
				.addCriterion("brown_mushroom", feedSnailTrigger(Items.BROWN_MUSHROOM))
				.addCriterion("crimson_fungus", feedSnailTrigger(Items.CRIMSON_FUNGUS))
				.addCriterion("warped_fungus", feedSnailTrigger(Items.WARPED_FUNGUS))
				.save(consumer, Autumnity.MOD_ID + ":husbandry/fungivore_diet");
	}

	private static Advancement.Builder createAdvancement(String name, String category, ResourceLocation parent, ItemLike icon, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return Advancement.Builder.advancement().parent(Advancement.Builder.advancement().build(parent)).display(icon,
				Component.translatable("advancements." + Autumnity.MOD_ID + "." + category + "." + name + ".title"),
				Component.translatable("advancements." + Autumnity.MOD_ID + "." + category + "." + name + ".description"),
				null, frame, showToast, announceToChat, hidden);
	}

	public static Criterion<TriggerInstance> feedSnailTrigger(Item item) {
		return PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item().of(item), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(AutumnityEntityTypes.SNAIL.get()))));
	}
}