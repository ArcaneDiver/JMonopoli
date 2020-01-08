package org.game.gui.match;

import xyz.farhanfarooqui.JRocket.JRocketClient;
import xyz.farhanfarooqui.JRocket.JRocketServer;

public class Game {

    public Game(JRocketClient client) {
        System.out.println("started as a client");

        new Window();
    }

    public Game(JRocketServer server) {
        System.out.println("started as a server");

        new Window();
    }
}
