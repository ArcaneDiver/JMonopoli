package org.game.gui.match;

import org.game.core.game.Player;
import org.json.JSONObject;
import xyz.farhanfarooqui.JRocket.JRocketClient;
import xyz.farhanfarooqui.JRocket.JRocketServer;

import javax.swing.*;

public class Game {

    public static int INITIAL_BUDGET = 1000;

    private Player player;
    private Window window;

    public Game(JRocketClient client, String name) {
        System.out.println("started as a client");

        player = new Player(name, INITIAL_BUDGET, new ImageIcon("assets/pawn.jpg"));

        client.onReceive("new_turn", jsonObject -> window.startNewTurn());

        window = new Window(player, (window) -> {

            client.send("turn_done", new JSONObject());
        });

    }

    public Game(JRocketServer server, String name) {
        System.out.println("started as a server");

        player = new Player(name, INITIAL_BUDGET, new ImageIcon("assets/pawn.jpg"));
        window = new Window(player, (window) -> {
            server.send("new_turn", new JSONObject());
            window.startNewTurn();
        });

    }
}
