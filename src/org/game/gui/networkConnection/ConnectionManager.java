package org.game.gui.networkConnection;

import javax.swing.*;

public class ConnectionManager {

    private Boolean isServer = null;

    public ConnectionManager() {

    }

    public void startConnectionFlow() {
        chooseConnectionType();
        System.out.println(isServer);

    }

    // Se avete un altra idea per rendere sincrono un callback
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
        SwingUtilities.invokeLater(() -> new ServerManager(30001, (server) -> {
            System.out.println(server);

            // Instanzi la finestra della partita
        }));
    }

    private void asClient() {
        SwingUtilities.invokeLater(() -> new ClientManager(30001, (client -> {
            System.out.println(client);
        })));
    }
}