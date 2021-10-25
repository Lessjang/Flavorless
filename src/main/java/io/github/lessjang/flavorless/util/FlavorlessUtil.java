package io.github.lessjang.flavorless.util;

import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlavorlessUtil {
	public static final Logger LOGGER = LogManager.getLogger("Flavorless");
	public static final String MODID = "flavorless";

	/**
	 * @return an identifier with this mod's namespace.
	 */
	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}
