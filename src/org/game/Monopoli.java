package org.game;

import org.game.gui.networkConnection.ConnectionManager;

public class Monopoli {
    public static void main(String[] args) {
        new ConnectionManager().startConnectionFlow();
        new ConnectionManager().startConnectionFlow();
    }
}
