package io.github.lessjang.flavorless.mixin;

import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.lessjang.flavorless.Flavorless.LOGGER;
import static io.github.lessjang.flavorless.Flavorless.NO_OP_SB;

@Mixin(SurfaceBuilder.class)
public class SurfaceBuilderMixin {
	/**
	 * @reason preventing errors when removing vanilla content
	 */
	@Inject(method = "<clinit>", at = @At("HEAD"), cancellable=true)
	private static void mixin_clinit(CallbackInfo info) {
		List<Field> fields = Arrays.stream(SurfaceBuilder.class.getDeclaredFields())
				.filter(f -> Modifier.isStatic(f.getModifiers()) && f.getType().equals(SurfaceBuilder.class)).toList();
		final List<String> reset = new ArrayList<>(); // Fields successfully reset
		for (Field f : fields) {
			final String name = f.getName();

			try {
				f.set(null, NO_OP_SB);
			} catch (Throwable ignored) {
				LOGGER.error("Couldn't cancel field: " + name);
				continue;
			}

			reset.add(name);
		}

		LOGGER.warn("Reset these " + reset.size() + " fields in SurfaceBuilder.class: " + reset);
		reset.clear(); // Remove from memory
		info.cancel();
	}
}
