package com.minecraftabnormals.autumnity.core.mixin;

import com.minecraftabnormals.autumnity.common.block.AbstractLargePumpkinSliceBlock;
import com.minecraftabnormals.autumnity.common.block.AutumnityJackOLanternBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements IItemProvider, IForgeBlock {
	public BlockMixin(Properties properties) {
		super(properties);
	}

	@Inject(method = "isExceptionForConnection", at = @At(value = "HEAD"), cancellable = true)
	private static void isExceptionForConnection(Block blockIn, CallbackInfoReturnable<Boolean> info) {
		if (blockIn instanceof AutumnityJackOLanternBlock || blockIn instanceof AbstractLargePumpkinSliceBlock) {
			info.setReturnValue(true);
		}
	}
}