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
package illarion.client.loading;

import illarion.client.resources.*;
import illarion.client.resources.loaders.*;
import illarion.common.util.ProgressMonitor;
import org.illarion.engine.Engine;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to allow the loading sequence of the client to load the resource tables.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
@NotThreadSafe
final class ResourceTableLoading implements LoadingTask {
    /**
     * The progress monitor that tracks the loading activity of this task.
     */
    @Nonnull
    private final ProgressMonitor progressMonitor;

    /**
     * The list of tasks that need to be finished during the resource table loading.
     */
    @Nonnull
    private final List<AbstractResourceLoader<? extends Resource>> taskList;

    /**
     * Create a new resource table loading task and enlist all the sub-tasks.
     *
     * @param gameEngine the engine of the game
     */
    ResourceTableLoading(@Nonnull Engine gameEngine) {
        taskList = new ArrayList<>();
        progressMonitor = new ProgressMonitor();

        addTask(new TileLoader(gameEngine.getAssets()), TileFactory.getInstance());
        addTask(new OverlayLoader(gameEngine.getAssets()), OverlayFactory.getInstance());
        addTask(new ItemLoader(gameEngine.getAssets()), ItemFactory.getInstance());
        addTask(new CharacterLoader(gameEngine.getAssets()), CharacterFactory.getInstance());
        addTask(new ClothLoader(gameEngine.getAssets()), new ClothFactoryRelay());
        addTask(new EffectLoader(gameEngine.getAssets()), EffectFactory.getInstance());
        addTask(new MiscImageLoader(gameEngine.getAssets()), MiscImageFactory.getInstance());
        addTask(new BookLoader(), BookFactory.getInstance());
    }

    /**
     * Add a task to the list of tasks and to the progress monitor.
     *
     * @param loader the loader of this task
     * @param factory the factory that is supposed to be filled
     * @param <T> the resource type that is load in this case
     */
    private <T extends Resource> void addTask(
            @Nonnull AbstractResourceLoader<T> loader, @Nonnull ResourceFactory<T> factory) {
        loader.setTarget(factory);
        progressMonitor.addChild(loader.getProgressMonitor());
        taskList.add(loader);
    }

    @Override
    public void load() {
        if (!taskList.isEmpty()) {
            AbstractResourceLoader<? extends Resource> loader = taskList.remove(0);
            try {
                loader.call();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
    }

    @Override
    public boolean isLoadingDone() {
        for (AbstractResourceLoader<? extends Resource> loader : taskList) {
            if (!loader.isLoadingDone()) {
                return false;
            }
        }

        return true;
    }

    @Nonnull
    @Override
    public ProgressMonitor getProgressMonitor() {
        return progressMonitor;
    }
}
