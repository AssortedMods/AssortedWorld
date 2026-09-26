package com.grim3212.assorted.portals.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * The inside of a void portal ring: {@code width} blocks along {@code across} and {@code height}
 * along {@code up} from {@code corner}, in the plane whose normal is {@code axis}.
 */
public record VoidPortalRect(BlockPos corner, Direction.Axis axis, Direction across, Direction up, int width, int height) {

    public List<BlockPos> interior() {
        List<BlockPos> inside = new ArrayList<>(this.width * this.height);
        for (int a = 0; a < this.width; a++) {
            for (int b = 0; b < this.height; b++) {
                inside.add(this.at(a, b));
            }
        }
        return inside;
    }

    /** One frame against each interior edge block; the corners are left open, as vanilla leaves them. */
    public List<BlockPos> ring() {
        List<BlockPos> slots = new ArrayList<>(2 * (this.width + this.height));
        for (int a = 0; a < this.width; a++) {
            slots.add(this.at(a, -1));
            slots.add(this.at(a, this.height));
        }
        for (int b = 0; b < this.height; b++) {
            slots.add(this.at(-1, b));
            slots.add(this.at(this.width, b));
        }
        return slots;
    }

    public BlockPos centre() {
        return this.at(this.width / 2, this.height / 2);
    }

    private BlockPos at(int a, int b) {
        return this.corner.relative(this.across, a).relative(this.up, b);
    }
}
