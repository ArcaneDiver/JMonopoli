package org.game.core.socket;

import xyz.farhanfarooqui.JRocket.*;

public class SocketClient {

    private Client client;
    private String name;

    public SocketClient(Client client, String name) {

        this.client = client;
        this.name = name;
    }

    public Client getClient() {
        return client;
    }

    public String getName() {
        return name;
    }
}
