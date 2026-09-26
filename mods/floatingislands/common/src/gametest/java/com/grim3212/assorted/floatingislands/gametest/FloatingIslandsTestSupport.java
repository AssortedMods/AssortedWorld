package com.grim3212.assorted.floatingislands.gametest;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/** Helpers shared by Assorted Floating Islands' gametest classes, which import them statically. */
final class FloatingIslandsTestSupport {

    private FloatingIslandsTestSupport() {
    }

    static boolean hasFeature(Biome biome, Identifier id) {
        for (HolderSet<PlacedFeature> step : biome.getGenerationSettings().features()) {
            for (Holder<PlacedFeature> feature : step) {
                if (feature.unwrapKey().filter(key -> key.identifier().equals(id)).isPresent()) {
                    return true;
                }
            }
        }
        return false;
    }
}
