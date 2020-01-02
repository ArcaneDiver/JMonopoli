package org.game.gui.networkConnection;

import javax.swing.*;
import java.awt.*;

public class ServerManager extends JFrame {

    private final Container contentPane;

    private JPanel connectedClientList;

    public ServerManager() {
        super("Server");
        setSize(new Dimension(700, 700));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentPane = getContentPane();

        buildConnectedClientList();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildConnectedClientList() {
        connectedClientList = new JPanel();
    }
}
