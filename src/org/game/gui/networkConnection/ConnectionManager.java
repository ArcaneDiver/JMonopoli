package org.game.gui.networkConnection;

import org.game.core.game.Client;
import org.game.core.game.Game;
import org.game.core.game.Server;

import javax.swing.*;

public class ConnectionManager {

    private Boolean isServer = null;

    public ConnectionManager() {

    }

    public void startConnectionFlow() {
        chooseConnectionType();
    }

    // Se un idea per rendere asincrono un callback senza un while(true)...
    private void chooseConnectionType() {
        SwingUtilities.invokeLater(() -> new ConnectionType(choose -> {
            isServer = choose;
            if(isServer) {
                asServer();
            } else {
                asClient();
            }
        }));
    }

    private void asServer() {
        SwingUtilities.invokeLater(() -> new ServerManager(Game.PORT, Server::new));
    }

    private void asClient() {
        SwingUtilities.invokeLater(() -> new ClientManager(Game.PORT, Client::new));
    }
}
