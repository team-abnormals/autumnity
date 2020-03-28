package com.markus1002.autumnity.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
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

		if (entityLiving instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) entityLiving;
			if (!player.abilities.isCreativeMode && !stack.isEmpty())
			{
				if (!player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE)))
				{
					player.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
				}
				return stack;
			}
		}

		return new ItemStack(Items.GLASS_BOTTLE);
	}

	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.DRINK;
	}
}