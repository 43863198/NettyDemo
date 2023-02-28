package com.simple.bio;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class fileDownServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务器连接已建立");
        Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            byte[] b = new byte[1024];
            int len;
            String fileDownName = null;
            while ((len = inputStream.read(b))!= -1){
                fileDownName += new String(b, 0, len);
            }

            String destFileName = null;
            if("1".equals(fileDownName)){
                destFileName = "src\\1.png";
            }else {
                destFileName = "src\\2.png";
            }
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(destFileName));
        byte[] bytes = streamToByteArray(bis);

        OutputStream outputStream = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        bos.write(bytes);
        socket.shutdownOutput();

        bos.close();
        bis.close();
        socket.close();
        serverSocket.close();
        System.out.println("服务端退出");


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
