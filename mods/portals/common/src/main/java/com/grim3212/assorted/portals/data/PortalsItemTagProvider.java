package com.grim3212.assorted.portals.data;

import com.grim3212.assorted.lib.data.LibItemTagProvider;
import com.grim3212.assorted.portals.api.PortalsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PortalsItemTagProvider extends LibItemTagProvider {

    public PortalsItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookup, blockTags);
    }

    @Override
    public void addCommonTags(Function<TagKey<Item>, TagAppender<Item>> tagger, BiConsumer<TagKey<Block>, TagKey<Item>> copier) {
        copier.accept(PortalsTags.Blocks.ORES_VOID_CRYSTAL, PortalsTags.Items.ORES_VOID_CRYSTAL);
    }
}
