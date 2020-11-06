package com.minecraftabnormals.autumnity.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.autumnity.common.entity.passive.TurkeyEntity;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelTurkey - Undefined
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class TurkeyModel<T extends TurkeyEntity> extends AgeableModel<T>
{
	private final ModelRenderer head;
	private final ModelRenderer beak;
	private final ModelRenderer waddle;
	private final ModelRenderer body;
	private final ModelRenderer rightWing;
	private final ModelRenderer leftWing;
	private final ModelRenderer tail;
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;

	public TurkeyModel()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 14.0F, -3.0F);
		this.head.addBox(-2.0F, -5.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F, 0.0F, 0.0F);

		this.beak = new ModelRenderer(this, 0, 9);
		this.beak.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.beak.addBox(-2.0F, -3.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, 0.0F, 0.0F);

		this.waddle = new ModelRenderer(this, 0, 13);
		this.waddle.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.waddle.addBox(-1.0F, -1.0F, -4.0F, 2.0F, 4.0F, 2.0F, 0.0F, 0.0F, 0.0F);

		this.head.addChild(this.waddle);
		this.head.addChild(this.beak);

		this.body = new ModelRenderer(this, 0, 14);
		this.body.setRotationPoint(0.0F, 21.0F, 1.0F);
		this.body.addBox(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F, 0.0F, 0.0F, 0.0F);

		this.rightWing = new ModelRenderer(this);
		this.rightWing.mirror = true;
		this.rightWing.setRotationPoint(5.0F, -8.0F, 0.0F);
		this.rightWing.setTextureOffset(44, 0).addBox(0.0F, 0.0F, -4.0F, 2.0F, 6.0F, 8.0F, 0.0F);

		this.leftWing = new ModelRenderer(this);
		this.leftWing.setRotationPoint(-5.0F, -8.0F, 0.0F);
		this.leftWing.setTextureOffset(44, 0).addBox(-2.0F, 0.0F, -4.0F, 2.0F, 6.0F, 8.0F, 0.0F);

		this.tail = new ModelRenderer(this, 16, 4);
		this.tail.setRotationPoint(0.0F, -7.0F, 4.0F);
		this.tail.addBox(-7.0F, -10.0F, 0.0F, 14.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F);

		this.setRotateAngle(tail, -0.7853981633974483F, 0.0F, 0.0F);

		this.body.addChild(this.leftWing);
		this.body.addChild(this.rightWing);
		this.body.addChild(this.tail);

		this.rightLeg = new ModelRenderer(this, 30, 17);
		this.rightLeg.setRotationPoint(-3.0F, 20.0F, 1.0F);
		this.rightLeg.addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);

		this.leftLeg = new ModelRenderer(this, 30, 17);
		this.leftLeg.mirror = true;
		this.leftLeg.setRotationPoint(3.0F, 20.0F, 1.0F);
		this.leftLeg.addBox(-1.5F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, 0.0F, 0.0F, 0.0F);
	}

	protected Iterable<ModelRenderer> getHeadParts()
	{
		return ImmutableList.of(this.head);
	}

	protected Iterable<ModelRenderer> getBodyParts()
	{
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg);
	}

	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		float partialtick = ageInTicks - (float)entityIn.ticksExisted;
		float winganim = entityIn.getWingRotation(partialtick);
		float peckanim = entityIn.getPeckProgress(partialtick);
		float intimidationanim = entityIn.getIntimidationAnimationScale(partialtick);

		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.head.rotateAngleX += peckanim * 0.8F;
		this.head.rotationPointZ = -3.0F - peckanim * 1.5F - intimidationanim * 2.0F;
		this.head.rotationPointY = 14.0F + intimidationanim * 2.5F;
		this.head.rotationPointX = MathHelper.cos(ageInTicks * 0.75F) * -2.0F * intimidationanim;
		
		this.rightWing.rotateAngleZ = -winganim;
		this.leftWing.rotateAngleZ = winganim;

		this.body.rotateAngleY = MathHelper.cos(ageInTicks * 0.75F) * 0.3F * intimidationanim;
		this.body.rotateAngleX = 0.3F * intimidationanim;
		
		this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
