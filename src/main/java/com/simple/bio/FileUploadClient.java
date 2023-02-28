package com.simple.bio;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileUploadClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8888);
        String filePath = "D:\\简历作品集2021\\1.jpg";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        BufferedInputStream bis = new BufferedInputStream(fileInputStream);
        byte[] bytes = streamToByteArray(bis);

        OutputStream outputStream = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        bos.write(bytes);
        bis.close();
        socket.shutdownOutput(); //设置结束标记

        bos.close();
        socket.close();
    }

    public static byte[] streamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024]; //字节数组
        int len;
        while ((len= is.read(b))!= -1){ //循环读取
            bos.write(b,0,len); //读取到bos
        }
        byte[] bytes = bos.toByteArray();
        bos.close();
        return bytes;
    }

}
