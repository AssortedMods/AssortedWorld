package com.grim3212.assorted.terrain.common.gen.feature;

import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.terrain.Constants;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class TerrainFeatures {

    public static final RegistryProvider<MapCodec<? extends Feature>> FEATURE_TYPES = RegistryProvider.create(Registries.FEATURE_TYPE, Constants.MOD_ID);

    public static final IRegistryObject<MapCodec<? extends Feature>> DESERT_WELL_FEATURE = FEATURE_TYPES.register("desert_well", () -> DesertWellFeature.CODEC);
    public static final IRegistryObject<MapCodec<? extends Feature>> CROP_FIELD_FEATURE = FEATURE_TYPES.register("crop_field", () -> CropFieldFeature.CODEC);
    public static final IRegistryObject<MapCodec<? extends Feature>> CACTUS_FIELD_FEATURE = FEATURE_TYPES.register("cactus_field", () -> CactusFieldFeature.CODEC);
    public static final IRegistryObject<MapCodec<? extends Feature>> SAND_PIT_FEATURE = FEATURE_TYPES.register("sand_pit", () -> SandPitFeature.CODEC);

    // One class, two feature types: the only difference is whether the block is the sapling or the log.
    public static final IRegistryObject<MapCodec<? extends Feature>> SAPLING_FEATURE = FEATURE_TYPES.register("sapling", () -> WoodPatchFeature.SAPLING_CODEC);
    public static final IRegistryObject<MapCodec<? extends Feature>> TREE_STUMP_FEATURE = FEATURE_TYPES.register("tree_stump", () -> WoodPatchFeature.STUMP_CODEC);

    public static void init() {
    }
}
