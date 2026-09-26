package com.grim3212.assorted.portals.data;

import com.grim3212.assorted.portals.api.PortalsLootTables;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class PortalsChestLoot implements LootTableSubProvider {

    public PortalsChestLoot(HolderLookup.Provider registries) {
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        // One end city chest in four, the ship's included, holds 1 to 3 void crystals.
        output.accept(PortalsLootTables.INJECT_END_CITY_TREASURE, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(PortalsItems.VOID_CRYSTAL.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                .add(EmptyLootItem.emptyItem().setWeight(3))));
    }
}
