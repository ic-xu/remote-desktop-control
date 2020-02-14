package com.example.demo.netty.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author ubuntu
 * @date $ $
 */
public class WSServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    /*
    当连接后触发
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        System.out.println("服务器收到的消息为："+msg.text());
        ctx.writeAndFlush(new TextWebSocketFrame(new Date().toString()+" : "+msg.text()));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
