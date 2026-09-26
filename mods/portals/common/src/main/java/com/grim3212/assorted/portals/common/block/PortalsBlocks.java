package com.grim3212.assorted.portals.common.block;

import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.portals.Constants;
import com.grim3212.assorted.portals.Family;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;
import java.util.function.Supplier;

public class PortalsBlocks {

    public static final RegistryProvider<Block> BLOCKS = RegistryProvider.create(Registries.BLOCK, Constants.MOD_ID).aliasFrom(Family.ID);
    public static final RegistryProvider<Item> ITEMS = RegistryProvider.create(Registries.ITEM, Constants.MOD_ID).aliasFrom(Family.ID);

    // End stone's hardness, since that is what it is found in; the frame is as hard as obsidian.
    public static final IRegistryObject<Block> VOID_CRYSTAL_ORE = register("void_crystal_ore", props -> new DropExperienceBlock(UniformInt.of(3, 7), props.mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASEDRUM).strength(3.0F, 9.0F).requiresCorrectToolForDrops()));
    public static final IRegistryObject<VoidPortalFrameBlock> VOID_PORTAL_FRAME = register("void_portal_frame", props -> new VoidPortalFrameBlock(props.mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.STONE).lightLevel(state -> 1).strength(50.0F, 1200.0F).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK)));

    public static final IRegistryObject<EnderFlamesBlock> ENDER_FLAMES = registerNoItem("ender_flames", props -> new EnderFlamesBlock(props.mapColor(MapColor.COLOR_PURPLE).replaceable().noCollision().instabreak().lightLevel(state -> 10).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY).noLootTable()));

    public static final IRegistryObject<VoidPortalBlock> VOID_PORTAL = registerNoItem("void_portal", props -> new VoidPortalBlock(props.mapColor(MapColor.COLOR_BLACK).noCollision().lightLevel(state -> 15).strength(-1.0F, 3600000.0F).noLootTable().pushReaction(PushReaction.BLOCK)));

    private static <T extends Block> IRegistryObject<T> register(String name, Function<BlockBehaviour.Properties, ? extends T> factory) {
        return register(name, factory, block -> item(name, block));
    }

    private static <T extends Block> IRegistryObject<T> register(String name, Function<BlockBehaviour.Properties, ? extends T> factory, Function<IRegistryObject<T>, Supplier<? extends Item>> itemCreator) {
        IRegistryObject<T> ret = registerNoItem(name, factory);
        ITEMS.register(name, itemCreator.apply(ret));
        return ret;
    }

    private static <T extends Block> IRegistryObject<T> registerNoItem(String name, Function<BlockBehaviour.Properties, ? extends T> factory) {
        // Since 1.21.2 every block has to know its own id before it is constructed, so the
        // properties are built here where the registration name is known.
        final ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        return BLOCKS.register(name, () -> factory.apply(BlockBehaviour.Properties.of().setId(key)));
    }

    private static Supplier<BlockItem> item(final String name, final IRegistryObject<? extends Block> block) {
        final ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        return () -> new BlockItem(block.get(), new Item.Properties().useBlockDescriptionPrefix().setId(key));
    }

    public static void init() {
    }
}
