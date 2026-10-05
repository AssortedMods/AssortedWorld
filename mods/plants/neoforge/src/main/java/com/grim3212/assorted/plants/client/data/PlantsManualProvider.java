package com.grim3212.assorted.plants.client.data;

import com.grim3212.assorted.lib.data.LibManualProvider;
import com.grim3212.assorted.plants.Constants;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import net.minecraft.data.PackOutput;

/**
 * This part's chapter of the Assorted World section, which every part shares; the explicit chapter order keeps the
 * section's order whichever parts are installed. Every block and item has to open a page, or the provider refuses.
 */
public class PlantsManualProvider extends LibManualProvider {

    public PlantsManualProvider(PackOutput output) {
        super(output, Constants.MOD_ID, Constants.FAMILY_ID);
    }

    @Override
    protected void addChapters() {
        ChapterBuilder plants = this.chapter("plants", 3);
        plants.recipes("gunpowder_reed", PlantsBlocks.GUNPOWDER_REED.get()).opens(PlantsBlocks.GUNPOWDER_REED.get());
        plants.recipesById("gunpowder", recipeId("gunpowder"));
        plants.recipes("glowstone_seeds", PlantsBlocks.GLOWSTONE_SEEDS.get()).opens(PlantsBlocks.GLOWSTONE_SEEDS.get());
    }
}
