package com.teamabnormals.autumnity.common.block;

import com.teamabnormals.autumnity.core.other.AutumnityEvents;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.autumnity.core.registry.AutumnityItems.AutumnityFoods;
import com.teamabnormals.autumnity.core.registry.AutumnityMobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelAccessor;

public class CookedTurkeyBlock extends TurkeyBlock {
	public CookedTurkeyBlock(Properties builder) {
		super(builder);
	}

	@Override
	protected void restoreHunger(LevelAccessor worldIn, Player player) {
		player.getFoodData().eat(AutumnityFoods.COOKED_TURKEY.nutrition(), AutumnityFoods.COOKED_TURKEY.saturation());

		int i = AutumnityFoods.COOKED_TURKEY.nutrition();
		int j = i == 1 ? i : (int) (i * 0.5F);

		if (player.hasEffect(AutumnityMobEffects.FOUL_TASTE)) {
			player.getFoodData().eat(j, 0.0F);
			AutumnityEvents.updateFoulTaste(player);
		}
	}

	@Override
	protected Item getLeg() {
		return AutumnityItems.COOKED_TURKEY_PIECE.get();
	}
}