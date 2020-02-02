package org.game.core.system;

import org.game.gui.networkConnection.FoundNewServer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class Network {
    private static IPv4 localIp;
    public static String gateway, ip;
    private static NetworkInterface actualInterface;


    public static void getNetworkIPs(int port, FoundNewServer callback) {
        if(localIp == null)
            getLocalData();

        isReachable("localhost", port, callback);

        Network.localIp.getAvailableIPs((int) Network.localIp.getNumberOfHosts())
                .parallelStream()
                .forEach(HOCisReachable(port, callback));
    }

    private static Consumer<String> HOCisReachable(int port, FoundNewServer callback) {

        return actualHost -> isReachable(actualHost, port, callback);
    }

    private static void isReachable(String actualHost, int port, FoundNewServer callback) {
        byte[] ip = new byte[0];

        try {
            ip = InetAddress.getByName(actualHost).getAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        InetAddress address = null;
        try {
            address = InetAddress.getByAddress(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            assert address != null;
            if (address.isReachable(100)) {
                String output = address.toString().substring(1);
                if(Network.isUsed(output, port)) {
                    callback.found(output);
                    System.out.println(output + " is on the network and server available");
                } else {
                   //System.out.println(output + " is on the network but port is open");
                }

            } else {
                String output = address.toString().substring(1);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    public static void getLocalData() {
        if(OSUtils.isWindows()) {
            parseWindows();
        } else {
            while(true) {
                new Thread(() -> {
                    final JOptionPane pane = new JOptionPane("<h1>Cambia PC</h1>");
                    final JDialog d = pane.createDialog(null, "");
                    d.setLocation(
                            (int) (Math.random() * Toolkit.getDefaultToolkit().getScreenSize().width),
                            (int) (Math.random() * Toolkit.getDefaultToolkit().getScreenSize().height)
                    );
                    d.setVisible(true);
                }).start();
            }
        }

        try {
            actualInterface = NetworkInterface.getByInetAddress(Inet4Address.getByName(ip));
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }

        try {
            localIp = new IPv4(ip + "/" + actualInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private static void parseWindows() {
        try
        {
            Process pro = Runtime.getRuntime().exec("cmd.exe /c route print");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            String line;
            while((line = bufferedReader.readLine())!=null)
            {
                line = line.trim();
                StringTokenizer rawToken = new StringTokenizer(line, " ", false);// line.split(" ");

                List<String> tokens = new ArrayList<>();

                // iterate through StringTokenizer tokens
                while(rawToken.hasMoreTokens()) {

                    // add tokens to AL
                    tokens.add(rawToken.nextToken());
                }

                if(tokens.size() == 5 && tokens.get(0).equals("0.0.0.0"))
                {
                    gateway = tokens.get(2);
                    ip = tokens.get(3);
                    return;
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private static boolean isUsed(String address, int port) {
        try (Socket ignored = new Socket(address, port)) {
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }
}
