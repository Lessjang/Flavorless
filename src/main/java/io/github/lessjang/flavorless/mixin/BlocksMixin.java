package io.github.lessjang.flavorless.mixin;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static io.github.lessjang.flavorless.Flavorless.LOGGER;
import static io.github.lessjang.flavorless.Flavorless.VOID_BLOCK;

@Mixin({Blocks.class/*, SurfaceBuilder.class, ConfiguredSurfaceBuilders.class/*, CarverDebugConfig.class, CarverConfig.class, CaveCarverConfig.class*/})
public class BlocksMixin {
	/**
	 * @reason preventing errors when removing vanilla content
	 */
	@Inject(method = "<clinit>", at = @At("HEAD"), cancellable=true)
	private static void mixin_clinit(CallbackInfo info) {
		//Block unvoidBlock = new Block(FabricBlockSettings.of(Material.STONE).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning((state, view, pos, entType)->false));

		Field[] fields = Blocks.class.getDeclaredFields();
		final List<String> reset = new ArrayList<>(); // Fields successfully reset
		for (Field f : fields) {
			//match "static Block" fields
			if (!Modifier.isStatic(f.getModifiers())) continue;
			if (!f.getType().equals(Block.class)) continue;

			final String name = f.getName();

			try {
				f.set(null, VOID_BLOCK);
			} catch (Throwable ignored) {
				LOGGER.error("Couldn't cancel field: " + name);
				continue;
			}

			reset.add(name);
		}

		LOGGER.warn("Reset these " + reset.size() + " fields in Blocks.class: " + reset);
		reset.clear(); // Remove from memory
		
		info.cancel();
	}
}
