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
	public boolean canGoOnHead;

	public FallingHeadBlockEntity(EntityType<? extends FallingHeadBlockEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	public FallingHeadBlockEntity(Level worldIn, double x, double y, double z, BlockState fallingBlockState, boolean drop) {
		this(AutumnityEntityTypes.FALLING_HEAD_BLOCK.get(), worldIn);
		this.blockState = fallingBlockState;
		this.blocksBuilding = true;
		this.setPos(x, y + (double) ((1.0F - this.getBbHeight()) / 2.0F), z);
		this.setDeltaMovement(Vec3.ZERO);
		this.xo = x;
		this.yo = y;
		this.zo = z;
		this.canGoOnHead = drop;
		this.setStartPos(this.blockPosition());
	}

	public FallingHeadBlockEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
		this(AutumnityEntityTypes.FALLING_HEAD_BLOCK.get(), world);
	}

	public static FallingHeadBlockEntity fall(Level level, BlockPos pos, BlockState state, boolean drop) {
		FallingHeadBlockEntity turkey = new FallingHeadBlockEntity(level, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(BlockStateProperties.WATERLOGGED, false) : state, drop);
		level.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
		level.addFreshEntity(turkey);
		return turkey;
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.isRemoved() && this.canGoOnHead) {
			for (Entity entity : this.level.getEntities(this, this.getBoundingBox(), (entity) ->
					(entity instanceof Player || entity instanceof Zombie || entity instanceof AbstractSkeleton || entity instanceof Piglin) && ((LivingEntity) entity).getItemBySlot(EquipmentSlot.HEAD).isEmpty())) {
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