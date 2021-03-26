package com.minecraftabnormals.autumnity.client.renderer.entity;

import com.minecraftabnormals.autumnity.client.renderer.entity.model.SnailModel;
import com.minecraftabnormals.autumnity.common.entity.passive.SnailEntity;
import com.minecraftabnormals.autumnity.core.Autumnity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnailRenderer extends MobRenderer<SnailEntity, SnailModel<SnailEntity>> {
	private static final ResourceLocation SNAIL_TEXTURES = new ResourceLocation(Autumnity.MOD_ID, "textures/entity/snail/snail.png");
	private static final ResourceLocation SNAKE_SNAIL_TEXTURES = new ResourceLocation(Autumnity.MOD_ID, "textures/entity/snail/snake_snail.png");
	private static final ResourceLocation NAUTILUS_SNAIL_TEXTURES = new ResourceLocation(Autumnity.MOD_ID, "textures/entity/snail/nautilus_snail.png");

	public SnailRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new SnailModel<>(), 0.5F);
	}

	public ResourceLocation getEntityTexture(SnailEntity entity) {
		String s = TextFormatting.getTextWithoutFormattingCodes(entity.getName().getString().toLowerCase());
		if (s != null) {
			if ("snake".equals(s) || "snakeblock".equals(s) || "snake block".equals(s) || "snailblock".equals(s) || "snail block".equals(s)) {
				return SNAKE_SNAIL_TEXTURES;
			} else if ("nautilus".equals(s) || "nautilus snail".equals(s)) {
				return NAUTILUS_SNAIL_TEXTURES;
			}
		}

		return SNAIL_TEXTURES;
	}

	protected void applyRotations(SnailEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		if (!((double) entityLiving.limbSwingAmount < 0.01D)) {
			double d0 = (double) entityLiving.getShakingAnimationScale(partialTicks);
			double d1 = entityLiving.getShakeTicks() > 0 ? 1.0D : -1.0D;
			double d2 = Math.sin(6.3D * d0) * d1 * d0;
			matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(6.0F * (float) d2));
		}
	}
}