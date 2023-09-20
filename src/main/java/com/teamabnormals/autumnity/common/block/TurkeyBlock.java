package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.core.other.AutumnityEvents;
import com.teamabnormals.autumnity.core.other.tags.AutumnityItemTags;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.autumnity.core.registry.AutumnityItems.AutumnityFoods;
import com.teamabnormals.autumnity.core.registry.AutumnityMobEffects;
import com.teamabnormals.autumnity.core.registry.AutumnitySoundEvents;
import com.teamabnormals.blueprint.common.block.BlueprintFallingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.ModList;

public class TurkeyBlock extends BlueprintFallingBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty CHUNKS = IntegerProperty.create("chunks", 0, 4);
	public static final VoxelShape[] NORTH_SHAPE = new VoxelShape[]{Shapes.or(Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D), Block.box(1.0D, 3.0D, 7.0D, 15.0D, 7.0D, 13.0D)),
			Shapes.or(Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D), Block.box(13.0D, 3.0D, 7.0D, 15.0D, 7.0D, 13.0D)),
			Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D),
			Block.box(3.0D, 0.0D, 6.0D, 13.0D, 8.0D, 14.0D),
			Block.box(3.0D, 0.0D, 10.0D, 13.0D, 8.0D, 14.0D)};
	public static final VoxelShape[] SOUTH_SHAPE = new VoxelShape[]{Shapes.or(Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D), Block.box(1.0D, 3.0D, 3.0D, 15.0D, 7.0D, 9.0D)),
			Shapes.or(Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D), Block.box(1.0D, 3.0D, 3.0D, 3.0D, 7.0D, 9.0D)),
			Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 14.0D),
			Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 10.0D),
			Block.box(3.0D, 0.0D, 2.0D, 13.0D, 8.0D, 6.0D)};
	public static final VoxelShape[] WEST_SHAPE = new VoxelShape[]{Shapes.or(Block.box(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D), Block.box(7.0D, 3.0D, 1.0D, 13.0D, 7.0D, 15.0D)),
			Shapes.or(Block.box(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D), Block.box(7.0D, 3.0D, 1.0D, 13.0D, 7.0D, 3.0D)),
			Block.box(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
			Block.box(6.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
			Block.box(10.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D)};
	public static final VoxelShape[] EAST_SHAPE = new VoxelShape[]{Shapes.or(Block.box(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D), Block.box(3.0D, 3.0D, 1.0D, 9.0D, 7.0D, 15.0D)),
			Shapes.or(Block.box(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D), Block.box(3.0D, 3.0D, 13.0D, 9.0D, 7.0D, 15.0D)),
			Block.box(2.0D, 0.0D, 3.0D, 14.0D, 8.0D, 13.0D),
			Block.box(2.0D, 0.0D, 3.0D, 10.0D, 8.0D, 13.0D),
			Block.box(2.0D, 0.0D, 3.0D, 6.0D, 8.0D, 13.0D)};

	public TurkeyBlock(Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CHUNKS, 0));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		int i = state.getValue(CHUNKS);

		if (state.getValue(FACING) == Direction.NORTH)
			return NORTH_SHAPE[i];
		else if (state.getValue(FACING) == Direction.SOUTH)
			return SOUTH_SHAPE[i];
		else if (state.getValue(FACING) == Direction.WEST)
			return WEST_SHAPE[i];
		else
			return EAST_SHAPE[i];
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		if (worldIn.isClientSide) {
			if (this.eatTurkey(worldIn, pos, state, player, itemstack, handIn) == InteractionResult.SUCCESS) {
				return InteractionResult.SUCCESS;
			}

			if (itemstack.isEmpty()) {
				return InteractionResult.CONSUME;
			}
		}

		return this.eatTurkey(worldIn, pos, state, player, itemstack, handIn);
	}

	private InteractionResult eatTurkey(Level worldIn, BlockPos pos, BlockState state, Player player, ItemStack itemstack, InteractionHand hand) {
		int i = state.getValue(CHUNKS);
		boolean flag = ModList.get().isLoaded("farmersdelight") ? itemstack.is(AutumnityItemTags.KNIVES) : itemstack.getItem() instanceof AxeItem;
		if (player.canEat(false) || flag) {
			if (flag) {
				popResource(worldIn, pos, new ItemStack(this.getLeg()));
				worldIn.playSound(player, pos, AutumnitySoundEvents.BLOCK_TURKEY_CUT.get(), SoundSource.BLOCKS, 1.0F, (worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.2F + 1.0F);
				itemstack.hurtAndBreak(1, player, (playerIn) -> {
					playerIn.broadcastBreakEvent(hand);
				});
			} else {
				ItemStack stack = this.getCloneItemStack(state, null, worldIn, pos, player);
				player.playSound(player.getEatingSound(stack), 1.0F, 1.0F + (worldIn.getRandom().nextFloat() - worldIn.getRandom().nextFloat()) * 0.4F);
				this.restoreHunger(worldIn, player);
			}
			if (i < 4) {
				worldIn.setBlock(pos, state.setValue(CHUNKS, i + 1), 3);
			} else {
				worldIn.removeBlock(pos, false);
			}

			return InteractionResult.SUCCESS;
		} else {
			return InteractionResult.PASS;
		}
	}

	protected void restoreHunger(LevelAccessor worldIn, Player player) {
		player.getFoodData().eat(AutumnityFoods.TURKEY.getNutrition(), AutumnityFoods.TURKEY.getSaturationModifier());

		if (!worldIn.isClientSide() && worldIn.getRandom().nextFloat() < 0.1F) {
			player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0));
		}

		int i = AutumnityFoods.TURKEY.getNutrition();
		int j = i == 1 ? i : (int) (i * 0.5F);

		if (player.hasEffect(AutumnityMobEffects.FOUL_TASTE.get())) {
			player.getFoodData().eat(j, 0.0F);
			AutumnityEvents.updateFoulTaste(player);
		}
	}

	protected Item getLeg() {
		return AutumnityItems.TURKEY_PIECE.get();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, CHUNKS);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
		return 10 - blockState.getValue(CHUNKS) * 2;
	}
}