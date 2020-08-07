package com.markus1002.autumnity.core.other;

import java.util.UUID;

import com.markus1002.autumnity.common.entity.passive.SnailEntity;
import com.markus1002.autumnity.common.item.ModFoods;
import com.markus1002.autumnity.core.registry.ModBiomes;
import com.markus1002.autumnity.core.registry.ModBlocks;
import com.markus1002.autumnity.core.registry.ModEffects;
import com.markus1002.autumnity.core.registry.ModItems;
import com.teamabnormals.abnormals_core.core.utils.TradeUtils;

import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.EntityPredicates;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler
{
	private static final AttributeModifier KNOCKBACK_MODIFIER = (new AttributeModifier(UUID.fromString("98D5CD1F-601F-47E6-BEEC-5997E1C4216F"), "Knockback modifier", 1.0D, AttributeModifier.Operation.ADDITION));

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(!event.getWorld().isRemote)
		{
			Entity entity = event.getEntity();

			if(entity instanceof PillagerEntity)
			{
				((CreatureEntity) entity).targetSelector.addGoal(4, new NearestAttackableTargetGoal<>((CreatureEntity)entity, SnailEntity.class, true));
			}
			else if(entity instanceof MooshroomEntity)
			{
				((CreatureEntity) entity).goalSelector.addGoal(4, new AvoidEntityGoal<>((CreatureEntity)entity, SnailEntity.class, 16.0F, 1.25D, 1.6D, EntityPredicates.CAN_AI_TARGET::test));
			}
		}
	}

	@SubscribeEvent
	public void onLivingSpawn(LivingSpawnEvent.SpecialSpawn event)
	{
		LivingEntity livingentity = event.getEntityLiving();
		if (livingentity instanceof ZombieEntity || livingentity instanceof AbstractSkeletonEntity)
		{
			if (livingentity.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty())
			{
				if (event.getWorld().getBiome(livingentity.func_233580_cy_()) == ModBiomes.PUMPKIN_FIELDS.get() && event.getWorld().getRandom().nextFloat() < 0.05F)
				{
					livingentity.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Blocks.CARVED_PUMPKIN));
					((MobEntity) livingentity).setDropChance(EquipmentSlotType.HEAD, 0.0F);
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		LivingEntity entity = event.getEntityLiving();

		entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(KNOCKBACK_MODIFIER);
		if(entity.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == ModItems.SNAIL_SHELL_CHESTPLATE.get() && entity.isSneaking())
		{
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).func_233767_b_(KNOCKBACK_MODIFIER);
		}
	}

	@SubscribeEvent
	public void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event)
	{
		ItemStack itemstack = event.getItem();
		if (event.getEntityLiving().isPotionActive(ModEffects.FOUL_TASTE) && event.getEntityLiving() instanceof PlayerEntity && itemstack.isFood())
		{
			Food food = itemstack.getItem().getFood();
			if (food != ModFoods.FOUL_BERRIES)
			{
				PlayerEntity player = (PlayerEntity) event.getEntityLiving();
				EffectInstance effect = player.getActivePotionEffect(ModEffects.FOUL_TASTE);
				
				int i = food.getHealing();
				int j = i == 1 ? i : (int) (i * 0.4F);
				
				player.getFoodStats().addStats(j, 0.0F);
				player.removePotionEffect(ModEffects.FOUL_TASTE);
				if (effect.getAmplifier() > 0)
				{
					player.addPotionEffect(new EffectInstance(ModEffects.FOUL_TASTE, effect.getDuration(), effect.getAmplifier() - 1));
				}
				
				ModCriteriaTriggers.CURE_FOUL_TASTE.trigger((ServerPlayerEntity) player); 
			}
		}
	}

	@SubscribeEvent
	public void onWandererTradesEvent(WandererTradesEvent event)
	{
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(ModBlocks.MAPLE_SAPLING.get().asItem(), 5, 1, 8, 1));
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(ModBlocks.YELLOW_MAPLE_SAPLING.get().asItem(), 5, 1, 8, 1));
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(ModBlocks.ORANGE_MAPLE_SAPLING.get().asItem(), 5, 1, 8, 1));
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(ModBlocks.RED_MAPLE_SAPLING.get().asItem(), 5, 1, 8, 1));
		event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(ModBlocks.SNAIL_SLIME.get().asItem(), 4, 1, 5, 1));
	}

	@SubscribeEvent
	public void onVillagerTradesEvent(VillagerTradesEvent event)
	{
		if (event.getType() == VillagerProfession.FARMER)
		{
			event.getTrades().get(2).add(new TradeUtils.ItemsForEmeraldsTrade(ModItems.FOUL_BERRIES.get(), 2, 16, 12, 10));
		}
	}
}