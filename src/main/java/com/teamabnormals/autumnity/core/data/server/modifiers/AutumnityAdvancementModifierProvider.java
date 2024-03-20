package com.teamabnormals.autumnity.core.data.server.modifiers;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.*;
import com.teamabnormals.blueprint.common.advancement.modification.AdvancementModifierProvider;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.CriteriaModifier;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.EffectsChangedModifier;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AutumnityAdvancementModifierProvider extends AdvancementModifierProvider {
	private static final EntityType<?>[] BREEDABLE_ANIMALS = new EntityType[]{AutumnityEntityTypes.SNAIL.get(), AutumnityEntityTypes.TURKEY.get()};

	public AutumnityAdvancementModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Autumnity.MOD_ID, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		this.entry("nether/all_potions").selects("nether/all_potions").addModifier(new EffectsChangedModifier("all_effects", false, MobEffectsPredicate.effects().and(AutumnityMobEffects.EXTENSION.get())));
		this.entry("nether/all_effects").selects("nether/all_effects").addModifier(new EffectsChangedModifier("all_effects", false, MobEffectsPredicate.effects().and(AutumnityMobEffects.EXTENSION.get()).and(AutumnityMobEffects.FOUL_TASTE.get())));

		CriteriaModifier.Builder balancedDiet = CriteriaModifier.builder(this.modId);
		AutumnityItems.HELPER.getDeferredRegister().getEntries().forEach(registryObject -> {
			Item item = registryObject.get();
			if (item.isEdible()) {
				balancedDiet.addCriterion(ForgeRegistries.ITEMS.getKey(item).getPath(), ConsumeItemTrigger.TriggerInstance.usedItem(item));
			}
		});
		this.entry("husbandry/balanced_diet").selects("husbandry/balanced_diet").addModifier(balancedDiet.requirements(RequirementsStrategy.AND).build());

		CriteriaModifier.Builder adventuringTime = CriteriaModifier.builder(this.modId);
		for (ResourceKey<Biome> biome : List.of(AutumnityBiomes.MAPLE_FOREST, AutumnityBiomes.PUMPKIN_FIELDS)) {
			adventuringTime.addCriterion(biome.location().toString(), PlayerTrigger.TriggerInstance.located(LocationPredicate.inBiome(biome)));
		}

		this.entry("adventure/adventuring_time").selects("adventure/adventuring_time").addModifier(adventuringTime.requirements(RequirementsStrategy.AND).build());

		this.entry("husbandry/plant_seed").selects("husbandry/plant_seed").addModifier(CriteriaModifier.builder(this.modId)
				.addCriterion("foul_berry_bush_pips", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(AutumnityBlocks.FOUL_BERRY_BUSH.get()))
				.addIndexedRequirements(0, false, "foul_berry_bush_pips").build());

		CriteriaModifier.Builder breedAllAnimals = CriteriaModifier.builder(this.modId);
		for (EntityType<?> entityType : BREEDABLE_ANIMALS) {
			breedAllAnimals.addCriterion(ForgeRegistries.ENTITY_TYPES.getKey(entityType).getPath(), BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityType)));
		}
		this.entry("husbandry/bred_all_animals").selects("husbandry/bred_all_animals").addModifier(breedAllAnimals.requirements(RequirementsStrategy.AND).build());

	}
}