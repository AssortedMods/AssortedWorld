package com.grim3212.assorted.portals;

import com.grim3212.assorted.lib.events.LootTableModifyEvent;
import com.grim3212.assorted.lib.migration.MovedIds;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import com.grim3212.assorted.portals.common.block.entity.PortalsBlockEntityTypes;
import com.grim3212.assorted.portals.common.gen.PortalsBiomeModifiers;
import com.grim3212.assorted.portals.common.handlers.LootTableHandlers;
import com.grim3212.assorted.portals.common.handlers.PortalsCreativeItems;
import com.grim3212.assorted.portals.common.item.PortalsItems;

public class PortalsCommonMod {

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");

        PortalsBlocks.init();
        PortalsItems.init();
        PortalsBlockEntityTypes.init();
        PortalsBiomeModifiers.init();
        PortalsCreativeItems.init();

        Services.EVENTS.registerEvent(LootTableModifyEvent.class, (final LootTableModifyEvent event) -> LootTableHandlers.init(event));

        // Recipes unlocked when this was all one mod carry over to their new ids.
        MovedIds.inherit(Family.ID, Constants.MOD_ID);
    }
}
