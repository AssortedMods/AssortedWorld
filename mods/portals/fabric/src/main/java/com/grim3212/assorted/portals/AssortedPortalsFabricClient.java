package com.grim3212.assorted.portals;

import com.grim3212.assorted.portals.client.PortalsClient;
import net.fabricmc.api.ClientModInitializer;

public class AssortedPortalsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PortalsClient.init();
    }

}