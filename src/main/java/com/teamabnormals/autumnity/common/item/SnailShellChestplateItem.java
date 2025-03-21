package com.teamabnormals.autumnity.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class SnailShellChestplateItem extends ArmorItem {

	public SnailShellChestplateItem(Holder<ArmorMaterial> materialIn, ArmorItem.Type slot, Properties builder) {
		super(materialIn, slot, builder);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean selected) {
		if (slotId == EquipmentSlot.CHEST.getIndex() && entity instanceof LivingEntity living && !level.isClientSide() && entity.isCrouching() && !entity.isSpectator()) {
			living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 2, false, false, true));
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		tooltip.add((Component.translatable("item.autumnity.snail_shell_chestplate.whenSneaking").withStyle(ChatFormatting.GRAY)));
		tooltip.add(Component.translatable(MobEffects.DAMAGE_RESISTANCE.getDescriptionId()).withStyle(ChatFormatting.BLUE).append(" ").append(Component.translatable("potion.potency.2").withStyle(ChatFormatting.BLUE)));
		tooltip.add((Component.translatable("attribute.modifier.plus." + AttributeModifier.Operation.ADD.toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(10), Component.translatable(Attributes.KNOCKBACK_RESISTANCE.getDescriptionId()))).withStyle(ChatFormatting.BLUE));
	}
}