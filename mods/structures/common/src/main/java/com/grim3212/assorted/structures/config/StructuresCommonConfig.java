package com.grim3212.assorted.structures.config;

import com.grim3212.assorted.lib.config.ConfigurationType;
import com.grim3212.assorted.lib.config.IConfigurationBuilder;
import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.structures.Constants;

import java.util.function.Supplier;

public class StructuresCommonConfig {

    public final Supplier<Double> runeChance;
    public final Supplier<Integer> spireRadius;
    public final Supplier<Integer> spireHeight;
    public final Supplier<Double> deathSpireChance;
    public final Supplier<Integer> waterDomePieceMod;
    public final Supplier<Double> waterDomeChestChance;
    public final Supplier<Double> waterDomeSuspiciousGravelChance;

    public final Supplier<Integer> sandstonePillarRarity;
    public final Supplier<Double> sandstonePillarSuspiciousSandChance;
    public final Supplier<Double> sandstonePillarRuneChance;

    /** Every rarity is read through {@code ConfigRarityFilter}, where 0 turns the feature off. */
    private static final String RARITY = "One in this many chunks gets one. Larger is rarer, and 0 turns them off entirely.";

    public StructuresCommonConfig() {
        final IConfigurationBuilder builder = Services.CONFIG.createBuilder(ConfigurationType.NOT_SYNCED, Constants.MOD_ID + "-common");

        runeChance = builder.defineDouble("common.runeChance", 0.15D, 0, 1, "Set this to the chance that a rune will generate inside a ruin. The four structures always generate exactly one rune.");
        waterDomePieceMod = builder.defineInteger("structures.waterDomePieceMod", 8, 0, 100, "This value determines how many extra pieces to a water dome are added.");
        waterDomeChestChance = builder.defineDouble("structures.waterDomeChestChance", 0.6D, 0, 1, "Set this to the chance that a water dome generates loot chests. A dome that does gets 1 or 2 of them, filled according to the material the dome is ribbed with.");
        waterDomeSuspiciousGravelChance = builder.defineDouble("structures.waterDomeSuspiciousGravelChance", 0.5D, 0, 1, "Set this to the chance that a water dome has suspicious gravel on its floor. A dome that does gets 2 to 5 pieces, holding what an ocean ruin's gravel holds.");

        spireRadius = builder.defineInteger("spires.spireRadius", 7, 0, 100, "Set this to the radius you would like for the spires.");
        spireHeight = builder.defineInteger("spires.spireHeight", 40, 0, 100, "Set this to the height you would like for spires.");
        deathSpireChance = builder.defineDouble("spires.deathSpireChance", 0.001D, 0, 1, "Set this to the chance for a death spire to generate.");

        sandstonePillarRarity = builder.defineInteger("worldGenExpanded.sandstonePillarRarity", 325, 0, 10000, RARITY);
        sandstonePillarSuspiciousSandChance = builder.defineDouble("worldGenExpanded.sandstonePillarSuspiciousSandChance", 0.35D, 0, 1, "Set this to the chance that a sandstone pillar has a block of suspicious sand worked into its plinth, holding what a desert pyramid's sand holds.");
        sandstonePillarRuneChance = builder.defineDouble("worldGenExpanded.sandstonePillarRuneChance", 0.15D, 0, 1, "Set this to the chance that a sandstone pillar has a single rune built into the middle of its shaft.");

        builder.setup();
    }
}
