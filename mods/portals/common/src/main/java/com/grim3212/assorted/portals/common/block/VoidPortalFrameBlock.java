package com.grim3212.assorted.portals.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.EnumMap;
import java.util.Map;

/**
 * A breakable end portal frame with its eye already set, facing any way so a ring can sit on a wall
 * or ceiling. See {@link VoidPortalShape} for the rings it makes.
 */
public class VoidPortalFrameBlock extends Block {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    // Vanilla's filled frame turned so its eye points along the facing.
    private static final Map<Direction, VoxelShape> SHAPES = makeShapes();

    public VoidPortalFrameBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && level instanceof ServerLevel serverLevel) {
            VoidPortalShape.extinguishFramedBy(serverLevel, pos);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return this.rotate(state, mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /** A 13 pixel deep frame with an 8 by 8 eye standing 3 pixels proud of the face it points out of. */
    private static Map<Direction, VoxelShape> makeShapes() {
        Map<Direction, VoxelShape> shapes = new EnumMap<>(Direction.class);
        for (Direction facing : Direction.values()) {
            int axis = facing.getAxis().ordinal();
            boolean positive = facing.getAxisDirection() == Direction.AxisDirection.POSITIVE;
            double[] frameFrom = {0.0, 0.0, 0.0};
            double[] frameTo = {16.0, 16.0, 16.0};
            double[] eyeFrom = {4.0, 4.0, 4.0};
            double[] eyeTo = {12.0, 12.0, 12.0};
            if (positive) {
                frameTo[axis] = 13.0;
                eyeFrom[axis] = 13.0;
                eyeTo[axis] = 16.0;
            } else {
                frameFrom[axis] = 3.0;
                eyeFrom[axis] = 0.0;
                eyeTo[axis] = 3.0;
            }
            shapes.put(facing, Shapes.or(Block.box(frameFrom[0], frameFrom[1], frameFrom[2], frameTo[0], frameTo[1], frameTo[2]),
                    Block.box(eyeFrom[0], eyeFrom[1], eyeFrom[2], eyeTo[0], eyeTo[1], eyeTo[2])));
        }
        return shapes;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }
}
