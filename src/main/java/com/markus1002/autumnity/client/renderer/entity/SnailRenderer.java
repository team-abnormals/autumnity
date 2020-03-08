package com.markus1002.autumnity.client.renderer.entity;

import com.markus1002.autumnity.client.renderer.entity.model.SnailModel;
import com.markus1002.autumnity.common.entity.passive.SnailEntity;
import com.markus1002.autumnity.core.util.Reference;

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
}