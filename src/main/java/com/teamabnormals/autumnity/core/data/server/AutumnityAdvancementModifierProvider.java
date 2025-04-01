package com.teamabnormals.autumnity.core.data.server;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.datapack.AutumnityBiomes;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityMobEffects;
import com.teamabnormals.blueprint.common.advancement.modification.AdvancementModifierProvider;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.CriteriaModifier;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.EffectsChangedModifier;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.autumnity.core.registry.AutumnityItems.*;

public class AutumnityAdvancementModifierProvider extends AdvancementModifierProvider {
	private static final Item[] EDIBLE_ITEMS = new Item[]{SYRUP_BOTTLE.get(), FOUL_BERRIES.get(), FOUL_SOUP.get(), PUMPKIN_BREAD.get(), TURKEY_PIECE.get(), COOKED_TURKEY_PIECE.get()};
	private static final EntityType<?>[] BREEDABLE_ANIMALS = new EntityType[]{AutumnityEntityTypes.SNAIL.get(), AutumnityEntityTypes.TURKEY.get()};

	public AutumnityAdvancementModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Autumnity.MOD_ID, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		this.entry("nether/all_potions").selects("nether/all_potions").addModifier(new EffectsChangedModifier("all_effects", false, MobEffectsPredicate.Builder.effects().and(AutumnityMobEffects.EXTENSION).build().get()));
		this.entry("nether/all_effects").selects("nether/all_effects").addModifier(new EffectsChangedModifier("all_effects", false, MobEffectsPredicate.Builder.effects().and(AutumnityMobEffects.EXTENSION).and(AutumnityMobEffects.FOUL_TASTE).build().get()));

		CriteriaModifier.Builder balancedDiet = CriteriaModifier.builder(this.modId);
		for (Item item : EDIBLE_ITEMS) {
			balancedDiet.addCriterion(BuiltInRegistries.ITEM.getKey(item).getPath(), ConsumeItemTrigger.TriggerInstance.usedItem(item));
		}
		this.entry("husbandry/balanced_diet").selects("husbandry/balanced_diet").addModifier(balancedDiet.requirements(Strategy.AND).build());

		CriteriaModifier.Builder adventuringTime = CriteriaModifier.builder(this.modId);
		RegistryLookup<Biome> biomes = provider.lookupOrThrow(Registries.BIOME);
		for (ResourceKey<Biome> biome : List.of(AutumnityBiomes.MAPLE_FOREST, AutumnityBiomes.PUMPKIN_FIELDS)) {
			adventuringTime.addCriterion(biome.location().toString(), PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(biomes.getOrThrow(biome))));
		}

		this.entry("adventure/adventuring_time").selects("adventure/adventuring_time").addModifier(adventuringTime.requirements(Strategy.AND).build());

		this.entry("husbandry/plant_seed").selects("husbandry/plant_seed").addModifier(CriteriaModifier.builder(this.modId)
				.addCriterion("foul_berry_bush_pips", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(AutumnityBlocks.FOUL_BERRY_BUSH.get()))
				.addIndexedRequirements(0, false, "foul_berry_bush_pips").build());

		CriteriaModifier.Builder breedAllAnimals = CriteriaModifier.builder(this.modId);
		for (EntityType<?> entityType : BREEDABLE_ANIMALS) {
			breedAllAnimals.addCriterion(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath(), BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityType)));
		}
		this.entry("husbandry/bred_all_animals").selects("husbandry/bred_all_animals").addModifier(breedAllAnimals.requirements(Strategy.AND).build());

	}
}