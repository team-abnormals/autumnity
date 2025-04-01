package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.autumnity.core.other.tags.AutumnityEntityTypeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.ModList;

public interface FoulBerryBush {

	static boolean hasBerries(BlockState state) {
		return state.is(AutumnityBlocks.TALL_FOUL_BERRY_BUSH) && state.getValue(TallFoulBerryBushBlock.AGE) > 1;
	}

	static ItemStack getCloneItemStack() {
		return new ItemStack(ModList.get().isLoaded("berry_good") && AutumnityConfig.COMMON.foulBerriesRequirePips.get() ? AutumnityItems.FOUL_BERRY_PIPS.get() : AutumnityItems.FOUL_BERRIES.get());
	}

	static void addParticles(VoxelShape shape, Level level, BlockPos pos, RandomSource random) {
		Vec3 vector3d = shape.bounds().getCenter();
		double d0 = (double) pos.getX() + vector3d.x;
		double d1 = (double) pos.getZ() + vector3d.z;

		int i = MobEffects.POISON.value().getColor();
		float f = (float) (i >> 16 & 0xFF) / 255.0F;
		float f1 = (float) (i >> 8 & 0xFF) / 255.0F;
		float f2 = (float) (i >> 0 & 0xFF) / 255.0F;

		level.addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, f, f1, f2), d0 + (double) (random.nextFloat() / 5.0F), (double) pos.getY() + (0.5D - (double) random.nextFloat()), d1 + (double) (random.nextFloat() / 5.0F), 0.0, 0.0, 0.0);
	}

	static void doStuckLogic(BlockState state, Level level, Entity entity) {
		if (entity instanceof LivingEntity living && !entity.getType().is(AutumnityEntityTypeTags.FOUL_BERRY_IMMUNE)) {
			entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
			if (!level.isClientSide() && !living.hasEffect(MobEffects.POISON) && !living.isSteppingCarefully()) {
				living.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
			}
		}
	}
}
