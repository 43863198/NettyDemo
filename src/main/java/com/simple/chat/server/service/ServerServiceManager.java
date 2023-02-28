package com.simple.chat.server.service;

import java.util.HashMap;

public class ServerServiceManager {


    public static HashMap<String,ServerService> hm = new HashMap<>();

    public static void addServiceServiceThread(String userId,ServerService serverService){
        hm.put(userId,serverService);
    }
}
