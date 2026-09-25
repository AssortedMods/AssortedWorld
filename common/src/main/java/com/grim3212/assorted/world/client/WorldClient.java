package com.grim3212.assorted.world.client;

import com.grim3212.assorted.lib.platform.ClientServices;
import com.grim3212.assorted.world.client.blockentity.VoidPortalRenderer;
import com.grim3212.assorted.world.common.block.entity.WorldBlockEntityTypes;

public class WorldClient {

    public static void init() {
        ClientServices.CLIENT.registerBlockEntityRenderer(WorldBlockEntityTypes.VOID_PORTAL, context -> new VoidPortalRenderer());
    }
}
