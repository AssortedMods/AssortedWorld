package com.grim3212.assorted.plants;

import com.grim3212.assorted.lib.family.Families;
import com.grim3212.assorted.lib.migration.MovedIds;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import com.grim3212.assorted.plants.common.gen.PlantsBiomeModifiers;
import com.grim3212.assorted.plants.common.handlers.PlantsCreativeItems;
import com.grim3212.assorted.plants.config.PlantsCommonConfig;
import net.minecraft.resources.Identifier;

public class PlantsCommonMod {

    public static final PlantsCommonConfig COMMON_CONFIG = new PlantsCommonConfig();

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");
        Families.join(Constants.MOD_ID, Constants.FAMILY_ID)
                .icon(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "gunpowder_reed"), 20)
                .manualOrder(140);

        PlantsBlocks.init();
        PlantsBiomeModifiers.init();
        PlantsCreativeItems.init();

        // Recipes unlocked when this was all one mod carry over to their new ids.
        MovedIds.inherit(Constants.FAMILY_ID, Constants.MOD_ID);
    }
}
