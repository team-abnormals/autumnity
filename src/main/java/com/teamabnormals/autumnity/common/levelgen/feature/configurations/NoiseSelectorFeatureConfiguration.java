package com.teamabnormals.autumnity.common.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.autumnity.common.levelgen.feature.NoiseThresholdPlacedFeature;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

import java.util.List;
import java.util.stream.Stream;

public class NoiseSelectorFeatureConfiguration implements FeatureConfiguration {
	public static final Codec<NoiseSelectorFeatureConfiguration> CODEC = RecordCodecBuilder.create(
			p_67898_ -> p_67898_.apply3(
					NoiseSelectorFeatureConfiguration::new,
					NoiseParameters.CODEC.fieldOf("noise").forGetter(p_191433_ -> p_191433_.parameters),
					NoiseThresholdPlacedFeature.CODEC.listOf().fieldOf("features").forGetter(p_161053_ -> p_161053_.features),
					PlacedFeature.CODEC.fieldOf("default").forGetter(p_204816_ -> p_204816_.defaultFeature)
			)
	);
	public final Holder<NoiseParameters> parameters;
	public final List<NoiseThresholdPlacedFeature> features;
	public final Holder<PlacedFeature> defaultFeature;
	private volatile NormalNoise noise;
	private volatile boolean initialized;

	public NoiseSelectorFeatureConfiguration(Holder<NoiseParameters> parameters, List<NoiseThresholdPlacedFeature> features, Holder<PlacedFeature> defaultFeature) {
		this.parameters = parameters;
		this.features = features;
		this.defaultFeature = defaultFeature;
	}

	public NormalNoise getNoise(WorldGenLevel level) {
		if (!this.initialized) {
			synchronized (this) {
				if (!this.initialized) {
					this.noise = NormalNoise.create(WorldgenRandom.Algorithm.LEGACY.newInstance(level.getSeed()).forkPositional().fromHashOf(this.parameters.unwrapKey().orElseThrow().location()), this.parameters.value());
				}
			}
		}
		return this.noise;
	}

	@Override
	public Stream<ConfiguredFeature<?, ?>> getFeatures() {
		return Stream.concat(this.features.stream().flatMap(p_204814_ -> p_204814_.feature.value().getFeatures()), this.defaultFeature.value().getFeatures());
	}
}