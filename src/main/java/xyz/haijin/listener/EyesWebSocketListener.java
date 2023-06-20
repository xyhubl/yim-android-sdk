package xyz.haijin.listener;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.haijin.inf.EyesWebSocketService;
import xyz.haijin.util.BufferUtil;
import xyz.haijin.util.LogUtil;

/**
 * websocket监听类
 * <p>
 * 2023/6/20.
 *
 * @author haijin
 * @version 1.0
 */
public class EyesWebSocketListener extends WebSocketListener {

    private final EyesWebSocketService eyesWebsocketService;

    private final EyesWebsocketInterface eyesWebsocketInterface;

    /**
     * 切割消息的下标索引
     */
    private static final Integer SUB_MESSAGE_INDEX = 32;

    public EyesWebSocketListener(EyesWebSocketService eyesWebsocketService, EyesWebsocketInterface eyesWebsocketInterface) {
        this.eyesWebsocketService = eyesWebsocketService;
        this.eyesWebsocketInterface = eyesWebsocketInterface;
    }


    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
        LogUtil.out("onClosed:"+reason);
        // 连接关闭则关闭心跳请求
        eyesWebsocketInterface.heartBeatShutDown();
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
        LogUtil.out("onClosing:"+reason);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        LogUtil.out("onFailure:"+t);
        LogUtil.out("onFailure:"+response);
        // 失败则关闭心跳请求
        eyesWebsocketInterface.heartBeatShutDown();
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        LogUtil.out("onMessage:"+text);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        super.onMessage(webSocket, bytes);
        String str = new String(bytes.toByteArray());
        if (str.length() > SUB_MESSAGE_INDEX) {
            str = str.substring(SUB_MESSAGE_INDEX);
            eyesWebsocketService.receiveMessage(str);
        }
        LogUtil.out("onMessage:"+bytes);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        byte[] bytes = BufferUtil.buildPacketData(eyesWebsocketInterface.getAuthJson());
        ByteString of = ByteString.of(bytes);
        boolean send = webSocket.send(of);
        // 认证通过则发送心跳请求
        LogUtil.out("发送心跳请求："+send);
        if (send) {
            eyesWebsocketInterface.heartBeat(webSocket);
        }
    }

    /**
     * 回调接口
     */
    public interface EyesWebsocketInterface {

        /**
         * 心跳方法
         * @param webSocket webSocket
         */
        void heartBeat(WebSocket webSocket);

        /**
         * 获取认证json
         * @return ignore
         */
        String getAuthJson();

        /**
         * 关闭心跳请求
         */
        void heartBeatShutDown();
    }
}
