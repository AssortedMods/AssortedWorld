package com.grim3212.assorted.portals.data;

import com.grim3212.assorted.lib.core.conditions.ConditionalRecipeProvider;
import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.portals.Constants;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

public class PortalsRecipes extends ConditionalRecipeProvider {

    private final HolderGetter<Item> items;

    public PortalsRecipes(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output, Constants.MOD_ID);
        this.items = registries.lookupOrThrow(Registries.ITEM);
    }

    @Override
    public void registerConditions() {
        // Nothing is conditioned: installing this mod is what turns its recipes on.
    }

    @Override
    public void buildRecipes() {
        super.buildRecipes();

        // One eye per frame, as a stronghold's ring takes twelve.
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, PortalsBlocks.VOID_PORTAL_FRAME.get(), 1).define('O', LibCommonTags.Items.OBSIDIAN).define('E', Items.ENDER_EYE).define('C', PortalsItems.VOID_CRYSTAL.get()).pattern("OEO").pattern("OCO").pattern("OOO").unlockedBy("has_void_crystal", has(PortalsItems.VOID_CRYSTAL.get())).save(this.output, key(name(PortalsBlocks.VOID_PORTAL_FRAME.get())));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.TOOLS, PortalsItems.VOID_STRIKER.get(), 1).requires(Items.FLINT_AND_STEEL).requires(PortalsItems.VOID_CRYSTAL.get()).requires(Items.DRAGON_BREATH).unlockedBy("has_void_crystal", has(PortalsItems.VOID_CRYSTAL.get())).save(this.output, key(name(PortalsItems.VOID_STRIKER.get())));
    }

    /**
     * Recipe providers are not data providers any more - a {@link RecipeProvider.Runner} owns the
     * file writing and builds a fresh provider around the {@link RecipeOutput} it hands out. This is
     * what the loader datagen entry points register.
     */
    public static class Runner extends ConditionalRecipeProvider.Runner {

        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries, Constants.MOD_ID);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new PortalsRecipes(registries, output);
        }

        @Override
        public String getName() {
            return "Recipes: " + Constants.MOD_ID;
        }
    }

    /**
     * Recipes are addressed by {@code ResourceKey<Recipe<?>>} rather than a raw id now.
     */
    private static ResourceKey<Recipe<?>> key(String path) {
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, path));
    }
}
