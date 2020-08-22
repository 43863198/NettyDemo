package com.simple.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @description: BIO Server
 * @author: zhaozhiming
 * @create: 2020-06-07 00:16
 */
public class BIOServer {

    public static void main(String[] args) throws Exception{
        //ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        final ExecutorService pool = new ThreadPoolExecutor(5, 10,
                60L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(false), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        ServerSocket serverSocket = new ServerSocket(7777);
        System.out.println("server启动了");
        System.out.println("线程id= " + Thread.currentThread().getId() + "线程name=" + Thread.currentThread().getName());
        while (true){
            final Socket socket = serverSocket.accept();
            System.out.println("接受到一个客户端");
            System.out.println("线程id= " + Thread.currentThread().getId() + "线程name=" + Thread.currentThread().getName());
            pool.execute(new Runnable() {
                public void run() {
                   handler(socket);
                }
            });
        }
    }
    public static void handler(Socket socket){
        byte[] bytes = new byte[1024];
        try {
            System.out.println("线程id= " + Thread.currentThread().getId() + "线程name=" + Thread.currentThread().getName());
            //获取输入流
            InputStream inputStream = socket.getInputStream();
            //循环读取socket输入流
            while (true){
                int read = inputStream.read(bytes);
                if (read != -1){
                    System.out.println(new String(bytes,0,read));
                }else {
                    System.out.println("输出" + new String(bytes,0,read));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
