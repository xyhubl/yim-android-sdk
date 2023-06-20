package xyz.haijin;

import okhttp3.WebSocket;
import xyz.haijin.inf.EyesWebSocketService;

/**
 * 测试
 * <p>
 * 2023/6/20.
 *
 * @author haijin
 */
public class Test {

    public static void main(String[] args) {
        String json = "{\"mid\":124, \"room_id\":\"live://1000\", \"platform\":\"web\", \"accepts\":[1000,1001,1002]}";
        WebsocketClient websocketClient = WebsocketClient.getInstance(json);
        EyesWebSocketService eyesWebSocketService = new EyesWebSocketServiceImpl();
        WebSocket webSocket = websocketClient.connectWebSocket(eyesWebSocketService);

    }

}
