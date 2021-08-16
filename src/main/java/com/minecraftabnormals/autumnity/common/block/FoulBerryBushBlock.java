package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.common.entity.passive.SnailEntity;
import com.minecraftabnormals.autumnity.common.entity.passive.TurkeyEntity;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEntities;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class FoulBerryBushBlock extends BushBlock implements IGrowable {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
	private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D), Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D)};

	public FoulBerryBushBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
	}

	public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(AutumnityItems.FOUL_BERRIES.get());
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPES[state.getValue(AGE)];
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.getValue(AGE) == 1 && rand.nextInt(5) == 0) {
			VoxelShape voxelshape = this.getShape(stateIn, worldIn, pos, ISelectionContext.empty());
			Vector3d vector3d = voxelshape.bounds().getCenter();
			double d0 = (double) pos.getX() + vector3d.x;
			double d1 = (double) pos.getZ() + vector3d.z;

			int i = Effects.POISON.getColor();
			double d2 = (double) (i >> 16 & 255) / 255.0D;
			double d3 = (double) (i >> 8 & 255) / 255.0D;
			double d4 = (double) (i >> 0 & 255) / 255.0D;

			worldIn.addParticle(ParticleTypes.ENTITY_EFFECT, d0 + (double) (rand.nextFloat() / 5.0F), (double) pos.getY() + (0.5D - (double) rand.nextFloat()), d1 + (double) (rand.nextFloat() / 5.0F), d2, d3, d4);
		}
	}

	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (worldIn.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(4) == 0)) {
			if (state.getValue(AGE) == 0) {
				worldIn.setBlock(pos, state.setValue(AGE, Integer.valueOf(1)), 2);
			} else if (worldIn.isEmptyBlock(pos.above())) {
				TallFoulBerryBushBlock tallfoulberrybush = (TallFoulBerryBushBlock) AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get();
				tallfoulberrybush.placeAt(worldIn, pos, 0, 2);
			}
			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
		}
	}

	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BEE && entityIn.getType() != AutumnityEntities.SNAIL.get() && entityIn.getType() != AutumnityEntities.TURKEY.get()) {
			LivingEntity livingentity = ((LivingEntity) entityIn);
			entityIn.makeStuckInBlock(state, new Vector3d(0.8F, 0.75D, 0.8F));
			if (!worldIn.isClientSide && !livingentity.hasEffect(Effects.POISON) && !livingentity.isShiftKeyDown()) {
				livingentity.addEffect(new EffectInstance(Effects.POISON, 120));
			}
		}
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		if (state.getValue(AGE) == 0) {
			worldIn.setBlock(pos, state.setValue(AGE, 1), 2);
		} else if (worldIn.isEmptyBlock(pos.above())) {
			TallFoulBerryBushBlock tallfoulberrybush = (TallFoulBerryBushBlock) AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get();
			tallfoulberrybush.placeAt(worldIn, pos, 0, 2);
		}
	}

	@Nullable
	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
		if (!(entity instanceof SnailEntity) && !(entity instanceof TurkeyEntity)) {
			return PathNodeType.DAMAGE_OTHER;
		}
		return super.getAiPathNodeType(state, world, pos, entity);
	}
}