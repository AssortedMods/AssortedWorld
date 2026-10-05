package com.grim3212.assorted.structures;

import com.grim3212.assorted.lib.family.Families;
import com.grim3212.assorted.lib.migration.MovedIds;
import com.grim3212.assorted.structures.common.block.StructuresBlocks;
import com.grim3212.assorted.structures.common.gen.StructuresBiomeModifiers;
import com.grim3212.assorted.structures.common.gen.feature.StructuresFeatures;
import com.grim3212.assorted.structures.common.gen.placement.StructuresPlacements;
import com.grim3212.assorted.structures.common.gen.structure.StructuresTypes;
import com.grim3212.assorted.structures.common.handlers.StructuresCreativeItems;
import com.grim3212.assorted.structures.config.StructuresCommonConfig;
import net.minecraft.resources.Identifier;

public class StructuresCommonMod {

    public static final StructuresCommonConfig COMMON_CONFIG = new StructuresCommonConfig();

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");
        Families.join(Constants.MOD_ID, Constants.FAMILY_ID)
                .icon(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ur_rune"), 40)
                .manualOrder(140);

        StructuresBlocks.init();
        StructuresTypes.init();
        StructuresFeatures.init();
        StructuresPlacements.init();
        StructuresBiomeModifiers.init();
        StructuresCreativeItems.init();

        // Structures and chest loot saved when this was all one mod, Assorted World, carry over to their new ids.
        MovedIds.inherit(Constants.FAMILY_ID, Constants.MOD_ID);
    }
}
