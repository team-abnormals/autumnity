package com.teamabnormals.autumnity.core.data.client;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityBlockFamilies;
import com.teamabnormals.blueprint.common.block.chest.BlueprintChestBlock;
import com.teamabnormals.blueprint.common.block.chest.BlueprintTrappedChestBlock;
import com.teamabnormals.blueprint.core.Blueprint;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.BlockFamily.Variant;
import net.minecraft.data.PackOutput;
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
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

import static com.teamabnormals.autumnity.core.registry.AutumnityBlocks.*;

public class AutumnityBlockStateProvider extends BlockStateProvider {
	public static final String[] MAPLE_BOOKSHELF_POSITIONS = new String[]{"top_left", "top_right", "bottom_left", "bottom_mid_left", "bottom_mid_right", "bottom_right"};

	public AutumnityBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Autumnity.MOD_ID, helper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.blockFamily(AutumnityBlockFamilies.MAPLE_PLANKS_FAMILY);
		this.blockFamily(AutumnityBlockFamilies.SNAIL_SHELL_BRICKS_FAMILY);
		this.blockFamily(AutumnityBlockFamilies.SNAIL_SHELL_TILES_FAMILY);

		this.logBlocks(MAPLE_LOG.get(), MAPLE_WOOD.get());
		this.logBlocks(STRIPPED_MAPLE_LOG.get(), STRIPPED_MAPLE_WOOD.get());
		this.logBlocks(SAPPY_MAPLE_LOG.get(), SAPPY_MAPLE_WOOD.get());
		this.hangingSigns(STRIPPED_MAPLE_LOG.get(), MAPLE_HANGING_SIGNS.getFirst().get(), MAPLE_HANGING_SIGNS.getSecond().get());

		this.leavesBlock(MAPLE_LEAVES.get());
		this.leavesBlock(YELLOW_MAPLE_LEAVES.get());
		this.leavesBlock(ORANGE_MAPLE_LEAVES.get());
		this.leavesBlock(RED_MAPLE_LEAVES.get());

		this.crossBlockWithPot(MAPLE_SAPLING.get(), POTTED_MAPLE_SAPLING.get());
		this.crossBlockWithPot(YELLOW_MAPLE_SAPLING.get(), POTTED_YELLOW_MAPLE_SAPLING.get());
		this.crossBlockWithPot(ORANGE_MAPLE_SAPLING.get(), POTTED_ORANGE_MAPLE_SAPLING.get());
		this.crossBlockWithPot(RED_MAPLE_SAPLING.get(), POTTED_RED_MAPLE_SAPLING.get());
		this.crossBlockWithPot(AUTUMN_CROCUS.get(), POTTED_AUTUMN_CROCUS.get());

