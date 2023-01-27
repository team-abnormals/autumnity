package com.teamabnormals.autumnity.core.registry;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.autumnity.common.item.SnailShellChestplateItem;
import com.teamabnormals.autumnity.common.item.SyrupBottleItem;
import com.teamabnormals.autumnity.common.item.TurkeyEggItem;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.other.AutumnityTiers;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBannerPatternTags;
import com.teamabnormals.autumnity.integration.boatload.AutumnityBoatTypes;
import com.teamabnormals.blueprint.core.util.registry.AbstractSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityItems {
	public static final ItemSubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getItemSubHelper();

	public static final Pair<RegistryObject<Item>, RegistryObject<Item>> MAPLE_BOAT = HELPER.createBoatAndChestBoatItem("maple", AutumnityBlocks.MAPLE_PLANKS);
	public static final RegistryObject<Item> MAPLE_FURNACE_BOAT = HELPER.createItem("maple_furnace_boat", ModList.get().isLoaded("boatload") ? AutumnityBoatTypes.MAPLE_FURNACE_BOAT : () -> new Item(new Item.Properties().tab(AbstractSubRegistryHelper.areModsLoaded("boatload") ? CreativeModeTab.TAB_TRANSPORTATION : null)));
	public static final RegistryObject<Item> LARGE_MAPLE_BOAT = HELPER.createItem("large_maple_boat", ModList.get().isLoaded("boatload") ? AutumnityBoatTypes.LARGE_MAPLE_BOAT : () -> new Item(new Item.Properties().tab(AbstractSubRegistryHelper.areModsLoaded("boatload") ? CreativeModeTab.TAB_TRANSPORTATION : null)));

	public static final RegistryObject<Item> SAP_BOTTLE = HELPER.createItem("sap_bottle", () -> new Item((new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(CreativeModeTab.TAB_MATERIALS)));
	public static final RegistryObject<Item> SYRUP_BOTTLE = HELPER.createItem("syrup_bottle", () -> new SyrupBottleItem((new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(CreativeModeTab.TAB_FOOD).food(AutumnityFoods.SYRUP_BOTTLE)));
	public static final RegistryObject<Item> FOUL_BERRIES = HELPER.createItem("foul_berries", () -> new ItemNameBlockItem(AutumnityBlocks.FOUL_BERRY_BUSH.get(), (new Item.Properties()).tab(CreativeModeTab.TAB_FOOD).food(AutumnityFoods.FOUL_BERRIES)));
	public static final RegistryObject<Item> FOUL_BERRY_PIPS = HELPER.createItem("foul_berry_pips", () -> new ItemNameBlockItem(AutumnityBlocks.FOUL_BERRY_BUSH_PIPS.get(), (new Item.Properties()).tab(AbstractSubRegistryHelper.areModsLoaded("berry_good") ? CreativeModeTab.TAB_MISC : null)));
	public static final RegistryObject<Item> FOUL_SOUP = HELPER.createItem("foul_soup", () -> new BowlFoodItem((new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_FOOD).food(AutumnityFoods.FOUL_SOUP)));
	public static final RegistryObject<Item> PUMPKIN_BREAD = HELPER.createItem("pumpkin_bread", () -> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_FOOD).food(AutumnityFoods.PUMPKIN_BREAD)));

	public static final RegistryObject<Item> SNAIL_SHELL_PIECE = HELPER.createItem("snail_shell_piece", () -> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_MATERIALS)));
	public static final RegistryObject<Item> SNAIL_SHELL_CHESTPLATE = HELPER.createItem("snail_shell_chestplate", () -> new SnailShellChestplateItem(AutumnityTiers.SNAIL_SHELL, EquipmentSlot.CHEST, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));

	public static final RegistryObject<Item> TURKEY_EGG = HELPER.createItem("turkey_egg", () -> new TurkeyEggItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> TURKEY_PIECE = HELPER.createItem("turkey_piece", () -> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_FOOD).food(AutumnityFoods.TURKEY)));
	public static final RegistryObject<Item> COOKED_TURKEY_PIECE = HELPER.createItem("cooked_turkey_piece", () -> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_FOOD).food(AutumnityFoods.COOKED_TURKEY)));

	public static final RegistryObject<Item> MAPLE_LEAF_BANNER_PATTERN = HELPER.createItem("maple_leaf_banner_pattern", () -> new BannerPatternItem(AutumnityBannerPatternTags.PATTERN_ITEM_MAPLE_LEAF, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));
	public static final RegistryObject<Item> SWIRL_BANNER_PATTERN = HELPER.createItem("swirl_banner_pattern", () -> new BannerPatternItem(AutumnityBannerPatternTags.PATTERN_ITEM_SWIRL, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));

	public static final RegistryObject<ForgeSpawnEggItem> SNAIL_SPAWN_EGG = HELPER.createSpawnEggItem("snail", AutumnityEntityTypes.SNAIL::get, 7355937, 14727558);
	public static final RegistryObject<ForgeSpawnEggItem> TURKEY_SPAWN_EGG = HELPER.createSpawnEggItem("turkey", AutumnityEntityTypes.TURKEY::get, 6765623, 5019859);

	public static class AutumnityFoods {
		public static final FoodProperties SYRUP_BOTTLE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.3F).build();
		public static final FoodProperties FOUL_BERRIES = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).effect(() -> new MobEffectInstance(AutumnityMobEffects.FOUL_TASTE.get(), 320, 0), 1.0F).build();
		public static final FoodProperties FOUL_SOUP = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.6F).effect(() -> new MobEffectInstance(AutumnityMobEffects.FOUL_TASTE.get(), 2400, 1), 1.0F).build();
		public static final FoodProperties PUMPKIN_BREAD = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.4F).build();
		public static final FoodProperties TURKEY = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).meat().effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.1F).fast().build();
		public static final FoodProperties COOKED_TURKEY = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.6F).meat().fast().build();
	}
}