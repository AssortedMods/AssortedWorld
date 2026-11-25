package com.grim3212.assorted.floatingislands.data;

import com.google.common.collect.Lists;
import com.grim3212.assorted.lib.data.LibDatapackRegistryProvider;
import com.grim3212.assorted.floatingislands.Constants;
import com.grim3212.assorted.floatingislands.common.gen.feature.FloatingIslandsFeatures;
import com.grim3212.assorted.floatingislands.common.gen.placement.ConfigRarityFilter;
import com.grim3212.assorted.floatingislands.common.gen.placement.FloatingIslandsPlacements;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FloatingIslandsGenData extends LibDatapackRegistryProvider {

    public static final ResourceLocation FLOATING_ISLAND_KEY = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "floating_island");

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey(ResourceLocation key) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, key);
    }

    private static Map<ResourceLocation, ConfiguredFeature<?, ?>> getConfiguredFeatures() {
        Map<ResourceLocation, ConfiguredFeature<?, ?>> map = new LinkedHashMap<>();

        map.put(FLOATING_ISLAND_KEY, new ConfiguredFeature<>(FloatingIslandsFeatures.FLOATING_ISLAND_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));

        return map;
    }

    private static Map<ResourceLocation, PlacedFeature> getPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        Map<ResourceLocation, PlacedFeature> map = new LinkedHashMap<>();

        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        map.put(FLOATING_ISLAND_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(FLOATING_ISLAND_KEY)), surfacePlacement(FloatingIslandsPlacements.Parts.FLOATING_ISLAND)));

        return map;
    }

    @Override
    public void addEntries(RegistrySetBuilder builder) {
        builder.add(Registries.CONFIGURED_FEATURE, context -> {
            FloatingIslandsGenData.getConfiguredFeatures().forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.CONFIGURED_FEATURE, r), f);
            });
        });

        builder.add(Registries.PLACED_FEATURE, context -> {
            FloatingIslandsGenData.getPlacedFeatures(context).forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.PLACED_FEATURE, r), f);
            });
        });
    }

    @Override
    public List<ResourceKey<? extends Registry<?>>> registries() {
        return Lists.newArrayList(Registries.CONFIGURED_FEATURE, Registries.PLACED_FEATURE);
    }

    /**
     * One attempt on the surface of a chunk the config's rarity picked out. The feature itself
     * decides whether the spot will do, since each of these shapes its own footprint.
     */
    private static List<PlacementModifier> surfacePlacement(String part) {
        return List.of(ConfigRarityFilter.onAverageOnceEvery(part), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }
}
