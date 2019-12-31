package org.game.core.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;

public class Network {
    public static IPv4 localIp;
    public static String gateway, ip;
    public static NetworkInterface actualInterface;

    public static void getNetworkIPs() throws UnknownHostException, SocketException {

        /*
            Si potrebbe migliorare cercando dal mio ip in su ed in giu
            invece che partire dall`inizio della subnet mask
         */

        if(localIp == null)
            getLocalData();

        List<String> avaiableIp = Network.localIp.getAvailableIPs((int) Network.localIp.getNumberOfHosts());

        for (String stringIp : avaiableIp) {

            byte[] ip = new byte[0];
            try {
                ip = InetAddress.getByName(stringIp).getAddress();
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

                if (address.isReachable(10)) {
                    String output = address.toString().substring(1);
                    System.out.println(output + " is on the network");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

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
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
