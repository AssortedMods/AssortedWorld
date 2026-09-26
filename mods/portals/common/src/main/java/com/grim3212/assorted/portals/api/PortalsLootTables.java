package com.grim3212.assorted.portals.api;

import com.grim3212.assorted.portals.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class PortalsLootTables {

    // Pooled into vanilla's end city chests by LootTableHandlers rather than overriding them.
    public static final ResourceKey<LootTable> INJECT_END_CITY_TREASURE = create("chests/inject/end_city_treasure");

    private static ResourceKey<LootTable> create(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
}
