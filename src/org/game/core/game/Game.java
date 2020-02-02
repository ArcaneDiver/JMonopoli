package org.game.core.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.util.Pair;


import java.util.*;

public final class Game {
    public static final byte MAX_NUMBER_OF_PLAYERS = 4;
    public static final long INITIAL_BUDGET = 900;
    public static final int START_GIFT = 60000;
    public static final byte STREET_LENGTH = 40;
    public static final byte GULAG_POSITION = 10;
    public static final short PORT = 31234;
    public static final String RUBLO = "RUB";
    public static final int DEPORT_TIME = 3;

    public static final HashMap<PropertyType, Double> EVENT_MULTIPLIER = new HashMap<PropertyType, Double>(){
        @Override
        public Double get(Object key) {
            if(!containsKey(key))
                return 1.0;
            return super.get(key);
        }
    };

    public static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();


    private Game() { }

    public static Pair<Integer, Integer> roll() {
        return new Pair<>((int) (Math.random() * 6 + 1), (int) (Math.random() * 6 + 1));
    }
}

