package com.simple.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

public class NettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //serverBootstrap.option(); 是给serversocketchannel配置参数
            // serverBootstrap.childOption()  //是给socketchannel配置参数
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    //http 协议编码器
                    ch.pipeline().addLast(new HttpServerCodec());
                    ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>(){
//                        @Override
//                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                            System.out.println("connected ... ");
//                        }
//
//                        @Override
//                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                            super.channelRead(ctx, msg);
//                        }

                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                            System.out.println(msg.uri());
                            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(msg.getProtocolVersion(), HttpResponseStatus.OK);
                            defaultFullHttpResponse.content().writeBytes("<h1>Hello World<h1>".getBytes());
                            byte[] bytes = "<h1>Hello World<h1>".getBytes();
                            defaultFullHttpResponse.headers().setInt(CONTENT_LENGTH,bytes.length);
                            ctx.writeAndFlush(defaultFullHttpResponse);

                        }
                    });
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            System.out.println("server error");
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
