package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.core.other.tags.AutumnityEntityTypeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.Nullable;

public class TallFoulBerryBushBlock extends DoublePlantBlock implements FoulBerryBush, BonemealableBlock {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	private static final VoxelShape TOP_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 6.0D, 15.0D);
	private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public TallFoulBerryBushBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, 0));
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		return FoulBerryBush.getCloneItemStack();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (state.getValue(AGE) == 0 && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			return TOP_SHAPE;
		} else {
			return SHAPE;
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(10) == 0) {
			FoulBerryBush.addParticles(this.getShape(state, level, pos, CollisionContext.empty()), level, pos, random);
		}
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		int i = state.getValue(AGE);
		if (i < 3 && state.getValue(HALF) == DoubleBlockHalf.LOWER && level.getRawBrightness(pos.above(), 0) >= 9 && CommonHooks.canCropGrow(level, pos, state, rand.nextInt(4) == 0)) {
			level.setBlock(pos, state.setValue(AGE, i + 1), 2);
			setHalfState(level, pos, state, i + 1);
			CommonHooks.fireCropGrowPost(level, pos, state);
		}
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		FoulBerryBush.doStuckLogic(state, level, entity);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		return state.getValue(AGE) != 3 && stack.is(Items.BONE_MEAL) ? ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION : super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
		if (FoulBerryBush.hasBerries(state)) {
			int i = state.getValue(AGE);
			Block.popResource(level, pos, new ItemStack(AutumnityItems.FOUL_BERRIES.get(), 2));
			level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
			BlockState newState = state.setValue(AGE, i - 1);
			level.setBlock(pos, newState, 2);
			setHalfState(level, pos, state, i - 1);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	}

	public static void setHalfState(Level level, BlockPos pos, BlockState state, int age) {
		if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			if (level.getBlockState(pos.below()).is(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get())) {
				level.setBlock(pos.below(), level.getBlockState(pos.below()).setValue(AGE, age), 2);
			}
		} else {
			if (level.getBlockState(pos.above()).is(AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get())) {
				level.setBlock(pos.above(), level.getBlockState(pos.above()).setValue(AGE, age), 2);
			}
		}
	}

	public void placeAt(LevelAccessor level, BlockPos pos, int age, int flags) {
		level.setBlock(pos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, age), flags);
		level.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, age), flags);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HALF, AGE);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return state.getValue(AGE) < 3;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
		int i = Math.min(3, state.getValue(AGE) + 1);
		level.setBlock(pos, state.setValue(AGE, i), 2);
		setHalfState(level, pos, state, i);
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