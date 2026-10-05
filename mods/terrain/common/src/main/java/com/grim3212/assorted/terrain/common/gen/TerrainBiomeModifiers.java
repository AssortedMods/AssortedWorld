package com.grim3212.assorted.terrain.common.gen;

import com.grim3212.assorted.lib.conditions.LibParts;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.platform.services.IWorldGenHelper;
import com.grim3212.assorted.terrain.Constants;
import com.grim3212.assorted.terrain.TerrainCommonMod;
import com.grim3212.assorted.terrain.api.TerrainTags;
import com.grim3212.assorted.terrain.data.TerrainGenData;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

public class TerrainBiomeModifiers {

    public static final Identifier VANILLA_DESERT_WELL = Identifier.withDefaultNamespace("desert_well");

    public static void init() {
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(BiomeTags.IS_OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES, TerrainGenData.RANDOMITE_KEY);

        // Whether each of these generates at all is the config's rarity, read per attempt by
        // ConfigRarityFilter, so they are attached here unconditionally.
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(TerrainTags.Biomes.HAS_DESERT_WELL), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainGenData.DESERT_WELL_KEY);
        // Pits reshape the ground, so they go in with vanilla's other terrain edits, before anything
        // is built or grown on top of the sand they take away.
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(TerrainTags.Biomes.HAS_SAND_PIT), GenerationStep.Decoration.LOCAL_MODIFICATIONS, TerrainGenData.SAND_PIT_KEY);

        // Ours replace vanilla's where both would generate, unless this part is off. Read when a world loads its biomes.
        Services.WORLD_GEN.removeFeatureFromBiomes((key, biome) -> LibParts.isEnabled(Constants.MOD_ID) && TerrainCommonMod.COMMON_CONFIG.desertWellReplaceVanilla.get() && biome.is(TerrainTags.Biomes.HAS_DESERT_WELL),
                GenerationStep.Decoration.SURFACE_STRUCTURES, VANILLA_DESERT_WELL);

        // The planted ones go in with the vegetation, after the trees they grow among are standing.
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(TerrainTags.Biomes.HAS_CROP_FIELD), GenerationStep.Decoration.VEGETAL_DECORATION, TerrainGenData.CROP_FIELD_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(TerrainTags.Biomes.HAS_CACTUS_FIELD), GenerationStep.Decoration.VEGETAL_DECORATION, TerrainGenData.CACTUS_FIELD_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(TerrainTags.Biomes.HAS_SAPLINGS), GenerationStep.Decoration.VEGETAL_DECORATION, TerrainGenData.SAPLING_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(TerrainTags.Biomes.HAS_TREE_STUMPS), GenerationStep.Decoration.VEGETAL_DECORATION, TerrainGenData.TREE_STUMP_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(TerrainTags.Biomes.HAS_MELONS), GenerationStep.Decoration.VEGETAL_DECORATION, TerrainGenData.MELON_KEY);
    }

    private static IWorldGenHelper.BiomePredicate matchesTag(TagKey<Biome> tag) {
        return (resourceLocation, biome) -> biome.is(tag);
    }
}
