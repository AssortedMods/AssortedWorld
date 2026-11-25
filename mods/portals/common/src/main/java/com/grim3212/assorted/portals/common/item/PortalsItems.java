package com.grim3212.assorted.portals.common.item;

import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.portals.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class PortalsItems {

    public static final RegistryProvider<Item> ITEMS = RegistryProvider.create(Registries.ITEM, Constants.MOD_ID).aliasFrom(Constants.FAMILY_ID);

    public static final IRegistryObject<Item> VOID_CRYSTAL = register("void_crystal", Item::new);
    public static final IRegistryObject<VoidStrikerItem> VOID_STRIKER = register("void_striker", props -> new VoidStrikerItem(props.durability(64)));

    private static <T extends Item> IRegistryObject<T> register(final String name, final Function<Item.Properties, ? extends T> factory) {
        return ITEMS.register(name, () -> factory.apply(new Item.Properties()));
    }

    public static void init() {
    }
}
