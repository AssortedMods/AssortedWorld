package com.grim3212.assorted.terrain;

import com.grim3212.assorted.lib.migration.MovedIds;
import com.grim3212.assorted.terrain.common.block.TerrainBlocks;
import com.grim3212.assorted.terrain.common.gen.TerrainBiomeModifiers;
import com.grim3212.assorted.terrain.common.gen.feature.TerrainFeatures;
import com.grim3212.assorted.terrain.common.gen.placement.TerrainPlacements;
import com.grim3212.assorted.terrain.common.handlers.TerrainCreativeItems;
import com.grim3212.assorted.terrain.config.TerrainCommonConfig;

public class TerrainCommonMod {

    public static final TerrainCommonConfig COMMON_CONFIG = new TerrainCommonConfig();

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");

        TerrainBlocks.init();
        TerrainFeatures.init();
        TerrainPlacements.init();
        TerrainBiomeModifiers.init();
        TerrainCreativeItems.init();

        // Desert well chests saved when this was all one mod carry over to their new loot table ids.
        MovedIds.inherit(Family.ID, Constants.MOD_ID);
    }
}
