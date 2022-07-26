package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.entity.animal.SnailEntity;
import com.teamabnormals.autumnity.common.entity.animal.TurkeyEntity;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class FoulBerryBushBlock extends BushBlock implements BonemealableBlock {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
	private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D), Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D)};

	public FoulBerryBushBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}

	public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(AutumnityItems.FOUL_BERRIES.get());
	}

	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPES[state.getValue(AGE)];
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		if (stateIn.getValue(AGE) == 1 && rand.nextInt(5) == 0) {
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

	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
		if (worldIn.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(4) == 0)) {
			if (state.getValue(AGE) == 0) {
				worldIn.setBlock(pos, state.setValue(AGE, 1), 2);
			} else if (worldIn.isEmptyBlock(pos.above())) {
				TallFoulBerryBushBlock tallfoulberrybush = (TallFoulBerryBushBlock) AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get();
				tallfoulberrybush.placeAt(worldIn, pos, 0, 2);
			}
			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
		}
	}

	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BEE && entityIn.getType() != AutumnityEntityTypes.SNAIL.get() && entityIn.getType() != AutumnityEntityTypes.TURKEY.get()) {
			LivingEntity livingentity = ((LivingEntity) entityIn);
			entityIn.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
			if (!worldIn.isClientSide && !livingentity.hasEffect(MobEffects.POISON) && !livingentity.isShiftKeyDown()) {
				livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
			}
		}
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state) {
		if (state.getValue(AGE) == 0) {
			worldIn.setBlock(pos, state.setValue(AGE, 1), 2);
		} else if (worldIn.isEmptyBlock(pos.above())) {
			TallFoulBerryBushBlock tallfoulberrybush = (TallFoulBerryBushBlock) AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get();
			tallfoulberrybush.placeAt(worldIn, pos, 0, 2);
		}
	}

	@Nullable
	@Override
	public BlockPathTypes getAiPathNodeType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
		if (!(entity instanceof SnailEntity) && !(entity instanceof TurkeyEntity)) {
			return BlockPathTypes.DAMAGE_OTHER;
		}
		return super.getAiPathNodeType(state, world, pos, entity);
	}
}