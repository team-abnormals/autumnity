package com.minecraftabnormals.autumnity.core.registry;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.autumnity.common.world.gen.feature.structure.MapleWitchHutPieces;
import com.minecraftabnormals.autumnity.common.world.gen.feature.structure.MapleWitchHutStructure;
import com.minecraftabnormals.autumnity.core.Autumnity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityStructures {
	public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Autumnity.MOD_ID);

	public static final RegistryObject<Structure<NoFeatureConfig>> MAPLE_WITCH_HUT = STRUCTURES.register("maple_witch_hut", () -> new MapleWitchHutStructure(NoFeatureConfig.CODEC));

	public static final class Configured {
		public static final StructureFeature<?, ?> MAPLE_WITCH_HUT = AutumnityStructures.MAPLE_WITCH_HUT.get().configured(IFeatureConfig.NONE);
		
		private static <FC extends IFeatureConfig> void register(String name, StructureFeature<FC, ?> stuctureFeature) {
			Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Autumnity.MOD_ID, name), stuctureFeature);
		}

		public static void registerConfiguredStructureFeatures() {
			register("maple_witch_hut", MAPLE_WITCH_HUT);
		}
	}

	public static final class Pieces {
		public static final IStructurePieceType MAPLE_WITCH_HUT_PIECE = IStructurePieceType.setPieceId(MapleWitchHutPieces.Piece::new, "maple_witch_hut_piece");
	}
	
	public static void registerNoiseSettings() {
		Structure.STRUCTURES_REGISTRY.put("maple_witch_hut", MAPLE_WITCH_HUT.get());
		WorldGenRegistries.NOISE_GENERATOR_SETTINGS.forEach(settings -> {
			settings.structureSettings().structureConfig().put(AutumnityStructures.MAPLE_WITCH_HUT.get(), new StructureSeparationSettings(32, 8, 56181419));
		});
		Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(MAPLE_WITCH_HUT.get()).build();
	}
}