package com.grim3212.assorted.structures.client.data;

import com.grim3212.assorted.structures.Constants;
import com.grim3212.assorted.structures.common.block.StructuresBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

/** Block states, block models and the runes' item models, which are all this mod has. */
public class StructuresBlockstateProvider extends ModelProvider {

    public StructuresBlockstateProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    public String getName() {
        return "Assorted Structures block states";
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (Block rune : StructuresBlocks.runeBlocks()) {
            blockModels.createTrivialCube(rune);
        }
    }
}
