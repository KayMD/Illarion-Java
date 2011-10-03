/*
 * This file is part of the Illarion Graphics Engine.
 *
 * Copyright © 2011 - Illarion e.V.
 *
 * The Illarion Graphics Engine is free software: you can redistribute i and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * The Illarion Graphics Engine is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * the Illarion Graphics Interface. If not, see <http://www.gnu.org/licenses/>.
 */
package illarion.graphics.lwjgl;

import java.lang.ref.WeakReference;

import illarion.graphics.Graphics;

import org.lwjgl.opengl.GL11;

/**
 * This is a helper class for the LWJGL render to switch the current render
 * mode. It ensures that the driver calls are only called in case its really
 * needed.
 * 
 * @author Martin Karing
 * @version 2.00
 * @since 2.00
 */
public final class DriverSettingsLWJGL {
    /**
     * The different modes that are supported by this driver settings.
     * 
     * @author Martin Karing
     * @since 2.00
     * @version 2.00
     */
    public enum Modes {
        /**
         * This mode should be used in case single dots are supposed to be
         * drawn.
         */
        DRAWDOT(new SettingsHandler() {
            @Override
            public void disableSettings() {
                GL11.glDisable(GL11.GL_POINT_SMOOTH);
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            }

            @Override
            public void enableSettings() {
                final int quality = Graphics.getInstance().getQuality();

                if (quality >= Graphics.QUALITY_NORMAL) {
                    GL11.glEnable(GL11.GL_POINT_SMOOTH);
                    if (quality >= Graphics.QUALITY_HIGH) {
                        GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
                    } else {
                        GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_FASTEST);
                    }
                } else {
                    GL11.glDisable(GL11.GL_POINT_SMOOTH);
                }
                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            }
        }),

        /**
         * This mode should be used in case lines are supposed to be drawn.
         */
        DRAWLINE(new SettingsHandler() {
            @Override
            public void disableSettings() {
                GL11.glDisable(GL11.GL_LINE_SMOOTH);
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            }

            @Override
            public void enableSettings() {
                final int quality = Graphics.getInstance().getQuality();
                if (quality >= Graphics.QUALITY_NORMAL) {
                    GL11.glEnable(GL11.GL_LINE_SMOOTH);
                    if (quality >= Graphics.QUALITY_HIGH) {
                        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
                    } else {
                        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_FASTEST);
                    }
                } else {
                    GL11.glDisable(GL11.GL_LINE_SMOOTH);
                }
                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            }
        }),

        /**
         * This mode should be used for any vertex array based shapes to draw.
         * The difference to the polygon mode is that the smoothing is disabled.
         */
        DRAWOTHER(new SettingsHandler() {
            @Override
            public void disableSettings() {
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            }

            @Override
            public void enableSettings() {
                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            }
        }),

        /**
         * This mode should be used for any vertex array based shapes to draw.
         * The difference to the polygon mode is that the smoothing is disabled.
         * In addition this mode also enables the color array.
         */
        DRAWOTHERCOLOR(new SettingsHandler() {
            @Override
            public void disableSettings() {
                GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            }

            @Override
            public void enableSettings() {
                GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            }
        }),

        /**
         * This mode should be used to draw a polygone with line smoothing.
         */
        DRAWPOLY(new SettingsHandler() {
            @Override
            public void disableSettings() {
                GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            }

            @Override
            public void enableSettings() {
                final int quality = Graphics.getInstance().getQuality();
                if (quality == Graphics.QUALITY_MAX) {
                    GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
                    GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
                }
                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            }
        }),

        /**
         * This mode should be used to draw a texture directly.
         */
        DRAWTEXTURE(new SettingsHandler() {
            @Override
            public void disableSettings() {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
            }

            @Override
            public void enableSettings() {
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }
        }),

        /**
         * This mode should be used to drawn a texture using a texture pointer.
         */
        DRAWTEXTUREPOINTER(new SettingsHandler() {
            @Override
            public void disableSettings() {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
                GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            }

            @Override
            public void enableSettings() {
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            }
        });

        /**
         * The settings handler of this instance.
         */
        private final SettingsHandler setHandler;

        /**
         * Constructor of a modes instance that takes the required settings
         * handler as parameter.
         * 
         * @param handler the handler
         */
        private Modes(final SettingsHandler handler) {
            setHandler = handler;
        }

        /**
         * Disable a mode.
         */
        protected void disable() {
            setHandler.disableSettings();
        }

        /**
         * Enable a mode.
         */
        protected void enable() {
            setHandler.enableSettings();
        }
    }

    /**
     * This interface is used to enable and disable the different modes of this
     * driver.
     * 
     * @author Martin Karing
     * @since 2.00
     * @version 2.00
     */
    private interface SettingsHandler {
        /**
         * Disable the settings that are controlled by this handler.
         */
        public void disableSettings();

        /**
         * Enable the settings that are controlled by this handler.
         */
        public void enableSettings();
    }

    /**
     * The singleton instance of this class.
     */
    private static final DriverSettingsLWJGL INSTANCE =
        new DriverSettingsLWJGL();

    /**
     * Get the singleton instance of this class.
     * 
     * @return the singleton instance of this helper class
     */
    public static DriverSettingsLWJGL getInstance() {
        return INSTANCE;
    }

    /**
     * The currently used modus.
     */
    private Modes currentMode = null;

    /**
     * Private constructor, to avoid any instances but the singleton instance.
     */
    private DriverSettingsLWJGL() {
        // nothing to do
    }

    /**
     * Activate a new driver mode.
     * 
     * @param gl the instance of the openGL interface
     * @param newMode the new mode to enable
     */
    public void enableMode(final Modes newMode) {
        enableMode(newMode, null);
    }

    /**
     * Reset the driver so any change causes that a newly selected mode is
     * properly enabled for sure.
     */
    public void reset() {
        currentMode = null;
        activeTexture = null;
        SpriteColorLWJGL.resetActiveColor();
    }

    /**
     * Activate a new driver mode.
     * 
     * @param gl the instance of the openGL interface
     * @param newMode the new mode to enable
     * @param texture the texture to bind to this new mode. This only has any
     *            effect in case a texture mode is used
     */
    public void enableMode(final Modes newMode,
        final TextureAtlasLWJGL texture) {
        enableTexture(newMode, texture);

        if (currentMode == newMode) {
            return;
        }
        if (currentMode != null) {
            currentMode.disable();
        }
        if (newMode != null) {
            newMode.enable();
        }
        currentMode = newMode;
    }

    /**
     * The texture that was last activated.
     */
    private WeakReference<TextureAtlasLWJGL> activeTexture;

    /**
     * This internal function takes care for enabling and disabling textures
     * as needed.
     * 
     * @param gl the instance of the openGL interface
     * @param newMode the new mode to enable
     * @param texture the texture to bind
     */
    private void enableTexture(final Modes mode,
        final TextureAtlasLWJGL texture) {
        TextureAtlasLWJGL activeTexAtlas = null;
        if (activeTexture != null) {
            activeTexAtlas = activeTexture.get();
        }

        if (mode != Modes.DRAWTEXTURE && mode != Modes.DRAWTEXTUREPOINTER) {
            if (activeTexAtlas != null) {
                activeTexAtlas.disable();
                activeTexture = null;
            }
            return;
        }

        if (activeTexAtlas == texture) {
            return;
        }

        if (texture != null) {
            texture.enable();
            activeTexture = new WeakReference<TextureAtlasLWJGL>(texture);
        } else if (activeTexAtlas != null) {
            activeTexAtlas.disable();
            activeTexture = null;
        }
    }
}