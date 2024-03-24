package com.teamabnormals.autumnity.core.other;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityBiomes;
import com.teamabnormals.blueprint.common.world.modification.ModdedBiomeSlice;
import com.teamabnormals.blueprint.core.registry.BlueprintBiomes;
import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
import com.teamabnormals.blueprint.core.util.BiomeUtil.MultiNoiseModdedBiomeProvider;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.world.level.biome.Climate.ParameterPoint;

public class AutumnityBiomeSlices {
	public static final ResourceKey<ModdedBiomeSlice> AUTUMN = createKey("autumn");

	private static final ResourceKey<Biome> MAPLE_FOREST_MIDDLE = AutumnityBiomes.createKey("maple_forest_middle");
	private static final ResourceKey<Biome> MAPLE_FOREST_MIDDLE_VARIANT = AutumnityBiomes.createKey("maple_forest_middle_variant");
	private static final ResourceKey<Biome> MAPLE_FOREST_PLATEAU = AutumnityBiomes.createKey("maple_forest_plateau");
	private static final ResourceKey<Biome> MAPLE_FOREST_PLATEAU_VARIANT = AutumnityBiomes.createKey("maple_forest_plateau_variant");
	private static final ResourceKey<Biome> PUMPKIN_FIELDS_MIDDLE = AutumnityBiomes.createKey("pumpkin_fields_middle");

	public static void bootstrap(BootstapContext<ModdedBiomeSlice> context) {
		List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> entries = new ArrayList<>();
		new AutumnBiomeBuilder().addBiomesToSlice(entries::add);

		context.register(AUTUMN, new ModdedBiomeSlice(20,
				MultiNoiseModdedBiomeProvider.builder()
						.biomes(entries::forEach)
						.area(MAPLE_FOREST_MIDDLE, AutumnityBiomes.MAPLE_FOREST)
						.area(PUMPKIN_FIELDS_MIDDLE, AutumnityBiomes.PUMPKIN_FIELDS)
						.area(MAPLE_FOREST_MIDDLE_VARIANT, AutumnityBiomes.MAPLE_FOREST)
						.area(MAPLE_FOREST_PLATEAU, AutumnityBiomes.MAPLE_FOREST)
						.area(MAPLE_FOREST_PLATEAU_VARIANT, AutumnityBiomes.MAPLE_FOREST)
						.build(), LevelStem.OVERWORLD));
	}

	public static ResourceKey<ModdedBiomeSlice> createKey(String name) {
		return ResourceKey.create(BlueprintDataPackRegistries.MODDED_BIOME_SLICES, new ResourceLocation(Autumnity.MOD_ID, name));
	}

