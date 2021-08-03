package com.minecraftabnormals.autumnity.common.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

public class MapleWitchHutStructure extends Structure<NoFeatureConfig> {
	private static final List<MobSpawnInfo.Spawners> SPAWN_LIST = ImmutableList.of(new MobSpawnInfo.Spawners(EntityType.WITCH, 1, 1, 1));
	private static final List<MobSpawnInfo.Spawners> CREATURE_SPAWN_LIST = ImmutableList.of(new MobSpawnInfo.Spawners(EntityType.CAT, 1, 1, 1));

	public MapleWitchHutStructure(Codec<NoFeatureConfig> p_i231989_1_) {
		super(p_i231989_1_);
	}

	@Override
	public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
		return MapleWitchHutStructure.Start::new;
	}

	@Override
	public GenerationStage.Decoration step() {
		return GenerationStage.Decoration.SURFACE_STRUCTURES;
	}

	@Override
	public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
		return SPAWN_LIST;
	}

	@Override
	public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {
		return CREATURE_SPAWN_LIST;
	}

	public static class Start extends StructureStart<NoFeatureConfig> {
		public Start(Structure<NoFeatureConfig> p_i225817_1_, int p_i225817_2_, int p_i225817_3_, MutableBoundingBox p_i225817_4_, int p_i225817_5_, long p_i225817_6_) {
			super(p_i225817_1_, p_i225817_2_, p_i225817_3_, p_i225817_4_, p_i225817_5_, p_i225817_6_);
		}

		public void generatePieces(DynamicRegistries p_230364_1_, ChunkGenerator p_230364_2_, TemplateManager p_230364_3_, int p_230364_4_, int p_230364_5_, Biome p_230364_6_, NoFeatureConfig p_230364_7_) {
			ChunkPos chunkpos = new ChunkPos(p_230364_4_, p_230364_5_);
			int i = chunkpos.getMinBlockX() + this.random.nextInt(16);
			int j = chunkpos.getMinBlockZ() + this.random.nextInt(16);
			int k = p_230364_2_.getSeaLevel();
			int l = k + this.random.nextInt(p_230364_2_.getGenDepth() - 2 - k);
			IBlockReader iblockreader = p_230364_2_.getBaseColumn(i, j);

			for(BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(i, l, j); l > k; --l) {
				BlockState blockstate = iblockreader.getBlockState(blockpos$mutable);
				blockpos$mutable.move(Direction.DOWN);
				BlockState blockstate1 = iblockreader.getBlockState(blockpos$mutable);
				if (blockstate.isAir() && blockstate1.isFaceSturdy(iblockreader, blockpos$mutable, Direction.UP)) {
					break;
				}
			}

			if (l > k) {
				Rotation rotation = Rotation.getRandom(this.random);
				Mirror mirror = this.random.nextFloat() < 0.5F ? Mirror.NONE : Mirror.FRONT_BACK;
				
				MapleWitchHutPieces.addPieces(p_230364_3_, new BlockPos(i, l, j), rotation, mirror, this.pieces, this.random);
				this.calculateBoundingBox();
			}
		}
	}
}