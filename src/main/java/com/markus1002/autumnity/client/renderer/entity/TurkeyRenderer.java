package com.markus1002.autumnity.client.renderer.entity;

import com.markus1002.autumnity.client.renderer.entity.model.TurkeyModel;
import com.markus1002.autumnity.common.entity.passive.TurkeyEntity;
import com.markus1002.autumnity.core.Reference;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurkeyRenderer extends MobRenderer<TurkeyEntity, TurkeyModel<TurkeyEntity>>
{
	private static final ResourceLocation TURKEY_TEXTURES = Reference.location("textures/entity/turkey.png");
	
	public TurkeyRenderer(EntityRendererManager manager)
	{
		super(manager, new TurkeyModel<>(), 0.5F);
	}

	@Override
	public ResourceLocation getEntityTexture(TurkeyEntity entity)
	{
		return TURKEY_TEXTURES;
	}
}