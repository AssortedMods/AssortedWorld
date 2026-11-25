package com.grim3212.assorted.plants.client;

import com.grim3212.assorted.lib.platform.ClientServices;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import net.minecraft.client.renderer.RenderType;

import java.util.List;

public class PlantsClient {

    public static void init() {
        // The layer the block models name, for Fabric, which ignores "render_type".
        ClientServices.CLIENT.registerBlockRenderType(RenderType.cutout(), () -> List.of(PlantsBlocks.GUNPOWDER_REED.get(), PlantsBlocks.GLOWSTONE_SEEDS.get()));
    }
}
