package com.teamabnormals.autumnity.core.data.client;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock;
import com.teamabnormals.blueprint.common.block.VerticalSlabBlock.VerticalSlabType;
import com.teamabnormals.blueprint.common.block.chest.BlueprintChestBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintTrappedChestBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintStandingSignBlock;
import com.teamabnormals.blueprint.common.block.sign.BlueprintWallSignBlock;
import com.teamabnormals.blueprint.core.Blueprint;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class AutumnityBlockStateProvider extends BlockStateProvider {

	public AutumnityBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Autumnity.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.registerBlockWithVariants(AutumnityBlocks.MAPLE_PLANKS.get(), AutumnityBlocks.MAPLE_STAIRS.get(), AutumnityBlocks.MAPLE_SLAB.get(), AutumnityBlocks.MAPLE_VERTICAL_SLAB.get());
		this.registerLogBlocks(AutumnityBlocks.MAPLE_LOG.get(), AutumnityBlocks.MAPLE_WOOD.get());
		this.registerLogBlocks(AutumnityBlocks.STRIPPED_MAPLE_LOG.get(), AutumnityBlocks.STRIPPED_MAPLE_WOOD.get());
		this.registerLogBlocks(AutumnityBlocks.SAPPY_MAPLE_LOG.get(), AutumnityBlocks.SAPPY_MAPLE_WOOD.get());
		this.registerFenceBlocks(AutumnityBlocks.MAPLE_PLANKS.get(), AutumnityBlocks.MAPLE_FENCE.get(), AutumnityBlocks.MAPLE_FENCE_GATE.get());
		this.registerDoorBlocks(AutumnityBlocks.MAPLE_DOOR.get(), AutumnityBlocks.MAPLE_TRAPDOOR.get());
		this.registerSignBlocks(AutumnityBlocks.MAPLE_PLANKS.get(), AutumnityBlocks.MAPLE_SIGN);
		this.registerButton(AutumnityBlocks.MAPLE_PLANKS.get(), AutumnityBlocks.MAPLE_BUTTON.get());
		this.registerPressurePlate(AutumnityBlocks.MAPLE_PLANKS.get(), AutumnityBlocks.MAPLE_PRESSURE_PLATE.get());

		this.registerWoodworksCompat(AutumnityBlocks.MAPLE_PLANKS.get(), AutumnityBlocks.MAPLE_BOARDS.get(), AutumnityBlocks.MAPLE_LADDER.get(), AutumnityBlocks.MAPLE_BOOKSHELF.get(), AutumnityBlocks.MAPLE_BEEHIVE.get());
		this.leaves(AutumnityBlocks.MAPLE_LEAVES.get(), AutumnityBlocks.MAPLE_LEAF_PILE.get());
		this.leaves(AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), AutumnityBlocks.YELLOW_MAPLE_LEAF_PILE.get());
		this.leaves(AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), AutumnityBlocks.ORANGE_MAPLE_LEAF_PILE.get());
		this.leaves(AutumnityBlocks.RED_MAPLE_LEAVES.get(), AutumnityBlocks.RED_MAPLE_LEAF_PILE.get());
		this.registerChestBlocks(AutumnityBlocks.MAPLE_PLANKS.get(), AutumnityBlocks.MAPLE_CHEST);

		this.pottedCross(AutumnityBlocks.MAPLE_SAPLING.get(), AutumnityBlocks.POTTED_MAPLE_SAPLING.get());
		this.pottedCross(AutumnityBlocks.YELLOW_MAPLE_SAPLING.get(), AutumnityBlocks.POTTED_YELLOW_MAPLE_SAPLING.get());
		this.pottedCross(AutumnityBlocks.ORANGE_MAPLE_SAPLING.get(), AutumnityBlocks.POTTED_ORANGE_MAPLE_SAPLING.get());
		this.pottedCross(AutumnityBlocks.RED_MAPLE_SAPLING.get(), AutumnityBlocks.POTTED_RED_MAPLE_SAPLING.get());

		this.registerVerticalPlanks(AutumnityBlocks.MAPLE_PLANKS.get(), AutumnityBlocks.VERTICAL_MAPLE_PLANKS.get());
		this.registerQuarkLeavesCompat(AutumnityBlocks.MAPLE_LEAVES.get(), AutumnityBlocks.MAPLE_LEAF_CARPET.get(), AutumnityBlocks.MAPLE_LOG.get(), AutumnityBlocks.MAPLE_HEDGE.get());
		this.registerQuarkLeavesCompat(AutumnityBlocks.YELLOW_MAPLE_LEAVES.get(), AutumnityBlocks.YELLOW_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.MAPLE_LOG.get(), AutumnityBlocks.YELLOW_MAPLE_HEDGE.get());
		this.registerQuarkLeavesCompat(AutumnityBlocks.ORANGE_MAPLE_LEAVES.get(), AutumnityBlocks.ORANGE_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.MAPLE_LOG.get(), AutumnityBlocks.ORANGE_MAPLE_HEDGE.get());
		this.registerQuarkLeavesCompat(AutumnityBlocks.RED_MAPLE_LEAVES.get(), AutumnityBlocks.RED_MAPLE_LEAF_CARPET.get(), AutumnityBlocks.MAPLE_LOG.get(), AutumnityBlocks.RED_MAPLE_HEDGE.get());
		this.registerPost(AutumnityBlocks.MAPLE_LOG.get(), AutumnityBlocks.MAPLE_POST.get());
		this.registerPost(AutumnityBlocks.STRIPPED_MAPLE_LOG.get(), AutumnityBlocks.STRIPPED_MAPLE_POST.get());
	}

	public void simpleBlockWithItem(Block block) {
		ModelFile model = cubeAll(block);
		simpleBlock(block, model);
		simpleBlockItem(block, model);
	}

	public void registerItemModel(Block block) {
		this.simpleBlockItem(block, new ExistingModelFile(blockTexture(block), this.models().existingFileHelper));
	}

	private void generatedItem(ItemLike item, String type) {
		generatedItem(item, item, type);
	}

	private void generatedItem(ItemLike item, ItemLike texture, String type) {
		itemModels().withExistingParent(item.asItem().getRegistryName().getPath(), "item/generated").texture("layer0", new ResourceLocation(texture.asItem().getRegistryName().getNamespace(), type + "/" + texture.asItem().getRegistryName().getPath()));
	}

	public void registerFenceBlocks(Block block, Block fence, Block fenceGate) {
		this.fenceBlock((FenceBlock) fence, blockTexture(block));
		this.itemModels().getBuilder(name(fence)).parent(this.models().fenceInventory(name(fence) + "_inventory", blockTexture(block)));
		this.fenceGateBlock((FenceGateBlock) fenceGate, blockTexture(block));
		this.registerItemModel(fenceGate);
	}

	public void pottedCross(Block cross, Block flowerPot) {
		this.simpleBlock(cross, models().cross(name(cross), blockTexture(cross)));
		this.generatedItem(cross, "block");

		this.simpleBlock(flowerPot, models().singleTexture(name(flowerPot), new ResourceLocation("block/flower_pot_cross"), "plant", blockTexture(cross)));
	}

	public void registerWoodworksCompat(Block block, Block boards, Block ladder, Block bookshelf, Block beehive) {
		this.horizontalBlock(ladder, models().withExistingParent(name(ladder), "block/ladder").texture("particle", blockTexture(ladder)).texture("texture", blockTexture(ladder)));
		this.generatedItem(ladder, "block");
		this.simpleBlock(bookshelf, this.models().cubeColumn(name(bookshelf), blockTexture(bookshelf), blockTexture(block)));
		this.registerItemModel(bookshelf);
		this.registerBeehive(beehive);
		this.boardsBlock(boards);
		this.registerItemModel(boards);
	}

	public void boardsBlock(Block boards) {
		ModelFile boardsModel = models().getBuilder(name(boards)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/template_boards"))).texture("all", blockTexture(boards));
		ModelFile boardsHorizontalModel = models().getBuilder(name(boards) + "_horizontal").parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/template_boards_horizontal"))).texture("all", blockTexture(boards));
		this.getVariantBuilder(boards).partialState().with(RotatedPillarBlock.AXIS, Axis.Y).modelForState().modelFile(boardsModel).addModel().partialState().with(RotatedPillarBlock.AXIS, Axis.Z).modelForState().modelFile(boardsHorizontalModel).addModel().partialState().with(RotatedPillarBlock.AXIS, Axis.X).modelForState().modelFile(boardsHorizontalModel).rotationY(270).addModel();
		this.simpleBlockItem(boards);
	}

	public void simpleBlockItem(Block block) {
		this.simpleBlockItem(block, new ExistingModelFile(blockTexture(block), this.models().existingFileHelper));
	}

	public void registerVerticalPlanks(Block block, Block verticalPlanks) {
		this.simpleBlock(verticalPlanks, models().getBuilder(name(verticalPlanks)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/vertical_planks"))).texture("all", blockTexture(block)));
		this.registerItemModel(verticalPlanks);
	}

	public void registerBeehive(Block block) {
		ModelFile beehive = models().orientableWithBottom(name(block), suffix(blockTexture(block), "_side"), suffix(blockTexture(block), "_front"), suffix(blockTexture(block), "_end"), suffix(blockTexture(block), "_end")).texture("particle", suffix(blockTexture(block), "_side"));
		ModelFile beehiveHoney = models().orientableWithBottom(name(block) + "_honey", suffix(blockTexture(block), "_side"), suffix(blockTexture(block), "_front_honey"), suffix(blockTexture(block), "_end"), suffix(blockTexture(block), "_end")).texture("particle", suffix(blockTexture(block), "_side"));
		this.horizontalBlock(block, (state -> state.getValue(BlockStateProperties.LEVEL_HONEY) == 5 ? beehiveHoney : beehive));
		this.registerItemModel(block);
	}

	public void registerButton(Block block, Block buttonBlock) {
		ModelFile button = models().withExistingParent(name(buttonBlock), "block/button").texture("texture", blockTexture(block));
		ModelFile buttonPressed = models().withExistingParent(name(buttonBlock) + "_pressed", "block/button_pressed").texture("texture", blockTexture(block));
		ModelFile buttonInventory = models().withExistingParent(name(buttonBlock) + "_inventory", "block/button_inventory").texture("texture", blockTexture(block));
		this.buttonBlock(buttonBlock, (state -> state.getValue(BlockStateProperties.POWERED) ? buttonPressed : button));
		this.itemModels().getBuilder(name(buttonBlock)).parent(buttonInventory);
	}

	public void registerPressurePlate(Block block, Block pressurePlateBlock) {
		ModelFile pressurePlate = models().withExistingParent(name(pressurePlateBlock), "block/pressure_plate_up").texture("texture", blockTexture(block));
		ModelFile pressurePlateDown = models().withExistingParent(name(pressurePlateBlock) + "_down", "block/pressure_plate_down").texture("texture", blockTexture(block));
		this.pressurePlateBlock(pressurePlateBlock, (state -> state.getValue(BlockStateProperties.POWERED) ? pressurePlateDown : pressurePlate));
		this.registerItemModel(pressurePlateBlock);
	}

	public void buttonBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
		this.getVariantBuilder(block)
				.forAllStates(state -> ConfiguredModel.builder()
						.modelFile(modelFunc.apply(state))
						.uvLock(state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.WALL)
						.rotationX(state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.WALL ? 90 : state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)
						.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + (state.getValue(BlockStateProperties.ATTACH_FACE) != AttachFace.CEILING ? 180 : 0)) % 360)
						.build()
				);
	}

	public void pressurePlateBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
		this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(modelFunc.apply(state)).build());
	}

	public void registerBlockVariants(Block block, Block stairs, Block slab, Block verticalSlab) {
		this.stairsBlock((StairBlock) stairs, blockTexture(block));
		this.slabBlock((SlabBlock) slab, blockTexture(block), blockTexture(block));
		this.verticalSlabBlock(block, (VerticalSlabBlock) verticalSlab);

		this.registerItemModel(stairs);
		this.registerItemModel(slab);
		this.registerItemModel(verticalSlab);
	}

	public void registerBlockVariants(Block block, Block stairs, Block slab, Block verticalSlab, Block wall) {
		this.registerBlockVariants(block, stairs, slab, verticalSlab);
		this.wallBlock((WallBlock) wall, blockTexture(block));
		this.itemModels().getBuilder(name(wall)).parent(this.models().wallInventory(name(wall) + "_inventory", blockTexture(block)));
	}

	public void registerBlockWithVariants(Block block, Block stairs, Block slab, Block verticalSlab) {
		this.simpleBlock(block);
		this.registerItemModel(block);
		this.registerBlockVariants(block, stairs, slab, verticalSlab);
	}

	public void registerBlockWithVariants(Block block, Block stairs, Block slab, Block verticalSlab, Block wall) {
		this.registerBlockWithVariants(block, stairs, slab, verticalSlab);
		this.wallBlock((WallBlock) wall, blockTexture(block));
		this.itemModels().getBuilder(name(wall)).parent(this.models().wallInventory(name(wall) + "_inventory", blockTexture(block)));
	}

	public void registerDoorBlocks(Block door, Block trapdoor) {
		this.doorBlock((DoorBlock) door, suffix(blockTexture(door), "_bottom"), suffix(blockTexture(door), "_top"));
		this.generatedItem(door, "item");
		this.trapdoorBlock((TrapDoorBlock) trapdoor, blockTexture(trapdoor), true);
		this.itemModels().getBuilder(name(trapdoor)).parent(this.models().trapdoorOrientableBottom(name(trapdoor) + "_bottom", blockTexture(trapdoor)));
	}

	public void leaves(Block leaves, Block leafPile) {
		this.simpleBlock(leaves, models().getBuilder(name(leaves)).parent(new UncheckedModelFile(new ResourceLocation("block/leaves"))).texture("all", blockTexture(AutumnityBlocks.MAPLE_LEAVES.get())));
		this.registerItemModel(leaves);

		ModelFile leafPileModel = models().getBuilder(name(leafPile)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/tinted_leaf_pile"))).texture("all", blockTexture(AutumnityBlocks.MAPLE_LEAVES.get()));
		MultiPartBlockStateBuilder builder = getMultipartBuilder(leafPile);
		builder.part().modelFile(leafPileModel).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.UP, true);
		builder.part().modelFile(leafPileModel).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		builder.part().modelFile(leafPileModel).addModel().condition(BlockStateProperties.NORTH, true);
		builder.part().modelFile(leafPileModel).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		builder.part().modelFile(leafPileModel).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.WEST, true);
		builder.part().modelFile(leafPileModel).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		builder.part().modelFile(leafPileModel).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.SOUTH, true);
		builder.part().modelFile(leafPileModel).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		builder.part().modelFile(leafPileModel).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.EAST, true);
		builder.part().modelFile(leafPileModel).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		builder.part().modelFile(leafPileModel).rotationX(90).uvLock(true).addModel().condition(BlockStateProperties.DOWN, true);
		builder.part().modelFile(leafPileModel).rotationX(90).uvLock(true).addModel().condition(BlockStateProperties.UP, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.DOWN, false);
		this.generatedItem(leafPile, AutumnityBlocks.MAPLE_LEAVES.get(), "block");
	}

	private void ironBarsBlock(Block block) {
		String name = name(block);
		ResourceLocation barsTexture = prefix("block/", new ResourceLocation(block.getRegistryName().getNamespace(), name(block).replace("waxed_", "")));
		ResourceLocation edgeTexture = suffix(barsTexture, "_edge");

		ModelFile post = barsBlock(name, "post", barsTexture).texture("bars", edgeTexture);
		ModelFile postEnds = barsBlock(name, "post_ends", barsTexture).texture("edge", edgeTexture);
		ModelFile side = barsBlock(name, "side", barsTexture).texture("bars", barsTexture).texture("edge", edgeTexture);
		ModelFile sideAlt = barsBlock(name, "side_alt", barsTexture).texture("bars", barsTexture).texture("edge", edgeTexture);
		ModelFile cap = barsBlock(name, "cap", barsTexture).texture("bars", barsTexture).texture("edge", edgeTexture);
		ModelFile capAlt = barsBlock(name, "cap_alt", barsTexture).texture("bars", barsTexture).texture("edge", edgeTexture);

		paneBlock(block, post, postEnds, side, sideAlt, cap, capAlt);
	}

	private BlockModelBuilder barsBlock(String name, String suffix, ResourceLocation barsTexture) {
		return models().getBuilder(name + "_" + suffix).parent(new UncheckedModelFile(new ResourceLocation("block/iron_bars_" + suffix))).texture("particle", barsTexture);
	}

	public void paneBlock(Block block, ModelFile post, ModelFile postEnds, ModelFile side, ModelFile sideAlt, ModelFile cap, ModelFile capAlt) {
		MultiPartBlockStateBuilder builder = getMultipartBuilder(block).part().modelFile(postEnds).addModel().end();
		builder.part().modelFile(post).addModel().condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.WEST, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.EAST, false).end();

		for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
			builder.part().modelFile(direction == Direction.SOUTH || direction == Direction.WEST ? capAlt : cap).rotationY(direction.getAxis() == Axis.X ? 90 : 0).addModel()
					.condition(BlockStateProperties.NORTH, PipeBlock.PROPERTY_BY_DIRECTION.get(direction) == BlockStateProperties.NORTH)
					.condition(BlockStateProperties.WEST, PipeBlock.PROPERTY_BY_DIRECTION.get(direction) == BlockStateProperties.WEST)
					.condition(BlockStateProperties.SOUTH, PipeBlock.PROPERTY_BY_DIRECTION.get(direction) == BlockStateProperties.SOUTH)
					.condition(BlockStateProperties.EAST, PipeBlock.PROPERTY_BY_DIRECTION.get(direction) == BlockStateProperties.EAST)
					.end();

		}

		PipeBlock.PROPERTY_BY_DIRECTION.forEach((dir, value) -> {
			if (dir.getAxis().isHorizontal()) {
				builder.part().modelFile(dir == Direction.SOUTH || dir == Direction.WEST ? sideAlt : side).rotationY(dir.getAxis() == Axis.X ? 90 : 0).addModel().condition(value, true).end();
			}
		});
	}

	public void registerQuarkLeavesCompat(Block leaves, Block leafCarpet, Block log, Block hedge) {
		ModelFile hedgePost = this.hedgePost(name(hedge) + "_post", blockTexture(log), blockTexture(AutumnityBlocks.MAPLE_LEAVES.get()));
		ModelFile hedgeSide = this.hedgeSide(name(hedge) + "_side", blockTexture(AutumnityBlocks.MAPLE_LEAVES.get()));
		ModelFile hedgeExtend = this.hedgeExtend(name(hedge) + "_extend", blockTexture(AutumnityBlocks.MAPLE_LEAVES.get()));
		this.hedgeBlock(hedge, hedgePost, hedgeSide, hedgeExtend);
		this.itemModels().getBuilder(name(hedge)).parent(hedgePost);
		this.simpleBlock(leafCarpet, models().getBuilder(name(leafCarpet)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/leaf_carpet"))).texture("all", blockTexture(AutumnityBlocks.MAPLE_LEAVES.get())));
		this.registerItemModel(leafCarpet);
	}

	public ModelFile verticalSlab(String name, ResourceLocation texture) {
		return models().getBuilder(name).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/vertical_slab"))).texture("side", texture).texture("bottom", texture).texture("top", texture);
	}

	public static final BooleanProperty[] CHAINED = new BooleanProperty[]{BooleanProperty.create("chain_down"), BooleanProperty.create("chain_up"), BooleanProperty.create("chain_north"), BooleanProperty.create("chain_south"), BooleanProperty.create("chain_west"), BooleanProperty.create("chain_east")};

	public void registerPost(Block log, Block post) {
		ModelFile model = models().getBuilder(name(post)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/post"))).texture("texture", blockTexture(log));
		ModelFile chainSmall = new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/chain_small"));
		ModelFile chainSmallTop = new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/chain_small_top"));
		this.getMultipartBuilder(post)
				.part().modelFile(model).addModel().condition(RotatedPillarBlock.AXIS, Axis.Y).end()
				.part().modelFile(model).rotationX(90).addModel().condition(RotatedPillarBlock.AXIS, Axis.Z).end()
				.part().modelFile(model).rotationX(90).rotationY(90).addModel().condition(RotatedPillarBlock.AXIS, Axis.X).end()
				.part().modelFile(chainSmall).addModel().condition(CHAINED[0], true).end()
				.part().modelFile(chainSmallTop).addModel().condition(CHAINED[1], true).end()
				.part().modelFile(chainSmallTop).rotationX(90).addModel().condition(CHAINED[2], true).end()
				.part().modelFile(chainSmall).rotationX(90).addModel().condition(CHAINED[3], true).end()
				.part().modelFile(chainSmall).rotationX(90).rotationY(90).addModel().condition(CHAINED[4], true).end()
				.part().modelFile(chainSmallTop).rotationX(90).rotationY(90).addModel().condition(CHAINED[5], true).end();
		this.registerItemModel(post);
	}

	public void hedgeBlock(Block block, ModelFile post, ModelFile side, ModelFile extend) {
		MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
		builder.part().modelFile(post).addModel().condition(BooleanProperty.create("extend"), false).end().part().modelFile(extend).addModel().condition(BooleanProperty.create("extend"), true);
		PipeBlock.PROPERTY_BY_DIRECTION.entrySet().forEach(e -> {
			Direction dir = e.getKey();
			if (dir.getAxis().isHorizontal()) {
				builder.part().modelFile(side).rotationY((((int) dir.toYRot()) + 180) % 360).uvLock(true).addModel().condition(e.getValue(), true);
			}
		});
	}

	public ModelFile hedgePost(String name, ResourceLocation log, ResourceLocation leaf) {
		return models().getBuilder(name).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/hedge_post"))).texture("log", log).texture("leaf", leaf);
	}

	public ModelFile hedgeSide(String name, ResourceLocation leaf) {
		return models().getBuilder(name).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/hedge_side"))).texture("leaf", leaf);
	}

	public ModelFile hedgeExtend(String name, ResourceLocation leaf) {
		return models().getBuilder(name).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/hedge_extend"))).texture("leaf", leaf);
	}

	public void registerSignBlocks(Block planks, Pair<RegistryObject<BlueprintStandingSignBlock>, RegistryObject<BlueprintWallSignBlock>> pair) {
		this.simpleBlock(pair.getFirst().get(), particle(pair.getFirst().get(), blockTexture(planks)));
		this.simpleBlock(pair.getSecond().get(), particle(pair.getFirst().get(), blockTexture(planks)));
		this.generatedItem(pair.getFirst().get(), "item");
	}

	public void registerChestBlocks(Block planks, Pair<RegistryObject<BlueprintChestBlock>, RegistryObject<BlueprintTrappedChestBlock>> pair) {
		this.simpleBlock(pair.getFirst().get(), particle(pair.getFirst().get(), blockTexture(planks)));
		this.simpleBlock(pair.getSecond().get(), particle(pair.getFirst().get(), blockTexture(planks)));
		this.simpleBlockItem(pair.getFirst().get(), new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "item/template_chest")));
		this.simpleBlockItem(pair.getSecond().get(), new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "item/template_chest")));
	}

	public ModelFile particle(Block block, ResourceLocation texture) {
		return this.models().getBuilder(name(block)).texture("particle", texture);
	}

	public void registerLogBlocks(Block log, Block wood) {
		this.logBlock((RotatedPillarBlock) log);
		this.registerItemModel(log);

		this.axisBlock((RotatedPillarBlock) wood, blockTexture(log), blockTexture(log));
		this.registerItemModel(wood);
	}

	public void verticalSlabBlock(Block planks, VerticalSlabBlock slab) {
		this.verticalSlab(name(slab), blockTexture(planks));
		UncheckedModelFile model = new UncheckedModelFile(prefix("block/", slab.getRegistryName()));
		this.getVariantBuilder(slab)
				.partialState().with(VerticalSlabBlock.TYPE, VerticalSlabType.NORTH).addModels(new ConfiguredModel(model, 0, 0, true))
				.partialState().with(VerticalSlabBlock.TYPE, VerticalSlabType.SOUTH).addModels(new ConfiguredModel(model, 0, 180, true))
				.partialState().with(VerticalSlabBlock.TYPE, VerticalSlabType.EAST).addModels(new ConfiguredModel(model, 0, 90, true))
				.partialState().with(VerticalSlabBlock.TYPE, VerticalSlabType.WEST).addModels(new ConfiguredModel(model, 0, 270, true))
				.partialState().with(VerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE).addModels(new ConfiguredModel(models().cubeAll(name(planks), blockTexture(planks))));
	}

	private String name(Block block) {
		return block.getRegistryName().getPath();
	}

	private ResourceLocation prefix(String prefix, ResourceLocation rl) {
		return new ResourceLocation(rl.getNamespace(), prefix + rl.getPath());
	}

	private ResourceLocation suffix(ResourceLocation rl, String suffix) {
		return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
	}
}