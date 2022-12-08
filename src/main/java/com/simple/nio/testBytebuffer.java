package com.simple.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class testBytebuffer {
    public static void main(String[] args) {

        try(FileChannel channel = new FileInputStream("file.text").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true){
                int read = channel.read(buffer);
                if(read == -1){
                    break;
                }
                buffer.flip(); //切换之读模式
                while (buffer.hasRemaining()){
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
                buffer.clear();
            }
        } catch (IOException e) {

        }

    }
}
