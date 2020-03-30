package com.markus1002.autumnity.common.entity.item.boat;

import com.markus1002.autumnity.core.registry.ModBlocks;
import com.markus1002.autumnity.core.registry.ModEntities;
import com.markus1002.autumnity.core.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;;

public class ModBoatEntity extends BoatEntity
{
	private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.createKey(ModBoatEntity.class, DataSerializers.VARINT);

	public ModBoatEntity(EntityType<? extends BoatEntity> entityType, World worldIn)
	{
		super(entityType, worldIn);
	}

	public ModBoatEntity(World worldIn, double x, double y, double z)
	{
		this(ModEntities.BOAT, worldIn);
		this.setPosition(x, y, z);
		this.setMotion(Vec3d.ZERO);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}
	
	public ModBoatEntity(FMLPlayMessages.SpawnEntity packet, World worldIn)
	{
		super(ModEntities.BOAT, worldIn);
	}
	
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(BOAT_TYPE, ModBoatEntity.BoatType.MAPLE.ordinal());
	}

	public void setModBoatType(ModBoatEntity.BoatType boatType)
	{
		this.dataManager.set(BOAT_TYPE, boatType.ordinal());
	}

	public ModBoatEntity.BoatType getModBoatType()
	{
		return ModBoatEntity.BoatType.byId(this.dataManager.get(BOAT_TYPE));
	}

	protected void writeAdditional(CompoundNBT compound)
	{
		compound.putString("Type", this.getModBoatType().getName());
	}

	protected void readAdditional(CompoundNBT compound)
	{
		if (compound.contains("Type", 8))
		{
			this.setModBoatType(ModBoatEntity.BoatType.getTypeFromString(compound.getString("Type")));
		}
	}

	protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos)
	{
		this.lastYd = this.getMotion().y;
		if (!this.isPassenger())
		{
			if (onGroundIn)
			{
				if (this.fallDistance > 3.0F)
				{
					if (this.status != BoatEntity.Status.ON_LAND)
					{
						this.fallDistance = 0.0F;
						return;
					}

					if (!this.world.isRemote && !this.removed)
					{
						this.remove();
						if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS))
						{
							this.dropBreakItems();
						}
					}
				}

				this.fallDistance = 0.0F;
			}
			else if (!this.world.getFluidState((new BlockPos(this)).down()).isTagged(FluidTags.WATER) && y < 0.0D)
			{
				this.fallDistance = (float)((double)this.fallDistance - y);
			}
		}
	}

	protected void dropBreakItems()
	{
		for(int i = 0; i < 3; ++i)
		{
			this.entityDropItem(this.getPlanks());
		}

		for(int j = 0; j < 2; ++j)
		{
			this.entityDropItem(Items.STICK);
		}
	}

	public Item getItemBoat()
	{
		switch(this.getModBoatType())
		{
		case MAPLE:
		default:
			return ModItems.MAPLE_BOAT.get();
		}
	}

	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected Block getPlanks()
	{
		switch(this.getModBoatType())
		{
		case MAPLE:
		default:
			return ModBlocks.MAPLE_PLANKS.get();
		}
	}

	public static enum BoatType
	{
		MAPLE("maple");

		private final String name;

		private BoatType(String nameIn) 
		{
			this.name = nameIn;
		}

		public String getName()
		{
			return this.name;
		}

		public String toString()
		{
			return this.name;
		}

		public static ModBoatEntity.BoatType byId(int id)
		{
			ModBoatEntity.BoatType[] aboatentity$type = values();
			if (id < 0 || id >= aboatentity$type.length)
			{
				id = 0;
			}

			return aboatentity$type[id];
		}

		public static ModBoatEntity.BoatType getTypeFromString(String nameIn)
		{
			ModBoatEntity.BoatType[] aboatentity$type = values();

			for(int i = 0; i < aboatentity$type.length; ++i)
			{
				if (aboatentity$type[i].getName().equals(nameIn))
				{
					return aboatentity$type[i];
				}
			}

			return aboatentity$type[0];
		}
	}
}