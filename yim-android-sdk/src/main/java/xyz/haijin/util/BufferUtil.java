package xyz.haijin.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * 二进制数据解析工具类
 * <p>
 * 2023/6/20.
 *
 * @author haijin
 * @version 1.0
 */
public class BufferUtil {

    /**
     * 构建请求认证消息
     * @param jsonToken 用户信息json
     * @return ignore
     */
    public static byte[] buildPacketData(String jsonToken) {
        short headerLength = 16;
        // 协议版本
        short ver = 1;
        // 协议指令
        int operation = 7;
        // 序列号
        int seq = 1;
        // 数据包体
        byte[] body = jsonToken.getBytes(StandardCharsets.UTF_8);
        // 计算包长度
        int packageLength = 4 + 2 + 2 + 4 + 4 + body.length;
        // 构建字节缓冲区，并按照协议格式填充数据
        ByteBuffer buffer = ByteBuffer.allocate(packageLength);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(packageLength);
        buffer.putShort(headerLength);
        buffer.putShort(ver);
        buffer.putInt(operation);
        buffer.putInt(seq);
        buffer.put(body);
        // 返回数据包的字节数组形式
        return buffer.array();
    }


    /**
     * 构建心跳请求二进制包
     * @return ignore
     */
    public static byte[] buildPacketPing() {
        // 构建自定义协议的数据包
        // 包长度，待填充
        int packageLength = 16;
        // 包头长度，待填充
        short headerLength = 16;
        // 协议版本
        short ver = 1;
        // 协议指令
        int operation = 2;
        // 序列号
        int seq = 1;
        // 数据包体
        // 构建字节缓冲区，并按照协议格式填充数据
        ByteBuffer buffer = ByteBuffer.allocate(packageLength);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(packageLength);
        buffer.putShort(headerLength);
        buffer.putShort(ver);
        buffer.putInt(operation);
        buffer.putInt(seq);
        // 返回数据包的字节数组形式
        return buffer.array();
    }

}
