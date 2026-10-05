package com.grim3212.assorted.structures.data;

import com.grim3212.assorted.lib.data.LibBlockLootProvider;
import com.grim3212.assorted.structures.common.block.StructuresBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class StructuresBlockLoot extends LibBlockLootProvider {

    // Loot sub providers are handed the registry lookup at construction now.
    public StructuresBlockLoot(HolderLookup.Provider registries) {
        super(registries, () -> StructuresBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList()));
    }

    @Override
    public void generate() {
        for (Block rune : StructuresBlocks.runeBlocks()) {
            this.dropSelf(rune);
        }
    }
}
