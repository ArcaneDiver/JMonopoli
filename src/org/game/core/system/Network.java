package org.game.core.system;

import org.game.gui.networkConnection.FoundNewServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.function.Consumer;

public class Network {
    private static IPv4 localIp;
    private static String gateway, ip;
    private static NetworkInterface actualInterface;


    public static void getNetworkIPs(int port, FoundNewServer callback) throws UnknownHostException, SocketException, InterruptedException {



        if(localIp == null)
            getLocalData();


        isReachable("localhost", port, callback);

        // Bruuuuuuuuuuuuuum
        Network.localIp.getAvailableIPs((int) Network.localIp.getNumberOfHosts())
            .parallelStream()
            .forEach(HOCisReachable(port, callback));


    }

    private static Consumer<String> HOCisReachable(int port, FoundNewServer callback) {
        // isReachable
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
                    System.out.println(output + " is on the network and port is closed");
                } else {
                   // System.out.println(output + " is on the network but port is open");
                }

            } else {
                String output = address.toString().substring(1);
                //System.out.println(output + " is not on the network");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    private static void getLocalData() throws SocketException, UnknownHostException {
        if(OSUtils.isWindows()) {
            System.out.println("Win");
            parseWindows();
        } else {
            System.out.println("Cambia PC");
        }

        actualInterface = NetworkInterface.getByInetAddress(Inet4Address.getByName(ip));
        localIp = new IPv4(ip + "/" + actualInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength());

        System.out.println("My ip: " + ip + " " + actualInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength());
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
            //pro.waitFor();
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
