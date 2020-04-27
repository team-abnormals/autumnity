package com.markus1002.autumnity.client.renderer.entity;

import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;

public class SpatterRenderer extends CreeperRenderer
{
	private static final ResourceLocation SPATTER_CREEPER_TEXTURES = Reference.location("textures/entity/creeper/spatter.png");

	public SpatterRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn);
	}

	public ResourceLocation getEntityTexture(CreeperEntity entity)
	{
		return SPATTER_CREEPER_TEXTURES;
	}
}