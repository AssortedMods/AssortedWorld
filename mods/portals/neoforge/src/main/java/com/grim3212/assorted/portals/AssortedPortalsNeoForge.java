package com.grim3212.assorted.portals;

import com.grim3212.assorted.portals.client.data.PortalsLanguageProvider;
import com.grim3212.assorted.portals.client.data.PortalsManualProvider;
import com.grim3212.assorted.lib.data.ForgeBiomeTagProvider;
import com.grim3212.assorted.lib.data.ForgeBlockTagProvider;
import com.grim3212.assorted.lib.data.ForgeItemTagProvider;
import com.grim3212.assorted.lib.data.ForgeRegistryEntries;
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
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class AssortedPortalsNeoForge {

    public AssortedPortalsNeoForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherData);

        PortalsCommonMod.init();
    }

    /**
     * One event for both halves; the data run's --server and --client flags pick which providers run.
     * If neither matches, the build still succeeds, with "All providers took: 0 ms".
     */
    private void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        ForgeBlockTagProvider blockTagProvider = generator.addProvider(event.includeServer(), new ForgeBlockTagProvider(packOutput, lookupProvider, Constants.MOD_ID, fileHelper, new PortalsBlockTagProvider(packOutput, lookupProvider)));
        generator.addProvider(event.includeServer(), new ForgeItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), Constants.MOD_ID, fileHelper, new PortalsItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter())));
        generator.addProvider(event.includeServer(), new ForgeBiomeTagProvider(packOutput, lookupProvider, Constants.MOD_ID, fileHelper, new PortalsBiomeTagProvider(packOutput, lookupProvider)));
        ForgeRegistryEntries.of(Constants.MOD_ID).world(new PortalsGenData()).recipes(PortalsRecipes::new).blockLoot(PortalsBlockLoot::new).loot(PortalsChestLoot::new, LootContextParamSets.CHEST).addTo(event);
        generator.addProvider(event.includeServer(), new PortalsCoreMachineRecipes(packOutput));

        generator.addProvider(event.includeClient(), new PortalsBlockstateProvider(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new PortalsItemModelProvider(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new PortalsLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new PortalsManualProvider(packOutput));
    }
}
