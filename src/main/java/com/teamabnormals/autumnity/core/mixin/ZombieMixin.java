package com.teamabnormals.autumnity.core.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamabnormals.autumnity.common.entity.animal.Turkey;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBiomeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import net.minecraft.core.Holder;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nullable;

@Mixin(Zombie.class)
public abstract class ZombieMixin<T extends Entity> extends Monster {

	protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;"), method = "finalizeSpawn")
	private T create(EntityType<T> entityType, Level level, Operation<T> original, ServerLevelAccessor serverLevel, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		T chicken = original.call(entityType, level);
		Holder<Biome> holder = serverLevel.getBiome(this.blockPosition());
		if (chicken != null && holder.is(AutumnityBiomeTags.HAS_TURKEY)) {
			Turkey turkey = AutumnityEntityTypes.TURKEY.get().create(level);
			if (turkey != null) {
				turkey.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
				turkey.finalizeSpawn(serverLevel, difficulty, MobSpawnType.JOCKEY, null);
				turkey.setTurkeyJockey(true);
				this.startRiding(turkey);
				level.addFreshEntity(turkey);
			}
			return null;
		} else {
			return chicken;
		}
	}
}