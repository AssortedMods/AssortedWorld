package com.grim3212.assorted.structures.gametest;

import net.minecraft.gametest.framework.GameTestHelper;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Automated in-world checks for Assorted Structures. A test volume cannot run worldgen, so these cover the blocks a structure
 * leaves, the loot its chests draw and the position-seeded helpers. Each name needs a {@code test_instance/<name>.json}.
 */
public final class StructuresGameTests {

    private StructuresGameTests() {
    }

    /** Every test in this mod, named once, so both loaders register the same set. */
    public static void forEach(BiConsumer<String, Consumer<GameTestHelper>> out) {
        AssetTests.register(out);
        AliasTests.register(out);
        CrossLoaderDataTests.register(out);
        RuneTests.register(out);
        WorldgenTests.register(out);
        PyramidTests.register(out);
        WaterDomeTests.register(out);
        FamilyTests.register(out);
    }
}
