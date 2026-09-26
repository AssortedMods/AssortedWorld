package com.grim3212.assorted.plants.client.data;

import com.grim3212.assorted.lib.data.LibLanguageProvider;
import com.grim3212.assorted.plants.Constants;
import net.minecraft.data.PackOutput;

/**
 * Generates the en_us.json of this mod. A block or item whose name is its id in title case needs no line here (see
 * {@link LibLanguageProvider}); the manual's keys are the Assorted World section's, which every part shares.
 */
public class PlantsLanguageProvider extends LibLanguageProvider {

    public PlantsLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void addNames() {
        this.add("itemGroup.assortedworld", "Assorted World");

        this.addManual();
    }

    /** This part's chapter of the Assorted World section, and the section's own title, which every part writes the same. */
    private void addManual() {
        this.add("manual.assortedworld.title", "Assorted World");
        this.add("manual.assortedworld.description",
                "What this mod buries, builds and grows out in the world, and what is worth going to look for.");

        this.add("manual.assortedworld.chapter.plants", "Plants");

        this.add("manual.assortedworld.chapter.plants.gunpowder_reed.title", "Gunpowder Reed");
        this.add("manual.assortedworld.chapter.plants.gunpowder_reed",
                "Gunpowder reed grows like sugar cane and glows faintly. Plant it once and it keeps growing, "
                        + "which turns a one off pile of gunpowder into a supply.");

        this.add("manual.assortedworld.chapter.plants.gunpowder.title", "Back to Gunpowder");
        this.add("manual.assortedworld.chapter.plants.gunpowder",
                "A reed breaks back down into gunpowder one for one.");

        this.add("manual.assortedworld.chapter.plants.glowstone_seeds.title", "Glowstone Seeds");
        this.add("manual.assortedworld.chapter.plants.glowstone_seeds",
                "A glowstone seed takes on the underside of a netherrack ceiling and ripens before bursting into a blob of glowstone.");
    }
}
