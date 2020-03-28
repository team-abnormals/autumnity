package com.markus1002.autumnity.client.renderer.entity;

import com.markus1002.autumnity.client.renderer.entity.model.SnailModel;
import com.markus1002.autumnity.common.entity.passive.SnailEntity;
import com.markus1002.autumnity.core.util.Reference;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnailRenderer extends MobRenderer<SnailEntity, SnailModel<SnailEntity>>
{
	private static final ResourceLocation COW_TEXTURES = Reference.location("textures/entity/snail.png");

	public SnailRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn, new SnailModel<>(), 0.5F);
	}

	protected ResourceLocation getEntityTexture(SnailEntity entity)
	{
		return COW_TEXTURES;
	}

	protected void applyRotations(SnailEntity entityLiving, float ageInTicks, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
		double d0 = (double) entityLiving.getShakingAnimationScale(partialTicks);
		double d1 = entityLiving.getShakeTicks() > 0 ? 1.0D : -1.0D;
		double d2 = Math.sin(6.3D * d0) * d1 * d0;
		GlStateManager.rotatef(6.0F * (float)d2, 0.0F, 0.0F, 1.0F);
	}
}