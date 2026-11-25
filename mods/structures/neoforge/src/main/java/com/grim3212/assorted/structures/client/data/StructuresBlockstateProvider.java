package com.grim3212.assorted.structures.client.data;

import com.grim3212.assorted.structures.Constants;
import com.grim3212.assorted.structures.common.block.StructuresBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/** Block states, block models and the runes' item models, which are all this mod has. */
public class StructuresBlockstateProvider extends BlockStateProvider {

    public StructuresBlockstateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Assorted Structures block states";
    }

    @Override
    protected void registerStatesAndModels() {
        // Every rune texture is fully opaque, so the models stay on the solid layer.
        for (Block rune : StructuresBlocks.runeBlocks()) {
            this.simpleBlockWithItem(rune, this.cubeAll(rune));
        }
    }
}
