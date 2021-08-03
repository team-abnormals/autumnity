package com.minecraftabnormals.autumnity.common.entity.item;

import com.minecraftabnormals.autumnity.core.registry.AutumnityEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class FallingHeadBlockEntity extends FallingBlockEntity implements IEntityAdditionalSpawnData {
	public FallingHeadBlockEntity(EntityType<? extends FallingHeadBlockEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public FallingHeadBlockEntity(World worldIn, double x, double y, double z, BlockState fallingBlockState) {
		this(AutumnityEntities.FALLING_HEAD_BLOCK.get(), worldIn);
		this.blockState = fallingBlockState;
		this.blocksBuilding = true;
		this.setPos(x, y + (double) ((1.0F - this.getBbHeight()) / 2.0F), z);
		this.setDeltaMovement(Vector3d.ZERO);
		this.xo = x;
		this.yo = y;
		this.zo = z;
		this.setStartPos(this.blockPosition());
	}

	public FallingHeadBlockEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
		this(AutumnityEntities.FALLING_HEAD_BLOCK.get(), world);
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.removed) {
			for (Entity entity : this.level.getEntities(this, this.getBoundingBox(), (p_234613_0_) ->
			{
				return (p_234613_0_ instanceof PlayerEntity || p_234613_0_ instanceof ZombieEntity || p_234613_0_ instanceof AbstractSkeletonEntity || p_234613_0_ instanceof PiglinEntity) && ((LivingEntity) p_234613_0_).getItemBySlot(EquipmentSlotType.HEAD).isEmpty();
			})) {
				double d0 = entity.getY() + entity.getBbHeight();
				if (this.yo >= d0 && this.getY() <= d0) {
					entity.setItemSlot(EquipmentSlotType.HEAD, new ItemStack((this.getBlockState().getBlock().asItem())));
					this.remove();
					break;
				}
			}
		}
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeInt(Block.getId(this.blockState));
	}

	@Override
	public void readSpawnData(PacketBuffer buffer) {
		this.blockState = Block.stateById(buffer.readInt());
	}
}