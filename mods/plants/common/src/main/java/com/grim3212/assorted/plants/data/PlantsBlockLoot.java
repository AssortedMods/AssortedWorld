package com.grim3212.assorted.plants.data;

import com.grim3212.assorted.lib.data.LibBlockLootProvider;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PlantsBlockLoot extends LibBlockLootProvider {

    public PlantsBlockLoot(LootTableSubProvider.Context context) {
        super(context, () -> PlantsBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList()));
    }

    @Override
    public void generate() {
        this.dropSelf(PlantsBlocks.GUNPOWDER_REED.get());

        // A seed breaks back into the dust it was made from, 1 or 2 of it, like GrimPack's did.
        this.add(PlantsBlocks.GLOWSTONE_SEEDS.get(), block -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1))
                .add(LootItem.lootTableItem(Items.GLOWSTONE_DUST).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                .when(ExplosionCondition.survivesExplosion())));
    }
}