	//Modified version of OverworldBiomeBuilder to simplify Autumnity's slice
	@SuppressWarnings("unchecked")
	private static final class AutumnBiomeBuilder {
		private final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
		private final Climate.Parameter[] temperatures = new Climate.Parameter[]{Climate.Parameter.span(-1.0F, -0.45F), Climate.Parameter.span(-0.45F, -0.15F), Climate.Parameter.span(-0.15F, 0.2F), Climate.Parameter.span(0.2F, 0.55F), Climate.Parameter.span(0.55F, 1.0F)};
		private final Climate.Parameter[] humidities = new Climate.Parameter[]{Climate.Parameter.span(-1.0F, -0.35F), Climate.Parameter.span(-0.35F, -0.1F), Climate.Parameter.span(-0.1F, 0.1F), Climate.Parameter.span(0.1F, 0.3F), Climate.Parameter.span(0.3F, 1.0F)};
		private final Climate.Parameter[] erosions = new Climate.Parameter[]{Climate.Parameter.span(-1.0F, -0.78F), Climate.Parameter.span(-0.78F, -0.375F), Climate.Parameter.span(-0.375F, -0.2225F), Climate.Parameter.span(-0.2225F, 0.05F), Climate.Parameter.span(0.05F, 0.45F), Climate.Parameter.span(0.45F, 0.55F), Climate.Parameter.span(0.55F, 1.0F)};
		private final Climate.Parameter FROZEN_RANGE = this.temperatures[0];
		private final Climate.Parameter UNFROZEN_RANGE = Climate.Parameter.span(this.temperatures[1], this.temperatures[4]);
		private final Climate.Parameter mushroomFieldsContinentalness = Climate.Parameter.span(-1.2F, -1.05F);
		private final Climate.Parameter deepOceanContinentalness = Climate.Parameter.span(-1.05F, -0.455F);
		private final Climate.Parameter oceanContinentalness = Climate.Parameter.span(-0.455F, -0.19F);
		private final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
		private final Climate.Parameter inlandContinentalness = Climate.Parameter.span(-0.11F, 0.55F);
		private final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
		private final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
		private final Climate.Parameter farInlandContinentalness = Climate.Parameter.span(0.3F, 1.0F);
		private final ResourceKey<Biome> VANILLA = BlueprintBiomes.ORIGINAL_SOURCE_MARKER;
		private final ResourceKey<Biome>[][] MIDDLE_BIOMES = new ResourceKey[][]{{VANILLA, VANILLA, VANILLA, VANILLA, VANILLA}, {PUMPKIN_FIELDS_MIDDLE, PUMPKIN_FIELDS_MIDDLE, MAPLE_FOREST_MIDDLE, VANILLA, VANILLA}, {VANILLA, PUMPKIN_FIELDS_MIDDLE, VANILLA, VANILLA, VANILLA}, {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA}, {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA}};
		private final ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT = new ResourceKey[][]{{VANILLA, null, VANILLA, null, null}, {MAPLE_FOREST_MIDDLE_VARIANT, null, null, null, VANILLA}, {VANILLA, MAPLE_FOREST_MIDDLE_VARIANT, null, VANILLA, null}, {null, null, VANILLA, VANILLA, VANILLA}, {null, null, null, null, null}};
		private final ResourceKey<Biome>[][] PLATEAU_BIOMES = new ResourceKey[][]{{VANILLA, VANILLA, VANILLA, VANILLA, VANILLA}, {VANILLA, VANILLA, MAPLE_FOREST_PLATEAU, VANILLA, VANILLA}, {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA}, {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA}, {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA}};
		private final ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT = new ResourceKey[][]{{VANILLA, null, null, null, null}, {null, null, VANILLA, VANILLA, VANILLA}, {null, null, MAPLE_FOREST_PLATEAU_VARIANT, VANILLA, null}, {null, null, null, null, null}, {VANILLA, VANILLA, null, null, null}};

		private void addBiomesToSlice(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> consumer) {
			this.addOffCoastBiomes(consumer);
			this.addInlandBiomes(consumer);
			this.addUndergroundBiomes(consumer);
		}

		private void addOffCoastBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
			this.addSurfaceBiome(consumer, this.FULL_RANGE, this.FULL_RANGE, this.mushroomFieldsContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, VANILLA);

			for (Climate.Parameter temperature : this.temperatures) {
				this.addSurfaceBiome(consumer, temperature, this.FULL_RANGE, this.deepOceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, VANILLA);
				this.addSurfaceBiome(consumer, temperature, this.FULL_RANGE, this.oceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, VANILLA);
			}
		}

