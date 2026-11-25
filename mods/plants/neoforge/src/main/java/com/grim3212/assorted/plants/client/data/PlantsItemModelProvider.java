package com.grim3212.assorted.plants.client.data;

import com.grim3212.assorted.plants.Constants;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Item models for the two plants, whose items are flat sprites rather than their block models. {@link
 * PlantsBlockstateProvider} models the blocks.
 */
public class PlantsItemModelProvider extends ItemModelProvider {

    public PlantsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Assorted Plants item models";
    }

    @Override
    protected void registerModels() {
        // The items are flat sprites of their own item/<name> textures, not the block models.
        this.basicItem(PlantsBlocks.GUNPOWDER_REED.get().asItem());
        this.basicItem(PlantsBlocks.GLOWSTONE_SEEDS.get().asItem());
    }
}
