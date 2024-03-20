package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.core.registry.AutumnityParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ColoredMapleLeavesBlock extends LeavesBlock {
	private final int color;

	public ColoredMapleLeavesBlock(Properties properties, int colorIn) {
		super(properties);
		this.color = colorIn;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
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

				worldIn.addParticle(AutumnityParticleTypes.FALLING_MAPLE_LEAF.get(), d3, d4, d6, d0, d1, d2);
			}
		}
	}
}