package org.game.gui.match;

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

        contentPane.setSize(1000, 700);
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
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void buildBoard() {
        board = new JPanel();
        board.setLayout(new BoxLayout(board, BoxLayout.PAGE_AXIS));

        JPanel firstRow = new JPanel();
        firstRow.setPreferredSize(new Dimension());
        firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.X_AXIS));



        for (int i = 0; i < 11; i++) {
            if (i == 0 || i == 10) {
                ImageIcon icon = new ImageIcon("assets/corner.png");
                JButton corner = new JButton(icon);
                corner.setMargin(new Insets(0,0,0,0));
                corner.setBorder(BorderFactory.createEmptyBorder());
                corner.setPreferredSize();

                firstRow.add(corner);
            } else {
                ImageIcon icon = new ImageIcon("assets/property/New York avenue.png");
                JButton property = new JButton(icon);
                property.setMargin(new Insets(0,0,0,0));
                property.setBorder(BorderFactory.createEmptyBorder());

                firstRow.add(property);
            }
        }

        JPanel column1 = new JPanel();
        column1.setLayout(new BoxLayout(column1, BoxLayout.Y_AXIS));

        for(int i = 0; i < 9; i++) {
            ImageIcon icon = new ImageIcon("assets/property/Mediteranean Avenue.png");
            JButton property = new JButton(icon);
            property.setMargin(new Insets(0,0,0,0));
            property.setBorder(BorderFactory.createEmptyBorder());

            column1.add(property);
        }

        JPanel column2 = new JPanel();
        column2.setLayout(new BoxLayout(column2, BoxLayout.Y_AXIS));

        for(int i = 0; i < 9; i++) {
            Property property = new Property(new ImageIcon("assets/property/Illinois Avenue.png"));
            column2.add(property);
        }

        ImageIcon bg = new ImageIcon("assets/main.png");
        JButton mainBoard = new JButton(bg);
        mainBoard.setMargin(new Insets(0,0,0,0));
        mainBoard.setBorder(BorderFactory.createEmptyBorder());


        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));

        center.add(column1);
        center.add(mainBoard);
        center.add(column2);


        JPanel lastRow = new JPanel();
        lastRow.setLayout(new BoxLayout(lastRow, BoxLayout.X_AXIS));

        for (int i = 0; i < 11; i++) {
            if (i == 0 || i == 10) {
                Corner corner = new Corner();
                lastRow.add(corner);
            } else {

                Property property = new Property(new ImageIcon("assets/property/BoardWalk.png"));
                lastRow.add(property);
            }
        }

        board.add(firstRow);
        board.add(center);
        board.add(lastRow);
    }

    private void buildPanel() {
        panel = new JPanel();
    }
}
