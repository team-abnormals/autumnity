package com.teamabnormals.autumnity.core.mixin;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Cat.class)
public abstract class CatMixin {

	@Inject(method = "finalizeSpawn", at = @At(value = "TAIL"))
	private void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag, CallbackInfoReturnable<SpawnGroupData> cir) {
		ServerLevel serverlevel = level.getLevel();
		Cat cat = ((Cat) (Object) this);
		if (serverlevel.structureFeatureManager().getStructureWithPieceAt(cat.blockPosition(), ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(Autumnity.MOD_ID, "maple_witch_hut"))).isValid()) {
			cat.setCatType(10);
			cat.setPersistenceRequired();
		}
	}
}