package com.grim3212.assorted.portals.data;

import com.grim3212.assorted.portals.api.PortalsLootTables;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class PortalsChestLoot implements LootTableSubProvider {

    private final LootTableSubProvider.Context output;

    public PortalsChestLoot(LootTableSubProvider.Context output) {
        this.output = output;
    }

    @Override
    public void run() {
        // One end city chest in four, the ship's included, holds 1 to 3 void crystals.
        output.accept(PortalsLootTables.INJECT_END_CITY_TREASURE, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                .add(LootItem.lootTableItem(PortalsItems.VOID_CRYSTAL.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                .add(EmptyLootItem.emptyItem().setWeight(3))));
    }
}
