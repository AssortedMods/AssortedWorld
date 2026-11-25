package com.grim3212.assorted.structures.gametest;

import com.grim3212.assorted.structures.Constants;
import com.grim3212.assorted.structures.StructuresCommonMod;
import com.grim3212.assorted.structures.common.block.RuneBlock;
import com.grim3212.assorted.structures.common.gen.feature.SpireFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Spires, placed straight into the test box with every roll coming up lowest, which makes each one a
 * single column of known height: a radius of 0 and a height of 5, doubled to a tower of 10 on its own
 * centre. That needs the default config, a spire radius above 2 and a death spire chance above 0.
 */
final class SpireTests {

    private SpireTests() {
    }

    /** Where a spire is placed, two above the floor: the block between is the foot the rune goes in. */
    private static final int ORIGIN_Y = 2;

    /** How tall the tower above the origin grows with every roll at its lowest. */
    private static final int TOWER = 10;

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("every_spire_can_get_a_rune", SpireTests::everySpireCanGetARune);
        out.accept("death_spire_lava_rises_with_the_tower", SpireTests::deathSpireLavaRisesWithTheTower);
    }

    /**
     * Each spire rolls for its own rune. The feature is one instance shared by the whole world, so a
     * "rune placed" flag kept on it lets only the first spire after a server start have one.
     */
    private static void everySpireCanGetARune(GameTestHelper helper) {
        List<BlockPos> columns = List.of(new BlockPos(2, 0, 4), new BlockPos(6, 0, 4));
        for (BlockPos column : columns) {
            placeSpire(helper, column);
        }

        List<String> problems = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            BlockState foot = helper.getBlockState(columns.get(i).above());
            if (!(foot.getBlock() instanceof RuneBlock)) {
                problems.add("spire " + (i + 1) + " has " + BuiltInRegistries.BLOCK.getKey(foot.getBlock()) + " at its foot instead of a rune");
            }
        }

        clear(helper, columns);
        helper.assertTrue(problems.isEmpty(), "a spire that rolled its rune did not get one: " + String.join("; ", problems));
        helper.succeed();
    }

    /**
     * The rare lava in a death spire's tower goes in the tower, where the netherrack it stands in for
     * would have gone. With every roll at its lowest each block of the tower is lava.
     */
    private static void deathSpireLavaRisesWithTheTower(GameTestHelper helper) {
        BlockPos column = new BlockPos(4, 0, 4);
        placeSpire(helper, column);

        List<String> problems = new ArrayList<>();
        for (int height = 0; height < TOWER; height++) {
            BlockState state = helper.getBlockState(column.above(ORIGIN_Y + height));
            if (!state.is(Blocks.LAVA)) {
                problems.add(BuiltInRegistries.BLOCK.getKey(state.getBlock()) + " at height " + height);
            }
        }

        clear(helper, List.of(column));
        helper.assertTrue(problems.isEmpty(), "the death spire's tower should be all lava, but has " + String.join(", ", problems));
        helper.succeed();
    }

    private static void placeSpire(GameTestHelper helper, BlockPos column) {
        helper.assertTrue(StructuresCommonMod.COMMON_CONFIG.spireRadius.get() > 2 && StructuresCommonMod.COMMON_CONFIG.deathSpireChance.get() > 0,
                "these tests need the default spire radius and death spire chance");
        ServerLevel level = helper.getLevel();
        Feature spire = level.registryAccess().lookupOrThrow(Registries.FEATURE).getValue(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "spire"));
        // The world's own instance: one made here would not show state left over from another spire.
        helper.assertTrue(spire instanceof SpireFeature, "the spire feature in the registry is " + spire);

        BlockPos origin = helper.absolutePos(column.above(ORIGIN_Y));
        // A built chunk keeps no worldgen heightmap up to date, and one read before the box was
        // placed would not see its floor.
        Heightmap.primeHeightmaps(level.getChunkAt(origin), EnumSet.of(Heightmap.Types.WORLD_SURFACE_WG));
        helper.assertTrue(spire.place(level, level.getChunkSource().getGenerator(), lowestRolls(), origin),
                "the spire at " + column + " did not build, so the ground under it was not flat and lit");
    }

    /** The tower rises out of the box, and its lava would flow into the next test. */
    private static void clear(GameTestHelper helper, List<BlockPos> columns) {
        for (BlockPos column : columns) {
            for (int y = 1; y < ORIGIN_Y + TOWER; y++) {
                helper.setBlock(column.above(y), Blocks.AIR);
            }
        }
    }

    /** Every roll comes up lowest, so every chance a spire has is taken. */
    private static RandomSource lowestRolls() {
        return new LegacyRandomSource(0L) {
            @Override
            public float nextFloat() {
                return 0.0F;
            }

            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };
    }
}
