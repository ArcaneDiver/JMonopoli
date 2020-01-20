package org.game.gui.match.components;

import javax.swing.*;
import java.awt.*;

public class Corner extends NonBuyable {

    public Corner() {
        this("Corner");
    }

    public Corner(String name) {
        super(name,"assets/corner.png");
    }

    public Corner(String name, String iconPath) {
        super(name, iconPath);
    }

}
