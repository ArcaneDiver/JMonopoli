package org.game.gui.match.components;

import jdk.nashorn.internal.scripts.JO;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.game.core.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public abstract class Box extends JPanel implements ComponentListener, MouseListener {

    protected JLabel pawn;
    protected ImageIcon icon;

    protected String name;
    protected int costo;


    public Box(String name, ImageIcon icon, int costo) {

        this.icon = icon;
        this.name = name;
        this.costo = costo;


        pawn = new JLabel();


        add(pawn, BorderLayout.CENTER);


        addComponentListener(this);
        addMouseListener(this);

    }

    public void hoverActualPlayer(Player player) {
        pawn.setIcon(new ImageIcon(player.getPawn().getImage().getScaledInstance(60, 60, Image.SCALE_FAST)));
    }

    public void moveAwayPlayer(Player player) {
        pawn.setIcon(null);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        icon = new ImageIcon(icon.getImage().getScaledInstance(getBounds().width, getBounds().height, Image.SCALE_FAST));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        JOptionPane.showMessageDialog(null,
                new JLabel(
                        String.format("<html><p><b>Nome:</b> %s <br>Costo: %s</p></html>", name, costo)
                )
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon.getImage(), 0, 0, (img, infoflags, x, y, width, height) -> false);

    }

    @Override
    public void componentHidden(ComponentEvent e) {}

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}



    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
