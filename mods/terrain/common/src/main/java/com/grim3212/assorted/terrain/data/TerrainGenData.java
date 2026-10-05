package com.grim3212.assorted.terrain.data;

import com.google.common.collect.Lists;
import com.grim3212.assorted.lib.data.LibDatapackRegistryProvider;
import com.grim3212.assorted.terrain.Constants;
import com.grim3212.assorted.terrain.common.gen.feature.TerrainFeatures;
import com.grim3212.assorted.terrain.common.gen.feature.TerrainTargets;
import com.grim3212.assorted.terrain.common.gen.placement.ConfigRarityFilter;
import com.grim3212.assorted.terrain.common.gen.placement.TerrainPlacements;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TerrainGenData extends LibDatapackRegistryProvider {

    public static final Identifier RANDOMITE_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ore_randomite");

    public static final Identifier DESERT_WELL_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "desert_well");
    public static final Identifier CROP_FIELD_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "crop_field");
    public static final Identifier CACTUS_FIELD_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "cactus_field");
    public static final Identifier SAND_PIT_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "sand_pit");
    public static final Identifier SAPLING_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "patch_saplings");
    public static final Identifier TREE_STUMP_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "tree_stumps");
    public static final Identifier MELON_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "patch_melons");

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey(Identifier key) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, key);
    }

    private static Map<Identifier, ConfiguredFeature<?, ?>> getConfiguredFeatures() {
        Map<Identifier, ConfiguredFeature<?, ?>> map = new HashMap<>();

        map.put(RANDOMITE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(TerrainTargets.ORE_RANDOMITE_TARGET_LIST, 8)));

        map.put(DESERT_WELL_KEY, new ConfiguredFeature<>(TerrainFeatures.DESERT_WELL_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
        map.put(CROP_FIELD_KEY, new ConfiguredFeature<>(TerrainFeatures.CROP_FIELD_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
        map.put(CACTUS_FIELD_KEY, new ConfiguredFeature<>(TerrainFeatures.CACTUS_FIELD_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
        map.put(SAND_PIT_KEY, new ConfiguredFeature<>(TerrainFeatures.SAND_PIT_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));

        // Saplings and stumps pick their wood from the biome they land in, which a state provider
        // cannot do, so each is its own feature; the placement below is what makes it a patch.
        map.put(SAPLING_KEY, new ConfiguredFeature<>(TerrainFeatures.SAPLING_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
        map.put(TREE_STUMP_KEY, new ConfiguredFeature<>(TerrainFeatures.TREE_STUMP_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
        map.put(MELON_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.MELON))));

        return map;
    }

    private static Map<Identifier, PlacedFeature> getPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        Map<Identifier, PlacedFeature> map = new HashMap<>();

        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        map.put(RANDOMITE_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(RANDOMITE_KEY)), commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP))));

        map.put(DESERT_WELL_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(DESERT_WELL_KEY)), surfacePlacement(TerrainPlacements.Parts.DESERT_WELL)));
        map.put(CROP_FIELD_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(CROP_FIELD_KEY)), surfacePlacement(TerrainPlacements.Parts.CROP_FIELD)));
        map.put(CACTUS_FIELD_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(CACTUS_FIELD_KEY)), surfacePlacement(TerrainPlacements.Parts.CACTUS_FIELD)));
        map.put(SAND_PIT_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(SAND_PIT_KEY)), surfacePlacement(TerrainPlacements.Parts.SAND_PIT)));

        map.put(SAPLING_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(SAPLING_KEY)), scatteredOnGrass(TerrainPlacements.Parts.SAPLING, 16)));
        map.put(TREE_STUMP_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(TREE_STUMP_KEY)), scatteredOnGrass(TerrainPlacements.Parts.TREE_STUMP, 12)));
        map.put(MELON_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(MELON_KEY)), pumpkinStylePatch(TerrainPlacements.Parts.MELON, 24)));

        return map;
    }

    @Override
    public void addEntries(RegistrySetBuilder builder) {
        builder.add(Registries.CONFIGURED_FEATURE, context -> {
            TerrainGenData.getConfiguredFeatures().forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.CONFIGURED_FEATURE, r), f);
            });
        });

        builder.add(Registries.PLACED_FEATURE, context -> {
            TerrainGenData.getPlacedFeatures(context).forEach((r, f) -> {
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

    /**
     * One attempt on the surface of a chunk the config's rarity picked out. The feature itself
     * decides whether the spot will do, since each of these shapes its own footprint.
     */
    private static List<PlacementModifier> surfacePlacement(String part) {
        return List.of(ConfigRarityFilter.onAverageOnceEvery(part), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

    /**
     * A scatter of single blocks over the forest floor. The offset comes before the heightmap so
     * every attempt gets its own ground height, which is what lets a patch follow a slope.
     */
    private static List<PlacementModifier> scatteredOnGrass(String part, int count) {
        return List.of(ConfigRarityFilter.onAverageOnceEvery(part), InSquarePlacement.spread(), CountPlacement.of(count), RandomOffsetPlacement.ofTriangle(6, 0), PlacementUtils.HEIGHTMAP_NO_LEAVES,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesFluids(Fluids.EMPTY),
                        BlockPredicate.matchesTag(new BlockPos(0, -1, 0), BlockTags.SUPPORTS_VEGETATION))), BiomeFilter.biome());
    }

    /**
     * Vanilla's pumpkin patch shape with the config's rarity in place of the baked in one, and well
     * under its 96 tries, since a patch of melons is worth more than a patch of pumpkins.
     */
    private static List<PlacementModifier> pumpkinStylePatch(String part, int count) {
        return List.of(ConfigRarityFilter.onAverageOnceEvery(part), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), CountPlacement.of(count),
                RandomOffsetPlacement.ofTriangle(7, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.GRASS_BLOCK))));
    }
}
