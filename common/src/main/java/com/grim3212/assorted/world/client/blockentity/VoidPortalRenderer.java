package com.grim3212.assorted.world.client.blockentity;

import com.grim3212.assorted.world.common.block.VoidPortalBlock;
import com.grim3212.assorted.world.common.block.entity.VoidPortalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.AbstractEndPortalRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Vanilla's end portal cube squashed across the portal's axis: the flat one where vanilla draws it,
 * the upright ones through the middle of the block, matching {@link VoidPortalBlock}'s shapes.
 */
public class VoidPortalRenderer extends AbstractEndPortalRenderer<VoidPortalBlockEntity, VoidPortalRenderState> {

    @Override
    public VoidPortalRenderState createRenderState() {
        return new VoidPortalRenderState();
    }

    @Override
    public void extractRenderState(VoidPortalBlockEntity blockEntity, VoidPortalRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.axis = blockEntity.getBlockState().getValue(VoidPortalBlock.AXIS);
    }

    @Override
    public void submit(VoidPortalRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        switch (state.axis) {
            case X -> {
                poseStack.translate(0.3125F, 0.0F, 0.0F);
                poseStack.scale(0.375F, 1.0F, 1.0F);
            }
            case Y -> {
                poseStack.translate(0.0F, 0.375F, 0.0F);
                poseStack.scale(1.0F, 0.375F, 1.0F);
            }
            case Z -> {
                poseStack.translate(0.0F, 0.0F, 0.3125F);
                poseStack.scale(1.0F, 1.0F, 0.375F);
            }
        }
        submitCube(state.facesToShow, RenderTypes.endPortal(), poseStack, submitNodeCollector);
        poseStack.popPose();
    }
}
