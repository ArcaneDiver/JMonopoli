package org.game.gui.networkConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConnectionType extends JFrame {

    private Container contentPane;
    private JPanel choose;

    private ChooseConnection chooseCallBack;

    public ConnectionType(ChooseConnection callback) {

        super("Scegli se hostare o connetterti");
        setSize(700, 700);
        contentPane = getContentPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chooseCallBack = callback;

        buildChoose();

        contentPane.add(choose, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void buildChoose() {
        choose = new JPanel();

        JButton clientButton = new JButton("Connect to a server");
        clientButton.setName("client");
        clientButton.addMouseListener(mouseClickListener);

        JButton serverButton = new JButton("Create a server");
        serverButton.setName("server");
        serverButton.addMouseListener(mouseClickListener);

        choose.setLayout(new GridLayout(2, 0, 20, 20));


        choose.add(clientButton);
        choose.add(serverButton);

    }

    private MouseAdapter mouseClickListener = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            JButton source = (JButton) e.getSource();
            if(source.getName().equals("server"))
                chooseCallBack.choose(true);
            else {
                System.out.println(source.getName());
                chooseCallBack.choose(false);
            }
            dispose();
        }
    };
}
