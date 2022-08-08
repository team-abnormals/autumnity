package com.teamabnormals.autumnity.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.autumnity.common.entity.animal.Snail;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnailModel<T extends Snail> extends EntityModel<T> {
	private final ModelPart body;
	private final ModelPart shell;
	private final ModelPart rightEye;
	private final ModelPart leftEye;
	private final ModelPart rightTentacle;
	private final ModelPart leftTentacle;
	private float hideAmount;

	public SnailModel(ModelPart root) {
		this.body = root.getChild("body");
		this.shell = root.getChild("shell");
		this.rightEye = root.getChild("right_eye");
		this.leftEye = root.getChild("left_eye");
		this.rightTentacle = root.getChild("right_tentacle");
		this.leftTentacle = root.getChild("left_tentacle");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 18.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, 24.0F, -9.0F, ((float) Math.PI / 2F), 0.0F, 0.0F));
		root.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 24).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 14.0F, 14.0F, false), PartPose.offsetAndRotation(0.0F, 7.0F, -1.0F, -0.22F, 0.0F, 0.0F));
		root.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 7.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, 18.0F, -7.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 7.0F, 2.0F, true), PartPose.offsetAndRotation(-2.5F, 18.0F, -7.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("right_tentacle", CubeListBuilder.create().texOffs(28, 9).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(3.0F, 22.0F, -9.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("left_tentacle", CubeListBuilder.create().texOffs(28, 9).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-3.0F, 22.0F, -9.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.hideAmount = entity.getHideAmount(partialTick);
		float f = 3.0F * this.hideAmount;
		float f1 = Mth.clamp(10.0F * this.hideAmount, 0.0F, 5.0F);
		boolean flag = this.hideAmount < 1.0F;

		this.rightEye.setPos(2.5F, 18.0F + f1, -7.0F + f);
		this.leftEye.setPos(-2.5F, 18.0F + f1, -7.0F + f);
		this.rightTentacle.setPos(3.0F, 22.0F, -9.0F + f);
		this.leftTentacle.setPos(-3.0F, 22.0F, -9.0F + f);
		this.shell.setPos(0.0F, 7.0F, -1.0F - f);

		this.rightEye.visible = flag;
		this.leftEye.visible = flag;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = 1.0F - this.hideAmount;
		float f1 = headPitch * ((float) Math.PI / 180F) * 0.5F + 0.3F;
		float f2 = netHeadYaw * ((float) Math.PI / 180F) * 0.5F;
		float f3 = entity.getId() % 2 == 0 ? 1.0F : 0.95F;
		float f4 = entity.getId() % 2 == 0 ? 0.95F : 1.0F;

		this.rightEye.xRot = f * (f1 + Mth.cos(0.04F * f3 * ageInTicks) * 0.15F);
		this.rightEye.yRot = f * f2;
		this.rightEye.zRot = f * (0.25F + Mth.sin(0.025F * f3 * ageInTicks) * 0.1F);
		this.leftEye.xRot = f * (f1 + Mth.sin(0.04F * f4 * ageInTicks) * 0.15F);
		this.leftEye.yRot = f * f2;
		this.leftEye.zRot = f * (-0.25F + Mth.cos(0.025F * f4 * ageInTicks) * 0.1F);

		if (entity.getAction() == Snail.Action.EATING) {
			this.rightTentacle.yRot = 0.25F * Mth.cos(0.6F * ageInTicks);
			this.leftTentacle.yRot = -this.rightTentacle.yRot;
		} else {
			this.rightTentacle.yRot = 0.0F;
			this.leftTentacle.yRot = 0.0F;
		}
	}

	@Override
	public void renderToBuffer(PoseStack p_102034_, VertexConsumer p_102035_, int p_102036_, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
		if (this.young) {
			p_102034_.pushPose();
			p_102034_.scale(0.7F, 0.7F, 0.7F);
			p_102034_.translate((double) (1.2F / 16.0F), (double) (13.0F / 16.0F), (double) (2.5F / 16.0F));
			this.leftEye.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
			p_102034_.popPose();
			p_102034_.pushPose();
			p_102034_.scale(0.7F, 0.7F, 0.7F);
			p_102034_.translate((double) (-1.2F / 16.0F), (double) (13.0F / 16.0F), (double) (2.5F / 16.0F));
			this.rightEye.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
			p_102034_.popPose();
			p_102034_.pushPose();
			p_102034_.scale(0.5F, 0.5F, 0.5F);
			p_102034_.translate(0.0D, (double) (24.0F / 16.0F), 0.0D);
			ImmutableList.of(this.rightTentacle, this.leftTentacle, this.shell).forEach((p_103587_) -> {
				p_103587_.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
			});
			p_102034_.pushPose();
			p_102034_.scale(1.0F, 1.0F, 1.0F - 1.0F / 3.0F * this.hideAmount);
			this.body.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
			p_102034_.popPose();
			p_102034_.popPose();
		} else {
			ImmutableList.of(this.rightEye, this.leftEye).forEach((p_103587_) -> {
				p_103587_.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
			});
			ImmutableList.of(this.rightTentacle, this.leftTentacle, this.shell).forEach((p_103587_) -> {
				p_103587_.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
			});
			p_102034_.pushPose();
			p_102034_.scale(1.0F, 1.0F, 1.0F - 1.0F / 3.0F * this.hideAmount);
			this.body.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
			p_102034_.popPose();
		}
	}
}