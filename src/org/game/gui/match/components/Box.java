package org.game.gui.match.components;

import com.google.gson.annotations.SerializedName;
import javafx.util.Pair;
import jdk.nashorn.internal.scripts.JO;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.game.core.game.Game;
import org.game.core.game.Player;
import sun.security.ec.point.ProjectivePoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public abstract class Box extends JPanel implements ComponentListener, MouseListener {

    protected ArrayList<Pair<JLabel, Player>> pawns = new ArrayList<>();
    protected ImageIcon icon;


    @SerializedName("boxName")
    protected String name;

    public Box(String name, ImageIcon icon) {

        this.icon = icon;
        this.name = name;


        setLayout(new MigLayout(
                new LC().wrapAfter(2).fill().insets("0 0 30 0").debug(1000),
                new AC().align("center").gap("2"),
                new AC().align("center").gap("2")
        ));

        addComponentListener(this);
        addMouseListener(this);
    }

    public void hoverActualPlayer(Player player) {

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(player.getPawn().getImage().getScaledInstance(40, 40, Image.SCALE_FAST)));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                //label.setIcon(new ImageIcon(player.getPawn().getImage().getScaledInstance(label.getBounds().width, label.getBounds().height, Image.SCALE_FAST)));
            }
        });

        add(label, new CC().grow().height("20").width("20"));
        pawns.add(new Pair<>(label, player));

        revalidate();
    }

    public void moveAwayPlayer(Player player) {

        // https://stackoverflow.com/questions/9806421/concurrentmodificationexception-when-adding-inside-a-foreach-loop-in-arraylist
        for(Pair<JLabel, Player> pawn : new ArrayList<>(pawns)) {
            if(pawn == null) {
                throw new Error("Sei un ritardato di merda impara a programmare");
            } else {
                if(pawn.getValue().equals(player)) {
                    remove(pawn.getKey());
                    revalidate();
                    repaint();

                    pawns.remove(pawn);
                }
            }
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        icon = new ImageIcon(icon.getImage().getScaledInstance(getBounds().width, getBounds().height, Image.SCALE_FAST));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        JOptionPane.showMessageDialog(null,
                new JLabel(
                        String.format("<html><h2>%s</h2></html>", this)
                )
        );
    }

    @Override
    public String toString() {
        return String.format("<b>Nome:</b> [ %s ]", name);
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
