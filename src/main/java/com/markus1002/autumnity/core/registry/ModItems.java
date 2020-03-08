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

	public static final Item MAPLE_BOAT = new ModBoatItem(ModBoatEntity.BoatType.MAPLE, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION));
	public static final Item SAP_BOTTLE = new Item((new Item.Properties()).containerItem(Items.GLASS_BOTTLE).group(ItemGroup.MATERIALS));
	public static final Item SYRUP_BOTTLE = new SyrupBottleItem((new Item.Properties()).containerItem(Items.GLASS_BOTTLE).maxStackSize(16).group(ItemGroup.FOOD).food(ModFoods.SYRUP_BOTTLE));
	public static final Item SNAIL_SPAWN_EGG = new SpawnEggItem(ModEntities.SNAIL, 7355937, 14727558, (new Item.Properties()).group(ItemGroup.MISC));
	public static final Item SNAIL_SHELL_PIECE = new Item((new Item.Properties()).group(ItemGroup.MATERIALS));
	public static final Item SNAIL_SHELL_CHESTPLATE = new SnailShellChestplateItem(SNAIL_SHELL_MATERIAL, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT));

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		registerItem(MAPLE_BOAT, "maple_boat");
		registerItem(SAP_BOTTLE, "sap_bottle");
		registerItem(SYRUP_BOTTLE, "syrup_bottle");
		registerItem(SNAIL_SPAWN_EGG, "snail_spawn_egg");
		registerItem(SNAIL_SHELL_PIECE, "snail_shell_piece");
		registerItem(SNAIL_SHELL_CHESTPLATE, "snail_shell_chestplate");
	}

	private static void registerItem(Item item, String name)
	{
		item.setRegistryName(Reference.location(name));
		ForgeRegistries.ITEMS.register(item);
	}
}