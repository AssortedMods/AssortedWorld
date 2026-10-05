package com.grim3212.assorted.floatingislands.data;

import com.grim3212.assorted.lib.data.LibBiomeTagProvider;
import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.floatingislands.api.FloatingIslandsTags;
import com.grim3212.assorted.floatingislands.common.gen.feature.FloatingIslandType;
import com.grim3212.assorted.floatingislands.common.gen.feature.FloatingIslandTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class FloatingIslandsBiomeTagProvider extends LibBiomeTagProvider {

    public FloatingIslandsBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    public void addCommonTags(Function<TagKey<Biome>, TagAppender<Biome>> tagger) {
        // Islands hang over any overworld surface; the floating_island/* tags pick which kind.
        tagger.apply(FloatingIslandsTags.Biomes.HAS_FLOATING_ISLAND).addOptionalTag(BiomeTags.IS_OVERWORLD);

        this.addFloatingIslands(tagger);
    }

    /**
     * Which kinds of floating island hang over which biomes. A biome in several of these rolls
     * between those kinds; one in none of them gets the fallback kinds.
     */
    private void addFloatingIslands(Function<TagKey<Biome>, TagAppender<Biome>> tagger) {
        island(tagger, FloatingIslandTypes.MEADOW).addOptionalTag(LibCommonTags.Biomes.IS_PLAINS).add(Biomes.MEADOW);
        island(tagger, FloatingIslandTypes.FOREST).addOptionalTag(BiomeTags.IS_FOREST).addOptionalTag(LibCommonTags.Biomes.IS_PLAINS);
        island(tagger, FloatingIslandTypes.CHERRY).add(Biomes.CHERRY_GROVE, Biomes.MEADOW);
        island(tagger, FloatingIslandTypes.DARK_FOREST).add(Biomes.DARK_FOREST);
        island(tagger, FloatingIslandTypes.PALE_GARDEN).add(Biomes.PALE_GARDEN, Biomes.DARK_FOREST);

        island(tagger, FloatingIslandTypes.SNOWY).addOptionalTag(LibCommonTags.Biomes.IS_SNOWY);
        island(tagger, FloatingIslandTypes.TAIGA).addOptionalTag(BiomeTags.IS_TAIGA).add(Biomes.WINDSWEPT_FOREST, Biomes.GROVE);
        island(tagger, FloatingIslandTypes.GLACIER).add(Biomes.ICE_SPIKES, Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS, Biomes.SNOWY_SLOPES, Biomes.FROZEN_OCEAN,
                Biomes.DEEP_FROZEN_OCEAN, Biomes.FROZEN_RIVER);
        island(tagger, FloatingIslandTypes.ROCKY).add(Biomes.STONY_PEAKS, Biomes.STONY_SHORE, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS,
                Biomes.JAGGED_PEAKS, Biomes.FROZEN_PEAKS, Biomes.SNOWY_SLOPES);

        island(tagger, FloatingIslandTypes.DESERT).addOptionalTag(LibCommonTags.Biomes.IS_DESERT);
        island(tagger, FloatingIslandTypes.SAVANNA).addOptionalTag(BiomeTags.IS_SAVANNA);
        island(tagger, FloatingIslandTypes.BADLANDS).addOptionalTag(BiomeTags.IS_BADLANDS);

        island(tagger, FloatingIslandTypes.JUNGLE).addOptionalTag(BiomeTags.IS_JUNGLE);
        island(tagger, FloatingIslandTypes.SWAMP).add(Biomes.SWAMP);
        island(tagger, FloatingIslandTypes.MANGROVE).add(Biomes.MANGROVE_SWAMP);
        island(tagger, FloatingIslandTypes.LUSH).addOptionalTag(BiomeTags.IS_JUNGLE).addOptionalTag(LibCommonTags.Biomes.IS_SWAMP);

        island(tagger, FloatingIslandTypes.MUSHROOM).addOptionalTag(LibCommonTags.Biomes.IS_MUSHROOM).add(Biomes.DARK_FOREST);
    }

    private static TagAppender<Biome> island(Function<TagKey<Biome>, TagAppender<Biome>> tagger, FloatingIslandType type) {
        return tagger.apply(type.biomes());
    }
}
