package com.grim3212.assorted.floatingislands.common.gen.feature;

import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.floatingislands.Constants;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FloatingIslandsFeatures {

    public static final RegistryProvider<MapCodec<? extends Feature>> FEATURE_TYPES = RegistryProvider.create(Registries.FEATURE_TYPE, Constants.MOD_ID);

    public static final IRegistryObject<MapCodec<? extends Feature>> FLOATING_ISLAND_FEATURE = FEATURE_TYPES.register("floating_island", () -> FloatingIslandFeature.CODEC);

    public static void init() {
    }
}
