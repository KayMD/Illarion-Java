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
package illarion.common.bug;

import javax.annotation.Nonnull;

/**
 * This is the definition of a factory that creates report dialogs.
 *
 * @author Martin Karing &lt;nitram@illarion.org&gt;
 */
@FunctionalInterface
public interface ReportDialogFactory {
    /**
     * Create a new instance of the report dialog.
     *
     * @return the instance of the report dialog that is supposed to be used.
     */
    @Nonnull
    ReportDialog createDialog();
}
