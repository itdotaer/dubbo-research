package com.itdotaer.dubbo.research.netty.subreq;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

/**
 * SubReqClientHandler
 *
 * @author jt_hu
 * @date 2018/9/28
 */
public class SubReqClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            SubReq subReq = generateSubReq(i);
            ctx.write(subReq);
        }

        ctx.flush();
    }

    private SubReq generateSubReq(int i) {
        SubReq subReq = new SubReq();

        subReq.setOrderId(Long.valueOf(i));
        subReq.setPrice(new BigDecimal("10"));
        subReq.setProductName("太湖一日游");

        return subReq;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response: " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("Exception : " + cause);
        ctx.close();
    }

}
