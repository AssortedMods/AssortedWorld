package com.grim3212.assorted.portals.gametest;

import com.grim3212.assorted.portals.data.PortalsGenData;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.portals.gametest.PortalsTestSupport.*;

/**
 * Worldgen: the void crystal ore's datapack entries and the biome modifier that places it.
 */
final class WorldgenTests {

    private WorldgenTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("worldgen_datapack_entries_resolve", WorldgenTests::worldgenDatapackEntriesResolve);
        out.accept("mod_features_are_attached_to_biomes", WorldgenTests::modFeaturesAreAttachedToBiomes);
    }

    /** The ore is in the loaded datapack. A misrouted datagen run prunes its json silently. */
    private static void worldgenDatapackEntriesResolve(GameTestHelper helper) {
        RegistryAccess registries = helper.getLevel().registryAccess();
        List<String> problems = new ArrayList<>();

        Identifier id = PortalsGenData.VOID_CRYSTAL_KEY;
        Registry<ConfiguredFeature<?, ?>> configured = registries.lookupOrThrow(Registries.CONFIGURED_FEATURE);
        Registry<PlacedFeature> placed = registries.lookupOrThrow(Registries.PLACED_FEATURE);
        if (configured.getValue(id) == null) {
            problems.add("configured feature " + id + " is not in the registry");
        }
        if (placed.getValue(id) == null) {
            problems.add("placed feature " + id + " is not in the registry");
        }

        helper.assertTrue(problems.isEmpty(), "worldgen datapack entries missing: " + String.join("; ", problems));
        helper.succeed();
    }

    /**
     * The ore is attached to a biome. {@code PortalsBiomeModifiers} does that through a different
     * loader API on each side, so this is where the two could disagree.
     */
    private static void modFeaturesAreAttachedToBiomes(GameTestHelper helper) {
        Registry<Biome> biomes = helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME);

        int biomeCount = 0;
        for (Biome biome : biomes) {
            if (hasFeature(biome, PortalsGenData.VOID_CRYSTAL_KEY)) {
                biomeCount++;
            }
        }

        helper.assertTrue(biomeCount > 0, PortalsGenData.VOID_CRYSTAL_KEY + " is attached to no biome at all");
        helper.succeed();
    }
}
