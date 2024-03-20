package com.teamabnormals.autumnity.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurkeyEggRenderer<T extends Entity & ItemSupplier> extends EntityRenderer<T> {
	private final net.minecraft.client.renderer.entity.ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
	private final float scale;
	private final boolean fullBright;

	public TurkeyEggRenderer(EntityRendererProvider.Context context, float scaleIn, boolean p_i226035_4_) {
		super(context);
		this.scale = scaleIn;
		this.fullBright = p_i226035_4_;
	}

	public TurkeyEggRenderer(EntityRendererProvider.Context context) {
		this(context, 1.0F, false);
	}

	@Override
	protected int getBlockLightLevel(T entityIn, BlockPos partialTicks) {
		return this.fullBright ? 15 : super.getBlockLightLevel(entityIn, partialTicks);
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		if (entityIn.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entityIn) < 12.25D)) {
			matrixStackIn.pushPose();
			matrixStackIn.scale(this.scale, this.scale, this.scale);
			matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
			matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F));
			this.itemRenderer.renderStatic(entityIn.getItem(), ItemDisplayContext.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, entityIn.level(), entityIn.getId());
			matrixStackIn.popPose();
			super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(Entity entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}