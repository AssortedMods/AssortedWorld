package com.grim3212.assorted.structures;

import com.grim3212.assorted.structures.client.data.StructuresLanguageProvider;
import com.grim3212.assorted.structures.client.data.StructuresManualProvider;
import com.grim3212.assorted.lib.data.ForgeBiomeTagProvider;
import com.grim3212.assorted.lib.data.ForgeBlockTagProvider;
import com.grim3212.assorted.lib.data.ForgeItemTagProvider;
import com.grim3212.assorted.lib.data.ForgeRegistryEntries;
import com.grim3212.assorted.structures.client.data.StructuresBlockstateProvider;
import com.grim3212.assorted.structures.data.StructuresBiomeTagProvider;
import com.grim3212.assorted.structures.data.StructuresBlockLoot;
import com.grim3212.assorted.structures.data.StructuresBlockTagProvider;
import com.grim3212.assorted.structures.data.StructuresChestLoot;
import com.grim3212.assorted.structures.data.StructuresGenData;
import com.grim3212.assorted.structures.data.StructuresItemTagProvider;
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
public class AssortedStructuresNeoForge {

    public AssortedStructuresNeoForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherData);

        StructuresCommonMod.init();
    }

    /** One event for both halves; the data run's --server and --client flags pick which providers run. */
    private void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        ForgeBlockTagProvider blockTagProvider = generator.addProvider(event.includeServer(), new ForgeBlockTagProvider(packOutput, lookupProvider, Constants.MOD_ID, fileHelper, new StructuresBlockTagProvider(packOutput, lookupProvider)));
        generator.addProvider(event.includeServer(), new ForgeItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), Constants.MOD_ID, fileHelper, new StructuresItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter())));
        generator.addProvider(event.includeServer(), new ForgeBiomeTagProvider(packOutput, lookupProvider, Constants.MOD_ID, fileHelper, new StructuresBiomeTagProvider(packOutput, lookupProvider)));
        ForgeRegistryEntries.of(Constants.MOD_ID).world(new StructuresGenData()).blockLoot(StructuresBlockLoot::new).loot(StructuresChestLoot::new, LootContextParamSets.CHEST).addTo(event);

        generator.addProvider(event.includeClient(), new StructuresBlockstateProvider(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new StructuresLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new StructuresManualProvider(packOutput));
    }
}
