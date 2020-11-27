package com.minecraftabnormals.autumnity.core.registry;

import com.minecraftabnormals.autumnity.common.item.SnailShellChestplateItem;
import com.minecraftabnormals.autumnity.common.item.SyrupBottleItem;
import com.minecraftabnormals.autumnity.common.item.TurkeyEggItem;
import com.minecraftabnormals.autumnity.core.Autumnity;
import com.minecraftabnormals.autumnity.core.other.AutumnityFoods;
import com.teamabnormals.abnormals_core.common.items.AbnormalsBannerPatternItem;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AutumnityItems
{
	public static final IArmorMaterial SNAIL_SHELL_MATERIAL = new IArmorMaterial()
	{
		@Override
		public int getDurability(EquipmentSlotType slotIn)
		{
			return 375;
		}

		@Override
		public int getDamageReductionAmount(EquipmentSlotType slotIn)
		{
			return 5;
		}

		@Override
		public int getEnchantability()
		{
			return 9;
		}

		@Override
		public SoundEvent getSoundEvent()
		{
			return SoundEvents.ITEM_ARMOR_EQUIP_TURTLE;
		}

		@Override
		public Ingredient getRepairMaterial()
		{
			return Ingredient.fromItems(AutumnityItems.SNAIL_SHELL_PIECE.get());
		}

		@Override
		public String getName()
		{
			return "snail_shell";
		}

		@Override
		public float getToughness()
		{
			return 0.0F;
		}
		
		@Override
		public float getKnockbackResistance()
		{
			return 0.0F;
		}
	};

	public static final RegistryHelper HELPER = Autumnity.REGISTRY_HELPER;
	public static final RegistryHelper REPLACER = Autumnity.REGISTRY_REPLACER;

	public static final RegistryObject<Item> MAPLE_BOAT = HELPER.createBoatItem("maple", AutumnityBlocks.MAPLE_PLANKS);
	
	public static final RegistryObject<Item> SAP_BOTTLE = HELPER.createItem("sap_bottle", () -> new Item((new Item.Properties()).containerItem(Items.GLASS_BOTTLE).group(ItemGroup.MATERIALS)));
	public static final RegistryObject<Item> SYRUP_BOTTLE = HELPER.createItem("syrup_bottle", () -> new SyrupBottleItem((new Item.Properties()).containerItem(Items.GLASS_BOTTLE).maxStackSize(16).group(ItemGroup.FOOD).food(AutumnityFoods.SYRUP_BOTTLE)));
	public static final RegistryObject<Item> FOUL_BERRIES = HELPER.createItem("foul_berries", () -> new BlockNamedItem(AutumnityBlocks.FOUL_BERRY_BUSH.get(), (new Item.Properties()).group(ItemGroup.FOOD).food(AutumnityFoods.FOUL_BERRIES)));
	public static final RegistryObject<Item> FOUL_BERRY_PIPS = HELPER.createItem("foul_berry_pips", () -> new BlockNamedItem(AutumnityBlocks.FOUL_BERRY_BUSH_PIPS.get(), (new Item.Properties()).group(ModList.get().isLoaded("berry_good") ? ItemGroup.MISC : null)));
	public static final RegistryObject<Item> PUMPKIN_BREAD = HELPER.createItem("pumpkin_bread", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(AutumnityFoods.PUMPKIN_BREAD)));
	
	public static final RegistryObject<Item> SNAIL_SPAWN_EGG = HELPER.createSpawnEggItem("snail", AutumnityEntities.SNAIL::get, 7355937, 14727558);
	public static final RegistryObject<Item> SNAIL_SHELL_PIECE = HELPER.createItem("snail_shell_piece", () -> new Item((new Item.Properties()).group(ItemGroup.MATERIALS)));
	public static final RegistryObject<Item> SNAIL_SHELL_CHESTPLATE = HELPER.createItem("snail_shell_chestplate", () -> new SnailShellChestplateItem(SNAIL_SHELL_MATERIAL, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT)));
	
	public static final RegistryObject<Item> TURKEY_SPAWN_EGG = HELPER.createSpawnEggItem("turkey", AutumnityEntities.TURKEY::get, 6765623, 5019859);
	public static final RegistryObject<Item> TURKEY_EGG = HELPER.createItem("turkey_egg", () -> new TurkeyEggItem(new Item.Properties().maxStackSize(16).group(ItemGroup.MISC)));
	public static final RegistryObject<Item> TURKEY_LEG = HELPER.createItem("turkey_leg", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(AutumnityFoods.TURKEY)));
	public static final RegistryObject<Item> COOKED_TURKEY_LEG = HELPER.createItem("cooked_turkey_leg", () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(AutumnityFoods.COOKED_TURKEY)));
	
    public static final RegistryObject<Item> MAPLE_LEAF_BANNER_PATTERN = HELPER.createItem("maple_leaf_banner_pattern", () -> new AbnormalsBannerPatternItem(AutumnityBanners.MAPLE_LEAF, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));
    public static final RegistryObject<Item> SWIRL_BANNER_PATTERN = HELPER.createItem("swirl_banner_pattern", () -> new AbnormalsBannerPatternItem(AutumnityBanners.SWIRL, new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));
}