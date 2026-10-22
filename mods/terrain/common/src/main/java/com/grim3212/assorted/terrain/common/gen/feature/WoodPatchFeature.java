package com.grim3212.assorted.terrain.common.gen.feature;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

/**
 * One stray sapling, or one stump left where a tree was, in a wood that suits the biome it stands
 * in. The scatter into a patch is the placement's job; this only decides what the block is.
 */
public class WoodPatchFeature implements Feature {

    // One codec per kind, so the two keep the feature type ids they had.
    public static final MapCodec<WoodPatchFeature> SAPLING_CODEC = MapCodec.unit(() -> new WoodPatchFeature(false));
    public static final MapCodec<WoodPatchFeature> STUMP_CODEC = MapCodec.unit(() -> new WoodPatchFeature(true));

    private final boolean stump;

    public WoodPatchFeature(boolean stump) {
        this.stump = stump;
    }

    @Override
    public MapCodec<WoodPatchFeature> codec() {
        return this.stump ? STUMP_CODEC : SAPLING_CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        BlockPos pos = origin;

        BiomeWoods wood = BiomeWoods.pick(level.getBiome(pos), random);
        BlockState state = this.stump ? wood.stump() : wood.sapling();

        // A stump is a log and stands anywhere; a sapling has to have soil it accepts under it.
        if (!this.stump && !state.canSurvive(level, pos)) {
            return false;
        }

        level.setBlock(pos, state, Block.UPDATE_CLIENTS);
        return true;
    }
}
