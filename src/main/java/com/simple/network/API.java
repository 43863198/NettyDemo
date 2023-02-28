package com.simple.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 */
public class API {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost.getHostName()+ ":" + localHost.getHostAddress()); //DESKTOP-5D1RG9S/192.168.0.106

        InetAddress byName = InetAddress.getByName("192.168.0.106");
        String hostName = byName.getHostName();
        System.out.println(hostName);
    }
}
