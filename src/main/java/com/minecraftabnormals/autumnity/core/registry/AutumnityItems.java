package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.abnormals_core.common.items.AbnormalsBannerPatternItem;
import com.minecraftabnormals.abnormals_core.common.items.AbnormalsSpawnEggItem;
import com.minecraftabnormals.abnormals_core.core.util.registry.ItemSubRegistryHelper;
import com.minecraftabnormals.autumnity.common.item.SnailShellChestplateItem;
import com.minecraftabnormals.autumnity.common.item.SyrupBottleItem;
import com.minecraftabnormals.autumnity.common.item.TurkeyEggItem;
import com.minecraftabnormals.autumnity.core.Autumnity;
import com.minecraftabnormals.autumnity.core.other.AutumnityFoods;
import com.minecraftabnormals.autumnity.core.other.AutumnityTiers;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.SoupItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityItems {
	public static final ItemSubRegistryHelper HELPER = Autumnity.REGISTRY_HELPER.getItemSubHelper();

	public static final RegistryObject<Item> MAPLE_BOAT = HELPER.createBoatItem("maple", AutumnityBlocks.MAPLE_PLANKS);

	public static final RegistryObject<Item> SAP_BOTTLE = HELPER.createItem("sap_bottle", () -> new Item((new Item.Properties()).containerItem(Items.GLASS_BOTTLE).group(ItemGroup.MATERIALS)));
	public static final RegistryObject<Item> SYRUP_BOTTLE = HELPER.createItem("syrup_bottle", () -> new SyrupBottleItem((new Item.Properties()).containerItem(Items.GLASS_BOTTLE).maxStackSize(16).group(ItemGroup.FOOD).food(AutumnityFoods.SYRUP_BOTTLE)));
	public static final RegistryObject<Item> FOUL_BERRIES = HELPER.createItem("foul_berries", () -> new BlockNamedItem(AutumnityBlocks.FOUL_BERRY_BUSH.get(), (new Item.Properties()).group(ItemGroup.FOOD).food(AutumnityFoods.FOUL_BERRIES)));
	public static final RegistryObject<Item> FOUL_BERRY_PIPS = HELPER.createItem("foul_berry_pips", () -> new BlockNamedItem(AutumnityBlocks.FOUL_BERRY_BUSH_PIPS.get(), (new Item.Properties()).group(ModList.get().isLoaded("berry_good") ? ItemGroup.MISC : null)));
	public static final RegistryObject<Item> FOUL_SOUP = HELPER.createItem("foul_soup", () -> new SoupItem((new Item.Properties()).maxStackSize(1).group(ItemGroup.FOOD).food(AutumnityFoods.FOUL_SOUP)));
	public static final RegistryObject<Item> PUMPKIN_BREAD = HELPER.createItem("pumpkin_bread", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(AutumnityFoods.PUMPKIN_BREAD)));

	public static final RegistryObject<Item> SNAIL_SHELL_PIECE = HELPER.createItem("snail_shell_piece", () -> new Item((new Item.Properties()).group(ItemGroup.MATERIALS)));
	public static final RegistryObject<Item> SNAIL_SHELL_CHESTPLATE = HELPER.createItem("snail_shell_chestplate", () -> new SnailShellChestplateItem(AutumnityTiers.SNAIL_SHELL, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT)));

	public static final RegistryObject<Item> TURKEY_EGG = HELPER.createItem("turkey_egg", () -> new TurkeyEggItem(new Item.Properties().maxStackSize(16).group(ItemGroup.MISC)));
	public static final RegistryObject<Item> TURKEY_PIECE = HELPER.createItem("turkey_piece", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(AutumnityFoods.TURKEY)));
	public static final RegistryObject<Item> COOKED_TURKEY_PIECE = HELPER.createItem("cooked_turkey_piece", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(AutumnityFoods.COOKED_TURKEY)));

	public static final RegistryObject<Item> MAPLE_LEAF_BANNER_PATTERN = HELPER.createItem("maple_leaf_banner_pattern", () -> new AbnormalsBannerPatternItem(AutumnityBanners.MAPLE_LEAF, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));
	public static final RegistryObject<Item> SWIRL_BANNER_PATTERN = HELPER.createItem("swirl_banner_pattern", () -> new AbnormalsBannerPatternItem(AutumnityBanners.SWIRL, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));

	public static final RegistryObject<AbnormalsSpawnEggItem> SNAIL_SPAWN_EGG = HELPER.createSpawnEggItem("snail", AutumnityEntities.SNAIL::get, 7355937, 14727558);
	public static final RegistryObject<AbnormalsSpawnEggItem> TURKEY_SPAWN_EGG = HELPER.createSpawnEggItem("turkey", AutumnityEntities.TURKEY::get, 6765623, 5019859);
}