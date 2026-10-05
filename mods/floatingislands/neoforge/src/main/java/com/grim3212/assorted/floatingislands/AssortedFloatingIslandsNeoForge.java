package com.grim3212.assorted.floatingislands;

import com.grim3212.assorted.floatingislands.client.data.FloatingIslandsLanguageProvider;
import com.grim3212.assorted.floatingislands.client.data.FloatingIslandsManualProvider;
import com.grim3212.assorted.floatingislands.data.FloatingIslandsBiomeTagProvider;
import com.grim3212.assorted.floatingislands.data.FloatingIslandsGenData;
import com.grim3212.assorted.lib.data.ForgeBiomeTagProvider;
import com.grim3212.assorted.lib.data.ForgeDatapackRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class AssortedFloatingIslandsNeoForge {

    /**
     * {@code FMLJavaModLoadingContext} is gone; the mod event bus and the mod container are injected
     * into the {@code @Mod} constructor instead.
     */
    public AssortedFloatingIslandsNeoForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherServerData);
        modBus.addListener(this::gatherClientData);

        FloatingIslandsCommonMod.init();
    }

    /**
     * Server datagen. The server and client halves are separate events; if the wrong one runs, the
     * build still succeeds, with "All providers took: 0 ms".
     */
    private void gatherServerData(final GatherDataEvent.Server event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.addProvider(new ForgeBiomeTagProvider(packOutput, lookupProvider, Constants.MOD_ID, new FloatingIslandsBiomeTagProvider(packOutput, lookupProvider)));
        event.addProvider(new ForgeDatapackRegistryProvider(Constants.MOD_ID, new FloatingIslandsGenData()).datpackEntriesProvider(packOutput, lookupProvider));
    }

    /** Client datagen: the language file and the manual, written into common for both loaders. */
    private void gatherClientData(final GatherDataEvent.Client event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();

        event.addProvider(new FloatingIslandsLanguageProvider(packOutput));
        event.addProvider(new FloatingIslandsManualProvider(packOutput));
    }
}
