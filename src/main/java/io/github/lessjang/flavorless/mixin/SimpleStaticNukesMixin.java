package io.github.lessjang.flavorless.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

@Mixin({ ConfiguredSurfaceBuilders.class })
public class SimpleStaticNukesMixin {
	@Inject(method = "<clinit>", at = @At("HEAD"), cancellable=true)
	private static void mixin_clinit(CallbackInfo info) {
		info.cancel();
	}
}
