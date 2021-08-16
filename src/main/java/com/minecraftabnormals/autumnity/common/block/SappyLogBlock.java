package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class SappyLogBlock extends RotatedPillarBlock {
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.STRIPPED_WARPED_STEM);
	private final Supplier<Block> saplessBlock;

	public SappyLogBlock(RegistryObject<Block> saplessBlockIn, Properties properties) {
		super(properties);
		this.saplessBlock = saplessBlockIn;
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		if (itemstack.isEmpty()) {
			return ActionResultType.PASS;
		} else {
			Item item = itemstack.getItem();
			if (item == Items.GLASS_BOTTLE) {
				if (!worldIn.isClientSide) {
					if (!player.abilities.instabuild) {
						ItemStack itemstack2 = new ItemStack(AutumnityItems.SAP_BOTTLE.get());
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							player.setItemInHand(handIn, itemstack2);
						} else if (!player.inventory.add(itemstack2)) {
							player.drop(itemstack2, false);
						} else if (player instanceof ServerPlayerEntity) {
							((ServerPlayerEntity) player).refreshContainer(player.inventoryMenu);
						}
					}

					worldIn.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					worldIn.setBlock(pos, saplessBlock.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 11);
				}

				return ActionResultType.SUCCESS;
			}

			return ActionResultType.PASS;
		}
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}