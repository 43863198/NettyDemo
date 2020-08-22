package com.simple.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @description: NIO服务端
 * @author: zzm
 * @create: 2020-08-15 23:22
 */
public class NioServer {
    public static void main(String[] args) throws Exception {
        //创建一个serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(7777));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //获取一个select
        Selector selector = Selector.open();
        //将ServerSocketChannel注册到selector上，并设置为事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            //等待一秒，如果没有事件发生，就return
            if(selector.select(1000) == 0){
                System.out.println("没有事件发生");
                continue;
            }
            //如果返回>0，就获取到对应的关注事件的selectionKeys
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                SelectionKey selectionKey = keyIterator.next();
                //Acceptable说明有新的客户端连接
                if(selectionKey.isAcceptable()){
                    //为该客户端生成一个socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //将该channel注册到selector,并且是一个读事件，并关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(selectionKey.isReadable()){
                    //反向获取到对应的事件socketchannel
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("客户端读取的数据:" + new String(byteBuffer.array()));
                }
                //多线程情况下防止重复
                keyIterator.remove();
            }
        }

    }
}
