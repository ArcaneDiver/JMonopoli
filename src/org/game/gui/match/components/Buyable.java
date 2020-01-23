package org.game.gui.match.components;

import org.game.core.game.Player;
import org.game.core.game.PropertyType;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Buyable extends Box {

    /**
     * Costo di base che verra moltipicato in base alle case
     */
    private Integer baseCost;
    private PropertyType type;
    private Player ownedBy;

    private Integer numberOfHouses = 0;

    // Debug
    public Buyable(String iconName) {
        this("", iconName, 100, PropertyType.TESSILE);
    }

    public Buyable(String name, String iconName, Integer cost, PropertyType type) {
        super(name, new ImageIcon(iconName));

        this.baseCost = cost;
        this.type = type;
        this.ownedBy = null;
    }

    public Integer getCostRelativeToHouses(int houses) {
        if(houses == 0) return baseCost;
        else if(houses == 1) return (int) ( baseCost * 1.5 );
        else return baseCost * ( houses + 1);
    }

    @Override
    public String toString() {
        return super.toString() + "<br>" + String.format(
                "<b>Costo per acquistare:</b> %s<br>" +
                "<b>Posseduto da:</b> <i>%s</i><br>" +
                "<b>Tipologia:</b> %s<br>",
                baseCost, ownedBy == null ? "terreno aquistabile" : ownedBy.getName(), type.name());
    }
}
