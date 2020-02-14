package com.example.demo.netty.handler;


import com.sun.jndi.toolkit.url.Uri;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;


/**
 * @author ubuntu
 * @date $ $
 */
public class HttpServerHandle extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {

        if (msg instanceof HttpRequest) {

            HttpRequest request = (HttpRequest) msg;
            try {
                Uri uri = new Uri(request.uri());
                System.out.println(uri.toString());
                System.err.println(msg.toString());
                System.out.println("**********************************************************");
            }catch (Exception e){
                e.printStackTrace();
            }

            ByteBuf byteBuf = Unpooled.copiedBuffer("hello ,我是服务器", CharsetUtil.UTF_8);
            //构造http相应
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, byteBuf);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            //将构建好的ｒｅｓｐｏｓｅ返回

            ctx.writeAndFlush(response);
        }
    }
}
