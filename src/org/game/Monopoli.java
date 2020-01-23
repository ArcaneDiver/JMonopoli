package org.game;

import org.game.gui.networkConnection.ConnectionManager;

public class Monopoli {
    public static void main(String[] args) {
        new ConnectionManager().startConnectionFlow();
        new ConnectionManager().startConnectionFlow(); // emulo un altro host (genera dei bug nell UI)
    }
}
