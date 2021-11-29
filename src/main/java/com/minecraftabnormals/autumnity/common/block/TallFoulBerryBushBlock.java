package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.common.entity.passive.SnailEntity;
import com.minecraftabnormals.autumnity.common.entity.passive.TurkeyEntity;
import com.minecraftabnormals.autumnity.core.registry.AutumnityBlocks;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEntities;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class TallFoulBerryBushBlock extends DoublePlantBlock implements IGrowable {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	private static final VoxelShape TOP_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 6.0D, 15.0D);
	private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public TallFoulBerryBushBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, Integer.valueOf(0)));
	}

	public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(AutumnityItems.FOUL_BERRIES.get());
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.getValue(AGE) == 0 && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			return TOP_SHAPE;
		} else {
			return SHAPE;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(10) == 0) {
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
		int i = state.getValue(AGE);
		if (i < 3 && state.getValue(HALF) == DoubleBlockHalf.LOWER && worldIn.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(4) == 0)) {
			worldIn.setBlock(pos, state.setValue(AGE, i + 1), 2);
			setHalfState(worldIn, pos, state, i + 1);

			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
		}
	}

	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BEE && entityIn.getType() != AutumnityEntities.SNAIL.get() && entityIn.getType() != AutumnityEntities.TURKEY.get()) {
			LivingEntity livingentity = ((LivingEntity) entityIn);
			livingentity.makeStuckInBlock(state, new Vector3d(0.8F, 0.75D, 0.8F));
			if (!worldIn.isClientSide && !livingentity.hasEffect(Effects.POISON) && !livingentity.isShiftKeyDown()) {
				livingentity.addEffect(new EffectInstance(Effects.POISON, 60));
			}
		}
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
		int i = state.getValue(AGE);
		boolean flag = i == 3;
		if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) {
			return ActionResultType.PASS;
		} else if (i > 1) {
			popResource(worldIn, pos, new ItemStack(AutumnityItems.FOUL_BERRIES.get(), 2));
			worldIn.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
			worldIn.setBlock(pos, state.setValue(AGE, i - 1), 2);
			setHalfState(worldIn, pos, state, i - 1);
			return ActionResultType.SUCCESS;
		} else {
			return super.use(state, worldIn, pos, player, handIn, p_225533_6_);
		}
	}

	public void placeAt(IWorld worldIn, BlockPos pos, int flags) {
		this.placeAt(worldIn, pos, 3, flags);
	}

	public void placeAt(IWorld worldIn, BlockPos pos, int age, int flags) {
		worldIn.setBlock(pos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, age), flags);
		worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, age), flags);
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HALF, AGE);
	}

	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return state.getValue(AGE) < 3;
	}

	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		int i = Math.min(3, state.getValue(AGE) + 1);
		worldIn.setBlock(pos, state.setValue(AGE, i), 2);
		setHalfState(worldIn, pos, state, i);
	}

	public Block.OffsetType getOffsetType() {
		return Block.OffsetType.NONE;
	}

	private static void setHalfState(World worldIn, BlockPos pos, BlockState state, int age) {
		if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			if (worldIn.getBlockState(pos.below()).getBlock() == AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get()) {
				worldIn.setBlock(pos.below(), worldIn.getBlockState(pos.below()).setValue(AGE, Integer.valueOf(age)), 2);
			}
		} else {
			if (worldIn.getBlockState(pos.above()).getBlock() == AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get()) {
				worldIn.setBlock(pos.above(), worldIn.getBlockState(pos.above()).setValue(AGE, Integer.valueOf(age)), 2);
			}
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