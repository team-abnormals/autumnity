package com.teamabnormals.autumnity.common.block;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.autumnity.core.other.tags.AutumnityEntityTypeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.Nullable;

public class FoulBerryBushBlock extends BushBlock implements FoulBerryBush, BonemealableBlock {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
	private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D), Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D)};

	public FoulBerryBushBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}

	@Override
	protected MapCodec<? extends BushBlock> codec() {
		return null;
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		return FoulBerryBush.getCloneItemStack();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPES[state.getValue(AGE)];
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (state.getValue(AGE) == 1 && random.nextInt(5) == 0) {
			FoulBerryBush.addParticles(this.getShape(state, level, pos, CollisionContext.empty()), level, pos, random);
		}
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		if (level.getRawBrightness(pos.above(), 0) >= 9 && CommonHooks.canCropGrow(level, pos, state, rand.nextInt(4) == 0)) {
			if (state.getValue(AGE) == 0) {
				level.setBlock(pos, state.setValue(AGE, 1), 2);
			} else if (level.isEmptyBlock(pos.above())) {
				TallFoulBerryBushBlock tallBush = (TallFoulBerryBushBlock) AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get();
				tallBush.placeAt(level, pos, 0, 2);
			}
			CommonHooks.fireCropGrowPost(level, pos, state);
		}
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		FoulBerryBush.doStuckLogic(state, level, entity);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
		if (state.getValue(AGE) == 0) {
			level.setBlock(pos, state.setValue(AGE, 1), 2);
		} else if (level.isEmptyBlock(pos.above())) {
			TallFoulBerryBushBlock tallBush = (TallFoulBerryBushBlock) AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get();
			tallBush.placeAt(level, pos, 0, 2);
		}
	}

	@Nullable
	@Override
	public PathType getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
		if (entity == null || !entity.getType().is(AutumnityEntityTypeTags.FOUL_BERRY_IMMUNE)) {
			return PathType.DAMAGE_OTHER;
		}
		return super.getBlockPathType(state, world, pos, entity);
	}
}