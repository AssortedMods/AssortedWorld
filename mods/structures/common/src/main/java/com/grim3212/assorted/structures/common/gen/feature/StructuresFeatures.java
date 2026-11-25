package com.grim3212.assorted.structures.common.gen.feature;

import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.structures.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StructuresFeatures {

    public static final RegistryProvider<Feature<?>> FEATURE_TYPES = RegistryProvider.create(Registries.FEATURE, Constants.MOD_ID);

    public static final IRegistryObject<Feature<NoneFeatureConfiguration>> RUIN_FEATURE = FEATURE_TYPES.register("ruin", RuinFeature::new);
    public static final IRegistryObject<Feature<NoneFeatureConfiguration>> SPIRE_FEATURE = FEATURE_TYPES.register("spire", SpireFeature::new);

    public static final IRegistryObject<Feature<NoneFeatureConfiguration>> SAND_PILLAR_FEATURE = FEATURE_TYPES.register("sand_pillar", SandPillarFeature::new);

    public static void init() {
    }
}
