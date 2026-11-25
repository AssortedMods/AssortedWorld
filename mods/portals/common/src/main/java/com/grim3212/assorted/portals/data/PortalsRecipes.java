package com.grim3212.assorted.portals.data;

import com.grim3212.assorted.lib.core.conditions.ConditionalRecipeProvider;
import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.portals.Constants;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class PortalsRecipes extends ConditionalRecipeProvider {

    public PortalsRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Constants.MOD_ID);
    }

    @Override
    public void registerConditions() {
        // Nothing is conditioned: installing this mod is what turns its recipes on.
    }

    @Override
    public void buildRecipes() {
        super.buildRecipes();

        // One eye per frame, as a stronghold's ring takes twelve.
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PortalsBlocks.VOID_PORTAL_FRAME.get(), 1).define('O', LibCommonTags.Items.OBSIDIAN).define('E', Items.ENDER_EYE).define('C', PortalsItems.VOID_CRYSTAL.get()).pattern("OEO").pattern("OCO").pattern("OOO").unlockedBy("has_void_crystal", has(PortalsItems.VOID_CRYSTAL.get())).save(this.output, key(name(PortalsBlocks.VOID_PORTAL_FRAME.get())));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, PortalsItems.VOID_STRIKER.get(), 1).requires(Items.FLINT_AND_STEEL).requires(PortalsItems.VOID_CRYSTAL.get()).requires(Items.DRAGON_BREATH).unlockedBy("has_void_crystal", has(PortalsItems.VOID_CRYSTAL.get())).save(this.output, key(name(PortalsItems.VOID_STRIKER.get())));
    }

    private static ResourceLocation key(String path) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
    }
}
