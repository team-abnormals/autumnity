package com.minecraftabnormals.autumnity.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FallingMapleLeafParticle extends SpriteTexturedParticle {
	private final float rotSpeed;

	private FallingMapleLeafParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double particleRedIn, double particleGreenIn, double particleBlueIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn);
		this.quadSize *= 1.2F;
		this.lifetime = 80;
		this.rotSpeed = ((float) Math.random() - 0.5F) * 0.1F;
		this.roll = (float) Math.random() * ((float) Math.PI * 2F);
	}

	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age >= this.lifetime) {
			this.remove();
		} else {
			this.move(this.xd, this.yd, this.zd);
			this.yd -= (double) 0.002F;
			this.yd = Math.max(this.yd, (double) -0.1F);

			this.oRoll = this.roll;
			if (!this.onGround) {
				this.roll += (float) Math.PI * this.rotSpeed * 1.6F;
			} else {
				this.yd = 0.0D;
			}

			if (this.onGround || this.y < 0) {
				this.age++;
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite sprite) {
			this.spriteSet = sprite;
		}

		public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			FallingMapleLeafParticle particle = new FallingMapleLeafParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.setColor((float) xSpeed, (float) ySpeed, (float) zSpeed);
			particle.pickSprite(this.spriteSet);
			return particle;
		}
	}
}