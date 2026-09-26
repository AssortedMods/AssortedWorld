package com.grim3212.assorted.portals.common.handlers;

import com.grim3212.assorted.lib.events.LootTableModifyEvent;
import com.grim3212.assorted.portals.api.PortalsLootTables;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class LootTableHandlers {

    public static void init(LootTableModifyEvent event) {
        if (event.getId().equals(BuiltInLootTables.END_CITY_TREASURE.identifier())) {
            event.getContext().addPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(PortalsLootTables.INJECT_END_CITY_TREASURE)));
        }
    }
}
