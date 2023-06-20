package xyz.haijin;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okio.ByteString;
import xyz.haijin.conf.SocketConfig;
import xyz.haijin.inf.EyesWebSocketService;
import xyz.haijin.listener.EyesWebSocketListener;
import xyz.haijin.util.BufferUtil;
import xyz.haijin.util.LogUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * websocket连接客户端
 * <p>
 * 2023/6/20.
 *
 * @author haijin
 * @version 1.0
 */
public class WebsocketClient implements EyesWebSocketListener.EyesWebsocketInterface {

    private final OkHttpClient mClient;

    private Request request;

    private ScheduledExecutorService executorService;

    private final String jsonAuth;

    private static volatile WebsocketClient instance;


    /**
     * 是否已经发送心跳
     */
    private  Boolean isHearBeat = false;

    private WebsocketClient(String jsonAuth) {
        this.jsonAuth = jsonAuth;
        mClient = new OkHttpClient.Builder()
                //设置读取超时时间
                .readTimeout(SocketConfig.READ_TIME_OUT, TimeUnit.SECONDS)
                //设置写的超时时间
                .writeTimeout(SocketConfig.WRITE_TIME_OUT, TimeUnit.SECONDS)
                //设置连接超时时间
                .connectTimeout(SocketConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 获取websocket客户端，单例
     * @param jsonAuth 用户认证信息
     * @return ignore
     */
    public static synchronized WebsocketClient getInstance(String jsonAuth) {
        if(instance == null) {
            synchronized (WebsocketClient.class) {
                if(instance == null) {
                    instance = new WebsocketClient(jsonAuth);
                }
            }
        }
        return instance;
    }

    /**
     * 连接websocket
     * @return ignore
     */
    public WebSocket connectWebSocket(EyesWebSocketService eyesWebsocketService) {
        //构建一个连接请求对象
        request = new Request.Builder().get().url(SocketConfig.URL).build();
        executorService = Executors.newSingleThreadScheduledExecutor();
        EyesWebSocketListener eyesWebSocketListener = new EyesWebSocketListener(eyesWebsocketService,this);
        return mClient.newWebSocket(request,eyesWebSocketListener);
    }

    @Override
    public void heartBeat(WebSocket webSocket) {
        // 如果线程池没有被终止则不进行发送心跳
        if (isHearBeat) {
            return;
        }
        LogUtil.out("WebSocketheartBeat:"+!executorService.isTerminated());
        // 构建二进制心跳数据包
        byte[] packetPing = BufferUtil.buildPacketPing();
        final ByteString heartbeatMsg =ByteString.of(packetPing);
        isHearBeat = true;
        executorService.scheduleAtFixedRate(() -> {
            boolean send = webSocket.send(heartbeatMsg);
            LogUtil.out("heartBeat:"+send);
        }, SocketConfig.HEART_BEAT_DELAY_TIME, SocketConfig.HEART_BEAT_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String getAuthJson() {
        return jsonAuth;
    }

    @Override
    public void heartBeatShutDown() {
        executorService.shutdown();
    }
}
