package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.common.block.RedstoneJackOLanternBlock;
import com.teamabnormals.autumnity.common.block.TurkeyBlock;
import com.teamabnormals.autumnity.common.block.util.JackOLanternUtil;
import com.teamabnormals.autumnity.common.entity.animal.Snail;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.autumnity.core.other.tags.AutumnityEntityTypeTags;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityCriteriaTriggers;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.autumnity.core.registry.AutumnityMobEffects;
import com.teamabnormals.autumnity.core.registry.datapack.AutumnityBiomes;
import com.teamabnormals.blueprint.core.events.FallingBlockEvent.FallingBlockTickEvent;
import com.teamabnormals.blueprint.core.util.TradeUtil;
import com.teamabnormals.blueprint.core.util.TradeUtil.BlueprintTrade;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

@EventBusSubscriber(modid = Autumnity.MOD_ID)
public class AutumnityEvents {
	private static final AttributeModifier KNOCKBACK_MODIFIER = (new AttributeModifier(Autumnity.location("knockback_modifier"), 1.0D, Operation.ADD_VALUE));

	@SubscribeEvent
	public static void rightClickBlock(RightClickBlock event) {
		ItemStack stack = event.getItemStack();
		if (stack.is(AutumnityItems.FOUL_BERRIES.get()) && ModList.get().isLoaded("berry_good") && AutumnityConfig.COMMON.foulBerriesRequirePips.get()) {
			event.setUseItem(TriState.FALSE);
		}
	}

	@SubscribeEvent
	public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
		Level level = event.getLevel();

