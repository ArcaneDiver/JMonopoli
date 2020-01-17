package org.game.core.game;

import org.game.gui.match.components.Buyable;

import java.util.ArrayList;

public class Player {

    private String name;
    private Integer budget;
    private ArrayList<Buyable> properties;

    public Player(String name, int startBudget) {

        this.name = name;
        budget = startBudget;
        properties = new ArrayList<>();
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
}
