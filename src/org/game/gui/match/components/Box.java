package org.game.gui.match.components;

import com.google.gson.annotations.SerializedName;
import javafx.util.Pair;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.game.core.game.Game;
import org.game.core.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public abstract class Box extends JPanel implements ComponentListener, MouseListener {

    private JPanel pawnContainer = new JPanel();
    protected ArrayList<Pair<JLabel, Player>> pawns = new ArrayList<>(4);
    protected ImageIcon icon;


    @SerializedName("boxName")
    protected String name;

    public Box(String name, ImageIcon icon) {

        this.icon = icon;
        this.name = name;

        setLayout(new MigLayout(
                new LC().fill().insets("0 0 0 0").wrapAfter(2),
                new AC().align("center"),
                new AC().align("center")
        ));

        for(int i = 0; i < Game.MAX_NUMBER_OF_PLAYERS; i++) {
            JLabel label = new JLabel();

            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

            label.setBorder(BorderFactory.createEmptyBorder());

            label.setVisible(false);

            pawns.add(new Pair<>(label, null));
            add(label, new CC().grow().minWidth("1").minHeight("1").width("20").height("20"));
        }

        addComponentListener(this);
        addMouseListener(this);
    }

    public void hoverActualPlayer(Player player) {
        for(Pair<JLabel, Player> pair : new ArrayList<>(pawns)) {
            if(pair.getValue() == null) {
                Pair<JLabel, Player> newPair = new Pair<>(pair.getKey(), player);
                pawns.set(pawns.indexOf(pair), newPair);
                pair = newPair;
            }

            if(pair.getValue().equals(player)) {
                int smallestSize = getBounds().width < getBounds().height ? getBounds().width : getBounds().height;
                pair.getKey().setIcon(new ImageIcon(player.getPawn().getImage().getScaledInstance(
                        smallestSize / 4 != 0 ? smallestSize / 4  : 20,
                        smallestSize / 4 != 0 ? smallestSize / 4 : 20,
                        Image.SCALE_FAST
                )));


                pair.getKey().setVisible(true);

                break;
            }
        }
    }

    public void moveAwayPlayer(Player player) {

        // https://stackoverflow.com/questions/9806421/concurrentmodificationexception-when-adding-inside-a-foreach-loop-in-arraylist
        for(Pair<JLabel, Player> pawn : pawns) {
            if(pawn == null) {
                throw new Error("Sei un ritardato di merda impara a programmare");
            } else {
                System.out.println(pawn.getValue());
                if(pawn.getValue() != null && pawn.getValue().equals(player)) {
                    pawn.getKey().setVisible(false);
                    break;
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
