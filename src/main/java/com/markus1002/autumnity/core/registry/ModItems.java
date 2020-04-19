package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.entity.item.boat.ModBoatEntity;
import com.markus1002.autumnity.common.item.ModBoatItem;
import com.markus1002.autumnity.common.item.ModFoods;
import com.markus1002.autumnity.common.item.SnailShellChestplateItem;
import com.markus1002.autumnity.common.item.SyrupBottleItem;
import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems
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
			return Ingredient.fromItems(ModItems.SNAIL_SHELL_PIECE.get());
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
	};

	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Reference.MOD_ID);

	public static RegistryObject<Item> MAPLE_BOAT = ITEMS.register("maple_boat", () -> new ModBoatItem(ModBoatEntity.BoatType.MAPLE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)));
	public static RegistryObject<Item> SAP_BOTTLE = ITEMS.register("sap_bottle", () -> new Item((new Item.Properties()).containerItem(Items.GLASS_BOTTLE).group(ItemGroup.MATERIALS)));
	public static RegistryObject<Item> SYRUP_BOTTLE = ITEMS.register("syrup_bottle", () -> new SyrupBottleItem((new Item.Properties()).containerItem(Items.GLASS_BOTTLE).maxStackSize(16).group(ItemGroup.FOOD).food(ModFoods.SYRUP_BOTTLE)));
	public static RegistryObject<Item> FOUL_BERRIES = ITEMS.register("foul_berries", ModList.get().isLoaded("berry_good") ? () -> new Item((new Item.Properties()).group(ItemGroup.FOOD).food(ModFoods.FOUL_BERRIES)) : () -> new BlockNamedItem(ModBlocks.FOUL_BERRY_BUSH.get(), (new Item.Properties()).group(ItemGroup.FOOD).food(ModFoods.FOUL_BERRIES)));
	public static RegistryObject<Item> FOUL_BERRY_PIPS = ITEMS.register("foul_berry_pips", () -> new BlockNamedItem(ModBlocks.FOUL_BERRY_BUSH_PIPS.get(), (new Item.Properties()).group(ModList.get().isLoaded("berry_good") ? ItemGroup.MISC : null)));
	public static RegistryObject<Item> FOUL_BERRY_LEAF = ITEMS.register("foul_berry_leaf", () -> new Item((new Item.Properties()).group(ItemGroup.BREWING)));
	public static RegistryObject<Item> SNAIL_SPAWN_EGG = ITEMS.register("snail_spawn_egg", () -> new SpawnEggItem(ModEntities.SNAIL, 7355937, 14727558, (new Item.Properties()).group(ItemGroup.MISC)));
	public static RegistryObject<Item> SNAIL_SHELL_PIECE = ITEMS.register("snail_shell_piece", () -> new Item((new Item.Properties()).group(ItemGroup.MATERIALS)));
	public static RegistryObject<Item> SNAIL_SHELL_CHESTPLATE = ITEMS.register("snail_shell_chestplate", () -> new SnailShellChestplateItem(SNAIL_SHELL_MATERIAL, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT)));

}