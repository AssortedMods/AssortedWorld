package com.grim3212.assorted.world.common.block.entity;

import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.world.Constants;
import com.grim3212.assorted.world.common.block.WorldBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class WorldBlockEntityTypes {

    public static final RegistryProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistryProvider.create(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    public static final IRegistryObject<BlockEntityType<VoidPortalBlockEntity>> VOID_PORTAL = BLOCK_ENTITIES.register("void_portal", () -> Services.PLATFORM.createBlockEntityType(VoidPortalBlockEntity::new, WorldBlocks.VOID_PORTAL.get()));

    public static void init() {
    }
}