		if (!level.isClientSide) {
			Entity entity = event.getEntity();

			if (entity instanceof Pillager) {
				((PathfinderMob) entity).targetSelector.addGoal(4, new NearestAttackableTargetGoal<>((PathfinderMob) entity, Snail.class, true));
			} else if (entity instanceof MushroomCow) {
				((PathfinderMob) entity).goalSelector.addGoal(4, new AvoidEntityGoal<>((PathfinderMob) entity, Snail.class, 16.0F, 1.25D, 1.6D, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test));
			}
		}
	}

	@SubscribeEvent
	public static void onLivingSpawn(FinalizeSpawnEvent event) {
		LevelAccessor level = event.getLevel();
		Mob entity = event.getEntity();

		if (entity instanceof Zombie || entity instanceof AbstractSkeleton) {
			if (entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
				BlockPos pos = entity.blockPosition();
				if (level.getRandom().nextFloat() < 0.05F && level.getBrightness(LightLayer.SKY, pos) > 11 && level.getBiome(pos).is(AutumnityBiomes.PUMPKIN_FIELDS)) {
					entity.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Blocks.CARVED_PUMPKIN));
					entity.setDropChance(EquipmentSlot.HEAD, 0.0F);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onSnailShellChestplateSneak(EntityTickEvent.Post event) {
		if (event.getEntity() instanceof LivingEntity entity && entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(KNOCKBACK_MODIFIER);
			if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == AutumnityItems.SNAIL_SHELL_CHESTPLATE.get() && entity.isSteppingCarefully()) {
				entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(KNOCKBACK_MODIFIER);
			}
		}
	}

	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event) {
		Level level = event.getLevel();
		Player player = event.getEntity();
		ItemStack itemstack = event.getItemStack();
		Item item = itemstack.getItem();
		BlockPos pos = event.getPos();
		BlockState state = level.getBlockState(pos);
		Block block = state.getBlock();

		if (!player.isSpectator()) {
			if (item == AutumnityItems.FOUL_BERRIES.get() && ModList.get().isLoaded("berry_good")) {
				event.setUseItem(TriState.FALSE);
			} else if (player.hasEffect(AutumnityMobEffects.FOUL_TASTE) && player.canEat(false) && (block instanceof CakeBlock || (ModList.get().isLoaded("atmospheric") && block == BuiltInRegistries.BLOCK.get(AutumnityConstants.YUCCA_GATEAU)))) {
				if (player.getFoodData().getFoodLevel() < 19) {
					player.getFoodData().eat(1, 0.0F);
				}
				updateFoulTaste(player);
			}
		}


		if (block == Blocks.CARVED_PUMPKIN) {
			Block jackolantern = JackOLanternUtil.getJackOLantern(itemstack);

			if (jackolantern instanceof CarvedPumpkinBlock) {
				Direction hitface = event.getFace();
				Direction facing = state.getValue(CarvedPumpkinBlock.FACING);

				if (hitface == facing) {
					if (!level.isClientSide()) {
						BlockState blockstate = jackolantern.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, facing);
						if (jackolantern == AutumnityBlocks.GIANT_REDSTONE_JACK_O_LANTERN_CHUNK.get()) {
							blockstate = blockstate.setValue(RedstoneJackOLanternBlock.LIT, level.hasNeighborSignal(pos));
						}

						level.setBlock(pos, blockstate, 11);
						level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
					}

					if (!player.getAbilities().instabuild) itemstack.shrink(1);
					event.setCanceled(true);
					event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
				}
			}
		}
	}

	@SubscribeEvent
	public static void onFoulBerriesEaten(LivingEntityUseItemEvent.Finish event) {
		ItemStack stack = event.getItem();
		FoodProperties food = stack.getFoodProperties(event.getEntity());
		if (event.getEntity() instanceof Player player && player.hasEffect(AutumnityMobEffects.FOUL_TASTE) && food != null) {
			for (PossibleEffect effect : food.effects()) {
				if (effect.effect().is(AutumnityMobEffects.FOUL_TASTE)) {
					return;
				}
			}

			SuspiciousStewEffects stewEffects = stack.getOrDefault(DataComponents.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffects.EMPTY);
			for (SuspiciousStewEffects.Entry entry : stewEffects.effects()) {
				if (entry.effect().is(AutumnityMobEffects.FOUL_TASTE)) {
					return;
				}
			}

			player.getFoodData().eat(Math.max(1, (int) (food.nutrition() * 0.5F)), 0.0F);
			updateFoulTaste(player);
		}
	}

	@SubscribeEvent
	public static void onWandererTradesEvent(WandererTradesEvent event) {
		TradeUtil.addWandererTrades(event,
				new BlueprintTrade(5, AutumnityBlocks.MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, AutumnityBlocks.YELLOW_MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, AutumnityBlocks.ORANGE_MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, AutumnityBlocks.RED_MAPLE_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(4, AutumnityBlocks.SNAIL_GOO.get().asItem(), 1, 5, 1)
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
	public static void onPotionAdded(MobEffectEvent.Added event) {
		LivingEntity livingentity = event.getEntity();
		MobEffectInstance effect = event.getEffectInstance();
		MobEffectInstance extension = livingentity.getEffect(AutumnityMobEffects.EXTENSION);

		if (extension != null) {
			if (effect.getEffect() != AutumnityMobEffects.EXTENSION.get()) {
				effect.update(new MobEffectInstance(effect.getEffect(), effect.getDuration() + 300 + 300 * (extension.getAmplifier() + 1), effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon()));
			}
		}
	}

	@SubscribeEvent
	public static void onFallingBlockTick(FallingBlockTickEvent event) {
		FallingBlockEntity fallingblock = event.getEntity();
		Level level = fallingblock.level();
		BlockState state = fallingblock.getBlockState();

		if (!level.isClientSide() && state.getBlock() instanceof TurkeyBlock && state.getValue(TurkeyBlock.CHUNKS) == 0 && fallingblock.getDeltaMovement().y() < 0.0D) {
			AABB aabb = fallingblock.getBoundingBox().inflate(0.0D, 0.2D, 0.0D);

			for (Entity entity : level.getEntities(fallingblock, aabb, (entity) -> {
				return entity.getType().is(AutumnityEntityTypeTags.CAN_WEAR_TURKEY) && ((LivingEntity) entity).getItemBySlot(EquipmentSlot.HEAD).isEmpty();
			})) {
				if (entity instanceof LivingEntity living && fallingblock.getY() >= entity.getEyeHeight()) {
					living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(state.getBlock().asItem()));
					fallingblock.discard();
					event.setCanceled(true);
					break;
				}
			}
		}
	}

	public static void updateFoulTaste(Player player) {
		MobEffectInstance effect = player.getEffect(AutumnityMobEffects.FOUL_TASTE);

		player.removeEffect(AutumnityMobEffects.FOUL_TASTE);
		if (effect.getAmplifier() > 0) {
			player.addEffect(new MobEffectInstance(AutumnityMobEffects.FOUL_TASTE, effect.getDuration(), effect.getAmplifier() - 1));
		}

		if (player instanceof ServerPlayer serverPlayer) {
			if (!player.getCommandSenderWorld().isClientSide()) {
				AutumnityCriteriaTriggers.CURE_FOUL_TASTE.get().trigger(serverPlayer);
			}
		}
	}
}