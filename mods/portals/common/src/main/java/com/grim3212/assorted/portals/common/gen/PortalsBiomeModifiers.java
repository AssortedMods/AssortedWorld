package com.grim3212.assorted.portals.common.gen;

import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.platform.services.IWorldGenHelper;
import com.grim3212.assorted.portals.api.PortalsTags;
import com.grim3212.assorted.portals.data.PortalsGenData;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

public class PortalsBiomeModifiers {

    public static void init() {
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(PortalsTags.Biomes.HAS_VOID_CRYSTAL_ORE), GenerationStep.Decoration.UNDERGROUND_ORES, PortalsGenData.VOID_CRYSTAL_KEY);
    }

    private static IWorldGenHelper.BiomePredicate matchesTag(TagKey<Biome> tag) {
        return (resourceLocation, biome) -> biome.is(tag);
    }
}
