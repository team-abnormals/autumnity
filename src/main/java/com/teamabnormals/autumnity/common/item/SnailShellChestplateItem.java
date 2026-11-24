package com.teamabnormals.autumnity.common.item;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = Autumnity.MOD_ID, value = Dist.CLIENT)
public class SnailShellChestplateItem extends ArmorItem {

	public SnailShellChestplateItem(Holder<ArmorMaterial> materialIn, ArmorItem.Type slot, Properties builder) {
		super(materialIn, slot, builder);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean selected) {
		if (entity instanceof LivingEntity living && living.getItemBySlot(EquipmentSlot.CHEST).equals(stack) && !level.isClientSide() && entity.isCrouching() && !entity.isSpectator()) {
			living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 2, false, false, true));
		}
	}

	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		if (event.getItemStack().is(AutumnityItems.SNAIL_SHELL_CHESTPLATE)) {
			event.getToolTip().add(Component.empty());
			event.getToolTip().add((Component.translatable("item.autumnity.snail_shell_chestplate.whenSneaking").withStyle(ChatFormatting.GRAY)));
			event.getToolTip().add(Component.translatable(MobEffects.DAMAGE_RESISTANCE.value().getDescriptionId()).withStyle(ChatFormatting.BLUE).append(" ").append(Component.translatable("potion.potency.2").withStyle(ChatFormatting.BLUE)));
			event.getToolTip().add((Component.translatable("attribute.modifier.plus." + Operation.ADD_VALUE.id(), ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(10), Component.translatable(Attributes.KNOCKBACK_RESISTANCE.value().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
		}
	}
}