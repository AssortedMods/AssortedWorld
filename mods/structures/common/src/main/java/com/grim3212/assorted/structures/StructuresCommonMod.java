package com.grim3212.assorted.structures;

import com.grim3212.assorted.lib.migration.MovedIds;
import com.grim3212.assorted.structures.common.block.StructuresBlocks;
import com.grim3212.assorted.structures.common.gen.StructuresBiomeModifiers;
import com.grim3212.assorted.structures.common.gen.feature.StructuresFeatures;
import com.grim3212.assorted.structures.common.gen.placement.StructuresPlacements;
import com.grim3212.assorted.structures.common.gen.structure.StructuresTypes;
import com.grim3212.assorted.structures.common.handlers.StructuresCreativeItems;
import com.grim3212.assorted.structures.config.StructuresCommonConfig;

public class StructuresCommonMod {

    public static final StructuresCommonConfig COMMON_CONFIG = new StructuresCommonConfig();

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");

        StructuresBlocks.init();
        StructuresTypes.init();
        StructuresFeatures.init();
        StructuresPlacements.init();
        StructuresBiomeModifiers.init();
        StructuresCreativeItems.init();

        // Structures and chest loot saved when this was all one mod, Assorted World, carry over to their new ids.
        MovedIds.inherit(Family.ID, Constants.MOD_ID);
    }
}
