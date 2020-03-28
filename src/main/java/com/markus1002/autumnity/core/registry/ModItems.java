package com.markus1002.autumnity.core.registry;

import com.markus1002.autumnity.common.entity.item.boat.ModBoatEntity;
import com.markus1002.autumnity.common.item.ModBoatItem;
import com.markus1002.autumnity.common.item.ModFoods;
import com.markus1002.autumnity.common.item.SnailShellChestplateItem;
import com.markus1002.autumnity.common.item.SyrupBottleItem;
import com.markus1002.autumnity.core.util.Reference;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
			return Ingredient.fromItems(ModItems.SNAIL_SHELL_PIECE);
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
	
	public static Item MAPLE_BOAT;
	public static Item SAP_BOTTLE;
	public static Item SYRUP_BOTTLE;
	public static Item SNAIL_SPAWN_EGG;
	public static Item SNAIL_SHELL_PIECE;
	public static Item SNAIL_SHELL_CHESTPLATE;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		MAPLE_BOAT = registerItem(new ModBoatItem(ModBoatEntity.BoatType.MAPLE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION)), "maple_boat");
		SAP_BOTTLE = registerItem(new Item((new Item.Properties()).containerItem(Items.GLASS_BOTTLE).group(ItemGroup.MATERIALS)), "sap_bottle");
		SYRUP_BOTTLE = registerItem(new SyrupBottleItem((new Item.Properties()).containerItem(Items.GLASS_BOTTLE).maxStackSize(16).group(ItemGroup.FOOD).food(ModFoods.SYRUP_BOTTLE)), "syrup_bottle");
		SNAIL_SPAWN_EGG = registerItem(new SpawnEggItem(ModEntities.SNAIL, 7355937, 14727558, (new Item.Properties()).group(ItemGroup.MISC)), "snail_spawn_egg");
		SNAIL_SHELL_PIECE = registerItem(new Item((new Item.Properties()).group(ItemGroup.MATERIALS)), "snail_shell_piece");
		SNAIL_SHELL_CHESTPLATE = registerItem(new SnailShellChestplateItem(SNAIL_SHELL_MATERIAL, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT)), "snail_shell_chestplate");
	}

	private static Item registerItem(Item item, String name)
	{
		item.setRegistryName(Reference.location(name));
		ForgeRegistries.ITEMS.register(item);
		return item;
	}
}