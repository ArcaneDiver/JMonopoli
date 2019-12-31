package org.game.core.socket;

import org.json.JSONObject;
import org.json.JSONException;

import xyz.farhanfarooqui.JRocket.*;
import xyz.farhanfarooqui.JRocket.ServerListeners.*;

import java.io.IOException;

public class SocketServer {

    public SocketServer() {
        try {
            // Start listening to port 1234 and set core thread pool size to 1000. Each client requires 2 threads, so we'll be handling 500 clients at a time.
            JRocketServer rocketServer = JRocketServer.listen(1234, 1000);

            // Client will send and wait for a hearbeat every 3000 milliseconds.
            rocketServer.setHeartBeatRate(3000);


            rocketServer.setOnClientConnectListener(new OnClientConnectListener() {

                @Override
                public void onClientConnect(Client client) {
                    System.out.println("New client connected. ID: " + client.getId());
                }
            });

            rocketServer.setOnClientDisconnectListener(new OnClientDisconnectListener() {
                @Override
                public void onClientDisconnect(Client client) {
                    System.out.println("Client disconnected. ID: " + client.getId());
                }
            });

            // Start listening for the event named client_name.
            rocketServer.onReceive("client_name", (data, client) -> {
                try {
                    System.out.println("Client " + client.getId() + " send its name. Name : " + data.getString("name"));
                    // Store the name in the client object.
                    client.put("name", data.getString("name"));
                    JSONObject responseData = new JSONObject();
                    responseData.put("response", "Hey, " + data.getString("name") + "!");
                    // Send an event to the client with a data payload
                    client.send("greeting", responseData);
                    rocketServer.disconnect(client);
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}