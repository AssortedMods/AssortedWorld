package com.grim3212.assorted.world.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Rings of void portal frames: 3 to 9 blocks a side on a floor, ceiling or wall, a frame on every
 * edge with the corners left open, and every frame facing out of the same side.
 */
public final class VoidPortalShape {

    public static final int MIN_SIZE = 3;
    public static final int MAX_SIZE = 9;
    private static final List<Direction.Axis> AXES = List.of(Direction.Axis.Y, Direction.Axis.X, Direction.Axis.Z);

    private VoidPortalShape() {
    }

    /** A finished ring whose inside holds this position, in whichever plane it is found first. */
    public static @Nullable VoidPortalRect findContaining(BlockGetter level, BlockPos inside) {
        for (Direction.Axis axis : AXES) {
            VoidPortalRect rect = find(level, inside, axis);
            if (rect != null) {
                return rect;
            }
        }
        return null;
    }

    /** A finished ring this frame is part of. */
    public static @Nullable VoidPortalRect findAround(BlockGetter level, BlockPos frame) {
        for (Direction.Axis axis : AXES) {
            for (Direction side : Direction.values()) {
                if (side.getAxis() != axis && level.getBlockState(frame.relative(side)).canBeReplaced()) {
                    VoidPortalRect rect = find(level, frame.relative(side), axis);
                    if (rect != null) {
                        return rect;
                    }
                }
            }
        }
        return null;
    }

    public static void light(ServerLevel level, VoidPortalRect rect) {
        BlockState portal = WorldBlocks.VOID_PORTAL.get().defaultBlockState().setValue(VoidPortalBlock.AXIS, rect.axis());
        for (BlockPos pos : rect.interior()) {
            level.destroyBlock(pos, true);
            level.setBlock(pos, portal, Block.UPDATE_CLIENTS);
        }

        // Vanilla's globalLevelEvent would sound for every player in the dimension.
        level.playSound(null, rect.centre(), SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    /** Puts out every portal touching this frame position. */
    public static void extinguishFramedBy(ServerLevel level, BlockPos frame) {
        for (Direction side : Direction.values()) {
            BlockState next = level.getBlockState(frame.relative(side));
            if (next.is(WorldBlocks.VOID_PORTAL.get())) {
                clear(level, frame.relative(side), next.getValue(VoidPortalBlock.AXIS));
            }
        }
    }

    /** Every connected portal block in its plane, which a finished ring caps at {@code MAX_SIZE} squared. */
    private static void clear(ServerLevel level, BlockPos start, Direction.Axis axis) {
        Set<BlockPos> seen = new HashSet<>();
        Queue<BlockPos> open = new ArrayDeque<>();
        open.add(start);
        seen.add(start);

        while (!open.isEmpty() && seen.size() <= MAX_SIZE * MAX_SIZE) {
            BlockPos pos = open.remove();
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            for (Direction side : Direction.values()) {
                BlockPos next = pos.relative(side);
                BlockState state = level.getBlockState(next);
                if (side.getAxis() != axis && state.is(WorldBlocks.VOID_PORTAL.get()) && state.getValue(VoidPortalBlock.AXIS) == axis && seen.add(next)) {
                    open.add(next);
                }
            }
        }
    }

    private static @Nullable VoidPortalRect find(BlockGetter level, BlockPos inside, Direction.Axis axis) {
        if (!level.getBlockState(inside).canBeReplaced()) {
            return null;
        }

        // Whichever two axes lie in the plane; up is Y on a wall so the ring reads as width by height.
        Direction across = Direction.fromAxisAndDirection(axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X, Direction.AxisDirection.POSITIVE);
        Direction up = Direction.fromAxisAndDirection(axis == Direction.Axis.Y ? Direction.Axis.Z : Direction.Axis.Y, Direction.AxisDirection.POSITIVE);

        int left = reachToFrame(level, inside, across.getOpposite());
        int right = reachToFrame(level, inside, across);
        int down = reachToFrame(level, inside, up.getOpposite());
        int top = reachToFrame(level, inside, up);
        if (left < 0 || right < 0 || down < 0 || top < 0) {
            return null;
        }

        int width = left + right + 1;
        int height = down + top + 1;
        if (width < MIN_SIZE || width > MAX_SIZE || height < MIN_SIZE || height > MAX_SIZE) {
            return null;
        }

        VoidPortalRect rect = new VoidPortalRect(inside.relative(across, -left).relative(up, -down), axis, across, up, width, height);
        boolean clear = rect.interior().stream().allMatch(pos -> level.getBlockState(pos).canBeReplaced());
        return clear && isFramedFacingOut(level, rect) ? rect : null;
    }

    /** Every slot holds a frame, all facing the same way out of the ring's plane. */
    private static boolean isFramedFacingOut(BlockGetter level, VoidPortalRect rect) {
        Direction facing = null;
        for (BlockPos slot : rect.ring()) {
            BlockState state = level.getBlockState(slot);
            if (!state.is(WorldBlocks.VOID_PORTAL_FRAME.get())) {
                return false;
            }
            Direction frameFacing = state.getValue(VoidPortalFrameBlock.FACING);
            if (facing == null) {
                facing = frameFacing;
            } else if (frameFacing != facing) {
                return false;
            }
        }
        return facing != null && facing.getAxis() == rect.axis();
    }

    /** Open blocks between here and the first frame that way, or -1 if something else is hit first. */
    private static int reachToFrame(BlockGetter level, BlockPos from, Direction way) {
        for (int step = 1; step <= MAX_SIZE; step++) {
            BlockState state = level.getBlockState(from.relative(way, step));
            if (state.is(WorldBlocks.VOID_PORTAL_FRAME.get())) {
                return step - 1;
            }
            if (!state.canBeReplaced()) {
                return -1;
            }
        }
        return -1;
    }
}
