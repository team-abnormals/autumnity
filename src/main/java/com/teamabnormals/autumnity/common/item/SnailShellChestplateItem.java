package com.teamabnormals.autumnity.common.item;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class SnailShellChestplateItem extends ArmorItem {

	public SnailShellChestplateItem(ArmorMaterial materialIn, ArmorItem.Type slot, Properties builder) {
		super(materialIn, slot, builder);
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		if (player.isShiftKeyDown()) {
			player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 2, false, false, true));
		}
	}

	@Override
	@Nullable
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return new ResourceLocation(Autumnity.MOD_ID, "textures/models/armor/snail_shell_layer_1.png").toString();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add((Component.translatable("item.autumnity.snail_shell_chestplate.whenSneaking").withStyle(ChatFormatting.GRAY)));
		tooltip.add(Component.translatable(MobEffects.DAMAGE_RESISTANCE.getDescriptionId()).withStyle(ChatFormatting.BLUE).append(" ").append(Component.translatable("potion.potency.2").withStyle(ChatFormatting.BLUE)));
		tooltip.add((Component.translatable("attribute.modifier.plus." + AttributeModifier.Operation.ADDITION.toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(10), Component.translatable(Attributes.KNOCKBACK_RESISTANCE.getDescriptionId()))).withStyle(ChatFormatting.BLUE));
	}
}