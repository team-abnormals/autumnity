package com.markus1002.autumnity.common.item;

import com.markus1002.autumnity.common.entity.projectile.TurkeyEggEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class TurkeyEggItem extends Item
{
	public TurkeyEggItem(Item.Properties builder)
	{
		super(builder);
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isRemote)
		{
			TurkeyEggEntity turkeyeggentity = new TurkeyEggEntity(worldIn, playerIn);
			turkeyeggentity.setItem(itemstack);
			turkeyeggentity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
			worldIn.addEntity(turkeyeggentity);
		}

		playerIn.addStat(Stats.ITEM_USED.get(this));
		if (!playerIn.abilities.isCreativeMode)
		{
			itemstack.shrink(1);
		}

		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	}
}