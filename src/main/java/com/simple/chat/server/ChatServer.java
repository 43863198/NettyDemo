package com.simple.chat.server;

import com.simple.chat.client.common.Message;
import com.simple.chat.client.common.User;
import com.simple.chat.server.common.MessageType;
import com.simple.chat.server.service.ServerService;
import com.simple.chat.server.service.ServerServiceManager;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ChatServer {

    private ServerSocket serverSocket;
    private User user = new User();
    private Socket socket;

    public static void main(String[] args) {
        new ChatServer();
    }
    private static HashMap<String ,User> users = new HashMap<>();
    {
        users.put("111",new User("111","123456"));
        users.put("222",new User("222","123456"));
        users.put("333",new User("333","123456"));
        users.put("444",new User("444","123456"));
    }
    public ChatServer(){
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("服务端在9999端口监听");
            while (true){
                socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                user = (User) ois.readObject();
                Message msg = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());;
                if(checkUser(user)){ //用户正确
                    msg.setMsgType(MessageType.MESSAGE_LOGIN_SUCCESS);
                    oos.writeObject(msg);
                    ServerService serverService = new ServerService(socket, user.getUserId());
                    serverService.start();
                    ServerServiceManager.addServiceServiceThread(user.getUserId(),serverService);
                }else {
                    System.out.println("登录失败" + user.getUserId());
                    msg.setMsgType(MessageType.MESSAGE_LOGIN_FAILED);
                    oos.writeObject(msg);
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkUser(User user) {
        User user1 = users.get(user.getUserId());
        if(user1 == null){
            return false;
        }
        if(!user.getPassword().equals(user1.getPassword())){
            return false;
        }
        return true;
    }
}
