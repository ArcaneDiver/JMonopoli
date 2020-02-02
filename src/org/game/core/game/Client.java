package org.game.core.game;

import com.google.gson.reflect.TypeToken;
import javafx.util.Pair;
import org.game.gui.match.Window;
import org.game.gui.match.components.Buyable;
import org.json.JSONException;
import org.json.JSONObject;
import xyz.farhanfarooqui.JRocket.JRocketClient;

import javax.swing.*;
import java.lang.reflect.Type;
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
                ArrayList<Player> players = Game.GSON.fromJson(jsonObject.getString("players"), new TypeToken<ArrayList<Player>>(){}.getType());


                window.updateProprierties(players);
                window.startNewTurn(player);

                if(jsonObject.has("event")) {
                    GlobalEvent event = Game.GSON.fromJson(jsonObject.getString("event"), new TypeToken<GlobalEvent>(){}.getType());
                    window.setActiveEvent(event);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        client.onReceive("roll_complete", jsonObject -> {
            System.out.println(me.getName() + " has got a roll response");
            try {
                Pair<Integer, Integer> rollData = new Pair<>(
                        Game.GSON.fromJson(jsonObject.getString("1"), new TypeToken<Integer>() {}.getType()),
                        Game.GSON.fromJson(jsonObject.getString("2"), new TypeToken<Integer>() {}.getType())
                );

                window.movePawn(rollData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        client.onReceive("buy_complete", jsonObject -> {
            try {
                Player buyer = Game.GSON.fromJson(jsonObject.getString("buyer"), new TypeToken<Player>() {}.getType());
                Buyable property = Game.GSON.fromJson(jsonObject.getString("property"), new TypeToken<Buyable>() {}.getType());

                if(buyer.equals(me)) {
                    me = buyer;
                    window.updatePlayer(me);
                }

                window.buyComplete(buyer, property);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        client.onReceive("pay_complete", jsonObject -> {
            try {
                Player whoPaid = Game.GSON.fromJson(jsonObject.getString("payer"), new TypeToken<Player>(){}.getType());
                Player whoGetPaid = Game.GSON.fromJson(jsonObject.getString("toBePayed"), new TypeToken<Player>(){}.getType());
                int howMuchToPay = Game.GSON.fromJson(jsonObject.getString("money"), new TypeToken<Integer>(){}.getType()) ;

                // Aggiorno l`intanza del mio player
                if(whoGetPaid.equals(me)) {
                    me = whoGetPaid;

                    window.updatePlayer(me);
                } else if(whoPaid.equals(me)) {
                    me = whoPaid;

                    window.updatePlayer(me);
                }


                window.payComplete(whoPaid, whoGetPaid, howMuchToPay);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        client.onReceive("unforeseen_applied", jsonObject -> {
            try {
                Player requester = Game.GSON.fromJson(jsonObject.getString("player"), new TypeToken<Player>(){}.getType());
                UnforeseenEvent event = Game.GSON.fromJson(jsonObject.getString("event"), new TypeToken<UnforeseenEvent>(){}.getType());

                if(requester.equals(me)) {
                    me = requester;
                    window.updatePlayer(me);
                }

                window.showUnforeseen(requester, event);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });


        client.onReceive("done_game", jsonObject -> {

            try {
                Player playerThatLoose = Game.GSON.fromJson(jsonObject.getString("player"), new TypeToken<Player>(){}.getType());

                if(me.equals(playerThatLoose)) {
                    client.disconnect();
                    window.dispose();
                }

                window.loosePlayer(playerThatLoose);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void initUI () {
        SwingUtilities.invokeLater(() -> window = new Window(me, new TurnHook() {
            @Override
            public void next(Window window) {
                me = window.getPlayingPlayer();
                try {
                    client.send("turn_done", new JSONObject()
                            .put("player", Game.GSON.toJson(me, new TypeToken<Player>(){}.getType()))
                    );

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void roll(Window window) {
                me = window.getPlayingPlayer();

                System.out.println(me.getName() + " has request a roll");
                client.send("roll", new JSONObject());
            }

            @Override
            public void buy(Window window, Buyable toBuy) {
                me = window.getPlayingPlayer();

                try {
                    client.send("buy", new JSONObject()
                        .put("buyer", Game.GSON.toJson(me, new TypeToken<Player>(){}.getType()))
                        .put("property", Game.GSON.toJson(toBuy, new TypeToken<Buyable>(){}.getType()))
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void pay(Window window, Player toBePayed, int money) {
                me = window.getPlayingPlayer();

                try {
                    client.send("pay", new JSONObject()
                        .put("payer", Game.GSON.toJson(me, new TypeToken<Player>(){}.getType()))
                        .put("toBePayed", Game.GSON.toJson(toBePayed, new TypeToken<Player>(){}.getType()))
                        .put("money", Game.GSON.toJson(money, new TypeToken<Integer>(){}.getType()))
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestUnforeseen(Window window) {
                me = window.getPlayingPlayer();

                try {
                    client.send("unforeseen", new JSONObject()
                            .put("player", Game.GSON.toJson(me, new TypeToken<Player>(){}.getType()))
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void soapDropped(Window window) {
                me = window.getPlayingPlayer();

                try {
                    client.send("soap_dropped", new JSONObject()
                            .put("player", Game.GSON.toJson(me))
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }));
    }
}
