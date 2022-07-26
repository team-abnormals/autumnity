package com.teamabnormals.autumnity.common.entity.item;

import com.teamabnormals.autumnity.core.registry.AutumnityEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class FallingHeadBlockEntity extends FallingBlockEntity implements IEntityAdditionalSpawnData {
	public FallingHeadBlockEntity(EntityType<? extends FallingHeadBlockEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public FallingHeadBlockEntity(Level worldIn, double x, double y, double z, BlockState fallingBlockState) {
		this(AutumnityEntityTypes.FALLING_HEAD_BLOCK.get(), worldIn);
		this.blockState = fallingBlockState;
		this.blocksBuilding = true;
		this.setPos(x, y + (double) ((1.0F - this.getBbHeight()) / 2.0F), z);
		this.setDeltaMovement(Vec3.ZERO);
		this.xo = x;
		this.yo = y;
		this.zo = z;
		this.setStartPos(this.blockPosition());
	}

	public FallingHeadBlockEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
		this(AutumnityEntityTypes.FALLING_HEAD_BLOCK.get(), world);
	}

	public static FallingHeadBlockEntity fall(Level p_201972_, BlockPos p_201973_, BlockState p_201974_) {
		FallingHeadBlockEntity fallingblockentity = new FallingHeadBlockEntity(p_201972_, (double)p_201973_.getX() + 0.5D, (double)p_201973_.getY(), (double)p_201973_.getZ() + 0.5D, p_201974_.hasProperty(BlockStateProperties.WATERLOGGED) ? p_201974_.setValue(BlockStateProperties.WATERLOGGED, false) : p_201974_);
		p_201972_.setBlock(p_201973_, p_201974_.getFluidState().createLegacyBlock(), 3);
		p_201972_.addFreshEntity(fallingblockentity);
		return fallingblockentity;
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.isRemoved()) {
			for (Entity entity : this.level.getEntities(this, this.getBoundingBox(), (p_234613_0_) ->
					(p_234613_0_ instanceof Player || p_234613_0_ instanceof Zombie || p_234613_0_ instanceof AbstractSkeleton || p_234613_0_ instanceof Piglin) && ((LivingEntity) p_234613_0_).getItemBySlot(EquipmentSlot.HEAD).isEmpty())) {
				double d0 = entity.getY() + entity.getBbHeight();
				if (this.yo >= d0 && this.getY() <= d0) {
					entity.setItemSlot(EquipmentSlot.HEAD, new ItemStack((this.getBlockState().getBlock().asItem())));
					this.discard();
					break;
				}
			}
		}
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		buffer.writeInt(Block.getId(this.blockState));
	}

	@Override
	public void readSpawnData(FriendlyByteBuf buffer) {
		this.blockState = Block.stateById(buffer.readInt());
	}
}