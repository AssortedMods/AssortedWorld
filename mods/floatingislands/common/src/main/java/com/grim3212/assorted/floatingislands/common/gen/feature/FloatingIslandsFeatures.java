package com.grim3212.assorted.floatingislands.common.gen.feature;

import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.floatingislands.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FloatingIslandsFeatures {

    public static final RegistryProvider<Feature<?>> FEATURES = RegistryProvider.create(Registries.FEATURE, Constants.MOD_ID);

    public static final IRegistryObject<Feature<NoneFeatureConfiguration>> FLOATING_ISLAND_FEATURE = FEATURES.register("floating_island", () -> new FloatingIslandFeature(NoneFeatureConfiguration.CODEC));

    public static void init() {
    }
}
