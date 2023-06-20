package xyz.haijin.inf;

/**
 * websocket业务回调接口
 * <p>
 * 2023/6/20.
 *
 * @author haijin
 * @version 1.0
 */
public interface EyesWebSocketService {

    /**
     * 接收消息回调
     * @param msg 消息
     */
    void receiveMessage(String msg);

}
