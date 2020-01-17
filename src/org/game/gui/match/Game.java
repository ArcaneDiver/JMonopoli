package org.game.gui.match;

import org.game.core.game.Player;
import xyz.farhanfarooqui.JRocket.JRocketClient;
import xyz.farhanfarooqui.JRocket.JRocketServer;

public class Game {

    public static int INITIAL_BUDGET = 1000;

    private Player player;
    private Window window;

    public Game(JRocketClient client, String name) {
        System.out.println("started as a client");

        player = new Player(name, INITIAL_BUDGET);
        window = new Window(player);
    }

    public Game(JRocketServer server, String name) {
        System.out.println("started as a server");

        player = new Player(name, INITIAL_BUDGET);
        window = new Window(player);
    }
}
