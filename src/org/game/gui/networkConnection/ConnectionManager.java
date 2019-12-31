package org.game.gui.networkConnection;

import javax.swing.*;

public class ConnectionManager {

    private Boolean isServer = null;

    public ConnectionManager() {

    }

    public void startConnectionFlow() {
        chooseConnectionType();

        if(isServer) {
            asServer();
        } else {
            asClient();
        }
    }

    // Se avete un altra idea per rendere sincrono un callback
    private void chooseConnectionType() {
        new ConnectionType(choose -> isServer = choose);
        while(true) {
            if(isServer != null) break;
        }
    }

    private void asServer() {

    }

    private void asClient() {

    }
}
