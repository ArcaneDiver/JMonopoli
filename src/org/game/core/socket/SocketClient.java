package org.game.core.socket;

import org.json.JSONObject;
import org.json.JSONException;

import xyz.farhanfarooqui.JRocket.*;
import xyz.farhanfarooqui.JRocket.JRocketClient.RocketClientListener;

public class SocketClient {


    public SocketClient() {
        // Prepare the JRocketClient to connect to the server hosted at 127.0.0.1 on port 1234.
        JRocketClient rocketClient = JRocketClient.prepare("127.0.0.1", 1234, new RocketClientListener() {
            @Override
            public void onConnect(JRocketClient rocketClient) {
                try {
                    System.out.println("Connected to the server");
                    JSONObject data = new JSONObject();
                    data.put("name", "Farhan");
                    // Send an event named client_name to the server with data payload.
                    rocketClient.send("client_name", data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onConnectFailed(JRocketClient rocketClient) {
                System.out.println("Failed to connect");
            }

            @Override
            public void onDisconnect(JRocketClient rocketClient) {
                System.out.println("Disconnected");
            }
        });

        // Client will send and wait for a heartbeat every 3000 milliseconds. This must be called before calling connect()
        rocketClient.setHeartBeatRate(3000);
        rocketClient.connect();

        // Start listening for the event named greeting.
        rocketClient.onReceive("greeting", jsonObject -> {
            try {
                System.out.println(jsonObject.getString("response"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
