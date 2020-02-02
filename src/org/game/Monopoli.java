package org.game;

import org.game.core.system.Network;
import org.game.gui.networkConnection.ConnectionManager;

public class Monopoli {
    public static void main(String[] args) {
        Network.getLocalData();
        new ConnectionManager().startConnectionFlow();
        new ConnectionManager().startConnectionFlow();
    }
}
