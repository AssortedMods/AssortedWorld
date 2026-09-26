package com.grim3212.assorted.floatingislands.common.gen;

import com.grim3212.assorted.lib.platform.Services;
import com.grim3212.assorted.lib.platform.services.IWorldGenHelper;
import com.grim3212.assorted.floatingislands.api.FloatingIslandsTags;
import com.grim3212.assorted.floatingislands.data.FloatingIslandsGenData;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

public class FloatingIslandsBiomeModifiers {

    public static void init() {
        // Attached unconditionally: ConfigRarityFilter reads the config's rarity on every attempt.
        Services.WORLD_GEN.addFeatureToBiomes(matchesTag(FloatingIslandsTags.Biomes.HAS_FLOATING_ISLAND), GenerationStep.Decoration.SURFACE_STRUCTURES, FloatingIslandsGenData.FLOATING_ISLAND_KEY);
    }

    private static IWorldGenHelper.BiomePredicate matchesTag(TagKey<Biome> tag) {
        return (resourceLocation, biome) -> biome.is(tag);
    }
}
