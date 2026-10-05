package com.grim3212.assorted.terrain.client.data;

import com.grim3212.assorted.lib.data.LibLanguageProvider;
import com.grim3212.assorted.terrain.Constants;
import net.minecraft.data.PackOutput;

/**
 * Generates the en_us.json of this mod. A block whose name is its id in title case needs no line here (see
 * {@link LibLanguageProvider}); the manual's keys are the Assorted World section's, which every part shares.
 */
public class TerrainLanguageProvider extends LibLanguageProvider {

    /** A blank line between paragraphs; the manual splits its text the way the font does. */
    private static final String BREAK = "\n\n";

    public TerrainLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void addNames() {
        this.add("itemGroup.assortedworld", "Assorted World");

        this.add("tag.item.c.ores.randomite", "Randomite Ores");

        this.addManual();
    }

    /** This part's chapters of the Assorted World section, and the section's own title, which every part writes the same. */
    private void addManual() {
        this.add("manual.assortedworld.title", "Assorted World");
        this.add("manual.assortedworld.description",
                "What this mod buries, builds and grows out in the world, and what is worth going to look for.");

        this.addRandomiteChapter();
        this.addWorldGenChapter();
    }

    private void addRandomiteChapter() {
        this.add("manual.assortedworld.chapter.randomite", "Randomite");

        this.add("manual.assortedworld.chapter.randomite.ore.title", "Randomite Ore");
        this.add("manual.assortedworld.chapter.randomite.ore",
                "Randomite looks like one more ore in the wall and drops as any of them. What it gives is "
                        + "decided when it breaks, not when it generates, so two blocks side by side rarely "
                        + "drop the same.");

        this.add("manual.assortedworld.chapter.randomite.finding.title", "Finding Randomite");
        this.add("manual.assortedworld.chapter.randomite.finding",
                "It generates all over and looks enough like the other ores you could mistake it for another.");
    }

    private void addWorldGenChapter() {
        this.add("manual.assortedworld.chapter.world_gen", "Out in the World");

        this.add("manual.assortedworld.chapter.world_gen.desert_wells.title", "Desert Wells");
        this.add("manual.assortedworld.chapter.world_gen.desert_wells",
                "Desert wells are not just a well. The shaft under them runs ten to thirty "
                        + "blocks down through the sandstone to a chest, and the deeper the swim the "
                        + "better what is waiting at the bottom.");

        this.add("manual.assortedworld.chapter.world_gen.world_gen_expanded.title", "A Fuller World");
        this.add("manual.assortedworld.chapter.world_gen.world_gen_expanded",
                "Wild fields of wheat, carrots, potatoes or beetroot grow on the plains, watered by "
                        + "holes dug in among the rows. Melons come up in loose patches around as well. "
                        + "Stray saplings and the stumps of felled trees are scattered through the woods."
                        + BREAK
                        + "The desert has more variety with fields of cactus and pits sunk into the sand in terraces.");
    }
}
