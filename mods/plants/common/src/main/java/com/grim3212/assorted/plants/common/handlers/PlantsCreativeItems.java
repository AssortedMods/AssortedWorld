package com.grim3212.assorted.plants.common.handlers;

import com.grim3212.assorted.lib.core.creative.SharedCreativeTabs;
import com.grim3212.assorted.plants.Family;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/** This part's share of the Assorted World tab, which every part asks for and the first to load registers. */
public class PlantsCreativeItems {

    public static final ResourceKey<CreativeModeTab> TAB = SharedCreativeTabs.tab(Identifier.fromNamespaceAndPath(Family.ID, "tab"), Family.ICONS);

    public static void init() {
        // After the randomite ores and before the void crystal, as the tab was when this was all one mod.
        SharedCreativeTabs.add(TAB, 200, PlantsCreativeItems::items);
    }

    private static List<ItemStack> items() {
        return List.of(new ItemStack(PlantsBlocks.GUNPOWDER_REED.get()), new ItemStack(PlantsBlocks.GLOWSTONE_SEEDS.get()));
    }
}
