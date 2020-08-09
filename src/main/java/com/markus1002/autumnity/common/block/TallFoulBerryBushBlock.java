package com.markus1002.autumnity.common.block;

import java.util.Random;

import com.markus1002.autumnity.core.registry.AutumnityBlocks;
import com.markus1002.autumnity.core.registry.AutumnityItems;

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
import net.minecraft.particles.ParticleTypes;
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

public class TallFoulBerryBushBlock extends DoublePlantBlock implements IGrowable
{
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
	private static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 6.0D, 15.0D);
	private static final VoxelShape SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public TallFoulBerryBushBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER).with(AGE, Integer.valueOf(0)));
	}

	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state)
	{
		return new ItemStack(AutumnityItems.FOUL_BERRIES.get());
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

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (rand.nextInt(10) == 0)
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
			LivingEntity livingentity = ((LivingEntity) entityIn);
			livingentity.setMotionMultiplier(state, new Vector3d((double)0.8F, 0.75D, (double)0.8F));
			if (!worldIn.isRemote && !livingentity.isPotionActive(Effects.POISON) && !livingentity.isSneaking())
			{
				livingentity.addPotionEffect(new EffectInstance(Effects.POISON, 120));
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
			spawnAsEntity(worldIn, pos, new ItemStack(AutumnityItems.FOUL_BERRIES.get(), 2));
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

	public void placeAt(IWorld worldIn, BlockPos pos, int flags)
	{
		this.placeAt(worldIn, pos, 3, flags);
	}

	public void placeAt(IWorld worldIn, BlockPos pos, int age, int flags)
	{
		worldIn.setBlockState(pos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(AGE, Integer.valueOf(age)), flags);
		worldIn.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(AGE, Integer.valueOf(age)), flags);
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
			if (worldIn.getBlockState(pos.down()).getBlock() == AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get())
			{
				worldIn.setBlockState(pos.down(), worldIn.getBlockState(pos.down()).with(AGE, Integer.valueOf(age)), 2);
			}
		}
		else
		{
			if (worldIn.getBlockState(pos.up()).getBlock() == AutumnityBlocks.TALL_FOUL_BERRY_BUSH.get())
			{
				worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(AGE, Integer.valueOf(age)), 2);
			}
		}
	}
}