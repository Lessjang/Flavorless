package io.github.lessjang.flavorless.mixin;

import io.github.lessjang.flavorless.Flavorless;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Registry.class)
public class RegistryMixin {
	/**
	 * @reason removing vanilla content
	 */
	@Inject(method = "register(Lnet/minecraft/util/registry/Registry;Lnet/minecraft/util/Identifier;Ljava/lang/Object;)Ljava/lang/Object;",
			at = @At("HEAD"), cancellable = true)
	private static <V> void onRegister(Registry<V> registry, Identifier id, V entry, CallbackInfoReturnable<V> cir) {
		String namespace = id.getNamespace();
		String path = id.getPath();

		if (namespace.isEmpty() || namespace.equals("minecraft")) {
			Flavorless.CANCELLED_ENTRIES.add(registry.getKey().getValue().getPath() + " : " + path);
			cir.cancel();
		}
	}
}
