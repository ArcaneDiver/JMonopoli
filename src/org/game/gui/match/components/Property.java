package org.game.gui.match.components;

import javax.swing.*;
import java.awt.*;

public class Property extends Box {


    public Property(ImageIcon icon) {
        super(icon);

        setMargin(new Insets(0,0,0,0));
        setBorder(BorderFactory.createEmptyBorder());

        setPreferredSize(new Dimension(56, 98));
    }
}
