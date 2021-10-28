package io.github.lessjang.flavorless;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.surfacebuilder.NopeSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class Flavorless implements ModInitializer {
	public static final List<String> CANCELLED_ENTRIES = new ArrayList<>();
	public static final Logger LOGGER = LogManager.getLogger("Flavorless");
	public static final String MODID = "flavorless";

	public static final Block VOID_BLOCK;
	public static final Block UNVOID_BLOCK;
	public static final SurfaceBuilder<TernarySurfaceConfig> NO_OP_SB;

	static {
		VOID_BLOCK = new Block(FabricBlockSettings.of(Material.AIR).noCollision().dropsNothing().air()) {
			public BlockRenderType getRenderType(BlockState state) {
				return BlockRenderType.INVISIBLE;
			}

			public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
				return VoxelShapes.empty();
			}
		};
		UNVOID_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).strength(-1.0F, 3600000.0F)
				.dropsNothing().allowsSpawning((state, view, pos, entType) -> false));
		NO_OP_SB = new NopeSurfaceBuilder(TernarySurfaceConfig.CODEC);
	}

	/**
	 * @return an identifier with this mod's namespace.
	 */
	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}

	@Override
	public void onInitialize() {
		LOGGER.warn("Cancelled these registry entries: " + CANCELLED_ENTRIES);
		CANCELLED_ENTRIES.clear(); // Remove from memory

		Registry.register(Registry.BLOCK, id("void"), VOID_BLOCK);
		Registry.register(Registry.BLOCK, id("unvoid"), UNVOID_BLOCK);
		Registry.register(Registry.SURFACE_BUILDER, id("no_op"), NO_OP_SB);
	}
}
