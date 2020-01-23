package org.game.gui.networkConnection;

import org.game.core.game.Player;
import xyz.farhanfarooqui.JRocket.JRocketServer;

import java.util.ArrayList;

interface StartGameAsServer {
    void start(JRocketServer server, Player name, ArrayList<Player> players);
}
