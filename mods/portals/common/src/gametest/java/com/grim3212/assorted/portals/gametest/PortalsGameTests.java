package com.grim3212.assorted.portals.gametest;

import net.minecraft.gametest.framework.GameTestHelper;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Automated in-world checks for Assorted Portals. The tests live in small {@code <Feature>Tests} classes;
 * this only lists them. Each name needs a matching {@code data/assortedportals/test_instance/<name>.json}.
 */
public final class PortalsGameTests {

    private PortalsGameTests() {
    }

    /** Every test in this mod, named once, so both loaders register the same set. */
    public static void forEach(BiConsumer<String, Consumer<GameTestHelper>> out) {
        AssetTests.register(out);
        AliasTests.register(out);
        CrossLoaderDataTests.register(out);
        WorldgenTests.register(out);
        EndPortalTests.register(out);
    }
}
