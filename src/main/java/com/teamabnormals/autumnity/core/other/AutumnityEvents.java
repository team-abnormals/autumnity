package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.common.block.RedstoneJackOLanternBlock;
import com.teamabnormals.autumnity.common.entity.animal.SnailEntity;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.registry.AutumnityBiomes;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityMobEffects;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.blueprint.core.util.TradeUtil;
import com.teamabnormals.blueprint.core.util.TradeUtil.BlueprintTrade;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.UUID;

@EventBusSubscriber(modid = Autumnity.MOD_ID)
public class AutumnityEvents {
	private static final AttributeModifier KNOCKBACK_MODIFIER = (new AttributeModifier(UUID.fromString("98D5CD1F-601F-47E6-BEEC-5997E1C4216F"), "Knockback modifier", 1.0D, AttributeModifier.Operation.ADDITION));

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		Level world = event.getWorld();

		if (!world.isClientSide) {
			Entity entity = event.getEntity();

			if (entity instanceof Pillager) {
				((PathfinderMob) entity).targetSelector.addGoal(4, new NearestAttackableTargetGoal<>((PathfinderMob) entity, SnailEntity.class, true));
			} else if (entity instanceof MushroomCow) {
				((PathfinderMob) entity).goalSelector.addGoal(4, new AvoidEntityGoal<>((PathfinderMob) entity, SnailEntity.class, 16.0F, 1.25D, 1.6D, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test));
			}
		}
	}

	@SubscribeEvent
	public static void onLivingSpawn(LivingSpawnEvent.SpecialSpawn event) {
		LevelAccessor world = event.getWorld();
		LivingEntity livingentity = event.getEntityLiving();

		if (livingentity instanceof Zombie || livingentity instanceof AbstractSkeleton) {
			if (livingentity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
				if (DataUtil.matchesKeys(world.getBiome(livingentity.blockPosition()).value().getRegistryName(), AutumnityBiomes.PUMPKIN_FIELDS.getKey()) && world.getRandom().nextFloat() < 0.05F) {
					livingentity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Blocks.CARVED_PUMPKIN));
					((Mob) livingentity).setDropChance(EquipmentSlot.HEAD, 0.0F);
				}
			}
		}
