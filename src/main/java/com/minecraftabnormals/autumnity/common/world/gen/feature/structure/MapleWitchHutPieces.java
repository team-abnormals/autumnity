package com.minecraftabnormals.autumnity.common.world.gen.feature.structure;

import java.util.List;
import java.util.Random;

import com.minecraftabnormals.autumnity.core.Autumnity;
import com.minecraftabnormals.autumnity.core.other.AutumnityLootTables;
import com.minecraftabnormals.autumnity.core.registry.AutumnityStructures;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class MapleWitchHutPieces {
	private static final BlockPos STRUCTURE_OFFSET = new BlockPos(-3, -4, 6);
	private static final ResourceLocation STRUCTURE = new ResourceLocation(Autumnity.MOD_ID, "witch_hut/maple_witch_hut");
	private static final ResourceLocation STRUCTURE_OVERGROWN = new ResourceLocation(Autumnity.MOD_ID, "witch_hut/maple_witch_hut_overgrown");

	public static void func_204760_a(TemplateManager p_204760_0_, BlockPos p_204760_1_, Rotation p_204760_2_, Mirror p_204760_3_, List<StructurePiece> p_204760_4_, Random p_204760_5_) {
		p_204760_4_.add(new MapleWitchHutPieces.Piece(p_204760_0_, STRUCTURE, p_204760_1_, p_204760_2_, p_204760_3_, 1.0F));
		p_204760_4_.add(new MapleWitchHutPieces.Piece(p_204760_0_, STRUCTURE_OVERGROWN, p_204760_1_, p_204760_2_, p_204760_3_, 0.2F));
	}

	public static class Piece extends TemplateStructurePiece {
		private final ResourceLocation structure;
		private final Rotation rotation;
		private final Mirror mirror;
		private final float integrity;

		public Piece(TemplateManager templateManager, ResourceLocation templateIn, BlockPos templatePositionIn, Rotation rotationIn, Mirror mirrorIn, float integrityIn) {
			super(AutumnityStructures.Pieces.MAPLE_WITCH_HUT_PIECE, 0);
			this.structure = templateIn;
			this.templatePosition = templatePositionIn;
			this.rotation = rotationIn;
			this.mirror = mirrorIn;
			this.integrity = integrityIn;
			this.func_204754_a(templateManager);
		}

		public Piece(TemplateManager templateManager, CompoundNBT tagCompound) {
			super(AutumnityStructures.Pieces.MAPLE_WITCH_HUT_PIECE, tagCompound);
			this.structure = new ResourceLocation(tagCompound.getString("Template"));
			this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
			this.mirror = Mirror.valueOf(tagCompound.getString("Mirror"));
			this.integrity = tagCompound.getFloat("Integrity");
			this.func_204754_a(templateManager);
		}

		@Override
		protected void readAdditional(CompoundNBT tagCompound) {
			super.readAdditional(tagCompound);
			tagCompound.putString("Template", this.structure.toString());
			tagCompound.putString("Rot", this.rotation.name());
			tagCompound.putString("Mirror", this.mirror.name());
			tagCompound.putFloat("Integrity", this.integrity);
		}

		private void func_204754_a(TemplateManager templateManager) {
			Template template = templateManager.getTemplateDefaulted(this.structure);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(this.mirror).setCenterOffset(MapleWitchHutPieces.STRUCTURE_OFFSET).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK).addProcessor(new IntegrityProcessor(this.integrity));

			this.setup(template, this.templatePosition, placementsettings);
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
			if ("chest".equals(function)) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				TileEntity tileentity = worldIn.getTileEntity(pos.down());
				if (tileentity instanceof ChestTileEntity) {
					((ChestTileEntity)tileentity).setLootTable(AutumnityLootTables.CHESTS_MAPLE_WITCH_HUT, rand.nextLong());
				}
			} else if (function.startsWith("witch")) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				WitchEntity witchentity = EntityType.WITCH.create(worldIn.getWorld());
				witchentity.enablePersistence();
				witchentity.setPosition((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D);
				witchentity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos((double)pos.getX(), (double)pos.getY(), (double)pos.getZ())), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
				worldIn.func_242417_l(witchentity);
			} else if (function.startsWith("cat")) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				CatEntity catentity = EntityType.CAT.create(worldIn.getWorld());
				catentity.enablePersistence();
				catentity.setPosition((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D);
				catentity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos((double)pos.getX(), (double)pos.getY(), (double)pos.getZ())), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
				worldIn.func_242417_l(catentity);
			}
		}

		@Override
		public boolean func_230383_a_(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_, Random p_230383_4_, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
			int i = 256;
			BlockPos blockpos = this.template.getSize();
			int k = blockpos.getX() * blockpos.getZ();
			if (k != 0) {
				BlockPos blockpos1 = this.templatePosition.add(blockpos.getX() - 1, 0, blockpos.getZ() - 1);

				for(BlockPos blockpos2 : BlockPos.getAllInBoxMutable(this.templatePosition, blockpos1)) {
					int l = p_230383_1_.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos2.getX(), blockpos2.getZ());
					i = Math.min(i, l);
				}
			}

			this.templatePosition = new BlockPos(this.templatePosition.getX(), i, this.templatePosition.getZ());
			return super.func_230383_a_(p_230383_1_, p_230383_2_, p_230383_3_, p_230383_4_, p_230383_5_, p_230383_6_, p_230383_7_);
		}
	}
}