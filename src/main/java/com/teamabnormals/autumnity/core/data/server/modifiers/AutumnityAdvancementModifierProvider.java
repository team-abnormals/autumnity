package com.teamabnormals.autumnity.core.data.server.modifiers;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.*;
import com.teamabnormals.blueprint.common.advancement.modification.AdvancementModifierProvider;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.CriteriaModifier;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.EffectsChangedModifier;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class AutumnityAdvancementModifierProvider extends AdvancementModifierProvider {
	private static final EntityType<?>[] BREEDABLE_ANIMALS = new EntityType[]{AutumnityEntityTypes.SNAIL.get(), AutumnityEntityTypes.TURKEY.get()};

	public AutumnityAdvancementModifierProvider(DataGenerator generator) {
		super(generator, Autumnity.MOD_ID);
	}

	@Override
	protected void registerEntries() {
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
		AutumnityBiomes.HELPER.getDeferredRegister().getEntries().forEach(biome -> {
			ResourceLocation key = ForgeRegistries.BIOMES.getKey(biome.get());
			adventuringTime.addCriterion(key.getPath(), LocationTrigger.TriggerInstance.located(LocationPredicate.inBiome(ResourceKey.create(Registry.BIOME_REGISTRY, key))));
		});
		this.entry("adventure/adventuring_time").selects("adventure/adventuring_time").addModifier(adventuringTime.requirements(RequirementsStrategy.AND).build());

		this.entry("husbandry/plant_seed").selects("husbandry/plant_seed").addModifier(CriteriaModifier.builder(this.modId)
				.addCriterion("foul_berry_bush_pips", PlacedBlockTrigger.TriggerInstance.placedBlock(AutumnityBlocks.FOUL_BERRY_BUSH_PIPS.get()))
				.addIndexedRequirements(0, false, "foul_berry_bush_pips").build());

		CriteriaModifier.Builder breedAllAnimals = CriteriaModifier.builder(this.modId);
		for (EntityType<?> entityType : BREEDABLE_ANIMALS) {
			breedAllAnimals.addCriterion(entityType.getRegistryName().getPath(), BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityType)));
		}
		this.entry("husbandry/bred_all_animals").selects("husbandry/bred_all_animals").addModifier(breedAllAnimals.requirements(RequirementsStrategy.AND).build());

	}
}