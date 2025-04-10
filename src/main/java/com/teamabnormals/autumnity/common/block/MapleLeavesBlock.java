package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class MapleLeavesBlock extends LeavesBlock {

	public MapleLeavesBlock(Properties properties) {
		super(properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);

		if (random.nextInt(100) == 0) {
			BlockPos blockpos = pos.below();
			if (level.isEmptyBlock(blockpos)) {
				double d3 = (float) pos.getX() + random.nextFloat();
				double d4 = (double) pos.getY() - 0.05D;
				double d6 = (float) pos.getZ() + random.nextFloat();

				if (state.is(AutumnityBlocks.MAPLE_LEAVES.get())) {
					int color = level.getBiome(pos).value().getFoliageColor();
					double d0 = (color >> 16 & 255) / 255.0F;
					double d1 = (color >> 8 & 255) / 255.0F;
					double d2 = (color & 255) / 255.0F;
					level.addParticle(AutumnityParticleTypes.MAPLE_LEAVES.get(), d3, d4, d6, d0, d1, d2);
				} else {
					level.addParticle(state.is(AutumnityBlocks.YELLOW_MAPLE_LEAVES.get()) ? AutumnityParticleTypes.YELLOW_MAPLE_LEAVES.get() : state.is(AutumnityBlocks.ORANGE_MAPLE_LEAVES.get()) ? AutumnityParticleTypes.ORANGE_MAPLE_LEAVES.get() : AutumnityParticleTypes.RED_MAPLE_LEAVES.get(), d3, d4, d6, 1.0D, 1.0D, 1.0D);
				}
			}
		}
	}
}