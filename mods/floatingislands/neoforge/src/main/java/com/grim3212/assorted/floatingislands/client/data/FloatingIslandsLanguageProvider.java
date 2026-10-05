package com.grim3212.assorted.floatingislands.client.data;

import com.grim3212.assorted.lib.data.LibLanguageProvider;
import com.grim3212.assorted.floatingislands.Constants;
import net.minecraft.data.PackOutput;

/**
 * Generates the en_us.json of this mod. It has no blocks or items to name; the manual's keys are the
 * Assorted World section's, which every part shares.
 */
public class FloatingIslandsLanguageProvider extends LibLanguageProvider {

    /** A blank line between paragraphs; the manual splits its text the way the font does. */
    private static final String BREAK = "\n\n";

    public FloatingIslandsLanguageProvider(PackOutput output) {
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

        this.add("manual.assortedworld.chapter.floating_islands", "Floating Islands");

        this.add("manual.assortedworld.chapter.floating_islands.floating_islands.title", "Floating Islands");
        this.add("manual.assortedworld.chapter.floating_islands.floating_islands",
                "Islands hang high over the ground and take after the land below them."
                        + BREAK
                        + "Getting up to one is the whole problem. Nothing grows a bridge for you.");
    }
}
