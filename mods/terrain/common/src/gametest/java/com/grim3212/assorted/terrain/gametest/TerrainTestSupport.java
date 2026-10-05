package com.grim3212.assorted.terrain.gametest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Helpers and constants shared by Assorted Terrain's gametest classes, which import them statically,
 * alongside AssortedLib's {@code TestSupport}.
 */
final class TerrainTestSupport {

    private TerrainTestSupport() {
    }

    /** Middle of the 9x9x9 box, one block above its floor - room on every side for a drop. */
    static final BlockPos CENTRE = new BlockPos(4, 1, 4);

    /** The mod's own assets are on the classpath even on a headless server, jar or source root. */
    static boolean resourceExists(String path) {
        try (InputStream in = TerrainGameTests.class.getResourceAsStream(path)) {
            return in != null;
        } catch (IOException e) {
            return false;
        }
    }

    static JsonObject readJson(GameTestHelper helper, String path) {
        try (InputStream in = TerrainGameTests.class.getResourceAsStream(path)) {
            helper.assertTrue(in != null, path + " is not on the classpath");
            return JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (IOException e) {
            helper.assertTrue(false, "could not read " + path + ": " + e);
            return new JsonObject();
        }
    }

    static boolean hasFeature(Biome biome, Identifier id) {
        for (HolderSet<PlacedFeature> step : biome.getGenerationSettings().features()) {
            for (Holder<PlacedFeature> feature : step) {
                if (feature.unwrapKey().filter(key -> key.identifier().equals(id)).isPresent()) {
                    return true;
                }
            }
        }
        return false;
    }
}
