package com.markus1002.autumnity.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class SyrupBottleItem extends Item
{
	public SyrupBottleItem(Properties properties)
	{
		super(properties);
	}

	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
	{
		super.onItemUseFinish(stack, worldIn, entityLiving);

		if (stack.isEmpty())
		{
			return new ItemStack(Items.GLASS_BOTTLE);
		}
		else
		{
			if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.isCreativeMode)
			{
				ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
				PlayerEntity playerentity = (PlayerEntity)entityLiving;
				if (!playerentity.inventory.addItemStackToInventory(itemstack))
				{
					playerentity.dropItem(itemstack, false);
				}
			}

			return stack;
		}
	}

	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.DRINK;
	}

	public SoundEvent func_225520_U__()
	{
		return SoundEvents.field_226141_eV_;
	}

	public SoundEvent func_225519_S__()
	{
		return SoundEvents.field_226141_eV_;
	}
}