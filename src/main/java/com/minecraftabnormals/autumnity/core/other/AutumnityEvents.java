package com.minecraftabnormals.autumnity.core.other;

import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.minecraftabnormals.abnormals_core.core.util.TradeUtil;
import com.minecraftabnormals.abnormals_core.core.util.TradeUtil.AbnormalsTrade;
import com.minecraftabnormals.autumnity.common.block.RedstoneJackOLanternBlock;
import com.minecraftabnormals.autumnity.common.entity.passive.SnailEntity;
import com.minecraftabnormals.autumnity.core.Autumnity;
import com.minecraftabnormals.autumnity.core.registry.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.*;
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
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Autumnity.MOD_ID)
public class AutumnityEvents {
	private static final AttributeModifier KNOCKBACK_MODIFIER = (new AttributeModifier(UUID.fromString("98D5CD1F-601F-47E6-BEEC-5997E1C4216F"), "Knockback modifier", 1.0D, AttributeModifier.Operation.ADDITION));

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		World world = event.getWorld();

		if (!world.isClientSide) {
			Entity entity = event.getEntity();

			if (entity instanceof PillagerEntity) {
				((CreatureEntity) entity).targetSelector.addGoal(4, new NearestAttackableTargetGoal<>((CreatureEntity) entity, SnailEntity.class, true));
			} else if (entity instanceof MooshroomEntity) {
				((CreatureEntity) entity).goalSelector.addGoal(4, new AvoidEntityGoal<>((CreatureEntity) entity, SnailEntity.class, 16.0F, 1.25D, 1.6D, EntityPredicates.NO_CREATIVE_OR_SPECTATOR::test));
			}
		}
	}

	@SubscribeEvent
	public static void onLivingSpawn(LivingSpawnEvent.SpecialSpawn event) {
		IWorld world = event.getWorld();
		LivingEntity livingentity = event.getEntityLiving();

		if (livingentity instanceof ZombieEntity || livingentity instanceof AbstractSkeletonEntity) {
			if (livingentity.getItemBySlot(EquipmentSlotType.HEAD).isEmpty()) {
				if (DataUtil.matchesKeys(world.getBiome(livingentity.blockPosition()).getRegistryName(), AutumnityBiomes.PUMPKIN_FIELDS.getKey()) && world.getRandom().nextFloat() < 0.05F) {
					livingentity.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(Blocks.CARVED_PUMPKIN));
					((MobEntity) livingentity).setDropChance(EquipmentSlotType.HEAD, 0.0F);
				}
			}
		} else if (livingentity instanceof CatEntity) {
			if (world instanceof ServerWorld && (((ServerWorld) world).structureFeatureManager().getStructureAt(livingentity.blockPosition(), true, AutumnityStructures.MAPLE_WITCH_HUT.get()).isValid())) {
				((CatEntity) livingentity).setCatType(10);
				((CatEntity) livingentity).setPersistenceRequired();
			}
		}
	}

	@SubscribeEvent
	public static void onSnailShellChestplateSneak(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();

		entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(KNOCKBACK_MODIFIER);
		if (entity.getItemBySlot(EquipmentSlotType.CHEST).getItem() == AutumnityItems.SNAIL_SHELL_CHESTPLATE.get() && entity.isShiftKeyDown()) {
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(KNOCKBACK_MODIFIER);
		}
	}

	@SubscribeEvent
	public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		World world = event.getWorld();
		PlayerEntity player = event.getPlayer();
		ItemStack itemstack = event.getItemStack();
		Item item = itemstack.getItem();
		BlockPos pos = event.getPos();
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (!player.isSpectator()) {
			if (item == AutumnityItems.FOUL_BERRIES.get() && ModList.get().isLoaded("berry_good")) {
				event.setUseItem(Event.Result.DENY);
			} else if (player.hasEffect(AutumnityEffects.FOUL_TASTE.get()) && player.canEat(false) && (block instanceof CakeBlock || (ModList.get().isLoaded("atmospheric") && block == AutumnityCompat.YUCCA_GATEAU))) {
				if (player.getFoodData().getFoodLevel() < 19) {
					player.getFoodData().eat(1, 0.0F);
				}
				updateFoulTaste(player);
			}
		}
	}

	@SubscribeEvent
	public static void onFoulBerriesEaten(LivingEntityUseItemEvent.Finish event) {
		ItemStack itemstack = event.getItem();
		if (event.getEntityLiving().hasEffect(AutumnityEffects.FOUL_TASTE.get()) && event.getEntityLiving() instanceof PlayerEntity && itemstack.isEdible()) {
			Item item = itemstack.getItem();
			Food food = item.getFoodProperties();
			boolean flag = true;

			if (item instanceof SuspiciousStewItem) {
				CompoundNBT compoundnbt = itemstack.getTag();
				if (compoundnbt != null && compoundnbt.contains("Effects", 9)) {
					ListNBT listnbt = compoundnbt.getList("Effects", 10);

					for (int i = 0; i < listnbt.size(); ++i) {
						CompoundNBT compoundnbt1 = listnbt.getCompound(i);

						Effect effect = Effect.byId(compoundnbt1.getByte("EffectId"));
						if (effect == AutumnityEffects.FOUL_TASTE.get()) {
							flag = false;
							break;
						}
					}
				}
			} else {
				for (Pair<EffectInstance, Float> pair : food.getEffects()) {
					if (pair.getFirst().getEffect() == AutumnityEffects.FOUL_TASTE.get()) {
						flag = false;
						break;
					}
				}
			}

			if (flag) {
				PlayerEntity player = (PlayerEntity) event.getEntityLiving();

				int i = food.getNutrition();
				int j = Math.max(1, (int) (i * 0.5F));

				player.getFoodData().eat(j, 0.0F);
				updateFoulTaste(player);
			}
		}
	}

	@SubscribeEvent
	public static void onWandererTradesEvent(WandererTradesEvent event) {
		TradeUtil.addWandererTrades(event,
				new AbnormalsTrade(5, AutumnityBlocks.MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new AbnormalsTrade(5, AutumnityBlocks.YELLOW_MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new AbnormalsTrade(5, AutumnityBlocks.ORANGE_MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new AbnormalsTrade(5, AutumnityBlocks.RED_MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new AbnormalsTrade(4, AutumnityBlocks.SNAIL_SLIME.get().asItem(), 1, 5, 1)
		);
	}

	@SubscribeEvent
	public static void onVillagerTradesEvent(VillagerTradesEvent event) {
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.APPRENTICE,
				new AbnormalsTrade(2, AutumnityItems.FOUL_BERRIES.get(), 16, 12, 10)
		);

		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.JOURNEYMAN,
				new AbnormalsTrade(AutumnityBlocks.TURKEY.get().asItem(), 6, 1, 16, 20)
		);
	}

	@SubscribeEvent
	public static void onMakeJackOLantern(PlayerInteractEvent.RightClickBlock event) {
		ItemStack itemstack = event.getItemStack();
		Block jackolantern = JackOLanternHelper.getJackOLantern(itemstack.getItem());

		if (jackolantern != null) {
			World world = event.getWorld();
			BlockPos blockpos = event.getPos();
			BlockState blockstate = event.getWorld().getBlockState(event.getPos());
			PlayerEntity player = event.getPlayer();

			boolean flag = !player.getMainHandItem().doesSneakBypassUse(world, blockpos, player) || !player.getOffhandItem().doesSneakBypassUse(world, blockpos, player);
			boolean flag1 = player.isSecondaryUseActive() && flag;

			if (blockstate.getBlock() == Blocks.CARVED_PUMPKIN && !flag1) {
				Direction direction = event.getFace();
				Direction direction1 = blockstate.getValue(CarvedPumpkinBlock.FACING);

				if (direction == direction1) {
					if (!world.isClientSide) {
						BlockState blockstate1 = jackolantern == AutumnityBlocks.REDSTONE_JACK_O_LANTERN.get() ? jackolantern.defaultBlockState().setValue(RedstoneJackOLanternBlock.LIT, world.hasNeighborSignal(blockpos)) : jackolantern.defaultBlockState();
						blockstate1.setValue(CarvedPumpkinBlock.FACING, direction1);
						world.setBlock(blockpos, blockstate1, 11);

						world.playSound(null, blockpos, SoundEvents.WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
						if (!event.getPlayer().abilities.instabuild) {
							itemstack.shrink(1);
						}
					}

					player.swing(event.getHand());
					event.setCancellationResult(ActionResultType.sidedSuccess(world.isClientSide));
					event.setUseItem(Result.DENY);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPotionAdded(PotionEvent.PotionAddedEvent event) {
		LivingEntity livingentity = event.getEntityLiving();
		EffectInstance effect = event.getPotionEffect();
		EffectInstance extension = livingentity.getEffect(AutumnityEffects.EXTENSION.get());

		if (extension != null) {
			if (effect.getEffect() != AutumnityEffects.EXTENSION.get()) {
				effect.update(new EffectInstance(effect.getEffect(), effect.getDuration() + 300 + 300 * (extension.getAmplifier() + 1), effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon()));
			}
		}
	}

	public static void updateFoulTaste(PlayerEntity player) {
		EffectInstance effect = player.getEffect(AutumnityEffects.FOUL_TASTE.get());

		player.removeEffect(AutumnityEffects.FOUL_TASTE.get());
		if (effect.getAmplifier() > 0) {
			player.addEffect(new EffectInstance(AutumnityEffects.FOUL_TASTE.get(), effect.getDuration(), effect.getAmplifier() - 1));
		}

		if (player instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
			if (!player.getCommandSenderWorld().isClientSide()) {
				AutumnityCriteriaTriggers.CURE_FOUL_TASTE.trigger((serverplayerentity));
			}
		}
	}
}