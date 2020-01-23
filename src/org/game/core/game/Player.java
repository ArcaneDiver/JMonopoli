package org.game.core.game;

import org.game.gui.match.components.Buyable;

import javax.swing.*;
import java.util.ArrayList;

public class Player {

    private int UUID = (int) java.util.UUID.randomUUID().getLeastSignificantBits();
    private String name;
    private Integer budget;
    private ArrayList<Buyable> properties;
    private ImageIcon pawn;
    private String pawnPath;
    private int position;

    public Player(String name, int startBudget, String icon) {
        this.name = name;

        budget = startBudget;
        pawn = new ImageIcon(icon);
        pawnPath = icon;

        properties = new ArrayList<>();

        System.out.println("Player called for " + name);

        position = 0;
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

    public int getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getBudget() {
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
