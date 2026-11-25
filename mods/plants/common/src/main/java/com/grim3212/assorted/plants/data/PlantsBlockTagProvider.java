package com.grim3212.assorted.plants.data;

import com.grim3212.assorted.lib.data.LibBlockTagProvider;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class PlantsBlockTagProvider extends LibBlockTagProvider {

    public PlantsBlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup) {
        super(packOutput, lookup);
    }

    @Override
    public void addCommonTags(Function<TagKey<Block>, TagAppender<Block>> appender) {
        // The plain TagAppender only accepts ResourceKeys.
        appender.apply(BlockTags.MINEABLE_WITH_AXE).add(BuiltInRegistries.BLOCK.getResourceKey(PlantsBlocks.GUNPOWDER_REED.get()).orElseThrow());
    }
}
