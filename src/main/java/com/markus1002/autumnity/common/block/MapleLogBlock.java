package com.markus1002.autumnity.common.block;

import java.util.function.Supplier;

import com.teamabnormals.abnormals_core.common.blocks.wood.AbnormalsLogBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class MapleLogBlock extends AbnormalsLogBlock
{
	private final Supplier<Block> sappyBlock;
	
	public MapleLogBlock(Supplier<Block> strippedBlock, Supplier<Block> sappyBlock, Properties properties)
	{
		super(strippedBlock, properties);
		this.sappyBlock = sappyBlock;
	}

	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result)
	{
		if(player.getHeldItem(hand).getItem() instanceof AxeItem && world.getRandom().nextInt(4) == 0)
		{
			world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.setBlockState(pos, this.sappyBlock.get().getDefaultState().with(AXIS, state.get(AXIS)));
			return ActionResultType.SUCCESS;
		}

		return super.onBlockActivated(state, world, pos, player, hand, result);
	}
}