package org.game.gui.match.components;

import org.game.core.game.Game;
import org.game.core.game.Player;
import org.game.core.game.PropertyType;

import com.google.gson.annotations.*;
import javax.swing.*;

public class Buyable extends Box {


    @Expose private Integer baseCost;
    @Expose private Integer actualCost;
    @Expose private PropertyType type;
    @Expose(serialize = false) private Player owner;

    @Expose private ImageIcon verticalIcon;
    @Expose private Integer numberOfHouses = 0;

    public Buyable(String name, String iconName, Integer cost, PropertyType type) {
        super(name, new ImageIcon(iconName));

        this.baseCost = cost;
        this.type = type;
        this.owner = null;
        actualCost = baseCost;
        this.verticalIcon = new ImageIcon(getVerticalIconFromType(type));
    }

    public boolean isBuyable() {
        return owner == null;
    }


    public void setOwner(Player player) {
        owner = player;
    }

    public Player getOwner(){
        return owner;
    }

    public void buyHome(Player player) {

    }

    public Integer getCostRelativeToHouses() {
        if(numberOfHouses == 0) return actualCost;
        else if(numberOfHouses == 1) return (int) ( actualCost * 1.5 );
        else return actualCost * ( numberOfHouses + 1);
    }

    public void updatePrice() {
        actualCost = (int) (baseCost * Game.EVENT_MULTIPLIER.get(type));
    }

    public ImageIcon getVerticalIcon() {
        return verticalIcon;
    }

    private String getVerticalIconFromType(PropertyType type) {
        switch (type) {
            case AUTOMOBILISTICA:
                return "assets/mini_property/automobilistica.png";
            case SPAZIALE:
                return "assets/mini_property/spaziale.png";
            case TESSILE:
                return "assets/mini_property/tessile.png";
            case SVILUPPO:
                return "assets/mini_property/sviluppo.png";
            case CASE_DEL_POPOLO:
                return "assets/mini_property/alberghiere.png";
            case ALIMENTARI:
                return "assets/mini_property/alimentari.png";
            case FERROVIERE:
                return "assets/mini_property/ferroviere.png";
            case PRODUZIONE_ARMI:
                return "assets/mini_property/prod_armi.png";
            default:
                throw new Error("Michele è ritardato");
        }
    }
    @Override
    public String toString() {
        return super.toString() + "<br>" + String.format(
                "<b>Costo:</b> %s RUB<br>" +
                "<b>Posseduto da:</b> <i>%s</i><br>" +
                "<b>Tipologia:</b> %s<br>" +
                "<b>Costo di base</b> %s<br>",
                actualCost, owner == null ? "terreno aquistabile" : owner.getName(), type.name(), baseCost);
    }
}
