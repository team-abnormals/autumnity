package com.teamabnormals.autumnity.core.data.server.modifiers;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.data.server.AutumnityRecipeProvider;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.blueprint.common.loot.modification.LootModifierProvider;
import com.teamabnormals.blueprint.common.loot.modification.modifiers.LootPoolsModifier;
import com.teamabnormals.blueprint.core.util.modification.selection.ConditionedResourceSelector;
import com.teamabnormals.blueprint.core.util.modification.selection.selectors.NamesResourceSelector;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AutumnityLootModifierProvider extends LootModifierProvider {

	public AutumnityLootModifierProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(Autumnity.MOD_ID, output, lookupProvider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		this.entry("foul_berry_bush")
				.selector(new ConditionedResourceSelector(
						new NamesResourceSelector(new ResourceLocation(Autumnity.MOD_ID, "blocks/foul_berry_bush")),
						AutumnityRecipeProvider.BERRY_GOOD_AND_PIPS)
				)
				.addModifier(new LootPoolsModifier(List.of(LootPool.lootPool()
						.name(Autumnity.MOD_ID + ":foul_berry_pips")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(AutumnityItems.FOUL_BERRY_PIPS.get())).build()), false));

		this.entry("tall_foul_berry_bush")
				.selector(new ConditionedResourceSelector(
						new NamesResourceSelector(new ResourceLocation(Autumnity.MOD_ID, "blocks/tall_foul_berry_bush")),
						AutumnityRecipeProvider.BERRY_GOOD_AND_PIPS)
				)
				.addModifier(new LootPoolsModifier(List.of(LootPool.lootPool()
						.name(Autumnity.MOD_ID + ":foul_berry_pips")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(AutumnityItems.FOUL_BERRY_PIPS.get()))
						.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
						.build()
				), false));
	}
}