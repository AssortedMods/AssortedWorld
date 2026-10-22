package com.grim3212.assorted.structures.common.gen.feature;

import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.structures.Constants;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class StructuresFeatures {

    public static final RegistryProvider<MapCodec<? extends Feature>> FEATURE_TYPES = RegistryProvider.create(Registries.FEATURE_TYPE, Constants.MOD_ID);

    public static final IRegistryObject<MapCodec<? extends Feature>> RUIN_FEATURE = FEATURE_TYPES.register("ruin", () -> RuinFeature.CODEC);
    public static final IRegistryObject<MapCodec<? extends Feature>> SPIRE_FEATURE = FEATURE_TYPES.register("spire", () -> SpireFeature.CODEC);

    public static final IRegistryObject<MapCodec<? extends Feature>> SAND_PILLAR_FEATURE = FEATURE_TYPES.register("sand_pillar", () -> SandPillarFeature.CODEC);

    public static void init() {
    }
}
