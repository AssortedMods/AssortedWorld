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
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.material.Fluids;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TerrainGenData extends LibDatapackRegistryProvider {

    public static final ResourceLocation RANDOMITE_KEY = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ore_randomite");

    public static final ResourceLocation DESERT_WELL_KEY = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "desert_well");
    public static final ResourceLocation CROP_FIELD_KEY = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "crop_field");
    public static final ResourceLocation CACTUS_FIELD_KEY = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cactus_field");
    public static final ResourceLocation SAND_PIT_KEY = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "sand_pit");
    public static final ResourceLocation SAPLING_KEY = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "patch_saplings");
    public static final ResourceLocation TREE_STUMP_KEY = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "tree_stumps");
    public static final ResourceLocation MELON_KEY = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "patch_melons");

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey(ResourceLocation key) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, key);
    }

    private static Map<ResourceLocation, ConfiguredFeature<?, ?>> getConfiguredFeatures() {
        Map<ResourceLocation, ConfiguredFeature<?, ?>> map = new LinkedHashMap<>();

        map.put(RANDOMITE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(TerrainTargets.ORE_RANDOMITE_TARGET_LIST, 8)));

        map.put(DESERT_WELL_KEY, unconfigured(TerrainFeatures.DESERT_WELL_FEATURE.get()));
        map.put(CROP_FIELD_KEY, unconfigured(TerrainFeatures.CROP_FIELD_FEATURE.get()));
        map.put(CACTUS_FIELD_KEY, unconfigured(TerrainFeatures.CACTUS_FIELD_FEATURE.get()));
        map.put(SAND_PIT_KEY, unconfigured(TerrainFeatures.SAND_PIT_FEATURE.get()));

        // Saplings and stumps pick their wood from the biome they land in, which a state provider
        // cannot do, so each is its own feature; the placement below is what makes it a patch.
        map.put(SAPLING_KEY, unconfigured(TerrainFeatures.SAPLING_FEATURE.get()));
        map.put(TREE_STUMP_KEY, unconfigured(TerrainFeatures.TREE_STUMP_FEATURE.get()));
        map.put(MELON_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.MELON))));

        return map;
    }

    private static ConfiguredFeature<?, ?> unconfigured(Feature<NoneFeatureConfiguration> feature) {
        return new ConfiguredFeature<>(feature, NoneFeatureConfiguration.INSTANCE);
    }

    private static Map<ResourceLocation, PlacedFeature> getPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        Map<ResourceLocation, PlacedFeature> map = new LinkedHashMap<>();

        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        map.put(RANDOMITE_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(RANDOMITE_KEY)), commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.top()))));

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
     * <p>
     * 1.21.1 has no {@code #supports_vegetation}; {@code #dirt} is what its saplings stand on.
     */
    private static List<PlacementModifier> scatteredOnGrass(String part, int count) {
        return List.of(ConfigRarityFilter.onAverageOnceEvery(part), InSquarePlacement.spread(), CountPlacement.of(count), RandomOffsetPlacement.of(triangle(6), triangle(0)), HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesFluids(Fluids.EMPTY),
                        BlockPredicate.matchesTag(new BlockPos(0, -1, 0), BlockTags.DIRT))), BiomeFilter.biome());
    }

    /**
     * Vanilla's pumpkin patch shape with the config's rarity in place of the baked in one, and well
     * under its 96 tries, since a patch of melons is worth more than a patch of pumpkins.
     */
    private static List<PlacementModifier> pumpkinStylePatch(String part, int count) {
        return List.of(ConfigRarityFilter.onAverageOnceEvery(part), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), CountPlacement.of(count),
                RandomOffsetPlacement.of(triangle(7), triangle(3)),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), List.of(Blocks.GRASS_BLOCK)))));
    }

    /**
     * An offset from {@code -spread} to {@code spread} that peaks at 0, the spread vanilla's random
     * patches scatter with. 1.21.1 has no trapezoid int provider, so it is the same distribution as
     * a weighted list: each offset weighted by how many ways two dice land on it.
     */
    private static IntProvider triangle(int spread) {
        if (spread == 0) {
            return ConstantInt.ZERO;
        }

        SimpleWeightedRandomList.Builder<IntProvider> offsets = SimpleWeightedRandomList.builder();
        for (int offset = -spread; offset <= spread; offset++) {
            offsets.add(ConstantInt.of(offset), spread + 1 - Math.abs(offset));
        }
        return new WeightedListInt(offsets.build());
    }
}
