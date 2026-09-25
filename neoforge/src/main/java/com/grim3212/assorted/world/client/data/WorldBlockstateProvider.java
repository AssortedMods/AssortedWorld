package com.grim3212.assorted.world.client.data;

import com.grim3212.assorted.world.Constants;
import com.grim3212.assorted.world.common.block.WorldBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Block states and block models. This owns every block and block item; {@link
 * WorldItemModelProvider} owns the rest, so the two never write the same file.
 */
public class WorldBlockstateProvider extends ModelProvider {

    private static final TextureSlot EYE = TextureSlot.create("eye");
    // Vanilla's end_portal_frame_filled has no parent, so it carries no display transforms for the item.
    private static final ModelTemplate VOID_PORTAL_FRAME_TEMPLATE = new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "block/template_void_portal_frame")), Optional.empty(), TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM, EYE);

    public WorldBlockstateProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    public String getName() {
        return "Assorted World block states";
    }

    /**
     * Only the block items belong here; everything else is {@link WorldItemModelProvider}'s, so the
     * two providers never write the same file.
     */
    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return super.getKnownItems().filter(holder -> holder.value() instanceof BlockItem);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        blockModels.createTrivialCube(WorldBlocks.RANDOMITE_ORE.get());
        blockModels.createTrivialCube(WorldBlocks.DEEPSLATE_RANDOMITE_ORE.get());

        for (Block rune : WorldBlocks.runeBlocks()) {
            blockModels.createTrivialCube(rune);
        }

        blockModels.createTrivialCube(WorldBlocks.VOID_CRYSTAL_ORE.get());
        voidPortalFrame(blockModels, WorldBlocks.VOID_PORTAL_FRAME.get());
        enderFlames(blockModels, WorldBlocks.ENDER_FLAMES.get());
        // Drawn by its block entity; the model only gives it vanilla's portal particle.
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(WorldBlocks.VOID_PORTAL.get(), BlockModelGenerators.plainVariant(Identifier.withDefaultNamespace("block/end_portal"))));

        cross(blockModels, WorldBlocks.GUNPOWDER_REED.get());
        // Every growth step looks the same, as it did in GrimPack
        cross(blockModels, WorldBlocks.GLOWSTONE_SEEDS.get());
    }

    /** Vanilla's filled frame on an obsidian frame of our own, eye toward its facing. */
    private void voidPortalFrame(BlockModelGenerators blockModels, Block block) {
        TextureMapping textures = new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.OBSIDIAN))
                .put(EYE, TextureMapping.getBlockTexture(Blocks.END_PORTAL_FRAME, "_eye"));
        MultiVariant model = BlockModelGenerators.plainVariant(VOID_PORTAL_FRAME_TEMPLATE.create(block, textures, blockModels.modelOutput));
        // The model's eye points up, as an amethyst cluster's tip does.
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model).with(BlockModelGenerators.ROTATIONS_COLUMN_WITH_FACING));
    }

    /** Soul fire's layout: floor and four sides always drawn, from the two animated textures. */
    private void enderFlames(BlockModelGenerators blockModels, Block block) {
        MultiVariant floor = blockModels.createFloorFireModels(block);
        MultiVariant side = blockModels.createSideFireModels(block);
        blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                .with(floor)
                .with(side)
                .with(side.with(BlockModelGenerators.Y_ROT_90))
                .with(side.with(BlockModelGenerators.Y_ROT_180))
                .with(side.with(BlockModelGenerators.Y_ROT_270)));
    }

    /**
     * A plant drawn as two crossed quads: the {@code cross} template baked against the block
     * texture. The texture's alpha makes it cutout.
     */
    private void cross(BlockModelGenerators blockModels, Block block) {
        MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.CROSS.create(block, TextureMapping.cross(block), blockModels.modelOutput));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model));
    }
}
