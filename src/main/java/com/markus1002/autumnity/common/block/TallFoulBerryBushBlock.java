package com.markus1002.autumnity.common.block;

import java.util.Random;

import com.markus1002.autumnity.core.registry.ModBlocks;
import com.markus1002.autumnity.core.registry.ModEffects;
import com.markus1002.autumnity.core.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TallFoulBerryBushBlock extends DoublePlantBlock implements IGrowable
{
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
	private static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 6.0D, 15.0D);
	private static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public TallFoulBerryBushBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)).with(HALF, DoubleBlockHalf.LOWER));
	}

	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state)
	{
		return new ItemStack(ModItems.FOUL_BERRIES.get());
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		if (state.get(AGE) == 0 && state.get(HALF) == DoubleBlockHalf.UPPER)
		{
			return TOP_SHAPE;
		}
		else
		{
			return SHAPE;
		}
	}

	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
	{
		super.tick(state, worldIn, pos, rand);
		int i = state.get(AGE);
		if (i < 3 && state.get(HALF) == DoubleBlockHalf.LOWER && worldIn.getLightSubtracted(pos.up(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(4) == 0))
		{
			worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(i + 1)), 2);
			setHalfState(worldIn, pos, state, i + 1);

			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
		}
	}

	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
	{
		if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.BEE)
		{
			entityIn.setMotionMultiplier(state, new Vec3d((double)0.8F, 0.75D, (double)0.8F));
			if (!worldIn.isRemote && state.get(AGE) > 1)
			{
				((LivingEntity) entityIn).addPotionEffect(new EffectInstance(ModEffects.STENCH, 800));
			}
		}
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_)
	{
		int i = state.get(AGE);
		boolean flag = i == 3;
		if (!flag && player.getHeldItem(handIn).getItem() == Items.BONE_MEAL)
		{
			return ActionResultType.PASS;
		}
		else if (i > 1)
		{
			int j = 2 + worldIn.rand.nextInt(2);
			spawnAsEntity(worldIn, pos, new ItemStack(ModItems.FOUL_BERRIES.get(), j + (flag ? 2 : 0)));
			worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
			worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(i - 1)), 2);
			setHalfState(worldIn, pos, state, i - 1);
			return ActionResultType.SUCCESS;
		}
		else
		{
			return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
		}
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HALF, AGE);
	}

	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
	{
		return state.get(AGE) < 3;
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state)
	{
		return true;
	}

	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state)
	{
		int i = Math.min(3, state.get(AGE) + 1);
		worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(i)), 2);
		setHalfState(worldIn, pos, state, i);
	}

	public Block.OffsetType getOffsetType()
	{
		return Block.OffsetType.NONE;
	}

	private static void setHalfState(World worldIn, BlockPos pos, BlockState state, int age)
	{
		if (state.get(HALF) == DoubleBlockHalf.UPPER)
		{
			if (worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.TALL_FOUL_BERRY_BUSH.get())
			{
				worldIn.setBlockState(pos.down(), worldIn.getBlockState(pos.down()).with(AGE, Integer.valueOf(age)), 2);
			}
		}
		else
		{
			if (worldIn.getBlockState(pos.up()).getBlock() == ModBlocks.TALL_FOUL_BERRY_BUSH.get())
			{
				worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(AGE, Integer.valueOf(age)), 2);
			}
		}
	}
}