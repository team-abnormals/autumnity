package com.teamabnormals.autumnity.client.renderer.entity;

import com.teamabnormals.autumnity.client.model.TurkeyModel;
import com.teamabnormals.autumnity.common.entity.animal.Turkey;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurkeyRenderer extends MobRenderer<Turkey, TurkeyModel<Turkey>> {
	private static final ResourceLocation TURKEY_TEXTURES = new ResourceLocation(Autumnity.MOD_ID, "textures/entity/turkey.png");

	public TurkeyRenderer(EntityRendererProvider.Context context) {
		super(context, new TurkeyModel<>(context.bakeLayer(AutumnityModelLayers.TURKEY)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(Turkey entity) {
		return TURKEY_TEXTURES;
	}
}