package com.grim3212.assorted.terrain.client.data;

import com.grim3212.assorted.terrain.Constants;
import com.grim3212.assorted.terrain.common.block.TerrainBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/** Block states, block models and the block items' models, which is every model this mod has. */
public class TerrainBlockstateProvider extends BlockStateProvider {

    public TerrainBlockstateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Assorted Terrain block states";
    }

    @Override
    protected void registerStatesAndModels() {
        this.cube(TerrainBlocks.RANDOMITE_ORE.get());
        this.cube(TerrainBlocks.DEEPSLATE_RANDOMITE_ORE.get());
    }

    /** A cube with the block's own texture on every face, and an item model that is the block's. */
    private void cube(Block block) {
        this.simpleBlockWithItem(block, this.cubeAll(block));
    }
}
