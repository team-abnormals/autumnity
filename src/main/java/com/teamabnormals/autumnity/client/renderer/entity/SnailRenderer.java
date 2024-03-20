package com.teamabnormals.autumnity.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.teamabnormals.autumnity.client.model.SnailModel;
import com.teamabnormals.autumnity.common.entity.animal.Snail;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityModelLayers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnailRenderer extends MobRenderer<Snail, SnailModel<Snail>> {
	private static final ResourceLocation SNAIL_TEXTURES = new ResourceLocation(Autumnity.MOD_ID, "textures/entity/snail/snail.png");
	private static final ResourceLocation SNAKE_SNAIL_TEXTURES = new ResourceLocation(Autumnity.MOD_ID, "textures/entity/snail/snake_snail.png");
	private static final ResourceLocation NAUTILUS_SNAIL_TEXTURES = new ResourceLocation(Autumnity.MOD_ID, "textures/entity/snail/nautilus_snail.png");

	public SnailRenderer(EntityRendererProvider.Context context) {
		super(context, new SnailModel<>(context.bakeLayer(AutumnityModelLayers.SNAIL)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(Snail entity) {
		String s = ChatFormatting.stripFormatting(entity.getName().getString().toLowerCase());
		if (s != null) {
			if ("snake".equals(s) || "snakeblock".equals(s) || "snake block".equals(s) || "snailblock".equals(s) || "snail block".equals(s)) {
				return SNAKE_SNAIL_TEXTURES;
			} else if ("nautilus".equals(s) || "nautilus snail".equals(s)) {
				return NAUTILUS_SNAIL_TEXTURES;
			}
		}

		return SNAIL_TEXTURES;
	}

	@Override
	protected void setupRotations(Snail entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		double d0 = entityLiving.getShakeAmount(partialTicks);
		double d1 = d0 > 0 ? 2.0D : -2.0D;
		double d2 = Math.sin(12.6D * d0) * d1 * d0;
		matrixStackIn.mulPose(Axis.ZP.rotationDegrees(6.0F * (float) d2));
	}
}