package com.grim3212.assorted.floatingislands.config;

import com.grim3212.assorted.lib.config.ConfigurationType;
import com.grim3212.assorted.lib.config.IConfigurationBuilder;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.floatingislands.Constants;

import java.util.function.Supplier;

public class FloatingIslandsCommonConfig {

    public final Supplier<Integer> floatingIslandRarity;
    public final Supplier<Integer> floatingIslandMinSize;
    public final Supplier<Integer> floatingIslandMaxSize;
    public final Supplier<Integer> floatingIslandMinHeight;
    public final Supplier<Integer> floatingIslandMaxHeight;
    public final Supplier<Boolean> floatingIslandOres;

    /** Every rarity is read through {@code ConfigRarityFilter}, where 0 turns the feature off. */
    private static final String RARITY = "One in this many chunks gets one. Larger is rarer, and 0 turns them off entirely.";

    public FloatingIslandsCommonConfig() {
        final IConfigurationBuilder builder = Services.CONFIG.createBuilder(ConfigurationType.NOT_SYNCED, Constants.MOD_ID + "-common");

        floatingIslandRarity = builder.defineInteger("floatingIslands.rarity", 750, 0, 10000, RARITY);
        floatingIslandMinSize = builder.defineInteger("floatingIslands.minSize", 6, 3, 15, "The smallest an island can be, as how many blocks it reaches from its middle. Sizes are rolled evenly between this and maxSize, so setting the two equal makes every island the same size.");
        floatingIslandMaxSize = builder.defineInteger("floatingIslands.maxSize", 14, 3, 15, "The largest an island can be, as how many blocks it reaches from its middle. Capped at 15 because an island has to stay inside the chunks the generator lets a feature write to.");
        floatingIslandMinHeight = builder.defineInteger("floatingIslands.minHeight", 16, 1, 256, "The least open air between an island's lowest point and the highest ground anywhere under it. Over the tallest mountains an island sinks below this, and then drops its trees, rather than not generating.");
        floatingIslandMaxHeight = builder.defineInteger("floatingIslands.maxHeight", 64, 1, 256, "The most open air between an island's lowest point and the highest ground anywhere under it. Heights are rolled evenly between this and minHeight.");
        floatingIslandOres = builder.defineBoolean("floatingIslands.ores", true, "Set this to true to give the stone inside floating islands ore veins and pockets of granite, diorite, andesite, gravel and dirt, as the ground has.");

        builder.setup();
    }
}
