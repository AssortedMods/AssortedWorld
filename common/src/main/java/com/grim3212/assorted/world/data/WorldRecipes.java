package com.grim3212.assorted.world.data;

import com.grim3212.assorted.lib.core.conditions.ConditionalRecipeProvider;
import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.world.Constants;
import com.grim3212.assorted.world.common.block.WorldBlocks;
import com.grim3212.assorted.world.common.crafting.WorldConditions;
import com.grim3212.assorted.world.common.item.WorldItems;
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

public class WorldRecipes extends ConditionalRecipeProvider {

    private final HolderGetter<Item> items;

    public WorldRecipes(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output, Constants.MOD_ID);
        this.items = registries.lookupOrThrow(Registries.ITEM);
    }

    @Override
    public void registerConditions() {
        this.addConditions(partEnabled(WorldConditions.Parts.GLOWSTONE_SEEDS), WorldBlocks.GLOWSTONE_SEEDS.getId());
        this.addConditions(partEnabled(WorldConditions.Parts.VOID_PORTAL_FRAMES), WorldBlocks.VOID_PORTAL_FRAME.getId(), WorldItems.VOID_STRIKER.getId());
    }

    @Override
    public void buildRecipes() {
        super.buildRecipes();

        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, Items.GUNPOWDER, 1).requires(WorldBlocks.GUNPOWDER_REED.get()).unlockedBy("has_gunpowder_reeds", has(WorldBlocks.GUNPOWDER_REED.get())).save(this.output, key("gunpowder"));
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, WorldBlocks.GUNPOWDER_REED.get(), 1).define('X', LibCommonTags.Items.GUNPOWDER).define('R', Items.SUGAR_CANE).pattern("XXX").pattern("XRX").pattern("XXX").unlockedBy("has_gunpowder", has(LibCommonTags.Items.GUNPOWDER)).save(this.output, key(name(WorldBlocks.GUNPOWDER_REED.get())));

        // Three glowstone dust around a soul sand, as GrimPack had it
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, WorldBlocks.GLOWSTONE_SEEDS.get(), 1).define('G', LibCommonTags.Items.DUSTS_GLOWSTONE).define('S', Items.SOUL_SAND).pattern("GSG").pattern(" G ").unlockedBy("has_glowstone_dust", has(LibCommonTags.Items.DUSTS_GLOWSTONE)).save(this.output, key(name(WorldBlocks.GLOWSTONE_SEEDS.get())));

        // One eye per frame, as a stronghold's ring takes twelve.
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, WorldBlocks.VOID_PORTAL_FRAME.get(), 1).define('O', LibCommonTags.Items.OBSIDIAN).define('E', Items.ENDER_EYE).define('C', WorldItems.VOID_CRYSTAL.get()).pattern("OEO").pattern("OCO").pattern("OOO").unlockedBy("has_void_crystal", has(WorldItems.VOID_CRYSTAL.get())).save(this.output, key(name(WorldBlocks.VOID_PORTAL_FRAME.get())));
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.TOOLS, WorldItems.VOID_STRIKER.get(), 1).requires(Items.FLINT_AND_STEEL).requires(WorldItems.VOID_CRYSTAL.get()).requires(Items.DRAGON_BREATH).unlockedBy("has_void_crystal", has(WorldItems.VOID_CRYSTAL.get())).save(this.output, key(name(WorldItems.VOID_STRIKER.get())));
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
            return new WorldRecipes(registries, output);
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
