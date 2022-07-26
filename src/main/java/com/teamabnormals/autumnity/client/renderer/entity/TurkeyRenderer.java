package com.teamabnormals.autumnity.client.renderer.entity;

import com.teamabnormals.autumnity.client.renderer.entity.model.TurkeyModel;
import com.teamabnormals.autumnity.common.entity.animal.TurkeyEntity;
import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurkeyRenderer extends MobRenderer<TurkeyEntity, TurkeyModel<TurkeyEntity>> {
	private static final ResourceLocation TURKEY_TEXTURES = new ResourceLocation(Autumnity.MOD_ID, "textures/entity/turkey.png");

	public TurkeyRenderer(EntityRendererProvider.Context context) {
		super(context, new TurkeyModel<>(context.bakeLayer(TurkeyModel.TURKEY_MODEL)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(TurkeyEntity entity) {
		return TURKEY_TEXTURES;
	}
}