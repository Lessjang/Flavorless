package io.github.lessjang.flavorless.mixin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.CarverDebugConfig;
import net.minecraft.world.gen.carver.CaveCarverConfig;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

@Mixin({Blocks.class })
public class BlocksMixin {
	
	@Inject(method = "<clinit>", at = @At("HEAD"), cancellable=true)
	private static void mixin_clinit(CallbackInfo info) {
		Block voidBlock = new Block(FabricBlockSettings.of(Material.AIR).noCollision().dropsNothing().air()) {
			public BlockRenderType getRenderType(BlockState state) {
				return BlockRenderType.INVISIBLE;
			}

			public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
				return VoxelShapes.empty();
			}
		};
		
		Block unvoidBlock = new Block(FabricBlockSettings.of(Material.STONE).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning((state, view, pos, entType)->false));
		
		Field[] fields = Blocks.class.getDeclaredFields();
		for(int i=0; i<fields.length; i++) {
			Field f = fields[i];
			
			//match "static Block" fields
			if (!Modifier.isStatic(f.getModifiers())) continue;
			if (!f.getType().equals(Block.class)) continue;
			
			try {
				f.set(null, (i==0) ? voidBlock : unvoidBlock);
				//System.out.println("SET "+f.getName());
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
		}
		
		info.cancel();
	}
}
