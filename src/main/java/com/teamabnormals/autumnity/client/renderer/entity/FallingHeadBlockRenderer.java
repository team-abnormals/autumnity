package com.teamabnormals.autumnity.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabnormals.autumnity.common.entity.item.FallingHeadBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class FallingHeadBlockRenderer extends EntityRenderer<FallingHeadBlockEntity> {

	public FallingHeadBlockRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.5F;
	}

	@Override
	public void render(FallingHeadBlockEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		BlockState blockstate = entityIn.getBlockState();
		if (blockstate.getRenderShape() == RenderShape.MODEL) {
			Level world = entityIn.getLevel();
			if (blockstate != world.getBlockState(entityIn.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
				matrixStackIn.pushPose();
				BlockPos blockpos = new BlockPos(entityIn.getX(), entityIn.getBoundingBox().maxY, entityIn.getZ());
				matrixStackIn.translate(-0.5D, 0.0D, -0.5D);
				BlockRenderDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRenderer();
				for (net.minecraft.client.renderer.RenderType type : net.minecraft.client.renderer.RenderType.chunkBufferLayers()) {
					if (ItemBlockRenderTypes.canRenderInLayer(blockstate, type)) {
						ForgeHooksClient.setRenderType(type);
						blockrendererdispatcher.getModelRenderer().tesselateBlock(world, blockrendererdispatcher.getBlockModel(blockstate), blockstate, blockpos, matrixStackIn, bufferIn.getBuffer(type), false, new Random(), blockstate.getSeed(entityIn.getStartPos()), OverlayTexture.NO_OVERLAY);
					}
				}
				ForgeHooksClient.setRenderType(null);
				matrixStackIn.popPose();
				super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
			}
		}
	}

	@Override
	public ResourceLocation getTextureLocation(FallingHeadBlockEntity entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}