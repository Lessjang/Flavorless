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

	static {
		VOID_BLOCK = Registry.register(Registry.BLOCK, id("void"), new Block(FabricBlockSettings.of(Material.AIR)
						.noCollision().dropsNothing().air()) {
			public BlockRenderType getRenderType(BlockState state) {
				return BlockRenderType.INVISIBLE;
			}

			public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
				return VoxelShapes.empty();
			}
		});
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
	}
}
