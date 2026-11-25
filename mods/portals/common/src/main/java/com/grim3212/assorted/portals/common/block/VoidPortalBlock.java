package com.grim3212.assorted.portals.common.block;

import com.grim3212.assorted.portals.common.block.entity.VoidPortalBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;

/**
 * Vanilla's end portal on any of the three axes, so a ring of void portal frames can stand on a
 * wall or hang from a ceiling. Axis {@code Y} is vanilla's flat portal.
 */
public class VoidPortalBlock extends BaseEntityBlock implements Portal {

    public static final MapCodec<VoidPortalBlock> CODEC = simpleCodec(VoidPortalBlock::new);
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    // Vanilla's end portal slab, and the same slab stood up across the X or Z axis.
    private static final VoxelShape FLAT = Block.box(0.0, 6.0, 0.0, 16.0, 12.0, 16.0);
    private static final VoxelShape ACROSS_X = Block.box(5.0, 0.0, 0.0, 11.0, 16.0, 16.0);
    private static final VoxelShape ACROSS_Z = Block.box(0.0, 0.0, 5.0, 16.0, 16.0, 11.0);

    public VoidPortalBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    protected MapCodec<VoidPortalBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VoidPortalBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case X -> ACROSS_X;
            case Y -> FLAT;
            case Z -> ACROSS_Z;
        };
    }

    /**
     * 1.21.1 calls this for anything inside the whole block space, so the entity has to touch the
     * portal's own slab first, as vanilla's end portal checks.
     */
    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.canUsePortal(false) && Shapes.joinIsNotEmpty(Shapes.create(entity.getBoundingBox().move(-pos.getX(), -pos.getY(), -pos.getZ())), state.getShape(level, pos), BooleanOp.AND)) {
            if (!level.isClientSide() && level.dimension() == Level.END && entity instanceof ServerPlayer player && !player.seenCredits) {
                player.showEndCredits();
            } else {
                entity.setAsInsidePortal(this, pos);
            }
        }
    }

    // The same trip as vanilla's end portal, which uses nothing of its own block to plan it.
    @Override
    public @Nullable DimensionTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos portalEntryPos) {
        return ((Portal) Blocks.END_PORTAL).getPortalDestination(level, entity, portalEntryPos);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Direction.Axis axis = state.getValue(AXIS);
        double x = pos.getX() + (axis == Direction.Axis.X ? 0.5 : random.nextDouble());
        double y = pos.getY() + (axis == Direction.Axis.Y ? 0.8 : random.nextDouble());
        double z = pos.getZ() + (axis == Direction.Axis.Z ? 0.5 : random.nextDouble());
        level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
    }

    // NeoForge deprecates this for its IBlockExtension overload, which is not in the vanilla jar common
    // compiles against; that overload's default calls this one, so overriding it answers pick block on both loaders.
    @SuppressWarnings("deprecation")
    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return RotatedPillarBlock.rotatePillar(state, rotation);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}
