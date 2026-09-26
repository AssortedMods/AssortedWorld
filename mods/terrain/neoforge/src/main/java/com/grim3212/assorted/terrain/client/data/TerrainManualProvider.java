package com.grim3212.assorted.terrain.client.data;

import com.grim3212.assorted.lib.data.LibManualProvider;
import com.grim3212.assorted.terrain.Constants;
import com.grim3212.assorted.terrain.Family;
import com.grim3212.assorted.terrain.common.block.TerrainBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

/**
 * This part's chapters of the Assorted World section, which every part shares; the explicit chapter orders keep the
 * section's order whichever parts are installed. Every block has to open a page, or the provider refuses to generate.
 */
public class TerrainManualProvider extends LibManualProvider {

    public TerrainManualProvider(PackOutput output) {
        super(output, Constants.MOD_ID, Family.ID);
    }

    @Override
    protected void addChapters() {
        this.section(Family.MANUAL_ORDER, Family.ICONS.toArray(Identifier[]::new));

        ChapterBuilder randomite = this.chapter("randomite", 0);
        randomite.items("ore", TerrainBlocks.RANDOMITE_ORE.get(), TerrainBlocks.DEEPSLATE_RANDOMITE_ORE.get())
                .every(50)
                .opens(TerrainBlocks.RANDOMITE_ORE.get(), TerrainBlocks.DEEPSLATE_RANDOMITE_ORE.get());
        randomite.image("finding", picture("randomite"), 94, 104);

        ChapterBuilder worldGen = this.chapter("world_gen", 6);
        worldGen.image("desert_wells", picture("desert_wells"), 126, 104);
        worldGen.image("world_gen_expanded", picture("world_gen_expanded"), 128, 101);
    }

    /** The screenshots under {@code textures/gui/manual}, sized to leave room for the text below. */
    private static Identifier picture(String name) {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/manual/" + name + ".png");
    }
}
