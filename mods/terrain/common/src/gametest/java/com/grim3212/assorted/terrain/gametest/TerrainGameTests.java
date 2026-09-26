package com.grim3212.assorted.terrain.gametest;

import net.minecraft.gametest.framework.GameTestHelper;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Automated in-world checks for Assorted Terrain. The tests live in small {@code <Feature>Tests} classes;
 * this only lists them. Each name needs a matching {@code data/assortedterrain/test_instance/<name>.json}.
 */
public final class TerrainGameTests {

    private TerrainGameTests() {
    }

    /** Every test in this mod, named once, so both loaders register the same set. */
    public static void forEach(BiConsumer<String, Consumer<GameTestHelper>> out) {
        AssetTests.register(out);
        AliasTests.register(out);
        CrossLoaderDataTests.register(out);
        OreTests.register(out);
        WorldgenTests.register(out);
        DesertWellTests.register(out);
        BiomeWoodsTests.register(out);
    }
}
