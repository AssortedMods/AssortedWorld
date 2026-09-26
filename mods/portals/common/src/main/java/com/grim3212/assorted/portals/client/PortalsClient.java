package com.grim3212.assorted.portals.client;

import com.grim3212.assorted.lib.platform.ClientServices;
import com.grim3212.assorted.portals.client.blockentity.VoidPortalRenderer;
import com.grim3212.assorted.portals.common.block.entity.PortalsBlockEntityTypes;

public class PortalsClient {

    public static void init() {
        ClientServices.CLIENT.registerBlockEntityRenderer(PortalsBlockEntityTypes.VOID_PORTAL, context -> new VoidPortalRenderer());
    }
}
