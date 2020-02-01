package org.game.core.game;

import org.game.gui.match.Window;
import org.game.gui.match.components.Buyable;
import org.json.JSONException;

public interface TurnHook {
    public void next(Window window);
    public void roll(Window window);
    public void buy(Window window, Buyable toBuy);
    public void pay(Window window, Player toBePayed, int money);
    public void loose(Window window);
}
