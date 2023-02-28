package com.simple.chat.client.service;

import com.simple.chat.client.common.Message;
import com.simple.chat.client.common.MessageType;
import com.simple.chat.client.common.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {

     private User user = new User();
     private Socket socket;

     private boolean login_flag = false;


     public boolean login(String userId,String password){
         user.setUserId(userId);
         user.setPassword(password);

         try {
             socket = new Socket(InetAddress.getLocalHost(),9999);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             oos.writeObject(user);

             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Message msg = (Message)ois.readObject();
             if(msg.getMsgType().equals(MessageType.MESSAGE_LOGIN_SUCCESS)){
                 ClientConnectServer ccs = new ClientConnectServer(socket,userId);
                 ccs.start();
                 ClientConnectServerManager.addClientConnectServerThread(user.getUserId(),ccs);
                 login_flag = true;
             }else {
                 socket.close();
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
         return login_flag;
     }
}
