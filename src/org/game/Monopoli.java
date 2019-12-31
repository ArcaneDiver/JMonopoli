package org.game;

import org.game.core.socket.*;
import org.game.core.system.*;

import java.io.IOException;

public class Monopoli {
    public static void main(String[] args) throws IOException {
        SocketServer server = new SocketServer();
        SocketClient client = new SocketClient();


        Network.getNetworkIPs();
    }
}
