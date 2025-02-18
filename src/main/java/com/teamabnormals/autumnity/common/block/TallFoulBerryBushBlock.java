package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.entity.animal.Snail;
import com.teamabnormals.autumnity.common.entity.animal.Turkey;
import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

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
	public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
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

			int i = MobEffects.POISON.getColor();
			double d2 = (double) (i >> 16 & 255) / 255.0D;
			double d3 = (double) (i >> 8 & 255) / 255.0D;
			double d4 = (double) (i >> 0 & 255) / 255.0D;

			worldIn.addParticle(ParticleTypes.ENTITY_EFFECT, d0 + (double) (rand.nextFloat() / 5.0F), (double) pos.getY() + (0.5D - (double) rand.nextFloat()), d1 + (double) (rand.nextFloat() / 5.0F), d2, d3, d4);
		}
	}

	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
		int i = state.getValue(AGE);
		if (i < 3 && state.getValue(HALF) == DoubleBlockHalf.LOWER && worldIn.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(4) == 0)) {
			worldIn.setBlock(pos, state.setValue(AGE, i + 1), 2);
			setHalfState(worldIn, pos, state, i + 1);

			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
		}
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BEE && entityIn.getType() != AutumnityEntityTypes.SNAIL.get() && entityIn.getType() != AutumnityEntityTypes.TURKEY.get()) {
			LivingEntity livingentity = ((LivingEntity) entityIn);
			livingentity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
			if (!worldIn.isClientSide && !livingentity.hasEffect(MobEffects.POISON) && !livingentity.isShiftKeyDown()) {
				livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
			}
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_225533_6_) {
		int i = state.getValue(AGE);
		boolean flag = i == 3;
		if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) {
			return InteractionResult.PASS;
		} else if (i > 1) {
			popResource(worldIn, pos, new ItemStack(AutumnityItems.FOUL_BERRIES.get(), 2));
			worldIn.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
			worldIn.setBlock(pos, state.setValue(AGE, i - 1), 2);
			setHalfState(worldIn, pos, state, i - 1);
			return InteractionResult.SUCCESS;
		} else {
			return super.use(state, worldIn, pos, player, handIn, p_225533_6_);
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
	public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
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
	public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
		if (!(entity instanceof Snail) && !(entity instanceof Turkey)) {
			return BlockPathTypes.DAMAGE_OTHER;
		}
		return super.getBlockPathType(state, world, pos, entity);
	}
}