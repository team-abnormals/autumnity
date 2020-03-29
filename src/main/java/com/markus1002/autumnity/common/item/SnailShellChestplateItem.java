package com.markus1002.autumnity.common.item;

import javax.annotation.Nullable;

import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class SnailShellChestplateItem extends ArmorItem
{
	public SnailShellChestplateItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
	{
		super(materialIn, slot, builder);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
	{
		if (player.isShiftKeyDown())
		{
			player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 10, 2, false, false, true));
		}
	}

	@Override
	@Nullable
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
	{
		return Reference.location("textures/models/armor/snail_shell_layer_1.png").toString();
	}
}