package io.github.lessjang.flavorless.client.mixin;

import io.github.lessjang.flavorless.util.FlavorlessUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
//@Environment(EnvType.CLIENT)
public class TitleScreenMixin {
	@Inject(method = "init", at = @At("HEAD"))
	private void onInit(CallbackInfo ci) {
		FlavorlessUtil.LOGGER.info("Hello title screen!");
	}
}
