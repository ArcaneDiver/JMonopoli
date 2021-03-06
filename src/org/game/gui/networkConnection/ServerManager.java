package org.game.gui.networkConnection;

import org.game.core.game.Player;
import org.game.core.game.Game;
import org.game.core.system.Network;
import org.json.JSONException;
import org.json.JSONObject;
import xyz.farhanfarooqui.JRocket.JRocketServer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

class ServerManager extends JFrame {

    private StartGameAsServer callback;
    private final Container contentPane;

    private JPanel connectedClientContainer;
    private DefaultListModel<String> connectedClientList;

    private JRocketServer server;
    private ArrayList<Player> clients = new ArrayList<>();

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

        JLabel label = new JLabel("Client connessi: ");
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        JList<String> list = new JList<>(connectedClientList);
        JScrollPane scrollableList = new JScrollPane(list);
        JPanel startGameContainer = new JPanel();
        JButton startGameButton = new JButton("Inizia la partita");
        JLabel actualIP = new JLabel("IP: " + Network.ip);

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

                String name = JOptionPane.showInputDialog(
                        JOptionPane.getFrameForComponent(contentPane),
                        "Inserisci il tuo nome",
                        "Nome giocatore",
                        JOptionPane.PLAIN_MESSAGE
                );
                callback.start(server, new Player(name, Game.INITIAL_BUDGET, String.format("assets/pawn%s.png", clients.size())), clients);
            }
        });

        actualIP.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        startGameContainer.setLayout(new BoxLayout(startGameContainer, BoxLayout.LINE_AXIS));
        startGameContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));



        startGameContainer.add(startGameButton);
        startGameContainer.add(Box.createRigidArea(new Dimension(30 ,0)));
        startGameContainer.add(actualIP);

        connectedClientContainer.add(label);
        connectedClientContainer.add(scrollableList);
        connectedClientContainer.add(startGameContainer);
    }

    private void initServer(int port){
        try {
            server = JRocketServer.listen(port, 1000);

            server.setOnClientConnectListener(client -> {});

            server.setHeartBeatRate(3000);

            // Lo salvo solo se si "autentica" con un nome
            server.onReceive("client_info", (data, client) -> {
                try {
                    String name = data.getString("name");
                    String ip = data.getString("ip");


                    client.send("player", new JSONObject()
                        .put("data", Game.GSON.toJson(addNewClient(name), Player.class))
                    );


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Player addNewClient(String name) {

        System.out.println("aggiungo un client " + name);

        connectedClientList.addElement(name);
        Player player = new Player(name, Game.INITIAL_BUDGET, String.format("assets/pawn%s.png", clients.size()));
        clients.add(player);

        revalidate();
        repaint();

        return player;
    }
}
