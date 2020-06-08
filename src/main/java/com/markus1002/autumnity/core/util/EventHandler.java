package com.markus1002.autumnity.core.util;

import java.util.UUID;

import com.markus1002.autumnity.common.entity.passive.SnailEntity;
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
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
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
	public void onEffectRemap(MissingMappings<Effect> event)
	{
		for (MissingMappings.Mapping<Effect> mapping : event.getMappings())
		{
			if (mapping.key.toString() == "autumnity:anti_healing")
			{
				mapping.remap(ModEffects.LIFE_STASIS);
				break;
			}
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(!event.getWorld().isRemote)
		{
			Entity entity = event.getEntity();

			if(entity instanceof PillagerEntity)
			{
				((PillagerEntity) entity).targetSelector.addGoal(4, new NearestAttackableTargetGoal<>((CreatureEntity)entity, SnailEntity.class, true));
			}
			else if(entity instanceof MooshroomEntity)
			{
				((MooshroomEntity) entity).goalSelector.addGoal(4, new AvoidEntityGoal<>((CreatureEntity)entity, SnailEntity.class, 16.0F, 1.25D, 1.6D, EntityPredicates.CAN_AI_TARGET::test));
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
				if (event.getWorld().getBiome(new BlockPos(livingentity)) == ModBiomes.PUMPKIN_FIELDS.get() && event.getWorld().getRandom().nextFloat() < 0.05F)
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

		entity.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).removeModifier(KNOCKBACK_MODIFIER);
		if(entity.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == ModItems.SNAIL_SHELL_CHESTPLATE.get() && entity.isShiftKeyDown())
		{
			entity.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(KNOCKBACK_MODIFIER);
		}
	}

	@SubscribeEvent
	public void onLivingHeal(LivingHealEvent event)
	{
		if (event.getEntityLiving().isPotionActive(ModEffects.LIFE_STASIS))
		{
			event.setCanceled(true);
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