package com.grim3212.assorted.portals.client.data;

import com.grim3212.assorted.portals.Constants;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Item models for everything but block items, which {@link PortalsBlockstateProvider} models: the void
 * crystal and the striker, both flat sprites.
 */
public class PortalsItemModelProvider extends ItemModelProvider {

    public PortalsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Assorted Portals item models";
    }

    @Override
    protected void registerModels() {
        this.basicItem(PortalsItems.VOID_CRYSTAL.get());
        this.basicItem(PortalsItems.VOID_STRIKER.get());
    }
}
