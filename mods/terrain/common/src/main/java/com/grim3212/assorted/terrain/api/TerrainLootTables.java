package com.grim3212.assorted.terrain.api;

import com.grim3212.assorted.terrain.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class TerrainLootTables {

    // One table per desert well depth tier. Deeper is a longer swim and a better chest.
    public static final ResourceKey<LootTable> CHESTS_DESERT_WELL_10 = create("chests/desert_well/level_10");
    public static final ResourceKey<LootTable> CHESTS_DESERT_WELL_15 = create("chests/desert_well/level_15");
    public static final ResourceKey<LootTable> CHESTS_DESERT_WELL_20 = create("chests/desert_well/level_20");
    public static final ResourceKey<LootTable> CHESTS_DESERT_WELL_25 = create("chests/desert_well/level_25");
    public static final ResourceKey<LootTable> CHESTS_DESERT_WELL_30 = create("chests/desert_well/level_30");

    private static ResourceKey<LootTable> create(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
}
