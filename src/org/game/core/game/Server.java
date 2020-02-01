package org.game.core.game;

import com.google.gson.reflect.TypeToken;
import javafx.util.Pair;
import org.game.gui.match.Window;
import org.game.gui.match.components.Buyable;
import org.json.JSONException;
import org.json.JSONObject;
import xyz.farhanfarooqui.JRocket.JRocketServer;

import javax.swing.*;
import java.util.ArrayList;

public class Server {

    private JRocketServer server;
    private ArrayList<Player> players;
    private Player me;

    private Window window;

    public Server(JRocketServer server, Player me, ArrayList<Player> players) {

        this.server = server;
        this.players = players;

        this.me = me;

        players.add(this.me);

        initEvents();
        initUI(this.me);

    }

    private void initEvents() {
        server.onReceive("turn_done", (jsonObject, client) -> {
            try {
                Player playerThatDone = Game.GSON.fromJson(jsonObject.getString("player"), Player.class);
                players.get(players.indexOf(playerThatDone)).setProperty(playerThatDone.getProperty());

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
                server.send("roll_complete", new JSONObject()
                        .put("1", Game.GSON.toJson(roll.getKey(), new TypeToken<Integer>(){}.getType()))
                        .put("2", Game.GSON.toJson(roll.getValue(), new TypeToken<Integer>(){}.getType()))
                );

                window.movePawn(roll);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        server.onReceive("buy", (jsonObject, client) -> {
            try {
                Player buyer = Game.GSON.fromJson(jsonObject.getString("buyer"), new TypeToken<Player>(){}.getType());
                Buyable property = Game.GSON.fromJson(jsonObject.getString("property"), new TypeToken<Buyable>(){}.getType());

                buyer.buy(property.getCostRelativeToHouses(), property);
                players.set(players.indexOf(buyer), buyer);


                server.send("buy_complete", new JSONObject()
                    .put("buyer", Game.GSON.toJson(buyer, new TypeToken<Player>(){}.getType()))
                    .put("property", Game.GSON.toJson(property, new TypeToken<Buyable>(){}.getType()))
                );

                window.buyComplete(buyer, property);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        server.onReceive("pay", (jsonObject, client) -> {
            try {
                Player payer = Game.GSON.fromJson(jsonObject.getString("payer"), new TypeToken<Player>(){}.getType());
                Player toBePayed = Game.GSON.fromJson(jsonObject.getString("toBePayed"), new TypeToken<Player>(){}.getType());
                int howMuchPay = Game.GSON.fromJson(jsonObject.getString("money"), new TypeToken<Integer>(){}.getType());

                payer.pay(toBePayed, howMuchPay);

                players.set(players.indexOf(payer), payer);
                players.set(players.indexOf(toBePayed), toBePayed);


                server.send("pay_complete", new JSONObject()
                    .put("payer", Game.GSON.toJson(payer, new TypeToken<Player>(){}.getType()))
                    .put("toBePayed", Game.GSON.toJson(toBePayed, new TypeToken<Player>(){}.getType()))
                    .put("money", Game.GSON.toJson(howMuchPay, new TypeToken<Integer>(){}.getType()))
                );

                if(toBePayed.equals(me)) {
                    me = toBePayed;
                    window.updatePlayer(me);
                } else if(payer.equals(me)) {
                    me = payer;
                    window.updatePlayer(me);
                }



                window.payComplete(payer, toBePayed, howMuchPay);
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

                        window.updateProprierties(players);
                        window.startNewTurn(nextPlayer.getKey());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void roll(Window window) {
                    Pair<Integer, Integer> roll = Game.roll();

                    try {
                        server.send("roll_complete", new JSONObject()
                                .put("1", Game.GSON.toJson(roll.getKey(), new TypeToken<Integer>(){}.getType()))
                                .put("2", Game.GSON.toJson(roll.getValue(), new TypeToken<Integer>(){}.getType()))
                        );


                        window.movePawn(roll);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void buy(Window window, Buyable toBuy) {
                    me = window.getPlayingPlayer();
                    me.buy(toBuy.getCostRelativeToHouses(), toBuy);

                    players.set(players.indexOf(me), me);

                    try {
                        server.send("buy_complete", new JSONObject()
                                .put("buyer", Game.GSON.toJson(me, new TypeToken<Player>(){}.getType()))
                                .put("property", Game.GSON.toJson(toBuy, new TypeToken<Buyable>(){}.getType()))
                        );

                        window.updatePlayer(me);
                        window.buyComplete(me, toBuy);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void pay(Window window, Player toBePayed, int money) {
                    me.pay(toBePayed, money);

                    players.set(players.indexOf(me), me);
                    players.set(players.indexOf(toBePayed), toBePayed);

                    try {
                        server.send("pay_complete", new JSONObject()
                                .put("payer", Game.GSON.toJson(me, new TypeToken<Player>(){}.getType()))
                                .put("toBePayed", Game.GSON.toJson(toBePayed, new TypeToken<Player>(){}.getType()))
                                .put("money", Game.GSON.toJson(money, new TypeToken<Integer>(){}.getType()))
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    window.updatePlayer(me);
                    window.payComplete(me, toBePayed, money);
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
