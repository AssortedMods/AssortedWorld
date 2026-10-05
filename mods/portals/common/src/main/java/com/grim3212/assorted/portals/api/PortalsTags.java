package com.grim3212.assorted.portals.api;

import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.portals.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class PortalsTags {

    public static class Blocks {
        public static final TagKey<Block> ORES_VOID_CRYSTAL = commonTag("ores/void_crystal");
        public static final TagKey<Block> INFINIBURN_ENDER_FLAMES = portalsTag("infiniburn_ender_flames");

        private static TagKey<Block> portalsTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        }

        private static TagKey<Block> commonTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(LibCommonTags.COMMON_NAMESPACE, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> ORES_VOID_CRYSTAL = commonTag("ores/void_crystal");

        private static TagKey<Item> commonTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(LibCommonTags.COMMON_NAMESPACE, name));
        }
    }

    public static class Biomes {

        public static final TagKey<Biome> HAS_VOID_CRYSTAL_ORE = create("has_feature/void_crystal_ore");

        private static TagKey<Biome> create(String n) {
            return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(Constants.MOD_ID, n));
        }
    }
}
