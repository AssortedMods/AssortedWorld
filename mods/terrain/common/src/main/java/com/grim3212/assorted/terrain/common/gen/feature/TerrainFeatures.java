package com.grim3212.assorted.terrain.common.gen.feature;

import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.terrain.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TerrainFeatures {

    public static final RegistryProvider<Feature<?>> FEATURES = RegistryProvider.create(Registries.FEATURE, Constants.MOD_ID);

    public static final IRegistryObject<Feature<NoneFeatureConfiguration>> DESERT_WELL_FEATURE = FEATURES.register("desert_well", () -> new DesertWellFeature(NoneFeatureConfiguration.CODEC));
    public static final IRegistryObject<Feature<NoneFeatureConfiguration>> CROP_FIELD_FEATURE = FEATURES.register("crop_field", () -> new CropFieldFeature(NoneFeatureConfiguration.CODEC));
    public static final IRegistryObject<Feature<NoneFeatureConfiguration>> CACTUS_FIELD_FEATURE = FEATURES.register("cactus_field", () -> new CactusFieldFeature(NoneFeatureConfiguration.CODEC));
    public static final IRegistryObject<Feature<NoneFeatureConfiguration>> SAND_PIT_FEATURE = FEATURES.register("sand_pit", () -> new SandPitFeature(NoneFeatureConfiguration.CODEC));

    // One class, two features: the only difference is whether the block is the sapling or the log.
    public static final IRegistryObject<Feature<NoneFeatureConfiguration>> SAPLING_FEATURE = FEATURES.register("sapling", () -> new WoodPatchFeature(NoneFeatureConfiguration.CODEC, false));
    public static final IRegistryObject<Feature<NoneFeatureConfiguration>> TREE_STUMP_FEATURE = FEATURES.register("tree_stump", () -> new WoodPatchFeature(NoneFeatureConfiguration.CODEC, true));

    public static void init() {
    }
}
