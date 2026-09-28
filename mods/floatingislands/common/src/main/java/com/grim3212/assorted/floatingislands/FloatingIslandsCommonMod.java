package com.grim3212.assorted.floatingislands;

import com.grim3212.assorted.floatingislands.common.gen.FloatingIslandsBiomeModifiers;
import com.grim3212.assorted.floatingislands.common.gen.feature.FloatingIslandsFeatures;
import com.grim3212.assorted.floatingislands.common.gen.placement.FloatingIslandsPlacements;
import com.grim3212.assorted.floatingislands.config.FloatingIslandsCommonConfig;
import com.grim3212.assorted.lib.family.Families;
import com.grim3212.assorted.lib.migration.MovedIds;
import net.minecraft.resources.Identifier;

public class FloatingIslandsCommonMod {

    public static final FloatingIslandsCommonConfig COMMON_CONFIG = new FloatingIslandsCommonConfig();

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");
        Families.join(Constants.MOD_ID, Constants.FAMILY_ID)
                .icon(Identifier.withDefaultNamespace("grass_block"), 10)
                .manualOrder(140);

        FloatingIslandsFeatures.init();
        FloatingIslandsPlacements.init();
        FloatingIslandsBiomeModifiers.init();

        // Anything saved under assortedworld from when this was all one mod carries over to its new ids.
        MovedIds.inherit(Constants.FAMILY_ID, Constants.MOD_ID);
    }
}
