package com.grim3212.assorted.portals.gametest;

import com.google.gson.JsonParser;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.portals.Family;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import com.grim3212.assorted.portals.common.block.entity.PortalsBlockEntityTypes;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** A world saved when this was all one mod, Assorted World, still has these blocks, items and portals in it. */
final class AliasTests {

    private AliasTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("assortedworld_ids_still_load", AliasTests::assortedworldIdsStillLoad);
    }

    private static void assortedworldIdsStillLoad(GameTestHelper helper) {
        // Block items register through PortalsBlocks, the plain items through PortalsItems.
        List<IRegistryObject<Item>> items = new ArrayList<>(PortalsBlocks.ITEMS.getEntries());
        items.addAll(PortalsItems.ITEMS.getEntries());
        for (IRegistryObject<Item> item : items) {
            ItemStack stack = ItemStack.CODEC.parse(helper.getLevel().registryAccess().createSerializationContext(JsonOps.INSTANCE),
                    JsonParser.parseString("{\"id\": \"" + old(item.getId()) + "\", \"count\": 1}")).getOrThrow();
            helper.assertTrue(stack.is(item.get()), "a stack saved as " + old(item.getId()) + " reads back as " + stack);
        }

        for (IRegistryObject<Block> block : PortalsBlocks.BLOCKS.getEntries()) {
            helper.assertValueEqual(BuiltInRegistries.BLOCK.getValue(old(block.getId())), block.get(), "the block saved as " + old(block.getId()));
        }

        for (IRegistryObject<BlockEntityType<?>> type : PortalsBlockEntityTypes.BLOCK_ENTITIES.getEntries()) {
            helper.assertValueEqual(BuiltInRegistries.BLOCK_ENTITY_TYPE.getValue(old(type.getId())), type.get(), "the block entity saved as " + old(type.getId()));
        }
        helper.succeed();
    }

    private static Identifier old(Identifier id) {
        return Identifier.fromNamespaceAndPath(Family.ID, id.getPath());
    }
}
