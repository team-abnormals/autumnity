package com.minecraftabnormals.autumnity.common.block;

import com.minecraftabnormals.autumnity.core.other.AutumnityEvents;
import com.minecraftabnormals.autumnity.core.registry.AutumnityEffects;
import com.minecraftabnormals.autumnity.core.registry.AutumnityItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.IWorld;

public class CookedTurkeyBlock extends TurkeyBlock {
	public CookedTurkeyBlock(Properties builder) {
		super(builder);
	}

	@Override
	protected void restoreHunger(IWorld worldIn, PlayerEntity player) {
		player.getFoodData().eat(AutumnityItems.Foods.COOKED_TURKEY.getNutrition(), AutumnityItems.Foods.COOKED_TURKEY.getSaturationModifier());

		int i = AutumnityItems.Foods.COOKED_TURKEY.getNutrition();
		int j = i == 1 ? i : (int) (i * 0.5F);

		if (player.hasEffect(AutumnityEffects.FOUL_TASTE.get())) {
			player.getFoodData().eat(j, 0.0F);
			AutumnityEvents.updateFoulTaste(player);
		}
	}

	@Override
	protected Item getLeg() {
		return AutumnityItems.COOKED_TURKEY_PIECE.get();
	}
}