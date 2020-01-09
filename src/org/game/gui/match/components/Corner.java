package org.game.gui.match.components;

import javax.swing.*;
import java.awt.*;

public class Corner extends Box {

    public Corner() {
        super(new ImageIcon("assets/corner.png"));

        setMargin(new Insets(0,0,0,0));
        setBorder(BorderFactory.createEmptyBorder());

        setPreferredSize(new Dimension(98, 98));
    }
}
