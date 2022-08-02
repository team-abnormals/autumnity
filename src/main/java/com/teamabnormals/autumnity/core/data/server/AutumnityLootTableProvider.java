package com.teamabnormals.autumnity.core.data.server;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.autumnity.common.block.PancakeBlock;
import com.teamabnormals.autumnity.common.block.TallFoulBerryBushBlock;
import com.teamabnormals.autumnity.common.block.TurkeyBlock;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock.VerticalSlabType;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.teamabnormals.autumnity.core.registry.AutumnityBlocks.*;

public class AutumnityLootTableProvider extends LootTableProvider {
	private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> tables = ImmutableList.of(Pair.of(AutumnityBlockLoot::new, LootContextParamSets.BLOCK), Pair.of(AutumnityEntityLoot::new, LootContextParamSets.ENTITY), Pair.of(AutumnityChestLoot::new, LootContextParamSets.CHEST));

	public AutumnityLootTableProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	public List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
		return tables;
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {
	}

	private static class AutumnityBlockLoot extends BlockLoot {
		private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

		@Override
		public void addTables() {
			this.dropSelf(SNAIL_SLIME.get());
			this.dropSelf(SNAIL_SLIME_BLOCK.get());
			this.add(PANCAKE.get(), AutumnityBlockLoot::createPancakeDrops);
			this.dropSelf(AUTUMN_CROCUS.get());
			this.dropPottedContents(POTTED_AUTUMN_CROCUS.get());

			this.add(TURKEY.get(), createTurkeyDrops(TURKEY.get(), AutumnityItems.TURKEY_PIECE.get()));
			this.add(COOKED_TURKEY.get(), createTurkeyDrops(COOKED_TURKEY.get(), AutumnityItems.COOKED_TURKEY_PIECE.get()));
			this.dropSelf(TURKEY_EGG_CRATE.get());

			this.dropSelf(FOUL_BERRY_BUSH_PIPS.get());
			this.add(FOUL_BERRY_BUSH.get(), noDrop());
			this.add(TALL_FOUL_BERRY_BUSH.get(), (block) -> applyExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TALL_FOUL_BERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TallFoulBerryBushBlock.AGE, 3).hasProperty(TallFoulBerryBushBlock.HALF, DoubleBlockHalf.LOWER))).add(LootItem.lootTableItem(AutumnityItems.FOUL_BERRIES.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TALL_FOUL_BERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TallFoulBerryBushBlock.AGE, 2).hasProperty(TallFoulBerryBushBlock.HALF, DoubleBlockHalf.LOWER))).add(LootItem.lootTableItem(AutumnityItems.FOUL_BERRIES.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
			this.dropPottedContents(POTTED_FOUL_BERRIES.get());
			this.dropSelf(FOUL_BERRY_SACK.get());

			this.dropSelf(SOUL_JACK_O_LANTERN.get());
			this.dropSelf(REDSTONE_JACK_O_LANTERN.get());
			this.dropSelf(ENDER_JACK_O_LANTERN.get());
			this.dropSelf(CUPRIC_JACK_O_LANTERN.get());

			this.dropSelf(LARGE_PUMPKIN_SLICE.get());
			this.dropSelf(CARVED_LARGE_PUMPKIN_SLICE.get());
			this.dropSelf(LARGE_JACK_O_LANTERN_SLICE.get());
			this.dropSelf(LARGE_SOUL_JACK_O_LANTERN_SLICE.get());
			this.dropSelf(LARGE_REDSTONE_JACK_O_LANTERN_SLICE.get());
			this.dropSelf(LARGE_ENDER_JACK_O_LANTERN_SLICE.get());
			this.dropSelf(LARGE_CUPRIC_JACK_O_LANTERN_SLICE.get());

			this.dropSelf(SNAIL_SHELL_BLOCK.get());
			this.dropSelf(SNAIL_SHELL_BRICKS.get());
			this.dropSelf(SNAIL_SHELL_BRICK_STAIRS.get());
			this.dropSelf(SNAIL_SHELL_BRICK_WALL.get());
			this.add(SNAIL_SHELL_BRICK_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(SNAIL_SHELL_BRICK_VERTICAL_SLAB.get(), AutumnityBlockLoot::createVerticalSlabItemTable);
			this.dropSelf(CHISELED_SNAIL_SHELL_BRICKS.get());
			this.dropSelf(SNAIL_SHELL_TILES.get());
			this.dropSelf(SNAIL_SHELL_TILE_STAIRS.get());
			this.dropSelf(SNAIL_SHELL_TILE_WALL.get());
			this.add(SNAIL_SHELL_TILE_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(SNAIL_SHELL_TILE_VERTICAL_SLAB.get(), AutumnityBlockLoot::createVerticalSlabItemTable);

			this.dropSelf(MAPLE_PLANKS.get());
			this.dropSelf(VERTICAL_MAPLE_PLANKS.get());
			this.dropSelf(MAPLE_LOG.get());
			this.dropSelf(MAPLE_WOOD.get());
			this.dropSelf(STRIPPED_MAPLE_LOG.get());
			this.dropSelf(STRIPPED_MAPLE_WOOD.get());
			this.dropSelf(SAPPY_MAPLE_LOG.get());
			this.dropSelf(SAPPY_MAPLE_WOOD.get());
			this.dropSelf(MAPLE_SIGN.getFirst().get());
			this.dropSelf(MAPLE_PRESSURE_PLATE.get());
			this.dropSelf(MAPLE_TRAPDOOR.get());
			this.dropSelf(MAPLE_BUTTON.get());
			this.dropSelf(MAPLE_STAIRS.get());
			this.dropSelf(MAPLE_FENCE.get());
			this.dropSelf(MAPLE_FENCE_GATE.get());
			this.dropSelf(MAPLE_BOARDS.get());
			this.dropSelf(MAPLE_POST.get());
			this.dropSelf(STRIPPED_MAPLE_POST.get());
			this.dropSelf(MAPLE_HEDGE.get());
			this.dropSelf(YELLOW_MAPLE_HEDGE.get());
			this.dropSelf(ORANGE_MAPLE_HEDGE.get());
			this.dropSelf(RED_MAPLE_HEDGE.get());
			this.dropSelf(MAPLE_LEAF_CARPET.get());
			this.dropSelf(YELLOW_MAPLE_LEAF_CARPET.get());
			this.dropSelf(ORANGE_MAPLE_LEAF_CARPET.get());
			this.dropSelf(RED_MAPLE_LEAF_CARPET.get());
			this.add(MAPLE_LEAF_PILE.get(), BlockLoot::createGlowLichenDrops);
			this.add(YELLOW_MAPLE_LEAF_PILE.get(), BlockLoot::createGlowLichenDrops);
			this.add(ORANGE_MAPLE_LEAF_PILE.get(), BlockLoot::createGlowLichenDrops);
			this.add(RED_MAPLE_LEAF_PILE.get(), BlockLoot::createGlowLichenDrops);

			this.dropSelf(MAPLE_SAPLING.get());
			this.dropSelf(YELLOW_MAPLE_SAPLING.get());
			this.dropSelf(ORANGE_MAPLE_SAPLING.get());
			this.dropSelf(RED_MAPLE_SAPLING.get());

			this.dropPottedContents(POTTED_MAPLE_SAPLING.get());
			this.dropPottedContents(POTTED_YELLOW_MAPLE_SAPLING.get());
			this.dropPottedContents(POTTED_ORANGE_MAPLE_SAPLING.get());
			this.dropPottedContents(POTTED_RED_MAPLE_SAPLING.get());

			this.dropSelf(MAPLE_LADDER.get());
			this.add(MAPLE_SLAB.get(), BlockLoot::createSlabItemTable);
			this.add(MAPLE_VERTICAL_SLAB.get(), AutumnityBlockLoot::createVerticalSlabItemTable);
			this.add(MAPLE_DOOR.get(), BlockLoot::createDoorTable);
			this.add(MAPLE_BEEHIVE.get(), BlockLoot::createBeeHiveDrop);
			this.add(MAPLE_CHEST.getFirst().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(MAPLE_CHEST.getSecond().get(), BlockLoot::createNameableBlockEntityTable);
			this.add(MAPLE_BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));

			this.add(MAPLE_LEAVES.get(), (block) -> createLeavesDrops(block, MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(YELLOW_MAPLE_LEAVES.get(), (block) -> createLeavesDrops(block, YELLOW_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(ORANGE_MAPLE_LEAVES.get(), (block) -> createLeavesDrops(block, ORANGE_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(RED_MAPLE_LEAVES.get(), (block) -> createLeavesDrops(block, RED_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		}

		protected static LootTable.Builder createVerticalSlabItemTable(Block block) {
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(block, LootItem.lootTableItem(block).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE)))))));
		}

		protected static LootTable.Builder createTurkeyDrops(Block block, Item piece) {
			return LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TurkeyBlock.CHUNKS, 0))), AlternativesEntry.alternatives(LootItem.lootTableItem(piece).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TurkeyBlock.CHUNKS, 4))), LootItem.lootTableItem(piece).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TurkeyBlock.CHUNKS, 3))), LootItem.lootTableItem(piece).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TurkeyBlock.CHUNKS, 2))), LootItem.lootTableItem(piece).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TurkeyBlock.CHUNKS, 1)))))));
		}

		protected static LootTable.Builder createPancakeDrops(Block block) {
			LootPoolSingletonContainer.Builder<?> item = LootItem.lootTableItem(block);
			for (int i = 1; i < 33; i++) {
				item.apply(SetItemCountFunction.setCount(ConstantValue.exactly((int) (i / 2))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PancakeBlock.PANCAKES, i))));
			}
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(block, item)));
		}

		@Override
		public Iterable<Block> getKnownBlocks() {
			return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block.getRegistryName().getNamespace().equals(Autumnity.MOD_ID)).collect(Collectors.toSet());
		}
	}

	private static class AutumnityEntityLoot extends EntityLoot {

		@Override
		public void addTables() {
			this.add(AutumnityEntityTypes.SNAIL.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(SNAIL_SLIME.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			this.add(AutumnityEntityTypes.TURKEY.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.FEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(TURKEY.get()).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
		}

		@Override
		public Iterable<EntityType<?>> getKnownEntities() {
			return ForgeRegistries.ENTITIES.getValues().stream().filter(block -> block.getRegistryName().getNamespace().equals(Autumnity.MOD_ID)).collect(Collectors.toSet());
		}
	}

	private static class AutumnityChestLoot extends ChestLoot {

		@Override
		public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
			consumer.accept(new ResourceLocation(Autumnity.MOD_ID, "maple_hut"), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
							.add(LootItem.lootTableItem(SNAIL_SLIME.get()).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
							.add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))))
					.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 5.0F))
							.add(LootItem.lootTableItem(AutumnityItems.FOUL_BERRIES.get()).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
							.add(LootItem.lootTableItem(Items.SUGAR).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
							.add(LootItem.lootTableItem(Items.WHEAT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
							.add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
							.add(LootItem.lootTableItem(Items.SPIDER_EYE).setWeight(10))
							.add(LootItem.lootTableItem(Items.PUMPKIN).setWeight(5))
							.add(LootItem.lootTableItem(Items.GLOWSTONE_DUST).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
							.add(LootItem.lootTableItem(Items.REDSTONE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
							.add(LootItem.lootTableItem(AutumnityItems.SAP_BOTTLE.get()).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))));
		}
	}
}