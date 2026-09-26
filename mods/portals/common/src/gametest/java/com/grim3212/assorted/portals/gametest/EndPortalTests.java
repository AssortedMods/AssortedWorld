package com.grim3212.assorted.portals.gametest;

import com.grim3212.assorted.portals.common.block.VoidPortalBlock;
import com.grim3212.assorted.portals.common.block.VoidPortalFrameBlock;
import com.grim3212.assorted.portals.common.block.VoidPortalRect;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.grim3212.assorted.portals.gametest.PortalsTestSupport.*;

/**
 * Craftable end portals: void crystal ore, void portal frames and the two ways to light a ring.
 */
final class EndPortalTests {

    private EndPortalTests() {
    }

    /** The 3x3 floor ring around {@code CENTRE}. */
    private static final VoidPortalRect FLOOR = new VoidPortalRect(CENTRE.offset(-1, 0, -1), Direction.Axis.Y, Direction.EAST, Direction.SOUTH, 3, 3);

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("ender_flames_light_a_finished_ring", EndPortalTests::enderFlamesLightAFinishedRing);
        out.accept("breaking_a_frame_closes_the_portal", EndPortalTests::breakingAFrameClosesThePortal);
        out.accept("ender_flames_need_all_twelve_frames", EndPortalTests::enderFlamesNeedAllTwelveFrames);
        out.accept("ring_with_one_frame_turned_does_not_light", EndPortalTests::ringWithOneFrameTurnedDoesNotLight);
        out.accept("ring_facing_along_its_plane_does_not_light", EndPortalTests::ringFacingAlongItsPlaneDoesNotLight);
        out.accept("seven_by_seven_ring_lights_and_closes", EndPortalTests::sevenBySevenRingLightsAndCloses);
        out.accept("wall_ring_lights_when_a_frame_is_struck", EndPortalTests::wallRingLightsWhenAFrameIsStruck);
        out.accept("ceiling_ring_lights_when_a_frame_is_struck", EndPortalTests::ceilingRingLightsWhenAFrameIsStruck);
        out.accept("ring_under_three_wide_does_not_light", EndPortalTests::ringUnderThreeWideDoesNotLight);
        out.accept("ender_flames_burn_forever_only_on_end_stone", EndPortalTests::enderFlamesBurnForeverOnlyOnEndStone);
        out.accept("void_portal_frame_is_as_hard_as_obsidian", EndPortalTests::voidPortalFrameIsAsHardAsObsidian);
        out.accept("void_crystal_ore_needs_an_iron_tool", EndPortalTests::voidCrystalOreNeedsAnIronTool);
        out.accept("end_city_chests_can_hold_void_crystal", EndPortalTests::endCityChestsCanHoldVoidCrystal);
    }

    /** Lit from a corner of the interior, and the frames are left facing as they were placed. */
    private static void enderFlamesLightAFinishedRing(GameTestHelper helper) {
        placeRing(helper, FLOOR, Direction.DOWN);
        helper.setBlock(CENTRE.offset(1, 0, -1), PortalsBlocks.ENDER_FLAMES.get());

        assertPortal(helper, FLOOR, true);
        for (BlockPos slot : FLOOR.ring()) {
            helper.assertValueEqual(helper.getBlockState(slot).getValue(VoidPortalFrameBlock.FACING), Direction.DOWN, "facing of the frame at " + slot);
        }
        helper.succeed();
    }

    private static void ringWithOneFrameTurnedDoesNotLight(GameTestHelper helper) {
        placeRing(helper, FLOOR, Direction.UP);
        BlockPos turned = FLOOR.ring().get(5);
        helper.setBlock(turned, helper.getBlockState(turned).setValue(VoidPortalFrameBlock.FACING, Direction.DOWN));
        helper.setBlock(CENTRE, PortalsBlocks.ENDER_FLAMES.get());

        assertPortal(helper, FLOOR, false);
        helper.succeed();
    }

    /** Frames all facing north on a floor agree with each other but face across the portal. */
    private static void ringFacingAlongItsPlaneDoesNotLight(GameTestHelper helper) {
        placeRing(helper, FLOOR, Direction.NORTH);
        helper.setBlock(CENTRE, PortalsBlocks.ENDER_FLAMES.get());

        assertPortal(helper, FLOOR, false);
        helper.succeed();
    }

    private static void breakingAFrameClosesThePortal(GameTestHelper helper) {
        placeRing(helper, FLOOR, Direction.UP);
        helper.setBlock(CENTRE, PortalsBlocks.ENDER_FLAMES.get());
        assertPortal(helper, FLOOR, true);

        helper.getLevel().destroyBlock(helper.absolutePos(FLOOR.ring().get(4)), true);

        assertPortal(helper, FLOOR, false);
        helper.assertFalse(helper.getEntities(EntityTypes.ITEM).isEmpty(), "the broken frame dropped nothing");
        helper.succeed();
    }

    /** With a frame missing the flames just burn, and placing it afterwards lights nothing. */
    private static void enderFlamesNeedAllTwelveFrames(GameTestHelper helper) {
        placeRing(helper, FLOOR, Direction.UP);
        BlockPos missing = FLOOR.ring().get(0);
        helper.setBlock(missing, Blocks.AIR);
        helper.setBlock(CENTRE, PortalsBlocks.ENDER_FLAMES.get());
        helper.assertBlockPresent(PortalsBlocks.ENDER_FLAMES.get(), CENTRE);

        helper.setBlock(missing, PortalsBlocks.VOID_PORTAL_FRAME.get().defaultBlockState().setValue(VoidPortalFrameBlock.FACING, Direction.UP));
        assertPortal(helper, FLOOR, false);
        helper.succeed();
    }

    /** The largest ring the 9x9 test box holds; breaking one frame has to clear all 49 blocks. */
    private static void sevenBySevenRingLightsAndCloses(GameTestHelper helper) {
        VoidPortalRect big = new VoidPortalRect(new BlockPos(1, 1, 1), Direction.Axis.Y, Direction.EAST, Direction.SOUTH, 7, 7);
        placeRing(helper, big, Direction.UP);
        helper.setBlock(new BlockPos(2, 1, 6), PortalsBlocks.ENDER_FLAMES.get());
        assertPortal(helper, big, true);

        helper.getLevel().destroyBlock(helper.absolutePos(big.ring().get(0)), false);
        assertPortal(helper, big, false);
        helper.succeed();
    }

    /** A 3 wide, 5 tall ring on the Z plane, struck from its north side. */
    private static void wallRingLightsWhenAFrameIsStruck(GameTestHelper helper) {
        VoidPortalRect wall = new VoidPortalRect(new BlockPos(3, 2, 4), Direction.Axis.Z, Direction.EAST, Direction.UP, 3, 5);
        placeRing(helper, wall, Direction.NORTH);

        strike(helper, wall.ring().get(0));

        assertPortal(helper, wall, true);
        helper.assertValueEqual(helper.getBlockState(wall.corner()).getValue(VoidPortalBlock.AXIS), Direction.Axis.Z, "portal axis");
        helper.succeed();
    }

    private static void ceilingRingLightsWhenAFrameIsStruck(GameTestHelper helper) {
        VoidPortalRect ceiling = new VoidPortalRect(new BlockPos(3, 7, 3), Direction.Axis.Y, Direction.EAST, Direction.SOUTH, 3, 3);
        placeRing(helper, ceiling, Direction.DOWN);

        strike(helper, ceiling.ring().get(0));

        assertPortal(helper, ceiling, true);
        helper.succeed();
    }

    private static void ringUnderThreeWideDoesNotLight(GameTestHelper helper) {
        VoidPortalRect narrow = new VoidPortalRect(new BlockPos(3, 1, 3), Direction.Axis.Y, Direction.EAST, Direction.SOUTH, 2, 3);
        placeRing(helper, narrow, Direction.UP);
        helper.setBlock(narrow.corner(), PortalsBlocks.ENDER_FLAMES.get());

        assertPortal(helper, narrow, false);
        helper.succeed();
    }

    /** Burn time is 100 to 159 ticks, so by 200 only the flames on end stone are left. */
    private static void enderFlamesBurnForeverOnlyOnEndStone(GameTestHelper helper) {
        BlockPos onEndStone = new BlockPos(2, 1, 4);
        BlockPos onStone = new BlockPos(6, 1, 4);
        helper.setBlock(onEndStone.below(), Blocks.END_STONE);
        helper.setBlock(onStone.below(), Blocks.STONE);
        helper.setBlock(onEndStone, PortalsBlocks.ENDER_FLAMES.get());
        helper.setBlock(onStone, PortalsBlocks.ENDER_FLAMES.get());

        helper.runAfterDelay(200, () -> {
            helper.assertBlockPresent(PortalsBlocks.ENDER_FLAMES.get(), onEndStone);
            helper.assertBlockPresent(Blocks.AIR, onStone);
            helper.succeed();
        });
    }

    private static void voidPortalFrameIsAsHardAsObsidian(GameTestHelper helper) {
        helper.setBlock(CENTRE, PortalsBlocks.VOID_PORTAL_FRAME.get());
        BlockState frame = helper.getBlockState(CENTRE);

        helper.assertValueEqual(frame.getDestroySpeed(helper.getLevel(), helper.absolutePos(CENTRE)), Blocks.OBSIDIAN.defaultDestroyTime(), "void portal frame hardness");
        helper.assertValueEqual(frame.getBlock().getExplosionResistance(), Blocks.OBSIDIAN.getExplosionResistance(), "void portal frame blast resistance");
        helper.assertFalse(new ItemStack(Items.IRON_PICKAXE).isCorrectToolForDrops(frame), "an iron pickaxe harvests a void portal frame");
        helper.assertTrue(new ItemStack(Items.DIAMOND_PICKAXE).isCorrectToolForDrops(frame), "a diamond pickaxe does not harvest a void portal frame");
        helper.succeed();
    }

    private static void voidCrystalOreNeedsAnIronTool(GameTestHelper helper) {
        helper.setBlock(CENTRE, PortalsBlocks.VOID_CRYSTAL_ORE.get());
        BlockState ore = helper.getBlockState(CENTRE);
        helper.assertFalse(new ItemStack(Items.STONE_PICKAXE).isCorrectToolForDrops(ore), "a stone pickaxe harvests void crystal ore");
        helper.assertTrue(new ItemStack(Items.IRON_PICKAXE).isCorrectToolForDrops(ore), "an iron pickaxe does not harvest void crystal ore");

        helper.getLevel().destroyBlock(helper.absolutePos(CENTRE), true);
        boolean dropped = helper.getEntities(EntityTypes.ITEM).stream().anyMatch(item -> item.getItem().is(PortalsItems.VOID_CRYSTAL.get()));
        helper.assertTrue(dropped, "void crystal ore dropped no void crystal");
        helper.succeed();
    }

    /** Rolls vanilla's own end city table, so this fails if either loader stops pooling ours into it. */
    private static void endCityChestsCanHoldVoidCrystal(GameTestHelper helper) {
        LootTable table = helper.getLevel().getServer().reloadableRegistries().getLootTable(BuiltInLootTables.END_CITY_TREASURE);
        LootParams params = new LootParams.Builder(helper.getLevel())
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(helper.absolutePos(CENTRE)))
                .create(LootContextParamSets.CHEST);

        int withCrystal = 0;
        for (long seed = 0; seed < 100; seed++) {
            if (table.getRandomItems(params, seed).stream().anyMatch(stack -> stack.is(PortalsItems.VOID_CRYSTAL.get()))) {
                withCrystal++;
            }
        }
        // One chest in four; 8 to 45 of 100 is a safe margin either side of 25.
        helper.assertTrue(withCrystal >= 8 && withCrystal <= 45, withCrystal + " of 100 end city chests held void crystal, not about one in four");
        helper.succeed();
    }

    /** The ring's frames all facing one way, on an end stone floor under a flat ring. */
    private static void placeRing(GameTestHelper helper, VoidPortalRect rect, Direction facing) {
        if (rect.axis() == Direction.Axis.Y && rect.corner().getY() == 1) {
            for (int x = -1; x <= rect.width(); x++) {
                for (int z = -1; z <= rect.height(); z++) {
                    helper.setBlock(rect.corner().offset(x, -1, z), Blocks.END_STONE);
                }
            }
        }
        for (BlockPos slot : rect.ring()) {
            helper.setBlock(slot, PortalsBlocks.VOID_PORTAL_FRAME.get().defaultBlockState().setValue(VoidPortalFrameBlock.FACING, facing));
        }
    }

    /** A survival player uses a void striker on a frame. */
    private static void strike(GameTestHelper helper, BlockPos frame) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack striker = new ItemStack(PortalsItems.VOID_STRIKER.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, striker);

        BlockPos absolute = helper.absolutePos(frame);
        BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(absolute), Direction.NORTH, absolute, false);
        striker.useOn(new UseOnContext(player, InteractionHand.MAIN_HAND, hit));
        helper.assertValueEqual(striker.getDamageValue(), 1, "striker damage after lighting a ring");
    }

    private static void assertPortal(GameTestHelper helper, VoidPortalRect rect, boolean lit) {
        List<BlockPos> interior = rect.interior();
        for (BlockPos inside : interior) {
            if (lit) {
                helper.assertBlockPresent(PortalsBlocks.VOID_PORTAL.get(), inside);
            } else {
                helper.assertBlockNotPresent(PortalsBlocks.VOID_PORTAL.get(), inside);
            }
        }
    }
}
