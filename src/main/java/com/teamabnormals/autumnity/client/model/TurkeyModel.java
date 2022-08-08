package com.teamabnormals.autumnity.client.model;

import com.google.common.collect.ImmutableList;
import com.teamabnormals.autumnity.common.entity.animal.Turkey;
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

/**
 * ModelTurkey - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class TurkeyModel<T extends Turkey> extends AgeableListModel<T> {
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public TurkeyModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.rightWing = this.body.getChild("right_wing");
		this.leftWing = this.body.getChild("left_wing");
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 6.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("waddle", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 9).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 14).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, 21.0F, 1.0F, 0.0F, 0.0F, 0.0F));
		body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(44, 0).addBox(0.0F, 0.0F, -4.0F, 2.0F, 6.0F, 8.0F, true), PartPose.offsetAndRotation(5.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(44, 0).addBox(-2.0F, 0.0F, -4.0F, 2.0F, 6.0F, 8.0F, false), PartPose.offsetAndRotation(-5.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 4).addBox(-7.0F, -10.0F, 0.0F, 14.0F, 10.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -7.0F, 4.0F, -0.7853982F, 0.0F, 0.0F));
		root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(30, 17).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, false), PartPose.offsetAndRotation(-3.0F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(30, 17).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, true), PartPose.offsetAndRotation(3.0F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg);
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float partialtick = ageInTicks - (float) entityIn.tickCount;
		float winganim = entityIn.getWingRotation(partialtick);
		float peckanim = entityIn.getPeckProgress(partialtick);

		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot += peckanim * 0.8F;
		this.head.z = -3.0F - peckanim * 1.5F;

		this.rightWing.zRot = -winganim;
		this.leftWing.zRot = winganim;

		this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
	}
}
