package com.simple.chat.server.service;

import com.simple.chat.server.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerService extends Thread{

    private Socket socket;
    private String userId;

    public ServerService(Socket socket,String userId){
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {
        while (true){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message)ois.readObject();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
