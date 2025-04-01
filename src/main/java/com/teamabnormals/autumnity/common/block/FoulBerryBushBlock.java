package com.teamabnormals.autumnity.common.block;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.autumnity.core.other.tags.AutumnityEntityTypeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.Nullable;

public class FoulBerryBushBlock extends BushBlock implements BonemealableBlock {
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
	public ItemStack getCloneItemStack(LevelReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(ModList.get().isLoaded("berry_good") && AutumnityConfig.COMMON.foulBerriesRequirePips.get() ? AutumnityItems.FOUL_BERRY_PIPS.get() : AutumnityItems.FOUL_BERRIES.get());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPES[state.getValue(AGE)];
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
		if (stateIn.getValue(AGE) == 1 && rand.nextInt(5) == 0) {
			VoxelShape voxelshape = this.getShape(stateIn, worldIn, pos, CollisionContext.empty());
			Vec3 vector3d = voxelshape.bounds().getCenter();
			double d0 = (double) pos.getX() + vector3d.x;
			double d1 = (double) pos.getZ() + vector3d.z;

			int i = MobEffects.POISON.value().getColor();
			float f = (float) (i >> 16 & 0xFF) / 255.0F;
			float f1 = (float) (i >> 8 & 0xFF) / 255.0F;
			float f2 = (float) (i >> 0 & 0xFF) / 255.0F;

			worldIn.addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, f, f1, f2), d0 + (double) (rand.nextFloat() / 5.0F), (double) pos.getY() + (0.5D - (double) rand.nextFloat()), d1 + (double) (rand.nextFloat() / 5.0F), 0.0, 0.0, 0.0);
		}
	}

	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
		if (worldIn.getRawBrightness(pos.above(), 0) >= 9 && CommonHooks.canCropGrow(worldIn, pos, state, rand.nextInt(4) == 0)) {
			if (state.getValue(AGE) == 0) {
				worldIn.setBlock(pos, state.setValue(AGE, 1), 2);
			} else if (worldIn.isEmptyBlock(pos.above())) {
				TallFoulBerryBushBlock tallfoulberrybush = (TallFoulBerryBushBlock) AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get();
				tallfoulberrybush.placeAt(worldIn, pos, 0, 2);
			}
			CommonHooks.fireCropGrowPost(worldIn, pos, state);
		}
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity living && !entity.getType().is(AutumnityEntityTypeTags.FOUL_BERRY_IMMUNE)) {
			entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
			if (!worldIn.isClientSide && !living.hasEffect(MobEffects.POISON) && !living.isSteppingCarefully()) {
				living.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		if (state.getValue(AGE) == 0) {
			worldIn.setBlock(pos, state.setValue(AGE, 1), 2);
		} else if (worldIn.isEmptyBlock(pos.above())) {
			TallFoulBerryBushBlock tallfoulberrybush = (TallFoulBerryBushBlock) AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get();
			tallfoulberrybush.placeAt(worldIn, pos, 0, 2);
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