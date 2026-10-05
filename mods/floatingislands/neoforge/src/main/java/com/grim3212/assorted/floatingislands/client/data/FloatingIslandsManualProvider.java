package com.grim3212.assorted.floatingislands.client.data;

import com.grim3212.assorted.lib.data.LibManualProvider;
import com.grim3212.assorted.floatingislands.Constants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

/**
 * This part's chapter of the Assorted World section, which every part shares; the explicit chapter
 * orders keep the section's order whichever parts are installed.
 */
public class FloatingIslandsManualProvider extends LibManualProvider {

    public FloatingIslandsManualProvider(PackOutput output) {
        super(output, Constants.MOD_ID, Constants.FAMILY_ID);
    }

    @Override
    protected void addChapters() {
        ChapterBuilder floatingIslands = this.chapter("floating_islands", 5);
        floatingIslands.image("floating_islands", picture("floating_islands"), 119, 104);
    }

    /** The screenshots under {@code textures/gui/manual}, sized to leave room for the text below. */
    private static Identifier picture(String name) {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/manual/" + name + ".png");
    }
}
