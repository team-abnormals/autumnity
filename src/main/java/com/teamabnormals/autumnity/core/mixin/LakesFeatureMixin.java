package com.teamabnormals.autumnity.core.mixin;

import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LakeFeature.class)
public class LakesFeatureMixin {

	@Inject(method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z", at = @At(value = "HEAD"), cancellable = true)
	private void place(FeaturePlaceContext<Configuration> context, CallbackInfoReturnable<Boolean> cir) {
//		BlockPos origin = context.origin();
//		WorldGenLevel level = context.level();
//
//		StructureStart structureStart = level.getLevel().structureFeatureManager().getStartForFeature(SectionPos.of(origin), AutumnityConfiguredStructureFeatures.MAPLE_WITCH_HUT.value(), level.getChunk(origin));
//		if (structureStart != null && structureStart.isValid()) {
//			cir.setReturnValue(false);
//		}
	}
}