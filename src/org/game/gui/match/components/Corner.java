package org.game.gui.match.components;

import javax.swing.*;
import java.awt.*;

public class Corner extends Box {

    public Corner() {
        this("Corner");
    }

    public Corner(String name) {
        super(name, new ImageIcon("assets/corner.png"));
    }

    public Corner(String name, String iconPath) {
        super(name, new ImageIcon(iconPath));
    }

}
