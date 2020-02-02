package org.game.core.game;

import org.game.gui.match.Window;
import org.game.gui.match.components.Buyable;
import org.json.JSONException;

public interface TurnHook {
    void next(Window window);
    void roll(Window window);
    void buy(Window window, Buyable toBuy);
    void pay(Window window, Player toBePayed, int money);
    void requestUnforeseen(Window window);
    void soapDropped(Window window);
}
