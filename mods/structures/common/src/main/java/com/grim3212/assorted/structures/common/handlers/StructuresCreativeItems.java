package com.grim3212.assorted.structures.common.handlers;

import com.grim3212.assorted.lib.core.creative.SharedCreativeTabs;
import com.grim3212.assorted.structures.Family;
import com.grim3212.assorted.structures.common.block.StructuresBlocks;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

/** This part's share of the Assorted World tab, which every part asks for and the first to load registers. */
public class StructuresCreativeItems {

    public static final ResourceKey<CreativeModeTab> TAB = SharedCreativeTabs.tab(Identifier.fromNamespaceAndPath(Family.ID, "tab"), Family.ICONS);

    public static void init() {
        // The runes came last in the tab when this was all one mod.
        SharedCreativeTabs.add(TAB, 400, StructuresCreativeItems::runes);
    }

    private static List<ItemStack> runes() {
        return Arrays.stream(StructuresBlocks.runeBlocks()).map(ItemStack::new).toList();
    }
}
