package com.grim3212.assorted.structures.common.gen;

import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.platform.services.IWorldGenHelper;
import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.structures.api.StructuresTags;
import com.grim3212.assorted.structures.data.StructuresGenData;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

public class StructuresBiomeModifiers {

    public static void init() {
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(StructuresTags.Biomes.SUPPORTS_RUIN_GENERATION), GenerationStep.Decoration.SURFACE_STRUCTURES, StructuresGenData.RUIN_KEY);
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(LibCommonTags.Biomes.IS_MOUNTAIN), GenerationStep.Decoration.SURFACE_STRUCTURES, StructuresGenData.SPIRE_KEY);

        // Whether pillars generate at all is the config's rarity, read per attempt by
        // ConfigRarityFilter, so they are attached here unconditionally.
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(StructuresTags.Biomes.HAS_SAND_PILLAR), GenerationStep.Decoration.SURFACE_STRUCTURES, StructuresGenData.SAND_PILLAR_KEY);
    }

    private static IWorldGenHelper.BiomePredicate matchesTag(TagKey<Biome> tag) {
        return (resourceLocation, biome) -> biome.is(tag);
    }
}
