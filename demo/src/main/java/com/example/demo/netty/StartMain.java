package com.example.demo.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author ubuntu
 * @date $ $
 */
public class StartMain {


    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new InitHandle());
            ChannelFuture channelFuture = bootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }
}
