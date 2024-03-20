package com.teamabnormals.autumnity.core.mixin;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.LakeFeature.Configuration;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LakeFeature.class)
public class LakeFeatureMixin {

	@Inject(method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z", at = @At(value = "HEAD"))
	private void place(FeaturePlaceContext<Configuration> context, CallbackInfoReturnable<Boolean> cir) {
		if (context.level() instanceof WorldGenRegion) {
			Registry<Structure> registry = context.level().registryAccess().registryOrThrow(Registries.STRUCTURE);
			StructureManager manager = context.level().getLevel().structureManager();
			Structure mapleWitchHut = registry.get(new ResourceLocation(Autumnity.MOD_ID, "maple_witch_hut"));

			if (mapleWitchHut != null && manager.getStructureAt(context.origin(), mapleWitchHut).isValid()) {
				cir.setReturnValue(false);
			}
		}
	}
}