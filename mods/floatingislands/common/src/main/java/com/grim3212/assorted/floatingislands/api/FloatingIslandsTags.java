package com.grim3212.assorted.floatingislands.api;

import com.grim3212.assorted.floatingislands.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class FloatingIslandsTags {

    public static class Biomes {

        public static final TagKey<Biome> HAS_FLOATING_ISLAND = create("has_feature/floating_island");

        /** Where one kind of floating island hangs; see {@code FloatingIslandTypes}. */
        public static TagKey<Biome> floatingIsland(String type) {
            return create("floating_island/" + type);
        }

        private static TagKey<Biome> create(String n) {
            return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(Constants.MOD_ID, n));
        }
    }
}
