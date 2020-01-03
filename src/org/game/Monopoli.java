package org.game;

import org.game.core.socket.*;
import org.game.core.system.*;
import org.game.gui.networkConnection.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;

public class Monopoli {
    public static void main(String[] args) throws IOException {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.startConnectionFlow();
    }
}
