package com.grim3212.assorted.world.common.block.entity;

import com.grim3212.assorted.world.common.block.VoidPortalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Vanilla's end portal entity, drawing the two faces across its block's axis rather than only up and down. */
public class VoidPortalBlockEntity extends TheEndPortalBlockEntity {

    public VoidPortalBlockEntity(BlockPos pos, BlockState state) {
        super(WorldBlockEntityTypes.VOID_PORTAL.get(), pos, state);
    }

    @Override
    public boolean shouldRenderFace(Direction direction) {
        return direction.getAxis() == this.getBlockState().getValue(VoidPortalBlock.AXIS);
    }
}
