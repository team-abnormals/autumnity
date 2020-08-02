package com.markus1002.autumnity.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.markus1002.autumnity.core.util.Reference;
import com.teamabnormals.abnormals_core.core.utils.ItemStackUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SnailShellChestplateItem extends ArmorItem
{
	public SnailShellChestplateItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder)
	{
		super(materialIn, slot, builder);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
	{
		if (player.isSneaking())
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
	
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
    	tooltip.add(StringTextComponent.EMPTY);
    	tooltip.add((new TranslationTextComponent("item.autumnity.snail_shell_chestplate.whenSneaking").func_240699_a_(TextFormatting.GRAY)));
    	tooltip.add(new TranslationTextComponent(Effects.RESISTANCE.getName()).func_240699_a_(TextFormatting.BLUE).func_240702_b_(" ").func_230529_a_(new TranslationTextComponent("potion.potency.2").func_240699_a_(TextFormatting.BLUE)));
    	tooltip.add((new TranslationTextComponent("attribute.modifier.plus." + AttributeModifier.Operation.ADDITION.getId(), ItemStack.DECIMALFORMAT.format(10), new TranslationTextComponent(Attributes.KNOCKBACK_RESISTANCE.func_233754_c_()))).func_240699_a_(TextFormatting.BLUE));
    }
    
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if(ItemStackUtils.isInGroup(this.asItem(), group))
		{
			int targetIndex = ItemStackUtils.findIndexOfItem(Items.TURTLE_HELMET, items);
			if(targetIndex != -1)
			{
				items.add(targetIndex + 1, new ItemStack(this));
			}
			else
			{
				super.fillItemGroup(group, items);
			}
		}
	}
}