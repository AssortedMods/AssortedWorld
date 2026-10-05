package com.grim3212.assorted.floatingislands.gametest;

import com.grim3212.assorted.floatingislands.Constants;
import com.grim3212.assorted.floatingislands.common.gen.placement.ConfigRarityFilter;
import com.grim3212.assorted.floatingislands.common.gen.placement.FloatingIslandsPlacements;
import com.grim3212.assorted.floatingislands.data.FloatingIslandsGenData;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.floatingislands.gametest.FloatingIslandsTestSupport.*;

/**
 * Worldgen: the island's datapack entries, its config rarity and its biome modifier.
 */
final class WorldgenTests {

    private WorldgenTests() {
    }

    /** Namespaces whose features this pack adds through AssortedLib's world gen helper. */
    private static final List<String> OURS = List.of(Constants.MOD_ID, "assortedores");

    /**
     * A feature's index in its generation step decides the seed it is placed from, so the same seed
     * only gives the same world if both loaders add the features in the same order. Fabric applies
     * its biome modifications sorted by the placed feature's id, so AssortedLib's NeoForge side sorts
     * the same way and this asserts the result: our features run in ascending id order in every step
     * they appear in. When this fails the two loaders still generate identical terrain and every
     * scattered feature has moved, which is not obvious from looking at one of them.
     */
    private static void addedFeaturesKeepOneOrderAcrossLoaders(GameTestHelper helper) {
        var biomes = helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME);
        int checked = 0;

        for (String biomeId : new String[]{"minecraft:plains", "minecraft:desert", "minecraft:jagged_peaks"}) {
            var biome = biomes.getOrThrow(ResourceKey.create(Registries.BIOME, Identifier.parse(biomeId)));
            List<HolderSet<PlacedFeature>> steps = biome.value().getGenerationSettings().features();

            for (int step = 0; step < steps.size(); step++) {
                String previous = null;

                for (Holder<PlacedFeature> feature : steps.get(step)) {
                    String id = feature.unwrapKey().map(key -> key.identifier().toString()).orElse(null);
                    if (id == null || OURS.stream().noneMatch(namespace -> id.startsWith(namespace + ":"))) {
                        continue;
                    }

                    checked++;
                    if (previous != null && previous.compareTo(id) > 0) {
                        helper.fail(biomeId + " step " + step + " has " + previous + " before " + id
                                + "; our features must be in ascending id order or the loaders place them differently");
                    }
                    previous = id;
                }
            }
        }

        helper.assertTrue(checked > 0, "found none of our features in these biomes, so this asserted nothing");
        helper.succeed();
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("worldgen_datapack_entries_resolve", WorldgenTests::worldgenDatapackEntriesResolve);
        out.accept("mod_features_are_attached_to_biomes", WorldgenTests::modFeaturesAreAttachedToBiomes);
        out.accept("config_rarity_gates_the_new_features", WorldgenTests::configRarityGatesTheNewFeatures);
        out.accept("added_features_keep_one_order_across_loaders", WorldgenTests::addedFeaturesKeepOneOrderAcrossLoaders);
    }

    /**
     * The island feature is in the loaded datapack. It can go missing silently: a misrouted datagen
     * run prunes its json.
     */
    private static void worldgenDatapackEntriesResolve(GameTestHelper helper) {
        RegistryAccess registries = helper.getLevel().registryAccess();
        List<String> problems = new ArrayList<>();

        Registry<ConfiguredFeature<?, ?>> configured = registries.lookupOrThrow(Registries.CONFIGURED_FEATURE);
        Registry<PlacedFeature> placed = registries.lookupOrThrow(Registries.PLACED_FEATURE);
        Identifier id = FloatingIslandsGenData.FLOATING_ISLAND_KEY;
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
     * The island's placed feature carries the config rarity modifier and its part reads back a rarity.
     * Without the modifier it would generate in every chunk, and with an unknown part in none.
     */
    private static void configRarityGatesTheNewFeatures(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();

        Identifier rarityType = BuiltInRegistries.PLACEMENT_MODIFIER_TYPE.getKey(FloatingIslandsPlacements.CONFIG_RARITY.get());
        if (rarityType == null) {
            problems.add("the config_rarity placement modifier type is not registered, so its placed features cannot load");
        }

        if (FloatingIslandsPlacements.rarity(FloatingIslandsPlacements.Parts.FLOATING_ISLAND) <= 0) {
            problems.add("part " + FloatingIslandsPlacements.Parts.FLOATING_ISLAND + " reads back no rarity, so it can never generate");
        }

        Registry<PlacedFeature> placed = helper.getLevel().registryAccess().lookupOrThrow(Registries.PLACED_FEATURE);
        Identifier id = FloatingIslandsGenData.FLOATING_ISLAND_KEY;
        PlacedFeature feature = placed.getValue(id);
        if (feature == null) {
            problems.add("placed feature " + id + " is not in the registry");
        } else if (feature.placement().stream().noneMatch(modifier -> modifier instanceof ConfigRarityFilter)) {
            problems.add("placed feature " + id + " has no config rarity filter, so the config cannot turn it off");
        }

        helper.assertTrue(problems.isEmpty(), "config gated features: " + String.join("; ", problems));
        helper.succeed();
    }

    /**
     * The island feature is attached to a biome. {@code FloatingIslandsBiomeModifiers} does that through
     * a different loader API on each side, so this is where the two could disagree.
     */
    private static void modFeaturesAreAttachedToBiomes(GameTestHelper helper) {
        Registry<Biome> biomes = helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME);
        Identifier id = FloatingIslandsGenData.FLOATING_ISLAND_KEY;

        int biomeCount = 0;
        for (Biome biome : biomes) {
            if (hasFeature(biome, id)) {
                biomeCount++;
            }
        }

        helper.assertTrue(biomeCount > 0, id + " is attached to no biome at all, so it can never generate");
        helper.succeed();
    }
}
