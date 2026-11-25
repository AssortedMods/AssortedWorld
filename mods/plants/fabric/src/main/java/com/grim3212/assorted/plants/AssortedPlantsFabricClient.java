package com.grim3212.assorted.plants;

import com.grim3212.assorted.plants.client.PlantsClient;
import net.fabricmc.api.ClientModInitializer;

public class AssortedPlantsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PlantsClient.init();
    }
}
