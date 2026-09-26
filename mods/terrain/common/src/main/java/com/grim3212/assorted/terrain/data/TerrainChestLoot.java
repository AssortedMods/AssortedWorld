package com.grim3212.assorted.terrain.data;

import com.grim3212.assorted.terrain.api.TerrainLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.function.BiConsumer;

public class TerrainChestLoot implements LootTableSubProvider {

    /**
     * The sherds a desert already gives up: vanilla's desert well archaeology pair and the four
     * from desert pyramids. A deep well is a second way to that set, never a new source for one.
     */
    private static final List<Item> DESERT_SHERDS = List.of(Items.ARMS_UP_POTTERY_SHERD, Items.BREWER_POTTERY_SHERD,
            Items.ARCHER_POTTERY_SHERD, Items.MINER_POTTERY_SHERD, Items.PRIZE_POTTERY_SHERD, Items.SKULL_POTTERY_SHERD);

    private final HolderLookup.Provider registries;

    public TerrainChestLoot(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        desertWellChests(output);
    }

    /**
     * The five desert well chests, one per depth tier. The shallow wells are the rubbish someone
     * threw down them and the deep ones are what was worth hiding at the bottom of a 30 block shaft.
     * Every tier is the same desert junk, a sherd roll and its own payout pool, so the three move
     * apart cleanly as the shaft gets deeper.
     */
    private void desertWellChests(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(TerrainLootTables.CHESTS_DESERT_WELL_10, LootTable.lootTable()
                .withPool(desertJunkPool(ConstantValue.exactly(3.0F)))
                .withPool(sherdPool(5))
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 4.0F))
                        .add(EmptyLootItem.emptyItem().setWeight(25))
                        .add(LootItem.lootTableItem(Items.COPPER_NUGGET).setWeight(20).apply(count(2.0F, 6.0F)))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(15).apply(count(2.0F, 5.0F)))
                        .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(15).apply(count(1.0F, 4.0F)))
                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(12).apply(count(1.0F, 4.0F)))
                        .add(LootItem.lootTableItem(Items.CACTUS).setWeight(12).apply(count(1.0F, 3.0F)))
                        .add(LootItem.lootTableItem(Items.LEATHER).setWeight(12).apply(count(1.0F, 3.0F)))
                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(8).apply(count(1.0F, 2.0F)))
                        .add(LootItem.lootTableItem(Items.BUCKET).setWeight(5))));

        output.accept(TerrainLootTables.CHESTS_DESERT_WELL_15, LootTable.lootTable()
                .withPool(desertJunkPool(ConstantValue.exactly(3.0F)))
                .withPool(sherdPool(10))
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 5.0F))
                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(20).apply(count(2.0F, 6.0F)))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(15).apply(count(1.0F, 3.0F)))
                        .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(15).apply(count(1.0F, 4.0F)))
                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(15).apply(count(1.0F, 4.0F)))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(12).apply(count(2.0F, 6.0F)))
                        .add(LootItem.lootTableItem(Items.CACTUS_FLOWER).setWeight(12).apply(count(1.0F, 2.0F)))
                        .add(LootItem.lootTableItem(Items.COPPER_TORCH).setWeight(10).apply(count(2.0F, 6.0F)))
                        .add(LootItem.lootTableItem(Items.FIREFLY_BUSH).setWeight(10).apply(count(1.0F, 2.0F)))
                        .add(LootItem.lootTableItem(Items.BRUSH).setWeight(8))
                        .add(LootItem.lootTableItem(Items.DECORATED_POT).setWeight(6))
                        .add(LootItem.lootTableItem(Items.COPPER_HORSE_ARMOR).setWeight(6))));

        output.accept(TerrainLootTables.CHESTS_DESERT_WELL_20, LootTable.lootTable()
                .withPool(desertJunkPool(ConstantValue.exactly(4.0F)))
                .withPool(sherdPool(20))
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 6.0F))
                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(18).apply(count(3.0F, 8.0F)))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(15).apply(count(2.0F, 5.0F)))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(12).apply(count(1.0F, 4.0F)))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(12).apply(count(1.0F, 3.0F)))
                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(10).apply(count(2.0F, 6.0F)))
                        .add(LootItem.lootTableItem(Items.SULFUR).setWeight(10).apply(count(2.0F, 6.0F)))
                        .add(LootItem.lootTableItem(Items.QUARTZ).setWeight(12).apply(count(1.0F, 3.0F)))
                        .add(LootItem.lootTableItem(Items.COPPER_SPEAR).setWeight(8))
                        .add(LootItem.lootTableItem(Items.COPPER_PICKAXE).setWeight(8))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(8).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR).setWeight(6))
                        .add(LootItem.lootTableItem(Items.SUSPICIOUS_STEW).setWeight(6))
                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(5))));

        output.accept(TerrainLootTables.CHESTS_DESERT_WELL_25, LootTable.lootTable()
                .withPool(desertJunkPool(ConstantValue.exactly(4.0F)))
                .withPool(sherdPool(30))
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 7.0F))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(15).apply(count(3.0F, 8.0F)))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(15).apply(count(3.0F, 8.0F)))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(12).apply(count(2.0F, 6.0F)))
                        .add(LootItem.lootTableItem(Items.CINNABAR).setWeight(10).apply(count(2.0F, 6.0F)))
                        .add(LootItem.lootTableItem(Items.SULFUR).setWeight(10).apply(count(3.0F, 8.0F)))
                        .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(10).apply(count(2.0F, 5.0F)))
                        .add(LootItem.lootTableItem(Items.QUARTZ).setWeight(10).apply(count(1.0F, 4.0F)))
                        .add(LootItem.lootTableItem(Items.BLAZE_ROD).setWeight(8).apply(count(1.0F, 5.0F)))
                        .add(LootItem.lootTableItem(Items.GHAST_TEAR).setWeight(5).apply(count(1.0F, 3.0F)))
                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(10).apply(count(2.0F, 6.0F)))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(10).apply(count(1.0F, 3.0F)))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                        .add(LootItem.lootTableItem(Items.IRON_SPEAR).setWeight(8))
                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR).setWeight(6))
                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(6))
                        .add(LootItem.lootTableItem(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(4))));

        output.accept(TerrainLootTables.CHESTS_DESERT_WELL_30, LootTable.lootTable()
                .withPool(desertJunkPool(ConstantValue.exactly(4.0F)))
                .withPool(sherdPool(50))
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(4.0F, 8.0F))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(12).apply(count(4.0F, 10.0F)))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(12).apply(count(2.0F, 6.0F)))
                        .add(LootItem.lootTableItem(Items.CINNABAR).setWeight(10).apply(count(4.0F, 10.0F)))
                        .add(LootItem.lootTableItem(Items.SULFUR).setWeight(10).apply(count(4.0F, 10.0F)))
                        .add(LootItem.lootTableItem(Items.QUARTZ).setWeight(10).apply(count(1.0F, 5.0F)))
                        .add(LootItem.lootTableItem(Items.BLAZE_ROD).setWeight(10).apply(count(1.0F, 6.0F)))
                        .add(LootItem.lootTableItem(Items.ENDER_PEARL).setWeight(8).apply(count(1.0F, 4.0F)))
                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(10).apply(count(3.0F, 8.0F)))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
                        .add(LootItem.lootTableItem(Items.GOLD_BLOCK).setWeight(8).apply(count(1.0F, 3.0F)))
                        .add(LootItem.lootTableItem(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(6).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))
                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR).setWeight(6))
                        .add(LootItem.lootTableItem(Items.DIAMOND_SPEAR).setWeight(5).apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, UniformGenerator.between(20.0F, 30.0F))))
                        .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(5).apply(EnchantWithLevelsFunction.enchantWithLevels(this.registries, UniformGenerator.between(20.0F, 30.0F))))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(4))
                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_RELIC).setWeight(1))));
    }

    /**
     * Sherds on their own roll, so a tier's depth can change how likely they are without disturbing
     * the rest of the table. {@code percent} is the chance of that roll being a sherd at all: each
     * sherd is weighted {@code percent} against an empty entry of {@code (100 - percent)} per sherd,
     * which makes the share exact and keeps it exact if the list grows.
     */
    private LootPool.Builder sherdPool(int percent) {
        LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F));

        for (Item sherd : DESERT_SHERDS) {
            pool.add(LootItem.lootTableItem(sherd).setWeight(percent));
        }

        return pool.add(EmptyLootItem.emptyItem().setWeight((100 - percent) * DESERT_SHERDS.size()));
    }

    /** What ended up down the shaft. Shared by all five tiers so they still read as one structure. */
    private LootPool.Builder desertJunkPool(NumberProvider rolls) {
        return LootPool.lootPool().setRolls(rolls)
                .add(LootItem.lootTableItem(Items.SAND).setWeight(15).apply(count(1.0F, 8.0F)))
                .add(LootItem.lootTableItem(Items.BONE).setWeight(12).apply(count(1.0F, 6.0F)))
                .add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(12).apply(count(1.0F, 3.0F)))
                .add(LootItem.lootTableItem(Items.DRY_SHORT_GRASS).setWeight(12).apply(count(1.0F, 4.0F)))
                .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(count(1.0F, 6.0F)))
                .add(LootItem.lootTableItem(Items.STRING).setWeight(10).apply(count(1.0F, 5.0F)))
                .add(LootItem.lootTableItem(Items.COBWEB).setWeight(10).apply(count(1.0F, 3.0F)))
                .add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(10).apply(count(2.0F, 6.0F)))
                .add(LootItem.lootTableItem(Items.DRY_TALL_GRASS).setWeight(8).apply(count(1.0F, 2.0F)))
                .add(LootItem.lootTableItem(Items.BRICK).setWeight(8).apply(count(1.0F, 4.0F)))
                .add(LootItem.lootTableItem(Items.FLINT).setWeight(8).apply(count(1.0F, 4.0F)))
                .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(8).apply(count(1.0F, 3.0F)));
    }

    private static LootItemConditionalFunction.Builder<?> count(float min, float max) {
        return SetItemCountFunction.setCount(UniformGenerator.between(min, max));
    }
}
