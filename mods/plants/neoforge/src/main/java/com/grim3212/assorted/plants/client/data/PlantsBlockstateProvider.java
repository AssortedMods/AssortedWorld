package com.grim3212.assorted.plants.client.data;

import com.grim3212.assorted.plants.Constants;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Block states and block models. {@link PlantsItemModelProvider} owns the item models, which are
 * flat sprites, so the two never write the same file.
 */
public class PlantsBlockstateProvider extends BlockStateProvider {

    public PlantsBlockstateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Assorted Plants block states";
    }

    @Override
    protected void registerStatesAndModels() {
        this.cross(PlantsBlocks.GUNPOWDER_REED.get());
        // Every growth step looks the same, as it did in GrimPack
        this.cross(PlantsBlocks.GLOWSTONE_SEEDS.get());
    }

    /**
     * A plant drawn as two crossed quads: the {@code cross} template baked against the block
     * texture, on the cutout layer its transparent pixels need.
     */
    private void cross(Block block) {
        this.simpleBlock(block, this.models().cross(this.name(block), this.blockTexture(block)).renderType("cutout"));
    }

    private String name(Block block) {
        return this.blockTexture(block).getPath().substring("block/".length());
    }
}
