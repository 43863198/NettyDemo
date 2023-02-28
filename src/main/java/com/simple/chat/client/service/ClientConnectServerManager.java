package com.simple.chat.client.service;

import java.util.HashMap;

public class ClientConnectServerManager {

    private static HashMap<String,ClientConnectServer> hm = new HashMap<>();

    public static void addClientConnectServerThread(String userId,ClientConnectServer clientConnectServer) {
        hm.put(userId,clientConnectServer);
    }

    public static ClientConnectServer getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }
}
