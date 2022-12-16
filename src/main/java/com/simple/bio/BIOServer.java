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
 * bio 每个连接对应一个线程，线程也是占用内存空间，差不多1m,加上好多客户端连接浪费内存和cpu的来回切换，浪费资源，所以引出nio
 * bio 优势 可以接受很多连接 .问题 : (1) 线程内存的浪费 cpu调度的消耗.
 *
 *
 * nio read不阻塞了,但是不精准(因为可能没有都到数据)，做到了一个线程读取多个连接,所以要循环读多个socket 判断有数据就能读到
 *
 *
 */
public class BIOServer {

    public static void main(String[] args) throws Exception{
        //ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        final ExecutorService pool = new ThreadPoolExecutor(5, 10,
                60L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(false), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        //socket >=3 -> bind(3,7777) -> listen(3) -> accept(3, ) -> receive(3,)
        ServerSocket serverSocket = new ServerSocket(7777);
        //serverSocket.setSoTimeout(300); 不用于netty和nio中，用于阻塞io的超时时间
        System.out.println("server启动了");
        System.out.println("线程id= " + Thread.currentThread().getId() + "线程name=" + Thread.currentThread().getName());
        while (true){
            //System.in.read();
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
