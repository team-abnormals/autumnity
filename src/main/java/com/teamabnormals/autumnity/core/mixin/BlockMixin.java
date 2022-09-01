package com.teamabnormals.autumnity.core.mixin;

import com.teamabnormals.autumnity.common.block.AbstractLargePumpkinSliceBlock;
import com.teamabnormals.autumnity.common.block.AutumnityJackOLanternBlock;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin extends BlockBehaviour implements ItemLike, IForgeBlock {

	public BlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "isExceptionForConnection", at = @At(value = "HEAD"), cancellable = true)
	private static void isExceptionForConnection(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.getBlock() instanceof AutumnityJackOLanternBlock || state.getBlock() instanceof AbstractLargePumpkinSliceBlock) {
			cir.setReturnValue(true);
		}
	}
}