package org.game.gui.networkConnection;

import org.game.core.system.Network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ClientManager extends JFrame {

    private final Container contentPane;

    private JPanel avaiableServerList;


    public ClientManager(int port) {
        super("Client");
        setSize(700, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentPane = getContentPane();


        buildAvaibleServerList();

        contentPane.add(avaiableServerList);

        new Thread(() -> startSearchServer(port)).start();
;
        setLocationRelativeTo(null);
        setVisible(true);



    }

    private void buildAvaibleServerList() {
        avaiableServerList = new JPanel();

        avaiableServerList.setLayout(new BoxLayout(avaiableServerList, BoxLayout.Y_AXIS));
        avaiableServerList.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Lista server disponibili");
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        avaiableServerList.add(label);
        avaiableServerList.add(Box.createRigidArea(new Dimension(0, 40)));
    }

    private void startSearchServer(int port){

        try {
            Network.getNetworkIPs(port, this::displayNewServer);
        } catch (UnknownHostException | SocketException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void displayNewServer(String address) {
        JButton bt = new JButton(address);

        bt.setName(address);
        bt.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        bt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bt.setMargin(new Insets(0, 0, 0, 0));
        bt.addMouseListener(buttonClick);

        avaiableServerList.add(bt);
        avaiableServerList.add(Box.createRigidArea(new Dimension(0, 40)));

        //Senno non painta
        paintComponents(getGraphics());
    }

    private MouseAdapter buttonClick = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            JButton source = (JButton) e.getSource();

            avaiableServerList.setVisible(false);
        }
    };

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        System.out.println("Paint called");
    }
}
