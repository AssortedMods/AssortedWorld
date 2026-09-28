package com.grim3212.assorted.portals.common.block.entity;

import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.portals.Constants;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class PortalsBlockEntityTypes {

    public static final RegistryProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistryProvider.create(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID).aliasFrom(Constants.FAMILY_ID);

    public static final IRegistryObject<BlockEntityType<VoidPortalBlockEntity>> VOID_PORTAL = BLOCK_ENTITIES.register("void_portal", () -> Services.PLATFORM.createBlockEntityType(VoidPortalBlockEntity::new, PortalsBlocks.VOID_PORTAL.get()));

    public static void init() {
    }
}
