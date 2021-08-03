package com.minecraftabnormals.autumnity.common.item;

import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import com.minecraftabnormals.autumnity.core.Autumnity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class SnailShellChestplateItem extends ArmorItem {
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.TURTLE_HELMET);

	public SnailShellChestplateItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (player.isShiftKeyDown()) {
			player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 10, 2, false, false, true));
		}
	}

	@Override
	@Nullable
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return new ResourceLocation(Autumnity.MOD_ID, "textures/models/armor/snail_shell_layer_1.png").toString();
	}

	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add((new TranslationTextComponent("item.autumnity.snail_shell_chestplate.whenSneaking").withStyle(TextFormatting.GRAY)));
		tooltip.add(new TranslationTextComponent(Effects.DAMAGE_RESISTANCE.getDescriptionId()).withStyle(TextFormatting.BLUE).append(" ").append(new TranslationTextComponent("potion.potency.2").withStyle(TextFormatting.BLUE)));
		tooltip.add((new TranslationTextComponent("attribute.modifier.plus." + AttributeModifier.Operation.ADDITION.toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(10), new TranslationTextComponent(Attributes.KNOCKBACK_RESISTANCE.getDescriptionId()))).withStyle(TextFormatting.BLUE));
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}