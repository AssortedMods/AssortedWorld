package com.grim3212.assorted.floatingislands;

import com.grim3212.assorted.floatingislands.common.gen.FloatingIslandsBiomeModifiers;
import com.grim3212.assorted.floatingislands.common.gen.feature.FloatingIslandsFeatures;
import com.grim3212.assorted.floatingislands.common.gen.placement.FloatingIslandsPlacements;
import com.grim3212.assorted.floatingislands.config.FloatingIslandsCommonConfig;
import com.grim3212.assorted.lib.migration.MovedIds;

public class FloatingIslandsCommonMod {

    public static final FloatingIslandsCommonConfig COMMON_CONFIG = new FloatingIslandsCommonConfig();

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");

        FloatingIslandsFeatures.init();
        FloatingIslandsPlacements.init();
        FloatingIslandsBiomeModifiers.init();

        // Anything saved under assortedworld from when this was all one mod carries over to its new ids.
        MovedIds.inherit(Family.ID, Constants.MOD_ID);
    }
}