		this.planksCompat(MAPLE_PLANKS.get(), MAPLE_BOARDS.get(), MAPLE_LADDER.get(), MAPLE_BEEHIVE.get(), MAPLE_CHEST.get(), TRAPPED_MAPLE_CHEST.get());
		this.bookshelfBlocks(MAPLE_PLANKS.get(), MAPLE_BOOKSHELF.get(), CHISELED_MAPLE_BOOKSHELF.get(), MAPLE_BOOKSHELF_POSITIONS, Autumnity.MOD_ID + ":block/chiseled_maple");
		this.leavesCompat(MAPLE_LEAVES.get(), MAPLE_LEAF_PILE.get());
		this.leavesCompat(MAPLE_LEAVES.get(), YELLOW_MAPLE_LEAF_PILE.get());
		this.leavesCompat(MAPLE_LEAVES.get(), ORANGE_MAPLE_LEAF_PILE.get());
		this.leavesCompat(MAPLE_LEAVES.get(), RED_MAPLE_LEAF_PILE.get());
	}

	public void block(Block block) {
		simpleBlock(block, cubeAll(block));
		blockItem(block);
	}

	public void blockFamily(BlockFamily family) {
		Block block = family.getBaseBlock();
		this.block(block);

		if (family.getVariants().containsKey(Variant.CHISELED)) {
			this.block(family.get(Variant.CHISELED));
		}

		if (family.getVariants().containsKey(Variant.SLAB)) {
			SlabBlock slab = (SlabBlock) family.get(Variant.SLAB);
			this.slabBlock(slab, blockTexture(block), blockTexture(block));
			this.blockItem(slab);
		}

		if (family.getVariants().containsKey(Variant.STAIRS)) {
			StairBlock stairs = (StairBlock) family.get(Variant.STAIRS);
			this.stairsBlock(stairs, blockTexture(block));
			this.blockItem(stairs);
		}

		if (family.getVariants().containsKey(Variant.WALL)) {
			WallBlock wall = (WallBlock) family.get(Variant.WALL);
			this.wallBlock(wall, blockTexture(block));
			this.itemModels().getBuilder(name(wall)).parent(this.models().wallInventory(name(wall) + "_inventory", blockTexture(block)));
		}

		if (family.getVariants().containsKey(Variant.FENCE)) {
			FenceBlock fence = (FenceBlock) family.get(Variant.FENCE);
			this.fenceBlock(fence, blockTexture(block));
			this.itemModels().getBuilder(name(fence)).parent(this.models().fenceInventory(name(fence) + "_inventory", blockTexture(block)));
		}

		if (family.getVariants().containsKey(Variant.FENCE_GATE)) {
			FenceGateBlock fenceGate = (FenceGateBlock) family.get(Variant.FENCE_GATE);
			this.fenceGateBlock(fenceGate, blockTexture(block));
			this.blockItem(fenceGate);
		}

		if (family.getVariants().containsKey(Variant.BUTTON)) {
			ButtonBlock button = (ButtonBlock) family.get(Variant.BUTTON);
			ModelFile buttonModel = models().withExistingParent(name(button), "block/button").texture("texture", blockTexture(block));
			ModelFile buttonPressedModel = models().withExistingParent(name(button) + "_pressed", "block/button_pressed").texture("texture", blockTexture(block));
			ModelFile buttonInventoryModel = models().withExistingParent(name(button) + "_inventory", "block/button_inventory").texture("texture", blockTexture(block));
			this.buttonBlock(button, (state -> state.getValue(BlockStateProperties.POWERED) ? buttonPressedModel : buttonModel));
			this.itemModels().getBuilder(name(button)).parent(buttonInventoryModel);
		}

		if (family.getVariants().containsKey(Variant.PRESSURE_PLATE)) {
			BasePressurePlateBlock pressurePlate = (BasePressurePlateBlock) family.get(Variant.PRESSURE_PLATE);
			ModelFile pressurePlateModel = models().withExistingParent(name(pressurePlate), "block/pressure_plate_up").texture("texture", blockTexture(block));
			ModelFile pressurePlateDownModel = models().withExistingParent(name(pressurePlate) + "_down", "block/pressure_plate_down").texture("texture", blockTexture(block));
			this.pressurePlateBlock(pressurePlate, (state -> state.getValue(BlockStateProperties.POWERED) ? pressurePlateDownModel : pressurePlateModel));
			this.blockItem(pressurePlate);
		}

		if (family.getVariants().containsKey(Variant.DOOR)) {
			DoorBlock door = (DoorBlock) family.get(Variant.DOOR);
			this.doorBlock(door, suffix(blockTexture(door), "_bottom"), suffix(blockTexture(door), "_top"));
			this.generatedItem(door, "item");
		}

		if (family.getVariants().containsKey(Variant.TRAPDOOR)) {
			TrapDoorBlock trapdoor = (TrapDoorBlock) family.get(Variant.TRAPDOOR);
			this.trapdoorBlock(trapdoor, blockTexture(trapdoor), true);
			this.itemModels().getBuilder(name(trapdoor)).parent(this.models().trapdoorOrientableBottom(name(trapdoor) + "_bottom", blockTexture(trapdoor)));
		}

		if (family.getVariants().containsKey(Variant.SIGN)) {
			SignBlock sign = (SignBlock) family.get(Variant.SIGN);
			ModelFile model = particle(sign, blockTexture(block));
			this.simpleBlock(sign, model);
			this.generatedItem(sign, "item");
			if (family.getVariants().containsKey(Variant.WALL_SIGN)) {
				this.simpleBlock(family.get(Variant.WALL_SIGN), model);
			}
		}
	}

	public void hangingSigns(Block strippedLog, Block sign, Block wallSign) {
		ModelFile model = particle(sign, blockTexture(strippedLog));
		this.simpleBlock(sign, particle(sign, blockTexture(strippedLog)));
		this.generatedItem(sign, "item");
		this.simpleBlock(wallSign, model);
	}

	public void blockItem(Block block) {
		this.simpleBlockItem(block, new ExistingModelFile(blockTexture(block), this.models().existingFileHelper));
	}

	private void generatedItem(ItemLike item, String type) {
		generatedItem(item, item, type);
	}

	private void generatedItem(ItemLike item, ItemLike texture, String type) {
		itemModels().withExistingParent(ForgeRegistries.ITEMS.getKey(item.asItem()).getPath(), "item/generated").texture("layer0", new ResourceLocation(ForgeRegistries.ITEMS.getKey(texture.asItem()).getNamespace(), type + "/" + ForgeRegistries.ITEMS.getKey(texture.asItem()).getPath()));
	}

	public void crossBlockWithPot(Block cross, Block flowerPot) {
		this.simpleBlock(cross, models().cross(name(cross), blockTexture(cross)));
		this.generatedItem(cross, "block");

		this.simpleBlock(flowerPot, models().singleTexture(name(flowerPot), new ResourceLocation("block/flower_pot_cross"), "plant", blockTexture(cross)));
	}

	public void bookshelfBlock(Block planks, Block bookshelf) {
		this.simpleBlock(bookshelf, this.models().cubeColumn(name(bookshelf), blockTexture(bookshelf), blockTexture(planks)));
		this.blockItem(bookshelf);
	}

	public void ladderBlock(Block ladder) {
		this.horizontalBlock(ladder, models().withExistingParent(name(ladder), "block/ladder").texture("particle", blockTexture(ladder)).texture("texture", blockTexture(ladder)));
		this.generatedItem(ladder, "block");
	}

	public void boardsBlock(Block boards) {
		ModelFile boardsModel = models().getBuilder(name(boards)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/template_boards"))).texture("all", blockTexture(boards));
		ModelFile boardsHorizontalModel = models().getBuilder(name(boards) + "_horizontal").parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/template_boards_horizontal"))).texture("all", blockTexture(boards));
		this.getVariantBuilder(boards).partialState().with(RotatedPillarBlock.AXIS, Axis.Y).modelForState().modelFile(boardsModel).addModel().partialState().with(RotatedPillarBlock.AXIS, Axis.Z).modelForState().modelFile(boardsHorizontalModel).addModel().partialState().with(RotatedPillarBlock.AXIS, Axis.X).modelForState().modelFile(boardsHorizontalModel).rotationY(270).addModel();
		this.blockItem(boards);
	}

	public void beehiveBlock(Block block) {
		ModelFile beehive = models().orientableWithBottom(name(block), suffix(blockTexture(block), "_side"), suffix(blockTexture(block), "_front"), suffix(blockTexture(block), "_end"), suffix(blockTexture(block), "_end")).texture("particle", suffix(blockTexture(block), "_side"));
		ModelFile beehiveHoney = models().orientableWithBottom(name(block) + "_honey", suffix(blockTexture(block), "_side"), suffix(blockTexture(block), "_front_honey"), suffix(blockTexture(block), "_end"), suffix(blockTexture(block), "_end")).texture("particle", suffix(blockTexture(block), "_side"));
		this.horizontalBlock(block, (state -> state.getValue(BlockStateProperties.LEVEL_HONEY) == 5 ? beehiveHoney : beehive));
		this.blockItem(block);
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

	public void planksCompat(Block planks, Block boards, Block ladder, Block beehive, BlueprintChestBlock chest, BlueprintTrappedChestBlock trappedChest) {
		this.boardsBlock(boards);
		this.ladderBlock(ladder);
		this.beehiveBlock(beehive);
		this.chestBlocks(planks, chest, trappedChest);
	}

	public void leavesCompat(Block leaves, Block leafPile) {
		this.leafPileBlock(leaves, leafPile);
	}

	public void leavesBlock(Block leaves) {
		this.simpleBlock(leaves, models().getBuilder(name(leaves)).parent(new UncheckedModelFile(new ResourceLocation("block/leaves"))).texture("all", blockTexture(MAPLE_LEAVES.get())));
		this.blockItem(leaves);
	}

	public void leafPileBlock(Block leaves, Block leafPile) {
		this.leafPileBlock(leaves, leafPile, true);
	}

	public void leafPileBlock(Block leaves, Block leafPile, boolean tint) {
		ModelFile leafPileModel = models().getBuilder(name(leafPile)).parent(new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "block/" + (tint ? "tinted_" : "") + "leaf_pile"))).texture("all", blockTexture(leaves));
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
		this.generatedItem(leafPile, leaves, "block");
	}

	public void chestBlocks(Block planks, BlueprintChestBlock chest, BlueprintTrappedChestBlock trappedChest) {
		this.simpleBlock(chest, particle(chest, blockTexture(planks)));
		this.simpleBlock(trappedChest, particle(chest, blockTexture(planks)));
		this.simpleBlockItem(chest, new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "item/template_chest")));
		this.simpleBlockItem(trappedChest, new UncheckedModelFile(new ResourceLocation(Blueprint.MOD_ID, "item/template_chest")));
	}

	public ModelFile particle(Block block, ResourceLocation texture) {
		return this.models().getBuilder(name(block)).texture("particle", texture);
	}

	public void logBlocks(Block log, Block wood) {
		this.logBlock((RotatedPillarBlock) log);
		this.blockItem(log);

		this.axisBlock((RotatedPillarBlock) wood, blockTexture(log), blockTexture(log));
		this.blockItem(wood);
	}

	public void bookshelfBlocks(Block planks, Block bookshelf, Block chiseledBookshelf, String[] parts, String parent) {
		this.simpleBlock(bookshelf, this.models().cubeColumn(name(bookshelf), blockTexture(bookshelf), blockTexture(planks)));
		this.blockItem(bookshelf);

		BlockModelBuilder model = this.models().withExistingParent(name(chiseledBookshelf), "block/chiseled_bookshelf").texture("top", blockTexture(chiseledBookshelf) + "_top").texture("side", blockTexture(chiseledBookshelf) + "_side");
		MultiPartBlockStateBuilder builder = getMultipartBuilder(chiseledBookshelf);
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			int rotation = (int) (direction.toYRot() + 180) % 360;
			builder.part().modelFile(model).rotationY(rotation).uvLock(true).addModel().condition(HorizontalDirectionalBlock.FACING, direction);
			chiseledBookshelfParts(builder, chiseledBookshelf, parts[0], parent, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_0_OCCUPIED, direction);
			chiseledBookshelfParts(builder, chiseledBookshelf, parts[1], parent, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_1_OCCUPIED, direction);
			chiseledBookshelfParts(builder, chiseledBookshelf, parts[2], parent, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_2_OCCUPIED, direction);
			chiseledBookshelfParts(builder, chiseledBookshelf, parts[3], parent, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_3_OCCUPIED, direction);
			chiseledBookshelfParts(builder, chiseledBookshelf, parts[4], parent, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_4_OCCUPIED, direction);
			chiseledBookshelfParts(builder, chiseledBookshelf, parts[5], parent, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_5_OCCUPIED, direction);
		}

		this.simpleBlockItem(chiseledBookshelf, this.models()
				.withExistingParent(name(chiseledBookshelf) + "_inventory", "block/chiseled_bookshelf_inventory")
				.texture("top", blockTexture(chiseledBookshelf) + "_top")
				.texture("side", blockTexture(chiseledBookshelf) + "_side")
				.texture("front", blockTexture(chiseledBookshelf) + "_empty"));
	}

	public void chiseledBookshelfParts(MultiPartBlockStateBuilder builder, Block chiseledBookshelf, String part, String parent, BooleanProperty property, Direction direction) {
		int rotation = (int) (direction.toYRot() + 180) % 360;
		builder.part().modelFile(this.models()
						.withExistingParent(name(chiseledBookshelf) + "_occupied_slot_" + part, parent + "_bookshelf_slot_" + part)
						.texture("texture", blockTexture(chiseledBookshelf) + "_occupied"))
				.rotationY(rotation).uvLock(true)
				.addModel().condition(HorizontalDirectionalBlock.FACING, direction).condition(property, true);
		builder.part().modelFile(this.models()
						.withExistingParent(name(chiseledBookshelf) + "_empty_slot_" + part, parent + "_bookshelf_slot_" + part)
						.texture("texture", blockTexture(chiseledBookshelf) + "_empty"))
				.rotationY(rotation).uvLock(true)
				.addModel().condition(HorizontalDirectionalBlock.FACING, direction).condition(property, false);
	}

	private String name(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block).getPath();
	}

	private ResourceLocation prefix(String prefix, ResourceLocation rl) {
		return new ResourceLocation(rl.getNamespace(), prefix + rl.getPath());
	}

	private ResourceLocation suffix(ResourceLocation rl, String suffix) {
		return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
	}
}