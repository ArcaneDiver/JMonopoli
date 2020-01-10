package org.game.gui.match.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


abstract class Box extends JButton implements ComponentListener {

    protected ImageIcon icon;

    public Box(ImageIcon icon, int width, int height) {
        super(new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));

        this.icon = icon;

        setMargin(new Insets(0,0,0,0));
        setBorder(BorderFactory.createEmptyBorder());

        addComponentListener(this);
    }

    public void componentResized(ComponentEvent e) {
        setIcon(new ImageIcon(icon.getImage().getScaledInstance(getBounds().width, getBounds().height, Image.SCALE_FAST)));
    }

    @Override
    public void componentHidden(ComponentEvent e) {}

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}
}
