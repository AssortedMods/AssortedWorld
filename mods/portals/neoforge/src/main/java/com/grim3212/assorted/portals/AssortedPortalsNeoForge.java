package com.grim3212.assorted.portals;

import com.grim3212.assorted.portals.client.data.PortalsLanguageProvider;
import com.grim3212.assorted.portals.client.data.PortalsManualProvider;
import com.grim3212.assorted.lib.data.ForgeBiomeTagProvider;
import com.grim3212.assorted.lib.data.ForgeBlockTagProvider;
import com.grim3212.assorted.lib.data.ForgeItemTagProvider;
import com.grim3212.assorted.lib.data.ForgeDatapackRegistryProvider;
import com.grim3212.assorted.portals.client.data.PortalsBlockstateProvider;
import com.grim3212.assorted.portals.client.data.PortalsItemModelProvider;
import com.grim3212.assorted.portals.data.PortalsBiomeTagProvider;
import com.grim3212.assorted.portals.data.PortalsBlockLoot;
import com.grim3212.assorted.portals.data.PortalsBlockTagProvider;
import com.grim3212.assorted.portals.data.PortalsChestLoot;
import com.grim3212.assorted.portals.data.PortalsCoreMachineRecipes;
import com.grim3212.assorted.portals.data.PortalsGenData;
import com.grim3212.assorted.portals.data.PortalsItemTagProvider;
import com.grim3212.assorted.portals.data.PortalsRecipes;
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
public class AssortedPortalsNeoForge {

    /**
     * {@code FMLJavaModLoadingContext} is gone; the mod event bus and the mod container are injected
     * into the {@code @Mod} constructor instead.
     */
    public AssortedPortalsNeoForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherServerData);
        modBus.addListener(this::gatherClientData);

        PortalsCommonMod.init();
    }

    /**
     * Server datagen. The server and client halves are separate events; if the wrong one runs, the
     * build still succeeds, with "All providers took: 0 ms".
     */
    private void gatherServerData(final GatherDataEvent.Server event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ForgeBlockTagProvider blockTagProvider = event.addProvider(new ForgeBlockTagProvider(packOutput, lookupProvider, Constants.MOD_ID, new PortalsBlockTagProvider(packOutput, lookupProvider)));
        event.addProvider(new ForgeItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), Constants.MOD_ID, new PortalsItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter())));
        event.addProvider(new ForgeBiomeTagProvider(packOutput, lookupProvider, Constants.MOD_ID, new PortalsBiomeTagProvider(packOutput, lookupProvider)));
        // Recipe providers are not data providers any more - the Runner owns the output.
        event.addProvider(new PortalsRecipes.Runner(packOutput, lookupProvider));
        event.addProvider(new PortalsCoreMachineRecipes(packOutput));
        event.addProvider(new LootTableProvider(packOutput, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(PortalsBlockLoot::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(PortalsChestLoot::new, LootContextParamSets.CHEST)), lookupProvider));
        event.addProvider(new ForgeDatapackRegistryProvider(Constants.MOD_ID, new PortalsGenData()).datpackEntriesProvider(packOutput, lookupProvider));
    }

    private void gatherClientData(final GatherDataEvent.Client event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();

        event.addProvider(new PortalsBlockstateProvider(packOutput));
        event.addProvider(new PortalsItemModelProvider(packOutput));
        event.addProvider(new PortalsLanguageProvider(packOutput));
        event.addProvider(new PortalsManualProvider(packOutput));
    }
}
