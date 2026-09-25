package com.grim3212.assorted.world.common.handlers;

import com.grim3212.assorted.lib.events.LootTableModifyEvent;
import com.grim3212.assorted.world.api.WorldLootTables;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class LootTableHandlers {

    public static void init(LootTableModifyEvent event) {
        if (event.getId().equals(BuiltInLootTables.END_CITY_TREASURE.identifier())) {
            event.getContext().addPool(LootPool.lootPool().add(NestedLootTable.lootTableReference(WorldLootTables.INJECT_END_CITY_TREASURE)));
        }
    }
}
