package com.teamabnormals.autumnity.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.autumnity.common.entity.animal.Snail;
import net.minecraft.client.model.AgeableListModel;
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
public class SnailModel<T extends Snail> extends AgeableListModel<T> {
	private final ModelPart body;
	private final ModelPart hideBody;
	private final ModelPart shell;
	private final ModelPart eye1;
	private final ModelPart eye2;
	private final ModelPart tentacle1;
	private final ModelPart tentacle2;

	public SnailModel(ModelPart root) {
		this.body = root.getChild("body");
		this.hideBody = root.getChild("hideBody");
		this.shell = root.getChild("shell");
		this.eye1 = root.getChild("eye1");
		this.eye2 = root.getChild("eye2");
		this.tentacle1 = root.getChild("tentacle1");
		this.tentacle2 = root.getChild("tentacle2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 18.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, 24.0F, -9.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition hideBody = root.addOrReplaceChild("hideBody", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 12.0F, 6.0F, false), PartPose.offsetAndRotation(0.0F, 24.0F, -6.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition shell = root.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 24).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 14.0F, 14.0F, false), PartPose.offsetAndRotation(0.0F, 7.0F, -1.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition eye1 = root.addOrReplaceChild("eye1", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 7.0F, 2.0F, false), PartPose.offsetAndRotation(2.5F, 18.0F, -7.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition eye2 = root.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 7.0F, 2.0F, true), PartPose.offsetAndRotation(-2.5F, 18.0F, -7.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition tentacle1 = root.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(28, 9).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(3.0F, 22.0F, -9.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition tentacle2 = root.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(28, 9).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(-3.0F, 22.0F, -9.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		float f = entity.getHidingAnim(partialTick);
		float f1 = 3.0F * f;
		float f2 = Mth.clamp(10.0F * f, 0.0F, 5.0F);

		this.hideBody.setPos(0.0F, 24.0F, -9.0F + f1);
		this.eye1.setPos(2.5F, 18.0F + f2, -7.0F + f1);
		this.eye2.setPos(-2.5F, 18.0F + f2, -7.0F + f1);
		this.tentacle1.setPos(3.0F, 22.0F, -9.0F + f1);
		this.tentacle2.setPos(-3.0F, 22.0F, -9.0F + f1);
		this.shell.setPos(0.0F, 7.0F, -1.0F - f1);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float partialtick = ageInTicks - (float) entity.tickCount;
		float f = entity.getHidingAnim(partialtick);
		float f1 = 3.0F * f;
		float f2 = Mth.clamp(10.0F * f, 0.0F, 5.0F);
		float f3 = 1.0F - f;

		this.hideBody.setPos(0.0F, 24.0F, -9.0F + f1);
		this.eye1.setPos(2.5F, 18.0F + f2, -7.0F + f1);
		this.eye2.setPos(-2.5F, 18.0F + f2, -7.0F + f1);
		this.tentacle1.setPos(3.0F, 22.0F, -9.0F + f1);
		this.tentacle2.setPos(-3.0F, 22.0F, -9.0F + f1);
		this.shell.setPos(0.0F, 7.0F, -1.0F - f1);

		this.body.xRot = ((float) Math.PI / 2F);
		this.hideBody.xRot = ((float) Math.PI / 2F);
		this.eye1.xRot = f3 * (headPitch * ((float) Math.PI / 180F) * 0.5F + 0.25F);
		this.eye1.yRot = f3 * (netHeadYaw * ((float) Math.PI / 180F) * 0.5F);
		this.eye1.zRot = f3 * 0.25F;
		this.eye2.xRot = this.eye1.xRot;
		this.eye2.yRot = this.eye1.yRot;
		this.eye2.zRot = -this.eye1.zRot;
		this.shell.xRot = -0.22F;

		if (entity.getAction() == Snail.Action.EATING) {
			this.tentacle1.yRot = 0.25F * Mth.sin(0.6F * ageInTicks);
			this.tentacle2.yRot = -tentacle1.yRot;
		} else {
			this.tentacle1.yRot = 0.0F;
			this.tentacle2.yRot = 0.0F;
		}

		if (entity.getHidingAnimTicks() == 0) {
			this.body.visible = true;
			this.hideBody.visible = false;
		} else {
			this.body.visible = false;
			this.hideBody.visible = true;
		}

		if (entity.getHidingAnimTicks() < 3) {
			this.eye1.visible = true;
			this.eye2.visible = true;
		} else {
			this.eye1.visible = false;
			this.eye2.visible = false;
		}
	}

	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.hideBody, this.eye1, this.eye2, this.tentacle1, this.tentacle2, this.shell);
	}
}