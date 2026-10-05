package com.grim3212.assorted.portals.data;

import com.google.common.collect.Lists;
import com.grim3212.assorted.lib.data.LibDatapackRegistryProvider;
import com.grim3212.assorted.portals.Constants;
import com.grim3212.assorted.portals.common.gen.feature.PortalsTargets;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalsGenData extends LibDatapackRegistryProvider {

    public static final Identifier VOID_CRYSTAL_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_void_crystal");

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey(Identifier key) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, key);
    }

    private static Map<Identifier, ConfiguredFeature<?, ?>> getConfiguredFeatures() {
        Map<Identifier, ConfiguredFeature<?, ?>> map = new HashMap<>();

        // Rarer than diamond; a fifth of what would show on an island surface is left out.
        map.put(VOID_CRYSTAL_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(PortalsTargets.ORE_VOID_CRYSTAL_TARGET_LIST, 4, 0.2F)));

        return map;
    }

    private static Map<Identifier, PlacedFeature> getPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        Map<Identifier, PlacedFeature> map = new HashMap<>();

        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        // The outer islands' end stone sits between about y 0 and 80; most attempts land in the void.
        map.put(VOID_CRYSTAL_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(VOID_CRYSTAL_KEY)), commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(80)))));

        return map;
    }

    @Override
    public void addEntries(RegistrySetBuilder builder) {
        builder.add(Registries.CONFIGURED_FEATURE, context -> {
            PortalsGenData.getConfiguredFeatures().forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.CONFIGURED_FEATURE, r), f);
            });
        });

        builder.add(Registries.PLACED_FEATURE, context -> {
            PortalsGenData.getPlacedFeatures(context).forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.PLACED_FEATURE, r), f);
            });
        });
    }

    @Override
    public List<ResourceKey<? extends Registry<?>>> registries() {
        return Lists.newArrayList(Registries.CONFIGURED_FEATURE, Registries.PLACED_FEATURE);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier placement, PlacementModifier modifier) {
        return List.of(placement, InSquarePlacement.spread(), modifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }
}
