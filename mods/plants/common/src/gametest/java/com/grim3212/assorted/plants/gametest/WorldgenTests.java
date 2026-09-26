package com.grim3212.assorted.plants.gametest;

import com.grim3212.assorted.plants.data.PlantsGenData;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.plants.gametest.PlantsTestSupport.*;

/**
 * Worldgen: the gunpowder reed patch is in the datapack and attached to biomes.
 */
final class WorldgenTests {

    private WorldgenTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("worldgen_datapack_entries_resolve", WorldgenTests::worldgenDatapackEntriesResolve);
        out.accept("mod_features_are_attached_to_biomes", WorldgenTests::modFeaturesAreAttachedToBiomes);
    }

    /** The reed patch is in the loaded datapack; a misrouted datagen run prunes its json silently. */
    private static void worldgenDatapackEntriesResolve(GameTestHelper helper) {
        RegistryAccess registries = helper.getLevel().registryAccess();
        Registry<ConfiguredFeature<?, ?>> configured = registries.lookupOrThrow(Registries.CONFIGURED_FEATURE);
        Registry<PlacedFeature> placed = registries.lookupOrThrow(Registries.PLACED_FEATURE);

        helper.assertTrue(configured.getValue(PlantsGenData.GUNPOWDER_REED_KEY) != null, "configured feature " + PlantsGenData.GUNPOWDER_REED_KEY + " is not in the registry");
        helper.assertTrue(placed.getValue(PlantsGenData.GUNPOWDER_REED_KEY) != null, "placed feature " + PlantsGenData.GUNPOWDER_REED_KEY + " is not in the registry");
        helper.succeed();
    }

    /**
     * The reed patch is attached to a biome. {@code PlantsBiomeModifiers} does that through a
     * different loader API on each side, so this is where the two could disagree.
     */
    private static void modFeaturesAreAttachedToBiomes(GameTestHelper helper) {
        Registry<Biome> biomes = helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME);
        boolean attached = false;
        for (Biome biome : biomes) {
            attached |= hasFeature(biome, PlantsGenData.GUNPOWDER_REED_KEY);
        }

        helper.assertTrue(attached, PlantsGenData.GUNPOWDER_REED_KEY + " is attached to no biome at all, so it can never generate");
        helper.succeed();
    }
}
