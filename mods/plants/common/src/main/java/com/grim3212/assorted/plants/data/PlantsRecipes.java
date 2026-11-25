package com.grim3212.assorted.plants.data;

import com.grim3212.assorted.lib.core.conditions.ConditionalRecipeProvider;
import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.plants.Constants;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class PlantsRecipes extends ConditionalRecipeProvider {

    public PlantsRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Constants.MOD_ID);
    }

    @Override
    public void registerConditions() {
    }

    @Override
    public void buildRecipes() {
        super.buildRecipes();

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GUNPOWDER, 1).requires(PlantsBlocks.GUNPOWDER_REED.get()).unlockedBy("has_gunpowder_reeds", has(PlantsBlocks.GUNPOWDER_REED.get())).save(this.output, key("gunpowder"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PlantsBlocks.GUNPOWDER_REED.get(), 1).define('X', LibCommonTags.Items.GUNPOWDER).define('R', Items.SUGAR_CANE).pattern("XXX").pattern("XRX").pattern("XXX").unlockedBy("has_gunpowder", has(LibCommonTags.Items.GUNPOWDER)).save(this.output, key(name(PlantsBlocks.GUNPOWDER_REED.get())));

        // Three glowstone dust around a soul sand, as GrimPack had it
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PlantsBlocks.GLOWSTONE_SEEDS.get(), 1).define('G', LibCommonTags.Items.DUSTS_GLOWSTONE).define('S', Items.SOUL_SAND).pattern("GSG").pattern(" G ").unlockedBy("has_glowstone_dust", has(LibCommonTags.Items.DUSTS_GLOWSTONE)).save(this.output, key(name(PlantsBlocks.GLOWSTONE_SEEDS.get())));
    }

    private static ResourceLocation key(String path) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
    }
}
