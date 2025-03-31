package com.teamabnormals.autumnity.core.registry;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class AutumnityCriteriaTriggers {
	public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, Autumnity.MOD_ID);

	public static final DeferredHolder<CriterionTrigger<?>, PlayerTrigger> CURE_FOUL_TASTE = TRIGGERS.register("cure_foul_taste", PlayerTrigger::new);

	public static Criterion<TriggerInstance> curedFoulTaste() {
		return CURE_FOUL_TASTE.get().createCriterion(new PlayerTrigger.TriggerInstance(EntityPredicate.wrap(Optional.empty())));
	}
}