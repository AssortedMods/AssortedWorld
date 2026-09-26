package com.grim3212.assorted.structures.data;

import com.grim3212.assorted.lib.data.LibItemTagProvider;
import com.grim3212.assorted.structures.api.StructuresTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class StructuresItemTagProvider extends LibItemTagProvider {

    public StructuresItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookup, blockTags);
    }

    @Override
    public void addCommonTags(Function<TagKey<Item>, TagAppender<Item>> tagger, BiConsumer<TagKey<Block>, TagKey<Item>> copier) {
        copier.accept(StructuresTags.Blocks.RUNES, StructuresTags.Items.RUNES);
    }
}
