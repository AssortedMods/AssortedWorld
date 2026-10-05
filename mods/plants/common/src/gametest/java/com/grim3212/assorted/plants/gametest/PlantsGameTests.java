package com.grim3212.assorted.plants.gametest;

import net.minecraft.gametest.framework.GameTestHelper;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Automated in-world checks for Assorted Plants. The tests live in small {@code <Feature>Tests} classes;
 * this only lists them. Each name needs a matching {@code data/assortedplants/test_instance/<name>.json}.
 */
public final class PlantsGameTests {

    private PlantsGameTests() {
    }

    /** Every test in this mod, named once, so both loaders register the same set. */
    public static void forEach(BiConsumer<String, Consumer<GameTestHelper>> out) {
        AssetTests.register(out);
        AliasTests.register(out);
        CrossLoaderDataTests.register(out);
        WorldgenTests.register(out);
        GunpowderReedTests.register(out);
        GlowstoneSeedTests.register(out);
        FamilyTests.register(out);
    }
}
