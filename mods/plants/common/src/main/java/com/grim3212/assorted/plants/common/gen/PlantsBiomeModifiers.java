package com.grim3212.assorted.plants.common.gen;

import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.plants.data.PlantsGenData;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;

public class PlantsBiomeModifiers {

    public static void init() {
        Services.WORLD_GEN.addFeatureToBiomes((resourceLocation, biome) -> biome.is(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.VEGETAL_DECORATION, PlantsGenData.GUNPOWDER_REED_KEY);
    }
}
