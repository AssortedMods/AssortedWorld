package com.grim3212.assorted.portals.client.data;

import com.grim3212.assorted.portals.Constants;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import com.grim3212.assorted.portals.common.block.VoidPortalFrameBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Block states, block models and the block items' models. {@link PortalsItemModelProvider} owns every
 * other item, so the two never write the same file.
 */
public class PortalsBlockstateProvider extends BlockStateProvider {

    /** 1.21.1 draws a block solid unless its model names a layer; the flames are cut out of their textures. */
    private static final String CUTOUT = "minecraft:cutout";

    public PortalsBlockstateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Assorted Portals block states";
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlockWithItem(PortalsBlocks.VOID_CRYSTAL_ORE.get(), this.cubeAll(PortalsBlocks.VOID_CRYSTAL_ORE.get()));
        this.voidPortalFrame(PortalsBlocks.VOID_PORTAL_FRAME.get());
        this.enderFlames(PortalsBlocks.ENDER_FLAMES.get());
        // Drawn by its block entity; the model only gives it vanilla's portal particle.
        this.simpleBlock(PortalsBlocks.VOID_PORTAL.get(), this.models().getExistingFile(this.mcLoc("block/end_portal")));
    }

    /**
     * Vanilla's filled frame on an obsidian frame of our own, eye toward its facing. Vanilla's
     * end_portal_frame_filled has no parent, so it carries no display transforms for the item.
     */
    private void voidPortalFrame(Block block) {
        String name = this.name(block);
        ModelFile model = this.models().withExistingParent(name, this.modLoc("block/template_void_portal_frame"))
                .texture("particle", this.modLoc("block/" + name + "_side"))
                .texture("top", this.modLoc("block/" + name + "_top"))
                .texture("side", this.modLoc("block/" + name + "_side"))
                .texture("bottom", this.mcLoc("block/obsidian"))
                .texture("eye", this.mcLoc("block/end_portal_frame_eye"));

        // The model's eye points up, as an amethyst cluster's tip does.
        this.getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(VoidPortalFrameBlock.FACING);
            ConfiguredModel.Builder<?> variant = ConfiguredModel.builder().modelFile(model);
            switch (facing) {
                case DOWN -> variant.rotationX(180);
                case NORTH -> variant.rotationX(90);
                case SOUTH -> variant.rotationX(90).rotationY(180);
                case WEST -> variant.rotationX(90).rotationY(270);
                case EAST -> variant.rotationX(90).rotationY(90);
                case UP -> {
                }
            }
            return variant.build();
        });
        this.simpleBlockItem(block, model);
    }

    /** Soul fire's layout: floor and four sides always drawn, from the two animated textures. */
    private void enderFlames(Block block) {
        String name = this.name(block);
        ModelFile floor0 = this.fire(name + "_floor0", "template_fire_floor", name + "_0");
        ModelFile floor1 = this.fire(name + "_floor1", "template_fire_floor", name + "_1");
        ModelFile side0 = this.fire(name + "_side0", "template_fire_side", name + "_0");
        ModelFile side1 = this.fire(name + "_side1", "template_fire_side", name + "_1");
        ModelFile sideAlt0 = this.fire(name + "_side_alt0", "template_fire_side_alt", name + "_0");
        ModelFile sideAlt1 = this.fire(name + "_side_alt1", "template_fire_side_alt", name + "_1");

        var builder = this.getMultipartBuilder(block);
        builder.part().modelFile(floor0).nextModel().modelFile(floor1).addModel().end();
        for (int rotation = 0; rotation < 360; rotation += 90) {
            builder.part()
                    .modelFile(side0).rotationY(rotation).nextModel()
                    .modelFile(side1).rotationY(rotation).nextModel()
                    .modelFile(sideAlt0).rotationY(rotation).nextModel()
                    .modelFile(sideAlt1).rotationY(rotation).addModel().end();
        }
    }

    private BlockModelBuilder fire(String model, String template, String texture) {
        return this.models().withExistingParent(model, this.mcLoc("block/" + template)).texture("fire", this.modLoc("block/" + texture)).renderType(CUTOUT);
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
