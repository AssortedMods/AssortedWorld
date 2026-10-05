package com.grim3212.assorted.terrain.config;

import com.grim3212.assorted.lib.config.ConfigurationType;
import com.grim3212.assorted.lib.config.IConfigurationBuilder;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.terrain.Constants;

import java.util.function.Supplier;

public class TerrainCommonConfig {

    public final Supplier<Integer> desertWellRarity;
    public final Supplier<Boolean> desertWellReplaceVanilla;

    public final Supplier<Integer> cropFieldRarity;
    public final Supplier<Integer> cropFieldSize;
    public final Supplier<Integer> saplingRarity;
    public final Supplier<Integer> treeStumpRarity;
    public final Supplier<Integer> cactusFieldRarity;
    public final Supplier<Integer> cactusFieldSize;
    public final Supplier<Integer> sandPitRarity;
    public final Supplier<Integer> sandPitSize;
    public final Supplier<Integer> melonRarity;

    /** Every rarity is read through {@code ConfigRarityFilter}, where 0 turns the feature off. */
    private static final String RARITY = "One in this many chunks gets one. Larger is rarer, and 0 turns them off entirely.";

    public TerrainCommonConfig() {
        final IConfigurationBuilder builder = Services.CONFIG.createBuilder(ConfigurationType.NOT_SYNCED, Constants.MOD_ID + "-common");

        desertWellRarity = builder.defineInteger("desertWells.rarity", 900, 0, 10000, RARITY + " These are the deep wells with a chest at the bottom.");
        desertWellReplaceVanilla = builder.defineBoolean("desertWells.replaceVanilla", true, "Set this to true to take vanilla's desert well out of every biome these wells generate in, so the two do not generate side by side. Needs a world reload. This is separate from rarity, which is all that decides whether these wells generate: off and rarity 0 leaves a desert with vanilla's wells only, on and rarity 0 leaves it with no wells at all, and off with any other rarity generates both.");

        cropFieldRarity = builder.defineInteger("worldGenExpanded.cropFieldRarity", 250, 0, 10000, RARITY);
        cropFieldSize = builder.defineInteger("worldGenExpanded.cropFieldSize", 6, 1, 11, "The radius in blocks of a crop field.");
        saplingRarity = builder.defineInteger("worldGenExpanded.saplingRarity", 300, 0, 10000, RARITY);
        treeStumpRarity = builder.defineInteger("worldGenExpanded.treeStumpRarity", 300, 0, 10000, RARITY);
        cactusFieldRarity = builder.defineInteger("worldGenExpanded.cactusFieldRarity", 350, 0, 10000, RARITY);
        cactusFieldSize = builder.defineInteger("worldGenExpanded.cactusFieldSize", 8, 1, 20, "The radius in blocks of a cactus field.");
        sandPitRarity = builder.defineInteger("worldGenExpanded.sandPitRarity", 400, 0, 10000, RARITY);
        sandPitSize = builder.defineInteger("worldGenExpanded.sandPitSize", 7, 2, 11, "The radius in blocks of a sand pit. Its depth follows from it.");
        melonRarity = builder.defineInteger("worldGenExpanded.melonRarity", 300, 0, 10000, RARITY);

        builder.setup();
    }
}
