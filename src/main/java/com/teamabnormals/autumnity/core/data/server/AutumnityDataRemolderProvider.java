package com.teamabnormals.autumnity.core.data.server;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.blueprint.common.remolder.data.RemolderProvider;
import com.teamabnormals.blueprint.common.remolder.util.LootRemolders;
import com.teamabnormals.blueprint.core.util.modification.selection.ConditionedResourceSelector;
import com.teamabnormals.blueprint.core.util.modification.selection.selectors.NamesResourceSelector;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

public class AutumnityDataRemolderProvider extends RemolderProvider {

	public AutumnityDataRemolderProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Autumnity.MOD_ID, Target.DATA_PACK, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		this.entry("foul_berry_bush")
				.path(new ConditionedResourceSelector(
						new NamesResourceSelector(Autumnity.location("loot_table/blocks/foul_berry_bush")),
						AutumnityRecipeProvider.BERRY_GOOD_AND_PIPS)
				)
				.remolder(LootRemolders.addPool(LootPool.lootPool()
						.name(Autumnity.MOD_ID + ":foul_berry_pips")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(AutumnityItems.FOUL_BERRY_PIPS.get())).build()));

		this.entry("tall_foul_berry_bush")
				.path(new ConditionedResourceSelector(
						new NamesResourceSelector(Autumnity.location("loot_table/blocks/tall_foul_berry_bush")),
						AutumnityRecipeProvider.BERRY_GOOD_AND_PIPS)
				)
				.remolder(LootRemolders.addPool(LootPool.lootPool()
						.name(Autumnity.MOD_ID + ":foul_berry_pips")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(AutumnityItems.FOUL_BERRY_PIPS.get()))
						.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
						.build()
				));
	}
}