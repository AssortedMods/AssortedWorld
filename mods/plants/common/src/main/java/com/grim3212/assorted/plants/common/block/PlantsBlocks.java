package com.grim3212.assorted.plants.common.block;

import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.plants.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;
import java.util.function.Supplier;

public class PlantsBlocks {

    public static final RegistryProvider<Block> BLOCKS = RegistryProvider.create(Registries.BLOCK, Constants.MOD_ID).aliasFrom(Constants.FAMILY_ID);
    public static final RegistryProvider<Item> ITEMS = RegistryProvider.create(Registries.ITEM, Constants.MOD_ID).aliasFrom(Constants.FAMILY_ID);

    // forceSolidOff() is deprecated in 26.2 and is redundant here: BlockStateBase#calculateSolid
    // already reports a block with an empty collision shape as non solid, and noCollision() gives
    // exactly that. Vanilla's own sugar cane dropped the call for the same reason.
    public static final IRegistryObject<GunpowderReedBlock> GUNPOWDER_REED = register("gunpowder_reed", props -> new GunpowderReedBlock(props.mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).isRedstoneConductor((state, getter, pos) -> false).noCollision().randomTicks().instabreak().lightLevel((l) -> 5).explosionResistance(200F).sound(SoundType.GRASS)));

    // noCollision() with an empty shape is what makes this non solid; see the gunpowder reed above.
    // Light level 12 matches the 0.8F the seeds glowed at before light levels were integers.
    public static final IRegistryObject<GlowstoneSeedBlock> GLOWSTONE_SEEDS = register("glowstone_seeds", props -> new GlowstoneSeedBlock(props.mapColor(MapColor.SAND).pushReaction(PushReaction.DESTROY).isRedstoneConductor((state, getter, pos) -> false).noCollision().randomTicks().instabreak().lightLevel((l) -> 12).sound(SoundType.GLASS)));

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
