/*
 * This file is part of the Illarion project.
 *
 * Copyright © 2015 - Illarion e.V.
 *
 * Illarion is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Illarion is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package illarion.common.graphics;

/**
 * Utility class that holds a few constants that are needed to define the map
 * correctly.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class MapConstants {
    /**
     * Step size in X direction. Means how much pixels the map as to scroll to
     * reach the next tile. Since the tiles on one row are optical half shifted
     * against each other, the step size is always half of the tile width in X
     * direction.
     */
    public static final int STEP_X;

    /**
     * Step size in Y direction. Means how much pixels the map as to scroll to
     * reach the next tile. Since the tiles on one row are optical half shifted
     * against each other, the step size is always half of the tile height in Y
     * direction.
     */
    public static final int STEP_Y;

    /**
     * Height of a tile in pixels. This should fit to the size of the images to
     * ensure that it looks good.
     */
    public static final int TILE_H = 37;

    /**
     * Width of a tile in pixels. This should fit to the size of the images to
     * ensure that it looks good.
     */
    public static final int TILE_W = 76;

    static {
        STEP_X = TILE_W / 2;
        STEP_Y = (TILE_H + 1) / 2;
    }

    /**
     * Private constructor to ensure that nothing can create a instance of this
     * utility class.
     */
    private MapConstants() {
        // private constructor does nothing at all
    }
}
