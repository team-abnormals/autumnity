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
		
		/*
		if (!worldIn.isRemote)
		{
			for(EffectInstance effect : entityLiving.getActivePotionEffects())
			{
				entityLiving.addPotionEffect(new EffectInstance(effect.getPotion(), effect.getDuration() + 20, effect.getAmplifier(), effect.isAmbient(), effect.doesShowParticles()));
			}
		}
		*/
		/*
			if (effect.getPotion().getEffectType() == EffectType.HARMFUL)
			{
				if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent(entityLiving, effect))) continue;

				entityLiving.potionsNeedUpdate = true;
				if (!entityLiving.world.isRemote)
				{
					effect.getPotion().removeAttributesModifiersFromEntity(entityLiving, entityLiving.getAttributes(), effect.getAmplifier());
				}

				if (entityLiving instanceof ServerPlayerEntity)
				{
					ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entityLiving;
					serverplayerentity.connection.sendPacket(new SRemoveEntityEffectPacket(entityLiving.getEntityId(), effect.getPotion()));
					if (effect.getPotion() == Effects.LEVITATION)
					{
						serverplayerentity.levitationStartPos = null;
					}

					CriteriaTriggers.EFFECTS_CHANGED.trigger(serverplayerentity);
				}
			}
		 */

		return new ItemStack(Items.GLASS_BOTTLE);
	}

	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.DRINK;
	}
}