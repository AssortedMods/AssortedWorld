package com.grim3212.assorted.portals.client.blockentity;

import com.grim3212.assorted.portals.common.block.VoidPortalBlock;
import com.grim3212.assorted.portals.common.block.entity.VoidPortalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import org.joml.Matrix4f;

/**
 * Vanilla's end portal faces, drawn on the two sides of the portal's slab across its axis: where
 * vanilla draws them for the flat one, through the middle of the block for the upright ones,
 * matching {@link VoidPortalBlock}'s shapes. Vertex order follows vanilla's end portal renderer.
 */
public class VoidPortalRenderer implements BlockEntityRenderer<VoidPortalBlockEntity> {

    private static final float FLAT_BOTTOM = 0.375F;
    private static final float FLAT_TOP = 0.75F;
    private static final float UPRIGHT_NEAR = 0.3125F;
    private static final float UPRIGHT_FAR = 0.6875F;

    @Override
    public void render(VoidPortalBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int packedLight, int packedOverlay) {
        Matrix4f pose = poseStack.last().pose();
        VertexConsumer consumer = buffers.getBuffer(RenderType.endPortal());
        switch (blockEntity.getBlockState().getValue(VoidPortalBlock.AXIS)) {
            case X -> {
                // East face, then west.
                quad(consumer, pose, UPRIGHT_FAR, 1.0F, 0.0F, UPRIGHT_FAR, 1.0F, 1.0F, UPRIGHT_FAR, 0.0F, 1.0F, UPRIGHT_FAR, 0.0F, 0.0F);
                quad(consumer, pose, UPRIGHT_NEAR, 0.0F, 0.0F, UPRIGHT_NEAR, 0.0F, 1.0F, UPRIGHT_NEAR, 1.0F, 1.0F, UPRIGHT_NEAR, 1.0F, 0.0F);
            }
            case Y -> {
                // Down face, then up.
                quad(consumer, pose, 0.0F, FLAT_BOTTOM, 0.0F, 1.0F, FLAT_BOTTOM, 0.0F, 1.0F, FLAT_BOTTOM, 1.0F, 0.0F, FLAT_BOTTOM, 1.0F);
                quad(consumer, pose, 0.0F, FLAT_TOP, 1.0F, 1.0F, FLAT_TOP, 1.0F, 1.0F, FLAT_TOP, 0.0F, 0.0F, FLAT_TOP, 0.0F);
            }
            case Z -> {
                // South face, then north.
                quad(consumer, pose, 0.0F, 0.0F, UPRIGHT_FAR, 1.0F, 0.0F, UPRIGHT_FAR, 1.0F, 1.0F, UPRIGHT_FAR, 0.0F, 1.0F, UPRIGHT_FAR);
                quad(consumer, pose, 0.0F, 1.0F, UPRIGHT_NEAR, 1.0F, 1.0F, UPRIGHT_NEAR, 1.0F, 0.0F, UPRIGHT_NEAR, 0.0F, 0.0F, UPRIGHT_NEAR);
            }
        }
    }

    private static void quad(VertexConsumer consumer, Matrix4f pose, float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
        consumer.addVertex(pose, x0, y0, z0);
        consumer.addVertex(pose, x1, y1, z1);
        consumer.addVertex(pose, x2, y2, z2);
        consumer.addVertex(pose, x3, y3, z3);
    }
}
