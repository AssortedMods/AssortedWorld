package com.grim3212.assorted.portals.common.handlers;

import com.grim3212.assorted.lib.core.creative.CreativeTabItems;
import com.grim3212.assorted.lib.core.creative.SharedCreativeTabs;
import com.grim3212.assorted.portals.Family;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/** This part's share of the Assorted World tab, which every part asks for and the first to load registers. */
public class PortalsCreativeItems {

    public static final ResourceKey<CreativeModeTab> TAB = SharedCreativeTabs.tab(Identifier.fromNamespaceAndPath(Family.ID, "tab"), Family.ICONS);

    private static List<ItemStack> getCreativeItems() {
        CreativeTabItems items = new CreativeTabItems();

        items.add(PortalsBlocks.VOID_CRYSTAL_ORE.get());
        items.add(PortalsItems.VOID_CRYSTAL.get());
        items.add(PortalsBlocks.VOID_PORTAL_FRAME.get());
        items.add(PortalsItems.VOID_STRIKER.get());

        return items.getItems();
    }

    public static void init() {
        // After the ores and plants and before the runes, as the tab was when this was all one mod.
        SharedCreativeTabs.add(TAB, 300, PortalsCreativeItems::getCreativeItems);
    }
}
