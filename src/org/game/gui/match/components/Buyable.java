package org.game.gui.match.components;

import org.game.core.game.PropertyType;

import javax.swing.*;
import java.util.ArrayList;

public class Buyable extends Box {

    /**
     * Costo di base che verra moltipicato in base alle case
     */
    private Integer baseCost;
    private PropertyType type;

    private Integer numberOfHouses = 0;

    // Debug
    public Buyable(String iconName) {
        this("", iconName, 100, PropertyType.TESSILE);
    }

    public Buyable(String name, String iconName, Integer cost, PropertyType type) {
        super(name, new ImageIcon(iconName), 1000);

        this.baseCost = cost;
        this.type = type;
    }

    public Integer getCostRelativeToHouses() {
        if(numberOfHouses == 0) return baseCost;
        else if(numberOfHouses == 1) return (int) ( baseCost * 1.5 );
        else return baseCost * ( numberOfHouses + 1);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
