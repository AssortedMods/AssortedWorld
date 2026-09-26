package com.grim3212.assorted.terrain.client.data;

import com.grim3212.assorted.terrain.Constants;
import com.grim3212.assorted.terrain.common.block.TerrainBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

/** Block states, block models and the block items' models, which is every model this mod has. */
public class TerrainBlockstateProvider extends ModelProvider {

    public TerrainBlockstateProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    public String getName() {
        return "Assorted Terrain block states";
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(TerrainBlocks.RANDOMITE_ORE.get());
        blockModels.createTrivialCube(TerrainBlocks.DEEPSLATE_RANDOMITE_ORE.get());
    }
}
