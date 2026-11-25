package com.grim3212.assorted.terrain.common.gen.feature;

import com.grim3212.assorted.terrain.TerrainCommonMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * A field of cacti on the sand, spaced two apart so they do not break each other, at the varying
 * heights a cactus grows to.
 */
public class CactusFieldFeature extends Feature<NoneFeatureConfiguration> {

    public CactusFieldFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    /** Cacti one block apart damage each other, so every other column gets one. */
    private static final int SPACING = 2;

    private static final int MAX_HEIGHT = 3;

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        return placeField(context.level(), context.random(), context.origin(), TerrainCommonMod.COMMON_CONFIG.cactusFieldSize.get());
    }

    /** A field of the given radius; public so a gametest can try the largest one the config allows. */
    public static boolean placeField(WorldGenLevel level, RandomSource random, BlockPos origin, int radius) {
        int planted = 0;

        for (int x = -radius; x <= radius; x += SPACING) {
            for (int z = -radius; z <= radius; z += SPACING) {
                if ((x * x) + (z * z) > radius * radius || random.nextInt(4) == 0) {
                    continue;
                }
                // The config allows a radius of 20, further than the 16 blocks past its own chunk a
                // feature may read and write; the generator throws on a read outside that.
                if (!inReach(origin, x, z)) {
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

    /** Whether a column is in the origin's chunk or one of its eight neighbours, all a feature may touch. */
    private static boolean inReach(BlockPos origin, int x, int z) {
        return Math.abs(SectionPos.blockToSectionCoord(origin.getX() + x) - SectionPos.blockToSectionCoord(origin.getX())) <= 1
                && Math.abs(SectionPos.blockToSectionCoord(origin.getZ() + z) - SectionPos.blockToSectionCoord(origin.getZ())) <= 1;
    }
}
