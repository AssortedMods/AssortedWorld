package com.grim3212.assorted.portals.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.grim3212.assorted.lib.data.CrossLoaderData;
import com.grim3212.assorted.portals.Constants;
import com.grim3212.assorted.portals.api.PortalsTags;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;

/**
 * Assorted Machines recipes for this mod's ores, written as json since this mod does not build
 * against Machines. Each only loads when Machines is installed.
 */
public class PortalsCoreMachineRecipes implements DataProvider {

    private static final String MACHINES = "assortedmachines";

    private final PackOutput.PathProvider recipes;
    private final PackOutput.PathProvider advancements;

    public PortalsCoreMachineRecipes(PackOutput output) {
        this.recipes = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
        this.advancements = output.createPathProvider(PackOutput.Target.DATA_PACK, "advancement");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        CachedOutput bothLoaders = CrossLoaderData.wrap(output);
        Identifier id = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "void_crystal_from_grinding_mill");
        String oreTag = "#" + PortalsTags.Items.ORES_VOID_CRYSTAL.location();

        // Two for one, as Core's grinding mill gives for diamond and its own gem ores.
        return CompletableFuture.allOf(
                DataProvider.saveStable(bothLoaders, grindingMill(oreTag, PortalsItems.VOID_CRYSTAL.getId(), 2, 0.3F), this.recipes.json(id)),
                DataProvider.saveStable(bothLoaders, unlockAdvancement(id, oreTag), this.advancements.json(id.withPrefix("recipes/"))));
    }

    private static JsonObject grindingMill(String oreTag, Identifier result, int count, float experience) {
        JsonObject ingredient = new JsonObject();
        ingredient.addProperty("ingredient", oreTag);

        JsonObject output = new JsonObject();
        output.addProperty("count", count);
        output.addProperty("id", result.toString());

        JsonObject recipe = new JsonObject();
        recipe.add(CrossLoaderData.NEOFORGE_CONDITIONS, coreLoaded());
        recipe.addProperty("type", MACHINES + ":grinding_mill");
        recipe.addProperty("cookingtime", 600);
        recipe.addProperty("experience", experience);
        recipe.add("ingredient", ingredient);
        recipe.add("result", output);
        return recipe;
    }

    /** What Core's recipe builders give a machine recipe: unlocked by holding the ingredient. */
    private static JsonObject unlockAdvancement(Identifier recipe, String ingredient) {
        JsonObject item = new JsonObject();
        item.addProperty("items", ingredient);
        JsonArray items = new JsonArray();
        items.add(item);
        JsonObject hasIngredient = criterion("minecraft:inventory_changed", "items", items);

        JsonObject hasRecipe = criterion("minecraft:recipe_unlocked", "recipe", new JsonPrimitive(recipe.toString()));

        JsonObject criteria = new JsonObject();
        criteria.add("has_ingredient", hasIngredient);
        criteria.add("has_the_recipe", hasRecipe);

        JsonArray anyOf = new JsonArray();
        anyOf.add("has_the_recipe");
        anyOf.add("has_ingredient");
        JsonArray requirements = new JsonArray();
        requirements.add(anyOf);

        JsonArray unlocked = new JsonArray();
        unlocked.add(recipe.toString());
        JsonObject rewards = new JsonObject();
        rewards.add("recipes", unlocked);

        JsonObject advancement = new JsonObject();
        advancement.add(CrossLoaderData.NEOFORGE_CONDITIONS, coreLoaded());
        advancement.addProperty("parent", "minecraft:recipes/root");
        advancement.add("criteria", criteria);
        advancement.add("requirements", requirements);
        advancement.add("rewards", rewards);
        return advancement;
    }

    private static JsonObject criterion(String trigger, String key, JsonElement value) {
        JsonObject conditions = new JsonObject();
        conditions.add(key, value);
        JsonObject criterion = new JsonObject();
        criterion.add("conditions", conditions);
        criterion.addProperty("trigger", trigger);
        return criterion;
    }

    private static JsonArray coreLoaded() {
        JsonObject condition = new JsonObject();
        condition.addProperty("type", "neoforge:mod_loaded");
        condition.addProperty("modid", MACHINES);
        JsonArray conditions = new JsonArray();
        conditions.add(condition);
        return conditions;
    }

    @Override
    public String getName() {
        return "Assorted Machines recipes: " + Constants.MOD_ID;
    }
}
