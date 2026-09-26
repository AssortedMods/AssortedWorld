package com.grim3212.assorted.structures.data;

import com.google.common.collect.Lists;
import com.grim3212.assorted.lib.data.LibDatapackRegistryProvider;
import com.grim3212.assorted.structures.Constants;
import com.grim3212.assorted.structures.api.StructuresTags;
import com.grim3212.assorted.structures.common.gen.feature.StructuresFeatures;
import com.grim3212.assorted.structures.common.gen.placement.ConfigRarityFilter;
import com.grim3212.assorted.structures.common.gen.placement.StructuresPlacements;
import com.grim3212.assorted.structures.common.gen.structure.fountain.FountainStructure;
import com.grim3212.assorted.structures.common.gen.structure.pyramid.PyramidStructure;
import com.grim3212.assorted.structures.common.gen.structure.snowball.SnowballStructure;
import com.grim3212.assorted.structures.common.gen.structure.waterdome.WaterDomeStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructuresGenData extends LibDatapackRegistryProvider {

    private static final Identifier SNOWBALL_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "snowball");
    private static final Identifier PYRAMID_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pyramid");
    private static final Identifier FOUNTAIN_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "fountain");
    private static final Identifier WATER_DOME_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "water_dome");

    private static final ResourceKey<Structure> SNOWBALL_RESOURCE_KEY = structureResourceKey(SNOWBALL_KEY);
    private static final ResourceKey<Structure> PYRAMID_RESOURCE_KEY = structureResourceKey(PYRAMID_KEY);
    private static final ResourceKey<Structure> FOUNTAIN_RESOURCE_KEY = structureResourceKey(FOUNTAIN_KEY);
    private static final ResourceKey<Structure> WATER_DOME_RESOURCE_KEY = structureResourceKey(WATER_DOME_KEY);

    public static final Identifier RUIN_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "ruin");
    public static final Identifier SPIRE_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "spire");
    public static final Identifier SAND_PILLAR_KEY = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "sand_pillar");

    private static ResourceKey<Structure> structureResourceKey(Identifier key) {
        return ResourceKey.create(Registries.STRUCTURE, key);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureResourceKey(Identifier key) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, key);
    }

    private static Map<ResourceKey<Structure>, Structure> getStructures(BootstrapContext<Structure> context) {
        Map<ResourceKey<Structure>, Structure> map = new HashMap<>();

        HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);

        map.put(SNOWBALL_RESOURCE_KEY, new SnowballStructure(new Structure.StructureSettings(holderGetter.getOrThrow(StructuresTags.Biomes.HAS_SNOWBALL), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
        map.put(PYRAMID_RESOURCE_KEY, new PyramidStructure(new Structure.StructureSettings(holderGetter.getOrThrow(StructuresTags.Biomes.HAS_PYRAMID), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
        map.put(FOUNTAIN_RESOURCE_KEY, new FountainStructure(new Structure.StructureSettings(holderGetter.getOrThrow(StructuresTags.Biomes.HAS_FOUNTAIN), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
        map.put(WATER_DOME_RESOURCE_KEY, new WaterDomeStructure(new Structure.StructureSettings(holderGetter.getOrThrow(StructuresTags.Biomes.HAS_WATER_DOME), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));

        return map;
    }

    private static Map<Identifier, StructureSet> getStructureSets(BootstrapContext<StructureSet> context) {
        Map<Identifier, StructureSet> map = new HashMap<>();

        HolderGetter<Structure> holderGetter = context.lookup(Registries.STRUCTURE);

        map.put(SNOWBALL_KEY, new StructureSet(holderGetter.getOrThrow(SNOWBALL_RESOURCE_KEY), new RandomSpreadStructurePlacement(36, 21, RandomSpreadType.LINEAR, 737462782)));
        map.put(PYRAMID_KEY, new StructureSet(holderGetter.getOrThrow(PYRAMID_RESOURCE_KEY), new RandomSpreadStructurePlacement(36, 10, RandomSpreadType.LINEAR, 827612344)));
        map.put(FOUNTAIN_KEY, new StructureSet(holderGetter.getOrThrow(FOUNTAIN_RESOURCE_KEY), new RandomSpreadStructurePlacement(32, 10, RandomSpreadType.LINEAR, 983497234)));
        map.put(WATER_DOME_KEY, new StructureSet(holderGetter.getOrThrow(WATER_DOME_RESOURCE_KEY), new RandomSpreadStructurePlacement(32, 14, RandomSpreadType.LINEAR, 432432568)));

        return map;
    }

    private static Map<Identifier, ConfiguredFeature<?, ?>> getConfiguredFeatures() {
        Map<Identifier, ConfiguredFeature<?, ?>> map = new HashMap<>();

        map.put(RUIN_KEY, new ConfiguredFeature<>(StructuresFeatures.RUIN_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
        map.put(SPIRE_KEY, new ConfiguredFeature<>(StructuresFeatures.SPIRE_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
        map.put(SAND_PILLAR_KEY, new ConfiguredFeature<>(StructuresFeatures.SAND_PILLAR_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));

        return map;
    }

    private static Map<Identifier, PlacedFeature> getPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        Map<Identifier, PlacedFeature> map = new HashMap<>();

        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        map.put(RUIN_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(RUIN_KEY)), heightmapPlacement(350)));
        map.put(SPIRE_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(SPIRE_KEY)), heightmapPlacement(350)));
        map.put(SAND_PILLAR_KEY, new PlacedFeature(holderGetter.getOrThrow(configuredFeatureResourceKey(SAND_PILLAR_KEY)), surfacePlacement(StructuresPlacements.Parts.SANDSTONE_PILLAR)));

        return map;
    }

    @Override
    public void addEntries(RegistrySetBuilder builder) {
        builder.add(Registries.STRUCTURE, context -> {
            StructuresGenData.getStructures(context).forEach((r, f) -> {
                context.register(r, f);
            });
        });

        builder.add(Registries.STRUCTURE_SET, context -> {
            StructuresGenData.getStructureSets(context).forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.STRUCTURE_SET, r), f);
            });
        });

        builder.add(Registries.CONFIGURED_FEATURE, context -> {
            StructuresGenData.getConfiguredFeatures().forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.CONFIGURED_FEATURE, r), f);
            });
        });

        builder.add(Registries.PLACED_FEATURE, context -> {
            StructuresGenData.getPlacedFeatures(context).forEach((r, f) -> {
                context.register(ResourceKey.create(Registries.PLACED_FEATURE, r), f);
            });
        });
    }

    @Override
    public List<ResourceKey<? extends Registry<?>>> registries() {
        return Lists.newArrayList(Registries.STRUCTURE, Registries.STRUCTURE_SET, Registries.CONFIGURED_FEATURE, Registries.PLACED_FEATURE);
    }

    /**
     * One attempt on the surface of a chunk the config's rarity picked out. The feature itself
     * decides whether the spot will do, since each of these shapes its own footprint.
     */
    private static List<PlacementModifier> surfacePlacement(String part) {
        return List.of(ConfigRarityFilter.onAverageOnceEvery(part), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

    private static List<PlacementModifier> heightmapPlacement(int rarity) {
        return List.of(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }
}
