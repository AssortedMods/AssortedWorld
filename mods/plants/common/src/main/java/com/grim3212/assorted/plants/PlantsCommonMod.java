package com.grim3212.assorted.plants;

import com.grim3212.assorted.lib.migration.MovedIds;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import com.grim3212.assorted.plants.common.gen.PlantsBiomeModifiers;
import com.grim3212.assorted.plants.common.handlers.PlantsCreativeItems;
import com.grim3212.assorted.plants.config.PlantsCommonConfig;

public class PlantsCommonMod {

    public static final PlantsCommonConfig COMMON_CONFIG = new PlantsCommonConfig();

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");

        PlantsBlocks.init();
        PlantsBiomeModifiers.init();
        PlantsCreativeItems.init();

        // Recipes unlocked when this was all one mod carry over to their new ids.
        MovedIds.inherit(Family.ID, Constants.MOD_ID);
    }
}
