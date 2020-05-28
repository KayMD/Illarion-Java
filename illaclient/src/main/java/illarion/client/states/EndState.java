/*
 * This file is part of the Illarion project.
 *
 * Copyright © 2014 - Illarion e.V.
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
package illarion.client.states;

import de.lessvoid.nifty.Nifty;
import illarion.client.Game;
import org.illarion.engine.GameContainer;

import javax.annotation.Nonnull;

/**
 * This state is activated during the shutdown of the game.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
public final class EndState implements GameState {

    @Override
    public void create(@Nonnull Game game, @Nonnull GameContainer container, @Nonnull Nifty nifty) {
        Util.loadXML(nifty, "illarion/client/gui/xml/shutdown.xml");
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(@Nonnull GameContainer container, int width, int height) {
    }

    @Override
    public void update(@Nonnull GameContainer container, int delta) {
    }

    @Override
    public void render(@Nonnull GameContainer container) {
    }

    @Override
    public boolean isClosingGame() {
        return true;
    }

    @Override
    public void enterState(@Nonnull GameContainer container, @Nonnull Nifty nifty) {
        nifty.gotoScreen("clientEnd");
    }

    @Override
    public void leaveState(@Nonnull GameContainer container) {
    }
}
