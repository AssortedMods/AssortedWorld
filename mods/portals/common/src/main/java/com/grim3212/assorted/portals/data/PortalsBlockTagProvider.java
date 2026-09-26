package com.grim3212.assorted.portals.data;

import com.grim3212.assorted.lib.data.LibBlockTagProvider;
import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.portals.api.PortalsTags;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class PortalsBlockTagProvider extends LibBlockTagProvider {

    public PortalsBlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup) {
        super(packOutput, lookup);
    }

    @Override
    public void addCommonTags(Function<TagKey<Block>, TagAppender<Block>> appender) {
        // The intrinsic tag appender is gone; TagAppender only accepts ResourceKeys. Wrap it back
        // into something that takes blocks so the tag lists below stay readable.
        Function<TagKey<Block>, BlockTagger> tagger = (tag) -> new BlockTagger(appender.apply(tag));

        tagger.apply(LibCommonTags.Blocks.ORES).addTag(PortalsTags.Blocks.ORES_VOID_CRYSTAL);

        tagger.apply(PortalsTags.Blocks.ORES_VOID_CRYSTAL).add(PortalsBlocks.VOID_CRYSTAL_ORE.get());
        tagger.apply(BlockTags.MINEABLE_WITH_PICKAXE).add(PortalsBlocks.VOID_CRYSTAL_ORE.get(), PortalsBlocks.VOID_PORTAL_FRAME.get());
        tagger.apply(BlockTags.NEEDS_IRON_TOOL).add(PortalsBlocks.VOID_CRYSTAL_ORE.get());
        tagger.apply(BlockTags.NEEDS_DIAMOND_TOOL).add(PortalsBlocks.VOID_PORTAL_FRAME.get());
        tagger.apply(BlockTags.DRAGON_IMMUNE).add(PortalsBlocks.VOID_PORTAL_FRAME.get());
        // #fire is what mob pathfinding steers around.
        tagger.apply(BlockTags.FIRE).add(PortalsBlocks.ENDER_FLAMES.get());
        tagger.apply(PortalsTags.Blocks.INFINIBURN_ENDER_FLAMES).add(Blocks.END_STONE);
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
