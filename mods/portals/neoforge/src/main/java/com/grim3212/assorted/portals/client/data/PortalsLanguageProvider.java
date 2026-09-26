package com.grim3212.assorted.portals.client.data;

import com.grim3212.assorted.lib.data.LibLanguageProvider;
import com.grim3212.assorted.portals.Constants;
import net.minecraft.data.PackOutput;

/**
 * Generates the en_us.json of this mod. A block, item or entity whose name is its id in title case needs
 * no line here (see {@link LibLanguageProvider}); the manual's keys are the Assorted World section's, which every part shares.
 */
public class PortalsLanguageProvider extends LibLanguageProvider {

    /** A blank line between paragraphs; the manual splits its text the way the font does. */
    private static final String BREAK = "\n\n";

    public PortalsLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void addNames() {
        this.add("itemGroup.assortedworld", "Assorted World");

        this.add("tag.item.c.ores.void_crystal", "Void Crystal Ores");

        this.addManual();
    }

    /** This part's chapter of the Assorted World section, and the section's own title, which every part writes the same. */
    private void addManual() {
        this.add("manual.assortedworld.title", "Assorted World");
        this.add("manual.assortedworld.description",
                "What this mod buries, builds and grows out in the world, and what is worth going to look for.");

        this.add("manual.assortedworld.chapter.end_portals", "End Portals");

        this.add("manual.assortedworld.chapter.end_portals.void_crystal.title", "Void Crystal");
        this.add("manual.assortedworld.chapter.end_portals.void_crystal",
                "Void crystal is found on the End islands. It is quite rare and needs an iron pickaxe level or higher to break.");

        this.add("manual.assortedworld.chapter.end_portals.frame.title", "Void Portal Frame");
        this.add("manual.assortedworld.chapter.end_portals.frame",
                "A void portal frame is an end portal frame you can make yourself. They are very slow to break as you would expect.");

        this.add("manual.assortedworld.chapter.end_portals.building.title", "Building a Portal");
        this.add("manual.assortedworld.chapter.end_portals.building",
                "You can place them in an opening of 3x3 to 9x9 on a floor, wall or ceiling. If they are all facing the same way you can construct a End Portal of your own using a Void Striker.");

        this.add("manual.assortedworld.chapter.end_portals.flames.title", "Ender Flames");
        this.add("manual.assortedworld.chapter.end_portals.flames",
                "Strike any frame of a finished ring with a void striker and the end portal will open. Ender flames set inside a closed Void Portal Frame ring will also open the portal."
                        + BREAK
                        + "On end stone the flames will burn forever.");
    }
}
