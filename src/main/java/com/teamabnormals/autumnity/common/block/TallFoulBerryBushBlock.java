package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.autumnity.core.other.tags.AutumnityEntityTypeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.Nullable;

public class TallFoulBerryBushBlock extends DoublePlantBlock implements BonemealableBlock {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	private static final VoxelShape TOP_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 6.0D, 15.0D);
	private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public TallFoulBerryBushBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, 0));
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(ModList.get().isLoaded("berry_good") && AutumnityConfig.COMMON.foulBerriesRequirePips.get() ? AutumnityItems.FOUL_BERRY_PIPS.get() : AutumnityItems.FOUL_BERRIES.get());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		if (state.getValue(AGE) == 0 && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			return TOP_SHAPE;
		} else {
			return SHAPE;
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
		if (rand.nextInt(10) == 0) {
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
		int i = state.getValue(AGE);
		if (i < 3 && state.getValue(HALF) == DoubleBlockHalf.LOWER && worldIn.getRawBrightness(pos.above(), 0) >= 9 && CommonHooks.canCropGrow(worldIn, pos, state, rand.nextInt(4) == 0)) {
			worldIn.setBlock(pos, state.setValue(AGE, i + 1), 2);
			setHalfState(worldIn, pos, state, i + 1);

			CommonHooks.fireCropGrowPost(worldIn, pos, state);
		}
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity living && !entity.getType().is(AutumnityEntityTypeTags.FOUL_BERRY_IMMUNE)) {
			living.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
			if (!worldIn.isClientSide && !living.hasEffect(MobEffects.POISON) && !living.isSteppingCarefully()) {
				living.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
			}
		}
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_225533_6_) {
		int i = state.getValue(AGE);
		boolean flag = i == 3;
		if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) {
			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		} else if (i > 1) {
			popResource(worldIn, pos, new ItemStack(AutumnityItems.FOUL_BERRIES.get(), 2));
			worldIn.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
			worldIn.setBlock(pos, state.setValue(AGE, i - 1), 2);
			setHalfState(worldIn, pos, state, i - 1);
			return ItemInteractionResult.SUCCESS;
		} else {
			return super.useItemOn(stack, state, worldIn, pos, player, handIn, p_225533_6_);
		}
	}

	public void placeAt(LevelAccessor worldIn, BlockPos pos, int flags) {
		this.placeAt(worldIn, pos, 3, flags);
	}

	public void placeAt(LevelAccessor worldIn, BlockPos pos, int age, int flags) {
		worldIn.setBlock(pos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, age), flags);
		worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, age), flags);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HALF, AGE);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state) {
		return state.getValue(AGE) < 3;
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		int i = Math.min(3, state.getValue(AGE) + 1);
		worldIn.setBlock(pos, state.setValue(AGE, i), 2);
		setHalfState(worldIn, pos, state, i);
	}

	private static void setHalfState(Level worldIn, BlockPos pos, BlockState state, int age) {
		if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			if (worldIn.getBlockState(pos.below()).getBlock() == AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get()) {
				worldIn.setBlock(pos.below(), worldIn.getBlockState(pos.below()).setValue(AGE, age), 2);
			}
		} else {
			if (worldIn.getBlockState(pos.above()).getBlock() == AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get()) {
				worldIn.setBlock(pos.above(), worldIn.getBlockState(pos.above()).setValue(AGE, age), 2);
			}
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