		private void addInlandBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
			this.addMidSlice(consumer, Climate.Parameter.span(-1.0F, -0.93333334F));
			this.addHighSlice(consumer, Climate.Parameter.span(-0.93333334F, -0.7666667F));
			this.addPeaks(consumer, Climate.Parameter.span(-0.7666667F, -0.56666666F));
			this.addHighSlice(consumer, Climate.Parameter.span(-0.56666666F, -0.4F));
			this.addMidSlice(consumer, Climate.Parameter.span(-0.4F, -0.26666668F));
			this.addLowSlice(consumer, Climate.Parameter.span(-0.26666668F, -0.05F));
			this.addValleys(consumer, Climate.Parameter.span(-0.05F, 0.05F));
			this.addLowSlice(consumer, Climate.Parameter.span(0.05F, 0.26666668F));
			this.addMidSlice(consumer, Climate.Parameter.span(0.26666668F, 0.4F));
			this.addHighSlice(consumer, Climate.Parameter.span(0.4F, 0.56666666F));
			this.addPeaks(consumer, Climate.Parameter.span(0.56666666F, 0.7666667F));
			this.addHighSlice(consumer, Climate.Parameter.span(0.7666667F, 0.93333334F));
			this.addMidSlice(consumer, Climate.Parameter.span(0.93333334F, 1.0F));
		}

		private void addPeaks(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness) {
			for (int i = 0; i < this.temperatures.length; ++i) {
				Climate.Parameter temperature = this.temperatures[i];

				for (int j = 0; j < this.humidities.length; ++j) {
					Climate.Parameter humidity = this.humidities[j];
					ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, weirdness);
					ResourceKey<Biome> resourcekey1 = this.pickMiddleBiomeOrBadlandsIfHot(i, j, weirdness);
					ResourceKey<Biome> resourcekey2 = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, weirdness);
					ResourceKey<Biome> resourcekey3 = this.pickPlateauBiome(i, j, weirdness);
					ResourceKey<Biome> resourcekey4 = this.pickShatteredBiome(i, j, weirdness);
					ResourceKey<Biome> resourcekey5 = this.maybePickWindsweptSavannaBiome(i, j, weirdness, resourcekey4);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[0], weirdness, 0.0F, VANILLA);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[1], weirdness, 0.0F, resourcekey2);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], weirdness, 0.0F, VANILLA);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), weirdness, 0.0F, resourcekey);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], weirdness, 0.0F, resourcekey3);
					this.addSurfaceBiome(consumer, temperature, humidity, this.midInlandContinentalness, this.erosions[3], weirdness, 0.0F, resourcekey1);
					this.addSurfaceBiome(consumer, temperature, humidity, this.farInlandContinentalness, this.erosions[3], weirdness, 0.0F, resourcekey3);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, resourcekey);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[5], weirdness, 0.0F, resourcekey5);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], weirdness, 0.0F, resourcekey4);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, resourcekey);
				}
			}
		}

		private void addHighSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness) {
			for (int i = 0; i < this.temperatures.length; ++i) {
				Climate.Parameter temperature = this.temperatures[i];

				for (int j = 0; j < this.humidities.length; ++j) {
					Climate.Parameter humidity = this.humidities[j];
					ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, weirdness);
					ResourceKey<Biome> resourcekey1 = this.pickMiddleBiomeOrBadlandsIfHot(i, j, weirdness);
					ResourceKey<Biome> resourcekey2 = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, weirdness);
					ResourceKey<Biome> resourcekey3 = this.pickPlateauBiome(i, j, weirdness);
					ResourceKey<Biome> resourcekey4 = this.pickShatteredBiome(i, j, weirdness);
					ResourceKey<Biome> resourcekey5 = this.maybePickWindsweptSavannaBiome(i, j, weirdness, resourcekey);
					ResourceKey<Biome> resourcekey6 = this.pickSlopeBiome(i, j, weirdness);
					this.addSurfaceBiome(consumer, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, resourcekey);
					this.addSurfaceBiome(consumer, temperature, humidity, this.nearInlandContinentalness, this.erosions[0], weirdness, 0.0F, resourcekey6);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[0], weirdness, 0.0F, VANILLA);
					this.addSurfaceBiome(consumer, temperature, humidity, this.nearInlandContinentalness, this.erosions[1], weirdness, 0.0F, resourcekey2);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], weirdness, 0.0F, resourcekey6);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), weirdness, 0.0F, resourcekey);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], weirdness, 0.0F, resourcekey3);
					this.addSurfaceBiome(consumer, temperature, humidity, this.midInlandContinentalness, this.erosions[3], weirdness, 0.0F, resourcekey1);
					this.addSurfaceBiome(consumer, temperature, humidity, this.farInlandContinentalness, this.erosions[3], weirdness, 0.0F, resourcekey3);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, resourcekey);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[5], weirdness, 0.0F, resourcekey5);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], weirdness, 0.0F, resourcekey4);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, resourcekey);
				}
			}
		}

		private void addMidSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness) {
			this.addSurfaceBiome(consumer, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), weirdness, 0.0F, VANILLA);
			this.addSurfaceBiome(consumer, this.UNFROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, VANILLA);

			for (int i = 0; i < this.temperatures.length; ++i) {
				Climate.Parameter temperature = this.temperatures[i];

				for (int j = 0; j < this.humidities.length; ++j) {
					Climate.Parameter humidity = this.humidities[j];
					ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, weirdness);
					ResourceKey<Biome> resourcekey1 = this.pickMiddleBiomeOrBadlandsIfHot(i, j, weirdness);
					ResourceKey<Biome> resourcekey2 = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, weirdness);
					ResourceKey<Biome> resourcekey3 = this.pickShatteredBiome(i, j, weirdness);
					ResourceKey<Biome> resourcekey4 = this.pickPlateauBiome(i, j, weirdness);
					ResourceKey<Biome> resourcekey6 = this.maybePickWindsweptSavannaBiome(i, j, weirdness, resourcekey);
					ResourceKey<Biome> resourcekey7 = this.pickShatteredCoastBiome(i, j, weirdness);
					ResourceKey<Biome> resourcekey8 = this.pickSlopeBiome(i, j, weirdness);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[0], weirdness, 0.0F, resourcekey8);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[1], weirdness, 0.0F, resourcekey2);
					this.addSurfaceBiome(consumer, temperature, humidity, this.farInlandContinentalness, this.erosions[1], weirdness, 0.0F, i == 0 ? resourcekey8 : resourcekey4);
					this.addSurfaceBiome(consumer, temperature, humidity, this.nearInlandContinentalness, this.erosions[2], weirdness, 0.0F, resourcekey);
					this.addSurfaceBiome(consumer, temperature, humidity, this.midInlandContinentalness, this.erosions[2], weirdness, 0.0F, resourcekey1);
					this.addSurfaceBiome(consumer, temperature, humidity, this.farInlandContinentalness, this.erosions[2], weirdness, 0.0F, resourcekey4);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[3], weirdness, 0.0F, resourcekey);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[3], weirdness, 0.0F, resourcekey1);
					if (weirdness.max() < 0L) {
						this.addSurfaceBiome(consumer, temperature, humidity, this.coastContinentalness, this.erosions[4], weirdness, 0.0F, VANILLA);
						this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, resourcekey);
					} else {
						this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, resourcekey);
					}

					this.addSurfaceBiome(consumer, temperature, humidity, this.coastContinentalness, this.erosions[5], weirdness, 0.0F, resourcekey7);
					this.addSurfaceBiome(consumer, temperature, humidity, this.nearInlandContinentalness, this.erosions[5], weirdness, 0.0F, resourcekey6);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], weirdness, 0.0F, resourcekey3);
					if (weirdness.max() < 0L) {
						this.addSurfaceBiome(consumer, temperature, humidity, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, VANILLA);
					} else {
						this.addSurfaceBiome(consumer, temperature, humidity, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, resourcekey);
					}

					if (i == 0) {
						this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, resourcekey);
					}
				}
			}
		}

		private void addLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness) {
			this.addSurfaceBiome(consumer, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), weirdness, 0.0F, VANILLA);
			this.addSurfaceBiome(consumer, this.UNFROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, VANILLA);

			for (int i = 0; i < this.temperatures.length; ++i) {
				Climate.Parameter temperature = this.temperatures[i];

				for (int j = 0; j < this.humidities.length; ++j) {
					Climate.Parameter humidity = this.humidities[j];
					ResourceKey<Biome> resourcekey = this.pickMiddleBiome(i, j, weirdness);
					ResourceKey<Biome> resourcekey1 = this.pickMiddleBiomeOrBadlandsIfHot(i, j, weirdness);
					ResourceKey<Biome> resourcekey2 = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, weirdness);
					ResourceKey<Biome> resourcekey3 = VANILLA;
					ResourceKey<Biome> resourcekey4 = this.maybePickWindsweptSavannaBiome(i, j, weirdness, resourcekey);
					ResourceKey<Biome> resourcekey5 = this.pickShatteredCoastBiome(i, j, weirdness);
					this.addSurfaceBiome(consumer, temperature, humidity, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, resourcekey1);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, resourcekey2);
					this.addSurfaceBiome(consumer, temperature, humidity, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[2], this.erosions[3]), weirdness, 0.0F, resourcekey);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), weirdness, 0.0F, resourcekey1);
					this.addSurfaceBiome(consumer, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.erosions[3], this.erosions[4]), weirdness, 0.0F, resourcekey3);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, resourcekey);
					this.addSurfaceBiome(consumer, temperature, humidity, this.coastContinentalness, this.erosions[5], weirdness, 0.0F, resourcekey5);
					this.addSurfaceBiome(consumer, temperature, humidity, this.nearInlandContinentalness, this.erosions[5], weirdness, 0.0F, resourcekey4);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], weirdness, 0.0F, resourcekey);
					this.addSurfaceBiome(consumer, temperature, humidity, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, resourcekey3);
					if (i == 0) {
						this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, resourcekey);
					}
				}
			}
		}

		private void addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness) {
			this.addSurfaceBiome(consumer, this.FROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, VANILLA);
			this.addSurfaceBiome(consumer, this.UNFROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, VANILLA);
			this.addSurfaceBiome(consumer, this.FROZEN_RANGE, this.FULL_RANGE, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, VANILLA);
			this.addSurfaceBiome(consumer, this.UNFROZEN_RANGE, this.FULL_RANGE, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, VANILLA);
			this.addSurfaceBiome(consumer, this.FROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[5]), weirdness, 0.0F, VANILLA);
			this.addSurfaceBiome(consumer, this.UNFROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[5]), weirdness, 0.0F, VANILLA);
			this.addSurfaceBiome(consumer, this.FROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, VANILLA);
			this.addSurfaceBiome(consumer, this.UNFROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, VANILLA);
			this.addSurfaceBiome(consumer, this.UNFROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, VANILLA);
			this.addSurfaceBiome(consumer, this.FROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, VANILLA);

			for (int i = 0; i < this.temperatures.length; ++i) {
				Climate.Parameter temperature = this.temperatures[i];

				for (int j = 0; j < this.humidities.length; ++j) {
					Climate.Parameter humidity = this.humidities[j];
					ResourceKey<Biome> resourcekey = this.pickMiddleBiomeOrBadlandsIfHot(i, j, weirdness);
					this.addSurfaceBiome(consumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, resourcekey);
				}
			}
		}

		private void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
			this.addUndergroundBiome(consumer, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.8F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, 0.0F, VANILLA);
			this.addUndergroundBiome(consumer, this.FULL_RANGE, Climate.Parameter.span(0.7F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, 0.0F, VANILLA);
		}

		private ResourceKey<Biome> pickMiddleBiome(int temperatureIndex, int humidityIndex, Climate.Parameter weirdness) {
			if (weirdness.max() < 0L) {
				return this.MIDDLE_BIOMES[temperatureIndex][humidityIndex];
			} else {
				ResourceKey<Biome> resourcekey = this.MIDDLE_BIOMES_VARIANT[temperatureIndex][humidityIndex];
				return resourcekey == null ? this.MIDDLE_BIOMES[temperatureIndex][humidityIndex] : resourcekey;
			}
		}

		private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHot(int p_187192_, int p_187193_, Climate.Parameter p_187194_) {
			return p_187192_ == 4 ? VANILLA : this.pickMiddleBiome(p_187192_, p_187193_, p_187194_);
		}

		private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(int p_187212_, int p_187213_, Climate.Parameter p_187214_) {
			return p_187212_ == 0 ? this.pickSlopeBiome(p_187212_, p_187213_, p_187214_) : this.pickMiddleBiomeOrBadlandsIfHot(p_187212_, p_187213_, p_187214_);
		}

		private ResourceKey<Biome> maybePickWindsweptSavannaBiome(int p_201991_, int p_201992_, Climate.Parameter p_201993_, ResourceKey<Biome> p_201994_) {
			return p_201991_ > 1 && p_201992_ < 4 && p_201993_.max() >= 0L ? VANILLA : p_201994_;
		}

		private ResourceKey<Biome> pickShatteredCoastBiome(int temperatureIndex, int humidityIndex, Climate.Parameter p_187225_) {
			ResourceKey<Biome> resourcekey = p_187225_.max() >= 0L ? this.pickMiddleBiome(temperatureIndex, humidityIndex, p_187225_) : VANILLA;
			return this.maybePickWindsweptSavannaBiome(temperatureIndex, humidityIndex, p_187225_, resourcekey);
		}

		private ResourceKey<Biome> pickPlateauBiome(int p_187234_, int p_187235_, Climate.Parameter p_187236_) {
			if (p_187236_.max() < 0L) {
				return this.PLATEAU_BIOMES[p_187234_][p_187235_];
			} else {
				ResourceKey<Biome> resourcekey = this.PLATEAU_BIOMES_VARIANT[p_187234_][p_187235_];
				return resourcekey == null ? this.PLATEAU_BIOMES[p_187234_][p_187235_] : resourcekey;
			}
		}

		private ResourceKey<Biome> pickSlopeBiome(int p_187245_, int p_187246_, Climate.Parameter p_187247_) {
			if (p_187245_ >= 3) {
				return this.pickPlateauBiome(p_187245_, p_187246_, p_187247_);
			} else {
				return VANILLA;
			}
		}

		private ResourceKey<Biome> pickShatteredBiome(int temperatureIndex, int humidityIndex, Climate.Parameter weirdness) {
			return temperatureIndex > 2 ? this.pickMiddleBiome(temperatureIndex, humidityIndex, weirdness) : VANILLA;
		}

		private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187181_, Climate.Parameter p_187182_, Climate.Parameter p_187183_, Climate.Parameter p_187184_, Climate.Parameter p_187185_, Climate.Parameter p_187186_, float p_187187_, ResourceKey<Biome> p_187188_) {
			p_187181_.accept(Pair.of(Climate.parameters(p_187182_, p_187183_, p_187184_, p_187185_, Climate.Parameter.point(0.0F), p_187186_, p_187187_), p_187188_));
			p_187181_.accept(Pair.of(Climate.parameters(p_187182_, p_187183_, p_187184_, p_187185_, Climate.Parameter.point(1.0F), p_187186_, p_187187_), p_187188_));
		}

		private void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> p_187201_, Climate.Parameter p_187202_, Climate.Parameter p_187203_, Climate.Parameter p_187204_, Climate.Parameter p_187205_, Climate.Parameter p_187206_, float p_187207_, ResourceKey<Biome> p_187208_) {
			p_187201_.accept(Pair.of(Climate.parameters(p_187202_, p_187203_, p_187204_, p_187205_, Climate.Parameter.span(0.2F, 0.9F), p_187206_, p_187207_), p_187208_));
		}
	}

}
