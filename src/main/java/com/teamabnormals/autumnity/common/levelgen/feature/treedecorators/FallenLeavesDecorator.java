package com.teamabnormals.autumnity.common.levelgen.feature.treedecorators;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.autumnity.common.levelgen.feature.FallenLeavesFeature;
import com.teamabnormals.autumnity.core.registry.AutumnityFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class FallenLeavesDecorator extends TreeDecorator {
	public static final MapCodec<FallenLeavesDecorator> CODEC = RecordCodecBuilder.mapCodec(codec -> codec.group(
			BlockStateProvider.CODEC.fieldOf("provider").forGetter(instance -> instance.provider),
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("radius").forGetter(instance -> instance.radius),
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").forGetter(instance -> instance.ySpread)
	).apply(codec, FallenLeavesDecorator::new));

	private final BlockStateProvider provider;
	private final int radius;
	private final int ySpread;

	public FallenLeavesDecorator(BlockStateProvider provider, int radius, int ySpread) {
		this.provider = provider;
		this.radius = radius;
		this.ySpread = ySpread;
	}

	@Override
	public void place(Context context) {
		RandomSource random = context.random();
		for (BlockPos blockpos : FallenLeavesFeature.placeLeaves(this.radius, this.ySpread, context.logs().get(0), context.level(), context.random()))
			context.setBlock(blockpos, this.provider.getState(random, blockpos));
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return AutumnityFeatures.FALLEN_LEAVES_DECORATOR.get();
	}
}