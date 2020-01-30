package org.game.core.game;

import com.google.gson.reflect.TypeToken;
import javafx.util.Pair;
import org.game.gui.match.Window;
import org.json.JSONException;
import org.json.JSONObject;
import xyz.farhanfarooqui.JRocket.JRocketServer;

import javax.swing.*;
import java.util.ArrayList;

public class Server {

    private JRocketServer server;
    private ArrayList<Player> players;
    private Player player;

    private Window window;

    public Server(JRocketServer server, Player me, ArrayList<Player> players) {

        this.server = server;
        this.players = players;

        player = me;

        players.add(player);

        initEvents();
        initUI(player);

    }

    private void initEvents() {
        server.onReceive("turn_done", (jsonObject, client) -> {
            try {
                Player playerThatDone = Game.GSON.fromJson(jsonObject.getString("player"), Player.class);
                Pair<Player, Boolean> playerOfNextTurn = getNextPlayerInTheTurn(playerThatDone);

                server.send("new_turn", new JSONObject()
                        .put("player", Game.GSON.toJson(playerOfNextTurn.getKey(), Player.class))
                        .put("players", Game.GSON.toJson(players, new TypeToken<ArrayList<Player>>(){}.getType()))
                );

                window.startNewTurn(playerOfNextTurn.getKey());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        server.onReceive("roll", (jsonObject, client) -> {
            Pair<Integer, Integer> roll = Game.roll();

            try {
                server.send("roll_res", new JSONObject()
                        .put("data", Game.GSON.toJson(roll, new TypeToken<Pair<Integer, Integer>>(){}.getType()))
                );

                window.movePawn(roll);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void initUI (Player player) {
        SwingUtilities.invokeLater(() -> {
            window = new Window(player, new TurnHook() {
                @Override
                public void next(Window window) {
                    try {
                        Pair<Player, Boolean> nextPlayer = getNextPlayerInTheTurn(player);

                        server.send("new_turn", new JSONObject()
                                .put("player", Game.GSON.toJson(nextPlayer.getKey(), new TypeToken<Player>(){}.getType()))
                                .put("players", Game.GSON.toJson(players, new TypeToken<ArrayList<Player>>(){}.getType()))
                        );

                        window.startNewTurn(nextPlayer.getKey());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void roll(Window window) {
                    Pair<Integer, Integer> roll = Game.roll();

                    try {
                        server.send("roll_res", new JSONObject()
                                .put("data", Game.GSON.toJson(roll, new TypeToken<Pair<Integer, Integer>>(){}.getType()))
                        );

                        window.movePawn(roll);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void loose(Window window) {

                }
            });

            try {
                server.send("start_game", new JSONObject()
                        .put("players", Game.GSON.toJson(players, new TypeToken<ArrayList<Player>>(){}.getType()))
                );
                window.startGame(players);

                server.send("new_turn", new JSONObject()
                        .put("player", Game.GSON.toJson(player, Player.class))
                        .put("players", Game.GSON.toJson(players, new TypeToken<ArrayList<Player>>(){}.getType()))
                );

                window.startNewTurn(player);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        );
    }

    private Pair<Player, Boolean> getNextPlayerInTheTurn(Player lastPlayer) {
        int position = players.indexOf(lastPlayer);

        if(position == players.size() - 1) {
            return new Pair<>(players.get(0), true);
        } else {
            return new Pair<>(players.get(position + 1), false);
        }
    }
}
