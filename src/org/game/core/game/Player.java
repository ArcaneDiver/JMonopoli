package org.game.core.game;

import org.game.gui.match.components.Buyable;

import javax.swing.*;
import java.util.ArrayList;

public class Player {

    private int UUID = (int) java.util.UUID.randomUUID().getLeastSignificantBits();

    private String name;
    private Long budget;
    private int position;

    private ArrayList<Buyable> properties;

    private ImageIcon pawn;
    private String pawnPath;

    public Player(String name, Long startBudget, String icon) {
        this.name = name;

        budget = startBudget;
        pawn = new ImageIcon(icon);
        pawnPath = icon;

        properties = new ArrayList<>();


        position = 0;
    }

    public void passedFromTheStart() {
        budget += Game.START_GIFT;
    }


    public boolean move(int steps) {
        if(position + steps > Game.STREET_LENGTH - 1) {
            position = steps - (Game.STREET_LENGTH - position);
            return true;
        } else {
            position += steps;
            return false;
        }
    }

    public boolean buy(int cost) {
        budget -= cost;

        return budget < 0;
    }

    public int getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Long getBudget() {
        return budget;
    }

    public void setProperties(ArrayList<Buyable> properties) {
        this.properties = properties;
    }

    public ArrayList<Buyable> getProperties() {
        return properties;
    }

    public ImageIcon getPawn() {
        if(pawn.getImage() == null) {
            pawn = new ImageIcon(pawnPath);
        }
        return pawn;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Player && ((Player) obj).getUUID() == UUID;
    }

    public int getUUID() {
        return UUID;
    }

    public void setUUID(int uuid) {
        this.UUID = uuid;
    }
}
