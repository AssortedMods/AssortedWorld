package com.grim3212.assorted.portals;

import com.grim3212.assorted.lib.events.LootTableModifyEvent;
import com.grim3212.assorted.lib.family.Families;
import com.grim3212.assorted.lib.migration.MovedIds;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import com.grim3212.assorted.portals.common.block.entity.PortalsBlockEntityTypes;
import com.grim3212.assorted.portals.common.gen.PortalsBiomeModifiers;
import com.grim3212.assorted.portals.common.handlers.LootTableHandlers;
import com.grim3212.assorted.portals.common.handlers.PortalsCreativeItems;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import net.minecraft.resources.Identifier;

public class PortalsCommonMod {

    public static void init() {
        Constants.LOG.info(Constants.MOD_NAME + " starting up...");
        Families.join(Constants.MOD_ID, Constants.FAMILY_ID)
                .icon(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "void_portal_frame"), 30)
                .manualOrder(140);

        PortalsBlocks.init();
        PortalsItems.init();
        PortalsBlockEntityTypes.init();
        PortalsBiomeModifiers.init();
        PortalsCreativeItems.init();

        Services.EVENTS.registerEvent(LootTableModifyEvent.class, (final LootTableModifyEvent event) -> LootTableHandlers.init(event));

        // Recipes unlocked when this was all one mod carry over to their new ids.
        MovedIds.inherit(Constants.FAMILY_ID, Constants.MOD_ID);
    }
}
