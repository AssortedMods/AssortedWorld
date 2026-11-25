package com.grim3212.assorted.portals.client;

import com.grim3212.assorted.lib.platform.ClientServices;
import com.grim3212.assorted.portals.client.blockentity.VoidPortalRenderer;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import com.grim3212.assorted.portals.common.block.entity.PortalsBlockEntityTypes;
import net.minecraft.client.renderer.RenderType;

import java.util.List;

public class PortalsClient {

    public static void init() {
        ClientServices.CLIENT.registerBlockEntityRenderer(PortalsBlockEntityTypes.VOID_PORTAL, context -> new VoidPortalRenderer());
        // 1.21.1 does not pick a chunk layer from the textures. NeoForge reads the model's render_type;
        // this is Fabric's, and a no-op on NeoForge.
        ClientServices.CLIENT.registerBlockRenderType(RenderType.cutout(), () -> List.of(PortalsBlocks.ENDER_FLAMES.get()));
    }
}
