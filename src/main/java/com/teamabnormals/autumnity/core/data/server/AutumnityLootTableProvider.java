package com.teamabnormals.autumnity.core.data.server;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.autumnity.common.block.PancakeBlock;
import com.teamabnormals.autumnity.common.block.TallFoulBerryBushBlock;
import com.teamabnormals.autumnity.common.block.TurkeyBlock;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.teamabnormals.autumnity.core.registry.AutumnityBlocks.*;

public class AutumnityLootTableProvider extends LootTableProvider {

	public AutumnityLootTableProvider(PackOutput output) {
		super(output, BuiltInLootTables.all(), ImmutableList.of(
				new LootTableProvider.SubProviderEntry(AutumnityBlockLoot::new, LootContextParamSets.BLOCK),
				new LootTableProvider.SubProviderEntry(AutumnityEntityLoot::new, LootContextParamSets.ENTITY),
				new LootTableProvider.SubProviderEntry(AutumnityChestLoot::new, LootContextParamSets.CHEST)
		));
	}


	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {
	}

	private static class AutumnityBlockLoot extends BlockLootSubProvider {
		private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.PIGLIN_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(Collectors.toSet());
		private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

		protected AutumnityBlockLoot() {
			super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
		}

		@Override
		public void generate() {
			this.dropSelf(SNAIL_GOO.get());
			this.dropSelf(SNAIL_GOO_BLOCK.get());
			this.add(PANCAKE.get(), this::createPancakeDrops);
			this.dropSelf(AUTUMN_CROCUS.get());
			this.dropPottedContents(POTTED_AUTUMN_CROCUS.get());

			this.add(TURKEY.get(), createTurkeyDrops(TURKEY.get(), AutumnityItems.TURKEY_PIECE.get()));
			this.add(COOKED_TURKEY.get(), createTurkeyDrops(COOKED_TURKEY.get(), AutumnityItems.COOKED_TURKEY_PIECE.get()));
			this.dropSelf(TURKEY_EGG_CRATE.get());

			this.add(FOUL_BERRY_BUSH.get(), noDrop());
			this.add(TALL_FOUL_BERRY_BUSH.get(), (block) -> applyExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TALL_FOUL_BERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TallFoulBerryBushBlock.AGE, 3).hasProperty(TallFoulBerryBushBlock.HALF, DoubleBlockHalf.LOWER))).add(LootItem.lootTableItem(AutumnityItems.FOUL_BERRIES.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 4.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TALL_FOUL_BERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TallFoulBerryBushBlock.AGE, 2).hasProperty(TallFoulBerryBushBlock.HALF, DoubleBlockHalf.LOWER))).add(LootItem.lootTableItem(AutumnityItems.FOUL_BERRIES.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
			this.dropPottedContents(POTTED_FOUL_BERRIES.get());
			this.dropSelf(FOUL_BERRY_BASKET.get());

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
			this.add(SNAIL_SHELL_BRICK_SLAB.get(), this::createSlabItemTable);
			this.dropSelf(CHISELED_SNAIL_SHELL_BRICKS.get());
			this.dropSelf(SNAIL_SHELL_TILES.get());
			this.dropSelf(SNAIL_SHELL_TILE_STAIRS.get());
			this.dropSelf(SNAIL_SHELL_TILE_WALL.get());
			this.add(SNAIL_SHELL_TILE_SLAB.get(), this::createSlabItemTable);

			this.dropSelf(MAPLE_PLANKS.get());
			this.dropSelf(MAPLE_LOG.get());
			this.dropSelf(MAPLE_WOOD.get());
			this.dropSelf(STRIPPED_MAPLE_LOG.get());
			this.dropSelf(STRIPPED_MAPLE_WOOD.get());
			this.dropSelf(SAPPY_MAPLE_LOG.get());
			this.dropSelf(SAPPY_MAPLE_WOOD.get());
			this.dropSelf(MAPLE_SIGNS.getFirst().get());
			this.dropSelf(MAPLE_HANGING_SIGNS.getFirst().get());
			this.dropSelf(MAPLE_PRESSURE_PLATE.get());
			this.dropSelf(MAPLE_TRAPDOOR.get());
			this.dropSelf(MAPLE_BUTTON.get());
			this.dropSelf(MAPLE_STAIRS.get());
			this.dropSelf(MAPLE_FENCE.get());
			this.dropSelf(MAPLE_FENCE_GATE.get());
			this.dropSelf(MAPLE_BOARDS.get());
			this.add(MAPLE_LEAF_PILE.get(), this::createLeafPileDrops);
			this.add(YELLOW_MAPLE_LEAF_PILE.get(), this::createLeafPileDrops);
			this.add(ORANGE_MAPLE_LEAF_PILE.get(), this::createLeafPileDrops);
			this.add(RED_MAPLE_LEAF_PILE.get(), this::createLeafPileDrops);

			this.dropSelf(MAPLE_SAPLING.get());
			this.dropSelf(YELLOW_MAPLE_SAPLING.get());
			this.dropSelf(ORANGE_MAPLE_SAPLING.get());
			this.dropSelf(RED_MAPLE_SAPLING.get());

			this.dropPottedContents(POTTED_MAPLE_SAPLING.get());
			this.dropPottedContents(POTTED_YELLOW_MAPLE_SAPLING.get());
			this.dropPottedContents(POTTED_ORANGE_MAPLE_SAPLING.get());
			this.dropPottedContents(POTTED_RED_MAPLE_SAPLING.get());

			this.dropSelf(MAPLE_LADDER.get());
			this.add(MAPLE_SLAB.get(), this::createSlabItemTable);
			this.add(MAPLE_DOOR.get(), this::createDoorTable);
			this.add(MAPLE_BEEHIVE.get(), BlockLootSubProvider::createBeeHiveDrop);
			this.add(MAPLE_CHEST.get(), this::createNameableBlockEntityTable);
			this.add(MAPLE_TRAPPED_CHEST.get(), this::createNameableBlockEntityTable);
			this.add(MAPLE_BOOKSHELF.get(), (block) -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));

			this.add(MAPLE_LEAVES.get(), (block) -> createLeavesDrops(block, MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(YELLOW_MAPLE_LEAVES.get(), (block) -> createLeavesDrops(block, YELLOW_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(ORANGE_MAPLE_LEAVES.get(), (block) -> createLeavesDrops(block, ORANGE_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
			this.add(RED_MAPLE_LEAVES.get(), (block) -> createLeavesDrops(block, RED_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		}

		protected LootTable.Builder createLeafPileDrops(Block block) {
			return createMultifaceBlockDrops(block, MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS)));
		}


		protected static LootTable.Builder createTurkeyDrops(Block block, Item piece) {
			return LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TurkeyBlock.CHUNKS, 0))), AlternativesEntry.alternatives(LootItem.lootTableItem(piece).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TurkeyBlock.CHUNKS, 4))), LootItem.lootTableItem(piece).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TurkeyBlock.CHUNKS, 3))), LootItem.lootTableItem(piece).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TurkeyBlock.CHUNKS, 2))), LootItem.lootTableItem(piece).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TurkeyBlock.CHUNKS, 1)))))));
		}

		protected LootTable.Builder createPancakeDrops(Block block) {
			LootPoolSingletonContainer.Builder<?> item = LootItem.lootTableItem(block);
			for (int i = 1; i < 33; i++) {
				item.apply(SetItemCountFunction.setCount(ConstantValue.exactly((int) (i / 2))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PancakeBlock.PANCAKES, i))));
			}
			return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(block, item)));
		}

		@Override
		public Iterable<Block> getKnownBlocks() {
			return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(Autumnity.MOD_ID)).collect(Collectors.toSet());
		}
	}

	private static class AutumnityEntityLoot extends EntityLootSubProvider {

		protected AutumnityEntityLoot() {
			super(FeatureFlags.REGISTRY.allFlags());
		}

		@Override
		public void generate() {
			this.add(AutumnityEntityTypes.SNAIL.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(SNAIL_GOO.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
			this.add(AutumnityEntityTypes.TURKEY.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.FEATHER).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(TURKEY.get()).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
		}

		@Override
		public Stream<EntityType<?>> getKnownEntityTypes() {
			return ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(entity -> ForgeRegistries.ENTITY_TYPES.getKey(entity).getNamespace().equals(Autumnity.MOD_ID));
		}
	}

	private static class AutumnityChestLoot implements LootTableSubProvider {

		@Override
		public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
			consumer.accept(new ResourceLocation(Autumnity.MOD_ID, "chests/maple_hut"), LootTable.lootTable()
					.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
							.add(LootItem.lootTableItem(SNAIL_GOO.get()).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
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