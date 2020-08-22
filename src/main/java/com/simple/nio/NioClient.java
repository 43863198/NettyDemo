package com.simple.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @description: socket客户端
 * @author: zzm
 * @create: 2020-08-16 01:02
 */
public class NioClient {
    public static void main(String[] args) throws Exception {
        //获取一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置为非阻塞
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 7777);
        if(!socketChannel.connect(inetSocketAddress)){
            if(!socketChannel.finishConnect()){
                System.out.println("正在连接");
            }

            String msg = "你好，这是第一条消息";
            ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
            socketChannel.write(byteBuffer);
            //System.in.read();
        }
    }
}
