package com.markus1002.autumnity.core.util;

import com.markus1002.autumnity.core.registry.ModBlocks;
import com.markus1002.autumnity.core.registry.ModEffects;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler
{
	@SubscribeEvent
	public void handleBlockRightClick(PlayerInteractEvent.RightClickBlock event)
	{
		PlayerEntity playerentity = event.getPlayer();
		World world = playerentity.getEntityWorld();
		BlockPos blockpos = event.getPos();
		BlockState blockstate = world.getBlockState(blockpos);

		if (world.getRandom().nextInt(4) == 0 && event.getItemStack().getItem() instanceof AxeItem)
		{
			Block block = blockstate.getBlock();

			if (block == ModBlocks.MAPLE_LOG || block == ModBlocks.MAPLE_WOOD)
			{
				Block block1 = block == ModBlocks.MAPLE_LOG ? ModBlocks.SAPPY_MAPLE_LOG : ModBlocks.SAPPY_MAPLE_WOOD;
				world.playSound(playerentity, blockpos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (!world.isRemote)
				{
					world.setBlockState(blockpos, block1.getDefaultState().with(RotatedPillarBlock.AXIS, blockstate.get(RotatedPillarBlock.AXIS)), 11);
					if (playerentity != null)
					{
						event.getItemStack().damageItem(1, playerentity, (p_220040_1_) -> {
							p_220040_1_.sendBreakAnimation(event.getHand());
						});
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDamage(LivingDamageEvent event)
	{
		LivingEntity livingentity = event.getEntityLiving();
		Entity sourceentity = event.getSource().getImmediateSource();

		if (sourceentity != null && livingentity.isPotionActive(ModEffects.STURDINESS))
		{
			if (sourceentity instanceof IronGolemEntity)
			{
				livingentity.setMotion(livingentity.getMotion().add(0.0D, -(double)0.4, 0.0D));
			}
		}
	}
}