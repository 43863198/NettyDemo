package com.simple.chat.client.view;

import com.simple.chat.client.service.UserClientService;

import java.util.Scanner;

public class ChatView {

    private boolean loop = true;
    String key;

    private UserClientService userClientService = new UserClientService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new ChatView().menu();
    }
    private void menu(){
        while (loop){
            System.out.println("==================欢迎登陆系统===============");
            System.out.println("\t\t 1 登陆系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择：");
            key = readString(1);
            switch (key){
                case "1":
                    System.out.println("请输入用户号： ");
                    String id = readString(50);
                    System.out.println("请输入密码： ");
                    String password = readString(50);
                    //写发送id 和 password 去server端验证
                    if(userClientService.login(id, password)){
                        while (loop){
                            System.out.println("================二级菜单(用户"+id + ")" + "============");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择：");
                            key = readString(1);
                            switch (key){
                                case "1":
                                    System.out.println("显示在线列表");
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    System.out.println("退出系统");
                                    loop = false;
                                    break;
                            }
                        }
                    }else {
                        System.out.println("输入信息错误");
                    }

                    break;
                case "9":
                    System.out.println("退出系统");
                    loop = false;
                    break;
            }
        }
    }

    private String readString(int limit) {
        String value = "";
        while (scanner.hasNext()){
            value = scanner.nextLine();
            if(value.length() < 1 || value.length() > limit){
                System.out.println("输入长度不大于limit, 请重新输入：");
            }
            break;
        }
        return value;
    }
}
