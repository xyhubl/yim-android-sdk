package xyz.haijin;

import xyz.haijin.inf.EyesWebSocketService;

/**
 * 2023/6/20.
 *
 * @author haijin
 */
public class EyesWebSocketServiceImpl implements EyesWebSocketService {

    @Override
    public void receiveMessage(String msg) {
        System.out.println("接收的消息==========："+msg);
    }
}
