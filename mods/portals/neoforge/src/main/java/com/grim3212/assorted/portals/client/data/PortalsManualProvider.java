package com.grim3212.assorted.portals.client.data;

import com.grim3212.assorted.lib.data.LibManualProvider;
import com.grim3212.assorted.portals.Constants;
import com.grim3212.assorted.portals.common.block.PortalsBlocks;
import com.grim3212.assorted.portals.common.item.PortalsItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

/**
 * This part's chapter of the Assorted World section, which every part shares; the explicit chapter order keeps the
 * section's order whichever parts are installed. Every block and item has to open a page, or the provider refuses to generate.
 */
public class PortalsManualProvider extends LibManualProvider {

    public PortalsManualProvider(PackOutput output) {
        super(output, Constants.MOD_ID, Constants.FAMILY_ID);
    }

    @Override
    protected void addChapters() {
        ChapterBuilder endPortals = this.chapter("end_portals", 4);
        endPortals.items("void_crystal", PortalsBlocks.VOID_CRYSTAL_ORE.get(), PortalsItems.VOID_CRYSTAL.get()).opens(PortalsBlocks.VOID_CRYSTAL_ORE.get(), PortalsItems.VOID_CRYSTAL.get());
        endPortals.recipes("frame", PortalsBlocks.VOID_PORTAL_FRAME.get()).opens(PortalsBlocks.VOID_PORTAL_FRAME.get());
        endPortals.image("building", picture("void_portals"), 128, 104);
        endPortals.recipes("flames", PortalsItems.VOID_STRIKER.get()).opens(PortalsItems.VOID_STRIKER.get()).opensBlocks(PortalsBlocks.ENDER_FLAMES.getId(), PortalsBlocks.VOID_PORTAL.getId());
    }

    /** The screenshots under {@code textures/gui/manual}, sized to leave room for the text below. */
    private static Identifier picture(String name) {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/manual/" + name + ".png");
    }
}
