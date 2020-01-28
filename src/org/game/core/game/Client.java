package org.game.core.game;

import com.google.gson.reflect.TypeToken;
import javafx.util.Pair;
import org.game.gui.match.Window;
import org.json.JSONException;
import org.json.JSONObject;
import xyz.farhanfarooqui.JRocket.JRocketClient;

import javax.swing.*;
import java.util.ArrayList;

public class Client  {
    private JRocketClient client;
    private Player me;

    private Window window;

    public Client(JRocketClient client, Player me) {

        this.client = client;
        this.me = me;

        initEvents();
        initUI();

    }

    private void initEvents() {
        client.onReceive("start_game", jsonObject -> {
            try {
                ArrayList<Player> players = Game.GSON.fromJson(jsonObject.getString("players"), new TypeToken<ArrayList<Player>>(){}.getType());
                System.out.println("I am " + me.getName() + " and Players recived => " + players);
                window.startGame(players);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        client.onReceive("new_turn", jsonObject -> {
            try {
                Player player = Game.GSON.fromJson(jsonObject.getString("player"), Player.class);

                System.out.println(me.getName() + " turno di " + player.getName());

                window.startNewTurn(player);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        client.onReceive("roll_res", jsonObject -> {
            System.out.println(me.getName() + " has got a roll response");
            try {
                Pair<Integer, Integer> rollData = Game.GSON.fromJson(jsonObject.getString("data"), new TypeToken<Pair<Integer, Integer>>(){}.getType());
                window.movePawn(rollData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void initUI () {
        SwingUtilities.invokeLater(() -> window = new Window(me, new TurnHook() {
            @Override
            public void next(Window window) {
                try {
                    System.out.println(me.getName() + " has done the turn");
                    client.send("turn_done", new JSONObject()
                            .put("player", Game.GSON.toJson(me))
                    );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void roll(Window window) {
                System.out.println(me.getName() + " has request a roll");
                client.send("roll", new JSONObject());
            }
        }));
    }
}
