package com.grim3212.assorted.portals.common.item;

import com.grim3212.assorted.portals.common.block.VoidPortalRect;
import com.grim3212.assorted.portals.common.block.VoidPortalShape;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

/**
 * A flint and steel that lights ender flames instead of fire, and lights a finished void portal
 * ring when struck on one of its frames. It leaves campfires and candles alone.
 */
public class VoidStrikerItem extends Item {

    public VoidStrikerItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        VoidPortalRect ring = level.getBlockState(context.getClickedPos()).is(PortalsBlocks.VOID_PORTAL_FRAME.get()) ? VoidPortalShape.findAround(level, context.getClickedPos()) : null;
        if (ring != null) {
            if (level instanceof ServerLevel serverLevel) {
                VoidPortalShape.light(serverLevel, ring);
                level.playSound(null, context.getClickedPos(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                if (player != null) {
                    context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
                }
            }
            return InteractionResult.SUCCESS;
        }

        BlockPos firePos = context.getClickedPos().relative(context.getClickedFace());
        BlockState flames = PortalsBlocks.ENDER_FLAMES.get().defaultBlockState();

        if (!level.getBlockState(firePos).isAir() || !flames.canSurvive(level, firePos)) {
            return InteractionResult.FAIL;
        }

        level.playSound(player, firePos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
        level.setBlock(firePos, flames, Block.UPDATE_ALL_IMMEDIATE);
        level.gameEvent(player, GameEvent.BLOCK_PLACE, context.getClickedPos());
        ItemStack stack = context.getItemInHand();
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, firePos, stack);
            stack.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
        }

        return InteractionResult.SUCCESS;
    }
}
