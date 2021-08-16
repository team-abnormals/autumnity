package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.abnormals_core.common.blocks.wood.AbnormalsLeavesBlock;
import com.minecraftabnormals.autumnity.core.registry.AutumnityParticles;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class ColoredMapleLeavesBlock extends AbnormalsLeavesBlock {
	private final int color;

	public ColoredMapleLeavesBlock(Properties properties, int colorIn) {
		super(properties);
		this.color = colorIn;
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		super.animateTick(stateIn, worldIn, pos, rand);

		if (rand.nextInt(100) == 0) {
			BlockPos blockpos = pos.below();
			if (worldIn.isEmptyBlock(blockpos)) {
				double d0 = (this.color >> 16 & 255) / 255.0F;
				double d1 = (this.color >> 8 & 255) / 255.0F;
				double d2 = (this.color & 255) / 255.0F;

				double d3 = (float) pos.getX() + rand.nextFloat();
				double d4 = (double) pos.getY() - 0.05D;
				double d6 = (float) pos.getZ() + rand.nextFloat();

				worldIn.addParticle(AutumnityParticles.FALLING_MAPLE_LEAF.get(), d3, d4, d6, d0, d1, d2);
			}
		}
	}
}