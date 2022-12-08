package com.simple.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;



public class TestLenthFieldDecode {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(new LengthFieldBasedFrameDecoder(1024,0,4,0,0),
                new LoggingHandler(LogLevel.DEBUG));

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer,"hello world");
        channel.writeInbound(buffer);
    }

    private static void send(ByteBuf buffer, String context){
        byte[] bytes = context.getBytes();
        int length = bytes.length;
        buffer.writeInt(length);
        buffer.writeBytes(bytes);
    }
}
