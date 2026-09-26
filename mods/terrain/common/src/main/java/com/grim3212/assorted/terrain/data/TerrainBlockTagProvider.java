package com.grim3212.assorted.terrain.data;

import com.grim3212.assorted.lib.data.LibBlockTagProvider;
import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.terrain.api.TerrainTags;
import com.grim3212.assorted.terrain.common.block.TerrainBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class TerrainBlockTagProvider extends LibBlockTagProvider {

    public TerrainBlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup) {
        super(packOutput, lookup);
    }

    @Override
    public void addCommonTags(Function<TagKey<Block>, TagAppender<Block>> appender) {
        // The intrinsic tag appender is gone; TagAppender only accepts ResourceKeys. Wrap it back
        // into something that takes blocks so the tag lists below stay readable.
        Function<TagKey<Block>, BlockTagger> tagger = (tag) -> new BlockTagger(appender.apply(tag));

        tagger.apply(LibCommonTags.Blocks.ORES).addTag(TerrainTags.Blocks.ORES_RANDOMITE);
        tagger.apply(TerrainTags.Blocks.ORES_RANDOMITE).add(TerrainBlocks.RANDOMITE_ORE.get(), TerrainBlocks.DEEPSLATE_RANDOMITE_ORE.get());

        tagger.apply(BlockTags.MINEABLE_WITH_PICKAXE).add(TerrainBlocks.RANDOMITE_ORE.get(), TerrainBlocks.DEEPSLATE_RANDOMITE_ORE.get());
        tagger.apply(BlockTags.NEEDS_STONE_TOOL).add(TerrainBlocks.RANDOMITE_ORE.get(), TerrainBlocks.DEEPSLATE_RANDOMITE_ORE.get());
    }

    private record BlockTagger(TagAppender<Block> appender) {

        BlockTagger add(Block... blocks) {
            for (Block block : blocks) {
                this.appender.add(BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow());
            }

            return this;
        }

        BlockTagger addTag(TagKey<Block> tag) {
            this.appender.addTag(tag);
            return this;
        }
    }
}
