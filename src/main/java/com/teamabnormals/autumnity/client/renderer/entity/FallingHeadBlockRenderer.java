package com.teamabnormals.autumnity.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabnormals.autumnity.common.entity.item.FallingHeadBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(Dist.CLIENT)
public class FallingHeadBlockRenderer extends EntityRenderer<FallingHeadBlockEntity> {
	private final BlockRenderDispatcher dispatcher;

	public FallingHeadBlockRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.5F;
		this.dispatcher = context.getBlockRenderDispatcher();
	}

	@Override
	public void render(FallingHeadBlockEntity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		BlockState blockstate = entity.getBlockState();
		if (blockstate.getRenderShape() == RenderShape.MODEL) {
			Level level = entity.getLevel();
			if (blockstate != level.getBlockState(entity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
				matrixStackIn.pushPose();
				BlockPos blockpos = new BlockPos(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
				matrixStackIn.translate(-0.5D, 0.0D, -0.5D);
				BakedModel model = this.dispatcher.getBlockModel(blockstate);
				for (RenderType type : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(entity.getStartPos())), ModelData.EMPTY)) {
					this.dispatcher.getModelRenderer().tesselateBlock(level, model, blockstate, blockpos, matrixStackIn, bufferIn.getBuffer(type), false, RandomSource.create(), blockstate.getSeed(entity.getStartPos()), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, type);
				}
				matrixStackIn.popPose();
				super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
			}
		}
	}

	@Override
	public ResourceLocation getTextureLocation(FallingHeadBlockEntity entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}