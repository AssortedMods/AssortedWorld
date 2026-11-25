package com.grim3212.assorted.structures.data;

import com.grim3212.assorted.lib.data.LibBiomeTagProvider;
import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.structures.api.StructuresTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class StructuresBiomeTagProvider extends LibBiomeTagProvider {

    public StructuresBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    public void addCommonTags(Function<TagKey<Biome>, TagAppender<Biome>> tagger) {
        // addOptionalTag takes the tag id, so a tag no loaded pack defines is skipped rather than an error.
        tagger.apply(StructuresTags.Biomes.SUPPORTS_RUIN_GENERATION).addOptionalTag(LibCommonTags.Biomes.IS_DRY_OVERWORLD.location()).addOptionalTag(LibCommonTags.Biomes.IS_SPARSE_OVERWORLD.location()).addOptionalTag(LibCommonTags.Biomes.IS_PLAINS.location()).addOptionalTag(LibCommonTags.Biomes.IS_DENSE_OVERWORLD.location()).addOptionalTag(LibCommonTags.Biomes.IS_CONIFEROUS.location());
        tagger.apply(StructuresTags.Biomes.HAS_FOUNTAIN).addOptionalTag(LibCommonTags.Biomes.IS_SWAMP.location());
        tagger.apply(StructuresTags.Biomes.HAS_PYRAMID).addOptionalTag(BiomeTags.HAS_DESERT_PYRAMID.location());
        tagger.apply(StructuresTags.Biomes.HAS_SNOWBALL).addOptionalTag(LibCommonTags.Biomes.IS_SNOWY.location());
        tagger.apply(StructuresTags.Biomes.HAS_WATER_DOME).addOptionalTag(BiomeTags.IS_OCEAN.location());

        // Red sandstone pillars stand on the badlands' red sand.
        tagger.apply(StructuresTags.Biomes.HAS_SAND_PILLAR).addOptionalTag(LibCommonTags.Biomes.IS_DESERT.location()).addOptionalTag(BiomeTags.IS_BADLANDS.location());
    }
}
