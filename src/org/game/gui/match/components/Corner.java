package org.game.gui.match.components;

import javax.swing.*;
import java.awt.*;

public class Corner extends Box {

    public Corner() {
        this(98);
    }

    public Corner(String iconPath) {
        this(iconPath, 98);
    }

    public Corner(String iconPath, int dim) {
        super(new ImageIcon(iconPath), dim, dim);
    }
    public Corner(int dim) {
        super(new ImageIcon("assets/corner.png"), dim, dim);
    }
}
