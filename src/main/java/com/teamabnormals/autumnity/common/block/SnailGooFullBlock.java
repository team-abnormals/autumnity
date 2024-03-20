package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.common.entity.animal.Snail;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class SnailGooFullBlock extends HalfTransparentBlock {
	public static final BooleanProperty SLIPPERY = BooleanProperty.create("slippery");
	protected static final VoxelShape SHAPE = Block.box(0.0D, 1.0D, 0.0D, 16.0D, 14.0D, 16.0D);

	public SnailGooFullBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(SLIPPERY, false));
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return !state.getValue(SLIPPERY) ? SHAPE : super.getCollisionShape(state, worldIn, pos, context);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(SLIPPERY, this.shouldBeSlippery(context.getClickedPos(), context.getLevel()));
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return stateIn.setValue(SLIPPERY, this.shouldBeSlippery(currentPos, worldIn));
	}

	public final boolean shouldBeSlippery(BlockPos blockPos, BlockGetter iBlockReader) {
		for (Direction direction : Direction.values()) {
			BlockPos blockpos1 = blockPos.relative(direction);
			if (this.doesBlockMakeSlippery(blockpos1, iBlockReader.getBlockState(blockpos1), iBlockReader)) {
				return true;
			}
		}
		return false;
	}

	public final boolean doesBlockMakeSlippery(BlockPos blockPos, BlockState state, BlockGetter iBlockReader) {
		FluidState fluidstate = iBlockReader.getFluidState(blockPos);
		return state.is(AutumnityBlockTags.SLIPPERY_SNAIL_GOO_BLOCKS) || fluidstate.is(FluidTags.WATER);
	}

	@Override
	public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
		if (entityIn.isShiftKeyDown()) {
			super.fallOn(worldIn, state, pos, entityIn, fallDistance);
		} else {
			entityIn.causeFallDamage(fallDistance, 0.0F, worldIn.damageSources().fall());
		}
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (!state.getValue(SLIPPERY) && !(entityIn instanceof Snail)) {
			if (entityIn.getBoundingBox().maxY <= pos.getY() + 0.0625D) {
				if (!entityIn.isShiftKeyDown()) {
					entityIn.makeStuckInBlock(state, new Vec3(1.0D, 0.0F, 1.0D));
				}
			} else {
				entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(0.4D, 1.0D, 0.4D));
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(SLIPPERY);
	}

	@Override
	public float getFriction(BlockState state, LevelReader world, BlockPos pos, @Nullable Entity entity) {
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