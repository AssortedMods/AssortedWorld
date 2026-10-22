package com.grim3212.assorted.terrain.common.gen.feature;

import com.grim3212.assorted.terrain.TerrainCommonMod;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;

/**
 * A field of cacti on the sand, spaced two apart so they do not break each other, at the varying
 * heights a cactus grows to.
 */
public class CactusFieldFeature implements Feature {

    public static final MapCodec<CactusFieldFeature> CODEC = MapCodec.unit(CactusFieldFeature::new);

    @Override
    public MapCodec<CactusFieldFeature> codec() {
        return CODEC;
    }

    /** Cacti one block apart damage each other, so every other column gets one. */
    private static final int SPACING = 2;

    private static final int MAX_HEIGHT = 3;

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
        int radius = TerrainCommonMod.COMMON_CONFIG.cactusFieldSize.get();
        int planted = 0;

        for (int x = -radius; x <= radius; x += SPACING) {
            for (int z = -radius; z <= radius; z += SPACING) {
                if ((x * x) + (z * z) > radius * radius || random.nextInt(4) == 0) {
                    continue;
                }

                BlockPos pos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, origin.offset(x, 0, z));
                if (!level.getBlockState(pos.below()).is(Blocks.SAND) || !level.isEmptyBlock(pos)) {
                    continue;
                }

                int height = 1 + random.nextInt(MAX_HEIGHT);
                for (int y = 0; y < height; y++) {
                    if (!level.isEmptyBlock(pos.above(y))) {
                        break;
                    }
                    level.setBlock(pos.above(y), Blocks.CACTUS.defaultBlockState(), Block.UPDATE_CLIENTS);
                }
                planted++;
            }
        }

        return planted > 0;
    }
}
