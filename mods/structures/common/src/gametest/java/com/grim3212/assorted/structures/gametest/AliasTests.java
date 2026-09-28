package com.grim3212.assorted.structures.gametest;

import com.google.gson.JsonParser;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.structures.Constants;
import com.grim3212.assorted.structures.common.block.StructuresBlocks;
import com.grim3212.assorted.structures.common.gen.structure.StructuresTypes;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/** A world saved when this was all one mod, Assorted World, still has these runes and structure pieces in it. */
final class AliasTests {

    private AliasTests() {
    }

    static void register(BiConsumer<String, Consumer<GameTestHelper>> out) {
        out.accept("assortedworld_ids_still_load", AliasTests::assortedworldIdsStillLoad);
    }

    private static void assortedworldIdsStillLoad(GameTestHelper helper) {
        for (IRegistryObject<Block> block : StructuresBlocks.BLOCKS.getEntries()) {
            helper.assertValueEqual(BuiltInRegistries.BLOCK.getValue(old(block.getId())), block.get(), "the block saved as " + old(block.getId()));
        }

        for (IRegistryObject<Item> item : StructuresBlocks.ITEMS.getEntries()) {
            ItemStack stack = ItemStack.CODEC.parse(helper.getLevel().registryAccess().createSerializationContext(JsonOps.INSTANCE),
                    JsonParser.parseString("{\"id\": \"" + old(item.getId()) + "\", \"count\": 1}")).getOrThrow();
            helper.assertTrue(stack.is(item.get()), "a stack saved as " + old(item.getId()) + " reads back as " + stack);
        }

        // A structure saved half generated names its pieces by id.
        for (IRegistryObject<StructurePieceType> piece : StructuresTypes.STRUCTURE_PIECES.getEntries()) {
            helper.assertValueEqual(BuiltInRegistries.STRUCTURE_PIECE.getValue(old(piece.getId())), piece.get(), "the structure piece saved as " + old(piece.getId()));
        }
        helper.succeed();
    }

    private static Identifier old(Identifier id) {
        return Identifier.fromNamespaceAndPath(Constants.FAMILY_ID, id.getPath());
    }
}
