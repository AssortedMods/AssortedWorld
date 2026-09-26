package com.grim3212.assorted.terrain.gametest;

import com.grim3212.assorted.terrain.Constants;
import com.grim3212.assorted.terrain.TerrainCommonMod;
import com.grim3212.assorted.terrain.api.TerrainLootTables;
import com.grim3212.assorted.terrain.common.gen.TerrainBiomeModifiers;
import com.grim3212.assorted.terrain.common.gen.placement.ConfigRarityFilter;
import com.grim3212.assorted.terrain.common.gen.placement.TerrainPlacements;
import com.grim3212.assorted.terrain.data.TerrainGenData;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.terrain.gametest.TerrainTestSupport.*;

/**
 * Worldgen: desert well chest loot, datapack entries and biome modifiers.
 */
final class WorldgenTests {

    private WorldgenTests() {
    }

    /** Namespaces whose features this pack adds through AssortedLib's world gen helper. */
    private static final List<String> OURS = List.of(Constants.MOD_ID, "assortedcore");

    /** Every placed feature this mod adds, each under the same id as its configured feature. */
    private static final List<String> FEATURES = List.of("ore_randomite", "desert_well", "crop_field", "cactus_field", "sand_pit", "patch_saplings", "tree_stumps", "patch_melons");

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
        out.accept("structure_chest_loot_tables_roll_items", WorldgenTests::structureChestLootTablesRollItems);
        out.accept("worldgen_datapack_entries_resolve", WorldgenTests::worldgenDatapackEntriesResolve);
        out.accept("mod_features_are_attached_to_biomes", WorldgenTests::modFeaturesAreAttachedToBiomes);
        out.accept("config_rarity_gates_the_new_features", WorldgenTests::configRarityGatesTheNewFeatures);
        out.accept("vanilla_desert_well_is_replaced", WorldgenTests::vanillaDesertWellIsReplaced);
        out.accept("added_features_keep_one_order_across_loaders", WorldgenTests::addedFeaturesKeepOneOrderAcrossLoaders);
    }

    /**
     * Every desert well chest loot table exists and rolls something. {@code setLootTable} on a
     * missing table is silent, and the chest simply comes up empty.
     */
    private static void structureChestLootTablesRollItems(GameTestHelper helper) {
        ReloadableServerRegistries.Holder registries = helper.getLevel().getServer().reloadableRegistries();
        // CHEST requires an origin - a table that rolls fine in datagen throws without one.
        LootParams params = new LootParams.Builder(helper.getLevel())
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(helper.absolutePos(CENTRE)))
                .create(LootContextParamSets.CHEST);

        List<ResourceKey<LootTable>> tables = List.of(
                TerrainLootTables.CHESTS_DESERT_WELL_10,
                TerrainLootTables.CHESTS_DESERT_WELL_15,
                TerrainLootTables.CHESTS_DESERT_WELL_20,
                TerrainLootTables.CHESTS_DESERT_WELL_25,
                TerrainLootTables.CHESTS_DESERT_WELL_30);

        for (ResourceKey<LootTable> key : tables) {
            LootTable table = registries.getLootTable(key);
            helper.assertFalse(table == LootTable.EMPTY, "loot table " + key.identifier() + " does not exist");

            // Every one of these has a pool of guaranteed rolls with no empty entry in it, so a
            // fixed seed makes "rolled nothing" a real failure rather than bad luck.
            helper.assertFalse(table.getRandomItems(params, 42L).isEmpty(), "loot table " + key.identifier() + " rolled nothing");
        }

        helper.succeed();
    }

    /**
     * Every configured and placed feature is in the loaded datapack. Each can go missing silently: a
     * misrouted datagen run prunes its json.
     */
    private static void worldgenDatapackEntriesResolve(GameTestHelper helper) {
        RegistryAccess registries = helper.getLevel().registryAccess();
        List<String> problems = new ArrayList<>();

        Registry<ConfiguredFeature<?, ?>> configured = registries.lookupOrThrow(Registries.CONFIGURED_FEATURE);
        Registry<PlacedFeature> placed = registries.lookupOrThrow(Registries.PLACED_FEATURE);
        for (String name : FEATURES) {
            Identifier id = Identifier.fromNamespaceAndPath(Constants.MOD_ID, name);
            if (configured.getValue(id) == null) {
                problems.add("configured feature " + id + " is not in the registry");
            }
            if (placed.getValue(id) == null) {
                problems.add("placed feature " + id + " is not in the registry");
            }
        }

        helper.assertTrue(problems.isEmpty(), "worldgen datapack entries missing: " + String.join("; ", problems));
        helper.succeed();
    }

    /**
     * The config rarity modifier is registered, every part name it knows reads back a rarity, and the
     * placed features that are meant to be config gated carry it. A placed feature that lost the
     * modifier would silently generate in every chunk, and one naming a part nothing registered
     * would silently generate in none.
     */
    private static void configRarityGatesTheNewFeatures(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();

        Identifier rarityType = BuiltInRegistries.PLACEMENT_MODIFIER_TYPE.getKey(TerrainPlacements.CONFIG_RARITY.get());
        if (rarityType == null) {
            problems.add("the config_rarity placement modifier type is not registered, so its placed features cannot load");
        }

        for (String part : List.of(TerrainPlacements.Parts.DESERT_WELL, TerrainPlacements.Parts.CROP_FIELD, TerrainPlacements.Parts.SAPLING,
                TerrainPlacements.Parts.TREE_STUMP, TerrainPlacements.Parts.CACTUS_FIELD, TerrainPlacements.Parts.SAND_PIT, TerrainPlacements.Parts.MELON)) {
            if (TerrainPlacements.rarity(part) <= 0) {
                problems.add("part " + part + " reads back no rarity, so it can never generate");
            }
        }

        Registry<PlacedFeature> placed = helper.getLevel().registryAccess().lookupOrThrow(Registries.PLACED_FEATURE);
        for (String name : List.of("desert_well", "crop_field", "cactus_field", "sand_pit", "patch_saplings", "tree_stumps", "patch_melons")) {
            Identifier id = Identifier.fromNamespaceAndPath(Constants.MOD_ID, name);
            PlacedFeature feature = placed.getValue(id);
            if (feature == null) {
                problems.add("placed feature " + id + " is not in the registry");
            } else if (feature.placement().stream().noneMatch(modifier -> modifier instanceof ConfigRarityFilter)) {
                problems.add("placed feature " + id + " has no config rarity filter, so the config cannot turn it off");
            }
        }

        helper.assertTrue(problems.isEmpty(), "config gated features: " + String.join("; ", problems));
        helper.succeed();
    }

    /**
     * With the config at its default, the desert has this mod's well and not vanilla's. The removal
     * goes through a different loader API on each side, like the additions.
     */
    private static void vanillaDesertWellIsReplaced(GameTestHelper helper) {
        helper.assertTrue(TerrainCommonMod.COMMON_CONFIG.desertWellReplaceVanilla.get(), "desertWells.replaceVanilla is off in the test config");

        Biome desert = helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME).getValueOrThrow(Biomes.DESERT);
        helper.assertFalse(hasFeature(desert, TerrainBiomeModifiers.VANILLA_DESERT_WELL), "the desert still has vanilla's desert well");
        helper.assertTrue(hasFeature(desert, TerrainGenData.DESERT_WELL_KEY), "the desert lost this mod's desert well as well");
        helper.succeed();
    }

    /**
     * Every placed feature is attached to a biome. {@code TerrainBiomeModifiers} does that through a
     * different loader API on each side, so this is where the two could disagree.
     */
    private static void modFeaturesAreAttachedToBiomes(GameTestHelper helper) {
        Registry<Biome> biomes = helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME);
        List<String> problems = new ArrayList<>();

        for (String name : FEATURES) {
            Identifier id = Identifier.fromNamespaceAndPath(Constants.MOD_ID, name);
            int biomeCount = 0;
            for (Biome biome : biomes) {
                if (hasFeature(biome, id)) {
                    biomeCount++;
                }
            }
            if (biomeCount == 0) {
                problems.add(id + " is attached to no biome at all");
            }
        }

        helper.assertTrue(problems.isEmpty(), "features that can never generate: " + String.join("; ", problems));
        helper.succeed();
    }
}
