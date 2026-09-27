package com.grim3212.assorted.structures.client.data;

import com.grim3212.assorted.lib.data.LibLanguageProvider;
import com.grim3212.assorted.structures.Constants;
import net.minecraft.data.PackOutput;

/**
 * Generates the en_us.json of this mod. A block whose name is its id in title case needs no line here (see
 * {@link LibLanguageProvider}); the manual's keys are the Assorted World section's, which every part shares.
 */
public class StructuresLanguageProvider extends LibLanguageProvider {

    public StructuresLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void addNames() {
        this.add("itemGroup.assortedworld", "Assorted World");

        this.add("block." + Constants.MOD_ID + ".ur_rune", "\u00A73UR\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".eoh_rune", "\u00A7dEOH\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".hagel_rune", "\u00A73HAGEL\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".eolh_rune", "\u00A73EOLH\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".cen_rune", "\u00A73CEN\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".ger_rune", "\u00A73GER\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".rad_rune", "\u00A73RAD\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".is_rune", "\u00A7dIS\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".daeg_rune", "\u00A7dDAEG\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".tyr_rune", "\u00A7dTYR\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".beorc_rune", "\u00A73BEORC\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".lagu_rune", "\u00A73LAGU\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".odal_rune", "\u00A73ODAL\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".nyd_rune", "\u00A7dNYD\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".thorn_rune", "\u00A7dTHORN\u00A7r Rune");
        this.add("block." + Constants.MOD_ID + ".os_rune", "\u00A7dOS\u00A7r Rune");

        this.add("tag.item." + Constants.MOD_ID + ".runes", "Runes");

        this.addManual();
    }

    /** This part's chapters of the Assorted World section, and the section's own title, which every part writes the same. */
    private void addManual() {
        this.add("manual.assortedworld.title", "Assorted World");
        this.add("manual.assortedworld.description",
                "What this mod buries, builds and grows out in the world, and what is worth going to look for.");

        this.addRunesChapter();
        this.addStructuresChapter();
    }

    private void addRunesChapter() {
        this.add("manual.assortedworld.chapter.runes", "Runes");

        this.add("manual.assortedworld.chapter.runes.runes.title", "Runes");
        this.add("manual.assortedworld.chapter.runes.runes",
                "Runes are carved blocks that hold an effect and hand it to whoever stands or uses them. There are "
                        + "sixteen, and they are not crafted: every one in the world was placed by a Ancient Structure.");
    }

    private void addStructuresChapter() {
        this.add("manual.assortedworld.chapter.structures", "Structures");

        this.add("manual.assortedworld.chapter.structures.ruins.title", "Ruins");
        this.add("manual.assortedworld.chapter.structures.ruins",
                "Ruins are what is left of a civilisation that was here first. Most have decayed down to a "
                        + "footprint and a few standing walls, but they still hold treasures worth exploring.");

        this.add("manual.assortedworld.chapter.structures.spires.title", "Spires");
        this.add("manual.assortedworld.chapter.structures.spires",
                "Spires are towers of stone reaching for the sky.");

        this.add("manual.assortedworld.chapter.structures.fountains.title", "Fountains");
        this.add("manual.assortedworld.chapter.structures.fountains",
                "Fountains are large stone structures surrounded by flowing water. Watch out for what is inside.");

        this.add("manual.assortedworld.chapter.structures.pyramids.title", "Pyramids");
        this.add("manual.assortedworld.chapter.structures.pyramids",
                "Pyramids are big decaying structures lost to time in the desert.");

        this.add("manual.assortedworld.chapter.structures.sandstone_pillars.title", "Sandstone Pillars");
        this.add("manual.assortedworld.chapter.structures.sandstone_pillars",
                "Sandstone pillars stand out of the dunes in the desert. Some hide suspicious sand or a rune worth digging for.");

        this.add("manual.assortedworld.chapter.structures.snowballs.title", "Snowballs");
        this.add("manual.assortedworld.chapter.structures.snowballs",
                "Snowballs are enormous stacked drifts of snow and ice that look a great deal like a snowman left to grow.");

        this.add("manual.assortedworld.chapter.structures.water_domes.title", "Water Domes");
        this.add("manual.assortedworld.chapter.structures.water_domes",
                "Water domes sit on the sea floor, ribbed with a material that varies from dome to dome.");
    }
}
