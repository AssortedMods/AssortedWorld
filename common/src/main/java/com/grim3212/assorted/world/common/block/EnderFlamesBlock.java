package com.grim3212.assorted.world.common.block;

import com.grim3212.assorted.world.api.WorldTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Fire from a void striker. It burns forever on {@code #assortedworld:infiniburn_ender_flames}
 * and goes out after a few seconds anywhere else; it never spreads.
 */
public class EnderFlamesBlock extends BaseFireBlock {

    public static final MapCodec<EnderFlamesBlock> CODEC = simpleCodec(EnderFlamesBlock::new);

    public EnderFlamesBlock(BlockBehaviour.Properties properties) {
        super(properties, 1.0F);
    }

    @Override
    public MapCodec<EnderFlamesBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState();
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return level.getBlockState(below).isFaceSturdy(level, below, Direction.UP);
    }

    @Override
    protected boolean canBurn(BlockState state) {
        return true;
    }

    // BaseFireBlock's own onPlace would light nether portals too, so it is not called.
    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (oldState.is(this) || !(level instanceof ServerLevel serverLevel)) {
            return;
        }

        VoidPortalRect ring = VoidPortalShape.findContaining(level, pos);
        if (ring != null) {
            VoidPortalShape.light(serverLevel, ring);
            return;
        }

        if (!state.canSurvive(level, pos)) {
            level.removeBlock(pos, false);
        } else if (!burnsForever(level, pos)) {
            level.scheduleTick(pos, this, burnTime(level.getRandom()));
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (!this.canSurvive(state, level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        // The end stone underneath was swapped for something that does not keep it lit.
        if (directionToNeighbour == Direction.DOWN && !burnsForever(level, pos)) {
            ticks.scheduleTick(pos, this, burnTime(random));
        }
        return state;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!burnsForever(level, pos)) {
            level.removeBlock(pos, false);
        }
    }

    private static boolean burnsForever(LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(WorldTags.Blocks.INFINIBURN_ENDER_FLAMES);
    }

    private static int burnTime(RandomSource random) {
        return 100 + random.nextInt(60);
    }
}
