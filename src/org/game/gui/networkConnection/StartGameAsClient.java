package org.game.gui.networkConnection;

import org.game.core.game.Player;
import xyz.farhanfarooqui.JRocket.JRocketClient;

interface StartGameAsClient {
    void start(JRocketClient client, Player me);
}
