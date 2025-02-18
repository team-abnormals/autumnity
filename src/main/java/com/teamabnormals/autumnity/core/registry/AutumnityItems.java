package com.teamabnormals.autumnity.core.registry;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.autumnity.common.item.SnailShellChestplateItem;
import com.teamabnormals.autumnity.common.item.SyrupBottleItem;
import com.teamabnormals.autumnity.common.item.TurkeyEggItem;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.autumnity.core.AutumnityConfig;
import com.teamabnormals.autumnity.core.other.AutumnityConstants;
import com.teamabnormals.autumnity.core.other.AutumnityTiers;
import com.teamabnormals.autumnity.core.other.tags.AutumnityBannerPatternTags;
import com.teamabnormals.autumnity.integration.boatload.AutumnityBoatTypes;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.AbstractSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

import static com.teamabnormals.autumnity.core.registry.AutumnityBlocks.modLoaded;
import static com.teamabnormals.autumnity.core.registry.AutumnityBlocks.ofID;
import static com.teamabnormals.blueprint.core.util.item.ItemStackUtil.is;
import static net.minecraft.world.item.CreativeModeTabs.*;
import static net.minecraft.world.item.crafting.Ingredient.of;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AutumnityItems {
	public static final ItemSubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getItemSubHelper();

	public static final Pair<RegistryObject<Item>, RegistryObject<Item>> MAPLE_BOAT = HELPER.createBoatAndChestBoatItem("maple", AutumnityBlocks.MAPLE_PLANKS);
	public static final RegistryObject<Item> MAPLE_FURNACE_BOAT = HELPER.createItem("maple_furnace_boat", ModList.get().isLoaded("boatload") ? AutumnityBoatTypes.MAPLE_FURNACE_BOAT : () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LARGE_MAPLE_BOAT = HELPER.createItem("large_maple_boat", ModList.get().isLoaded("boatload") ? AutumnityBoatTypes.LARGE_MAPLE_BOAT : () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> SAP_BOTTLE = HELPER.createItem("sap_bottle", () -> new Item((new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
	public static final RegistryObject<Item> SYRUP_BOTTLE = HELPER.createItem("syrup_bottle", () -> new SyrupBottleItem((new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).food(AutumnityFoods.SYRUP_BOTTLE)));
	public static final RegistryObject<Item> FOUL_BERRIES = HELPER.createItem("foul_berries", AbstractSubRegistryHelper.areModsLoaded("berry_good") ? () -> new Item((new Item.Properties()).food(AutumnityFoods.FOUL_BERRIES)) : () -> new ItemNameBlockItem(AutumnityBlocks.FOUL_BERRY_BUSH.get(), (new Item.Properties()).food(AutumnityFoods.FOUL_BERRIES)));
	public static final RegistryObject<Item> FOUL_BERRY_PIPS = HELPER.createItem("foul_berry_pips", AbstractSubRegistryHelper.areModsLoaded("berry_good") ? () -> new ItemNameBlockItem(AutumnityBlocks.FOUL_BERRY_BUSH.get(), (new Item.Properties())) : () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FOUL_SOUP = HELPER.createItem("foul_soup", () -> new BowlFoodItem((new Item.Properties()).stacksTo(1).food(AutumnityFoods.FOUL_SOUP)));
	public static final RegistryObject<Item> PUMPKIN_BREAD = HELPER.createItem("pumpkin_bread", () -> new Item((new Item.Properties()).food(AutumnityFoods.PUMPKIN_BREAD)));

	public static final RegistryObject<Item> SNAIL_SHELL_PIECE = HELPER.createItem("snail_shell_piece", () -> new Item((new Item.Properties())));
	public static final RegistryObject<Item> SNAIL_SHELL_CHESTPLATE = HELPER.createItem("snail_shell_chestplate", () -> new SnailShellChestplateItem(AutumnityTiers.SNAIL_SHELL, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));

	public static final RegistryObject<Item> TURKEY_EGG = HELPER.createItem("turkey_egg", () -> new TurkeyEggItem(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> TURKEY_PIECE = HELPER.createItem("turkey_piece", () -> new Item((new Item.Properties()).food(AutumnityFoods.TURKEY)));
	public static final RegistryObject<Item> COOKED_TURKEY_PIECE = HELPER.createItem("cooked_turkey_piece", () -> new Item((new Item.Properties()).food(AutumnityFoods.COOKED_TURKEY)));

	public static final RegistryObject<Item> MAPLE_LEAF_BANNER_PATTERN = HELPER.createItem("maple_leaf_banner_pattern", () -> new BannerPatternItem(AutumnityBannerPatternTags.PATTERN_ITEM_MAPLE_LEAF, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> SWIRL_BANNER_PATTERN = HELPER.createItem("swirl_banner_pattern", () -> new BannerPatternItem(AutumnityBannerPatternTags.PATTERN_ITEM_SWIRL, new Item.Properties().stacksTo(1)));

	public static final RegistryObject<ForgeSpawnEggItem> SNAIL_SPAWN_EGG = HELPER.createSpawnEggItem("snail", AutumnityEntityTypes.SNAIL::get, 7355937, 14727558);
	public static final RegistryObject<ForgeSpawnEggItem> TURKEY_SPAWN_EGG = HELPER.createSpawnEggItem("turkey", AutumnityEntityTypes.TURKEY::get, 6765623, 5019859);

	public static void setupTabEditors() {
		CreativeModeTabContentsPopulator.mod(Autumnity.MOD_ID)
				.tab(FOOD_AND_DRINKS)
				.addItemsBefore(of(Items.RABBIT), AutumnityBlocks.TURKEY, AutumnityBlocks.COOKED_TURKEY, TURKEY_PIECE, COOKED_TURKEY_PIECE)
				.addItemsAfter(of(Items.SWEET_BERRIES), FOUL_BERRIES)
				.addItemsAfter(of(Items.BREAD), PUMPKIN_BREAD)
				.addItemsAfter(of(Items.RABBIT_STEW), FOUL_SOUP)
				.addItemsAfter(of(Items.HONEY_BOTTLE), SYRUP_BOTTLE)
				.tab(NATURAL_BLOCKS)
				.addItemsAfter(of(Items.SWEET_BERRIES), FOUL_BERRIES)
				.tab(INGREDIENTS)
				.addItemsAfter(of(Items.HONEYCOMB), SAP_BOTTLE)
				.addItemsAfter(of(Items.EGG), TURKEY_EGG)
				.addItemsAfter(of(Items.SCUTE), SNAIL_SHELL_PIECE)
				.addItemsAfter(of(Items.GLOBE_BANNER_PATTERN), MAPLE_LEAF_BANNER_PATTERN, SWIRL_BANNER_PATTERN)
				.tab(TOOLS_AND_UTILITIES)
				.addItemsBefore(of(Items.BAMBOO_RAFT), MAPLE_BOAT.getFirst(), MAPLE_BOAT.getSecond())
				.addItemsBefore(modLoaded(Items.BAMBOO_RAFT, "boatload"), MAPLE_FURNACE_BOAT, LARGE_MAPLE_BOAT)
				.tab(COMBAT)
				.addItemsAfter(of(Items.TURTLE_HELMET), SNAIL_SHELL_CHESTPLATE)
				.tab(SPAWN_EGGS)
				.addItemsAlphabetically(is(SpawnEggItem.class), SNAIL_SPAWN_EGG, TURKEY_SPAWN_EGG);

		CreativeModeTabContentsPopulator.mod("berry_good_1")
				.predicate(event -> event.getTabKey() == NATURAL_BLOCKS && AutumnityConfig.COMMON.foulBerriesRequirePips.get())
				.editor(event -> event.getEntries().remove(new ItemStack(FOUL_BERRIES.get())))
				.addItemsAfter(AutumnityBlocks.ofID(AutumnityConstants.SWEET_BERRY_PIPS, "berry_good"), FOUL_BERRY_PIPS);
	}

	public static class AutumnityFoods {
		public static final FoodProperties SYRUP_BOTTLE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.3F).build();
		public static final FoodProperties FOUL_BERRIES = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).effect(() -> new MobEffectInstance(AutumnityMobEffects.FOUL_TASTE.get(), 320, 0), 1.0F).build();
		public static final FoodProperties FOUL_SOUP = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.6F).effect(() -> new MobEffectInstance(AutumnityMobEffects.FOUL_TASTE.get(), 2400, 1), 1.0F).build();
		public static final FoodProperties PUMPKIN_BREAD = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.4F).build();
		public static final FoodProperties TURKEY = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).meat().effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.1F).fast().build();
		public static final FoodProperties COOKED_TURKEY = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.6F).meat().fast().build();
	}
}