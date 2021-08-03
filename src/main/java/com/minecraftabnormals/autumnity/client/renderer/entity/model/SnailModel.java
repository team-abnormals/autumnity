package com.minecraftabnormals.autumnity.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.autumnity.common.entity.passive.SnailEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnailModel<T extends SnailEntity> extends AgeableModel<T> {
	private final ModelRenderer body;
	private final ModelRenderer hideBody;
	private final ModelRenderer shell;
	private final ModelRenderer eye1;
	private final ModelRenderer eye2;
	private final ModelRenderer tentacle1;
	private final ModelRenderer tentacle2;

	public SnailModel() {
		this.texWidth = 64;
		this.texHeight = 64;

		this.body = new ModelRenderer(this);
		this.body.setPos(0.0F, 24.0F, -9.0F);
		this.body.addBox(-4.0F, 0.0F, 0.0F, 8, 18, 6, 0.0F);

		this.hideBody = new ModelRenderer(this);
		this.hideBody.setPos(0.0F, 24.0F, -6.0F);
		this.hideBody.addBox(-4.0F, 0.0F, 0.0F, 8, 12, 6, 0.0F);

		this.eye1 = new ModelRenderer(this, 28, 0);
		this.eye1.setPos(2.5F, 18.0F, -7.0F);
		this.eye1.addBox(-1.0F, -6.0F, -1.0F, 2, 7, 2, 0.0F);

		this.eye2 = new ModelRenderer(this, 28, 0);
		this.eye2.mirror = true;
		this.eye2.setPos(-2.5F, 18.0F, -7.0F);
		this.eye2.addBox(-1.0F, -6.0F, -1.0F, 2, 7, 2, 0.0F);

		this.tentacle1 = new ModelRenderer(this, 28, 9);
		this.tentacle1.setPos(3.0F, 22.0F, -9.0F);
		this.tentacle1.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2, 0.0F);

		this.tentacle2 = new ModelRenderer(this, 28, 9);
		this.tentacle2.setPos(-3.0F, 22.0F, -9.0F);
		this.tentacle2.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 2, 0.0F);

		this.shell = new ModelRenderer(this, 0, 24);
		this.shell.setPos(0.0F, 7.0F, -1.0F);
		this.shell.addBox(-4.5F, 0.0F, 0.0F, 9, 14, 14, 0.0F);
	}

	public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		float f = entityIn.getHidingAnimationScale(partialTick);
		float f1 = 3.0F * f;
		float f2 = MathHelper.clamp(10.0F * f, 0.0F, 5.0F);

		this.hideBody.setPos(0.0F, 24.0F, -9.0F + f1);
		this.eye1.setPos(2.5F, 18.0F + f2, -7.0F + f1);
		this.eye2.setPos(-2.5F, 18.0F + f2, -7.0F + f1);
		this.tentacle1.setPos(3.0F, 22.0F, -9.0F + f1);
		this.tentacle2.setPos(-3.0F, 22.0F, -9.0F + f1);
		this.shell.setPos(0.0F, 7.0F, -1.0F - f1);
	}

	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		boolean flag = entityIn.getHideTicks() == 0;

		this.body.xRot = ((float) Math.PI / 2F);
		this.hideBody.xRot = ((float) Math.PI / 2F);
		if (flag) {
			this.eye1.xRot = headPitch * ((float) Math.PI / 180F) * 0.5F + 0.25F;
			this.eye1.yRot = netHeadYaw * ((float) Math.PI / 180F) * 0.5F;
			this.eye1.zRot = 0.25F;
		} else {
			this.eye1.xRot = 0.0F;
			this.eye1.yRot = 0.0F;
			this.eye1.zRot = 0.0F;
		}
		this.eye2.xRot = this.eye1.xRot;
		this.eye2.yRot = this.eye1.yRot;
		this.eye2.zRot = -this.eye1.zRot;
		this.shell.xRot = -0.22F;

		if (entityIn.isEating()) {
			this.tentacle1.yRot = 0.25F * MathHelper.sin(0.6F * ageInTicks);
			this.tentacle2.yRot = -tentacle1.yRot;
		} else {
			this.tentacle1.yRot = 0.0F;
			this.tentacle2.yRot = 0.0F;
		}


		if (entityIn.getHideTicks() == 0) {
			this.body.visible = true;
			this.hideBody.visible = false;
		} else {
			this.body.visible = false;
			this.hideBody.visible = true;
		}

		if (entityIn.getHideTicks() < 3) {
			this.eye1.visible = true;
			this.eye2.visible = true;
		} else {
			this.eye1.visible = false;
			this.eye2.visible = false;
		}
	}

	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of();
	}

	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.hideBody, this.eye1, this.eye2, this.tentacle1, this.tentacle2, this.shell);
	}
}