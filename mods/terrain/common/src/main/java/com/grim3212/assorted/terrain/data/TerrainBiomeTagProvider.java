package com.grim3212.assorted.terrain.data;

import com.grim3212.assorted.lib.data.LibBiomeTagProvider;
import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.terrain.api.TerrainTags;
import com.grim3212.assorted.terrain.common.gen.feature.BiomeWoods;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class TerrainBiomeTagProvider extends LibBiomeTagProvider {

    public TerrainBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    public void addCommonTags(Function<TagKey<Biome>, TagAppender<Biome>> tagger) {
        tagger.apply(TerrainTags.Biomes.HAS_DESERT_WELL).addOptionalTag(LibCommonTags.Biomes.IS_DESERT);
        // Desert only: c:is_sandy also holds the beach and the badlands.
        tagger.apply(TerrainTags.Biomes.HAS_CACTUS_FIELD).addOptionalTag(LibCommonTags.Biomes.IS_DESERT);
        tagger.apply(TerrainTags.Biomes.HAS_SAND_PIT).addOptionalTag(LibCommonTags.Biomes.IS_DESERT);

        tagger.apply(TerrainTags.Biomes.HAS_CROP_FIELD).addOptionalTag(LibCommonTags.Biomes.IS_PLAINS);
        tagger.apply(TerrainTags.Biomes.HAS_MELONS).addOptionalTag(LibCommonTags.Biomes.IS_PLAINS).addOptionalTag(LibCommonTags.Biomes.IS_WET_OVERWORLD);

        // Where trees already grow, so a stray sapling or stump reads as part of the wood. The two
        // c: tags only reach the dense and old growth woods, so the plain vanilla ones come in
        // alongside them - a forest is the first place anyone looks.
        for (TagKey<Biome> woodland : List.of(TerrainTags.Biomes.HAS_SAPLINGS, TerrainTags.Biomes.HAS_TREE_STUMPS)) {
            tagger.apply(woodland).addOptionalTag(LibCommonTags.Biomes.IS_DENSE_OVERWORLD).addOptionalTag(LibCommonTags.Biomes.IS_CONIFEROUS)
                    .addOptionalTag(BiomeTags.IS_FOREST).addOptionalTag(BiomeTags.IS_TAIGA).addOptionalTag(BiomeTags.IS_JUNGLE)
                    .add(Biomes.CHERRY_GROVE, Biomes.PALE_GARDEN, Biomes.WINDSWEPT_FOREST, Biomes.MANGROVE_SWAMP, Biomes.SWAMP);
        }

        this.addWoods(tagger);
    }

    /**
     * Which wood a stray sapling or stump is made of, per biome. A biome in several of these rolls
     * between those woods; one in none of them gets the woods marked as a fallback.
     */
    private void addWoods(Function<TagKey<Biome>, TagAppender<Biome>> tagger) {
        wood(tagger, BiomeWoods.OAK).addOptionalTag(BiomeTags.IS_FOREST).addOptionalTag(LibCommonTags.Biomes.IS_PLAINS).addOptionalTag(LibCommonTags.Biomes.IS_SWAMP)
                .add(Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_HILLS, Biomes.SPARSE_JUNGLE);
        wood(tagger, BiomeWoods.BIRCH).addOptionalTag(BiomeTags.IS_FOREST).add(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);
        wood(tagger, BiomeWoods.SPRUCE).addOptionalTag(BiomeTags.IS_TAIGA).addOptionalTag(LibCommonTags.Biomes.IS_SNOWY).add(Biomes.GROVE, Biomes.WINDSWEPT_FOREST);
        wood(tagger, BiomeWoods.JUNGLE).addOptionalTag(BiomeTags.IS_JUNGLE);
        wood(tagger, BiomeWoods.ACACIA).addOptionalTag(BiomeTags.IS_SAVANNA);
        wood(tagger, BiomeWoods.DARK_OAK).add(Biomes.DARK_FOREST);
        wood(tagger, BiomeWoods.CHERRY).add(Biomes.CHERRY_GROVE);
        wood(tagger, BiomeWoods.PALE_OAK).add(Biomes.PALE_GARDEN);
        wood(tagger, BiomeWoods.MANGROVE).add(Biomes.MANGROVE_SWAMP);
    }

    private static TagAppender<Biome> wood(Function<TagKey<Biome>, TagAppender<Biome>> tagger, BiomeWoods wood) {
        return tagger.apply(wood.biomes());
    }
}
