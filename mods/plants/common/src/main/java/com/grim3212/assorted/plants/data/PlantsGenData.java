package com.grim3212.assorted.plants.data;

import com.google.common.collect.Lists;
import com.grim3212.assorted.lib.data.LibDatapackRegistryProvider;
import com.grim3212.assorted.plants.Constants;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantsGenData extends LibDatapackRegistryProvider {

    public static final Identifier GUNPOWDER_REED_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "patch_gunpowder_reed");

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey(Identifier key) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, key);
    }

    private static Map<Identifier, ConfiguredFeature<?, ?>> getConfiguredFeatures() {
        Map<Identifier, ConfiguredFeature<?, ?>> map = new HashMap<>();

        // Feature.RANDOM_PATCH and RandomPatchConfiguration are gone: the "try N times around this
        // spot" behaviour is expressed with placement modifiers now (see the placed feature below),
        // so the configured feature is just the block column that used to be wrapped by the patch.
        map.put(GUNPOWDER_REED_KEY, new ConfiguredFeature<>(Feature.BLOCK_COLUMN, BlockColumnConfiguration.simple(BiasedToBottomInt.of(2, 4), BlockStateProvider.simple(PlantsBlocks.GUNPOWDER_REED.get()))));

        return map;
    }

    private static Map<Identifier, PlacedFeature> getPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        Map<Identifier, PlacedFeature> map = new HashMap<>();

        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        map.put(GUNPOWDER_REED_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(GUNPOWDER_REED_KEY)), reedPatchPlacement(8)));

        return map;
    }

    @Override
    public void addEntries(RegistrySetBuilder builder) {
        builder.add(Registries.CONFIGURED_FEATURE, context -> {
            PlantsGenData.getConfiguredFeatures().forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.CONFIGURED_FEATURE, r), f);
            });
        });

        builder.add(Registries.PLACED_FEATURE, context -> {
            PlantsGenData.getPlacedFeatures(context).forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.PLACED_FEATURE, r), f);
            });
        });
    }

    @Override
    public List<ResourceKey<? extends Registry<?>>> registries() {
        return Lists.newArrayList(Registries.CONFIGURED_FEATURE, Registries.PLACED_FEATURE);
    }

    /**
     * Twenty attempts over a 4 block horizontal radius, keeping only spots that can hold a reed
     * beside water, like vanilla's sugar cane patch.
     */
    private static List<PlacementModifier> reedPatchPlacement(int rarity) {
        return List.of(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), CountPlacement.of(20), RandomOffsetPlacement.ofTriangle(4, 0),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(PlantsBlocks.GUNPOWDER_REED.get().defaultBlockState(), BlockPos.ZERO),
                        BlockPredicate.anyOf(BlockPredicate.matchesFluids(new BlockPos(1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER), BlockPredicate.matchesFluids(new BlockPos(-1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER), BlockPredicate.matchesFluids(new BlockPos(0, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER),
                                BlockPredicate.matchesFluids(new BlockPos(0, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER)))));
    }
}
