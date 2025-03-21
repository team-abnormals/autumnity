package com.teamabnormals.autumnity.core.registry;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition.Serializer;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AutumnityConditions {
	public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.CONDITION_SERIALIZERS, Autumnity.MOD_ID);

	public static final DeferredHolder<MapCodec<? extends ICondition>, Serializer> CONFIG = CONDITION_SERIALIZERS.register("config", () -> new Serializer(DataUtil.getConfigValues(AutumnityConfig.COMMON)));
}