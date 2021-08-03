package com.minecraftabnormals.autumnity.core.mixin;

import com.minecraftabnormals.autumnity.core.registry.AutumnityStructures;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.LakesFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LakesFeature.class)
public class LakesFeatureMixin {

	@Inject(method = "place", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/math/BlockPos;below(I)Lnet/minecraft/util/math/BlockPos;"), cancellable = true)
	private void place(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, BlockStateFeatureConfig config, CallbackInfoReturnable<Boolean> cir) {
		if (world.startsForFeature(SectionPos.of(pos), AutumnityStructures.MAPLE_WITCH_HUT.get()).findAny().isPresent()) {
			cir.setReturnValue(false);
		}
	}
}