package com.grim3212.assorted.floatingislands.gametest;

import net.minecraft.gametest.framework.GameTestHelper;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Automated in-world checks for Assorted Floating Islands. A test volume cannot run worldgen, so these
 * cover the island shapes and kinds, and that the feature is registered and attached to biomes.
 */
public final class FloatingIslandsGameTests {

    private FloatingIslandsGameTests() {
    }

    /** Every test in this mod, named once, so both loaders register the same set. */
    public static void forEach(BiConsumer<String, Consumer<GameTestHelper>> out) {
        WorldgenTests.register(out);
        FloatingIslandTests.register(out);
        CrossLoaderDataTests.register(out);
    }
}
