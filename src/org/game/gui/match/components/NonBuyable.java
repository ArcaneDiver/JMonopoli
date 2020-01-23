package org.game.gui.match.components;

import javax.swing.*;

public class NonBuyable extends Box {

    public NonBuyable(String iconName) {
       this("", iconName);
    }

    public NonBuyable(String name, String iconName) {
        super(name, new ImageIcon(iconName));
    }
}
