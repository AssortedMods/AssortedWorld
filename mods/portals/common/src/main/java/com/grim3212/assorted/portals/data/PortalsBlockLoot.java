package com.grim3212.assorted.portals.data;

import com.grim3212.assorted.lib.data.LibBlockLootProvider;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import net.minecraft.core.HolderLookup;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PortalsBlockLoot extends LibBlockLootProvider {

    // Loot sub providers are handed the registry lookup at construction now.
    public PortalsBlockLoot(HolderLookup.Provider registries) {
        super(registries, () -> PortalsBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList()));
    }

    @Override
    public void generate() {
        this.dropSelf(PortalsBlocks.VOID_PORTAL_FRAME.get());
        this.add(PortalsBlocks.VOID_CRYSTAL_ORE.get(), block -> this.createOreDrop(block, PortalsItems.VOID_CRYSTAL.get()));
    }
}
