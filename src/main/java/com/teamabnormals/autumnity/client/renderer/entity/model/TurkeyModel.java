package com.teamabnormals.autumnity.client.renderer.entity.model;

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
	private final ModelPart beak;
	private final ModelPart waddle;
	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart tail;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public TurkeyModel(ModelPart root) {
		this.head = root.getChild("head");
		this.waddle = this.head.getChild("waddle");
		this.beak = this.head.getChild("beak");
		this.body = root.getChild("body");
		this.leftWing = this.body.getChild("leftWing");
		this.rightWing = this.body.getChild("rightWing");
		this.tail = this.body.getChild("tail");
		this.rightLeg = root.getChild("rightLeg");
		this.leftLeg = root.getChild("leftLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 6.0F, 3.0F, false), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition waddle = head.addOrReplaceChild("waddle", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 4.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 9).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 2.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 14).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, 21.0F, 1.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftWing = body.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(44, 0).addBox(-2.0F, 0.0F, -4.0F, 2.0F, 6.0F, 8.0F, false), PartPose.offsetAndRotation(-5.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition rightWing = body.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(44, 0).addBox(0.0F, 0.0F, -4.0F, 2.0F, 6.0F, 8.0F, true), PartPose.offsetAndRotation(5.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 4).addBox(-7.0F, -10.0F, 0.0F, 14.0F, 10.0F, 0.0F, false), PartPose.offsetAndRotation(0.0F, -7.0F, 4.0F, -0.7853982F, 0.0F, 0.0F));
		PartDefinition rightLeg = root.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(30, 17).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, false), PartPose.offsetAndRotation(-3.0F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition leftLeg = root.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(30, 17).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, true), PartPose.offsetAndRotation(3.0F, 20.0F, 1.0F, 0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg);
	}

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
