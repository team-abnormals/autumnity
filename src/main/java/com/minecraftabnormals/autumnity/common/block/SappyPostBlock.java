package com.minecraftabnormals.autumnity.common.block;

import java.util.function.Supplier;

import com.minecraftabnormals.abnormals_core.common.blocks.wood.WoodPostBlock;
import com.minecraftabnormals.abnormals_core.core.util.BlockUtil;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import net.minecraftforge.fml.RegistryObject;

public class SappyPostBlock extends WoodPostBlock {
	private final Supplier<Block> saplessBlock;
	
	public SappyPostBlock(RegistryObject<Block> saplessBlockIn, Properties properties) {
		super(properties);
		this.saplessBlock = saplessBlockIn;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getHeldItem(handIn);
		if (itemstack.isEmpty()) {
			return ActionResultType.PASS;
		} else {
			Item item = itemstack.getItem();
			if (item == Items.GLASS_BOTTLE) {
				if (!worldIn.isRemote) {
					if (!player.abilities.isCreativeMode) {
						ItemStack itemstack2 = new ItemStack(AutumnityItems.SAP_BOTTLE.get());
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							player.setHeldItem(handIn, itemstack2);
						} else if (!player.inventory.addItemStackToInventory(itemstack2)) {
							player.dropItem(itemstack2, false);
						} else if (player instanceof ServerPlayerEntity) {
							((ServerPlayerEntity) player).sendContainerToPlayer(player.container);
						}
					}

					worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					worldIn.setBlockState(pos, BlockUtil.transferAllBlockStates(state, this.saplessBlock.get().getDefaultState()));
				}

				return ActionResultType.SUCCESS;
			}

			return ActionResultType.PASS;
		}
	}
}
