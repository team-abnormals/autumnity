package com.teamabnormals.autumnity.common.item;

import com.teamabnormals.autumnity.common.entity.projectile.TurkeyEggEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TurkeyEggItem extends Item {

	public TurkeyEggItem(Item.Properties builder) {
		super(builder);
	}

	public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand handIn) {
		ItemStack itemstack = player.getItemInHand(handIn);
		worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isClientSide) {
			TurkeyEggEntity turkeyeggentity = new TurkeyEggEntity(worldIn, player);
			turkeyeggentity.setItem(itemstack);
			turkeyeggentity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			worldIn.addFreshEntity(turkeyeggentity);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		if (!player.getAbilities().instabuild) {
			itemstack.shrink(1);
		}

		return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
	}
}