package com.grim3212.assorted.terrain.gametest;

import com.grim3212.assorted.terrain.common.gen.feature.CactusFieldFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.terrain.gametest.TerrainTestSupport.*;

/**
 * Cactus fields: how far from its own chunk a field reaches.
 */
final class CactusFieldTests {

    private CactusFieldTests() {
    }

    /** The largest radius {@code worldGenExpanded.cactusFieldSize} allows. */
    private static final int LARGEST_FIELD = 20;

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("cactus_field_stays_in_the_chunks_it_may_touch", CactusFieldTests::cactusFieldStaysInTheChunksItMayTouch);
    }

    /**
     * Worldgen hands a feature its own chunk and the eight around it, and a read of any other chunk
     * crashes the generator with "Requested chunk unavailable during world generation". The level the
     * field is placed in here fails the same way, and the field starts in a chunk's corner so the
     * largest radius reaches two chunks over.
     */
    private static void cactusFieldStaysInTheChunksItMayTouch(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos centre = helper.absolutePos(CENTRE);
        int chunkX = SectionPos.blockToSectionCoord(centre.getX());
        int chunkZ = SectionPos.blockToSectionCoord(centre.getZ());
        BlockPos origin = new BlockPos(SectionPos.sectionToBlockCoord(chunkX, 15), centre.getY(), SectionPos.sectionToBlockCoord(chunkZ, 15));
        helper.assertTrue(SectionPos.blockToSectionCoord(origin.getX() + LARGEST_FIELD) - chunkX == 2,
                "a field of radius " + LARGEST_FIELD + " at " + origin + " does not reach two chunks over, so this would check nothing");

        List<BlockPos> outside = new ArrayList<>();
        int[] inside = {0};
        WorldGenLevel region = (WorldGenLevel) Proxy.newProxyInstance(WorldGenLevel.class.getClassLoader(), new Class<?>[]{WorldGenLevel.class},
                (proxy, method, args) -> {
                    for (Object arg : args == null ? new Object[0] : args) {
                        if (arg instanceof BlockPos pos) {
                            if (Math.abs(SectionPos.blockToSectionCoord(pos.getX()) - chunkX) > 1
                                    || Math.abs(SectionPos.blockToSectionCoord(pos.getZ()) - chunkZ) > 1) {
                                outside.add(pos.immutable());
                                throw new IllegalStateException("Requested chunk unavailable during world generation: " + pos);
                            }
                            inside[0]++;
                        }
                    }
                    try {
                        return method.invoke(level, args);
                    } catch (InvocationTargetException e) {
                        throw e.getCause();
                    }
                });

        try {
            CactusFieldFeature.placeField(region, RandomSource.create(42L), origin, LARGEST_FIELD);
        } catch (IllegalStateException e) {
            // Recorded in outside, and reported below with the rest.
        }

        helper.assertTrue(outside.isEmpty(), "a cactus field of radius " + LARGEST_FIELD + " at " + origin + " reached " + outside
                + ", outside the chunks worldgen lets a feature touch");
        helper.assertTrue(inside[0] > 0, "the cactus field looked at nothing, so this checked nothing");
        helper.succeed();
    }
}
