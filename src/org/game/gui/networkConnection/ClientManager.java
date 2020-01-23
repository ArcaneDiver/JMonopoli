package org.game.gui.networkConnection;

import org.game.core.game.Game;
import org.game.core.game.Player;
import org.game.core.system.IPv4;
import org.game.core.system.Network;
import org.json.JSONException;
import org.json.JSONObject;
import xyz.farhanfarooqui.JRocket.JRocketClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Pattern;

class ClientManager extends JFrame {

    private final Container contentPane;
    private JPanel avaiableServerList;

    private ArrayList<String> foundAddresses = new ArrayList<>();
    private final int port;
    private StartGameAsClient callback;

    private Thread netScanner;

    public ClientManager(int port, StartGameAsClient callback) {
        super("Client");

        this.port = port;
        this.callback = callback;

        setSize(700, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentPane = getContentPane();


        buildAvailableServerList();

        contentPane.add(avaiableServerList);

        netScanner = new Thread(() -> startSearchServer(port));
        netScanner.start();
;
        setLocationRelativeTo(null);
        setVisible(true);



    }

    private void buildAvailableServerList() {
        avaiableServerList = new JPanel();

        avaiableServerList.setLayout(new BoxLayout(avaiableServerList, BoxLayout.Y_AXIS));
        avaiableServerList.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Lista server disponibili");
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        JButton manual = new JButton("Manual connection");
        manual.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        manual.setName("manual");
        manual.addMouseListener(buttonClick);

        avaiableServerList.add(label);
        avaiableServerList.add(Box.createRigidArea(new Dimension(0, 40)));
        avaiableServerList.add(manual);
        avaiableServerList.add(Box.createRigidArea(new Dimension(0, 40)));
    }

    private void startSearchServer(int port){
        while(true) {
            Network.getNetworkIPs(port, this::displayNewServer);
        }
    }

    private void displayNewServer(String address) {

        for(String addr : foundAddresses) {
            if(addr.equals(address)) {
                return;
            }
        }

        foundAddresses.add(address);

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

    private final MouseAdapter buttonClick = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            JButton source = (JButton) e.getSource();

            if(source.getName().equals("manual")) {
                String ip = JOptionPane.showInputDialog(
                        JOptionPane.getFrameForComponent(contentPane),
                        "Inserisci l` ip a cui vuoi connetterti",
                        "IP",
                        JOptionPane.PLAIN_MESSAGE
                );

                if(Pattern.matches("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", ip)) {
                        connectToServer(ip);
                } else {
                    JOptionPane.showMessageDialog(
                            JOptionPane.getFrameForComponent(contentPane),
                            "IP non valido",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } else connectToServer(source.getName());

        }
    };

    private void connectToServer(String ip) {
        String name = JOptionPane.showInputDialog(
                JOptionPane.getFrameForComponent(contentPane),
                "Inserisci il tuo nome",
                "Nome giocatore",
                JOptionPane.PLAIN_MESSAGE
        );

        JRocketClient client = JRocketClient.prepare(ip, port, new JRocketClient.RocketClientListener() {
            @Override
            public void onConnect(JRocketClient socketClient) {


                JSONObject info = new JSONObject();

                try {
                    info.put("name", name);
                    info.put("ip", socketClient.getInetAddress().getHostAddress());
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

                socketClient.send("client_info", info);

                socketClient.onReceive("player", jsonObject -> {
                    JOptionPane.showMessageDialog(
                            JOptionPane.getFrameForComponent(contentPane),
                            "Connessione con il server completata",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    dispose();
                    netScanner.stop();

                    try {
                        callback.start(socketClient, Game.GSON.fromJson(jsonObject.getString("data"), Player.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

            }

            @Override
            public void onConnectFailed(JRocketClient socketClient) {
                System.out.println("Failed");
                JOptionPane.showMessageDialog(
                        JOptionPane.getFrameForComponent(contentPane),
                        "Connection failed with the server",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            @Override
            public void onDisconnect(JRocketClient jRocketClient) {
            }

        });

        client.setHeartBeatRate(3000);
        client.connect();

    }
}
