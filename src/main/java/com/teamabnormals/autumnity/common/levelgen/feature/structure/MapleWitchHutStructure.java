//package com.minecraftabnormals.autumnity.common.levelgen.feature.structure;
//
//import com.google.common.collect.ImmutableList;
//import com.mojang.serialization.Codec;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.core.Direction;
//import net.minecraft.world.level.block.Mirror;
//import net.minecraft.world.level.block.Rotation;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.ChunkPos;
//import net.minecraft.world.level.levelgen.structure.BoundingBox;
//import net.minecraft.core.RegistryAccess;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.biome.MobSpawnSettings;
//import net.minecraft.world.level.chunk.ChunkGenerator;
//import net.minecraft.world.level.levelgen.GenerationStep;
//import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
//import net.minecraft.world.level.levelgen.feature.StructureFeature;
//import net.minecraft.world.level.levelgen.structure.StructureStart;
//import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
//
//import java.util.List;
//
//public class MapleWitchHutStructure extends StructureFeature<NoneFeatureConfiguration> {
//	private static final List<MobSpawnSettings.SpawnerData> SPAWN_LIST = ImmutableList.of(new MobSpawnSettings.SpawnerData(EntityType.WITCH, 1, 1, 1));
//	private static final List<MobSpawnSettings.SpawnerData> CREATURE_SPAWN_LIST = ImmutableList.of(new MobSpawnSettings.SpawnerData(EntityType.CAT, 1, 1, 1));
//
//	public MapleWitchHutStructure(Codec<NoneFeatureConfiguration> p_i231989_1_) {
//		super(p_i231989_1_);
//	}
//
//	@Override
//	public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
//		return MapleWitchHutStructure.Start::new;
//	}
//
//	@Override
//	public GenerationStep.Decoration step() {
//		return GenerationStep.Decoration.SURFACE_STRUCTURES;
//	}
//
//	@Override
//	public List<MobSpawnSettings.SpawnerData> getDefaultSpawnList() {
//		return SPAWN_LIST;
//	}
//
//	@Override
//	public List<MobSpawnSettings.SpawnerData> getDefaultCreatureSpawnList() {
//		return CREATURE_SPAWN_LIST;
//	}
//
//	public static class Start extends StructureStart<NoneFeatureConfiguration> {
//		public Start(StructureFeature<NoneFeatureConfiguration> p_i225817_1_, int p_i225817_2_, int p_i225817_3_, BoundingBox p_i225817_4_, int p_i225817_5_, long p_i225817_6_) {
//			super(p_i225817_1_, p_i225817_2_, p_i225817_3_, p_i225817_4_, p_i225817_5_, p_i225817_6_);
//		}
//
//		public void generatePieces(RegistryAccess p_230364_1_, ChunkGenerator p_230364_2_, StructureManager p_230364_3_, int p_230364_4_, int p_230364_5_, Biome p_230364_6_, NoneFeatureConfiguration p_230364_7_) {
//			ChunkPos chunkpos = new ChunkPos(p_230364_4_, p_230364_5_);
//			int i = chunkpos.getMinBlockX() + this.random.nextInt(16);
//			int j = chunkpos.getMinBlockZ() + this.random.nextInt(16);
//			int k = p_230364_2_.getSeaLevel();
//			int l = k + this.random.nextInt(p_230364_2_.getGenDepth() - 2 - k);
//			BlockGetter iblockreader = p_230364_2_.getBaseColumn(i, j);
//
//			for (BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos(i, l, j); l > k; --l) {
//				BlockState blockstate = iblockreader.getBlockState(blockpos$mutable);
//				blockpos$mutable.move(Direction.DOWN);
//				BlockState blockstate1 = iblockreader.getBlockState(blockpos$mutable);
//				if (blockstate.isAir() && blockstate1.isFaceSturdy(iblockreader, blockpos$mutable, Direction.UP)) {
//					break;
//				}
//			}
//
//			if (l > k) {
//				Rotation rotation = Rotation.getRandom(this.random);
//				Mirror mirror = this.random.nextFloat() < 0.5F ? Mirror.NONE : Mirror.FRONT_BACK;
//
//				MapleWitchHutPieces.addPieces(p_230364_3_, new BlockPos(i, l, j), rotation, mirror, this.pieces, this.random);
//				this.calculateBoundingBox();
//			}
//		}
//	}
//}