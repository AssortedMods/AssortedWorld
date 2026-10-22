package com.grim3212.assorted.plants.data;

import com.grim3212.assorted.lib.core.conditions.ConditionalRecipeProvider;
import com.grim3212.assorted.lib.util.LibCommonTags;
import com.grim3212.assorted.plants.Constants;
import com.grim3212.assorted.plants.common.block.PlantsBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

public class PlantsRecipes extends ConditionalRecipeProvider {

    public PlantsRecipes(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        super(recipes, advancements, Constants.MOD_ID);
    }

    @Override
    public void registerConditions() {
    }

    @Override
    public void buildRecipes() {
        super.buildRecipes();

        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, Items.GUNPOWDER, 1).requires(PlantsBlocks.GUNPOWDER_REED.get()).unlockedBy("has_gunpowder_reeds", has(PlantsBlocks.GUNPOWDER_REED.get())).save(this.output, key("gunpowder"));
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, PlantsBlocks.GUNPOWDER_REED.get(), 1).define('X', LibCommonTags.Items.GUNPOWDER).define('R', Items.SUGAR_CANE).pattern("XXX").pattern("XRX").pattern("XXX").unlockedBy("has_gunpowder", has(LibCommonTags.Items.GUNPOWDER)).save(this.output, key(name(PlantsBlocks.GUNPOWDER_REED.get())));

        // Three glowstone dust around a soul sand, as GrimPack had it
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, PlantsBlocks.GLOWSTONE_SEEDS.get(), 1).define('G', LibCommonTags.Items.DUSTS_GLOWSTONE).define('S', Items.SOUL_SAND).pattern("GSG").pattern(" G ").unlockedBy("has_glowstone_dust", has(LibCommonTags.Items.DUSTS_GLOWSTONE)).save(this.output, key(name(PlantsBlocks.GLOWSTONE_SEEDS.get())));
    }

    /**
     * Recipes are addressed by {@code ResourceKey<Recipe<?>>} rather than a raw id now.
     */
    private static ResourceKey<Recipe<?>> key(String path) {
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(Constants.MOD_ID, path));
    }
}
