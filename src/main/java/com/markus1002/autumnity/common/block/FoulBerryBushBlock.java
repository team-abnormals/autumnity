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
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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

	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
	{
		super.tick(state, worldIn, pos, rand);
		if (worldIn.getLightSubtracted(pos.up(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(4) == 0))
		{
			if (state.get(AGE) == 0)
			{
				worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(1)), 2);
			}
			else if (worldIn.isAirBlock(pos.up()))
			{
				TallFoulBerryBushBlock tallfoulberrybush = (TallFoulBerryBushBlock)ModBlocks.TALL_FOUL_BERRY_BUSH.get();
				tallfoulberrybush.placeAt(worldIn, pos, 2);
			}
			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
		}
	}

	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
	{
		if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BEE)
		{
			entityIn.setMotionMultiplier(state, new Vec3d((double)0.8F, 0.75D, (double)0.8F));
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
			tallfoulberrybush.placeAt(worldIn, pos, 2);
		}
	}
}