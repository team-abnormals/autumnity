//package com.minecraftabnormals.autumnity.common.levelgen.feature.structure;
//
//import com.minecraftabnormals.autumnity.core.Autumnity;
//import com.minecraftabnormals.autumnity.core.other.AutumnityLootTables;
//import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
//import com.minecraftabnormals.autumnity.core.registry.AutumnityStructureFeatures;
//import net.minecraft.core.BlockPos;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.MobSpawnType;
//import net.minecraft.world.entity.animal.Cat;
//import net.minecraft.world.entity.monster.Witch;
//import net.minecraft.world.gen.feature.template.*;
//import net.minecraft.world.level.ServerLevelAccessor;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.Mirror;
//import net.minecraft.world.level.block.Rotation;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.entity.ChestBlockEntity;
//import net.minecraft.world.level.levelgen.structure.BoundingBox;
//import net.minecraft.world.level.levelgen.structure.StructurePiece;
//import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
//import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
//import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
//import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
//import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
//import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
//import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
//
//import java.util.List;
//import java.util.Random;
//
//public class MapleWitchHutPieces {
//	private static final BlockPos STRUCTURE_OFFSET = new BlockPos(0, 0, 0);
//	private static final ResourceLocation STRUCTURE = new ResourceLocation(Autumnity.MOD_ID, "witch_hut/maple_witch_hut");
//	private static final ResourceLocation STRUCTURE_OVERGROWN = new ResourceLocation(Autumnity.MOD_ID, "witch_hut/maple_witch_hut_overgrown");
//
//	public static void addPieces(StructureManager p_204760_0_, BlockPos p_204760_1_, Rotation p_204760_2_, Mirror p_204760_3_, List<StructurePiece> p_204760_4_, Random p_204760_5_) {
//		p_204760_4_.add(new MapleWitchHutPieces.Piece(p_204760_0_, STRUCTURE, p_204760_1_, p_204760_2_, p_204760_3_, 1.0F));
//		p_204760_4_.add(new MapleWitchHutPieces.Piece(p_204760_0_, STRUCTURE_OVERGROWN, p_204760_1_, p_204760_2_, p_204760_3_, 0.2F));
//	}
//
//	public static class Piece extends TemplateStructurePiece {
//		private final ResourceLocation structure;
//		private final Rotation rotation;
//		private final Mirror mirror;
//		private final float integrity;
//
//		public Piece(StructureManager templateManager, ResourceLocation templateIn, BlockPos templatePositionIn, Rotation rotationIn, Mirror mirrorIn, float integrityIn) {
//			super(AutumnityStructureFeatures.Pieces.MAPLE_WITCH_HUT_PIECE, 0);
//			this.structure = templateIn;
//			this.templatePosition = templatePositionIn;
//			this.rotation = rotationIn;
//			this.mirror = mirrorIn;
//			this.integrity = integrityIn;
//			this.loadTemplate(templateManager);
//		}
//
//		public Piece(StructureManager templateManager, CompoundTag tagCompound) {
//			super(AutumnityStructureFeatures.Pieces.MAPLE_WITCH_HUT_PIECE, tagCompound);
//			this.structure = new ResourceLocation(tagCompound.getString("Template"));
//			this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
//			this.mirror = Mirror.valueOf(tagCompound.getString("Mirror"));
//			this.integrity = tagCompound.getFloat("Integrity");
//			this.loadTemplate(templateManager);
//		}
//
//		@Override
//		protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tagCompound) {
//			super.addAdditionalSaveData(context, tagCompound);
//			tagCompound.putString("Template", this.structure.toString());
//			tagCompound.putString("Rot", this.rotation.name());
//			tagCompound.putString("Mirror", this.mirror.name());
//			tagCompound.putFloat("Integrity", this.integrity);
//		}
//
//		private void loadTemplate(StructureManager templateManager) {
//			StructureTemplate template = templateManager.getOrCreate(this.structure);
//			StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setRotation(this.rotation).setMirror(this.mirror).setRotationPivot(MapleWitchHutPieces.STRUCTURE_OFFSET).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK).addProcessor(new BlockRotProcessor(this.integrity));
//
//			this.setup(template, this.templatePosition, placementsettings);
//		}
//
//		@Override
//		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, Random rand, BoundingBox sbb) {
//			if ("chest".equals(function)) {
//				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
//				BlockEntity tileentity = worldIn.getBlockEntity(pos.below());
//				if (tileentity instanceof ChestBlockEntity) {
//					((ChestBlockEntity) tileentity).setLootTable(AutumnityLootTables.CHESTS_MAPLE_WITCH_HUT, rand.nextLong());
//				}
//			} else if ("decor".equals(function)) {
//				if (rand.nextInt(2) == 0) {
//					worldIn.setBlock(pos, Blocks.POTTED_RED_MUSHROOM.defaultBlockState(), 2);
//				} else {
//					worldIn.setBlock(pos, Blocks.POTTED_BROWN_MUSHROOM.defaultBlockState(), 2);
//				}
//			} else if ("flower".equals(function)) {
//				if (rand.nextInt(4) == 0) {
//					worldIn.setBlock(pos.below(), AutumnityBlocks.AUTUMN_CROCUS.get().defaultBlockState(), 2);
//				}
//				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
//			} else if (function.startsWith("witch")) {
//				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
//				Witch witchentity = EntityType.WITCH.create(worldIn.getLevel());
//				witchentity.setPersistenceRequired();
//				witchentity.setPos((double) pos.getX() + 0.5D, (double) pos.getY() - 1.0D, (double) pos.getZ() + 0.5D);
//				witchentity.finalizeSpawn(worldIn, worldIn.getCurrentDifficultyAt(new BlockPos(pos.getX(), pos.getY(), (double) pos.getZ())), MobSpawnType.STRUCTURE, null, null);
//				worldIn.addFreshEntityWithPassengers(witchentity);
//			} else if (function.startsWith("cat")) {
//				worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
//				Cat catentity = EntityType.CAT.create(worldIn.getLevel());
//				catentity.setPersistenceRequired();
//				catentity.setPos((double) pos.getX() + 0.5D, (double) pos.getY() - 1.0D, (double) pos.getZ() + 0.5D);
//				catentity.finalizeSpawn(worldIn, worldIn.getCurrentDifficultyAt(new BlockPos(pos.getX(), pos.getY(), (double) pos.getZ())), MobSpawnType.STRUCTURE, null, null);
//				catentity.setCatType(10);
//				worldIn.addFreshEntityWithPassengers(catentity);
//			}
//		}
//	}
//}