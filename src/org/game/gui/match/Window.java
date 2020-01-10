package org.game.gui.match;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.game.gui.match.components.Corner;
import org.game.gui.match.components.Property;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

class Window extends JFrame {

    private JPanel window;

    private JPanel board;
    private JPanel panel;

    private Container contentPane;

    public Window() {
        super("Monopoli");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window = new JPanel();
        contentPane = window;
        setContentPane(contentPane);

        contentPane.setSize(700, 700);
        contentPane.setLayout(new GridBagLayout());

        buildBoard();
        buildPanel();

        GridBagConstraints boardContr = new GridBagConstraints();
        GridBagConstraints panelContr = new GridBagConstraints();

        boardContr.gridx = boardContr.gridy = 0;
        boardContr.gridwidth = boardContr.gridheight = 1;
        boardContr.fill = GridBagConstraints.BOTH;
        boardContr.anchor = GridBagConstraints.NORTHWEST;
        boardContr.weightx = boardContr.weighty = 70;
        contentPane.add(board, boardContr);

        panelContr.gridx = 1;
        panelContr.gridy = 0;
        panelContr.gridwidth = 1;
        panelContr.gridheight = 2;
        panelContr.fill = GridBagConstraints.BOTH;
        panelContr.anchor = GridBagConstraints.NORTHEAST;
        panelContr.weightx = panelContr.weighty = 30;
        contentPane.add(panel, panelContr);

        board.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "70"));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "30"));


        // Full screen
        // setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(null);
        pack();

        setVisible(true);
    }

    private void buildBoard() {
        board = new JPanel();
        board.setLayout(new MigLayout(
                new LC().fill(),
                new AC().gap("0"),
                new AC().gap("0")
        ));

        for(int i = 0; i < 11; i++) {
            if(i == 0 || i == 10) {
                Corner corner = new Corner();

                if(i == 10) {
                    board.add(corner, new CC().height("98").width("98").grow().minWidth("10").minHeight("10").wrap());
                } else {
                    board.add(corner, new CC().height("98").width("98").grow().minWidth("10").minHeight("10"));
                }
            } else {
                Property property = new Property("assets/property/New York avenue.png", 56, 98);
                board.add(property, new CC().width("56").height("98").grow().minWidth("10").minHeight("10"));
            }
        }

        for(int i = 0; i < 18; i ++) {

            if(i % 2 == 0) {
                Property property = new Property("assets/property/Mediteranean Avenue.png", 98, 56);
                board.add(property, new CC().width("98").height("56").grow().minWidth("10").minHeight("10"));
            } else {
                Property property = new Property("assets/property/Illinois Avenue.png", 98, 56);
                board.add(property, new CC().width("98").height("56").grow().minWidth("10").minHeight("10").wrap());
            }
            if(i == 0) {
                Corner main = new Corner("assets/main.png");
                board.add(main, new CC().width("504").height("504").grow().span(9,9).minWidth("10").minHeight("10"));
            }
        }

        for(int i = 0; i < 11; i++) {
            if(i == 0 || i == 10) {
                Corner corner = new Corner();
                board.add(corner, new CC().height("98").width("98").grow().minWidth("10").minHeight("10"));
            } else {
                Property property = new Property("assets/property/North Carolina Avenue.png", 56, 98);
                board.add(property, new CC().width("56").height("98").grow().minWidth("10").minHeight("10"));
            }
        }

    }

    private void buildPanel() {
        panel = new JPanel();
    }
}
