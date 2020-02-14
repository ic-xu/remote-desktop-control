package com.example.demo.netty;


import com.example.demo.netty.handler.WSServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author ubuntu
 * @date $ $
 */
public class InitHandle extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {

        ChannelPipeline pipeline = socketChannel.pipeline();


        /*
            说明：
           1、Codec 编解码器的缩写,netty 提供处理http的编解码器
           2、webSocket 是基于http协议的，所以要用到http编解码器
           3、这点和http服务器一样
         */
        pipeline.addLast("httpHandle_encode-decode", new HttpServerCodec());

        /*
            说明：
            1、是以块方式写的，所以要添加ChunkedWriteHandler
         */

        pipeline.addLast(new ChunkedWriteHandler());

        /*
            说明：
            1、因为http协议在传输过程中是分段的，HttpObjectAggregator 就是可以将多个段
            2、聚合起来,这就是为什么当浏览器发送大量数据时就发出多次请求的原因
         */
        pipeline.addLast(new HttpObjectAggregator(8192));


          /*
            说明：
            1、对于webSocket的数据是以帧的形式传递的，
            2、可以看到webSocketFrame 下面有六个子类。
            3、浏览器请求时，地址为 ws://localhost:7000/hello,表示请求的url
            4、WebSocketServerProtocolHandler 核心功能，将http协议升级为ws协议，即保持长连接
            5、注意hello与地址中的对应关系
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

        pipeline.addLast(new WSServerHandler());
    }
}
