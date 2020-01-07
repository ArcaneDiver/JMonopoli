package org.game.gui.networkConnection;

import org.game.core.socket.SocketClient;
import org.json.JSONException;
import xyz.farhanfarooqui.JRocket.Client;
import xyz.farhanfarooqui.JRocket.JRocketServer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerManager extends JFrame {

    private StartGameAsServer callback;
    private final Container contentPane;

    private JPanel connectedClientContainer;
    private DefaultListModel<String> connectedClientList;

    private JRocketServer server;
    private ArrayList<SocketClient> clients = new ArrayList<>();

    public ServerManager(int port, StartGameAsServer callback) {
        super("Server");

        this.callback = callback;

        setSize(new Dimension(700, 700));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentPane = getContentPane();

        buildConnectedClientList();

        contentPane.add(connectedClientContainer);

        initServer(port);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildConnectedClientList() {
        connectedClientContainer = new JPanel();
        connectedClientList = new DefaultListModel<>();

        JList<String> list = new JList<>(connectedClientList);
        JScrollPane scrollableList = new JScrollPane(list);
        JPanel startGameContainer = new JPanel();
        JButton startGameButton = new JButton("Inizia la partita");

        connectedClientContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        connectedClientContainer.setLayout(new BoxLayout(connectedClientContainer, BoxLayout.Y_AXIS));
        connectedClientContainer.setPreferredSize(new Dimension(500, 500));

        list.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBackground(Color.WHITE);
        list.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        scrollableList.setPreferredSize(new Dimension(500, 500));

        startGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                callback.start(server);
            }
        });

        startGameContainer.setLayout(new BoxLayout(startGameContainer, BoxLayout.LINE_AXIS));
        startGameContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JLabel label = new JLabel("Client connessi: ");
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));

        startGameContainer.add(startGameButton);


        connectedClientContainer.add(label);
        connectedClientContainer.add(scrollableList);
        connectedClientContainer.add(startGameContainer);
    }

    private void initServer(int port){
        try {
            server = JRocketServer.listen(port, 1000);

            server.setOnClientConnectListener(client -> System.out.println("New client connected. ID: " + client.getId()));
            server.setHeartBeatRate(3000);

            // Lo salvo solo se si "autentica" con un nome
            server.onReceive("client_info", (data, client) -> {
                try {
                    String name = data.getString("name");
                    String ip = data.getString("ip");

                    System.out.println(ip);

                    SocketClient socketClient = new SocketClient(client, name);
                    clients.add(socketClient);

                    addNewClient(name);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewClient(String name) {
        System.out.println("aggiungo un client " + name);
        connectedClientList.addElement(name);

        paintComponents(getGraphics());

    }
}
