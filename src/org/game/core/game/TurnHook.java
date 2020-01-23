package org.game.core.game;

import org.game.gui.match.Window;
import org.json.JSONException;

public interface TurnHook {
    public void next(Window window);
    public void roll(Window window);
}
