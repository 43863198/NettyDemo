package com.simple.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class testFileChannelTransfer {
    public static void main(String[] args) {
        try(
                FileChannel from = new FileInputStream("file.text").getChannel();
                FileChannel to = new FileOutputStream("out.txt").getChannel();
                ){
            long size = from.size();
            for (long left = size; left > 0;){

                left -= from.transferTo(size-left, left, to);
            }


        }catch (Exception e){

        }
    }
}
