package com.grim3212.assorted.structures.data;

import com.grim3212.assorted.lib.data.LibBlockTagProvider;
import com.grim3212.assorted.structures.api.StructuresTags;
import com.grim3212.assorted.structures.common.block.StructuresBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class StructuresBlockTagProvider extends LibBlockTagProvider {

    public StructuresBlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup) {
        super(packOutput, lookup);
    }

    @Override
    public void addCommonTags(Function<TagKey<Block>, TagAppender<Block>> appender) {
        // The intrinsic tag appender is gone; TagAppender only accepts ResourceKeys. Wrap it back
        // into something that takes blocks so the tag lists below stay readable.
        Function<TagKey<Block>, BlockTagger> tagger = (tag) -> new BlockTagger(appender.apply(tag));

        tagger.apply(StructuresTags.Blocks.RUNES).add(StructuresBlocks.runeBlocks());
        tagger.apply(BlockTags.MINEABLE_WITH_PICKAXE).add(StructuresBlocks.runeBlocks());
    }

    private record BlockTagger(TagAppender<Block> appender) {

        BlockTagger add(Block... blocks) {
            for (Block block : blocks) {
                this.appender.add(BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow());
            }

            return this;
        }
    }
}
