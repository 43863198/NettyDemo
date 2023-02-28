package com.simple.bio;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class fileDownClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入下载文件名字");
        String fileName = scanner.next();

        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);

        OutputStream outputStream = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        bos.write(fileName.getBytes());
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        byte[] bytes = streamToByteArray(bis);

        String filepath = "src\\3.png";
        BufferedOutputStream bos1 = new BufferedOutputStream(new FileOutputStream(filepath));
        bos1.write(bytes);

        bos1.close();
        bis.close();
        bos.close();
        socket.close();


    }

    private static byte[] streamToByteArray(BufferedInputStream bis) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len;
        while ((len = bis.read(b)) != -1 ){
            bos.write(b,0,len);
        }
        byte[] bytes = bos.toByteArray();
        bos.close();
        return bytes;
    }
}
