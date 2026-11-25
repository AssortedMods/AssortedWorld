package com.grim3212.assorted.terrain;

import com.grim3212.assorted.terrain.client.data.TerrainLanguageProvider;
import com.grim3212.assorted.terrain.client.data.TerrainManualProvider;
import com.grim3212.assorted.lib.data.ForgeBiomeTagProvider;
import com.grim3212.assorted.lib.data.ForgeBlockTagProvider;
import com.grim3212.assorted.lib.data.ForgeItemTagProvider;
import com.grim3212.assorted.lib.data.ForgeRegistryEntries;
import com.grim3212.assorted.terrain.client.data.TerrainBlockstateProvider;
import com.grim3212.assorted.terrain.data.TerrainBiomeTagProvider;
import com.grim3212.assorted.terrain.data.TerrainBlockLoot;
import com.grim3212.assorted.terrain.data.TerrainBlockTagProvider;
import com.grim3212.assorted.terrain.data.TerrainChestLoot;
import com.grim3212.assorted.terrain.data.TerrainGenData;
import com.grim3212.assorted.terrain.data.TerrainItemTagProvider;
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
public class AssortedTerrainNeoForge {

    /**
     * {@code FMLJavaModLoadingContext} is gone; the mod event bus and the mod container are injected
     * into the {@code @Mod} constructor instead.
     */
    public AssortedTerrainNeoForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherData);

        TerrainCommonMod.init();
    }

    /** One event for both halves; the data run's --server and --client flags pick which providers run. */
    private void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        ForgeBlockTagProvider blockTagProvider = generator.addProvider(event.includeServer(), new ForgeBlockTagProvider(packOutput, lookupProvider, Constants.MOD_ID, fileHelper, new TerrainBlockTagProvider(packOutput, lookupProvider)));
        generator.addProvider(event.includeServer(), new ForgeItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), Constants.MOD_ID, fileHelper, new TerrainItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter())));
        generator.addProvider(event.includeServer(), new ForgeBiomeTagProvider(packOutput, lookupProvider, Constants.MOD_ID, fileHelper, new TerrainBiomeTagProvider(packOutput, lookupProvider)));
        ForgeRegistryEntries.of(Constants.MOD_ID).world(new TerrainGenData()).blockLoot(TerrainBlockLoot::new).loot(TerrainChestLoot::new, LootContextParamSets.CHEST).addTo(event);

        generator.addProvider(event.includeClient(), new TerrainBlockstateProvider(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new TerrainLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new TerrainManualProvider(packOutput));
    }
}