//		else if (livingentity instanceof Cat cat) {
//			if (world instanceof ServerLevel serverLevel && serverLevel.structureFeatureManager().getStructureAt(livingentity.blockPosition(), AutumnityConfiguredStructureFeatures.MAPLE_WITCH_HUT.value()).isValid()) {
//				cat.setCatType(10);
//				cat.setPersistenceRequired();
//			}
//		}
	}

	@SubscribeEvent
	public static void onSnailShellChestplateSneak(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();

		entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(KNOCKBACK_MODIFIER);
		if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == AutumnityItems.SNAIL_SHELL_CHESTPLATE.get() && entity.isShiftKeyDown()) {
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(KNOCKBACK_MODIFIER);
		}
	}

	@SubscribeEvent
	public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		Level world = event.getWorld();
		Player player = event.getPlayer();
		ItemStack itemstack = event.getItemStack();
		Item item = itemstack.getItem();
		BlockPos pos = event.getPos();
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if (!player.isSpectator()) {
			if (item == AutumnityItems.FOUL_BERRIES.get() && ModList.get().isLoaded("berry_good")) {
				event.setUseItem(Event.Result.DENY);
			} else if (player.hasEffect(AutumnityMobEffects.FOUL_TASTE.get()) && player.canEat(false) && (block instanceof CakeBlock || (ModList.get().isLoaded("atmospheric") && block == AutumnityCompat.YUCCA_GATEAU))) {
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
		if (event.getEntityLiving().hasEffect(AutumnityMobEffects.FOUL_TASTE.get()) && event.getEntityLiving() instanceof Player && itemstack.isEdible()) {
			Item item = itemstack.getItem();
			FoodProperties food = item.getFoodProperties();
			boolean flag = true;

			if (item instanceof SuspiciousStewItem) {
				CompoundTag compoundnbt = itemstack.getTag();
				if (compoundnbt != null && compoundnbt.contains("Effects", 9)) {
					ListTag listnbt = compoundnbt.getList("Effects", 10);

					for (int i = 0; i < listnbt.size(); ++i) {
						CompoundTag compoundnbt1 = listnbt.getCompound(i);

						MobEffect effect = MobEffect.byId(compoundnbt1.getByte("EffectId"));
						if (effect == AutumnityMobEffects.FOUL_TASTE.get()) {
							flag = false;
							break;
						}
					}
				}
			} else {
				for (Pair<MobEffectInstance, Float> pair : food.getEffects()) {
					if (pair.getFirst().getEffect() == AutumnityMobEffects.FOUL_TASTE.get()) {
						flag = false;
						break;
					}
				}
			}

			if (flag) {
				Player player = (Player) event.getEntityLiving();

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
				new BlueprintTrade(5, AutumnityBlocks.MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, AutumnityBlocks.YELLOW_MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, AutumnityBlocks.ORANGE_MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, AutumnityBlocks.RED_MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(4, AutumnityBlocks.SNAIL_SLIME.get().asItem(), 1, 5, 1)
		);
	}

	@SubscribeEvent
	public static void onVillagerTradesEvent(VillagerTradesEvent event) {
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.APPRENTICE,
				new BlueprintTrade(2, AutumnityItems.FOUL_BERRIES.get(), 16, 12, 10)
		);

		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.JOURNEYMAN,
				new BlueprintTrade(AutumnityBlocks.TURKEY.get().asItem(), 6, 1, 16, 20)
		);
	}

	@SubscribeEvent
	public static void onMakeJackOLantern(PlayerInteractEvent.RightClickBlock event) {
		ItemStack itemstack = event.getItemStack();
		Block jackolantern = JackOLanternHelper.getJackOLantern(itemstack.getItem());

		if (jackolantern != null) {
			Level world = event.getWorld();
			BlockPos blockpos = event.getPos();
			BlockState blockstate = event.getWorld().getBlockState(event.getPos());
			Player player = event.getPlayer();

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

						world.playSound(null, blockpos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
						if (!event.getPlayer().getAbilities().instabuild) {
							itemstack.shrink(1);
						}
					}

					player.swing(event.getHand());
					event.setCancellationResult(InteractionResult.sidedSuccess(world.isClientSide));
					event.setUseItem(Result.DENY);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPotionAdded(PotionEvent.PotionAddedEvent event) {
		LivingEntity livingentity = event.getEntityLiving();
		MobEffectInstance effect = event.getPotionEffect();
		MobEffectInstance extension = livingentity.getEffect(AutumnityMobEffects.EXTENSION.get());

		if (extension != null) {
			if (effect.getEffect() != AutumnityMobEffects.EXTENSION.get()) {
				effect.update(new MobEffectInstance(effect.getEffect(), effect.getDuration() + 300 + 300 * (extension.getAmplifier() + 1), effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon()));
			}
		}
	}

	public static void updateFoulTaste(Player player) {
		MobEffectInstance effect = player.getEffect(AutumnityMobEffects.FOUL_TASTE.get());

		player.removeEffect(AutumnityMobEffects.FOUL_TASTE.get());
		if (effect.getAmplifier() > 0) {
			player.addEffect(new MobEffectInstance(AutumnityMobEffects.FOUL_TASTE.get(), effect.getDuration(), effect.getAmplifier() - 1));
		}

		if (player instanceof ServerPlayer serverplayerentity) {
			if (!player.getCommandSenderWorld().isClientSide()) {
				AutumnityCriteriaTriggers.CURE_FOUL_TASTE.trigger((serverplayerentity));
			}
		}
	}
}