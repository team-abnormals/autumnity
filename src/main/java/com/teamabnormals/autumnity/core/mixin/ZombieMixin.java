package com.teamabnormals.autumnity.core.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamabnormals.autumnity.common.entity.animal.Turkey;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBiomeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import net.minecraft.core.Holder;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(Zombie.class)
public abstract class ZombieMixin<T extends Entity> extends Monster {

	protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	// TODO: Make not run if chicken is chosen later
	@Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/ServerLevelAccessor;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"), method = "finalizeSpawn")
	public void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
		List<Turkey> turkeys = level.getEntitiesOfClass(
				Turkey.class, this.getBoundingBox().inflate(5.0, 3.0, 5.0), EntitySelector.ENTITY_NOT_BEING_RIDDEN
		);
		if (!turkeys.isEmpty()) {
			Turkey turkey = turkeys.getFirst();
			turkey.setTurkeyJockey(true);
			this.startRiding(turkey);
		}
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;create(Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;"), method = "finalizeSpawn")
	private T create(EntityType<T> entityType, Level level, Operation<T> original, ServerLevelAccessor serverLevel, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		Holder<Biome> holder = serverLevel.getBiome(this.blockPosition());
		if (holder.is(AutumnityBiomeTags.HAS_TURKEY)) {
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
			return original.call(entityType, level);
		}
	}
}