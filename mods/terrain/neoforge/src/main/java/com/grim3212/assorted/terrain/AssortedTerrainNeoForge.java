package com.grim3212.assorted.terrain;

import com.grim3212.assorted.terrain.client.data.TerrainLanguageProvider;
import com.grim3212.assorted.terrain.client.data.TerrainManualProvider;
import com.grim3212.assorted.lib.data.ForgeBiomeTagProvider;
import com.grim3212.assorted.lib.data.ForgeBlockTagProvider;
import com.grim3212.assorted.lib.data.ForgeItemTagProvider;
import com.grim3212.assorted.lib.data.ForgeDatapackRegistryProvider;
import com.grim3212.assorted.terrain.client.data.TerrainBlockstateProvider;
import com.grim3212.assorted.terrain.data.TerrainBiomeTagProvider;
import com.grim3212.assorted.terrain.data.TerrainBlockLoot;
import com.grim3212.assorted.terrain.data.TerrainBlockTagProvider;
import com.grim3212.assorted.terrain.data.TerrainChestLoot;
import com.grim3212.assorted.terrain.data.TerrainGenData;
import com.grim3212.assorted.terrain.data.TerrainItemTagProvider;
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
public class AssortedTerrainNeoForge {

    /**
     * {@code FMLJavaModLoadingContext} is gone; the mod event bus and the mod container are injected
     * into the {@code @Mod} constructor instead.
     */
    public AssortedTerrainNeoForge(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::gatherServerData);
        modBus.addListener(this::gatherClientData);

        TerrainCommonMod.init();
    }

    /**
     * Server datagen. The server and client halves are separate events; if the wrong one runs, the
     * build still succeeds, with "All providers took: 0 ms".
     */
    private void gatherServerData(final GatherDataEvent.Server event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ForgeBlockTagProvider blockTagProvider = event.addProvider(new ForgeBlockTagProvider(packOutput, lookupProvider, Constants.MOD_ID, new TerrainBlockTagProvider(packOutput, lookupProvider)));
        event.addProvider(new ForgeItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), Constants.MOD_ID, new TerrainItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter())));
        event.addProvider(new ForgeBiomeTagProvider(packOutput, lookupProvider, Constants.MOD_ID, new TerrainBiomeTagProvider(packOutput, lookupProvider)));
        event.addProvider(new LootTableProvider(packOutput, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(TerrainBlockLoot::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(TerrainChestLoot::new, LootContextParamSets.CHEST)), lookupProvider));
        event.addProvider(new ForgeDatapackRegistryProvider(Constants.MOD_ID, new TerrainGenData()).datpackEntriesProvider(packOutput, lookupProvider));
    }

    private void gatherClientData(final GatherDataEvent.Client event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();

        event.addProvider(new TerrainBlockstateProvider(packOutput));
        event.addProvider(new TerrainLanguageProvider(packOutput));
        event.addProvider(new TerrainManualProvider(packOutput));
    }
}
