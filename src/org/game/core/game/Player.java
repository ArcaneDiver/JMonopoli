package org.game.core.game;

import org.game.gui.match.components.Buyable;

import javax.swing.*;
import java.util.ArrayList;

public class Player {

    private String name;
    private Integer budget;
    private ArrayList<Buyable> properties;
    private ImageIcon pawn;
    private int position;

    public Player(String name, int startBudget, ImageIcon icon) {
        this.name = name;

        budget = startBudget;
        pawn = icon;

        properties = new ArrayList<>();

        position = 0;
    }


    public void move(int steps) {
        position += steps;
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
        return pawn;
    }

}
