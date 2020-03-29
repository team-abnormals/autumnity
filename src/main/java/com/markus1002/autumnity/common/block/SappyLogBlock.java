package com.markus1002.autumnity.common.block;

import com.markus1002.autumnity.core.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class SappyLogBlock extends LogBlock
{
	private final Block saplessBlock;

	public SappyLogBlock(Block saplessBlockIn, MaterialColor verticalColorIn, Properties properties)
	{
		super(verticalColorIn, properties);
		this.saplessBlock = saplessBlockIn;
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		ItemStack itemstack = player.getHeldItem(handIn);
		if (itemstack.isEmpty())
		{
			return ActionResultType.PASS;
		}
		else
		{
			Item item = itemstack.getItem();
			if (item == Items.GLASS_BOTTLE)
			{
				if (!worldIn.isRemote)
				{
					if (!player.abilities.isCreativeMode)
					{
						ItemStack itemstack2 = new ItemStack(ModItems.SAP_BOTTLE.get());
						itemstack.shrink(1);
						if (itemstack.isEmpty())
						{
							player.setHeldItem(handIn, itemstack2);
						}
						else if (!player.inventory.addItemStackToInventory(itemstack2))
						{
							player.dropItem(itemstack2, false);
						}
						else if (player instanceof ServerPlayerEntity)
						{
							((ServerPlayerEntity)player).sendContainerToPlayer(player.container);
						}
					}

					worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					worldIn.setBlockState(pos, saplessBlock.getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)), 11);
				}

				return ActionResultType.SUCCESS;
			}

			return ActionResultType.PASS;
		}
	}
}