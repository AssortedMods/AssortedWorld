package com.grim3212.assorted.terrain.api;

import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.terrain.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class TerrainTags {

    public static class Blocks {
        public static final TagKey<Block> ORES_RANDOMITE = commonTag("ores/randomite");

        private static TagKey<Block> commonTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(LibCommonTags.COMMON_NAMESPACE, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> ORES_RANDOMITE = commonTag("ores/randomite");

        private static TagKey<Item> commonTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(LibCommonTags.COMMON_NAMESPACE, name));
        }
    }

    public static class Biomes {

        public static final TagKey<Biome> HAS_DESERT_WELL = create("has_feature/desert_well");
        public static final TagKey<Biome> HAS_CROP_FIELD = create("has_feature/crop_field");
        public static final TagKey<Biome> HAS_SAPLINGS = create("has_feature/saplings");
        public static final TagKey<Biome> HAS_TREE_STUMPS = create("has_feature/tree_stumps");
        public static final TagKey<Biome> HAS_CACTUS_FIELD = create("has_feature/cactus_field");
        public static final TagKey<Biome> HAS_SAND_PIT = create("has_feature/sand_pit");
        public static final TagKey<Biome> HAS_MELONS = create("has_feature/melons");

        /** Where one wood grows, for stray saplings and stumps; see {@code BiomeWoods}. */
        public static TagKey<Biome> woods(String wood) {
            return create("woods/" + wood);
        }

        private static TagKey<Biome> create(String n) {
            return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(Constants.MOD_ID, n));
        }
    }
}
