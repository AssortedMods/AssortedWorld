package com.grim3212.assorted.terrain.common.handlers;

import com.grim3212.assorted.lib.core.creative.SharedCreativeTabs;
import com.grim3212.assorted.terrain.Family;
import com.grim3212.assorted.terrain.common.block.TerrainBlocks;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/** This part's share of the Assorted World tab, which every part asks for and the first to load registers. */
public class TerrainCreativeItems {

    public static final ResourceKey<CreativeModeTab> TAB = SharedCreativeTabs.tab(Identifier.fromNamespaceAndPath(Family.ID, "tab"), Family.ICONS);

    public static void init() {
        // The ores led the tab when this was all one mod, so they still come first.
        SharedCreativeTabs.add(TAB, 100, TerrainCreativeItems::items);
    }

    private static List<ItemStack> items() {
        return List.of(new ItemStack(TerrainBlocks.RANDOMITE_ORE.get()), new ItemStack(TerrainBlocks.DEEPSLATE_RANDOMITE_ORE.get()));
    }
}
