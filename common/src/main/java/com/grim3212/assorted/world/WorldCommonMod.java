package com.grim3212.assorted.world;

import com.grim3212.assorted.lib.events.LootTableModifyEvent;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.world.common.block.WorldBlocks;
import com.grim3212.assorted.world.common.block.entity.WorldBlockEntityTypes;
import com.grim3212.assorted.world.common.crafting.WorldConditions;
import com.grim3212.assorted.world.common.gen.WorldBiomeModifiers;
import com.grim3212.assorted.world.common.gen.feature.WorldFeatures;
import com.grim3212.assorted.world.common.gen.placement.WorldPlacements;
import com.grim3212.assorted.world.common.gen.structure.WorldStructures;
import com.grim3212.assorted.world.common.handlers.LootTableHandlers;
import com.grim3212.assorted.world.common.handlers.WorldCreativeItems;
import com.grim3212.assorted.world.common.item.WorldItems;
import com.grim3212.assorted.world.config.WorldCommonConfig;

public class WorldCommonMod {

    public static final WorldCommonConfig COMMON_CONFIG = new WorldCommonConfig();

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");

        WorldBlocks.init();
        WorldItems.init();
        WorldBlockEntityTypes.init();
        WorldStructures.init();
        WorldFeatures.init();
        WorldPlacements.init();
        WorldBiomeModifiers.init();
        WorldConditions.init();
        WorldCreativeItems.init();

        Services.EVENTS.registerEvent(LootTableModifyEvent.class, (final LootTableModifyEvent event) -> LootTableHandlers.init(event));
    }
}
