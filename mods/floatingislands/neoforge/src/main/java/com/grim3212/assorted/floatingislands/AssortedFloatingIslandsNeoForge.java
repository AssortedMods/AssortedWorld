package com.grim3212.assorted.floatingislands;

import com.grim3212.assorted.floatingislands.client.data.FloatingIslandsLanguageProvider;
import com.grim3212.assorted.floatingislands.client.data.FloatingIslandsManualProvider;
import com.grim3212.assorted.floatingislands.data.FloatingIslandsBiomeTagProvider;
import com.grim3212.assorted.floatingislands.data.FloatingIslandsGenData;
import com.grim3212.assorted.lib.data.ForgeBiomeTagProvider;
import com.grim3212.assorted.lib.data.ForgeRegistryEntries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class AssortedFloatingIslandsNeoForge {

    /**
     * {@code FMLJavaModLoadingContext} is gone; the mod event bus and the mod container are injected
     * into the {@code @Mod} constructor instead.
     */
    public AssortedFloatingIslandsNeoForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherData);

        FloatingIslandsCommonMod.init();
    }

    /** One event for both halves; the data run's --server and --client flags pick which providers run. */
    private void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new ForgeBiomeTagProvider(packOutput, lookupProvider, Constants.MOD_ID, fileHelper, new FloatingIslandsBiomeTagProvider(packOutput, lookupProvider)));
        ForgeRegistryEntries.of(Constants.MOD_ID).world(new FloatingIslandsGenData()).addTo(event);

        // The language file and the manual, written into common for both loaders.
        generator.addProvider(event.includeClient(), new FloatingIslandsLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new FloatingIslandsManualProvider(packOutput));
    }
}
