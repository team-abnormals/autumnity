package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.common.entity.passive.SnailEntity;
import com.minecraftabnormals.autumnity.core.other.AutumnityTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BreakableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class SnailSlimeBlock extends BreakableBlock {
	public static final BooleanProperty SLIPPERY = BooleanProperty.create("slippery");
	protected static final VoxelShape SHAPE = Block.box(0.0D, 1.0D, 0.0D, 16.0D, 14.0D, 16.0D);

	public SnailSlimeBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(SLIPPERY, Boolean.valueOf(false)));
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return !state.getValue(SLIPPERY) ? SHAPE : super.getCollisionShape(state, worldIn, pos, context);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(SLIPPERY, Boolean.valueOf(this.shouldBeSlippery(context.getClickedPos(), context.getLevel())));
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return stateIn.setValue(SLIPPERY, Boolean.valueOf(this.shouldBeSlippery(currentPos, worldIn)));
	}

	public final boolean shouldBeSlippery(BlockPos blockPos, IBlockReader iBlockReader) {
		for (Direction direction : Direction.values()) {
			BlockPos blockpos1 = blockPos.relative(direction);
			Block block = iBlockReader.getBlockState(blockpos1).getBlock();
			if (this.doesBlockMakeSlippery(blockpos1, block, iBlockReader)) {
				return true;
			}
		}
		return false;
	}

	public final boolean doesBlockMakeSlippery(BlockPos blockPos, Block block, IBlockReader iBlockReader) {
		FluidState fluidstate = iBlockReader.getFluidState(blockPos);
		return AutumnityTags.SLIPPERY_SNAIL_SLIME_BLOCKS.contains(block) || fluidstate.is(FluidTags.WATER);
	}

	public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		if (entityIn.isShiftKeyDown()) {
			super.fallOn(worldIn, pos, entityIn, fallDistance);
		} else {
			entityIn.causeFallDamage(fallDistance, 0.0F);
		}
	}

	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!state.getValue(SLIPPERY) && !(entityIn instanceof SnailEntity)) {
			if (entityIn.getBoundingBox().maxY <= pos.getY() + 0.0625D) {
				if (!entityIn.isShiftKeyDown()) {
					entityIn.makeStuckInBlock(state, new Vector3d(1.0D, 0.0F, 1.0D));
				}
			} else {
				entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(0.4D, 1.0D, 0.4D));
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(SLIPPERY);
	}

	@Override
	public float getSlipperiness(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity) {
		return state.getValue(SLIPPERY) ? 0.98F : 0.6F;
	}

	@Override
	public boolean isStickyBlock(BlockState state) {
		return !state.getValue(SLIPPERY);
	}

	@Override
	public boolean canStickTo(BlockState state, BlockState other) {
		if (other.getBlock() == Blocks.SLIME_BLOCK) return false;
		if (other.getBlock() == Blocks.HONEY_BLOCK) return false;
		if (other.getBlock() == ForgeRegistries.BLOCKS.getValue(new ResourceLocation("upgrade_aquatic", "mulberry_jam_block")))
			return false;
		if (other.getBlock() == ForgeRegistries.BLOCKS.getValue(new ResourceLocation("atmospheric", "aloe_gel_block")))
			return false;

		return super.canStickTo(state, other);
	}
}