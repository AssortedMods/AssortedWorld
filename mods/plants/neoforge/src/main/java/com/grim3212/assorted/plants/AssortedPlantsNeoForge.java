package com.grim3212.assorted.plants;

import com.grim3212.assorted.plants.client.data.PlantsLanguageProvider;
import com.grim3212.assorted.plants.client.data.PlantsManualProvider;
import com.grim3212.assorted.lib.data.ForgeBlockTagProvider;
import com.grim3212.assorted.lib.data.ForgeDatapackRegistryProvider;
import com.grim3212.assorted.plants.client.data.PlantsBlockstateProvider;
import com.grim3212.assorted.plants.client.data.PlantsItemModelProvider;
import com.grim3212.assorted.plants.data.PlantsBlockLoot;
import com.grim3212.assorted.plants.data.PlantsBlockTagProvider;
import com.grim3212.assorted.plants.data.PlantsGenData;
import com.grim3212.assorted.plants.data.PlantsRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class AssortedPlantsNeoForge {

    /**
     * {@code FMLJavaModLoadingContext} is gone; the mod event bus and the mod container are injected
     * into the {@code @Mod} constructor instead.
     */
    public AssortedPlantsNeoForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherServerData);
        modBus.addListener(this::gatherClientData);

        PlantsCommonMod.init();
    }

    /**
     * Server datagen. The server and client halves are separate events; if the wrong one runs, the
     * build still succeeds, with "All providers took: 0 ms".
     */
    private void gatherServerData(final GatherDataEvent.Server event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.addProvider(new ForgeBlockTagProvider(packOutput, lookupProvider, Constants.MOD_ID, new PlantsBlockTagProvider(packOutput, lookupProvider)));
        // Recipe providers are not data providers any more - the Runner owns the output.
        event.addProvider(new PlantsRecipes.Runner(packOutput, lookupProvider));
        event.addProvider(new LootTableProvider(packOutput, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(PlantsBlockLoot::new, LootContextParamSets.BLOCK)), lookupProvider));
        event.addProvider(new ForgeDatapackRegistryProvider(Constants.MOD_ID, new PlantsGenData()).datpackEntriesProvider(packOutput, lookupProvider));
    }

    private void gatherClientData(final GatherDataEvent.Client event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();

        event.addProvider(new PlantsBlockstateProvider(packOutput));
        event.addProvider(new PlantsItemModelProvider(packOutput));
        event.addProvider(new PlantsLanguageProvider(packOutput));
        event.addProvider(new PlantsManualProvider(packOutput));
    }
}
