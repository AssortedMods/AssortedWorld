package com.grim3212.assorted.structures.client.data;

import com.grim3212.assorted.lib.data.LibManualProvider;
import com.grim3212.assorted.structures.Constants;
import com.grim3212.assorted.structures.Family;
import com.grim3212.assorted.structures.common.block.StructuresBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

/**
 * This part's chapters of the Assorted World section, which every part shares; the explicit chapter orders keep the
 * section's order whichever parts are installed. Every block has to open a page, or the provider refuses to generate.
 */
public class StructuresManualProvider extends LibManualProvider {

    public StructuresManualProvider(PackOutput output) {
        super(output, Constants.MOD_ID, Family.ID);
    }

    @Override
    protected void addChapters() {
        this.section(Family.MANUAL_ORDER, Family.ICONS.toArray(Identifier[]::new));

        Block[] runes = StructuresBlocks.runeBlocks();
        ChapterBuilder runeChapter = this.chapter("runes", 1);
        runeChapter.items("runes", runes).opens(runes);

        ChapterBuilder structures = this.chapter("structures", 2);
        structures.image("ruins", picture("ruins"), 113, 104);
        structures.image("spires", picture("spires"), 79, 104);
        structures.image("fountains", picture("fountains"), 98, 104);
        structures.image("pyramids", picture("pyramids"), 113, 104);
        structures.image("snowballs", picture("snowballs"), 58, 104);
        structures.image("water_domes", picture("water_domes"), 128, 95);
    }

    /** The screenshots under {@code textures/gui/manual}, sized to leave room for the text below. */
    private static Identifier picture(String name) {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/manual/" + name + ".png");
    }
}
