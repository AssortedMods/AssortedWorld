package com.grim3212.assorted.plants.config;

import com.grim3212.assorted.lib.config.ConfigurationType;
import com.grim3212.assorted.lib.config.IConfigurationBuilder;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.plants.Constants;

import java.util.function.Supplier;

public class PlantsCommonConfig {

    public final Supplier<Integer> glowstoneSeedPlantHeight;

    public PlantsCommonConfig() {
        final IConfigurationBuilder builder = Services.CONFIG.createBuilder(ConfigurationType.NOT_SYNCED, Constants.MOD_ID + "-common");

        glowstoneSeedPlantHeight = builder.defineInteger("glowstoneSeeds.plantHeight", 15, -64, 320, "Outside the Nether, glowstone seeds only take on a netherrack ceiling at or below this height. In the Nether they take at any height.");

        builder.setup();
    }
}
