package com.simple.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileUploadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务端在8888端口接听");
        while (true){
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            byte[] bytes = streamToByteArray(bis);

            String dest = "src\\1.png";
            FileOutputStream fos = new FileOutputStream(dest);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            bos.close();

        }

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
