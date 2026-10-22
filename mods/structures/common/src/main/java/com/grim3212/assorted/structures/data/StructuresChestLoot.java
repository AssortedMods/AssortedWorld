package com.grim3212.assorted.structures.data;

import com.grim3212.assorted.structures.api.StructuresLootTables;
import com.grim3212.assorted.structures.common.block.StructuresBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class StructuresChestLoot implements LootTableSubProvider {

    private final LootTableSubProvider.Context output;
    private final HolderGetter<Enchantment> enchantments;

    public StructuresChestLoot(LootTableSubProvider.Context output) {
        this.output = output;
        this.enchantments = output.lookup(Registries.ENCHANTMENT);
    }

    @Override
    public void run() {
        output.accept(StructuresLootTables.CHESTS_FOUNTAIN, LootTable.lootTable().withPool(treasurePool().add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(20)).add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2)).add(EmptyLootItem.emptyItem().setWeight(15))).withPool(fountainJunkPool()));

        output.accept(StructuresLootTables.CHESTS_PYRAMID, LootTable.lootTable().withPool(treasurePool().add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(20)).add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))).withPool(junkPool()));

        output.accept(StructuresLootTables.CHESTS_RUIN, LootTable.lootTable().withPool(treasurePool()).withPool(junkPool()));

        waterDomeChests();
    }

    /**
     * The four water dome chests, one per ribbing material. The three rare domes carry the payout
     * and the rune odds climb with the tier; all share one seabed junk pool.
     */
    private void waterDomeChests() {
        output.accept(StructuresLootTables.CHESTS_WATER_DOME_COBBLESTONE, LootTable.lootTable()
                .withPool(oceanJunkPool(ContextIntProviders.exactly(4)))
                .withPool(runePool(ContextIntProviders.exactly(1), 32))
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(2, 4))
                        .add(EmptyLootItem.emptyItem().setWeight(25))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(20).apply(count(2, 6)))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(15).apply(count(1, 3)))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(15).apply(count(2, 6)))
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(15).apply(count(1, 6)))
                        .add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS).setWeight(10).apply(count(1, 3)))
                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(10).apply(count(1, 5)))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(5).apply(count(1, 2)))
                        .add(LootItem.lootTableItem(Items.FISHING_ROD).setWeight(5))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.enchantments)))));

        output.accept(StructuresLootTables.CHESTS_WATER_DOME_GLOWSTONE, LootTable.lootTable()
                .withPool(oceanJunkPool(ContextIntProviders.exactly(4)))
                .withPool(runePool(ContextIntProviders.between(1, 2), 16))
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(3, 5))
                        .add(LootItem.lootTableItem(Items.GLOWSTONE_DUST).setWeight(20).apply(count(4, 10)))
                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(15).apply(count(4, 9)))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(12).apply(count(2, 5)))
                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(12).apply(count(2, 6)))
                        .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(10).apply(count(2, 5)))
                        .add(LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS).setWeight(10).apply(count(2, 5)))
                        .add(LootItem.lootTableItem(Items.SEA_LANTERN).setWeight(10).apply(count(1, 3)))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(10).apply(count(1, 4)))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(8).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.enchantments)))
                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(5))));

        output.accept(StructuresLootTables.CHESTS_WATER_DOME_IRON, LootTable.lootTable()
                .withPool(oceanJunkPool(ContextIntProviders.exactly(4)))
                .withPool(runePool(ContextIntProviders.exactly(2), 8))
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(3, 6))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(20).apply(count(4, 12)))
                        .add(LootItem.lootTableItem(Items.IRON_BLOCK).setWeight(15).apply(count(1, 3)))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(15).apply(count(3, 8)))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(12).apply(count(1, 4)))
                        .add(LootItem.lootTableItem(Items.NAUTILUS_SHELL).setWeight(12).apply(count(1, 3)))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(12).apply(count(2, 6)))
                        .add(LootItem.lootTableItem(Items.SPONGE).setWeight(10).apply(count(1, 4)))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.enchantments)))
                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(8).apply(EnchantWithLevelsFunction.enchantWithLevels(this.enchantments, ContextIntProviders.between(20, 30))))
                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(8))
                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR).setWeight(5))));

        output.accept(StructuresLootTables.CHESTS_WATER_DOME_OBSIDIAN, LootTable.lootTable()
                .withPool(oceanJunkPool(ContextIntProviders.exactly(4)))
                .withPool(runePool(ContextIntProviders.between(2, 3), 0))
                .withPool(LootPool.lootPool().setRolls(ContextIntProviders.between(4, 7))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(15).apply(count(3, 8)))
                        .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(15).apply(count(4, 12)))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(12).apply(count(4, 10)))
                        .add(LootItem.lootTableItem(Items.NAUTILUS_SHELL).setWeight(12).apply(count(2, 5)))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(12).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.enchantments)))
                        .add(LootItem.lootTableItem(Items.ECHO_SHARD).setWeight(8).apply(count(1, 3)))
                        .add(LootItem.lootTableItem(Items.HEART_OF_THE_SEA).setWeight(6))
                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(6).apply(count(1, 2)))
                        .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(6).apply(EnchantWithLevelsFunction.enchantWithLevels(this.enchantments, ContextIntProviders.between(25, 35))))
                        .add(LootItem.lootTableItem(Items.DIAMOND_BLOCK).setWeight(5).apply(count(1, 2)))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(5))
                        .add(LootItem.lootTableItem(Items.TRIDENT).setWeight(4).apply(EnchantWithLevelsFunction.enchantWithLevels(this.enchantments, ContextIntProviders.between(25, 35))))));
    }

    /**
     * Runes on their own roll, so a dome's tier can change how likely they are without disturbing
     * the rest of the table. Every rune is equally weighted (16 in total) and {@code emptyWeight}
     * dilutes them: 32 gives a third of a rune per roll, 0 makes every roll a rune.
     */
    private LootPool.Builder runePool(Holder<ContextIntProvider> rolls, int emptyWeight) {
        LootPool.Builder pool = LootPool.lootPool().setRolls(rolls);

        for (Block rune : StructuresBlocks.runeBlocks()) {
            pool.add(LootItem.lootTableItem(rune).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))));
        }

        if (emptyWeight > 0) {
            pool.add(EmptyLootItem.emptyItem().setWeight(emptyWeight));
        }

        return pool;
    }

    /** Silt off the seabed. Shared by all four dome tiers so they still read as the same structure. */
    private LootPool.Builder oceanJunkPool(Holder<ContextIntProvider> rolls) {
        return LootPool.lootPool().setRolls(rolls)
                .add(LootItem.lootTableItem(Items.KELP).setWeight(15).apply(count(2, 8)))
                .add(LootItem.lootTableItem(Items.SEAGRASS).setWeight(15).apply(count(2, 8)))
                .add(LootItem.lootTableItem(Items.COD).setWeight(12).apply(count(1, 4)))
                .add(LootItem.lootTableItem(Items.SALMON).setWeight(10).apply(count(1, 4)))
                .add(LootItem.lootTableItem(Items.INK_SAC).setWeight(10).apply(count(1, 5)))
                .add(LootItem.lootTableItem(Items.PRISMARINE_SHARD).setWeight(10).apply(count(1, 5)))
                .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(count(1, 6)))
                .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(count(1, 6)))
                .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(8).apply(count(1, 3)))
                .add(LootItem.lootTableItem(Items.LEATHER).setWeight(8).apply(count(1, 3)));
    }

    private static LootItemConditionalFunction.Builder<?> count(int min, int max) {
        return SetItemCountFunction.setCount(ContextIntProviders.between(min, max));
    }

    /**
     * The rune / valuables pool shared by all three chests. Every rune is equally weighted so the
     * odds of any single rune stay independent of how many runes the mod registers.
     */
    private LootPool.Builder treasurePool() {
        LootPool.Builder pool = LootPool.lootPool().setRolls(ContextIntProviders.between(2, 4));

        for (Block rune : StructuresBlocks.runeBlocks()) {
            pool.add(LootItem.lootTableItem(rune).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))));
        }

        return pool.add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 7))))
                .add(LootItem.lootTableItem(Items.EMERALD).setWeight(15).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                .add(LootItem.lootTableItem(Items.ENDER_PEARL).setWeight(6).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6))))
                .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(25).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(25).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                .add(LootItem.lootTableItem(Items.BONE).setWeight(25).apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 6))))
                .add(LootItem.lootTableItem(Items.SPIDER_EYE).setWeight(25).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(25).apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 7))))
                .add(LootItem.lootTableItem(Items.SADDLE).setWeight(20))
                .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR).setWeight(15))
                .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR).setWeight(10))
                .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR).setWeight(5))
                .add(LootItem.lootTableItem(Items.BOOK).setWeight(20).apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.enchantments)));
    }

    private LootPool.Builder junkPool() {
        return LootPool.lootPool().setRolls(ContextIntProviders.exactly(4))
                .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                .add(LootItem.lootTableItem(Items.COBWEB).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                .add(LootItem.lootTableItem(Items.PAPER).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))));
    }

    private LootPool.Builder fountainJunkPool() {
        return LootPool.lootPool().setRolls(ContextIntProviders.exactly(4))
                .add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                .add(LootItem.lootTableItem(Items.STRING).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                .add(LootItem.lootTableItem(Items.COBWEB).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 8))))
                .add(LootItem.lootTableItem(Items.COAL).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6))))
                .add(LootItem.lootTableItem(Items.TORCH).setWeight(8).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6))))
                .add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(10).apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 10))));
    }
}
