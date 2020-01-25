package org.game.core.game;

import com.google.gson.Gson;
import javafx.util.Pair;

public final class Game {
    public static final byte MAX_NUMBER_OF_PLAYERS = 4;
    public static final short INITIAL_BUDGET = 1000;
    public static final byte STREET_LENGTH = 40;
    public static final short PORT = 31234;

    public static final Gson GSON = new Gson();

    private Game() { }

    public static Pair<Integer, Integer> roll() {
        return new Pair<>((int) (Math.random() * 6 + 1), (int) (Math.random() * 6 + 1));
    }
}

