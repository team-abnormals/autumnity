package com.teamabnormals.autumnity.common.item;

import com.teamabnormals.autumnity.common.entity.projectile.ThrownTurkeyEgg;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class TurkeyEggItem extends Item implements ProjectileItem {

	public TurkeyEggItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!level.isClientSide) {
			ThrownTurkeyEgg egg = new ThrownTurkeyEgg(level, player);
			egg.setItem(stack);
			egg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			level.addFreshEntity(egg);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		stack.consume(1, player);

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
		ThrownTurkeyEgg egg = new ThrownTurkeyEgg(level, pos.x(), pos.y(), pos.z());
		egg.setItem(stack);
		return egg;
	}
}