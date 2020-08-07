package com.markus1002.autumnity.common.block;

import java.util.Random;

import com.markus1002.autumnity.core.registry.ModBlocks;
import com.markus1002.autumnity.core.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
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

public class FoulBerryBushBlock extends BushBlock implements IGrowable
{
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_1;
	private static final VoxelShape[] SHAPES = new VoxelShape[]{Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D), Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D)};

	public FoulBerryBushBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)));
	}

	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state)
	{
		return new ItemStack(ModItems.FOUL_BERRIES.get());
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return SHAPES[state.get(AGE)];
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (stateIn.get(AGE) == 1 && rand.nextInt(5) == 0)
		{
			VoxelShape voxelshape = this.getShape(stateIn, worldIn, pos, ISelectionContext.dummy());
			Vector3d vector3d = voxelshape.getBoundingBox().getCenter();
			double d0 = (double)pos.getX() + vector3d.x;
			double d1 = (double)pos.getZ() + vector3d.z;

			int i = Effects.POISON.getLiquidColor();
			double d2 = (double)(i >> 16 & 255) / 255.0D;
			double d3 = (double)(i >> 8 & 255) / 255.0D;
			double d4 = (double)(i >> 0 & 255) / 255.0D;

			worldIn.addParticle(ParticleTypes.ENTITY_EFFECT, d0 + (double)(rand.nextFloat() / 5.0F), (double)pos.getY() + (0.5D - (double)rand.nextFloat()), d1 + (double)(rand.nextFloat() / 5.0F), d2, d3, d4);
		}
	}
	
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
	{
		if (worldIn.getLightSubtracted(pos.up(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(4) == 0))
		{
			if (state.get(AGE) == 0)
			{
				worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(1)), 2);
			}
			else if (worldIn.isAirBlock(pos.up()))
			{
				TallFoulBerryBushBlock tallfoulberrybush = (TallFoulBerryBushBlock)ModBlocks.TALL_FOUL_BERRY_BUSH.get();
				tallfoulberrybush.placeAt(worldIn, pos, 0, 2);
			}
			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
		}
	}

	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
	{
		if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BEE)
		{
			LivingEntity livingentity = ((LivingEntity) entityIn);
			entityIn.setMotionMultiplier(state, new Vector3d((double)0.8F, 0.75D, (double)0.8F));
			if (!worldIn.isRemote && !livingentity.isPotionActive(Effects.POISON) && !livingentity.isSneaking())
			{
				livingentity.addPotionEffect(new EffectInstance(Effects.POISON, 120));
			}
		}
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(AGE);
	}

	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
	{
		return true;
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state)
	{
		return true;
	}

	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state)
	{
		if (state.get(AGE) == 0)
		{
			worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(1)), 2);
		}
		else if (worldIn.isAirBlock(pos.up()))
		{
			TallFoulBerryBushBlock tallfoulberrybush = (TallFoulBerryBushBlock)ModBlocks.TALL_FOUL_BERRY_BUSH.get();
			tallfoulberrybush.placeAt(worldIn, pos, 0, 2);
		}
	}
}