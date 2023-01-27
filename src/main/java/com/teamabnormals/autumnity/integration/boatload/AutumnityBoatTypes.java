package com.teamabnormals.autumnity.integration.boatload;

import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.boatload.common.item.FurnaceBoatItem;
import com.teamabnormals.boatload.common.item.LargeBoatItem;
import com.teamabnormals.boatload.core.api.BoatloadBoatType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class AutumnityBoatTypes {
	public static final BoatloadBoatType MAPLE = BoatloadBoatType.register(BoatloadBoatType.create(new ResourceLocation(Autumnity.MOD_ID, "maple"), () -> AutumnityBlocks.MAPLE_PLANKS.get().asItem(), () -> AutumnityItems.MAPLE_BOAT.getFirst().get(), () -> AutumnityItems.MAPLE_BOAT.getSecond().get(), () -> AutumnityItems.MAPLE_FURNACE_BOAT.get(), () -> AutumnityItems.LARGE_MAPLE_BOAT.get()));

	public static final Supplier<Item> MAPLE_FURNACE_BOAT = () -> new FurnaceBoatItem(MAPLE);
	public static final Supplier<Item> LARGE_MAPLE_BOAT = () -> new LargeBoatItem(MAPLE);
}