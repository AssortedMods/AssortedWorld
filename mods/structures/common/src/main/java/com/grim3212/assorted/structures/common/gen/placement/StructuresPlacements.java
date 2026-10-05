package com.grim3212.assorted.structures.common.gen.placement;

import com.grim3212.assorted.lib.registry.IRegistryObject;
import com.grim3212.assorted.lib.registry.RegistryProvider;
import com.grim3212.assorted.structures.Constants;
import com.grim3212.assorted.structures.StructuresCommonMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * The placement modifier type behind {@link ConfigRarityFilter}, and the part names its json
 * carries. A part is one thing the config can make rarer or turn off on its own.
 */
public class StructuresPlacements {

    public static final RegistryProvider<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = RegistryProvider.create(Registries.PLACEMENT_MODIFIER_TYPE, Constants.MOD_ID);

    public static final IRegistryObject<PlacementModifierType<ConfigRarityFilter>> CONFIG_RARITY = PLACEMENT_MODIFIER_TYPES.register("config_rarity", () -> () -> ConfigRarityFilter.CODEC);

    public static class Parts {
        public static final String SANDSTONE_PILLAR = "sandstone_pillar";
    }

    private static final Map<String, Supplier<Integer>> RARITIES = new HashMap<>();

    /** The configured rarity of a part, or 0 - never generate - for a name nothing registered. */
    public static int rarity(String part) {
        Supplier<Integer> rarity = RARITIES.get(part);
        return rarity == null ? 0 : rarity.get();
    }

    public static void init() {
        RARITIES.put(Parts.SANDSTONE_PILLAR, StructuresCommonMod.COMMON_CONFIG.sandstonePillarRarity);
    }
}
