package org.game.gui.match;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private JPanel window;

    private JPanel board;
    private JPanel panel;

    private Container contentPane;

    public Window() {
        super("Monopoli");

        setSize(1000, 1000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window = new JPanel();


        contentPane = window;
        setContentPane(contentPane);


        buildBoard();
        buildPanel();

        contentPane.add(board);
        contentPane.add(panel);

        // Full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildBoard() {

    }

    private void buildPanel() {

    }

}
