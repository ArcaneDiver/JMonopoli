package org.game.core.game;

import com.google.gson.annotations.Expose;
import org.game.gui.match.components.Buyable;

import javax.swing.*;
import java.util.ArrayList;

public class Player {


    @Expose private int UUID = (int) java.util.UUID.randomUUID().getLeastSignificantBits();

    @Expose private String name;
    @Expose private Long budget;
    @Expose private int position;
    @Expose private ArrayList<Buyable> property;
    @Expose private boolean deported;
    @Expose private boolean inGame;

    @Expose private ImageIcon pawn;
    @Expose private String pawnPath;

    public Player(String name, Long startBudget, String icon) {
        this.name = name;

        budget = startBudget;
        pawn = new ImageIcon(icon);
        pawnPath = icon;

        property = new ArrayList<>();

        deported = false;
        inGame = true;
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

    public boolean buy(int cost, Buyable propertyToBuy) {
        budget -= cost;

        property.add(propertyToBuy);

        return budget < 0;
    }

    public boolean pay(Player toBePayed, int money) {
        budget -= money;

        toBePayed.setBudget(toBePayed.getBudget() + (budget < 0 ? money - ( - budget) : money));

        return budget < 0;
    }

    public boolean isDeported() {
        return deported;
    }

    public void deportToTheGulag() {
        position = 10;
        deported = true;
    }

    public void releaseFromTheGulag() {
        deported = false;
    }

    public void loose() {
        inGame = false;
    }

    public boolean isInGame() {
        return inGame;
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

    public void setProperty(ArrayList<Buyable> property) {
        this.property = property;
    }

    public ArrayList<Buyable> getProperty() {
        return property;
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

    @Override
    public String toString() {
        return String.format("Name: %s, Property: %s, Position: %s, Budget: %s", name, property, position, budget);
    }
}
